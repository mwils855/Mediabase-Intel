package MediabaseReports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import parser.MediabaseParser;

public class PublishedChart{
	
	private ArrayList<ChartRecord> myChart;
	private String chartFormat;
	private String chartType;
	private int numDays;
	
	
	public PublishedChart(String file) {
		super();
		try {
			myChart = MediabaseParser.chartParser(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		String format = myChart.get(0).getFormat();
		String type = myChart.get(0).getChartType();
		this.setChartFormat(format);
		this.setChartType(type);
		setGainerRank();
		setVulnerable();
	}
	
	


	public String getChartFormat() {
		return chartFormat;
	}


	private void setChartFormat(String chartFormat) {
		this.chartFormat = chartFormat;
	}


	public String getChartType() {
		return chartType;
	}


	private void setChartType(String chartType) {
		this.chartType = chartType;
	}


	public int getNumDays() {
		return numDays;
	}


	private void setNumDays(int numDays) {
		this.numDays = numDays;
	}
	
	public ArrayList<ChartRecord> getMyChart() {
		return myChart;
	}
	private void setGainerRank() {
		ArrayList <ChartRecord> gg = this.getMyChart();
		Collections.sort(gg, new SortByGreatestGainer());
		int count = 1;
		for (ChartRecord cr : gg) {
			cr.setGainerRank(count);
			count += 1;
		}
		Collections.sort(gg, new SortByRankTW());
	}
	private void setVulnerable() {
		for (ChartRecord cr : this.myChart) {
			if (cr.getSpinsChange() > - 50 && cr.getSpinsChange() < 25) {
				cr.setVulnerable(true);
			}
		}
	}
	public void printChart() {
		for (ChartRecord cr : this.myChart) {
			System.out.println(cr.getArtist() + " " + cr.getTitle() + " LW " +
					cr.getRankLW() + " TW " + cr.getRankTW() + " " + cr.getSpinsChange());
		}
	}
	public List<ChartRecord> getVulernableAhead(ChartRecord record){
		List<ChartRecord> list = new ArrayList<ChartRecord>();
		for (ChartRecord cr : this.myChart) {
			if(cr.getRankTW() < record.getRankTW() && cr.isVulnerable()) {
				list.add(0, cr);
			}
		}	
		return list;
	}
	public List<ChartRecord> lostBullets(){
		List<ChartRecord> list = new ArrayList<ChartRecord>();
		for (ChartRecord cr : this.myChart) {
			if(cr.getSpinsChange() < 0 && cr.getRankTW() < 51) {
				list.add(cr);
			}
		}
		return list;
		
	}


	public static void main(String[] args) {
		//Chart c = new Chart("/Users/mikewilson/Desktop/Published Chart (1).csv");
		PublishedChart a = new PublishedChart("/Users/mikewilson/Desktop/Published Chart (2).csv");
		//Collections.sort(c.getMyChart(), new SortByChartJump());
		
		//for (ChartRecord cr : a.getMyChart()) {
			//System.out.println(cr.getArtist() + " " + cr.getTitle() + " " + cr.isBulleted());
			//if (cr.isVulnerable() && cr.isBulleted()) {
			//	System.out.println(cr.getRankTW() + " " + cr.getArtist() + " " + cr.getTitle() + " " + cr.getSpinsChange());
			//}
			//if (cr.getRankTW() < 50 && cr.getGainerRank() < 11) {
			//	System.out.println(cr.getRankLW() + " " + cr.getRankTW() + " " + cr.getGainerRank() + " " + cr.getArtist() + " " + cr.getTitle());
			//}
			//System.out.println(cr.getArtist() + " " + cr.getTitle() + " " + cr.getRankLW() + " " + cr.getRankTW() +" " +
			//		cr.getSpinsChange() + " " + cr.getGainerRank());
			
		//}
		//a.printChart();
		ArrayList<ChartRecord> records = a.getMyChart();
		for (ChartRecord record : records) {
			if (record.isVulnerable()) {
				System.out.println(record.getArtist() + " " + record.getTitle());
			}
		}
	}
	
}
