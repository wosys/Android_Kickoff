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

package lib.wintmain.xplayer.utils

import android.content.Context
import android.text.TextUtils
import android.util.Base64
import lib.wintmain.xplayer.BaseApp
import lib.wintmain.xplayer.BaseApp.Companion.getContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

/**
 * SharePreference封装
 */
object PrefUtils {

    private const val PREF_NAME = "config"

    fun getBoolean(
        key: String,
        defaultValue: Boolean
    ): Boolean {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.getBoolean(key, defaultValue)
    }

    fun setBoolean(ctx: Context, key: String, value: Boolean) {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putBoolean(key, value).apply()
    }

    fun getString(ctx: Context, key: String, defaultValue: String): String? {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.getString(key, defaultValue)
    }

    fun getString(key: String, defaultValue: String): String? {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.getString(key, defaultValue)
    }

    fun setString(ctx: Context, key: String, value: String) {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putString(key, value).apply()
    }

    fun setInt(ctx: Context, key: String, value: Int) {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putInt(key, value).apply()
    }

    fun getInt(ctx: Context, key: String, defaultValue: Int): Int {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.getInt(key, defaultValue)
    }

    fun setLong(ctx: Context, key: String, value: Long) {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putLong(key, value).apply()
    }

    fun getLong(ctx: Context, key: String, defaultValue: Long): Long {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.getLong(key, defaultValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putBoolean(key, value).apply()
    }

    fun getString(key: String): String? {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        var result: String? = null
        result = try {
            sp.getString(key, "")
        } catch (e: ClassCastException) {
            sp.getInt(key, -1).toString()
        }

        return result
    }

    fun setString(key: String, value: String) {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putString(key, value).apply()
    }

    fun setInt(key: String, value: Int) {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.getInt(key, defaultValue)
    }

    fun setLong(key: String, value: Long) {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putLong(key, value).apply()
    }

    fun getLong(key: String, defaultValue: Long): Long {
        val sp = BaseApp.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sp.getLong(key, defaultValue)
    }

    fun setHashSet(key: String, value: HashSet<String>) {
        val sp = BaseApp.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sp.edit().putStringSet(key, value).apply()
    }

    fun getHashSet(key: String): MutableSet<String>? {
        val sp = BaseApp.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sp.getStringSet(key, null)
    }

    fun removeKey(key: String): Boolean {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.edit().remove(key).commit()
    }

    fun setObject(key: String, value: Any?) {
        if (value == null) {
            return
        }
        if (value !is Serializable) {
            return
        }
        var baos: ByteArrayOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            val sp = getContext().getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
            baos = ByteArrayOutputStream()
            oos = ObjectOutputStream(baos)
            oos.writeObject(value)
            val temp = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
            sp.edit().putString(key, temp).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (oos != null && baos != null) {
                CloseUtils.closeIO(oos, baos)
            }
        }
    }

    fun getObject(key: String): Any? {
        var `object`: Any? = null
        var bais: ByteArrayInputStream? = null
        var ois: ObjectInputStream? = null

        val sp = getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        val temp = sp.getString(key, "")
        if (!TextUtils.isEmpty(temp)) {
            try {
                bais = ByteArrayInputStream(Base64.decode(temp!!.toByteArray(), Base64.DEFAULT))
                ois = ObjectInputStream(bais)
                `object` = ois.readObject()
            } catch (ignored: Exception) {

            } finally {
                if (ois != null && bais != null) {
                    CloseUtils.closeIO(ois, bais)
                }
            }
        }
        return `object`
    }
}
