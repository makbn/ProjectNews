package ir.ac.um.ce.projectnews.crawler;

import com.opencsv.CSVWriter;
import ir.ac.um.ce.projectnews.exception.InvalidConfigurationException;
import ir.ac.um.ce.projectnews.utils.Constants;
import ir.ac.um.ce.projectnews.utils.DateTimeHelper;
import me.jhenrique.manager.TweetManager;
import me.jhenrique.manager.TwitterCriteria;
import me.jhenrique.model.Tweet;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Crawler {

    private static HashMap<String, String> argsMap;

    static {
        argsMap = new HashMap<>();
    }

    /**
     * @param args -i -s -e -m -p -n
     * @throws IOException in writing csv file
     */
    public static void main(String[] args) throws IOException, RuntimeException {
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-i":
                        argsMap.put("-i", args[i + 1]);
                        break;
                    case "-s":
                        argsMap.put("-s", args[i + 1]);
                        break;
                    case "-e":
                        argsMap.put("-e", args[i + 1]);
                        break;
                    case "-m":
                        argsMap.put("-m", args[i + 1]);
                        break;
                    case "-p":
                        argsMap.put("-p", args[i + 1]);
                        break;
                    case "-n":
                        argsMap.put("-n", args[i + 1]);
                        break;
                    default:
                        System.out.println("unknown arg: " + args[i]);
                        break;
                }
            }
            if (argsMap.get("-i") == null)
                throw new InvalidConfigurationException("-i flag is required!");
        } else {
            throw new InvalidConfigurationException("flags not set!");
        }

        TwitterCriteria criteria = TwitterCriteria.create()
                .setUsername(argsMap.get("-i") == null
                        ? Constants.USERNAME : argsMap.get("-i"))
                .setMaxTweets(argsMap.get("-m") == null
                        ? Constants.DEFAULT_MAX_TWEETS : Integer.parseInt(argsMap.get("-m")))
                .setSince(argsMap.get("-s"))
                .setUntil(argsMap.get("-e"));

        System.out.println("Start Crawling id: " + argsMap.get("-i"));

        ArrayList<Tweet> tweets = (ArrayList<Tweet>) TweetManager.getTweets(criteria);

        try (Writer writer = Files.newBufferedWriter(Paths.get(generatePath()));
             CSVWriter csvWriter = new CSVWriter(writer,
                     CSVWriter.DEFAULT_SEPARATOR,
                     CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                     CSVWriter.DEFAULT_LINE_END)
        ) {
            String[] headerRecord = {"ID", "Permalink", "Text", "Date", "Retweets", "Favorites"};
            csvWriter.writeNext(headerRecord);


            for (Tweet t : tweets) {
                csvWriter.writeNext(new String[]{
                        t.getId(),
                        t.getPermalink(),
                        t.getText(),
                        DateTimeHelper.getSimpleDateFormat().format(t.getDate()),
                        String.valueOf(t.getRetweets()),
                        String.valueOf(t.getFavorites())
                });
            }
        }
    }

    private static String generatePath() {
        return (argsMap.get("-p") == null
                ? Constants.CSV_PATH : argsMap.get("-p")) +
                (argsMap.get("-n") == null
                        ? generateName() + ".csv" : argsMap.get("-n"));
    }

    private static String generateName() {
        return "out"
                + "_" + argsMap.get("-i")
                + "_" + argsMap.get("-s")
                + "_" + argsMap.get("-e")
                + "_" + argsMap.get("-m");
    }
}
