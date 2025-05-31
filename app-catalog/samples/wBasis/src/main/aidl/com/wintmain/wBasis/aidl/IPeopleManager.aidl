// IPeopleManager.aidl
// AIDL接口中只支持方法，不支持声明静态常量
package com.wintmain.wBasis.aidl;

// 自定义的Parcelable对象和AIDL对象不管是否和当前的AIDL文件位于同一个包内，也必须要显式import进来

import com.wintmain.wBasis.aidl.Book;
import com.wintmain.wBasis.aidl.People;
import com.wintmain.wBasis.aidl.INewPeopleListener;

interface IPeopleManager {
    List<People> getPeopleList();
    // addPeople方法的参数中有in关键字，因为aidl中除了基本数据类型，其它类型的参数必须标上方向：in、out或者inout，
    // 我们需根据实现需要去指定参数类型，因为这在底层实现是有开销的。
    void addPeople(in People people);

    void addPeopleAndBook(in People people, in Book book);

    // 扩展新增代码：新增接口
    void registerListener(INewPeopleListener listener);
    void unregisterListener(INewPeopleListener listener);
}
