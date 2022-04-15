package com.wang.graduationproject.Utils;

import com.antherd.smcrypto.sm4.Sm4;


public class SM4Utils {
    private static String SM4Key="1151263480abcdeffedcba9876543210"; // 16 进制字符串，要求为 128 比特

    public static String SM4Encrypt(String msg){
        return Sm4.encrypt(msg,SM4Key);
    }

    public static String SM4Decrypt(String msg){
        return Sm4.decrypt(msg,SM4Key);
    }

    public static void main(String[] args) {
        System.out.println(SM4Encrypt("e10adc3949ba59abbe56e057f20f883e"));
        System.out.println(SM4Decrypt("df8a80d0d58b29ee7f79d6b12c6a992d7c8614d0cac0ab37a629d4ca18d1f7f418a8dd39b6ca3436ba6d0e441b9917fdc6d280cbaf07ec45f129930c23649c33d32073c14ab36b82961bef46d72e8be8466f08e191153798ac4c1949b240d085d0c7310a24d0e1836bf376c9e48817e4807b5ba8e6a75e2a340fae7cfc99d747"));
    }
}
