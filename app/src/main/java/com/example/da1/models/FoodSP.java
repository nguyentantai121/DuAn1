package com.example.da1.models;

public class FoodSP {


    private int  gia,soluong;
    private String name;

    public FoodSP(String name, int gia, int soluong) {

        this.gia = gia;
        this.name = name;
        this.soluong = soluong;

    }

    public FoodSP() {
    }


    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class DonHangCloud {
        int ban,tongtien,trangThai;
        String idDongHang;
        String ngay,gio,ghiChu;

        public DonHangCloud(int ban, int tongtien, String idDongHang, String ngay, String gio, String ghiChu, int trangThai) {
            this.ban = ban;
            this.tongtien = tongtien;
            this.idDongHang = idDongHang;
            this.ngay = ngay;
            this.gio = gio;
            this.ghiChu = ghiChu;
            this.trangThai =trangThai;
        }

        public int getTrangThai() {
            return trangThai;
        }

        public void setTrangThai(int trangThai) {
            this.trangThai = trangThai;
        }

        public String getGhiChu() {
            return ghiChu;
        }

        public void setGhiChu(String ghiChu) {
            this.ghiChu = ghiChu;
        }

        public DonHangCloud() {
        }

        public int getBan() {
            return ban;
        }

        public void setBan(int ban) {
            this.ban = ban;
        }

        public int getTongtien() {
            return tongtien;
        }

        public void setTongtien(int tongtien) {
            this.tongtien = tongtien;
        }

        public String getIdDongHang() {
            return idDongHang;
        }

        public void setIdDongHang(String idDongHang) {
            this.idDongHang = idDongHang;
        }

        public String getNgay() {
            return ngay;
        }

        public void setNgay(String ngay) {
            this.ngay = ngay;
        }

        public String getGio() {
            return gio;
        }

        public void setGio(String gio) {
            this.gio = gio;
        }
    }

    public static class FoodCloud {
        String imageURL,nameFood,idFood;

        int LoaiFood,giaFood;

        public FoodCloud(String imageURL,String idFood, String nameFood, int giaFood, int loaiFood) {
            this.imageURL = imageURL;
            this.nameFood = nameFood;
            this.idFood = idFood;
            this.giaFood = giaFood;
            LoaiFood = loaiFood;
        }

        public FoodCloud() {
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

        public String getNameFood() {
            return nameFood;
        }

        public void setNameFood(String nameFood) {
            this.nameFood = nameFood;
        }

        public String getIdFood() {
            return idFood;
        }

        public void setIdFood(String idFood) {
            this.idFood = idFood;
        }

        public int getGiaFood() {
            return giaFood;
        }

        public void setGiaFood(int giaFood) {
            this.giaFood = giaFood;
        }

        public int getLoaiFood() {
            return LoaiFood;
        }

        public void setLoaiFood(int loaiFood) {
            LoaiFood = loaiFood;
        }
    }
}
