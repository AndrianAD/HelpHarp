package com.example.user.helpharp;

public enum HoleEnum {

    G("1"), Ab("-1'"), A("-1"), Bb("1*"), B("2"), C("-2''");


    private String tab;

    public String getCode() {
        return tab;
    }


    HoleEnum(String code) {
        this.tab = code;


    }
}
