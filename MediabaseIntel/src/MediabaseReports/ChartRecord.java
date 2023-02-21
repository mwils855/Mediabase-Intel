package MediabaseReports;

public class ChartRecord {
	
	private int id;
	private static int idGen = 1;
	private int peak;
	private String format;
	private String chartType;
	private int rankLW;
	private int rankTW;
	private boolean bulleted;
	private String artist;
	private String title;
	private String label;
	private int weeksOn;
	private int spinsTW;
	private int spinsLW;
	private int spinsChange;
	private int gainerRank;
	private int daypartOVN;
	private int daypartAMD;
	private int daypartMID;
	private int dayPartPMD;
	private int daypartEVE;
	private double impressionsTW;
	private double impressionsLW;
	private double impressionsChange;
	private int stationsOn;
	private int stationsNew;
	private boolean vulnerable;
	
	public ChartRecord(int peak, int rankLW, int rankTW, boolean bulleted, String artist, String title, String label,
			int weeksOn, int spinsTW, int spinsLW, int spinsChange, int daypartOVN, int daypartAMD, int daypartMID, int dayPartPMD,
			int daypartEVE, double impressionsTW, double impressionsLW, double impressionsChange, int stationsOn,
			int stationsNew) {
		super();
		this.peak = peak;
		this.rankLW = rankLW;
		this.rankTW = rankTW;
		this.bulleted = bulleted;
		this.artist = artist;
		this.title = title;
		this.label = label;
		this.weeksOn = weeksOn;
		this.spinsTW = spinsTW;
		this.spinsLW = spinsLW;
		this.spinsChange = spinsChange;
		this.daypartOVN = daypartOVN;
		this.daypartAMD = daypartAMD;
		this.daypartMID = daypartMID;
		this.dayPartPMD = dayPartPMD;
		this.daypartEVE = daypartEVE;
		this.impressionsTW = impressionsTW;
		this.impressionsLW = impressionsLW;
		this.impressionsChange = impressionsChange;
		this.stationsOn = stationsOn;
		this.stationsNew = stationsNew;
		this.vulnerable = false;
		this.id = idGen;
		idGen += 1;
	}
	public ChartRecord() {
		super();
		this.peak = 0;
		this.rankLW = 0;
		this.rankTW = 0;
		this.bulleted = false;
		this.artist = null;
		this.title = null;
		this.label = null;
		this.weeksOn = 0;
		this.spinsTW = 0;
		this.spinsLW = 0;
		this.spinsChange = 0;
		this.daypartOVN = 0;
		this.daypartAMD = 0;
		this.daypartMID = 0;
		this.dayPartPMD = 0;
		this.daypartEVE = 0;
		this.impressionsTW = 0;
		this.impressionsLW = 0;
		this.impressionsChange = 0;
		this.stationsOn = 0;
		this.stationsNew = 0;
		this.vulnerable = false;
		this.id = idGen;
		idGen += 1;
	}

	public int getId() {
		return id;
	}

	public static int getIdGen() {
		return idGen;
	}

	public int getRankLW() {
		return rankLW;
	}

	public int getRankTW() {
		return rankTW;
	}

	public boolean isBulleted() {
		return bulleted;
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

	public int getWeeksOn() {
		return weeksOn;
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

	public int getDaypartOVN() {
		return daypartOVN;
	}

	public int getDaypartAMD() {
		return daypartAMD;
	}

	public int getDayPartPMD() {
		return dayPartPMD;
	}

	public int getDaypartEVE() {
		return daypartEVE;
	}

	public double getImpressionsTW() {
		return impressionsTW;
	}

	public double getImpressionsLW() {
		return impressionsLW;
	}

	public double getImpressionsChange() {
		return impressionsChange;
	}

	public int getStationsOn() {
		return stationsOn;
	}

	public int getStationsNew() {
		return stationsNew;
	}

	public void setId(int id) {
		this.id = id;
	}
	public static void setIdGen(int idGen) {
		ChartRecord.idGen = idGen;
	}
	public void setPeak(int peak) {
		this.peak = peak;
	}
	public void setRankLW(int rankLW) {
		this.rankLW = rankLW;
	}
	public void setRankTW(int rankTW) {
		this.rankTW = rankTW;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void setWeeksOn(int weeksOn) {
		this.weeksOn = weeksOn;
	}
	public void setSpinsTW(int spinsTW) {
		this.spinsTW = spinsTW;
	}
	public void setSpinsLW(int spinsLW) {
		this.spinsLW = spinsLW;
	}
	public void setSpinsChange(int spinsChange) {
		this.spinsChange = spinsChange;
	}
	public void setDaypartOVN(int daypartOVN) {
		this.daypartOVN = daypartOVN;
	}
	public void setDaypartAMD(int daypartAMD) {
		this.daypartAMD = daypartAMD;
	}
	public void setDaypartMID(int daypartMID) {
		this.daypartMID = daypartMID;
	}
	public void setDayPartPMD(int dayPartPMD) {
		this.dayPartPMD = dayPartPMD;
	}
	public void setDaypartEVE(int daypartEVE) {
		this.daypartEVE = daypartEVE;
	}
	public void setImpressionsTW(double impressionsTW) {
		this.impressionsTW = impressionsTW;
	}
	public void setImpressionsLW(double impressionsLW) {
		this.impressionsLW = impressionsLW;
	}
	public void setImpressionsChange(double impressionsChange) {
		this.impressionsChange = impressionsChange;
	}
	public void setStationsOn(int stationsOn) {
		this.stationsOn = stationsOn;
	}
	public void setStationsNew(int stationsNew) {
		this.stationsNew = stationsNew;
	}
	public boolean isVulnerable() {
		return vulnerable;
	}

	public void setVulnerable(boolean vulnerable) {
		this.vulnerable = vulnerable;
	}
	
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
	public void setGainerRank(int gainerRank) {
		this.gainerRank = gainerRank;
	}

	public int getGainerRank() {
		return gainerRank;
	}

	public void setBulleted(boolean bulleted) {
		this.bulleted = bulleted;
	}

	@Override
	public String toString() {
		return "ChartRecord [id=" + id + ", format=" + format + ", chartType=" + chartType + ", rankLW=" + rankLW + ", rankTW=" + rankTW + ", bulleted=" + bulleted
				+ ", artist=" + artist + ", title=" + title + ", label=" + label + ", weeksOn=" + weeksOn + ", spinsTW="
				+ spinsTW + ", spinsLW=" + spinsLW + ", spinsChange=" + spinsChange + ", daypartOVN=" + daypartOVN
				+ ", daypartAMD=" + daypartAMD + ", dayPartPMD=" + dayPartPMD + ", daypartEVE=" + daypartEVE
				+ ", impressionsTW=" + impressionsTW + ", impressionsLW=" + impressionsLW + ", impressionsChange="
				+ impressionsChange + ", stationsOn=" + stationsOn + ", stationsNew=" + stationsNew + ", vulnerable="
				+ vulnerable + "]";
	}
	
	
	
	
	
}
