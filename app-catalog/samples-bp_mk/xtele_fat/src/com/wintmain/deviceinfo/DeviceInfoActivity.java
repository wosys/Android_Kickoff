/*
 * Copyright 2023-2024 wintmain
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wintmain.deviceinfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncResult;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.telephony.CellLocation;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.android.internal.telephony.IccCard;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.UiccController;
import com.android.settingslib.DeviceInfoUtils;
import com.android.settings.network.SubscriptionUtil;

import com.qti.extphone.ExtTelephonyManager;
import com.qti.extphone.QtiImeiInfo;
import com.qti.extphone.ServiceCallback;
import com.wintmain.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Display the following information # Battery Strength : TODO # Uptime # Awake Time #
 * XMPP/buzz/tickle status : TODO
 */
public class DeviceInfoActivity extends PreferenceActivity {
    // Please get these values from IccConstants
    // CDMA RUIM file ids from 3GPP2 C.S0023-0
    // RUIM EF stores the (up to) 56-bit electronic identification
    // number (ID) unique to the R-UIM. (Removable UIM_ID)
    static final int EF_RUIM_ID = 0x6f31;
    // 3GPP2 C.S0065
    static final int EF_CSIM_PRL = 0x6F30;
    // C.S0074-Av1.0 Section 4
    static final int EF_CSIM_MLPL = 0x4F20;
    static final int EF_CSIM_MSPL = 0x4F21;
    private static final String TAG = DeviceInfoActivity.class.getSimpleName();
    private static final String KEY_DEVICE_META = "device_meta";
    private static final String KEY_HW_VERSION = "hardware_version";
    private static final String KEY_MEID_ESN = "meid_esn_number";
    private static final String KEY_TELEPHONE_NUMBER = "telephone_number";
    private static final String KEY_BASEBAND_VERSION = "baseband_version";
    private static final String KEY_PRL_VERSION = "prl_version";
    private static final String KEY_ANDROID_VERSION = "android_version";
    private static final String KEY_SOFTWARE_VERSION = "software_version";
    private static final String KEY_IMSI = "imsi";
    private static final String KEY_UIM_ID = "uim_id";
    private static final String KEY_SID_NUMBER = "sid_number";
    private static final String KEY_NID_NUMBER = "nid_number";
    private static final String KEY_EPRL_NUMBER = "eprl_number";
    private static final String KEY_MSPL_NUMBER = "mspl_number";
    private static final String KEY_MLPL_NUMBER = "mlpl_number";
    private static final String KEY_ICCID_NUMBER = "iccid_number";
    private static final String KEY_IMEI_NUMBER_1 = "imei_number_1";
    private static final String KEY_IMEI_NUMBER_2 = "imei_number_2";
    private static final String SP_MEID = "sp_meid";
    private static final String SP_ESN = "sp_esn";
    private static final int PRIMARY_STACK_MODEM_ID = 0;
    private Context mContext;
    // End of IccConstants value
    private Phone mPhone = null;
    private String mUnknown = null;

    private ObtainIdHandler mHandler = new ObtainIdHandler();
    private SharedPreferences mSharedPreferences;
    private MetaInfoFetcherProxy mMetaInfoFetcherProxy;

    private ExtTelephonyManager mExtTelephonyManager = null;
    private SubscriptionManager mSubscriptionManager;
    private int mNumPhone = 1;
    private ServiceCallback mServiceCallback =
            new ServiceCallback() {

                @Override
                public void onConnected() {
                    Log.d(TAG, "ExtTelephony Service connected");
                    // get imei
                    updateImei();
                }

                @Override
                public void onDisconnected() {
                    Log.d(TAG, "ExtTelephony Service disconnected...");
                    if (isActivityEnable()) {
                        updateImeiByPhone();
                    }
                }
            };

    public static int getPrimaryStackPhoneId() {
        String modemUuId = null;
        int primayStackPhoneId = SubscriptionManager.INVALID_PHONE_INDEX;

        for (Phone phone : PhoneFactory.getPhones()) {
            if (phone == null) {
                continue;
            }

            Log.d(
                    TAG,
                    "Logical Modem id: "
                            + phone.getModemUuId()
                            + " phoneId: "
                            + phone.getPhoneId());
            modemUuId = phone.getModemUuId();
            if ((modemUuId == null) || (modemUuId.length() <= 0) || modemUuId.isEmpty()) {
                continue;
            }
            // Select the phone id based on modemUuid
            // if modemUuid is 0 for any phone instance, primary stack is mapped
            // to it so return the phone id as the primary stack phone id.
            int modemUuIdValue = PRIMARY_STACK_MODEM_ID;
            try {
                modemUuIdValue = Integer.parseInt(modemUuId);
            } catch (NumberFormatException e) {
                Log.w(TAG, "modemUuId is not an integer: " + modemUuId);
            }
            if (modemUuIdValue == PRIMARY_STACK_MODEM_ID) {
                primayStackPhoneId = phone.getPhoneId();
                Log.d(TAG, "Primay Stack phone id: " + primayStackPhoneId + " selected");
                break;
            }
        }

        // If phone id is invalid return default phone id
        if (primayStackPhoneId == SubscriptionManager.INVALID_PHONE_INDEX) {
            Log.d(TAG, "Returning default phone id");
            primayStackPhoneId = 0;
        }

        return primayStackPhoneId;
    }

    public static String getDeviceModel() {
        // Use SettingLib, getMsvSuffix(), as example.
        FutureTask<String> msvSuffixTask = new FutureTask<>(() -> DeviceInfoUtils.getMsvSuffix());

        msvSuffixTask.run();
        try {
            // Wait for msv suffix value.
            final String msvSuffix = msvSuffixTask.get();
            return Build.MODEL + msvSuffix;
        } catch (ExecutionException e) {
            Log.e(TAG, "Execution error, so we only show model name");
        } catch (InterruptedException e) {
            Log.e(TAG, "Interruption error, so we only show model name");
        }
        // If we can't get an msv suffix value successfully,
        // it's better to return model name.
        return Build.MODEL;
    }

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mContext = getBaseContext();

        addPreferencesFromResource(R.xml.device_info);

        mMetaInfoFetcherProxy = new MetaInfoFetcherProxy(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        TelephonyManager tm = getSystemService(TelephonyManager.class);
        mNumPhone = tm.getActiveModemCount();
        Log.d(TAG, "numPhone: " + mNumPhone);

        mPhone = getPhone();
        mSubscriptionManager = mContext.getSystemService(SubscriptionManager.class);
        mExtTelephonyManager = ExtTelephonyManager.getInstance(this);
        mExtTelephonyManager.connectService(mServiceCallback);
        Log.d(TAG, "Connect to ExtTelephony bound service...");

        if (mUnknown == null) {
            mUnknown = getResources().getString(R.string.device_info_unknown);
        }

        // TODO, remove this. Only for test import static_libs.
        // Now you can use class in packages/apps/Settings !!!
        boolean isSimHWV = SubscriptionUtil.isSimHardwareVisible(mContext);

        setPreferenceSummary();
        mMetaInfoFetcherProxy.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This chooses the best possible Phone object to read the values for the various fields shown
     * in this activity.
     *
     * <p>Since there are a few fields that are specific to CDMA, we first check if there is any
     * Phone object with {@link TelephonyManager#PHONE_TYPE_CDMA}.
     *
     * <p>If no CDMA type Phone is found, we go on to choose the Phone corresponding to the first
     * slot that has an active SIM card.
     *
     * <p>If there is no SIM on the device, we simply return the Phone object corresponding to the
     * first slot.
     *
     * @return the Phone object through which the values for the fields shown in this activity will
     * be retrieved
     */
    private Phone getPhone() {
        SubscriptionManager subscriptionManager =
                (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

        TelephonyManager telephonyManager =
                (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        int numPhones = telephonyManager.getActiveModemCount();
        List<SubscriptionInfo> subInfos = subscriptionManager.getActiveSubscriptionInfoList();

        if (subInfos != null) {
            // Check if there are any Phone objects of TYPE_CDMA
            for (SubscriptionInfo subInfo : subInfos) {
                if (subInfo != null) {
                    int slotId = subInfo.getSimSlotIndex();
                    if (slotId >= 0 && slotId < numPhones) {
                        Phone phone = PhoneFactory.getPhone(slotId);
                        if (phone.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
                            Log.d(TAG, "TYPE CDMA found on slot " + slotId);
                            return phone;
                        }
                    }
                }
            }

            // No CDMA phone found, return the Phone corresponding to the first active
            // SubscriptionInfo
            for (SubscriptionInfo subInfo : subInfos) {
                if (subInfo != null) {
                    int slotId = subInfo.getSimSlotIndex();
                    if (slotId >= 0 && slotId < numPhones) {
                        Phone phone = PhoneFactory.getPhone(slotId);
                        Log.d(TAG, "Active subscription info found on slot " + slotId);
                        return phone;
                    }
                }
            }
        }

        // If we have reached here, there may not be any SIM on the device, simply return the Phone
        // object corresponding to the first slot.
        return PhoneFactory.getPhone(PhoneConstants.SUB1);
    }

    private boolean isActivityEnable() {
        if (this == null || isDestroyed() || isFinishing()) {
            return false;
        }
        return true;
    }

    private void updateImeiByPhone() {
        final PreferenceScreen prefSet = getPreferenceScreen();
        String imei = null;
        for (int slot = 0; slot < PhoneConstants.MAX_PHONE_COUNT_DUAL_SIM; slot++) {
            if (slot < mNumPhone) {
                Phone phone = PhoneFactory.getPhone(slot);
                if (phone != null) {
                    imei = phone.getImei();
                    Log.d(TAG, "phone getImei on slot " + slot + ": " + imei);
                }

                switch (slot) {
                    case PhoneConstants.SUB1:
                        setSummaryText(KEY_IMEI_NUMBER_1, imei);
                        break;
                    case PhoneConstants.SUB2:
                        setSummaryText(KEY_IMEI_NUMBER_2, imei);
                        break;
                }
            } else {
                removePreference(KEY_IMEI_NUMBER_2, prefSet);
            }
        }
    }

    private void updateImei() {
        final PreferenceScreen prefSet = getPreferenceScreen();

        for (int slot = 0; slot < PhoneConstants.MAX_PHONE_COUNT_DUAL_SIM; slot++) {
            if (slot < mNumPhone) {
                String imei = null;
                QtiImeiInfo[] qtiImeiInfo = null;
                qtiImeiInfo = mExtTelephonyManager.getImeiInfo();

                if (qtiImeiInfo != null) {
                    for (int i = 0; i < qtiImeiInfo.length; i++) {
                        if (null != qtiImeiInfo[i] && qtiImeiInfo[i].getSlotId() == slot) {
                            imei = qtiImeiInfo[i].getImei();
                            Log.d(TAG, "getImei: " + imei + " on slot" + slot);
                        }
                    }
                }

                if (TextUtils.isEmpty(imei)) {
                    Phone phone = PhoneFactory.getPhone(slot);
                    if (phone != null) {
                        imei = phone.getImei();
                        Log.d(TAG, "phone getImei on slot " + slot + ": " + imei);
                    }
                }

                switch (slot) {
                    case PhoneConstants.SUB1:
                        setSummaryText(KEY_IMEI_NUMBER_1, imei);
                        break;
                    case PhoneConstants.SUB2:
                        setSummaryText(KEY_IMEI_NUMBER_2, imei);
                        break;
                }
            } else {
                removePreference(KEY_IMEI_NUMBER_2, prefSet);
            }
        }
    }

    private void setPreferenceSummary() {

        final PreferenceScreen prefSet = getPreferenceScreen();
        // get hardware
        String hwVersion = null;
        try {
            hwVersion = getDeviceModel();
        } catch (Exception e) {
            hwVersion = mUnknown;
        }
        setSummaryText(KEY_HW_VERSION, hwVersion);

        // get android version, get property of ro.build.version.release_or_codename.
        setSummaryText(KEY_ANDROID_VERSION, Build.VERSION.RELEASE_OR_CODENAME);

        // get my phone number
        if (mPhone != null) {
            // get baseband version
            String basebandVersion =
                    mHandler.getBasebandVersionForPhone(mPhone.getPhoneId(), mUnknown);
            setSummaryText(KEY_BASEBAND_VERSION, basebandVersion);

            updateMeidEsn();

            updatePhoneNumber(mPhone.getPhoneId());

            // get PRL version
            mHandler.getPrlVersion();

            // get IMSI
            String imsi = mPhone.getSubscriberId();
            if (imsi == null || imsi.length() == 0) {
                IccCard card = mPhone.getIccCard();
                if (card != null) {
                    IccRecords record = card.getIccRecords();
                    if (record != null) {
                        imsi = record.getIMSI();
                    }
                }
            }
            setSummaryText(KEY_IMSI, imsi);

            // get UIM ID
            mHandler.GetRuimId();

            // get EPrl number
            setSummaryText(KEY_EPRL_NUMBER, mPhone.getCdmaPrlVersion());

            // get Mspl number
            mHandler.getMsplVersion();

            // get Mlpl number
            mHandler.getMlplVersion();

            // get NID and SID
            CellLocation cellLocation = mPhone.getCurrentCellIdentity().asCellLocation();
            if (cellLocation != null && cellLocation instanceof CdmaCellLocation) {
                CdmaCellLocation cdmaCellLoc = (CdmaCellLocation) cellLocation;
                setSummaryText(KEY_SID_NUMBER, cdmaCellLoc.getSystemId() + "");
                setSummaryText(KEY_NID_NUMBER, cdmaCellLoc.getNetworkId() + "");
            }

            // get iccid
            setSummaryText(KEY_ICCID_NUMBER, mPhone.getFullIccSerialNumber());
        }
    }

    private void setSummaryText(String preferenceKey, String value) {
        Preference preference = findPreference(preferenceKey);
        if (preference == null) {
            return;
        }

        if (TextUtils.isEmpty(value)) {
            preference.setSummary(mUnknown);
        } else {
            preference.setSummary(value);
        }
    }

    private void removePreference(String preferenceKey, PreferenceScreen prefSet) {
        Preference preference = findPreference(preferenceKey);
        if (preference == null) {
            return;
        }

        prefSet.removePreference(preference);
    }

    private void updateMeidEsn() {
        String meid = mSharedPreferences.getString(SP_MEID, "");
        String esn = mSharedPreferences.getString(SP_ESN, "");
        if (invalidMeid(meid) || invalidMeid(esn)) {
            mHandler.getDeviceIdentity();
        } else {
            setSummaryText(KEY_MEID_ESN, meid + "-" + esn);
        }
    }

    private void updatePhoneNumber(int simSlot) {
        final SubscriptionInfo subscriptionInfo = Utils.getSubscriptionInfo(mContext, simSlot);
        if (subscriptionInfo == null) {
            setSummaryText(KEY_TELEPHONE_NUMBER, mUnknown);
            return;
        }

        String phoneNumber = Utils.getBidiFormattedPhoneNumber(mContext, subscriptionInfo);
        setSummaryText(KEY_TELEPHONE_NUMBER, phoneNumber);
    }

    private boolean invalidMeid(String meid) {
        return TextUtils.isEmpty(meid) || meid.equals("0") || meid.startsWith("00000000");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mExtTelephonyManager != null && mServiceCallback != null) {
            mExtTelephonyManager.disconnectService(mServiceCallback);
        }
        mMetaInfoFetcherProxy.stop();
    }

    // running in work thread
    private void onMetaInfoLoaded(String swVer, String metaVer) {
        runOnUiThread(
                () -> {
                    setSummaryText(KEY_SOFTWARE_VERSION, swVer);
                    setSummaryText(KEY_DEVICE_META, metaVer);
                });
    }

    private abstract static class TextFilePraserBase
            implements CompletionHandler<Integer, ByteBuffer> {
        private AsynchronousFileChannel mAsynchronousFileChannel;
        private ByteBuffer mBuffer;
        private boolean mIsStarted = false;

        public TextFilePraserBase() {
            Path path = Paths.get(getFileName());
            try {
                mAsynchronousFileChannel =
                        AsynchronousFileChannel.open(path, StandardOpenOption.READ);
                mBuffer = ByteBuffer.allocate((int) mAsynchronousFileChannel.size());
            } catch (IOException e) {
                Rlog.e(TAG, " failed to open " + getFileName());
            }
        }

        protected abstract String getFileName();

        protected abstract void onCompleted(String ret);

        public void start() {
            if (mAsynchronousFileChannel != null && !mIsStarted) {
                mIsStarted = true;
                mAsynchronousFileChannel.read(mBuffer, 0, mBuffer, this);
            }
        }

        public void stop() {
            if (mIsStarted) {
                mIsStarted = false;
                close();
            }
        }

        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            close();
            attachment.flip();
            try {
                CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
                CharBuffer charBuffer = decoder.decode(attachment);
                onCompleted(charBuffer.toString());
            } catch (CharacterCodingException e) {
                Rlog.e(TAG, " decoded with failure");
            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            close();
            onCompleted(null);
        }

        private void close() {
            try {
                if (mAsynchronousFileChannel != null) {
                    mAsynchronousFileChannel.close();
                }
            } catch (IOException e) {
                Rlog.e(TAG, " failed to close");
            }
        }
    }

    /*
     * Parse version info based on json format below
     * {
     *   "Image_Build_IDs": {
     *     "apps": "LA.UM.9.12.r1-06402-SMxx50.1-6",
     *     "qssi": "LA.QSSI.11.0.r1-03602-qssi.1-2",
     *      ....
     *    },
     *    "Metabuild_Info": {
     *       "Meta_Build_ID": "SM8250_SDX55.LA_TN.2-0_2-0-00106-STD_LP5.INT-1",
     *        ...
     *     },
     *    "Version": "1.0"
     * }
     */
    private static final class MetaInfoFetcherProxy extends TextFilePraserBase {
        private static final String FILENAME_META_VERSION_INFO =
                "/data/vendor/modem_config/ver_info.txt";
        private static final String KEY_META_BUILD_INFO = "Metabuild_Info";
        private static final String KEY_META_BUILD_ID = "Meta_Build_ID";

        private DeviceInfoActivity mListener;

        public MetaInfoFetcherProxy(DeviceInfoActivity deviceInfoActivity) {
            super();
            mListener = deviceInfoActivity;
        }

        @Override
        protected String getFileName() {
            return FILENAME_META_VERSION_INFO;
        }

        @Override
        protected void onCompleted(String ret) {
            String metaId = null;
            try {
                if (TextUtils.isEmpty(ret)) {
                    Rlog.d(TAG, "result is null or empty");
                    return;
                }

                final JSONObject jsonVerInfo = new JSONObject(ret);
                metaId =
                        jsonVerInfo.getJSONObject(KEY_META_BUILD_INFO).getString(KEY_META_BUILD_ID);
            } catch (JSONException e) {
                Rlog.e(TAG, "parsing failed as json format");
                metaId = ret;
            } finally {
                String swVer = SystemProperties.get("ro.software.version", metaId);
                mListener.onMetaInfoLoaded(swVer, metaId);
            }
        }
    }

    private class ObtainIdHandler extends Handler {
        private static final int MESSAGE_GET_EF_RUIM = 1;
        private static final int MESSAGE_GET_EF_PRL = 2;
        private static final int MESSAGE_GET_EF_MSPL = 3;
        private static final int MESSAGE_GET_EF_MLPL = 4;
        private static final int MESSAGE_GET_DEVICE_IDENTITY = 5;
        // RUIM ID is 8 bytes data
        private static final int NUM_BYTES_RUIM_ID = 8;
        // True PRL ID is x bytes data
        private static final int NUM_BYTES_PRL_ID = 4;
        // MSPL ID is x bytes data
        private static final int NUM_BYTES_MSPL_ID = 5;
        // MLPL ID is 8 bytes data
        private static final int NUM_BYTES_MLPL_ID = 5;

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_GET_EF_RUIM:
                    handleGetEFRuim(msg);
                    break;
                case MESSAGE_GET_EF_PRL:
                    handleGetEFPrl(msg);
                    break;
                case MESSAGE_GET_EF_MSPL:
                    handleGetEFMspl(msg);
                    break;
                case MESSAGE_GET_EF_MLPL:
                    handleGetEFMlpl(msg);
                    break;
                case MESSAGE_GET_DEVICE_IDENTITY:
                    handleGetDeviceIdentity(msg);
                    break;
            }
        }

        private void handleGetEFRuim(Message msg) {
            AsyncResult ar = (AsyncResult) msg.obj;
            byte[] data = (byte[]) ar.result;
            if (ar.exception == null) {
                // for RUIM ID data, the first byte represent the num bytes of valid data. From
                // the second byte to num+1 byte, it is valid RUIM ID data. And the second
                // byte is the lowest-order byte, the num+1 byte is highest-order
                int numOfBytes = data[0];
                if (numOfBytes < NUM_BYTES_RUIM_ID) {
                    byte[] decodeData = new byte[numOfBytes];
                    for (int i = 0; i < numOfBytes; i++) {
                        decodeData[i] = data[numOfBytes - i];
                    }
                    String ruimid = IccUtils.bytesToHexString(decodeData);
                    setSummaryText(KEY_UIM_ID, ruimid);
                }
            }
        }

        private void handleGetEFPrl(Message msg) {
            AsyncResult ar = (AsyncResult) msg.obj;
            byte[] data = (byte[]) ar.result;
            if (ar.exception == null) {
                if (data.length > NUM_BYTES_PRL_ID - 1) {
                    int prlId = ((data[2] & 0xFF) << 8) | (data[3] & 0xFF);
                    setSummaryText(KEY_PRL_VERSION, String.valueOf(prlId));
                }
            }
        }

        private void handleGetEFMspl(Message msg) {
            AsyncResult ar = (AsyncResult) msg.obj;
            byte[] data = (byte[]) ar.result;
            if (ar.exception == null) {
                if (data.length > NUM_BYTES_MSPL_ID - 1) {
                    int msplId = ((data[3] & 0xFF) << 8) | (data[4] & 0xFF);
                    setSummaryText(KEY_MSPL_NUMBER, String.valueOf(msplId));
                }
            }
        }

        private void handleGetEFMlpl(Message msg) {
            AsyncResult ar = (AsyncResult) msg.obj;
            byte[] data = (byte[]) ar.result;
            if (ar.exception == null) {
                if (data.length > NUM_BYTES_MLPL_ID - 1) {
                    int mlplId = ((data[3] & 0xFF) << 8) | (data[4] & 0xFF);
                    setSummaryText(KEY_MLPL_NUMBER, String.valueOf(mlplId));
                }
            }
        }

        private void handleGetDeviceIdentity(Message msg) {
            AsyncResult ar = (AsyncResult) msg.obj;
            if (ar.exception == null) {
                String[] respId = (String[]) ar.result;
                Log.d(TAG, "respId: " + respId);
                setSummaryText(KEY_MEID_ESN, respId[3] + "-" + respId[2]);
                // Need clear calling identity due to run in phone wiht system uid
                final long token = Binder.clearCallingIdentity();
                mSharedPreferences.edit().putString(SP_ESN, respId[2]).apply();
                mSharedPreferences.edit().putString(SP_MEID, respId[3]).apply();
            }
        }

        public void GetRuimId() {
            UiccController controller = UiccController.getInstance();
            if (controller != null && mPhone != null) {
                IccFileHandler fh =
                        controller.getIccFileHandler(
                                mPhone.getPhoneId(), UiccController.APP_FAM_3GPP2);
                if (fh != null) {
                    // fh.loadEFTransparent(IccConstants.EF_RUIM_ID, NUM_BYTES_RUIM_ID,
                    fh.loadEFTransparent(
                            EF_RUIM_ID,
                            NUM_BYTES_RUIM_ID,
                            mHandler.obtainMessage(ObtainIdHandler.MESSAGE_GET_EF_RUIM));
                }
            }
        }

        public void getPrlVersion() {
            UiccController controller = UiccController.getInstance();
            if (controller != null && mPhone != null) {
                IccFileHandler fh =
                        controller.getIccFileHandler(
                                mPhone.getPhoneId(), UiccController.APP_FAM_3GPP2);
                if (fh != null) {
                    // fh.loadEFTransparent(IccConstants.EF_CSIM_PRL, NUM_BYTES_PRL_ID,
                    fh.loadEFTransparent(
                            EF_CSIM_PRL,
                            NUM_BYTES_PRL_ID,
                            mHandler.obtainMessage(ObtainIdHandler.MESSAGE_GET_EF_PRL));
                }
            }
        }

        public void getMsplVersion() {
            UiccController controller = UiccController.getInstance();
            if (controller != null && mPhone != null) {
                IccFileHandler fh =
                        controller.getIccFileHandler(
                                mPhone.getPhoneId(), UiccController.APP_FAM_3GPP2);
                if (fh != null) {
                    // fh.loadEFTransparent(IccConstants.EF_CSIM_MSPL, NUM_BYTES_MSPL_ID,
                    fh.loadEFTransparent(
                            EF_CSIM_MSPL,
                            NUM_BYTES_MSPL_ID,
                            mHandler.obtainMessage(ObtainIdHandler.MESSAGE_GET_EF_MSPL));
                }
            }
        }

        public void getMlplVersion() {
            UiccController controller = UiccController.getInstance();
            if (controller != null && mPhone != null) {
                IccFileHandler fh =
                        controller.getIccFileHandler(
                                mPhone.getPhoneId(), UiccController.APP_FAM_3GPP2);
                if (fh != null) {
                    // fh.loadEFTransparent(IccConstants.EF_CSIM_MLPL, NUM_BYTES_MLPL_ID,
                    fh.loadEFTransparent(
                            EF_CSIM_MLPL,
                            NUM_BYTES_MLPL_ID,
                            mHandler.obtainMessage(ObtainIdHandler.MESSAGE_GET_EF_MLPL));
                }
            }
        }

        public String getBasebandVersionForPhone(int phoneId, String defaultVal) {
            if (SubscriptionManager.isValidPhoneId(phoneId)) {
                return TelephonyManager.getDefault().getBasebandVersionForPhone(phoneId);
            }
            return defaultVal;
        }

        public void getDeviceIdentity() {
            Phone phone = PhoneFactory.getPhone(getPrimaryStackPhoneId());
            if (phone != null) {
                phone.mCi.getDeviceIdentity(obtainMessage(MESSAGE_GET_DEVICE_IDENTITY));
            }
        }
    }
}
