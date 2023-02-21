package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import MediabaseReports.ChartRecord;
import MediabaseReports.PlaylistRecord;
import MediabaseReports.PublishedChart;
import MediabaseReports.SongAnalysisRecord;
import MediabaseReports.StreamingRecord;

public class MediabaseParser {

	public List<PlaylistRecord> playlistParser(String file) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<PlaylistRecord> records = new ArrayList<PlaylistRecord>();
        String s = null;
        int count = 0;
        String station = "";
        String format = "";
        while ((s = br.readLine()) != null){
        	//System.out.println(s);
        	//s = removeCommas(s);
        	//System.out.println(s);
        	s = s.replaceAll(", ","zxzx");
        	s = s.replaceAll("\"","");
            
        	String[] recordData = s.split(",");
        	for (int k = 0; k < recordData.length; k++) {
        		recordData[k] = recordData[k].replaceAll("zxzx", ", ");
        	}
        	recordData = fillEmptyWithZero(recordData);
        	//System.out.println(Arrays.toString(record));
        	if(count == 1) {
        		int start = recordData[0].indexOf("Station: ") + 9;
        		int end = start + 7;
        		station = recordData[0].substring(start, end);
        		int formatStart = end + 1;
        		int formatEnd = recordData[0].indexOf("/", formatStart);
        		format = recordData[0].substring(formatStart, formatEnd);
        	}
        	
        	if (count > 4) {
	            PlaylistRecord pr = makePlaylistRecord (recordData);
	            pr.setStation(station);
	            pr.setFormat(format);
	            records.add(pr);
	            //System.out.println(pr.toString());
	        }
        count +=1;	
       
        }
        br.close();
        return records;
    }
	public List<StreamingRecord> streamingParser(String file) throws IOException {
		 BufferedReader br = new BufferedReader(new FileReader(file));
	        List<StreamingRecord> records = new ArrayList<StreamingRecord>();  
	        String s = null;
	        int count = 0;
	        String format = "";
	        HashMap<String, Integer> map = new HashMap<String, Integer>();
	        while ((s = br.readLine()) != null) {
	        	String[] recordData = s.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	        	recordData = fillEmptyWithZero(recordData);
	        	if (count == 7) {
	        		format = recordData[1];
	        	}
	        	if (count == 9) {
	        		map = getHeaderIndexes(recordData);
	        	}
	        	if (count > 9) {
	        		StreamingRecord record = makeStreamingRecord(recordData, map);
	        		record.setFormat(format);
	        		record.setNationalStreamsLW((int) (record.getNationalStreamsTW()/(1 + (record.getPercentChange()*0.01))));
	        		record.setStreamChange(record.getNationalStreamsTW() - record.getNationalStreamsLW());
	        		records.add(record);
	        	}
	        	count +=1;
	        }
	        return records;
	}
	public List<SongAnalysisRecord> songParser(String file) throws IOException {
		 BufferedReader br = new BufferedReader(new FileReader(file));
	        List<SongAnalysisRecord> songs = new ArrayList<SongAnalysisRecord>();  
	        String s = null;
	        int count = 0;
	        String artist = "";
	        String title = "";
	        String[] headers =  new String[50];
	        HashMap<String, Integer> map = new HashMap<String, Integer>();
	        while ((s = br.readLine()) != null) {
	        	String[] songData = s.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	        	removeQuotes(songData);
	        	songData = fillEmptyWithZero(songData);
	        	if (count == 1) {
	        		String meta = songData[0];
	        		int start = meta.indexOf("Current Song") + 14;
	            	int end = meta.length();
	            	String artistAndTitle = meta.substring(start, end);
	            	String[] artistAndTitleArray = artistAndTitle.split("-");
	            	artist = artistAndTitleArray[0];
	            	title = artistAndTitleArray[1];
	        	}
	            if (count == 3) {;
		    		headers = fillEmptyHeaders(songData);
		    		
		    	}
		    	if (count == 4) {
					 map = getHeaderIndexes(headers, songData);
		    	}
		    	if (count > 4) {
		    		SongAnalysisRecord song = makeSongAnalysisRecord(songData, map);
		    		song.setArtist(artist);
		    		song.setTitle(title);
		    		songs.add(song);
		    	}
		    	count += 1;
	    }
	    return songs;
	}
	
	public static ArrayList<ChartRecord> chartParser(String file) throws IOException {
		ArrayList<ChartRecord> chart = new ArrayList<ChartRecord>();
		BufferedReader br = new BufferedReader(new FileReader(file));
	    String s = null;
	    int count = 0;
	    String format = null;
	    String formatType = null;
	    String[] headers = new String[50];
	    HashMap<String, Integer> map = new HashMap<String, Integer>();
	    while ((s = br.readLine()) != null){
	    	s = s.replaceAll(", ","zxzx");
	    	s = s.replaceAll("\"","");
	    	String[] chartData = s.split(",");
	    	for (int k = 0; k < chartData.length; k++) {
	    		chartData[k] = chartData[k].replaceAll("zxzx", ", ");
	    	}
	    	chartData = fillEmptyWithZero(chartData);
	    	if(count == 1) {
	        	int formatStart = chartData[0].indexOf("Format: ") + 8;
	        	int formatEnd = chartData[0].indexOf("  ", formatStart + 1);
	        	format = chartData[0].substring(formatStart, formatEnd);
	        	int chartTypeStart = chartData[0].indexOf("Panel: ") + 7;
	        	int chartTypeEnd = chartData[0].indexOf("   ", chartTypeStart + 1);
	        	formatType = chartData[0].substring(chartTypeStart, chartTypeEnd);
	        	}
	    	if (count == 3) {;
	    		headers = fillEmptyHeaders(chartData);
	    		
	    	}
	    	if (count == 4) {
				 map = getHeaderIndexes(headers, chartData);
	    	}
	    	
	    	
	    	//System.out.println(headers.length);
	    	if (count > 4) {
	            ChartRecord cr = makeChartRecord (chartData, map);
	            cr.setFormat(format);
	            cr.setChartType(formatType);
	            chart.add(cr);
	            //System.out.println(cr.toString());
	    	}
	    	count += 1;
	    }
	    br.close();
	    /*for (String key : map.keySet()) {
			System.out.println(key + " " + map.get(key));
		}*/
		return chart;
	}
	private static String removeCommasAndQuotes(String s) {
		String result = s.replaceAll("\"", "");
		result = result.replaceAll(",", "");
		return result;
		
		
	}
	private static void removeQuotes(String[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = array[i].replaceAll("\"", "");
		}
	}
	//TODO change so that N/A makes a boolean identifying as recurrent
	public static String[] fillEmptyWithZero(String[] array) {
		for (int k = 0; k < array.length; k++) {
			if (array[k].equals("") || array[k].equals("N/A") || array[k].equals("-")){
				array[k] = "0";
			}
		}
		return array;
	}
	
	private static String[] fillEmptyHeaders(String[] array) {
		String header = "";
		for (int i = 0; i < array.length; i++) {
			if(array[i]!= "0") {
				header = array[i];
			}
			else {
				array[i] = header;
			}
		}
		return array;
	}
	public static HashMap<String, Integer> getHeaderIndexes(String[] header, String[] subheader){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < header.length; i++) {
			if (subheader[i].equals("0")) {
				subheader[i] = "";
			}
			map.put(header[i].trim() + subheader[i].trim(), i);
		}
		
		return map;
	}
	public static HashMap<String, Integer> getHeaderIndexes(String[] header){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < header.length; i++) {
			map.put(header[i].trim(), i);
		}
		
		return map;
	}
	
	private static ChartRecord makeChartRecord(String[] chart) {
		String numTotalAdds = chart[21].substring(0, chart[21].indexOf("/") - 1);
		String numNewAdds = chart[21].substring(chart[21].indexOf("/") + 2);
		ChartRecord cr = new ChartRecord(
				Integer.parseInt(chart[0]),
				Integer.parseInt(chart[1]),
				Integer.parseInt(chart[2]),
				false,
				chart[4],
				chart[5],
				chart[6],
				Integer.parseInt(chart[7]),
				Integer.parseInt(chart[8]),
				Integer.parseInt(chart[9]),
				Integer.parseInt(chart[10]),
				Integer.parseInt(chart[11]),
				Integer.parseInt(chart[12]),
				Integer.parseInt(chart[13]),
				Integer.parseInt(chart[14]),
				Integer.parseInt(chart[15]),
				Double.parseDouble(chart[16]),
				Double.parseDouble(chart[17]),
				Double.parseDouble(chart[18]),
				Integer.parseInt(numTotalAdds),
				Integer.parseInt(numNewAdds)
				);
		if (chart[3].equals("Yes")) {
			cr.setBulleted(true);
		}
		return cr;
	}
	public SongAnalysisRecord makeSongAnalysisRecord(String[] songData, HashMap<String, Integer> headers) {
		SongAnalysisRecord song = new SongAnalysisRecord();
		for (String key : headers.keySet()) {
			if (key.equals("Station")) {
				song.setStation(songData[headers.get(key)]);
			}
			if (key.equals("Owner")) {
				song.setOwner(songData[headers.get(key)]);
			}
			if (key.equals("Song Rk@Station(Currents)")) {
				song.setRankTW(Integer.parseInt(songData[headers.get(key)]));
			}
			if (key.equals("MarketRank")) {
				if (songData[headers.get(key)].equals("--")) {
					songData[headers.get(key)] = "0";
				}
				song.setMarketRank(Integer.parseInt(songData[headers.get(key)]));
			}
			if (key.equals("Owner")) {
				song.setOwner(songData[headers.get(key)]);
			}	
			if (key.equals("Market")) {
				song.setMarket(songData[headers.get(key)]);
			}	
			if (key.equals("Format")) {
				song.setFormat(songData[headers.get(key)]);
			}	
			if (key.equals("SpinsTW")) {
				song.setSpinsTW(Integer.parseInt(songData[headers.get(key)]));
			}
			if (key.equals("SpinsLW")) {
				song.setSpinsLW(Integer.parseInt(songData[headers.get(key)]));
			}
			if (key.equals("Spins+/-")) {
				song.setSpinChange(Integer.parseInt(songData[headers.get(key)]));
			}
			if (key.equals("Day By Day Spins-1")) {
				song.setDayByDaySpins1DayAgo(Integer.parseInt(songData[headers.get(key)]));
			}
			if (key.equals("Historical DataSpins To Date")) {
				song.setSpinsToDate(Integer.parseInt(songData[headers.get(key)]));
			}
		}
		
		return song;
	}
	
	public PlaylistRecord makePlaylistRecord (String[] record) {
		PlaylistRecord pr = new PlaylistRecord(
				Integer.parseInt(record[0]), 
				Integer.parseInt(record[1]), 
				record[2], 
				record[3],
				record[4], 
				Integer.parseInt(record[5]), 
				Integer.parseInt(record[6]), 
				Integer.parseInt(record[7]), 
				Integer.parseInt(record[8]),
				Integer.parseInt(record[9]), 
				Integer.parseInt(record[10]), 
				Integer.parseInt(record[11]),
				Integer.parseInt(record[12]),  
				Integer.parseInt(record[18]), 
				Integer.parseInt(record[19]));
				
				
		return pr;
		
		
	}
	private static StreamingRecord makeStreamingRecord(String[] chartData, HashMap<String, Integer> headers) {
		StreamingRecord record = new StreamingRecord();
		for (String key: headers.keySet()) {
			if (key.equals("Artist")) {
				record.setArtist(chartData[headers.get(key)].toUpperCase());
			}
			if (key.equals("Title")) {
				record.setTitle(chartData[headers.get(key)]);
			}
			if (key.equals("Release Year")) {
				record.setReleaseYear(chartData[headers.get(key)]);
			}
			if (key.equals("TW National Rank")) {
				record.setNationalRankTW(Integer.parseInt(chartData[headers.get(key)]));
			}
			if (key.equals("TW National Vol")) {
				chartData[headers.get(key)] = removeCommasAndQuotes(chartData[headers.get(key)]);
				record.setNationalStreamsTW(Integer.parseInt(chartData[headers.get(key)]));
			}
			if (key.equals("LW National Rank")) {
				record.setNationalRankLW(Integer.parseInt(chartData[headers.get(key)]));
			}
			if (key.equals("% Chg")) {
				record.setPercentChange(Double.parseDouble(chartData[headers.get(key)]));
			}
			if (key.equals("National YTD Vol")) {
				chartData[headers.get(key)] = removeCommasAndQuotes(chartData[headers.get(key)]);
				record.setNationalYTDStreams(Long.parseLong(chartData[headers.get(key)]));
			}
			if (key.equals("National ATD Vol")) {
				chartData[headers.get(key)] = removeCommasAndQuotes(chartData[headers.get(key)]);
				record.setNationalATDStreams(Long.parseLong(chartData[headers.get(key)]));
			}
		}
		return record;
	}
	
	private static ChartRecord makeChartRecord(String[] chartData, HashMap<String, Integer> headers) {
		ChartRecord record = new ChartRecord();
		for (String key: headers.keySet()) {
			if (key.equals("RankLW")) {
				record.setRankLW(Integer.parseInt(chartData[headers.get(key)]));
			}
			if (key.equals("RankTW")) {
				record.setRankTW(Integer.parseInt(chartData[headers.get(key)]));
			}
			if (key.equals("Title")) {
				record.setTitle(chartData[headers.get(key)]);
			}
			if (key.equals("Artist")) {
				record.setArtist(chartData[headers.get(key)]);
			}
			if (key.equals("Label")) {
				record.setLabel(chartData[headers.get(key)]);
			}
			//TODO need to fill in the rest of the headers and settters
			if (key.equals("PK")) {
				record.setPeak(Integer.parseInt(chartData[headers.get(key)]));
			}
			if (key.equals("Wks On")) {
				record.setWeeksOn(Integer.parseInt(chartData[headers.get(key)]));
			}
			if (key.equals("SpinsTW") || key.equals("Spins Within Date Range SpecifiedTW")) {
				record.setSpinsTW(Integer.parseInt(chartData[headers.get(key)]));
			}
			if (key.equals("SpinsLW") || key.equals("Spins Within Date Range SpecifiedTL")) {
				record.setSpinsLW(Integer.parseInt(chartData[headers.get(key)]));
			}
			if (key.equals("Spins+/-") || key.equals("Spins Within Date Range Specified+/-")) {
				record.setSpinsChange(Integer.parseInt(chartData[headers.get(key)]));
			}
			if (key.equals("DayPartsOVN")) {
				record.setDaypartOVN(Integer.parseInt(chartData[headers.get(key)]));
			}
			if (key.equals("DayPartsAMD")) {
				record.setDaypartAMD(Integer.parseInt(chartData[headers.get(key)]));
			}
			if (key.equals("DayPartsMID")) {
				record.setDaypartMID(Integer.parseInt(chartData[headers.get(key)]));
			}
			if (key.equals("DayPartsPMD")) {
				record.setDayPartPMD(Integer.parseInt(chartData[headers.get(key)]));
			}
			if (key.equals("DayPartsEVE")) {
				record.setDaypartEVE(Integer.parseInt(chartData[headers.get(key)]));
			}
			if (key.equals("ImpressionsTW")) {
				record.setImpressionsTW(Double.parseDouble(chartData[headers.get(key)]));
			}
			if (key.equals("ImpressionsLW")) {
				record.setImpressionsLW(Double.parseDouble(chartData[headers.get(key)]));
			}
			if (key.equals("Impressions+/-")) {
				record.setImpressionsChange(Double.parseDouble(chartData[headers.get(key)]));
			}
			if (key.equals("StationsOn/New")) {
				String numTotalAdds = chartData[headers.get(key)].substring(0, chartData[headers.get(key)].indexOf("/") - 1);
				String numNewAdds = chartData[headers.get(key)].substring(chartData[headers.get(key)].indexOf("/") + 2);
				record.setStationsOn(Integer.parseInt(numTotalAdds));
				record.setStationsNew(Integer.parseInt(numNewAdds));
			}
			
			
			
		}
		return record;
	}
	public static void renameAndMoveFiles(File file) throws IOException {
		String fileName = file.getAbsolutePath();
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String fileType = reader.readLine();
		String info = reader.readLine();
		String newFileName = "";
		if (fileType.indexOf("Station Playlist") != -1) {
			newFileName = makePlaylistFileName(info);
		}
		if (fileType.indexOf("Published Chart") != -1) {
			newFileName = makePublishedChartFileName(info);
		}
		File newFile = new File(newFileName);
		boolean success = file.renameTo(newFile);
		if (!success) {
			System.out.println("**Fail: " + newFile);
		}
		
	}
	private static String makePlaylistFileName(String info) {
		int stationStart = info.indexOf("Station:") + 9;
		String station = info.substring(stationStart, stationStart + 7);
		int dateStart = info.indexOf("TW: ") + 4;
		int dateEnd = info.indexOf("  ", dateStart);
		String date = info.substring(dateStart, dateEnd);
		date = date.replaceAll("/", "");
		String fileName = station + date;
		String newDirectory = "StationPlaylists_" + date;
		boolean addDirectory = new File("/users/mikewilson/Desktop/Radio Station Insights/*Old Station CSVs/" + newDirectory).mkdir();
		return "/users/mikewilson/Desktop/Radio Station Insights/*Old Station CSVs/" + newDirectory + "/" + fileName + ".csv";
	}
	private static String makePublishedChartFileName(String info) {
		int formatStart = info.indexOf("Format:") + 8;
		int formatEnd = info.indexOf("  ", formatStart);
		int dateStart = info.indexOf("Ending:") + 8 ;
		int dateEnd = info.indexOf("  ", dateStart);
		String format = info.substring(formatStart, formatEnd);
		String date = info.substring(dateStart, dateEnd);
		date = date.replaceAll("/", "");
		String fileName = format + date;
		String newDirectory = "PublishedChart_" + date;
		boolean addDirectory = new File("/users/mikewilson/Desktop/Radio Station Insights/*Old Published Chart CSVs/" + newDirectory).mkdir();
		return "/users/mikewilson/Desktop/Radio Station Insights/*Old Published Chart CSVs/" + newDirectory + "/" + fileName + ".csv";
	}
	
	/*public void testMediabaseParser(String file) throws IOException{
	    ArrayList<String[]> records = mediabaseParser(file);
	    for (String[] record : records){
	        System.out.println(Arrays.toString(record));
	    }
	    String[] panic = records.get(5);
	    for (String s : panic){
	        System.out.println(s);
	    }
	    int x = Integer.parseInt(panic[17]) + 6;
	    System.out.println(x);
	}*/
	public void testMediabaseParser(String file) throws IOException {
		List<PlaylistRecord> records = playlistParser(file);
		for (PlaylistRecord record : records) {
			System.out.println(record.toString());
		}
	}
	public void testChartParser(String file) throws IOException {
		ArrayList<ChartRecord> chart = chartParser(file);
		for (ChartRecord c : chart) {
			System.out.println(c.toString());
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MediabaseParser mp = new MediabaseParser();
		/*try {
			mp.testChartParser("/Users/mikewilson/Desktop/Building Chart (2).csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*String[] header = {"Rank","","Artist", "Title", "Label", "Impressions in Mill","","",""};
		header = MediabaseParser.fillEmptyHeaders(header);
		String[] subheader = {"TW", "LW","", "", "", "", "TW", "LW", "+/-", "rTW"};
		HashMap<String, Integer> maptest = MediabaseParser.getHeaderIndexes(header, subheader);
		for (String key : maptest.keySet()) {
			System.out.println(key + "--" + maptest.get(key));
		}*/
		try {
			List<SongAnalysisRecord> songs =mp.songParser("/Users/mikewilson/Desktop/METALLICA-Lux Aeterna.csv");
			for (SongAnalysisRecord song : songs) {
				System.out.println(song);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
