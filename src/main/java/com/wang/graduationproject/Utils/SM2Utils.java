package com.wang.graduationproject.Utils;

import com.antherd.smcrypto.sm2.Keypair;
import com.antherd.smcrypto.sm2.Sm2;

public class SM2Utils {
    private static Keypair keypair = Sm2.generateKeyPairHex();
    private static String privateKey = keypair.getPrivateKey(); // 公钥
    private static String publicKey = keypair.getPublicKey(); // 私钥


    private static String encryptData = Sm2.doEncrypt("e10adc3949ba59abbe56e057f20f883e", publicKey); // 加密结果
    private static String decryptData = Sm2.doDecrypt(encryptData, privateKey); // 解密结果

    public static String getPublicKey(){
        return publicKey;
    }

    /**
     * SM2加密
     * */
    public static String encrypt(String encryptData){
        return Sm2.doEncrypt("e10adc3949ba59abbe56e057f20f883e", publicKey);
    }

    /**
     * SM2解密
     * */
    public static String decrypt(String encryptData){
        return Sm2.doDecrypt(encryptData, privateKey);
    }


}
