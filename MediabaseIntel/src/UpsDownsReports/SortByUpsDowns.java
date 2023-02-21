package UpsDownsReports;


import org.apache.commons.csv.*;
import java.util.Comparator;
/**
 * Write a description of class SortByUpDowns here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SortByUpsDowns implements Comparator<CSVRecord>
{
    public int compare(CSVRecord a, CSVRecord b){
        return Integer.parseInt(a.get(11)) - Integer.parseInt(b.get(11));
    }
}

