package ir.ac.um.ce.projectnews.crawler;

import com.opencsv.CSVWriter;
import ir.ac.um.ce.projectnews.utils.DateTimeHelper;
import me.jhenrique.manager.TweetManager;
import me.jhenrique.manager.TwitterCriteria;
import me.jhenrique.model.Tweet;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Crawler {
    private static final String USERNAME = "Tasnimnews_Fa";
    private static final String CSV_PATH = "./out.csv";

    public static void main(String[] args) throws IOException {
        TwitterCriteria criteria = TwitterCriteria.create()
                .setUsername(USERNAME).setMaxTweets(1000000)
                .setSince("2018-06-01").setUntil(DateTimeHelper.getLastDayOfMonth(2018, 6));
        ArrayList<Tweet> tweets = (ArrayList<Tweet>) TweetManager.getTweets(criteria);
        System.out.println(tweets.size());


        try (Writer writer = Files.newBufferedWriter(Paths.get(CSV_PATH));
             CSVWriter csvWriter = new CSVWriter(writer,
                     CSVWriter.DEFAULT_SEPARATOR,
                     CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                     CSVWriter.DEFAULT_LINE_END)
        ) {
            String[] headerRecord = {"ID", "Permalink", "Text", "Date", "Retweets", "Favorites"};
            csvWriter.writeNext(headerRecord);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            for (Tweet t : tweets) {
                csvWriter.writeNext(new String[]{
                        t.getId(),
                        t.getPermalink(),
                        t.getText(),
                        formatter.format(t.getDate()),
                        t.getRetweets() + "",
                        t.getFavorites() + ""
                });
            }
        }
    }
}
