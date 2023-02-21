package MediabaseReports;

import java.util.Comparator;

public class SortByTitle implements Comparator<StreamingRecord> {
	
	public int compare(StreamingRecord a, StreamingRecord b) {
		
		/*for (int i = 0; i < a.getTitle().length() || i < b.getTitle().length(); i++) {
			if ((int) a.getTitle().charAt(i) < (int)b.getTitle().charAt(i)) {
				return -1;
			}
			if (a.getTitle().charAt(i) > b.getTitle().charAt(i)) {
				return 1;
			}
		
		}
		return -1;*/
		return a.getTitle().toLowerCase().compareTo(b.getTitle().toLowerCase());
		
	}
}
