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

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment

/**
 * 页面跳转传参 注解+反射获取页面入参
 */
object ParamUtil {
    /**
     * Fragment
     */
    fun initParam(fragment: Fragment) {
        val javaClass = fragment.javaClass
        fragment.arguments?.apply {
            setParam(fragment, this)
        }
    }

    /**
     * Activity
     */
    fun initParam(activity: Activity) {
        activity.intent.extras?.apply {
            setParam(activity, this)
        }
    }

    private fun setParam(obj: Any, intent: Bundle) {
        val javaClass = obj.javaClass
        val fields = javaClass.declaredFields
        for (item in fields) {
            if (item.isAnnotationPresent(Param::class.java)) {
                item.getAnnotation(Param::class.java)?.let {
                    val key: String = if (TextUtils.isEmpty(it.value)) item.name else it.value
                    if (intent.containsKey(key)) {
                        val type = item.type
                        when (type) {
                            Boolean::class.javaPrimitiveType -> {
                                intent.getBoolean(key, false)
                            }

                            Int::class.javaPrimitiveType -> {
                                intent.getInt(key, 0)
                            }

                            Long::class.javaPrimitiveType -> {
                                intent.getLong(key, 0L)
                            }

                            String::class.java -> {
                                intent.getString(key)
                            }

                            Double::class.javaPrimitiveType -> {
                                intent.getDouble(key, 0.0)
                            }

                            Byte::class.javaPrimitiveType -> {
                                intent.getByte(key, "".toByte())
                            }

                            Char::class.javaPrimitiveType -> {
                                intent.getChar(key, '\u0000')
                            }

                            Float::class.javaPrimitiveType -> {
                                intent.getFloat(key, 0f)
                            }

                            else -> {
                                intent.getSerializable(key)
                            }
                        }?.apply {
                            item.isAccessible = true
                            try {
                                item[obj] = this
                            } catch (e: IllegalAccessException) {
                                e.printStackTrace()
                            }
                            item.isAccessible = false
                        }
                    }
                }
            }
        }
    }
}