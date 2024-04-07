package com.example.da1;

public class Orderitem {
    String idDonHang,idFood;
    int soLuong;

    public Orderitem(String idDonHang, String idFood, int soLuong) {
        this.idDonHang = idDonHang;
        this.idFood = idFood;
        this.soLuong = soLuong;
    }

    public Orderitem() {
    }

    public String getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(String idDonHang) {
        this.idDonHang = idDonHang;
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
