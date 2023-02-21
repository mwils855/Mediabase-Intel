package MediabaseReports;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import parser.MediabaseParser;

public class Song {
	
	private List<SongAnalysisRecord> songAnalysis;
	
	public Song(String file) {
		
		MediabaseParser mp = new MediabaseParser();
		try {
			songAnalysis = mp.songParser(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<SongAnalysisRecord> getSongAnalysis() {
		return songAnalysis;
	}
	
	public List<SongAnalysisRecord> getRecordsByFormat(String format){
		List<SongAnalysisRecord> list = new ArrayList<SongAnalysisRecord>();
		for (SongAnalysisRecord record : songAnalysis) {
			if(record.getFormat().equals(format)) {
				list.add(record);
			}
		}
		return list;
	}
	public List<SongAnalysisRecord> getRelevantSpinChangeRecords(List<SongAnalysisRecord> records, int threshold){
		List<SongAnalysisRecord> list = new ArrayList<SongAnalysisRecord>();
		for (SongAnalysisRecord record : records) {
			if (record.getSpinChange() >= threshold || record.getSpinChange() <= (threshold * -1)) {
				list.add(record);
			}
		}
		Collections.sort(list, new SortBySpinChange());
		return list;
	}
	public List<SongAnalysisRecord> getZeroSpinsYesterday(List<SongAnalysisRecord> records) {
		List<SongAnalysisRecord> list = new ArrayList<SongAnalysisRecord>();
		for (SongAnalysisRecord record : records) {
			if (record.getDayByDaySpins1DayAgo() == 0 && record.getSpinsToDate() >= 10) {
				list.add(record);
			}
		}
		return list;
	}
	
	
}
