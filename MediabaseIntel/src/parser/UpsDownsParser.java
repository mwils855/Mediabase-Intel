package parser;

import org.apache.commons.csv.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class UpsDownsParser 
{
    private String myFile;
    
    public UpsDownsParser(String file){
        myFile = file;
    }
    
    public BufferedReader getReader(String s) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(s));
        return br;
    }
    public void printReader(){
        try{
        BufferedReader br = getReader("SongAnalysis.csv");
        String line = "";
        while((line = br.readLine())!= null){
            System.out.println(line);
        }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
    public CSVParser getParser() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(myFile));
        //***very important not to call readline() twice see below
        String s = null;
        String t = "";
        while ((s = br.readLine()) != null){
            t = t + s + "\n";
        }
        CSVParser parser = CSVParser.parse(t, CSVFormat.RFC4180);
        return parser;
    }
    public ArrayList<String[]> mediabaseParser(String file) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<String[]> records = new ArrayList<String[]>();
        String s = null;
        while ((s = br.readLine()) != null){
            //s = s.replaceAll("\"","");
            String[] record = s.split(",");
            records.add(record);
        }
        return records;
    }
    public void printAllRecords() throws IOException{
        CSVParser parser = getParser();
        for (CSVRecord record : parser){
            if (record.size() == 16){
                System.out.print(record.get(1).substring(0,4) + "\t");
                System.out.print(record.get(7) + "\t");
                System.out.println(record);
            }
        }
        
    }
    public void testMediabaseParser() throws IOException{
        ArrayList<String[]> records = mediabaseParser(myFile);
        for (String[] record : records){
            System.out.println(Arrays.toString(record));
        }
        String[] panic = records.get(5);
        for (String s : panic){
            System.out.println(s);
        }
        int x = Integer.parseInt(panic[17]) + 6;
        System.out.println(x);
    }
   
}
