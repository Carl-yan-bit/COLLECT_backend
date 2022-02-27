package com.seiii.backend_511.util;

import org.springframework.util.DigestUtils;

public class Encryption {
    public static String encryptPassword(String pwd,String salt){
        return DigestUtils.md5DigestAsHex((salt+pwd).getBytes());
    }
}
