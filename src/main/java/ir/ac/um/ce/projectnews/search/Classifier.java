package ir.ac.um.ce.projectnews.search;

import org.apache.lucene.search.similarities.BM25Similarity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Classifier {

    public static void main(String[] args) throws IOException {
        String index = "indices";
        String queriesPath = null;
        int resultsCount = 1000000;
        if (args.length < 1) {
            System.err.println("Queries not defined!");
            System.err.println("Index path not defined!");
            System.err.println("Results count not defined!");
            System.exit(1);
        } else if (args.length == 1) {
            System.err.println("INFO: Using default path ('indices') folder for index path.");
            System.err.println("INFO: Using default results count (5).");
            queriesPath = args[0];
        } else if (args.length == 2) {
            System.err.println("Results count not defined!");
            System.exit(1);
        } else {
            index = args[0];
            Path indexPath = Paths.get(index);
            if (!Files.exists(indexPath)) {
                System.err.println("Index path does not exist.");
                System.exit(1);
            }
            queriesPath = args[1];
            resultsCount = Integer.parseInt(args[2]);
        }

        Searcher searcher = new Searcher(index, queriesPath, resultsCount, new BM25Similarity());

        try {
            long startTime = System.currentTimeMillis();

            searcher.search();

            System.out.printf("DONE in %.3fs.\n", (System.currentTimeMillis() - startTime) / 1000.0);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
