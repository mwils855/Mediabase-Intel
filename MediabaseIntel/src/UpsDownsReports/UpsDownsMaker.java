package UpsDownsReports;


import org.apache.commons.csv.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.io.*;
import parser.UpsDownsParser;

public class UpsDownsMaker 
{
    private String[] myFormats;
    private String myFile;
    
    public UpsDownsMaker(String file, String[] formats){
        myFormats = formats;
        myFile = file;
    }
    private ArrayList<CSVRecord> relevantUpDowns(CSVParser udp){
        ArrayList<CSVRecord> records = new ArrayList<CSVRecord>();
        /*for (CSVRecord record : udp) {
        	System.out.println(record.size());
        	System.out.println(record);
        }*/
       int count = 0;
       for (CSVRecord record : udp){
           count += 1; 
            if(count > 5 && record.size() == 29 && (Integer.parseInt(record.get(11)) >= 3 ||
                Integer.parseInt(record.get(11)) <= -3)){
                records.add(record);
                //System.out.println(record.get(0));
                }
        }
       	records.remove(records.size() -1 );
        Collections.sort(records, new SortByUpsDowns());
        
        return records;
    }
    private void printUps(String format) throws IOException{
        UpsDownsParser mp = new UpsDownsParser(myFile);
        CSVParser parser = mp.getParser();
        ArrayList<CSVRecord> records = relevantUpDowns(parser);
        int count = 0;
        BufferedWriter bw = new BufferedWriter(new FileWriter(UpsDownsClient.upDownsFile, true));
        //System.out.println("UPS");
        bw.write("<b>UPS</b><br>");
        for (int k = records.size() - 1; k > 0; k --){
            if (Integer.parseInt(records.get(k).get(11)) > 0 && records.get(k).get(5).equals(format)){
                //System.out.print(records.get(k).get(0).substring(0,4) + " ");
                //bw.write(records.get(k).get(0).substring(0,4) + " ");
            	bw.write(getStationLetters(records.get(k)) + " ");
                //System.out.println("+" + records.get(k).get(8));
                bw.write("+" + records.get(k).get(11) + "<br>");
                count += 1;
            }
        }
        if (count == 0){
            //System.out.println("N/A");
            bw.write("N/A<br>");
        }
        bw.write("<br>");
        bw.close();
    }
    private void printDowns(String format) throws IOException{
        UpsDownsParser mp = new UpsDownsParser(myFile);
        CSVParser parser = mp.getParser();
        ArrayList<CSVRecord> records = relevantUpDowns(parser);
        int count = 0;
        BufferedWriter bw = new BufferedWriter(new FileWriter(UpsDownsClient.upDownsFile, true));
        //System.out.println("DOWNS");
        bw.write("<b>DOWNS</b><br>");
        for (int k = 0; k < records.size(); k ++){
            if (Integer.parseInt(records.get(k).get(11)) < 0 && records.get(k).get(5).equals(format)){
                //System.out.print(records.get(k).get(0).substring(0,4) + " ");
                //bw.write(records.get(k).get(0).substring(0,4) + " ");
            	bw.write(getStationLetters(records.get(k)) + " ");
                //System.out.println(records.get(k).get(8));
                bw.write(records.get(k).get(11) + "<br>");
                count += 1;
            }
        }
        if (count == 0){
            //System.out.println("N/A");
            bw.write("N/A<br>");
        }
        bw.write("<br>");
        bw.close();
    }
    private void printZeroSpinsYest(String format) throws IOException{
        UpsDownsParser mp = new UpsDownsParser(myFile);
        CSVParser parser = mp.getParser();
        int count = 0;
        BufferedWriter bw = new BufferedWriter(new FileWriter(UpsDownsClient.upDownsFile, true));
        bw.write("<b>ZERO SPINS YESTERDAY</b><br>");
        //System.out.println("ZERO SPINS YESTERDAY");
        for (CSVRecord record : parser){
            if (record.size() == 29 && record.get(5).equals(format)
                && Integer.parseInt(record.get(18)) == 0 && 
                Integer.parseInt(record.get(26)) > 10){
                    //System.out.print(record.get(0).substring(0,4) + " ");
                    //bw.write(record.get(0).substring(0,4) + " ");
            		bw.write(getStationLetters(record) + " ");
                    count += 1;
                }
        }
        if (count == 0){
            //System.out.print("N/A");
            bw.write("N/A");
        }
        //System.out.println();
        bw.write("<br><br>");
        bw.close();
    }
    private String getStationLetters(CSVRecord record) {
    	String station = record.get(0);
    	if (station.contains("F2") || station.contains("F3")) {
    		station = station.substring(0, 7);
    	}
    	else if ("WKXM".indexOf(station.substring(0,1)) != -1){
    		station = station.substring(0, 4);
    	}
    	return station;
    }
    //format must be written "Alternative", "Active Rock", or "Triple A"
    public void printOneSongOneFormat(String format) throws IOException{
        String bandName = myFile.substring(myFile.lastIndexOf("/") + 1, myFile.indexOf("-"));
        String recordName = myFile.substring(myFile.indexOf("-") + 1, myFile.length() -4);
        BufferedWriter bw = new BufferedWriter(new FileWriter(UpsDownsClient.upDownsFile, true));
        //System.out.println(bandName + " " + "\""+ recordName + "\""+ " " + "(" + format + ")");
        bw.write("<b><u>" + bandName + " " + "\""+ recordName + "\""+ " " + "(" + format + ")</b></u><br>");
        bw.close();
        printUps(format);
        printDowns(format);
        //System.out.println();
        //System.out.println();
        printZeroSpinsYest(format);
        //System.out.println();
    }
    public void printOneSongMultiFormat() throws IOException{
        for(String s : myFormats){
            printOneSongOneFormat(s);
        }
    //CSVParser parser = mp.getParser();
    /*
    public void testRelevantUpDowns() throws IOException{
    }
        UpsDownsParser mp = new UpsDownsParser(myFile);
        ArrayList<CSVRecord> records = relevantUpDowns(parser);
        for(CSVRecord record : records){
            System.out.println(record);
            printDowns(format);
        }*/
    }
    public static void main(String[] args) throws IOException {
    	String[] rock = {"Active Rock"};
    	UpsDownsParser udp = new UpsDownsParser("/users/mikewilson/Desktop/met.csv");
    	UpsDownsMaker udm = new UpsDownsMaker("/users/mikewilson/Desktop/met.csv", rock);
    	ArrayList<CSVRecord> list = udm.relevantUpDowns(udp.getParser());
    	System.out.println(list.size());
    	for (CSVRecord record : list) {
    		System.out.println(record.get(0) + "\t" + record.get(8));
    	}
    	udm.printUps("Active Rock");
    	udm.printZeroSpinsYest("Active Rock");
    }
}

