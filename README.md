## Twitter Persian news tagcloud extraction

Final project of Information retrieval course.

TPNT is a Tag cloud generator that extracts hot keywords from Twitter page of a major Persian news agency in the fields of **Economics** and **Socials** for each month in a year.

---

### Dependencies

*   `GetOldTweets-java v1.2.0`
*   `Lucene 7.2.1`

### News agency
 
*   Tasnim News([@TasnimNews_Fa](https://twitter.com/tasnimnews_fa))

---

### How to Run

This project has to main steps. First, twitts are stored in a `csv` file with the help of `Crawler` class. this class needs some **options** to work properly:

*   `-i` : The Id of twitter page  `required`
*   `-s` : Start date of extraction  `required`
     *  format: `YYY-MM-DD`
*   `-e` : End date of extraction 
     *  format: `YYY-MM-DD`
*   `-m` : Limitation in the number of retrieved twitts 
*   `-p` : Path of csv file
*   `-n` : Name of csv file

An example for retrieving twitts from ([@TasnimNews_Fa](https://twitter.com/tasnimnews_fa)) starting from 2018-06-01 to 2018-07-01 in `$PWD/result/` path:

```bash
java -cp ProjectNews.jar ir.ac.um.ce.projectnews.crawler.Crawler -i Tasnimnews_Fa -s 2018-06-01 -e 2018-07-01 -p result/
```

The next step is indexing docs. After removing stop-words from docs we use `Searcher` and `Classifier` classes plus a Bag of word to create some queries to estimate the correlation of each doc with context. Finally, we use the most corrolated words to generate a tag clud.

### Contributors

*   [Mehdi Akbarian-Rastaghi](https://github.com/makbn)
*   [Mohammad-Reza Daliri](https://github.com/mrdaliri)

