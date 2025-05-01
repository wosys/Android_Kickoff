// INewPeopleListener.aidl
package com.wintmain.foundation.aidl;

import com.wintmain.foundation.aidl.People;

// AIDL中无法使用普通接口，所以创建此文件

interface INewPeopleListener {
    void onNewPeople(in People newPeople);
}