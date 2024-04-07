package com.example.da1;

public class UserData {
    private static UserData instance;
    private String username;

    private UserData() {
        // Constructor riêng để ngăn việc khởi tạo từ bên ngoài
    }

    // Phương thức getInstance() trả về instance của lớp UserData
    public static synchronized UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    // Phương thức getter cho username
    public String getUsername() {
        return username;
    }

    // Phương thức setter cho username
    public void setUsername(String username) {
        this.username = username;
    }
}
