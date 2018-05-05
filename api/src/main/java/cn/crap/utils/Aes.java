package cn.crap.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.crap.dto.SettingDto;
import cn.crap.framework.SpringContextHolder;
import cn.crap.service.tool.SettingCache;


public class Aes
{
	private static String handlerKey(String apiKey) {
		String tempKey = "HDALd)9dkA*&1kS$CKSJ}{|A";
		if (apiKey.length() > 16) {
			apiKey = apiKey.substring(0, 16);
		} else if (apiKey.length() < 16) {
			apiKey = apiKey + tempKey.substring(0, 16 - apiKey.length());
		}
		return apiKey;
	}
	 public static final String iv="CRAPG_@W8#_19#10";
	 public static String encrypt(String data){
         SettingCache settingCache = SpringContextHolder.getBean("settingCache", SettingCache.class);
		 SettingDto setting = settingCache.get(ISetting.S_SECRETKEY);
		 String PWD = "";
		 if(setting!=null)
			 PWD = setting.getValue();
		 PWD = handlerKey(setting.getValue());
         try {
             Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
             int blockSize = cipher.getBlockSize();
 
             byte[] dataBytes = data.getBytes();
             int plaintextLength = dataBytes.length;
             if (plaintextLength % blockSize != 0) {
                 plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
             }
 
             byte[] plaintext = new byte[plaintextLength];
             System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
              
             SecretKeySpec keyspec = new SecretKeySpec(PWD.getBytes(), "AES");
             IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
 
             cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
             byte[] encrypted = cipher.doFinal(plaintext);
 
             return  parseByte2HexStr(encrypted);
 
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }
 
     public static String desEncrypt(String data){
         SettingCache settingCache = SpringContextHolder.getBean("settingCache", SettingCache.class);
    	 SettingDto setting = settingCache.get(ISetting.S_SECRETKEY);
		 String PWD = "";
		 if(setting!=null)
			 PWD = setting.getValue();
		 PWD = handlerKey(setting.getValue());
         try
         {
        	 if(data==null||data.equals("")){
        		 return "";
        	 }
        	 data=data.trim();
             byte[] encrypted1 = parseHexStr2Byte(data);
              
             Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
             SecretKeySpec keyspec = new SecretKeySpec(PWD.getBytes(), "AES");
             IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
              
             cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
 
             byte[] original = cipher.doFinal(encrypted1);
             String originalString = new String(original);
             return originalString.trim();
         }
         catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }  
     /**将二进制转换成16进制
      * @param buf
      * @return
      */
     public static String parseByte2HexStr(byte buf[]) {
             StringBuffer sb = new StringBuffer();
             for (int i = 0; i < buf.length; i++) {
                     String hex = Integer.toHexString(buf[i] & 0xFF);
                     if (hex.length() == 1) {
                             hex = '0' + hex;
                     }
                     sb.append(hex.toUpperCase());
             }
             return sb.toString();
     }
     /**将16进制转换为二进制
      * @param hexStr
      * @return
      */
     public static byte[] parseHexStr2Byte(String hexStr) {
             if (hexStr.length() < 1)
                     return null;
             byte[] result = new byte[hexStr.length()/2];
             for (int i = 0;i< hexStr.length()/2; i++) {
                     int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
                     int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
                     result[i] = (byte) (high * 16 + low);
             }
             return result;
     }
     public static void main(String args[]){
    	 
     }
 }