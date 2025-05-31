// INewPeopleListener.aidl
package com.wintmain.wBasis.aidl;

import com.wintmain.wBasis.aidl.People;

// AIDL中无法使用普通接口，所以创建此文件

interface INewPeopleListener {
    void onNewPeople(in People newPeople);
}