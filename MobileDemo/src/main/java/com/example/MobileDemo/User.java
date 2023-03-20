package com.example.MobileDemo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User{


    @Id
    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_table")
    private String table;


    public void setUser(String password, String email, String table){
        this.email = email;
        this.password = password;
        this.table = table;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public String getTable() {
        return table;
    }


}
