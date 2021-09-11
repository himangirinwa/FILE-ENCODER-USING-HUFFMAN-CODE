import java.util.*;
import java.io.*;


class HuffmanNode {
    int freq;
    char c;
    HuffmanNode left;
    HuffmanNode right;

    HuffmanNode(char c, int freq){
        this.c = c;
        this.freq = freq;
        left = right = null;
    }
}

//comparator interface helps to compare user defined object
class ImplementComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y) {
      return x.freq - y.freq;
    }
}

public class HuffmanCompressor {

    File file;
    //keeps count of frequency of every character
    HashMap<Character, Integer> freqCount = new HashMap<Character, Integer>();
    //keeps the huffman code for every character
    HashMap<Character, String> huffmanCode = new HashMap<Character, String>();
    PriorityQueue<HuffmanNode> que = new PriorityQueue<HuffmanNode>(new ImplementComparator());
    HuffmanNode Root = null;
    //this will be the root of our tree

    //constructor
    HuffmanCompressor(String fileName){

        file = new File(fileName);
    }


    //count the frequency of every character to create huffman tree
    void countCharFreq(){
        try{
            // file
            BufferedReader reader = new BufferedReader(new FileReader(file));
            
            int i; 
            //counting the frequency of every character   
            while((i=reader.read())!=-1){  
                char ch = (char)i;
                if(!freqCount.containsKey(ch)){
                    freqCount.put((char)i, 1);
                }else{
                    freqCount.put(ch, freqCount.get(ch)+1);
                }
            }  

            reader.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    //generating huffman tree
    void generateEncodingTree(){
        //getting all the nodes in the prority queue
        for(Map.Entry map : freqCount.entrySet()){    
            HuffmanNode node = new HuffmanNode((char)map.getKey(), (int)map.getValue());   
            // System.out.println(node.c + " " + node.freq);

            //adding node in the queue
            que.add(node);
        } 

        //get encoding tree
        while(que.size()>1){

            HuffmanNode x = que.peek();
            que.poll();

            HuffmanNode y = que.peek();
            que.poll();

            HuffmanNode node = new HuffmanNode('~', x.freq+y.freq);
            node.left = x;
            node.right = y;

            Root = node;

            que.add(node);
        }
    }

    //gives huffman code for every character in the file
    void getCode(HuffmanNode root, String s){
        if (root.left == null && root.right == null && root.c !=('~')) {
            huffmanCode.put(root.c, s);
            return;
          }
          getCode(root.left, s + "0");
          getCode(root.right, s + "1");
    }

    void writeEncodedFile(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter("ENCODED.TXT"));

            int i;
            while((i=reader.read())!= -1){
                char ch = (char) i;

                writer.write(huffmanCode.get(ch));
            }

            reader.close();
            writer.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    void encodeFile(){
        //counting total number of occurenceces of every character
        countCharFreq();

        //generating huffman encoding tree using calculated frequiences
        generateEncodingTree();

        //storing huffman code in a hashman for efficient encodinf
        getCode(Root, "");

        //wrting encoded data to new file (encoded.txt)
        writeEncodedFile();
    }
}
