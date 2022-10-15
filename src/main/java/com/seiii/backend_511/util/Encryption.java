package com.seiii.backend_511.util;

import org.springframework.util.DigestUtils;

public class Encryption {
    public static void main(String[] args) {
        System.out.println(Encryption.encryptPassword("123456", "空白"));
    }
    public static String encryptPassword(String pwd,String salt){
        return DigestUtils.md5DigestAsHex((salt+pwd).getBytes());
    }
}
