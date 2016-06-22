package com.admin.app0612;

/**
 * Created by Administrator on 2016/5/12.
 */
public class UserEntity{
    public String username;
    public String pin;

    public UserEntity(){};

    public UserEntity(String username, String password) {
        this.username = username;
        this.pin = password;
    }
    public String getusername() {
        return username;
    }
    public void setusername(String username) {
        this.username = username;
    }
    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", pin=" + pin + "]";
    }
}
