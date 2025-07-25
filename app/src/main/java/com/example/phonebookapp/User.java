package com.example.phonebookapp;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.google.firebase.database.PropertyName;

public class User extends BaseObservable {

    private String username;
    private String phoneNumber;
    private String groupUser;



    public User(String username, String phonenumber, String groupUser) {
        this.username = username;
        this.phoneNumber = phonenumber;
        this.groupUser = groupUser;
    }


    @Bindable
    @PropertyName("username")
    public String getUsername() {
        return username;
    }

    @PropertyName("username")
    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    @PropertyName("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @PropertyName("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        notifyPropertyChanged(BR.phoneNumber);
    }

    @Bindable
    @PropertyName("groupUser")
    public String getGroupUser() {
        return groupUser;
    }

    @PropertyName("groupUser")
    public void setGroupUser(String groupUser) {
        this.groupUser = groupUser;
        notifyPropertyChanged(BR.groupUser);
    }
}