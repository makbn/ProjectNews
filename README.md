## Twitter Persian news tagcloud extraction

Final project of Information retrieval course.

TPNT is a Tag cloud generator that extracts hot keywords from Twitter page of a major Persian news agency in the fields of **Economics** and **Socials** for each month in a year.

---

### Dependencies

* `GetOldTweets-java v1.2.0`

* `Lucene 7.2.1`

### News agency
 
* Tasnim News([@TasnimNews_Fa](https://twitter.com/tasnimnews_fa))

---

### How to Run

This project has to main steps. First, twitts are stored in a `csv` file with the help of `Crawler` class. this class needs some **options** to work properly:

 * `-i` : The Id of twitter page  `required`
 * `-s` : Start date of extraction  `required`
   * format: `YYY-MM-DD`
 * `-e` : End date of extraction 
   * format: `YYY-MM-DD`
 * `-m` : Limitation in the number of retrieved twitts 
 * `-p` : Path of csv file
 * `-n` : Name of csv file


example for retrieving twitts from ([@TasnimNews_Fa](https://twitter.com/tasnimnews_fa)) starting from 2018-06-01 to 2018-07-01 in `$PWD/result/`path:

```bash
java -cp ProjectNews.jar ir.ac.um.ce.projectnews.crawler.Crawler -i Tasnimnews_Fa -s 2018-06-01 -e 2018-07-01 -p result/
```

<p dir="rtl">
در مرحله بعد با استفاده از کلاس Searcher و Classifier  ابتدا سند های موجود را  پس از حذف Stop word ها نمایه گذاری می‌کنیم، سپس با استفاده از تعدادی کوئری که از Bag of Words مربوط به هر حوزه ساخته شده است اسناد را بازیابی و بر اساس میزان مرتبط بودن مرتب می‌کنیم
در نهایت کلمات پرتکرار این اسناد را بر اساس تعداد تکرار به عنوان کلمات کلیدی در نظر می‌گیریم.
</p>

### Contributors

* [Mehdi Akbarian-Rastaghi](@makbn)
* [Mohammad-Reza Daliri](@mrdaliri)

