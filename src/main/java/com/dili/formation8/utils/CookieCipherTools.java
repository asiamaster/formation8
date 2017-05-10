package com.dili.formation8.utils;

import com.dili.formation8.utils.security.Base32;
import com.dili.formation8.utils.security.DESCoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;


public class CookieCipherTools {
    private static final Logger log = LoggerFactory.getLogger(CookieCipherTools.class);
    private String charsetName;
    
    public String encrypt(String value, String key) {
      try {
        byte[] data;
        if (!StringUtils.isEmpty(this.charsetName)) {
          try {
            data = value.getBytes(this.charsetName);
          } catch (Exception e1) {
            log.error("charset " + this.charsetName + " Unsupported!", e1);
            data = value.getBytes();
          }
        } else {
          data = value.getBytes();
        }
        byte[] bytes = encrypt(key, data);
        return encoding(bytes);
      } catch (Exception e) {
        log.error("encrypt error", e); }
      return null;
    }
    
    private String encoding(byte[] bytes)
      throws Exception
    {
      return Base32.encode(bytes);
    }
    
    private byte[] decoding(String value) throws Exception {
      return Base32.decode(value);
    }
    
    private byte[] encrypt(String key, byte[] data) throws Exception
    {
      return DESCoder.encrypt(data, key);
    }
    
    private byte[] decrypt(String key, byte[] data) throws Exception {
      return DESCoder.decrypt(data, key);
    }
    
    public String decrypt(String value, String key) {
      try {
        byte[] data = decoding(value);
        byte[] bytes = decrypt(key, data);
        if (!StringUtils.isEmpty(this.charsetName)) {
          try {
            return new String(bytes, this.charsetName);
          } catch (UnsupportedEncodingException e1) {
            log.error("charset " + this.charsetName + " Unsupported!", e1);
            return new String(bytes);
          }
        }
        return new String(bytes);
      }
      catch (Exception e) {
        log.error("encrypt error", e); }
      return null;
    }
    
    public void setCharsetName(String charsetName)
    {
      this.charsetName = charsetName;
    }
}
