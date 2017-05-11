package com.dili.formation8.passport;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * 用户信息解密工具类
 * Created by asiam on 2017/5/9 0009.
 */
public class AuthenticationUtil {
    private static final String ENCODING = "UTF-16LE";

    public static AuthTicket getFormsAuthenticationTicket(String desc, String key) throws Exception{
        return parseTicket(decrypt(desc, key));
    }

    private static AuthTicket parseTicket(byte[] ticketBytes)
            throws Exception {
        AuthTicket ticket = null;
        int pos = 8;

        int version = ticketBytes[pos];
        pos++;

        byte[] userId = new byte[8];
        System.arraycopy(ticketBytes, pos, userId, 0, userId.length);
        pos += userId.length;

        byte[] usernames = Utils.readUtf16le(ticketBytes, pos);
        pos += usernames.length+2;

        byte[] expires = new byte[8];
        System.arraycopy(ticketBytes, pos, expires, 0,
                expires.length);
        pos += expires.length;

        byte[] referralCodes = Utils.readUtf16le(ticketBytes, pos);
        pos += referralCodes.length+2;

        byte[] referers = new byte[8];
        System.arraycopy(ticketBytes, pos, referers, 0, referers.length);
        pos += referers.length;

        int userType = ticketBytes[pos];
        pos++;

        try {
            long uId = Utils.byteArrayToLong(userId);

            String username = new String(usernames, ENCODING);

            Date expire = Utils.ByteArrayToDate(expires);

            String referralCode = new String(referralCodes, ENCODING);

            long referer = Utils.byteArrayToLong(referers);

            ticket = new AuthTicket(version, uId, username, expire, userType, referer, referralCode);
        } catch (Exception e) {
            throw e;
        }
        return ticket;
    }

    private static byte[] decrypt(String str, String key) throws Exception {
        if ((str == null) || (str.trim().length() < 1)) {
            return null;
        }

        byte[] keybytes = Utils.hexToByte(key);
        byte[] ivbytes = CookieEncryptUtil.IV_BYTES.getBytes();

        SecretKeySpec skeySpec = new SecretKeySpec(keybytes, "AES");

        IvParameterSpec iv = new IvParameterSpec(ivbytes);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        byte[] encrypted1 = Utils.hexToByte(str);

        byte[] original = cipher.doFinal(encrypted1);

        return original;
    }

    public static void main1(String[] args) throws Exception {
        System.out
                .println(AuthenticationUtil
                        .getFormsAuthenticationTicket(
                                "79FE4560C26477015A0549A1DD6F99AFC64304BD6E45EAE1230BE04AD989AC1127E6DDBC1A7B1A162D5E31D29BB67E2BEFA6180BEAD195765B8D8C2D13BC1226711FCBBF2607B125F7FF48902FB6E8AAB3A3D2F1D7FD3EE307182B1851A539EE8BF710F97884C1515118447DDD7EF24F1B6B865AE5A2EEB2D5C76B29F26DC4EC9E7EAB76ED74F5FFCB78EA07F09671215DB3D7D8E32F595C5207272C8E9F60652E343D27C68700345CDDE75798DAC9DD6AC8D04D3843009B28A9A325E3F42A16138A6D7A8B365772EF311CBF6B5BCA262669EE8CD3139954F6E87E3C3BF1A062",
                                "E812B0CC2607104AB9942EE22666DE23"));
    }
}
