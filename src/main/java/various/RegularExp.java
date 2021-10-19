package various;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExp {
    
    public static void main(String[] args){
        String url = "jdbc:db2://192.168.1.1:50000/bludb:sslConnection=true;" ;
        groupExtract(url) ;


        fileNameMatches("cvs.txt") ;
        fileNameMatches("<script>.txt") ;

        splitTest() ;
    }

    /**
     * Extract the groups from the message
     * ?: group but does not capture
     * @param url
     */
    public static void groupExtract(String url){
        
        String JDBC_URL = "^(jdbc:db2:\\/\\/(.+):(?:\\d+)\\/([^:]+:?))(.*)$" ;
        Pattern JDBC_URL_PATTERN = Pattern.compile(JDBC_URL) ;
        Matcher matcher = JDBC_URL_PATTERN.matcher(url) ;

        if(matcher.find()){
            System.out.println("[groupExtract] bingle.") ;
            System.out.println("Group1:" + matcher.group(1));
            System.out.println("Group2:" + matcher.group(2));
            System.out.println("Group3:" + matcher.group(3));
            System.out.println("Group4:" + matcher.group(4));
         
        }
    }


    public static void fileNameMatches(String fileName){
        String fileNameRegExp = "^[A-Za-z0-9_]+[A-Za-z0-9_.@#-]*$" ;
        System.out.println(fileName.matches(fileNameRegExp)) ;
    }

    public static void splitTest(){
        String msg = "a,b , c, d ,e" ;
        /** 
         * skip the space before and after `,`
         * \s is space, the two below are equals
         * 
         **/
        // String[] result = msg.split(" *, *") ;
        // String[] result = msg.split("\s*,\s*") ;

        // for(int i=0;i<result.length;i++){
		// 	System.out.println("[splitTest]:" + result[i] + ".");
		// }
    }
}
