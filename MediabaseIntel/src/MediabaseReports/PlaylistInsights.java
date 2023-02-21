package MediabaseReports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

import parser.MediabaseParser;



public class PlaylistInsights {
	
	private RollingPlaylist[] playlists;
	private PublishedChart myChart;
	private List<String> myArtists;
	
	public PlaylistInsights() {
		myArtists = new ArrayList<String>();
	}
	
	
	public void selectChart() throws IOException {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		JComponent frame = null;
		chooser.showOpenDialog(frame);
		File file = chooser.getSelectedFile();
		String filePath = file.getAbsolutePath();
		myChart = new PublishedChart(filePath);
		MediabaseParser.renameAndMoveFiles(file);
		
	}
	public void selectPlaylists() throws IOException {
		JFileChooser chooser = new JFileChooser();
	    chooser.setMultiSelectionEnabled(true);
	    JComponent frame = null;
	    chooser.showOpenDialog(frame);
	    File[] selectFiles = chooser.getSelectedFiles();
	    playlists = new RollingPlaylist[selectFiles.length];
	    int pos = 0;
	    for (File file : selectFiles) {
	    	String filePath = file.getAbsolutePath();
	    	RollingPlaylist playlist = new RollingPlaylist(filePath);
	    	playlists[pos] = playlist;
	    	MediabaseParser.renameAndMoveFiles(file);
	    	pos += 1;
	    }
	}
	public void loadChartAndPlaylists() throws IOException {
		System.out.println("Please upload chart");
		selectChart();
		System.out.println("Please upload playlists");
		selectPlaylists();
	}
	public ChartRecord getRecord(PublishedChart chart) throws IOException {
		System.out.println("\nWhat record do you want to analyze?\n" +
				"Enter \"list records\" to show all records\nEnter \"write playlist\" to write playlist to .html file");
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		if (s.equals("list records")) {
			printChartRecords();
			return null;
		}	
		if (s.equals("write playlist")) {
			writePlaylists();
			return null;
		}
		//System.out.println(s);
		for (ChartRecord record : chart.getMyChart()) {
			//System.out.println(record.getArtist());
			if ((record.getTitle().toLowerCase()).indexOf(s.toLowerCase()) != -1) {
				System.out.println("Is " + record.getArtist() + " " + record.getTitle() +
						" correct?\nEnter: \"yes\" if correct");
				s = scanner.nextLine();
				if (s.equals("yes")) {
					//System.out.println(record);
					return record;
				}
			}
		}
		return null;
	}
	private void printChartRecords() {
		for (ChartRecord record: myChart.getMyChart()) {
			System.out.println(record.getArtist() + " " + record.getTitle());
		}
	}
	public void printPlaylistRank(ChartRecord record) {
		for (RollingPlaylist playlist : playlists) {
			PlaylistRecord plr = getPlaylistRecord(record, playlist);
			if (plr != null) {
				System.out.println(plr.getStation() + " " + 
						plr.getArtist() + " " + plr.getTitle() + " Station rank: " +
						plr.getStationRankTW());
			}
		}
	}
	public void printPeakedRecordsAbove(ChartRecord chartRecordA) {
		List<ChartRecord> lostBullets = myChart.lostBullets();
		System.out.println("\nPeaked records above: " + chartRecordA.getArtist() + " " + chartRecordA.getTitle());
		for (RollingPlaylist playlist : playlists) {
			PlaylistRecord prA = getPlaylistRecord(chartRecordA, playlist);
			System.out.println("\n" + playlist.getStation());
			for (ChartRecord lostBulletRecord : lostBullets) {
				PlaylistRecord prB = getPlaylistRecord(lostBulletRecord, playlist);
				if (prA != null && prB != null) {
					if (prA.getStationRankTW() > prB.getStationRankTW() && prA.getFormatRank() < prB.getFormatRank() && prB.getStationRankTW() != 0) {
						System.out.println(prB.getArtist() + " " + prB.getTitle());
						//System.out.println("A rank TW: " + prA.getStationRankTW() + "B Rank TW: " + prB.getStationRankTW());
						}
					}
				if (prA == null & prB != null) {
					System.out.println(prB.getArtist() + " " + prB.getTitle());
				}
			}
		}
	}
	public void printKeyStats() throws IOException {
		ChartRecord record = null;
		while(record == null) {
			record = getRecord(myChart);
		}
		printPlaylistRank(record);
		printPeakedRecordsAbove(record);
		RecordsThatAreOutperforming(record);
		//OutperformingRecords(record);
		
	}
	public void initializeFiles() throws IOException {
		for (RollingPlaylist playlist : playlists) {
			String station = playlist.getStation();
			String directory = "/users/mikewilson/Desktop/Radio Station Insights/" + station + ".html";
			File file = new File(directory);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			 bw.write("<html><style> body{font-family: \"Times New Roman\", Times, serif;" +
			            "font-size: 12pt;}</style>");
			 bw.write("<h2>Station: " + station + "</h2>");
			 bw.close();
		}
		 
	}
	public void writeKeyStats() throws IOException {
		ChartRecord record = null;
		while(record == null) {
			record = getRecord(myChart);
		}
		myArtists.add(record.getArtist());
		for (RollingPlaylist playlist : playlists) {
			String station = playlist.getStation();
			String directory = "/users/mikewilson/Desktop/Radio Station Insights/" + station + ".html";
			File file = new File(directory);
			writeToFileWithoutBreak("<h3><b>" + record.getArtist() + " " + record.getTitle() + "</h3></b>", file);
			writePlaylistRank(record, playlist, file);
			PlaylistRecord plr = getPlaylistRecord(record, playlist);
			writeRecordsBySameArtist(plr, playlist, file);
			writePeakedRecordsAbove(plr, playlist, file);
			writeRecordsThatAreOutperforming(plr, record, playlist, file);
			//writePlaylist(plr, playlist, file);
		}
	}
	public void writePlaylistRank(ChartRecord record, RollingPlaylist playlist, File file) throws IOException {		
		PlaylistRecord plr = getPlaylistRecord(record, playlist);
		if (plr == null) {
			return;
		}
		if (plr.getStationRankTW() == 0) {
			writeToFile("**Record has no spins<br>", file);
		}
		
		else {
			writeToFile(( 
					" Station rank: " +
					plr.getStationRankTW()) + "; Spins TW: " + plr.getSpinsTW() + " ; Category: " + plr.getCategory() + "<br>", file);
			}
		
	}
	public void writeRecordsBySameArtist(PlaylistRecord record, RollingPlaylist playlist, File file) throws IOException {
		if (record == null) {
			return;
		}
		String output = "    Also playing by " + record.getArtist() + ": ";
		int count = 0;
		for (PlaylistRecord plr : playlist.getPlaylist()) {
			if (plr.getArtist().equals(record.getArtist()) && !plr.getTitle().equals(record.getTitle())){
				output = output + "<br>" + plr.getTitle() + "- Station Rank: " + plr.getStationRankTW() + "; Category: " + plr.getCategory() + "; Spins TW: " + plr.getSpinsTW();
				count += 1;
			}
		}
		if (count == 0) {
			writeToFile("Station is playing no other titles by " + record.getArtist() + "<br>", file);
			return;
		}
		writeToFile(output + "<br>", file);
	}
	
	public void writePeakedRecordsAbove(PlaylistRecord prA,  RollingPlaylist playlist, File file) throws IOException {
		if (prA == null) {
			return;
		}
		List<ChartRecord> lostBullets = myChart.lostBullets();
		writeToFileWithoutBreak("\n<b><u>PEAKED RECORDS</b> above: " + prA.getArtist() + " " + prA.getTitle() + "</u><br><table>", file );
		int count = 0;
		for (ChartRecord lostBulletRecord : lostBullets) {
			PlaylistRecord prB = getPlaylistRecord(lostBulletRecord, playlist);
			if (prA != null && prB != null) {
				if (prA.getStationRankTW() > prB.getStationRankTW() && prA.getFormatRank() < prB.getFormatRank() && prB.getStationRankTW() != 0) {
					writeToFileWithoutBreak("<td><b>" + prB.getArtist() + "  </td></b><td>" + " " + prB.getTitle() + "  </td><td>  Rank:  " + prB.getStationRankTW() + "</td><td>;<b>  Category - </b><i>" + prB.getCategory() + "</i></td><td>; Spins TW: " + prB.getSpinsTW() + "</td></tr>", file);					
					count += 1;
				}
			}
				if (prA == null & prB != null) {
					System.out.println(prB.getArtist() + " " + prB.getTitle());
				}
			}
		if (count == 0) {
			writeToFile("No Peaked Records ahead<br>", file);
		}
		else {
			writeToFileWithoutBreak("</table><br>", file);
		}
		
	}
	public void writeRecordsThatAreOutperforming(PlaylistRecord recordA, ChartRecord cr, RollingPlaylist playlist, File file) throws IOException {
			int count = 0;
			writeToFileWithoutBreak("<u>Records that are <b>OUTPERFORMING</b>: " + cr.getArtist() + " " + cr.getTitle() +"</u></br><table>", file);
			for (PlaylistRecord recordB : playlist.getPlaylist()) {
				if (recordA == null) {
					writeToFile("<i>***Record not added at " + playlist.getStation() + "</i>", file);
					break;
				}
				if (recordB.getFormatRank() > recordA.getFormatRank() && (recordB.getSpinsTW() - recordA.getSpinsTW()) > 0  && recordB.getStationRankTW() != 0){
					writeToFileWithoutBreak("<tr><td><b>" + recordB.getArtist() + "  </b></td><td> " + recordB.getTitle() + "  </td><td><b>Category -</b><i> " + recordB.getCategory() + "</td><td></i> ;  Spin Difference: " + (recordB.getSpinsTW() - recordA.getSpinsTW()) + "</td></tr>", file);
					//System.out.println("Record A rank: " + plr.getFormatRank() + " Record B Rank: " + pr.getStationRankTW());
					count += 1;
				}
			}
			if (count == 0 && recordA != null) {
				writeToFile("<i>No records are outperforming " + recordA.getTitle() + "</i>", file);
			}
			else {
				writeToFileWithoutBreak("</table></br>", file);
			}
	}
	public void writePlaylists() throws IOException {
		for (RollingPlaylist playlist : playlists) {
			String station = playlist.getStation();
			String directory = "/users/mikewilson/Desktop/Radio Station Insights/" + station + ".html";
			File file = new File(directory);
			writePlaylist(playlist, file);
		}
		System.out.println("Playlists have been written!");
	}
	
	
	public void writePlaylist(RollingPlaylist playlist, File file) throws IOException {
		writeToFileWithoutBreak("<br><table><tr><b><th>Rank LW</th><th>Rank TW</th><th>Artist</th><th>Title</th><th>Category</th><th>Spins TW</th>" +
				"<th>Spins LW</th><th>+/-</th><th>Spins To Date</th></b></tr>", file);
		String info = "";
		for (PlaylistRecord plr : playlist.getPlaylist()) {
			info = "<td>" + plr.getStationRankLW() + "</td><td>" + plr.getStationRankTW() + "</td><td>" + plr.getArtist() + "</td><td>" + 
					plr.getTitle() + "</td><td>" + plr.getCategory() + "</td><td>" + plr.getSpinsTW() + "</td><td>" + plr.getSpinsLW() +
					"</td><td>" + plr.getSpinsChange() + "</td><td>" + plr.getHistSpins() + "</td>";
			//TODO need to fix bolding myArtists
			for (String s : myArtists) {
				if (s.equals(plr.getArtist())){
					info = "<td><b><i>" + plr.getStationRankLW() + "</b></i></td><td><b><i>" + plr.getStationRankTW() + "</b></i></td><td><b><i>" + plr.getArtist() + "</b></i></td><td><b><i>" + 
							plr.getTitle() + "</b></i></td><td><b><i>" + plr.getCategory() + "</b></i></td><td><b><i>" + plr.getSpinsTW() + "</b></i></td><td><b><i>" + plr.getSpinsLW() +
							"</b></i></td><td><b><i>" + plr.getSpinsChange() + "</b></i></td><td><b><i>" + plr.getHistSpins() + "</b></i></td>";
				}
			}
			writeToFileWithoutBreak("<tr>" + info + "</tr>", file);
		}
		writeToFileWithoutBreak("</table>", file);
	}
	
	public void writeToFile(String input, File file) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		bw.write(input + "<br>");
		bw.close();
	}
	public void writeToFileWithoutBreak(String input, File file) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		bw.write(input);
		bw.close();
	}
	
	
	public void RecordsThatAreOutperforming(ChartRecord record) {
		for (RollingPlaylist playlist : playlists) {
			PlaylistRecord pr = getPlaylistRecord(record, playlist);
			System.out.println("\nStation: " + playlist.getStation() + findRecordsBySameArtist(pr, playlist));
			int count = 0;
			System.out.println("\nRecords Outperforming: " + record.getArtist() + " " + record.getTitle());
			for (PlaylistRecord plr : playlist.getPlaylist()) {
				if (pr == null) {
					System.out.println("Record not added at " + playlist.getStation());
					break;
				}
				if (plr.getFormatRank() > pr.getFormatRank() && plr.getStationRankTW() < pr.getStationRankTW()  && plr.getStationRankTW() != 0){
					System.out.println(plr.getArtist() + " " + plr.getTitle() + "\tSpin Difference: " + (plr.getSpinsTW() - pr.getSpinsTW()));
					//System.out.println("Record A rank: " + plr.getFormatRank() + " Record B Rank: " + pr.getStationRankTW());
					count += 1;
				}
			}
			if (count == 0 && pr != null) {
				System.out.println("No records are outperforming " + pr.getTitle());
			}
		
		}
	}
	//TODO write a method showing records that we are outperforming
	public void OutperformingRecords(ChartRecord record) {
		for (RollingPlaylist playlist : playlists) {
			PlaylistRecord pr = getPlaylistRecord(record, playlist);
			System.out.println("\nStation: " + playlist.getStation() + findRecordsBySameArtist(pr, playlist));
			int count = 0;
			System.out.println("\nRecords that " + record.getArtist() + " " + record.getTitle() + " is outperforming: ");
			for (PlaylistRecord plr : playlist.getPlaylist()) {
				if (pr == null) {
					System.out.println("Record not added at " + playlist.getStation());
					break;
				}
				if (plr.getFormatRank() < pr.getFormatRank() && plr.getStationRankTW() > pr.getStationRankTW()  && plr.getStationRankTW() != 0){
					System.out.println(plr.getArtist() + " " + plr.getTitle() + "\tSpin Difference: " + (plr.getSpinsTW() - pr.getSpinsTW()));
					//System.out.println("Record A rank: " + plr.getFormatRank() + " Record B Rank: " + pr.getStationRankTW());
					count += 1;
				}
			}
			if (count == 0 && pr != null) {
				System.out.println(pr.getTitle() + " is not outperforming any records");
			}
		
		}
	}
	private String findRecordsBySameArtist(PlaylistRecord record, RollingPlaylist playlist) {
		if (record == null) {
			return "";
		}
		String output = "    Also playing by " + record.getArtist() + ": ";
		int count = 0;
		for (PlaylistRecord plr : playlist.getPlaylist()) {
			if (plr.getArtist().equals(record.getArtist()) && !plr.getTitle().equals(record.getTitle())){
				output = output + plr.getTitle() + " --Spins TW: " + plr.getSpinsTW() + "; ";
				count += 1;
			}
		}
		if (count == 0) {
			return "     Station is playing no other titles by " + record.getArtist();
		}
		return output;
	}
	
	private PlaylistRecord getPlaylistRecord(ChartRecord record, RollingPlaylist playlist) {
		for (PlaylistRecord plr : playlist.getPlaylist()) {
			if (record.getTitle().equals(plr.getTitle())) {
				return plr;
			}
		}
		return null;
	}
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		PlaylistInsights pi = new PlaylistInsights();
		//RollingPlaylist rp = new RollingPlaylist("/Users/mikewilson/Desktop/Station Playlist copy 2.csv");
		/*pi.assignCategory(rp);
		for(PlaylistRecord record : rp.getPlaylist()) {
			System.out.println(record.getArtist() + " " + record.getTitle() + " " + record.getCategory()
					+ " " + record.getSpinsTW());
		}
		
		try {
			pi.getCategories(rp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		pi.loadChartAndPlaylists();
		//pi.writeKeyStats();
		pi.initializeFiles();
		while(true) {
			//pi.printKeyStats();
			pi.writeKeyStats();
		}		
	}
	
}
