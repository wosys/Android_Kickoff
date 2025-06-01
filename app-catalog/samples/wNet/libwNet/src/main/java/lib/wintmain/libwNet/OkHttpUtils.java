/*
 * Copyright 2023-2025 wintmain
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

package lib.wintmain.libwNet;

import kotlin.jvm.JvmStatic;
import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.internal.cache.DiskLruCache;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("KotlinInternalInJava")
public class OkHttpUtils {

    /**
     * 标签集合
     */
    @JvmStatic
    public static Map<Class<?>, Object> tags(Request.Builder builder) {
        return builder.getTags$okhttp();
    }

    /**
     * 通过反射返回Request的标签可变集合
     */
    @JvmStatic
    public static Map<Class<?>, Object> tags(Request request)
            throws NoSuchFieldException, IllegalAccessException {
        Map<Class<?>, Object> tagsOkhttp = request.getTags$okhttp();
        if (tagsOkhttp.isEmpty()) {
            Field tagsField = request.getClass().getDeclaredField("tags");
            tagsField.setAccessible(true);
            LinkedHashMap<Class<?>, Object> tags = new LinkedHashMap<>();
            tagsField.set(request, tags);
            return tags;
        }
        Field tagsField = tagsOkhttp.getClass().getDeclaredField("m");
        tagsField.setAccessible(true);
        Object tags = tagsField.get(tagsOkhttp);
        return (Map<Class<?>, Object>) tags;
    }

    /**
     * 全部的请求头
     */
    @JvmStatic
    public static Headers.Builder headers(Request.Builder builder) {
        return builder.getHeaders$okhttp();
    }

    @JvmStatic
    public static Headers.Builder addLenient(Headers.Builder builder, String line) {
        return builder.addLenient$okhttp(line);
    }

    @JvmStatic
    public static DiskLruCache diskLruCache(Cache cache) {
        return cache.getCache$okhttp();
    }
}
