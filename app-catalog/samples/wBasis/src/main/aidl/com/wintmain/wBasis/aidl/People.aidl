// People.aidl
package com.wintmain.wBasis.aidl;

// Declare any non-default types here with import statements

// 在Android的AIDL中，仅支持的数据类型有：
// 1. 基本数据类型（int、long、char、boolean、double等）
// 2. String和CharSequence
// 3. List：只支持ArrayList，里面的每个元素都必须能够被AIDL支持
// 4. Map：只支持HashMap，里面的每个元素都必须能够被AIDL支持
// 5. Parcelable：所有实现了Parcelable接口的对象
// 6. AIDL：所有的AIDL接口本身也可以在AIDL文件中使用

// Parcelable的作用是序列化对象，因为跨进程通信传输对象必须是以序列化和反序列化的方式进行。
// Parcelable的实现有些繁琐，但性能效率很高。

import com.wintmain.wBasis.aidl.People;

// People should be declared in a file called com/wintmain/wBasis/aidl/People.aidl
parcelable People;
