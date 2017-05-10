package com.dili.formation8.utils;

import com.dili.formation8.vo.AuthTicket;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Date;
import java.util.Random;


public class Utils {
    
    public static byte[] hexToByte(String s) throws IOException {
        int i = s.length() / 2;
        byte[] abyte0 = new byte[i];
        int j = 0;
        if (s.length() % 2 != 0)
            throw new IOException("hexadecimal string with odd number of characters");
        for (int k = 0; k < i; k++) {
            char c = s.charAt(j++);
            int l = "0123456789abcdef0123456789ABCDEF".indexOf(c);
            if (l == -1)
                throw new IOException(
                        "hexadecimal string contains non hex character");
            int i1 = (l & 0xF) << 4;
            c = s.charAt(j++);
            l = "0123456789abcdef0123456789ABCDEF".indexOf(c);
            i1 += (l & 0xF);
            abyte0[k] = ((byte) i1);
        }

        return abyte0;
    }
    
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
    public static byte[] longToByteArray(long l) {
        byte[] bArray = new byte[8];
        ByteBuffer bBuffer = ByteBuffer.wrap(bArray);
        bBuffer.order(ByteOrder.LITTLE_ENDIAN);
        LongBuffer lBuffer = bBuffer.asLongBuffer();
        lBuffer.put(0, l);
        return bArray;
    }

    public static long byteArrayToLong(byte[] bArray) {
        ByteBuffer bBuffer = ByteBuffer.wrap(bArray);
        bBuffer.order(ByteOrder.LITTLE_ENDIAN);
        LongBuffer lBuffer = bBuffer.asLongBuffer();
        long l = lBuffer.get(0);
        return l;
    }
    
    public static byte[] DateToByteArray(Date date) {
        long longDate = date.getTime();
        longDate *= 10000L;
        longDate += 116444736000000000L;
        return longToByteArray(longDate);
    }

    public static Date ByteArrayToDate(byte[] bytes) throws Exception {
        if (bytes.length != 8)
            throw new Exception("must be 8 bytes");
        long date = byteArrayToLong(bytes);
        return new Date((date - 116444736000000000L) / 10000L);
    }

    /**
     * 8位随机二进制字节流
     * @return
     */
    public static byte[] generateRandomByteArray(){
        Random random = new Random();
        byte[] bytes = new byte[8];
        for (int i = 0; i < bytes.length; i++){
            bytes[i] = (byte)random.nextInt(256);
        }
        return bytes;
    }
    
    public static byte[] readUtf16le(byte[] ticketBytes, int start) {
        int end = checkUtf16leEnd(ticketBytes, start);
        if (end < 0) {
            return new byte[0];
        }
        int len = end - start;
        byte[] desc = new byte[len];
        System.arraycopy(ticketBytes, start, desc, 0, len);
        return desc;
    }

    private static int checkUtf16leEnd(byte[] ticketBytes, int start) {
        int end = ticketBytes.length;

        for (int i = start; i < ticketBytes.length - 1; i += 2) {
            byte i1 = ticketBytes[i];
            byte i2 = ticketBytes[(i + 1)];
            if ((i1 == 0) && (i2 == 0)) {
                end = i;
                break;
            }
        }
        return end;
    }
    @SuppressWarnings("restriction")
    public static String generateKey() throws NoSuchAlgorithmException{
        Security.addProvider(new com.sun.crypto.provider.SunJCE());  
        //实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)  
        KeyGenerator keygen = KeyGenerator.getInstance("AES");  
        keygen.init(192);
        //生成密钥  
        SecretKey deskey = keygen.generateKey();
        return byte2hex(deskey.getEncoded());
    }
    
    public static void main1(String[] args) throws Exception {
        /*try {
            System.out.println(generateKey());
            //E812B0CC2607104AB9942EE22666DE233F05F88D43DBD01C
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/
        AuthTicket ticket = AuthenticationUtil.getFormsAuthenticationTicket("5B5359F0CD1760BAE0DDEE79694A7FD8447462C803DAADD2EFCA2A03644172CD5064CFB158E1644FD5C2885561B43267160435D626A847F20029BF7422A08A6B", "E812B0CC2607104AB9942EE22666DE233F05F88D43DBD01C");
        System.out.println(ticket);
    }
}
