package MediabaseReports;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import parser.MediabaseParser;

public class PlaylistRecord {
	private static int idGen = 1;
	private int id;
	private String station;
	private String format;
	private int stationRankTW;
	private String category;
	private int stationRankLW;
	private String artist;
	private String title;
	private String label;
	private int spinsTW;
	private int spinsLW;
	private int spinsChange;
	private int daypartOVN;
	private int daypartAMD;
	private int daypartMID;
	private int daypartPMD;
	private int daypartEVE;
	private int histSpins;
	private int formatRank;
	private int streamsTW;
	boolean vulnerable;
	
	
	public PlaylistRecord(int stationRankLW, int stationRankTW, String artist, String title,
			String label, int spinsTW, int spinsLW, int spinsChange, int daypartOVN, int daypartAMD, int daypartMID, int daypartPMD,
			int daypartEVE, int histSpins, int formatRank) {
		super();
		this.stationRankTW = stationRankTW;
		this.stationRankLW = stationRankLW;
		this.artist = artist;
		this.title = title;
		this.label = label;
		this.spinsTW = spinsTW;
		this.spinsLW = spinsLW;
		this.spinsChange = spinsChange;
		this.daypartOVN = daypartOVN;
		this.daypartAMD = daypartAMD;
		this.daypartMID = daypartMID;
		this.daypartPMD = daypartPMD;
		this.daypartEVE = daypartEVE;
		this.histSpins = histSpins;
		this.formatRank = formatRank;
		vulnerable = false;
		id = idGen;
		idGen += 1;
		streamsTW = 0;
		
	}


	public static int getIdGen() {
		return idGen;
	}


	public int getId() {
		return id;
	}


	public String getStation() {
		return station;
	}


	public int getStationRankTW() {
		return stationRankTW;
	}


	public int getStationRankLW() {
		return stationRankLW;
	}


	public String getArtist() {
		return artist;
	}


	public String getTitle() {
		return title;
	}


	public String getLabel() {
		return label;
	}


	public int getSpinsTW() {
		return spinsTW;
	}


	public int getSpinsLW() {
		return spinsLW;
	}


	public int getSpinsChange() {
		return spinsChange;
	}


	public int getDaypartAMD() {
		return daypartAMD;
	}


	public int getDaypartMID() {
		return daypartMID;
	}


	public int getDaypartPMD() {
		return daypartPMD;
	}


	public int getDaypartEVE() {
		return daypartEVE;
	}

	public int getHistSpins() {
		return histSpins;
	}


	public int getFormatRank() {
		return formatRank;
	}


	public boolean isVulnerable() {
		return vulnerable;
	}


	public int getDaypartOVN() {
		return daypartOVN;
	}


	public void setStation(String station) {
		this.station = station;
	}
	public PlaylistRecord getRecordByID(int id) {
		return this;
		
	}

	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getCategory() {
		return this.category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setStreamsTW(int streamsTW) {
		this.streamsTW = streamsTW;
	}
	public int getStreamsTW() {
		return streamsTW;
	}
	

	//haven't set category in toString
	@Override
	public String toString() {
		return "PlaylistRecord [id=" + id + ", station=" + station + ", format=" + format + ", stationRankTW=" + stationRankTW
				+ ", stationRankLW=" + stationRankLW + ", artist=" + artist + ", title=" + title + ", label=" + label
				+ ", spinsTW=" + spinsTW + ", spinsLW=" + spinsLW + ", spinsChange=" + spinsChange + ", daypartOVN="
				+ daypartOVN + ", daypartAMD=" + daypartAMD + ", daypartMID=" + daypartMID + ", daypartPMD="
				+ daypartPMD + ", daypartEVE=" + daypartEVE + ", histSpins=" + histSpins + ", formatRank=" + formatRank
				+ ", vulnerable=" + vulnerable + "]";
	}
	
	
}
