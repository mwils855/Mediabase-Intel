package MediabaseReports;

import java.util.Comparator;

public class SortByChartJump implements Comparator <ChartRecord>{
	public int compare(ChartRecord a, ChartRecord b) {
		int chartJumpA = a.getRankLW() - a.getRankTW();
		int chartJumpB = b.getRankLW() - b.getRankTW();
		return chartJumpB - chartJumpA;
	}
}
