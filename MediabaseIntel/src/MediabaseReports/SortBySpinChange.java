package MediabaseReports;

import java.util.Comparator;

public class SortBySpinChange implements Comparator<SongAnalysisRecord>{
	
	public int compare(SongAnalysisRecord a, SongAnalysisRecord b) {
		if (a.getSpinChange() > b.getSpinChange()) {
			return -1;
		}
		return 1;
	}
}
