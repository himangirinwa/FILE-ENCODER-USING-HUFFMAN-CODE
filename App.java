import java.util.Scanner;
class App{
   
    public static void main(String[] args){
        
        //getting fileName which has to be compressed
        Scanner in = new Scanner(System.in); 
        System.out.print("ENTER FILE NAME OR PATH I.E TO BE COMPRESSED : ");
        String fileName = in.nextLine();
        
        HuffmanCompressor encoder = new HuffmanCompressor(fileName);
        encoder.encodeFile();
        in.close();  
    }
}