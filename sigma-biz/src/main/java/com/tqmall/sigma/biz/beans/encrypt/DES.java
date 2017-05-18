package com.tqmall.sigma.biz.beans.encrypt;

import com.tqmall.core.common.exception.BusinessCheckFailException;
import com.tqmall.sigma.biz.beans.constant.SigmaConstant;
import com.tqmall.sigma.biz.beans.tonglian.error.SigmaError;
import com.tqmall.sigma.component.utils.Base64;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by hms on 2016/4/28.
 */
@Slf4j
public class DES {
    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(SigmaConstant.DES_IV.getBytes());
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return Base64.encode(encryptedData);
    }

    public static String encryptDES(String encryptString) {
        try {
            return encryptDES(encryptString, SigmaConstant.DES_KEY);
        } catch (Exception e) {
            log.error("连连支付参数加密失败", e);
            throw new BusinessCheckFailException(SigmaError.DES_ENCRYPT_ERROR);
        }
    }

    public static String encryptDESDB(String encryptString) {
        try {
            return encryptDES(encryptString, SigmaConstant.DES_DB_KEY);
        } catch (Exception e) {
            log.error("连连支付参数加密失败", e);
            throw new BusinessCheckFailException(SigmaError.DES_ENCRYPT_ERROR);
        }
    }

    public static String decryptDES(String decryptString, String decryptKey) throws Exception {
        byte[] byteMi = Base64.decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(SigmaConstant.DES_IV.getBytes());
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);
        return new String(decryptedData);
    }

    public static String decryptDES(String decryptString) {
        try {
            return decryptDES(decryptString, SigmaConstant.DES_KEY);
        } catch (Exception e) {
            log.error("解密失败", e);
            throw new BusinessCheckFailException(SigmaError.DES_DECRYPT_ERROR);
        }
    }

    public static String decryptDESDB(String decryptString) {
        try {
            return decryptDES(decryptString, SigmaConstant.DES_DB_KEY);
        } catch (Exception e) {
            log.error("解密失败", e);
            throw new BusinessCheckFailException(SigmaError.DES_DECRYPT_ERROR);
        }
    }

    public static Integer decrypyDESToID(String decryptString){
        String decryptedString = decryptDES(decryptString);
        try {
            return Integer.parseInt(decryptedString);
        } catch (Exception e) {
            log.error("解密后数据转换异常，解密后数据为：" + decryptedString);
            throw new BusinessCheckFailException(SigmaError.CODE_NULL);
        }
    }
}
