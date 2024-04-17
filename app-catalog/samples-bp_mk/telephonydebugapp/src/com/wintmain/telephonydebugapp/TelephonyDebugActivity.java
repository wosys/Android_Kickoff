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

package com.wintmain.telephonydebugapp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.MenuItem;

import com.wintmain.R;

public class TelephonyDebugActivity extends PreferenceActivity {
    public static class TelephonyDebugFragment extends PreferenceFragment
           implements Preference.OnPreferenceChangeListener {

        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getActivity() != null && getActivity().getActionBar() != null) {
                getActivity().getActionBar().setDisplayOptions(
                        ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
            }

            final Preference readConfigPreference;
            final Preference callScreeningPreference;

            // Set Preference
            addPreferencesFromResource(R.xml.debug_menu);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            return true;
        }

        @Override
        public boolean onPreferenceTreeClick(
                PreferenceScreen preferenceScreen, Preference preference) {
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }
    }

    @Override
    public Intent getIntent() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SHOW_FRAGMENT, TelephonyDebugFragment.class.getName());
        intent.putExtra(EXTRA_NO_HEADERS, true);
        return intent;
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return true;
    }

    @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
              case android.R.id.home:
                 finish();
                 return true;
              default:
                 break;
         }
         return super.onOptionsItemSelected(item);
     }
}
