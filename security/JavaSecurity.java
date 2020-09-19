package security;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.util.TextUtils;

public class JavaSecurity {




    
    public static byte[] generateRandomString(int length){				
        SecureRandom random = new SecureRandom();
        byte[] result = new byte[16] ;
        random.nextBytes(result);
        return result ;
    }
   
   public static void javaHash() throws UnsupportedEncodingException, NoSuchAlgorithmException{
       String origin = "abc" ;
       System.out.println(origin.hashCode());
       origin = "abcabcabcabcabcabcabcabcabcabcabcabcabcabcababcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabccabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabc" ;
       System.out.println(origin.hashCode());
       

       byte[] bytesOfMessage = origin.getBytes(Charset.forName("UTF8"));
       
       MessageDigest md = MessageDigest.getInstance("MD5");
       byte[] thedigest = md.digest(bytesOfMessage);
       System.out.println(thedigest);
       origin = "abcabc" ;
       
       thedigest = md.digest(bytesOfMessage);
       System.out.println(thedigest);
       
       origin = "abcabcabcabcabcabcabca" ;
       
       thedigest = md.digest(bytesOfMessage);
       System.out.println(thedigest);
   }

   public static void listSupportCiphersInJVM(){
           
       System.out.println(System.getProperty("java.version"));   
       //Condition #1, add this in the run java configure -Dhttps.protocols\=SSL_TLSv2
       System.setProperty("https.cipherSuites","SSL_ECDH_ECDSA_WITH_AES_256_GCM_SHA384,SSL_RSA_WITH_AES_256_GCM_SHA384");
       System.setProperty("https.protocols", "TLSv1,TLSv1.1") ;
       
       
       //Condition #2, handle dsweb_tls_disabledAlgorithms
       boolean cond2 = false ;
       if(cond2){
           String disabledAlgorithms = System.getProperty("dsweb_tls_disabledAlgorithms",Security.getProperty("jdk.tls.disabledAlgorithms"));
           
           if (disabledAlgorithms == null) 
           {
               disabledAlgorithms = "SSLv3,RC4,DESede,MD5,DH keySize <2048";  // we turn these off by default - for BEAST & other vulnerabilities
           } 
           else
           {
                 if (!disabledAlgorithms.contains("RC4")) 
                   {
                       disabledAlgorithms += ", RC4";
                   }
                   if(!disabledAlgorithms.contains("SSLv3"))
                   {
                       disabledAlgorithms += ",SSLv3";
                   }
                   if(!disabledAlgorithms.contains("DESede"))
                   {
                       disabledAlgorithms += ",DESede";
                   }
                   if(!disabledAlgorithms.contains("MD5"))
                   {
                       disabledAlgorithms += ",MD5";
                   }
               if(!disabledAlgorithms.contains("DH keySize <2048"))
               {
                   disabledAlgorithms += ",DH keySize <2048";
               }
           }
           
           disabledAlgorithms += ",DHE_RSA" ;	
           disabledAlgorithms += ",SHA1" ;		
           Security.setProperty("jdk.tls.disabledAlgorithms", disabledAlgorithms);
       }
   
       

       //User default SSLServerSocketFactory from java.net
       SSLServerSocketFactory ssf = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
       String[] defaultCiphers = ssf.getDefaultCipherSuites();
       Set<String> defaultList = new HashSet<String>() ;      
       for(int i=0;i<defaultCiphers.length;i++){
           defaultList.add(defaultCiphers[i]) ;   	
       }
       String[] availableCiphers = ssf.getSupportedCipherSuites();
       Set<String> avalilableList = new HashSet<String>() ;      
       for(int i=0;i<availableCiphers.length;i++){
           avalilableList.add(availableCiphers[i]) ;   	
       }
       
       
       System.out.println("There are " + defaultCiphers.length + " default cipher suites");
       for(int i=0;i<defaultCiphers.length;i++){
            System.out.println(defaultCiphers[i]);
       }
       System.out.println("*******************************************");
       System.out.println("*******************************************");
       System.out.println("*******************************************");
       avalilableList.removeAll(defaultList) ;
       System.out.println("There are " + avalilableList.size() + " supported cipher suites but not in default list.");
       
       for(String cipher:avalilableList){
            System.out.println(cipher);    	
       }
      
       SSLContextBuilder builder = new SSLContextBuilder();
      
       SSLConnectionSocketFactory sslsf = null ;
       try {
           builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
           SSLContext context = builder.build();
           sslsf = new SSLConnectionSocketFactory(
                   context,
                   split(System.getProperty("https.protocols")),
                   split(System.getProperty("https.cipherSuites")),
                   new NoopHostnameVerifier());
           defaultCiphers = context.getSocketFactory().getDefaultCipherSuites() ;
           availableCiphers = context.getSocketFactory().getSupportedCipherSuites() ;
           
           
       } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {	
           e.printStackTrace();
       }
     
           
       defaultList = new HashSet<String>() ;      
       for(int i=0;i<defaultCiphers.length;i++){
           defaultList.add(defaultCiphers[i]) ;   	
       }
       avalilableList = new HashSet<String>() ;      
       for(int i=0;i<availableCiphers.length;i++){
           avalilableList.add(availableCiphers[i]) ;   	
       }
       System.out.println("*******************************************");
       System.out.println("Apache Http Client");
       System.out.println("*******************************************");
       System.out.println("There are " + defaultCiphers.length + " default cipher suites");
       for(int i=0;i<defaultCiphers.length;i++){
            System.out.println(defaultCiphers[i]);
       }
       System.out.println("*******************************************");
       System.out.println("*******************************************");
       System.out.println("*******************************************");
       avalilableList.removeAll(defaultList) ;
       System.out.println("There are " + avalilableList.size() + " supported cipher suites but not in default list.");
       
       for(String cipher:avalilableList){
            System.out.println(cipher);    	
       }       
       
   }
   
   /**
    * Java Cryptography Architecture (JCA) see more details in 
    * https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html
    * 
    * As JCA is provider based, each provider will impl the details algorithm, this method list all
    * the supported algorithm supported in JVM
    * 
    * For the standard name of algorithm, see
    * https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html
    * 
    * */
   public static void listJavaSecurityProperties(){
        
       
       Set<String> cipher = Security.getAlgorithms("Cipher") ;
       System.out.println("Cipher provided: "+ cipher.toString()) ;
       
       Set<String> mac = Security.getAlgorithms("Mac") ;
       System.out.println("Mac: "+ mac.toString()) ;
       
       Set<String> keyStore = Security.getAlgorithms("KeyStore") ;
       System.out.println("KeyStore provided: "+ keyStore.toString()) ;
       
       
       Provider[] providers = Security.getProviders() ;
       for(int i=0;i<providers.length;i++){
           System.out.println("Security Provider:" + providers[i].getName());
           System.out.println(providers[i].getInfo());
       }
   }
   
   /**
    * The messageDigest algorithm in jdk
    * */
   public static void messageDigest() {
       String original = "hello world" ;
       System.out.println("Original message:" + original);
       //Get all message Digest
       Set<String> messageDigest = Security.getAlgorithms("MessageDigest") ;
       System.out.println("MessageDigest provided: "+ messageDigest.toString()) ;
       try {
           //Get the md5 algorithm from default provider
           MessageDigest md = MessageDigest.getInstance("SHA") ;
           System.out.println("Provider:" + md.getProvider().getName()); 
           byte[] result = md.digest(original.getBytes()) ;
           System.out.println("Result[" + md.getAlgorithm() + "/"+ md.getDigestLength()+ "] :"+ result);
           
       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       }
   }
   
   /**
    * The signature provided by jdk test.
    * sender use private key to encrypt the message. and receiver use public key to de-crypt the data. 
    * 
    * The algorithm used in private/public key should meet the algorithm used in signature. 
    * i.e. if private/public key is generated using RSA, the signature should use *WITHRSA
    * 
    * Notes. *WITHRSA and RSA are two different algorithm
    * */
   public static void signature(){
       String original = "hello world, this is a topic I want to express" ;
       System.out.println("Original message:" + original);
       Set<String> signature = Security.getAlgorithms("Signature") ;
       System.out.println("Signature provided: "+ signature.toString()) ;
       try {
           //Generate the private key and public key
           KeyPair kp = generateKeyPairs("DSA") ;
           PublicKey pubk = kp.getPublic() ;
           PrivateKey prik = kp.getPrivate() ;
           
           Signature sign = Signature.getInstance(prik.getAlgorithm()) ;	
           System.out.println("The algorighm used in signature is: " + sign.getAlgorithm());
           //use private to sign the key
           sign.initSign(prik);
           //put into original data
           sign.update(original.getBytes());
           //get the signed data
           byte[] signed = sign.sign() ;
           System.out.println("Signed Data:" + signed);
           
           Signature sign2 = Signature.getInstance(pubk.getAlgorithm()) ;
           sign2.initVerify(pubk);
           //set the original data, so the verify method will compare it with the de-crypt data.
           sign2.update(original.getBytes());
           boolean verified = sign2.verify(signed) ;
           if(verified){
               System.out.println("signature is verified");
           }else{
               System.out.println("Signature is not verified");
           }
                   
           
       } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
           e.printStackTrace();
       }
       
   }
   
   /**
    * Generate the private and public key pairs, using the given algorithm
    * algorithm: RSA, DSA
    *
    * */
   public static KeyPair generateKeyPairs(String algo) throws NoSuchAlgorithmException{
       KeyPairGenerator kg = KeyPairGenerator.getInstance(algo) ;
       kg.initialize(1024);
       System.out.println("The private/public key is generated using " + kg.getAlgorithm() );
       KeyPair kp = kg.genKeyPair() ;
       return kp ;
   }
   private static String[] split(final String s) {
    if (TextUtils.isBlank(s)) {
        return null;
    }
    return s.split(" *, *");
}

}
