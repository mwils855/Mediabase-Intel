package MediabaseReports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import parser.MediabaseParser;

public class RollingPlaylist {
	
	private List<PlaylistRecord> playlist;
	private String station;
	
	public RollingPlaylist(String file){
		
		MediabaseParser mp = new MediabaseParser();
		try {
			playlist = mp.playlistParser(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		station = playlist.get(0).getStation();
		assignCategories();
		
	}

	public List<PlaylistRecord> getPlaylist() {
		return playlist;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}
	
	public int[] getCategories(){
		List<PlaylistRecord> records = getPlaylist();
		int buffer = 6;
		List<int[]> breakers = detectCategoryBreaks(buffer);
		while (breakers.size() < 5) {
			buffer-= 1;
			if (buffer == 3) {
				break;
			}
			breakers = detectCategoryBreaks(buffer);
		}
		int[] breakerArray = new int[breakers.size()];
		int pos = 0;
		for (int [] breaker : breakers) {
			System.out.println("buffer amount: " + breaker[0] + " breaker position: " + breaker[1]);
			breakerArray[pos] = breaker[1];
			pos += 1;
		}
		return breakerArray;
	}
	private List<int[]> detectCategoryBreaks(int buffer) {
		ArrayList<int[]> breakers = new ArrayList<int[]>();
		int lastSpins = getPlaylist().get(0).getSpinsTW();
		for (PlaylistRecord pr : getPlaylist()) {
			int currSpins = pr.getSpinsTW();
			int breaker = lastSpins - currSpins;
			//TODO come up with a formula that more accurately breaks up categories
			if (breaker >= buffer && pr.getSpinsTW() > 6 && pr.getSpinsChange() < 5) {
				int[] pair = new int[2];
				pair[0] = breaker;
				pair[1] = pr.getStationRankTW();
				breakers.add(pair);
			}
			lastSpins = currSpins;
		}
		return breakers;
	}
	public void assignCategories() {
		int[] breakers = getCategories();
		if (breakers.length == 0) {
			breakers = new int[] {5, 15, 25};
		}
		String[] categories = {"Power", "Sub Power", "B", "C", "D"};
		System.out.println(Arrays.toString(breakers));
		System.out.println(Arrays.toString(categories));
		int pos = 0;
		for (PlaylistRecord record : getPlaylist()) {
			if (record.getStationRankTW() == breakers[pos] && pos < breakers.length - 1) {
				pos += 1;
			}
			if (record.getStationRankTW() < breakers[pos]) {
					record.setCategory(categories[pos]);
			}
			if (record.getStationRankTW() >= breakers[pos]) {
				record.setCategory(categories[pos + 1]);
			}
			if (record.getStationRankTW() == 0) {
				record.setCategory("N/A");
			}
			if (record.getSpinsTW() < 4 && record.getStationRankTW() != 0) {
				record.setCategory("Spike");
			}
		}
	}
		
		public void setStreamingInfo(StreamingChart stream) {
			List<StreamingRecord> streamList = stream.getStreamingChart();
			Collections.sort(streamList, new SortByTitle());
			for (PlaylistRecord record : playlist) {
				int index = binarySearchStreamingRecordList(streamList, record, streamList.size() - 1, 0);
				if (index >= 0) {
					StreamingRecord sr = streamList.get(index);
					int streamsTW = sr.getNationalStreamsTW();
					record.setStreamsTW(streamsTW);
					
				}
			}
		}
		public int binarySearchStreamingRecordList(List<StreamingRecord> list, PlaylistRecord record, int hi, int low) {
			int mid;
			String recordLower = record.getTitle().toLowerCase();
			if (low > hi) {
				if (list.get(hi).getArtist().toUpperCase().equals(record.getArtist().toUpperCase())){
					return hi;
				}
				if (low != list.size() && list.get(low).getArtist().toUpperCase().equals(record.getArtist().toUpperCase())) {
					return low;
				}
				else {
					return -1;
				}
			}	
			else {
				mid = (hi + low) / 2;
			}
			if (list.get(mid).getTitle().toLowerCase().equals(recordLower)) {
				return mid;
			}
			String a = list.get(mid).getTitle().toLowerCase();
			if (recordLower.compareTo(a) > 0) {
				return binarySearchStreamingRecordList(list, record, hi, mid + 1);
			}
			else {
				return binarySearchStreamingRecordList(list, record, mid - 1, low);
			}
					
	}
	public static void main(String[] args) {
		RollingPlaylist rp = new RollingPlaylist("/Users/mikewilson/Desktop/WFXH.csv");
		StreamingChart sc = new StreamingChart("/Users/mikewilson/Desktop/RockStreams.csv");
		rp.setStreamingInfo(sc);
		for (PlaylistRecord record : rp.getPlaylist()) {
			System.out.println(record.getArtist() + " " + record.getTitle() + ": " + record.getStreamsTW());
		}
	}
	

	
	
	
}
