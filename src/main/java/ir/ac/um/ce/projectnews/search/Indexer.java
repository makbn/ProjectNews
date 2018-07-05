package ir.ac.um.ce.projectnews.search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Indexer {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Indexer program!");
        System.out.println("It's developed by Mohammad-Reza Daliri at Ferdowsi University of Mashhad");
        System.out.println("--------------------");

        String index = null;
        if (args.length < 1) {
            System.err.println("Corpus file not defined!");
            System.exit(1);
        } else if (args.length == 1) {
            System.out.println("INFO: Using default path ('indices') folder to store index.");
            index = "indices";
        } else {
            index = args[1];
            Path indexPath = Paths.get(index);
            if (Files.exists(indexPath) && !Files.isWritable(indexPath)) {
                System.err.println("Index path is not writable.");
                System.exit(1);
            } else if (!Files.exists(indexPath)) {
                Files.createDirectories(indexPath);
                System.out.println("INFO: Creating directory for index storage.");
            }
        }
        String corpus = args[0];

        Writer writer = new Writer(corpus, index);

        try {
            System.out.println("Processing...");
            long startTime = System.currentTimeMillis();

            writer.write();

            System.out.printf("DONE in %.3fs.\n", (System.currentTimeMillis() - startTime) / 1000.0);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }


}
