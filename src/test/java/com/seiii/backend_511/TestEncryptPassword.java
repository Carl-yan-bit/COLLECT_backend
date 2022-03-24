package com.seiii.backend_511;

import com.seiii.backend_511.util.Encryption;
import org.junit.Test;

public class TestEncryptPassword {
    @Test
    public void testEncryptPassword(){
        System.out.println(Encryption.encryptPassword("123456","employer1"));

    }
}
