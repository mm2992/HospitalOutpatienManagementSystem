package com.graduate.hospital.model;

import lombok.Data;


@Data
public class User extends DocAndUser{
    private String userId;

    private String userName;

    private String userSex;

    private Integer userCategory;

    private String userTel;

    private String entryTime;

    private String password;

}