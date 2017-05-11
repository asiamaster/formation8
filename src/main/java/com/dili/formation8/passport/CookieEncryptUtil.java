package com.dili.formation8.passport;


import com.dili.formation8.vo.UserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * 用户信息加密工具类
 */
@Component("cookieEncryptUtil")
public class CookieEncryptUtil {

    @Value("${cookie-key}")
    private String COOKIE_KEY;

    /**
     *
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    private static byte[] cookieEncrypt(UserData data)
            throws UnsupportedEncodingException {
//        8位随机二进制字节流
        byte[] random = Utils.generateRandomByteArray();
        byte[] buffer = (byte[]) null;
        int bufferLength = 7;
        UserDataTransformer userDataTransformer = new UserDataTransformer(data);
        byte version = userDataTransformer.getVersion();
        byte[] uId = userDataTransformer.getUserId();
        byte[] username = userDataTransformer.getUserName();
        byte userType = userDataTransformer.getUserType();
        byte[] expires = userDataTransformer.getExpires();
        byte[] referralCode = userDataTransformer.getReferralCode();
        byte[] referer = userDataTransformer.getReferer();
        bufferLength += random.length;
        bufferLength += uId.length;
        bufferLength += username.length;
        bufferLength += expires.length;
        bufferLength += referralCode.length;
        bufferLength += referer.length;
        buffer = new byte[bufferLength + 1];//userType
        int pos = 0;
        System.arraycopy(random, 0, buffer, pos, random.length);
        pos += random.length;

        buffer[pos] = version;
        pos++;
        
        System.arraycopy(uId, 0, buffer, pos, uId.length);
        pos += uId.length;
        
        System.arraycopy(username, 0, buffer, pos, username.length);
        pos += username.length;
        buffer[pos] = 0;
        pos++;
        buffer[pos] = 0;
        pos++;

        System.arraycopy(expires, 0, buffer, pos, expires.length);
        pos += expires.length;

        System.arraycopy(referralCode, 0, buffer, pos, referralCode.length);
        pos += referralCode.length;
        buffer[pos] = 0;
        pos++;
        buffer[pos] = 0;
        pos++;
        
        System.arraycopy(referer, 0, buffer, pos, referer.length);
        pos += referer.length;
        
        buffer[pos] = userType;
        pos++;

        return buffer;
    }

    public static final String IV_BYTES = "9102930009060708";

    public  String generateCookieString(UserData userData) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
//        byte[] ivbytes = new byte[16];
//        Key长度必须为16位
        byte[] ivbytes = IV_BYTES.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(Utils.hexToByte(COOKIE_KEY), "AES");
        IvParameterSpec iv = new IvParameterSpec(ivbytes);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        //    默认JDK中的加密库对密钥长度限制在128位以下,所以无法使用128位以上的密钥.
//            如果需要在AES中使用128位以上密钥加密时,必须下载扩展的加密库包
//        这种限制是因为美国对软件出口的控制。
        byte[] b = cipher.doFinal(cookieEncrypt(userData));
        return Utils.byte2hex(b);
    }
    
    public static void main(String[] args) throws Exception {
        CookieEncryptUtil cookieEncryptUtil = new CookieEncryptUtil();
        cookieEncryptUtil.COOKIE_KEY = "E812B0CC2607104AB9942EE22666DE23";
        UserData userData = new UserData();
        userData.setUserName("wm");
        userData.setUid(Long.MAX_VALUE);
        userData.setVersion(1);
        userData.setUserType(1);
        userData.setExpires(new Date(System.currentTimeMillis()+3600000));
        userData.setReferer(Long.MAX_VALUE-1);
        userData.setReferralCode("xfe123");
        System.out.println(cookieEncryptUtil.generateCookieString(userData));

        try {
            AuthTicket ticket = AuthenticationUtil.getFormsAuthenticationTicket("FFA65BC7C38066AC59404267D7EA637014FA2891D1DE45234337B5E2A4603403BC38E1C834DBA2FFBE415A7E4A6F436CD54F73E9A6B8EAC284EAEEC92B1C5CB8", "E812B0CC2607104AB9942EE22666DE23");
            System.out.println(ticket);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
