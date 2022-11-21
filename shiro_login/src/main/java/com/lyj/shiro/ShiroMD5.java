package com.lyj.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.sql.SQLOutput;

/**
 *  使用shiro的md5进行密码加密
 *
 */
public class ShiroMD5 {

    public static void main(String[] args) {
        // 密码
        String pass = "z3";
        // 1、MD5直接加密
        Md5Hash md5Hash1 = new Md5Hash(pass);
        System.out.println("md5Hash1 = " + md5Hash1);
        // 2、带盐的加密
        Md5Hash md5Hash2 = new Md5Hash(pass, "salt");
        System.out.println("md5Hash2 = " + md5Hash2);
        // 3、带盐多次加密
        Md5Hash md5Hash3 = new Md5Hash(pass, "salt", 3);
        System.out.println("md5Hash3 = " + md5Hash3);
        // 4、使用父类进行加密
        SimpleHash md5Hash4 = new SimpleHash("MD5", pass, "salt", 3);
        System.out.println("md5Hash4 = " + md5Hash4);
    }

    static {
        // 验证多次加密
        System.out.println("验证多次加密---------------------");
        String pass = "z3";

        // 2、带盐的加密
        Md5Hash md5Hash1 = new Md5Hash(pass, "salt");
        System.out.println("1111 = " + md5Hash1);
        // 2、带盐的加密
        Md5Hash md5Hash2 = new Md5Hash(md5Hash1);
        System.out.println("2222 = " + md5Hash2);

        // 带盐加密只会在第一次带盐，后面的加密都是对前一次加密结果直接进行加密
        Md5Hash md5Hash4 = new Md5Hash(pass, "salt", 2);
        System.out.println("4444 = " + md5Hash4);
    }
}
