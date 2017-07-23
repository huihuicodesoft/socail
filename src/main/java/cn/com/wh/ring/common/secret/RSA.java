package cn.com.wh.ring.common.secret;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {
    private static final String KEY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCI4zvwncf23kH5jxfrCgcp4R2KMaBOns5RQyygxfIM/w6UEZANbOcc95GulQ2ZoU1AvGI5Zg3VSf736DMyJ5FzL0i2mXagjkbvc3Fk/YHE8pipdPkRd/9fYLYJJDOhAB5OIcVOvcHgt9hyluIXXu0swrLPMV42saTuL/OtMD7fhQIDAQAB";
    private static final String KEY_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIjjO/Cdx/beQfmPF+sKBynhHYoxoE6ezlFDLKDF8gz/DpQRkA1s5xz3ka6VDZmhTUC8YjlmDdVJ/vfoMzInkXMvSLaZdqCORu9zcWT9gcTymKl0+RF3/19gtgkkM6EAHk4hxU69weC32HKW4hde7SzCss8xXjaxpO4v860wPt+FAgMBAAECgYAv+1a7VNMO2YFOtLWxkWZJa7srAZIrfMbc/SrbgAcEPzMg+7b+vbmVEcJi66REwaGOm3ninL8kkDdrNl0bq3rz3ZfOxdxY9ro4g9lzZfjLseeYyoHAck6UOHe8f9Lejip4JspajpO+ix2zATqATQjxxz4YK7RF7mjsycv0FSm20QJBAMcGmFW/jCGDNceVaSoCkyLGnDJD3Z0rP+AhAEpU5+YKmNGM9WnzMzCLHHC2O3VK7zRPOtkU0V3Yo+xWkOCQjtsCQQCwEuh8vNfbO6SnKCwnKfJZnOMc4QhNqtFIMan0Id9hkMNAaYu7gpL5Uyb/6Ig/pszpGkIr1ugJ8sSjthjG/akfAkEArhltzqzFt1chB1sC6JXiAey9HBdT2DXKJZSYvW0ygoBImB53W9w7rj4yfcJblsobH6YjM1xLnY1yL76180thOwJAJSlYeDtDM7o63izUZM+tnsxGAupb3kiXMy2IOUaM5wKuswGTxFeivYyXRpvFZenGhcxM+wO4paDfb84XHKic8QJABhO0JPGWGkiCZ7t0TMGW8ZtySrOdjBWdmCZcAzYX4VgAwCuLmCp5nazR6NI0TgQRumG/zz86lad+wBY/zjrSCQ==";

    /**
     * 生成RAS公钥与私钥字符串
     *
     * @return
     */
    private static void getKeys() {
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        //得到公钥字符串
        String publicKey = Base64.encode(keyPair.getPublic().getEncoded());
        //得到私钥字符串
        String privateKey = Base64.encode(keyPair.getPrivate().getEncoded());
        System.out.println("公钥 = {" + publicKey + "}");
        System.out.println("私钥 = {" + privateKey + "}");
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥字符串
     * @return
     * @throws Exception
     */
    private static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从字符串中加载私钥
     *
     * @param privateKeyStr 私钥字符串
     * @return
     * @throws Exception
     */
    private static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 公钥加密过程
     *
     * @param publicKey     公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    private static String encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return Base64.encode(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 私钥加密过程
     *
     * @param privateKey    私钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    private static String encrypt(RSAPrivateKey privateKey, byte[] plainTextData) throws Exception {
        if (privateKey == null) {
            throw new Exception("加密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(plainTextData);
            return Base64.encode(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 私钥解密过程
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    private static String decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return new String(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 公钥解密过程
     *
     * @param publicKey  公钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    private static String decrypt(RSAPublicKey publicKey, byte[] cipherData) throws Exception {
        if (publicKey == null) {
            throw new Exception("解密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(cipherData);
            return new String(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    public static String decrypt(String content) {
        String result = null;
        try {
            result = decrypt(loadPrivateKey(KEY_PRIVATE), Base64.decode(content));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static void main(String[] args) {
        try {
            String var1 = encrypt(loadPublicKey(KEY_PUBLIC), "往回".getBytes());
            //String var2 = decrypt("aELv5IT81RbEJ0RP24+FgCQiBZtR43BkrjZiVTT3vAOzkUoMMuoApXA9uBGjziHbx9xFDhz4R3avHWhv3/cWLEF3Amw2AX3qzESlWWkpqYNvdyp2esbT1+TglIBINIdltKO54jyL/G3wiqZ4eDLQufsNvau824R+z5hFZsynHTM=");
            String var2 = decrypt(var1);
            System.out.println("公钥加密="+var1);
            System.out.println("私钥解密="+var2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
