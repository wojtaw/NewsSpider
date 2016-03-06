import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by wojtaw on 04/03/16.
 */
public class Main {
    public static void main(String[] args) {
        IhnedScraper ihned = new IhnedScraper();
        ArrayList<ArticleLinks> articleLinks = ihned.scrapeKeyword("NWR");

        /*
        FileOutputStream fileOut =
                null;
        try {
            fileOut = new FileOutputStream("/storage/ihned-articles-nwr.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(articleLinks);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /storage/ihned-articles-nwr.ser");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
}
