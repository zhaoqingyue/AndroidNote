package com.study.commonlib.util.utilcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : 加密解密相关的工具类
 * </pre>
 */
public class EncryptUtils {

    private EncryptUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    // <editor-folder desc="哈希加密相关">
    // <editor-folder desc="MD2加密">
    /**
     * MD2加密
     *
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String encryptMD2ToString(String data) {
        return encryptMD2ToString(data.getBytes());
    }

    /**
     * MD2加密
     *
     * @param data 明文字节数组
     * @return 16进制密文
     */
    public static String encryptMD2ToString(byte[] data) {
        return ConvertUtils.bytes2HexString(encryptMD2(data));
    }

    /**
     * MD2加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptMD2(byte[] data) {
        return hashTemplate(data, "MD2");
    }
    // </editor-folder>

    // <editor-folder desc="MD5加密">
    /**
     * MD5加密
     *
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String encryptMD5ToString(String data) {
        return encryptMD5ToString(data.getBytes());
    }

    /**
     * MD5加密
     *
     * @param data 明文字符串
     * @param salt 盐
     * @return 16进制加盐密文
     */
    public static String encryptMD5ToString(String data, String salt) {
        return ConvertUtils.bytes2HexString(encryptMD5((data + salt).getBytes()));
    }

    /**
     * MD5加密
     *
     * @param data 明文字节数组
     * @return 16进制密文
     */
    public static String encryptMD5ToString(byte[] data) {
        return ConvertUtils.bytes2HexString(encryptMD5(data));
    }

    /**
     * MD5加密
     *
     * @param data 明文字节数组
     * @param salt 盐字节数组
     * @return 16进制加盐密文
     */
    public static String encryptMD5ToString(byte[] data, byte[] salt) {
        if (data == null || salt == null) return null;
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return ConvertUtils.bytes2HexString(encryptMD5(dataSalt));
    }

    /**
     * MD5加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptMD5(byte[] data) {
        return hashTemplate(data, "MD5");
    }

    /**
     * MD5加密文件
     *
     * @param filePath 文件路径
     * @return 文件的16进制密文
     */
    public static String encryptMD5File2String(String filePath) {
        File file = StringUtils.isSpace(filePath) ? null : new File(filePath);
        return encryptMD5File2String(file);
    }

    /**
     * MD5加密文件
     *
     * @param filePath 文件路径
     * @return 文件的MD5校验码
     */
    public static byte[] encryptMD5File(String filePath) {
        File file = StringUtils.isSpace(filePath) ? null : new File(filePath);
        return encryptMD5File(file);
    }

    /**
     * MD5加密文件
     *
     * @param file 文件
     * @return 文件的16进制密文
     */
    public static String encryptMD5File2String(File file) {
        return ConvertUtils.bytes2HexString(encryptMD5File(file));
    }

    /**
     * MD5加密文件
     *
     * @param file 文件
     * @return 文件的MD5校验码
     */
    public static byte[] encryptMD5File(File file) {
        if (file == null) return null;
        FileInputStream fis = null;
        DigestInputStream digestInputStream;
        try {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            digestInputStream = new DigestInputStream(fis, md);
            byte[] buffer = new byte[256 * 1024];
            while (digestInputStream.read(buffer) > 0) ;
            md = digestInputStream.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(fis);
        }
    }

    // </editor-folder>

    // <editor-folder desc="SHA1加密">
    /**
     * SHA1加密
     *
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String encryptSHA1ToString(String data) {
        return encryptSHA1ToString(data.getBytes());
    }

    /**
     * SHA1加密
     *
     * @param data 明文字节数组
     * @return 16进制密文
     */
    public static String encryptSHA1ToString(byte[] data) {
        return ConvertUtils.bytes2HexString(encryptSHA1(data));
    }

    /**
     * SHA1加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA1(byte[] data) {
        return hashTemplate(data, "SHA1");
    }

    // </editor-folder>

    //<editor-folder desc="SHA224加密">
    /**
     * SHA224加密
     *
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String encryptSHA224ToString(String data) {
        return encryptSHA224ToString(data.getBytes());
    }

    /**
     * SHA224加密
     *
     * @param data 明文字节数组
     * @return 16进制密文
     */
    public static String encryptSHA224ToString(byte[] data) {
        return ConvertUtils.bytes2HexString(encryptSHA224(data));
    }

    /**
     * SHA224加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA224(byte[] data) {
        return hashTemplate(data, "SHA224");
    }

    // </editor-folder>

    // <editor-folder desc="SHA256加密">

    /**
     * SHA256加密
     *
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String encryptSHA256ToString(String data) {
        return encryptSHA256ToString(data.getBytes());
    }

    /**
     * SHA256加密
     *
     * @param data 明文字节数组
     * @return 16进制密文
     */
    public static String encryptSHA256ToString(byte[] data) {
        return ConvertUtils.bytes2HexString(encryptSHA256(data));
    }

    /**
     * SHA256加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA256(byte[] data) {
        return hashTemplate(data, "SHA256");
    }

    // </editor-folder>

    // <editor-folder desc="SHA384加密">

    /**
     * SHA384加密
     *
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String encryptSHA384ToString(String data) {
        return encryptSHA384ToString(data.getBytes());
    }

    /**
     * SHA384加密
     *
     * @param data 明文字节数组
     * @return 16进制密文
     */
    public static String encryptSHA384ToString(byte[] data) {
        return ConvertUtils.bytes2HexString(encryptSHA384(data));
    }

    /**
     * SHA384加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA384(byte[] data) {
        return hashTemplate(data, "SHA384");
    }

    // </editor-folder>

    // <editor-folder desc="SHA512加密">

    /**
     * SHA512加密
     *
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String encryptSHA512ToString(String data) {
        return encryptSHA512ToString(data.getBytes());
    }

    /**
     * SHA512加密
     *
     * @param data 明文字节数组
     * @return 16进制密文
     */
    public static String encryptSHA512ToString(byte[] data) {
        return ConvertUtils.bytes2HexString(encryptSHA512(data));
    }

    /**
     * SHA512加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA512(byte[] data) {
        return hashTemplate(data, "SHA512");
    }

    // </editor-folder>

    /**
     * hash加密模板
     *
     * @param data      数据
     * @param algorithm 加密算法
     * @return 密文字节数组
     */
    private static byte[] hashTemplate(byte[] data, String algorithm) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // <editor-folder desc="HmacMD5加密">

    /**
     * HmacMD5加密
     *
     * @param data 明文字符串
     * @param key  秘钥
     * @return 16进制密文
     */
    public static String encryptHmacMD5ToString(String data, String key) {
        return encryptHmacMD5ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacMD5加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 16进制密文
     */
    public static String encryptHmacMD5ToString(byte[] data, byte[] key) {
        return ConvertUtils.bytes2HexString(encryptHmacMD5(data, key));
    }

    /**
     * HmacMD5加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 密文字节数组
     */
    public static byte[] encryptHmacMD5(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacMD5");
    }

    // </editor-folder>

    // <editor-folder desc="HmacSHA1加密">

    /**
     * HmacSHA1加密
     *
     * @param data 明文字符串
     * @param key  秘钥
     * @return 16进制密文
     */
    public static String encryptHmacSHA1ToString(String data, String key) {
        return encryptHmacSHA1ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA1加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 16进制密文
     */
    public static String encryptHmacSHA1ToString(byte[] data, byte[] key) {
        return ConvertUtils.bytes2HexString(encryptHmacSHA1(data, key));
    }

    /**
     * HmacSHA1加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 密文字节数组
     */
    public static byte[] encryptHmacSHA1(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacSHA1");
    }

    // </editor-folder>

    // <editor-folder desc="HmacSHA224加密">

    /**
     * HmacSHA224加密
     *
     * @param data 明文字符串
     * @param key  秘钥
     * @return 16进制密文
     */
    public static String encryptHmacSHA224ToString(String data, String key) {
        return encryptHmacSHA224ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA224加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 16进制密文
     */
    public static String encryptHmacSHA224ToString(byte[] data, byte[] key) {
        return ConvertUtils.bytes2HexString(encryptHmacSHA224(data, key));
    }

    /**
     * HmacSHA224加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 密文字节数组
     */
    public static byte[] encryptHmacSHA224(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacSHA224");
    }

    // </editor-folder>

    // <editor-folder desc="HmacSHA256加密">

    /**
     * HmacSHA256加密
     *
     * @param data 明文字符串
     * @param key  秘钥
     * @return 16进制密文
     */
    public static String encryptHmacSHA256ToString(String data, String key) {
        return encryptHmacSHA256ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA256加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 16进制密文
     */
    public static String encryptHmacSHA256ToString(byte[] data, byte[] key) {
        return ConvertUtils.bytes2HexString(encryptHmacSHA256(data, key));
    }

    /**
     * HmacSHA256加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 密文字节数组
     */
    public static byte[] encryptHmacSHA256(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacSHA256");
    }

    // </editor-folder>

    // <editor-folder desc="HmacSHA384加密">

    /**
     * HmacSHA384加密
     *
     * @param data 明文字符串
     * @param key  秘钥
     * @return 16进制密文
     */
    public static String encryptHmacSHA384ToString(String data, String key) {
        return encryptHmacSHA384ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA384加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 16进制密文
     */
    public static String encryptHmacSHA384ToString(byte[] data, byte[] key) {
        return ConvertUtils.bytes2HexString(encryptHmacSHA384(data, key));
    }

    /**
     * HmacSHA384加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 密文字节数组
     */
    public static byte[] encryptHmacSHA384(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacSHA384");
    }

    // </editor-folder>

    // <editor-folder desc="HmacSHA512加密">

    /**
     * HmacSHA512加密
     *
     * @param data 明文字符串
     * @param key  秘钥
     * @return 16进制密文
     */
    public static String encryptHmacSHA512ToString(String data, String key) {
        return encryptHmacSHA512ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA512加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 16进制密文
     */
    public static String encryptHmacSHA512ToString(byte[] data, byte[] key) {
        return ConvertUtils.bytes2HexString(encryptHmacSHA512(data, key));
    }

    /**
     * HmacSHA512加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 密文字节数组
     */
    public static byte[] encryptHmacSHA512(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacSHA512");
    }

    // </editor-folder>

    /**
     * Hmac加密模板
     *
     * @param data      数据
     * @param key       秘钥
     * @param algorithm 加密算法
     * @return 密文字节数组
     */
    private static byte[] hmacTemplate(byte[] data, byte[] key, String algorithm) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    // </editor-folder>

    // <editor-folder desc="DES加解密相关">
    /**
     * DES转变
     * <p>法算法名称/加密模式/填充方式</p>
     * <p>加密模式有：电子密码本模式ECB、加密块链模式CBC、加密反馈模式CFB、输出反馈模式OFB</p>
     * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
     */
    public static        String DES_Transformation = "DES/ECB/NoPadding";
    private static final String DES_Algorithm      = "DES";

    // <editor-folder desc="DES加密">

    /**
     * DES加密后转为Base64编码
     *
     * @param data 明文
     * @param key  8字节秘钥
     * @return Base64密文
     */
    public static byte[] encryptDES2Base64(byte[] data, byte[] key) {
        return EncodeUtils.base64Encode(encryptDES(data, key));
    }

    /**
     * DES加密后转为16进制
     *
     * @param data 明文
     * @param key  8字节秘钥
     * @return 16进制密文
     */
    public static String encryptDES2HexString(byte[] data, byte[] key) {
        return ConvertUtils.bytes2HexString(encryptDES(data, key));
    }

    /**
     * DES加密
     *
     * @param data 明文
     * @param key  8字节秘钥
     * @return 密文
     */
    public static byte[] encryptDES(byte[] data, byte[] key) {
        return desTemplate(data, key, DES_Algorithm, DES_Transformation, true);
    }

    // </editor-folder>

    // <editor-folder desc="DES解密">

    /**
     * DES解密Base64编码密文
     *
     * @param data Base64编码密文
     * @param key  8字节秘钥
     * @return 明文
     */
    public static byte[] decryptBase64DES(byte[] data, byte[] key) {
        return decryptDES(EncodeUtils.base64Decode(data), key);
    }

    /**
     * DES解密16进制密文
     *
     * @param data 16进制密文
     * @param key  8字节秘钥
     * @return 明文
     */
    public static byte[] decryptHexStringDES(String data, byte[] key) {
        return decryptDES(ConvertUtils.hexString2Bytes(data), key);
    }

    /**
     * DES解密
     *
     * @param data 密文
     * @param key  8字节秘钥
     * @return 明文
     */
    public static byte[] decryptDES(byte[] data, byte[] key) {
        return desTemplate(data, key, DES_Algorithm, DES_Transformation, false);
    }

    // </editor-folder>
    // </editor-folder>

    // <editor-folder desc="3DES加解密相关">
    /**
     * 3DES转变
     * <p>法算法名称/加密模式/填充方式</p>
     * <p>加密模式有：电子密码本模式ECB、加密块链模式CBC、加密反馈模式CFB、输出反馈模式OFB</p>
     * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
     */
    public static        String TripleDES_Transformation = "DESede/ECB/NoPadding";
    private static final String TripleDES_Algorithm      = "DESede";

    // <editor-folder desc="3DES加密">
    /**
     * 3DES加密后转为Base64编码
     *
     * @param data 明文
     * @param key  24字节秘钥
     * @return Base64密文
     */
    public static byte[] encrypt3DES2Base64(byte[] data, byte[] key) {
        return EncodeUtils.base64Encode(encrypt3DES(data, key));
    }

    /**
     * 3DES加密后转为16进制
     *
     * @param data 明文
     * @param key  24字节秘钥
     * @return 16进制密文
     */
    public static String encrypt3DES2HexString(byte[] data, byte[] key) {
        return ConvertUtils.bytes2HexString(encrypt3DES(data, key));
    }

    /**
     * 3DES加密
     *
     * @param data 明文
     * @param key  24字节密钥
     * @return 密文
     */
    public static byte[] encrypt3DES(byte[] data, byte[] key) {
        return desTemplate(data, key, TripleDES_Algorithm, TripleDES_Transformation, true);
    }

    // </editor-folder>

    // <editor-folder desc="3DES解密">
    /**
     * 3DES解密Base64编码密文
     *
     * @param data Base64编码密文
     * @param key  24字节秘钥
     * @return 明文
     */
    public static byte[] decryptBase64_3DES(byte[] data, byte[] key) {
        return decrypt3DES(EncodeUtils.base64Decode(data), key);
    }

    /**
     * 3DES解密16进制密文
     *
     * @param data 16进制密文
     * @param key  24字节秘钥
     * @return 明文
     */
    public static byte[] decryptHexString3DES(String data, byte[] key) {
        return decrypt3DES(ConvertUtils.hexString2Bytes(data), key);
    }

    /**
     * 3DES解密
     *
     * @param data 密文
     * @param key  24字节密钥
     * @return 明文
     */
    public static byte[] decrypt3DES(byte[] data, byte[] key) {
        return desTemplate(data, key, TripleDES_Algorithm, TripleDES_Transformation, false);
    }
    // </editor-folder>

    // </editor-folder>

    // <editor-folder desc="AES加解密相关">
    /**
     * AES转变
     * <p>法算法名称/加密模式/填充方式</p>
     * <p>加密模式有：电子密码本模式ECB、加密块链模式CBC、加密反馈模式CFB、输出反馈模式OFB</p>
     * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
     */
    public static        String AES_Transformation = "AES/ECB/NoPadding";
    public static        String AES_Transformation1 = "AES/CBC/PKCS5Padding"; // 云服务APP使用的加解密规则
    private static final String AES_Algorithm      = "AES";

    // <editor-folder desc="AES加密">
    /**
     * AES加密后转为Base64编码
     *
     * @param data 明文
     * @param key  16、24、32字节秘钥
     * @return Base64密文
     */
    public static byte[] encryptAES2Base64(byte[] data, byte[] key) {
        return EncodeUtils.base64Encode(encryptAES(data, key));
    }

    /**
     * AES加密后转为16进制
     *
     * @param data 明文
     * @param key  16、24、32字节秘钥
     * @return 16进制密文
     */
    public static String encryptAES2HexString(byte[] data, byte[] key) {
        return ConvertUtils.bytes2HexString(encryptAES(data, key));
    }
    /**
     * AES加密后转为16进制
     *
     * @param data 明文
     * @param key  16、24、32字节秘钥
     * @return 16进制密文
     */
    public static String encryptAES2HexString1(byte[] data, byte[] key) {
        return ConvertUtils.bytes2HexString(encryptAES1(data, key));
    }

    /**
     * AES加密
     *
     * @param data 明文
     * @param key  16、24、32字节秘钥
     * @return 密文
     */
    public static byte[] encryptAES(byte[] data, byte[] key) {
        return desTemplate(data, key, AES_Algorithm, AES_Transformation, true);
    }

    /**
     * AES加密
     *
     * @param data 明文
     * @param key  16、24、32字节秘钥
     * @return 密文
     */
    public static byte[] encryptAES1(byte[] data, byte[] key) {
        return desTemplate1(data, key, AES_Algorithm, AES_Transformation1, true);
    }

    // </editor-folder>

    // <editor-folder desc="AES解密">
    /**
     * AES解密Base64编码密文
     *
     * @param data Base64编码密文
     * @param key  16、24、32字节秘钥
     * @return 明文
     */
    public static byte[] decryptBase64AES(byte[] data, byte[] key) {
        return decryptAES(EncodeUtils.base64Decode(data), key);
    }

    /**
     * AES解密16进制密文
     *
     * @param data 16进制密文
     * @param key  16、24、32字节秘钥
     * @return 明文
     */
    public static byte[] decryptHexStringAES(String data, byte[] key) {
        return decryptAES(ConvertUtils.hexString2Bytes(data), key);
    }
    /**
     * AES解密16进制密文
     *
     * @param data 16进制密文
     * @param key  16、24、32字节秘钥
     * @return 明文
     */
    public static byte[] decryptHexStringAES1(String data, byte[] key) {
        return decryptAES1(ConvertUtils.hexString2Bytes(data), key);
    }

    /**
     * AES解密（AES转变规则："AES/ECB/NoPadding"）
     *
     * @param data 密文
     * @param key  16、24、32字节秘钥
     * @return 明文
     */
    public static byte[] decryptAES(byte[] data, byte[] key) {
        return desTemplate(data, key, AES_Algorithm, AES_Transformation, false);
    }

    /**
     * AES解密（用云服务app的AES转变规则："AES/CBC/PKCS5Padding"）
     *
     * @param data 密文
     * @param key  16、24、32字节秘钥
     * @return 明文
     */
    public static byte[] decryptAES1(byte[] data, byte[] key) {
        return desTemplate1(data, key, AES_Algorithm, AES_Transformation1, false);
    }

    public static String decryptAES2HexString(String data, byte[] key) {
        return ConvertUtils.bytes2HexString(decryptAES(ConvertUtils.hexString2Bytes(data), key));
    }

    // </editor-folder>

    // </editor-folder>

    /**
     * DES，3DES，AES加密模板（注意不同的加密模式初始化Cipher时的区别）
     *
     * @param data           数据
     * @param key            秘钥
     * @param algorithm      加密算法
     * @param transformation 转变
     * @param isEncrypt      {@code true}: 加密 {@code false}: 解密
     * @return 密文或者明文，适用于DES，3DES，AES
     */
    public static byte[] desTemplate(byte[] data, byte[] key, String algorithm, String transformation, boolean isEncrypt) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            //两个参数，第一个为私钥字节数组， 第二个为加密方式 AES、DES、3DES
            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
            // 实例化加密类，参数为加密方式，要写全
            Cipher cipher = Cipher.getInstance(transformation);

            // AES的CBC模式加密时不可用下面的方法
            SecureRandom random = new SecureRandom();
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, random);

            // AES的ECB加密模式时，不能用此方法
//            IvParameterSpec iv = createIV("123456789ABCDEF");
//            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, iv);

            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * DES，3DES，AES加密模板（注意不同的加密模式初始化Cipher时的区别）（针对CBC模式需设置IvParameterSpec）
     *
     * @param data           数据
     * @param key            秘钥
     * @param algorithm      加密算法
     * @param transformation 转变
     * @param isEncrypt      {@code true}: 加密 {@code false}: 解密
     * @return 密文或者明文，适用于DES，3DES，AES
     */
    public static byte[] desTemplate1(byte[] data, byte[] key, String algorithm, String transformation, boolean isEncrypt) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            //两个参数，第一个为私钥字节数组， 第二个为加密方式 AES、DES、3DES
            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
            // 实例化加密类，参数为加密方式，要写全
            Cipher cipher = Cipher.getInstance(transformation);

            // AES的CBC模式加密时不可用下面的方法
//            SecureRandom random = new SecureRandom();
//            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, random);

            // AES的ECB加密模式时，不能用此方法
            IvParameterSpec iv = createIV("123456789ABCDEF");
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, iv);

            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成IvParameterSpec（初始化向量参数），CBC模式加密需要指定一个初始化向量
     * @param ivParameter 向量参数
     *                    <br/>（AES加密时长度为16字节，不足16，会在生成IvParameterSpec自动在后补0，多于16时，会只取前16位）
     *                    <br/>（DES加密时长度为8字节，不足16，会在生成IvParameterSpec自动在后补0，多于8时，会只取前8位）
     */
    private static IvParameterSpec createIV(String ivParameter) {
        byte[] data = null;
        if (ivParameter == null) {
            ivParameter = "";
        }
        StringBuffer sb = new StringBuffer(16);
        sb.append(ivParameter);
        while (sb.length() < 16) {
            sb.append("0");
        }
        if (sb.length() > 16) {
            sb.setLength(16);
        }
        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new IvParameterSpec(data);
    }


    // ==============================云服务AES加解密===========================================
//    private static final String algorithm = "AES/CBC/PKCS5Padding"; // 用时注意要设置IvParameterSpec，16字节
//
//    /**
//     * 加密时可调用此方法
//     */
//    public static byte[] encrypt(byte[] key,byte[] data) throws Exception {
//        return encrypt(new SecretKeySpec(key, "AES"), data);
////        return encrypt(new SecretKeySpec(key, algorithm), data);
//    }
//
//    /**
//     * AES数据加密
//     */
//    public static byte[] encrypt(SecretKey key, byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException,
//            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//        try {
//            IvParameterSpec iv = new IvParameterSpec(new byte[16]);
////            IvParameterSpec iv = createIV("123456789ABCDEF");
//            Cipher cipher = Cipher.getInstance(algorithm);
//            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
//            return cipher.doFinal(data);
//        } catch (InvalidKeyException e) {
//            throw new InvalidKeyException("不正确的密钥");
//        } catch(IllegalBlockSizeException e) {
//            throw new IllegalBlockSizeException("不正确的数据块长度");
//        } catch(BadPaddingException e) {
//            throw new BadPaddingException("加密失败");
//        }
//    }
//
//    /**
//     * 正常数据的AES解密
//     */
//    public static byte[] decrypt(byte[] key,byte[] data) throws Exception {
//        return decrypt(new SecretKeySpec(key, "AES"),data);
//    }
//
//    public static byte[] decrypt(SecretKey key, byte[] data) throws Exception {
//        try{
//            IvParameterSpec iv=new IvParameterSpec(new byte[16]);
//            Cipher cipher=Cipher.getInstance(algorithm);
//            cipher.init(Cipher.DECRYPT_MODE,key,iv);
//            return cipher.doFinal(data);
//        } catch(InvalidKeyException e) {
//            throw new Exception("不正确的密钥");
//        } catch(IllegalBlockSizeException e) {
//            throw new Exception("不正确的数据块长度");
//        } catch(BadPaddingException e) {
//            throw new Exception("解密失败");
//        }
//    }
//
//    /**
//     * 加密（返回16进制字符串）
//     */
//    public static String encrypt(String content, String password) {
//        byte[] data = null;
//        try {
//            data = content.getBytes("UTF-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        data = encrypt(data, password);
//        return ConvertUtils.bytes2HexString(data);
//    }
//
//    /**
//     * 加密字节数据
//     */
//    public static byte[] encrypt(byte[] content, String password) {
//        try {
//            SecretKeySpec key = createKey(password);
//            Cipher cipher = Cipher.getInstance(algorithm);
//            cipher.init(Cipher.ENCRYPT_MODE, key);
//            return cipher.doFinal(content);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 解密16进制的字符串为字符串
//     */
//    public static String hex_decrypt(String content, String password) {
//        byte[] data = null;
//        try {
//            data = ConvertUtils.hexString2Bytes(content);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        data = decrypt(data, password);
//        if (data == null)
//            return null;
//        String result = null;
//        try {
//            result = new String(data, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    /**
//     * 解密字节数组
//     */
//    public static byte[] decrypt(byte[] content, String password) {
//        try {
//            SecretKeySpec key = createKey(password);
//            Cipher cipher = Cipher.getInstance(algorithm);
//            cipher.init(Cipher.DECRYPT_MODE, key);
//            return cipher.doFinal(content);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 创建密钥
//     */
//    private static SecretKeySpec createKey(String password) {
//        byte[] data = null;
//        if (password == null) {
//            password = "";
//        }
//        StringBuffer sb = new StringBuffer(32);
//        sb.append(password);
//        while (sb.length() < 32) {
//            sb.append("0");
//        }
//        if (sb.length() > 32) {
//            sb.setLength(32);
//        }
//        try {
//            data = sb.toString().getBytes("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return new SecretKeySpec(data, "AES");
//    }

}