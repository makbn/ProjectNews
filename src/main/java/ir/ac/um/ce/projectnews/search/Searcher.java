package ir.ac.um.ce.projectnews.search;


import ir.ac.um.ce.projectnews.utils.Pair;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;

class Searcher {
    private final String indexPath;
    private String query;
    private int resultsCount;
    private Analyzer analyzer;

    private Similarity similarity;

    public Searcher(String indexPath, String queriesPath, int resultsCount, Similarity similarity) throws IOException {
        this.indexPath = indexPath;
        this.resultsCount = resultsCount;
        this.similarity = similarity;

        this.analyzer = Writer.getAnalyzer();

        query = readFromFile(queriesPath);
    }

    public void search() throws Exception {
        PrintWriter output = new PrintWriter(System.out);
        IndexReader reader = createReader(indexPath);
        IndexWriter relatedWriter = createWriter();
        IndexSearcher searcher = new IndexSearcher(reader);
        searcher.setSimilarity(similarity);
        Query q = null;
        List<Document> documents = new ArrayList<>();
        try {
            q = new QueryParser("body", analyzer).parse(query);
            TopDocs result = searcher.search(q, resultsCount);

            double maxScore = result.getMaxScore();
            double minScore = result.scoreDocs[result.scoreDocs.length - 1].score;
            double threshold = (maxScore + minScore) / 2;

            System.out.printf("Threshold: %.3f\n", threshold);
            for (int j = 0; j < result.scoreDocs.length; j++) {
                ScoreDoc doc = result.scoreDocs[j];
                Document storedDocument = reader.document(doc.doc);

                boolean isRelated = doc.score >= threshold;
                if (isRelated) {
                    documents.add(createDocument(storedDocument.get("body")));
                }
            }
            relatedWriter.addDocuments(documents);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            relatedWriter.close();
        }

        reader = createReader(indexPath + "_related");
        Terms terms = MultiFields.getTerms(reader, "body");
        TermsEnum it = terms.iterator();
        BytesRef term;
        ArrayList<Pair> counts = new ArrayList<>();
        while ((term = it.next()) != null) {
            StringBuilder result = new StringBuilder();
            PostingsEnum docsEnum = MultiFields.getTermDocsEnum(reader, "body", term);
            int docId;
            long seen = 0;
            while ((docId = docsEnum.nextDoc()) != PostingsEnum.NO_MORE_DOCS) {
                seen += docsEnum.freq();
            }
            counts.add(new Pair(term.utf8ToString(), seen));
        }
        counts.sort((o1, o2) -> {
            if (o1.second > o2.second)
                return -1;
            else if (o1.second < o2.second)
                return 1;
            return 0;
        });

        counts.forEach(System.out::println);

        output.close();
    }

    private IndexReader createReader(String indexPath) throws IOException {
        Directory dir = FSDirectory.open(Paths.get(indexPath));
        return DirectoryReader.open(dir);
    }


    private Document createDocument(String body) {
        Document document = new Document();
        document.add(new TextField("body", body, Field.Store.NO));

        return document;
    }

    private IndexWriter createWriter() throws IOException {
        FSDirectory dir = FSDirectory.open(Paths.get(indexPath + "_related"));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        return new IndexWriter(dir, config);
    }

    private String readFromFile(String path) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(path));
        return input.readLine();
    }

}