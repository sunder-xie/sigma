package com.tqmall.sigma.component.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * Created by huangzhangting on 17/3/30.
 */
public class RSAUtil {
    private static Provider provider = new BouncyCastleProvider();
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public RSAUtil(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String encrypt(String data) throws Exception {
        return Base64.encode(encrypt(this.privateKey, data.getBytes()));
    }

    public String dencrypt(String data) throws Exception {
        return new String(decrypt(this.publicKey, Base64.decode(data)));
    }

    public byte[] encrypt(byte[] data) throws Exception {
        return encrypt(this.privateKey, data);
    }

    public byte[] dencrypt(byte[] data) throws Exception {
        return decrypt(this.publicKey, data);
    }

    public String sign(String text) throws Exception {
        return sign(this.privateKey, text);
    }

    public boolean verify(String text, String sign) throws Exception {
        return verify(this.publicKey, text, sign);
    }

    public static String sign(PrivateKey privateKey, String text) throws Exception {
        Signature signature = Signature.getInstance("SHA1WithRSA", provider);
        signature.initSign(privateKey);
        signature.update(text.getBytes("utf8"));
        byte[] data = signature.sign();
        return Base64.encode(data);
    }

    public static boolean verify(PublicKey publicKey, String text, String sign) throws Exception {
        Signature signature = Signature.getInstance("SHA1WithRSA", provider);
        signature.initVerify(publicKey);
        signature.update(text.getBytes("utf8"));
        byte[] signed = Base64.decode(sign);
        return signature.verify(signed);
    }

    public static PrivateKey loadPrivateKey(String alias, String path, String password) throws Exception {
        FileInputStream ksfis = null;

        PrivateKey var8;
        try {
            KeyStore ks = KeyStore.getInstance("pkcs12");
            ksfis = new FileInputStream(path);
            char[] storePwd = password.toCharArray();
            char[] keyPwd = password.toCharArray();
            ks.load(ksfis, storePwd);
            var8 = (PrivateKey)ks.getKey(alias, keyPwd);
        } finally {
            if(ksfis != null) {
                ksfis.close();
            }

        }

        return var8;
    }

    public static PublicKey loadPublicKey(String alias, String path, String password) throws Exception {
        FileInputStream ksfis = null;

        PublicKey var7;
        try {
            KeyStore ks = KeyStore.getInstance("pkcs12");
            ksfis = new FileInputStream(path);
            char[] storePwd = password.toCharArray();
            ks.load(ksfis, storePwd);
            var7 = ks.getCertificate(alias).getPublicKey();
        } finally {
            if(ksfis != null) {
                ksfis.close();
            }

        }

        return var7;
    }

    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception {
        KeyFactory keyFac = null;

        try {
            keyFac = KeyFactory.getInstance("RSA", provider);
        } catch (NoSuchAlgorithmException var6) {
            throw var6;
        }

        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));

        try {
            return (RSAPublicKey)keyFac.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException var5) {
            throw var5;
        }
    }

    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception {
        KeyFactory keyFac = null;

        try {
            keyFac = KeyFactory.getInstance("RSA", provider);
        } catch (NoSuchAlgorithmException var6) {
            throw var6;
        }

        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));

        try {
            return (RSAPrivateKey)keyFac.generatePrivate(priKeySpec);
        } catch (InvalidKeySpecException var5) {
            throw var5;
        }
    }

    public static byte[] encrypt(Key key, byte[] data) throws Exception {
        try {
            Cipher e = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);
            e.init(1, key);
            int blockSize = e.getBlockSize();
            int outputSize = e.getOutputSize(data.length);
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0?data.length / blockSize + 1:data.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];

            for(int i = 0; data.length - i * blockSize > 0; ++i) {
                if(data.length - i * blockSize > blockSize) {
                    e.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                } else {
                    e.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
                }
            }

            return raw;
        } catch (Exception var9) {
            throw var9;
        }
    }

    public static byte[] decrypt(Key key, byte[] raw) throws Exception {
        try {
            Cipher e = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);
            e.init(2, key);
            int blockSize = e.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);

            for(int j = 0; raw.length - j * blockSize > 0; ++j) {
                bout.write(e.doFinal(raw, j * blockSize, blockSize));
            }

            return bout.toByteArray();
        } catch (Exception var6) {
            throw var6;
        }
    }

    public static String getPublicKeyString(RSAPublicKey key) throws Exception {
        String exponent = byte2hex(key.getPublicExponent().toByteArray());
        String modulus = byte2hex(key.getModulus().toByteArray());
        StringBuffer sb = new StringBuffer();
        sb.append(modulus).append(" ").append(exponent);
        return sb.toString();
    }

    public static String getPrivateKeyString(RSAPrivateKey key) throws Exception {
        String exponent = byte2hex(key.getPrivateExponent().toByteArray());
        String modulus = byte2hex(key.getModulus().toByteArray());
        StringBuffer sb = new StringBuffer();
        sb.append(modulus).append(" ").append(exponent);
        return sb.toString();
    }

    public static RSAPublicKey getPublicKey(String keyString) throws Exception {
        String[] part = keyString.split(" ");
        if(part.length != 2) {
            throw new Exception("密钥文件错误。");
        } else {
            byte[] bytes = hex2byte(part[0]);
            BigInteger modulus = new BigInteger(bytes);
            bytes = hex2byte(part[1]);
            BigInteger publicExponent = new BigInteger(bytes);
            return generateRSAPublicKey(modulus.toByteArray(), publicExponent.toByteArray());
        }
    }

    public static RSAPrivateKey getPrivateKey(String keyString) throws Exception {
        String[] part = keyString.split(" ");
        if(part.length != 2) {
            throw new Exception("密钥文件错误。");
        } else {
            byte[] bytes = hex2byte(part[0]);
            BigInteger modulus = new BigInteger(bytes);
            bytes = hex2byte(part[1]);
            BigInteger privateExponent = new BigInteger(bytes);
            return generateRSAPrivateKey(modulus.toByteArray(), privateExponent.toByteArray());
        }
    }

    public static String byte2hex(byte[] bytes) {
        StringBuffer retString = new StringBuffer();

        for(int i = 0; i < bytes.length; ++i) {
            retString.append(Integer.toHexString(256 + (bytes[i] & 255)).substring(1).toUpperCase());
        }

        return retString.toString();
    }

    public static String byte2hex(byte[] bytes, int index, int len) {
        StringBuffer retString = new StringBuffer();

        for(int i = index; i < len; ++i) {
            retString.append(Integer.toHexString(256 + (bytes[i] & 255)).substring(1).toUpperCase());
        }

        return retString.toString();
    }

    public static byte[] hex2byte(String hex) {
        byte[] bts = new byte[hex.length() / 2];

        for(int i = 0; i < bts.length; ++i) {
            bts[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }

        return bts;
    }
}
