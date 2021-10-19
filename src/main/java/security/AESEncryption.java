package security;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.util.TextUtils;

public class AESEncryption {

    public static void main(String[] args) {
        String clearText = "This is clearText";
        String key = "sample_key";
        String iv = "abc";
        String result = null;
        try {
            result = encryptAES(key, clearText, iv);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String decryptBack = null;
        try {
            decryptBack = dencryptAES(key, result, iv);
            System.out.println("DecryptBack: " + decryptBack);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static  StringBuffer formattedStringRep = new StringBuffer(24);
    private static  int AES_LENGTH = 256 ;
    
    public static String dencryptAES(String key,String message,String vector) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] customizedKeyBytes = key.getBytes("UTF-8");
        //Use SHA-256 to get the signature for the bytes, the output is 256 bytes
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        customizedKeyBytes = sha.digest(customizedKeyBytes) ;
        //Get a raw with length is 16
        byte[] raw = Arrays.copyOf(customizedKeyBytes, AES_LENGTH/8);
        SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
        
        byte[] ivb = sha.digest(vector.getBytes()) ;
        byte[] rawiv = Arrays.copyOf(ivb, 16);
        IvParameterSpec iv = new IvParameterSpec(rawiv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec,iv);
        byte[] bytes = getFromHexEncoding(message);
        byte[] encryptedMessage = cipher.doFinal(bytes);
    
        return new String(encryptedMessage, "UTF-8");
    }

    public static String encryptAES(String key,String message,String vector) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] customizedKeyBytes = key.getBytes("UTF-8");
        //Use SHA-256 to get the signature for the bytes, the output is 256 bytes
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        customizedKeyBytes = sha.digest(customizedKeyBytes) ;
        //Get a raw with length is 16
        byte[] raw = Arrays.copyOf(customizedKeyBytes, AES_LENGTH/8);
        SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
        
        byte[] ivb = sha.digest(vector.getBytes()) ;
        byte[] rawiv = Arrays.copyOf(ivb, 16);
        IvParameterSpec iv = new IvParameterSpec(rawiv);
    
        cipher.init(Cipher.ENCRYPT_MODE, keySpec,iv);
        byte[] encryptedMessage = cipher.doFinal(message.getBytes("UTF-8"));
    
        return createHexEncodingFromByteArray(encryptedMessage) ;
    }
    public static byte[] getFromHexEncoding(String hexInput)
    {
        byte[] output = new byte[hexInput.length() / 2];
        
        for (int i = 0; i < hexInput.length() / 2; i++)
        {
            // We'll parse 2 bytes at a time from the input string to be
            // converted
            // into a single byte value. For example, given the input hex value
            // "fe8034", we will process "fe", then "80" and finally "34"
            // The integer value will then be converted to it's signed byte
            // values ( in the example above : -2, 128 and 52 ).
            // The Byte.parseByte() routine does not handle values greater than
            // 7F
            // which is why we're using an Integer to parse the byte value.
            
            String byteChar = hexInput.substring(i * 2, i * 2 + 2);
            Integer val = Integer.parseInt(byteChar, 16);
            
            output[i] = val.byteValue();
        }
        return output;
    }
   
    public static String createHexEncodingFromByteArray(byte[] input)
    {
        //    	 StringBuffer formattedStringRep = new StringBuffer(input.length * 2);
        formattedStringRep.setLength(0);
        for (int i = 0; i < input.length; i++)
        {
            formattedStringRep.append(String.format("%02x", input[i]));
        }
        return formattedStringRep.toString();
    }
}
