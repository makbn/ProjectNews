package ir.ac.um.ce.projectnews.search;

import com.opencsv.CSVReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.analysis.fa.PersianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Writer {
    private static final String STOPWORD_FILE = "./stopwords.txt";
    private final String indexPath;
    private final String corpusPath;

    public Writer(String corpusPath, String indexPath) {
        this.indexPath = indexPath;
        this.corpusPath = corpusPath;
    }

    public static Analyzer getAnalyzer() throws IOException {
        return new PersianAnalyzer(WordlistLoader.getWordSet(new InputStreamReader(
                new FileInputStream(STOPWORD_FILE), StandardCharsets.UTF_8)
        ));
    }

    public void write() throws IOException {
        List<Entry> corpus = readFromFile(corpusPath);
        IndexWriter writer = createWriter();
        List<Document> documents = new ArrayList<>();

        for (Entry entry : corpus) {
            documents.add(createDocument(entry.getId(), entry.getBody()));
        }

        writer.deleteAll();

        writer.addDocuments(documents);
        writer.commit();
        writer.close();
    }

    private Document createDocument(String id, String body) {
        Document document = new Document();
        document.add(new StringField("id", id, Field.Store.YES));
        document.add(new TextField("body", body, Field.Store.YES));

        return document;
    }

    private IndexWriter createWriter() throws IOException {
        FSDirectory dir = FSDirectory.open(Paths.get(indexPath));
        IndexWriterConfig config = new IndexWriterConfig(getAnalyzer());
        return new IndexWriter(dir, config);
    }

    private List<Entry> readFromFile(String path) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        CSVReader csvReader = new CSVReader(input);
        ArrayList<Entry> documents = new ArrayList<>();

        String[] nextRecord;
        csvReader.readNext();

        while ((nextRecord = csvReader.readNext()) != null) {
            if (nextRecord.length > 2) {
                documents.add(new Entry(nextRecord[0], nextRecord[2]));
            }
        }
        return documents;
    }
}