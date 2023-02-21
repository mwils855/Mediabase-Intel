package UpsDownsReports;


import java.util.*;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JComponent;
import java.io.File;
import org.apache.commons.csv.*;
import java.io.FileWriter;
import java.io.BufferedWriter;
import parser.UpsDownsParser;
/**
 * Write a description of class UpDownsClient here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class UpsDownsClient
{
    public static File upDownsFile;
    private String info;
    
    public void runUpDowns() throws IOException{
    	//JFileChooser chooser = new JFileChooser();
        //chooser.setMultiSelectionEnabled(true);
        //JComponent frame = null;
        //chooser.showOpenDialog(frame);
        //File[] files = chooser.getSelectedFiles();
    	File [] files = changeFiles();
        System.out.println("****************************\n* Ups Downs Maker v2.0 *****\n* Authored by: Mike Wilson *"
        		+ "\n****************************\n****************************");
        System.out.println();
        /*int columns = -1;
        while (columns == -1) {
        	columns = getChartType();
        }*/
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
            UpsDownsMaker udm = new UpsDownsMaker(path, formats);
            udm.printOneSongMultiFormat();
        }
        System.out.println("Thank you! Check Desktop for file UpDowns.html");
    }
    /*
    public void runCharts() throws IOException{
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        JComponent frame = null;
        chooser.showOpenDialog(frame);
        File[] files = chooser.getSelectedFiles();
        for (File f : files){
            String path = f.getAbsolutePath();
            ChartPositions cp = new ChartPositions();
            cp.printChartInfo(path);
        }
    }*/
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
            String name = getArtistAndRecordNameEZ(s);
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
    //Version 2.0 method
    public String getArtistAndRecordNameEZ(String file) throws IOException{
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
    
    //Version 1.0 method
    private String getArtistAndRecordName(String file) throws IOException{
        UpsDownsParser mp = new UpsDownsParser(file);
        CSVParser parser = mp.getParser();
        String name = "";
        int count = 0;
        for (CSVRecord record : parser){
            if (count > 1){
                break;
            }
            name = name + record.get(1) + "---";
            count += 1;
        }
        StringBuilder sb = new StringBuilder(name);
        for (int k = 0; k < sb.length(); k++){
            if (sb.charAt(k)==('/')){
                sb.setCharAt(k,' ');
            }
        }
        return sb.toString();
    }
    private void printDate() throws IOException {
        Calendar c = Calendar.getInstance();
        c.add(c.DAY_OF_YEAR, - 7);
        java.util.Date oldDate = c.getTime();
        String oldDateString = String.format("%1$tB %1$td %1$tY", oldDate);
        c.add(c.DAY_OF_YEAR, +6);
        java.util.Date currDate = c.getTime();
        String currDateString = String.format("%1$s %2$tB %2$td %2$tY"," - ", currDate);
        BufferedWriter myWriter = new BufferedWriter(new FileWriter(upDownsFile));
        //System.out.print("Mediabase Rolling Chart ");
        myWriter.write("<html><style> body{font-family: \"Times New Roman\", Times, serif;" +
            "font-size: 12pt;}</style>");
        myWriter.write("Mediabase Rolling Chart ");
        //System.out.printf(oldDateString);
        myWriter.write(oldDateString);
        //System.out.printf(currDateString);
        myWriter.write(currDateString);
        //System.out.println();
        myWriter.newLine();
        //System.out.println("(Minimum Spins This Week/Last Week: 3, +3/-3 variance). ");
        myWriter.write("<br>(Minimum Spins This Week/Last Week: 3, +3/-3 variance). <br><br>");
        //System.out.println();
        myWriter.close();
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
            myWriter.write("<br>(Minimum Spins This Week/Last Week: 3, +3/-3 variance). <br><br>");
            //System.out.println();
            myWriter.close();
            //System.out.println(chartType + " " + dates);
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
    public static void main (String[] args) throws IOException{
    	UpsDownsClient udc = new UpsDownsClient();
    	//udc.getChartType();
    	/*String name = udc.getArtistAndRecordNameEZ("/users/mikewilson/Desktop/met.csv");
    	System.out.println("Name: " + name);
    	udc.printDateAndChartType();*/
    	try {
			udc.runUpDowns();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
   

