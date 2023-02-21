package MediabaseReports;

public class SongAnalysisRecord {
	
	private String artist;
	private String title;
	private String station;
	private String owner;
	private int rankTW;
	private int marketRank;
	private String market;
	private String format;
	private int spinsTW;
	private int spinsLW;
	private int spinChange;
	private int dayByDaySpins1DayAgo;
	private int spinsToDate;
	
	public SongAnalysisRecord() {
		artist = "";
		title = "";
		station = "";
		owner = "";
		rankTW = 0;
		marketRank = 0;
		market = "";
		format = "";
		spinsTW = 0;
		spinsLW = 0;
		spinChange = 0;
		dayByDaySpins1DayAgo = 0;
		spinsToDate = 0;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getRankTW() {
		return rankTW;
	}

	public void setRankTW(int rankTW) {
		this.rankTW = rankTW;
	}

	public int getMarketRank() {
		return marketRank;
	}

	public void setMarketRank(int marketRank) {
		this.marketRank = marketRank;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public int getSpinsTW() {
		return spinsTW;
	}

	public void setSpinsTW(int spinsTW) {
		this.spinsTW = spinsTW;
	}

	public int getSpinsLW() {
		return spinsLW;
	}

	public void setSpinsLW(int spinsLW) {
		this.spinsLW = spinsLW;
	}

	public int getSpinChange() {
		return spinChange;
	}

	public void setSpinChange(int spinChange) {
		this.spinChange = spinChange;
	}

	public int getDayByDaySpins1DayAgo() {
		return dayByDaySpins1DayAgo;
	}

	public void setDayByDaySpins1DayAgo(int dayByDaySpins1DayAgo) {
		this.dayByDaySpins1DayAgo = dayByDaySpins1DayAgo;
	}

	public int getSpinsToDate() {
		return spinsToDate;
	}

	public void setSpinsToDate(int spinsToDate) {
		this.spinsToDate = spinsToDate;
	}
	

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public String toString() {
		return "SongAnalysisRecord [artist=" + artist + ", title=" + title + ", station=" + station + ", owner=" + owner
				+ ", rankTW=" + rankTW + ", marketRank=" + marketRank + ", market=" + market + ", format=" + format
				+ ", spinsTW=" + spinsTW + ", spinsLW=" + spinsLW + ", spinChange=" + spinChange
				+ ", dayByDaySpins1DayAgo=" + dayByDaySpins1DayAgo + ", spinsToDate=" + spinsToDate + "]";
	}
	
	
}

