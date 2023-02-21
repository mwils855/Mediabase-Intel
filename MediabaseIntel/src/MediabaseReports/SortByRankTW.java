package MediabaseReports;

import java.util.Comparator;

public class SortByRankTW implements Comparator <ChartRecord>{
	public int compare(ChartRecord a, ChartRecord b) {
		return a.getRankTW() - b.getRankTW();
				
	}
}
