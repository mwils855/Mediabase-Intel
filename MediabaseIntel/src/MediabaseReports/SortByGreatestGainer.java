package MediabaseReports;

import java.util.Comparator;

public class SortByGreatestGainer implements Comparator<ChartRecord> {
	public int compare(ChartRecord a, ChartRecord b) {
		return b.getSpinsChange() - a.getSpinsChange();
		
	}
}
