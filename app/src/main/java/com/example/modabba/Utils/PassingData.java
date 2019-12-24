package com.example.modabba.Utils;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class PassingData implements Parcelable {


    private String password;
    private String name;
    private String email;
    private String phone;
    private String altphone;
    private Context context;

    public PassingData(Context context) {
        this.context = context;
    }

    protected PassingData(Parcel in) {
        password = in.readString();
        name = in.readString();
        email = in.readString();
        phone = in.readString();
        altphone=in.readString();
    }

    public static final Creator<PassingData> CREATOR = new Creator<PassingData>() {
        @Override
        public PassingData createFromParcel(Parcel in) {
            return new PassingData(in);
        }

        @Override
        public PassingData[] newArray(int size) {
            return new PassingData[size];
        }
    };

    public String getAltphone() { return altphone; }

    public void setAltphone(String altphone) {this.altphone = altphone; }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(password);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(altphone);
    }
}
