package com.endergeek.rookie.askillfuldemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangsenhui on 11/10/16.
 * 将一个完整的对象进行分解，分解后的每一部分都是Intent所支持的数据类型，效率较高
 */
public class PersonParcelable implements Parcelable{

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static final Creator<PersonParcelable> CREATOR = new Creator<PersonParcelable>() {
        @Override
        public PersonParcelable createFromParcel(Parcel in) {
            PersonParcelable person = new PersonParcelable();
            /**
             * 注意此处读顺序需要与写顺序完全相同
             */
            person.name = in.readString();  // 读取name
            person.age = in.readInt();      // 读取age
            return person;
        }

        @Override
        public PersonParcelable[] newArray(int size) {
            return new PersonParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeString(name);   // 写出name
        parcel.writeInt(age);       // 写出age
    }
}
