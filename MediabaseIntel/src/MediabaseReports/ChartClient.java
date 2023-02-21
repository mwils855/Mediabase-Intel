package MediabaseReports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

public class ChartClient {
	
	private PublishedChart[] charts;
	public static File chartFile;
	
	public ChartClient() {
		selectCharts();
		chartFile = new File("/Users/mikewilson/Desktop/ChartFile.html");
		try {
			initializeFile(chartFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void initializeFile(File input) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(chartFile));
		 bw.write("<html><style> body{font-family: \"Times New Roman\", Times, serif;" +
		            "font-size: 12pt;}</style>");
		 bw.close();
	}
	
	public void selectCharts() {
		JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        JComponent frame = null;
        chooser.showOpenDialog(frame);
        File[] selectFiles = chooser.getSelectedFiles();
        charts = new PublishedChart[selectFiles.length];
        int pos = 0;
        for (File file : selectFiles) {
        	String filePath = file.getAbsolutePath();
        	PublishedChart chart = new PublishedChart(filePath);
        	charts[pos] = chart;
        	pos += 1;
        }
	}
	
	public List<ChartRecord> getRecordsAllFormats(){
		List<ChartRecord> masterList = new ArrayList<ChartRecord>();
		String[] formats = getFormat();
		System.out.println(Arrays.toString(formats));
		List<PublishedChart> selectCharts = getCharts(formats);
		for (PublishedChart chart : selectCharts) {
			List<ChartRecord> records = getRecordByFormat(chart);
			for (ChartRecord record : records) {
				masterList.add(record);
			}
		}
		return masterList;
	}
	
	public List<ChartRecord> getRecordByFormat(PublishedChart chart) {
		List<ChartRecord> myRecords = new ArrayList<ChartRecord>();
 		String done = "";
		while (!done.equals("done")) {
			Scanner scanner = new Scanner(System.in);
			ChartRecord record = getRecordByTitle(chart);
			if (record != null) {
				myRecords.add(record);
			}
			System.out.println("Are you finished selecting records?\nWrite 'done' if yes");
			done = scanner.next();
		}
		
		return myRecords;
	}
	public ChartRecord getRecordByTitle(PublishedChart chart) {
		System.out.println("Which record would you like to add from the " +
				chart.getChartFormat() + " chart?");
		Scanner scanner = new Scanner(System.in);
		String response = scanner.nextLine().toLowerCase();
		for (ChartRecord record : chart.getMyChart()) {
			if(record.getTitle().toLowerCase().indexOf(response) != -1) {
				System.out.println("Is " + record.getArtist() + " " + record.getTitle() + 
						" correct?\n \"yes\" or \"no\"");
				String yesOrNo = scanner.nextLine();
				if (yesOrNo.equals("yes")) {
					return record;
				}
			}
		}
		System.out.println("Record not found!!\n");
		return null;	
	}
	
	public List<PublishedChart> getCharts(String[] formats) {
		List<PublishedChart> chartlist = new ArrayList<PublishedChart>();
		for (int i = 0; i < formats.length; i++) {
			for (int j = 0; j < getCharts().length; j++) {
				if (formats[i].equals(getCharts()[j].getChartFormat())) {
				chartlist.add(charts[j]);
				}
			}
		}
		System.out.println(chartlist.size());
		return chartlist;
	}
	public String[] getFormat() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("What format would you like to work with?\n "
				+ "'a' for Alternative | 'r' for Active Rock | 't' for Triple A");
		String s = scanner.nextLine();
		String[] formats = new String[s.length()];
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == 'a') {
				formats[i] = "Alternative"; 
			}
			if (s.charAt(i) == 'r') {
				formats[i] = "Active Rock";
			}
			if (s.charAt(i) == 't') {
				formats[i] = "Triple A";
			}
		}
		return formats;
	}
		
	public void printCharts() {
		for (int i = 0; i < charts.length; i++) {
			charts[i].printChart();
		}
	}
	public List<ChartRecord> getVulernableAhead(ChartRecord record){
		List<ChartRecord> list = new ArrayList<ChartRecord>();
		for (int i = 0; i < charts.length; i++) {
			if(charts[i].getChartFormat().equals(record.getFormat())) {
				list = charts[i].getVulernableAhead(record);
			}
		}
		return list;
	}
	public List<ChartRecord> getLostBullets(String format){
		List<ChartRecord> list = new ArrayList<ChartRecord>();
		for (int i = 0; i < charts.length; i++) {
			if(charts[i].getChartFormat().equals(format)) {
				list = charts[i].lostBullets();
			}
		}
		return list;
	}
	
	public void printKeyStats(List<ChartRecord> records) {
		for (ChartRecord record : records) {
			System.out.println(record.getFormat());
			System.out.println(record.getArtist() + " " + record.getTitle());
			System.out.println("LW " + record.getRankLW() + " TW " + record.getRankTW() + " " +
					record.getSpinsChange());
			System.out.println("#" + record.getGainerRank() + " Greatest Gainer");
			System.out.println(record.getWeeksOn() + " weeks on the chart\n");
		}
	}
	public void writeKeyStats(List<ChartRecord> records) throws IOException {
		for (ChartRecord record : records) {
			writeToFile("-------------------------");
			writeToFile("<b>" + record.getArtist() + " " + record.getTitle() + " (" + record.getFormat() + ")</b>");
			writeToFile(" Mediabase LW " + record.getRankLW() + " TW " + record.getRankTW() + " " +
					"Spin Increase " + returnPlus(record) + record.getSpinsChange());
			writeToFile("<i>#" + record.getGainerRank() + " Greatest Gainer</i>");
			writeToFile(record.getWeeksOn() + " weeks on the chart\n");
			writeToFile(" ");
			writeChartRecordList(getVulernableAhead(record), "Vulnerable Records Ahead");
			writeToFile(" ");
			
		}
		writeChartRecordList(getLostBullets("Active Rock"), "Active Rock records that lost their bullet this week");
		writeToFile(" ");
	}
	private String returnPlus(ChartRecord record) {
		if (record.getSpinsChange() > 0) {
			return "+";
		}
		return "";
	}
	private void writeChartRecordList(List<ChartRecord> records, String input) throws IOException {
		writeToFile("<u>" + input + "</u>");
		for (ChartRecord record : records) {
			writeToFile(record.getArtist() + " " + record.getTitle() + " Mediabase LW " + record.getRankLW() + " TW " + record.getRankTW() + " " +
					"Spin Change " + returnPlus(record) + record.getSpinsChange() + " " + record.getWeeksOn() + " weeks on chart");
		}
	}
	public PublishedChart[] getCharts() {
		return charts;
	}
	public void writeToFile(String input) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(chartFile, true));
		bw.write(input + "<br>");
		bw.close();
	}
	public static void main (String[] args) throws IOException {
		ChartClient cc = new ChartClient();
		//cc.printCharts();
		List<ChartRecord> myRecords = cc.getRecordsAllFormats();
		//cc.printKeyStats(myRecords);
		cc.writeKeyStats(myRecords);
	}
	
}
