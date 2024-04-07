package com.example.da1;

public class UserApp {
    String userName,password,sdt;
    int lv,tuoi;

    public UserApp() {
    }

    public UserApp(String userName, String password, String sdt, int lv, int tuoi) {
        this.userName = userName;
        this.password = password;
        this.sdt = sdt;
        this.lv = lv;
        this.tuoi = tuoi;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }
}
