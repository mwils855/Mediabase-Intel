package MediabaseReports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import UpsDownsReports.UpsDownsMaker;
import parser.UpsDownsParser;

public class UpDownsClient {
	private Song[] songs;
	private String info;
	private File upDownsFile;
	
	public UpDownsClient() {
		try {
			runUpDowns();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void runUpDowns() throws IOException{
    	File [] files = changeFiles();
        System.out.println("****************************\n* Ups Downs Maker v2.0 *****\n* Authored by: Mike Wilson *"
        		+ "\n****************************\n****************************");
        System.out.println();
        files = orderChooser(files);
        upDownsFile = new File("/Users/mikewilson/Desktop/UpDowns.html");
        if (upDownsFile.createNewFile()){
            System.out.println("File created: " + upDownsFile.getName());
        }
        printDateAndChartType();
        for (File f : files){
            String path = f.getAbsolutePath();
            String[] formats = getFormats(f);
            //String [] formats = {"Alternative", "Active Rock", "Triple A"};
            while (formats[0] == null){
                formats = getFormats(f);
            }
            Song song = new Song(path);
            for (int i = 0; i < formats.length; i++) {
            	List<SongAnalysisRecord> recordsByFormat = song.getRecordsByFormat(formats[i]);
            	List<SongAnalysisRecord> relevantRecords = song.getRelevantSpinChangeRecords(recordsByFormat, 3);
            	SongAnalysisRecord record = song.getSongAnalysis().get(0);
            	writeToFile("<br><b><u>" + record.getArtist() + " \"" + record.getTitle() + "\" (" + formats[i] + ")</b></u>");
            	writeUpsAndDowns(relevantRecords);
            	List<SongAnalysisRecord> zeroSpinsRecords = song.getZeroSpinsYesterday(recordsByFormat);
            	writeZeroSpinsYesterday(zeroSpinsRecords);
            }
        }
        System.out.println("Thank you! Check Desktop for file UpDowns.html");
    }
	
	
	public File[] changeFiles() throws IOException{
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        JComponent frame = null;
        chooser.showOpenDialog(frame);
        File[] selectFiles = chooser.getSelectedFiles();
        File[] newFiles = new File[selectFiles.length];
        int pos = 0;
        for (File f : selectFiles){
            String s = f.getAbsolutePath();
            String name = getArtistAndRecordName(s);
            File newFile = new File("/users/mikewilson/Desktop/MediabaseCSVFiles/" + name + ".csv");
            boolean success = f.renameTo(newFile);
            if(success){
                System.out.println(newFile.getName());
                System.out.println("Success!");
                System.out.println();
                newFiles[pos] = newFile;
                pos += 1;
            }
            else{
                System.out.println("Fail!");
            }
        }
        return newFiles;
	}
	public String getArtistAndRecordName(String file) throws IOException{
    	UpsDownsParser udp = new UpsDownsParser(file);
    	CSVParser parser = udp.getParser();
    	String name = "";
    	List<CSVRecord> records = parser.getRecords();
    	String meta = records.get(1).get(0);
    	info = meta;
    	//System.out.println(meta);
    	int start = meta.indexOf("Current Song") + 14;
    	int end = meta.length();
    	//System.out.println(start + " " + end);
    	name = meta.substring(start, end);
    	return name;
    	
    }
	private File[] orderChooser(File [] files){
        for (int k = 0; k < files.length; k++){
            System.out.println("Select file for position: " + (k + 1));
            for (int j = k; j < files.length; j++){
                System.out.println("\t" + files[j].getName());
                }
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            for (int l = 0; l < files.length; l++){
                String fileName = files[l].getName().toLowerCase();
                if (fileName.indexOf(input) != -1){
                    File tempFile = files[k];
                    files[k] = files[l];
                    files [l] = tempFile;
                    break;
                }
                if (l == (files.length - 1)){
                    System.out.println("File not found. Please re-enter");
                    k = k - 1;
                }
            }
        }
        return files;
    }
	public void printDateAndChartType() throws IOException {
    	BufferedWriter myWriter = new BufferedWriter(new FileWriter(upDownsFile));
        
    		myWriter.write("<html><style> body{font-family: \"Times New Roman\", Times, serif;" +
                "font-size: 12pt;}</style>");
    		  String chartType = info.substring(13, 24).trim();
              int datesStart = info.indexOf("TW:") + 4;
    		  String dates = info.substring(datesStart, datesStart + 23);
    		myWriter.write("Mediabase " + chartType + " Chart ");
    		myWriter.write(dates);
            myWriter.newLine();
            //System.out.println("(Minimum Spins This Week/Last Week: 3, +3/-3 variance). ");
            myWriter.write("<br>(Minimum Spins This Week/Last Week: 3, +3/-3 variance). <br>");
            //System.out.println();
            myWriter.close();
            //System.out.println(chartType + " " + dates);
    }
	
	public int getChartType() {
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("What type of chart?\n\ta) Rolling\n\tb) 2-Day Building\n\tc) 3-Day Building\n\t"
    			+ "d) 4-Day Building\n\t e) 5-Day Building");
    	String input = scanner.nextLine();
    	if (input.equals("a")) {
    		return 0;
    	}
    	if (input.equals("b")) {
    		return 0;
    	}
    	if (input.equals("c")) {
    		return 0;
    	}
    	if (input.equals("d")) {
    		return 0;
    	}
    	if (input.equals("e")) {
    		return 0;
    	}
    	
    	return -1;
    }
	 private String[] getFormats(File f){
	        Scanner scanner = new Scanner(System.in);
	        System.out.println("What formats for file:  " + f.getName() + "?");
	        System.out.println("'a' = Alternative; 'r' = Active Rock; 't' = Triple A");
	        String input = scanner.nextLine();
	        String[] formats = new String[input.length()];
	        int pos = 0;
	        for(int k = 0; k < input.length(); k++){
	            if (input.charAt(k) == 'a'){
	                formats[pos] = "Alternative";
	                pos += 1;
	            }
	            if (input.charAt(k) == 'r'){
	                formats[pos] = "Active Rock";
	                pos +=1;
	            }
	            if (input.charAt(k) == 't'){
	                formats[pos] = "Triple A";
	                pos += 1;
	            }
	            if ("art".indexOf(String.valueOf(input.charAt(k))) == -1){
	                System.out.println("Error. Not valid formats. Try again ");
	                Arrays.fill(formats, null);
	                return formats;
	            }
	        }
	        return formats;
	   }
	 public void writeUpsAndDowns(List<SongAnalysisRecord> records) throws IOException {
		 writeToFile("<b>UPS</b>");
		 int count = 0;
		 for (int i = 0; i < records.size(); i++) {
			 SongAnalysisRecord record = records.get(i);
			 if (record.getSpinChange() > 0) {
				 writeToFile(formatStation(record.getStation()) + " +" + record.getSpinChange());
				 count += 1;
			 }
		 }
		 if (count == 0) {
			 writeToFile("N/A");
		 }
		 count = 0;
		 writeToFile("<br><b>DOWNS</b>");
		 for (int i = records.size() - 1; i >= 0; i--) {
			 SongAnalysisRecord record = records.get(i);
			 if (record.getSpinChange() < 0) {
				 writeToFile(formatStation(record.getStation()) + " " + record.getSpinChange());
				 count +=1;
			 }
		 }
		 if (count == 0) {
			 writeToFile("N/A");
		 }
	 }
	 public void writeZeroSpinsYesterday(List<SongAnalysisRecord> records) throws IOException {
		 writeToFile("<br><b>ZERO SPINS YESTERDAY</b>");
		 if (records.isEmpty()) {
			 writeToFile("N/A");
			 return;
		 }
		 String s = "";
		 for (SongAnalysisRecord record : records) {
			 s = s + formatStation(record.getStation()) + " ";
		 }
		 writeToFile(s);
	 }
	 
	 private String formatStation(String station) {
		 if (station.indexOf("FM") != -1) {
			 station = station.substring(0, 4);
		 }
		 return station;
	 }
	 
	 public void writeToFile(String input) throws IOException {
			BufferedWriter bw = new BufferedWriter(new FileWriter(upDownsFile, true));
			bw.write(input + "<br>");
			bw.close();
	 }
	 public static void main (String[] args) {
		 UpDownsClient udc = new UpDownsClient();
	 }
    
}
