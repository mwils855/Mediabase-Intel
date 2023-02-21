package MediabaseReports;

import java.io.IOException;
import java.util.List;

import parser.MediabaseParser;

public class StreamingChart {
	
	private List<StreamingRecord> streamingChart;
	private String format;
	
	public StreamingChart(String file) {
		MediabaseParser mp = new MediabaseParser();
		try {
			streamingChart = mp.streamingParser(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		format = streamingChart.get(0).getFormat();
	}

	public List<StreamingRecord> getStreamingChart() {
		return streamingChart;
	}
	public static void main(String[] args) {
		StreamingChart sc = new StreamingChart("/Users/mikewilson/Desktop/Metro Radio 01312023.csv");
		for (StreamingRecord sr : sc.getStreamingChart()) {
			System.out.println(sr);
		}
	}
	
}
