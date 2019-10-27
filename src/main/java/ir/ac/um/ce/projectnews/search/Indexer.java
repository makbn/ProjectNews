package ir.ac.um.ce.projectnews.search;

import ir.ac.um.ce.projectnews.exception.InvalidConfigurationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Indexer {

    public static void main(String[] args) throws IOException {
        String index;
        if (args.length < 1) {
            throw new InvalidConfigurationException("Corpus file not defined!");
        } else if (args.length == 1) {
            System.out.println("INFO: Using default path ('indices') folder to store index.");
            index = "indices";
        } else {
            index = args[1];
            Path indexPath = Paths.get(index);
            if (Files.exists(indexPath) && !Files.isWritable(indexPath)) {
                throw new InvalidConfigurationException("Index path is not writable.");
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
