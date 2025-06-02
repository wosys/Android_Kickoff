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

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

android {
    namespace = "com.wintmain.wNet"
    compileSdk = 35

    defaultConfig {
        // sub模块只需要指定最小的sdk即可
        minSdk = 26
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.casa.base)
    ksp(libs.casa.processor)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.androidx.coreExtkt)
    implementation(libs.compose.foundation.foundation)
    implementation(libs.compose.ui.ui)
    implementation(libs.compose.material.material3)

    // [customize] - 将common的依赖放到这里
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Add from here
    implementation(libs.androidx.coreExtkt)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.recyclerview)
    implementation(libs.material)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // ------------------------------网络请求-------------------------------------
    implementation(project(":app-catalog:samples:wNet:libwNet"))
    implementation(libs.glide)
    // ------------------------------JSON解析-------------------------------------
    implementation(libs.kotlinx.serialization.json) // JSON序列化库, 首选推荐使用
    implementation(libs.kotlinx.serialization.protobuf) // protobuf序列化
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0") // JSON序列化库, 强校验, JSON字段缺失会导致解析异常, 故不推荐
    implementation(libs.kotlin.reflect.v2021)
    implementation(libs.gson) // JSON序列化库, 会导致kotlin默认值无效, 故不推荐
    implementation("com.alibaba:fastjson:1.2.73") // JSON序列化库, 会导致kotlin默认值无效(除非引入kt-reflect), 不推荐
    // ------------------------------其他库-------------------------------------
    implementation("com.github.liangjingkanji:StatusBar:2.0.2") // 透明状态栏
    implementation("com.github.liangjingkanji:debugkit:1.3.0") // 开发调试窗口工具
    implementation("com.github.liangjingkanji:Tooltip:1.2.2") // 吐司工具
    implementation("com.github.liangjingkanji:Engine:0.0.74")
    implementation("com.github.liangjingkanji:Serialize:3.0.1")
    implementation("com.github.liangjingkanji:BRV:1.5.2") // 提供自动分页/缺省页/自动下拉刷新功能
    implementation("com.github.chuckerteam.chucker:library:3.5.2") // 通知栏监听网络日志
    implementation("com.squareup.okhttp3:mockwebserver:4.10.0")
    //debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")
}