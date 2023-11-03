package com.ning;

import java.util.Random;

public class CodeUtils {
    /**
     * 生成六位数随机验证码
     * */
    public static String getCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
}
