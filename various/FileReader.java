package various;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class FileReader{
    public static void main(String[] args){
        testScanner() ;
    }

    public static void testScanner() {
    
        boolean configMapEnabled = false ;
        File file = new File("/Users/tao/bludr.enable") ;
        if(file == null || !file.exists()) {
            System.out.println("Flag file " + file.getPath() + " does not exists.");
            return ;
        }	
        try (Scanner scanner = new Scanner(file)) {

            if (scanner.hasNext()){
                String options = scanner.nextLine() ;
                configMapEnabled = "true".equalsIgnoreCase(options.trim()) ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Result: " + configMapEnabled) ;
        
    }
}
