package MediabaseReports;

public class StreamingRecord {
	
	private String format;
	private String artist;
	private String title;
	private String releaseYear;
	private int nationalRankTW;
	private int nationalStreamsTW;
	private int nationalRankLW;
	private int nationalStreamsLW;
	private double percentChange;
	private int streamChange;
	private long nationalYTDStreams;
	private long nationalATDStreams;
	
	public StreamingRecord() {
		format = "";
		artist = "";
		title = "";
		releaseYear = "";
		nationalRankTW = 0;
		nationalStreamsTW = 0;
		nationalRankLW = 0;
		nationalStreamsLW = 0;
		percentChange = 0.0;
		nationalYTDStreams = 0;
		nationalATDStreams = 0;
		streamChange = 0;
	}
	
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
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
	public String getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}
	public int getNationalRankTW() {
		return nationalRankTW;
	}
	public void setNationalRankTW(int nationalRankTW) {
		this.nationalRankTW = nationalRankTW;
	}
	public int getNationalStreamsTW() {
		return nationalStreamsTW;
	}
	public void setNationalStreamsTW(int nationalStreamsTW) {
		this.nationalStreamsTW = nationalStreamsTW;
	}
	public int getNationalRankLW() {
		return nationalRankLW;
	}
	public void setNationalRankLW(int nationalRankLW) {
		this.nationalRankLW = nationalRankLW;
	}
	public int getNationalStreamsLW() {
		return nationalStreamsLW;
	}
	public void setNationalStreamsLW(int nationalStreamsLW) {
		this.nationalStreamsLW = nationalStreamsLW;
	}
	public double getPercentChange() {
		return percentChange;
	}
	public void setPercentChange(double percentChange) {
		this.percentChange = percentChange;
	}
	public long getNationalYTDStreams() {
		return nationalYTDStreams;
	}
	public void setNationalYTDStreams(long l) {
		this.nationalYTDStreams = l;
	}
	public long getNationalATDStreams() {
		return nationalATDStreams;
	}
	public void setNationalATDStreams(long l) {
		this.nationalATDStreams = l;
	}
	public int getStreamChange() {
		return streamChange;
	}

	public void setStreamChange(int streamChange) {
		this.streamChange = streamChange;
	}

	@Override
	public String toString() {
		return "StreamingRecord [format=" + format + ", artist=" + artist + ", title=" + title + ", releaseYear="
				+ releaseYear + ", nationalRankTW=" + nationalRankTW + ", nationalStreamsTW=" + nationalStreamsTW
				+ ", nationalRankLW=" + nationalRankLW + ", nationalStreamsLW=" + nationalStreamsLW + ", percentChange="
				+ ", streamChange=" + streamChange + ", percentChange=" + percentChange + ", nationalYTDStreams=" + nationalYTDStreams + ", nationalATDStreams="
				+ nationalATDStreams + "]";
	}
	
	
}
