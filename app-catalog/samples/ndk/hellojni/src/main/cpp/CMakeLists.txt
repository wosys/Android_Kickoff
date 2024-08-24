cmake_minimum_required(VERSION 3.22.1)

project("hello-jni")

add_library(hello-jni SHARED
        hello-jni.cpp)

target_link_libraries(hello-jni android log)
