package com.example.da1;

import android.widget.TextView;

public class Chuyendoi {
    public static int chuyenInt(String input) {
        String soString = input.replaceAll("[^0-9]", ""); // Loại bỏ tất cả ký tự không phải là số
        soString = soString.toString();
        return Integer.parseInt(soString);
    }
    public  static  String chuyenString(TextView a){
        String b = a.getText().toString();
        return b;
    }
}
