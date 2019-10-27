## Twitter Persian news tagcloud extraction

Final project of Information retrieval course.

TPNT is a Tag Cloud generator that extracts hot keywords from Twitter page of a major Persian news agency in the fields of Economics and Socials for each month in a year.

---

### Dependencies

* `GetOldTweets-java v1.2.0`

* `Lucene 7.2.1`

### News Agency
 
* Tasnim News([@TasnimNews_Fa](https://twitter.com/tasnimnews_fa))

---

<p dir="rtl">
۱- اجرای پروژه
اجرای پروژه ۲ مرحله اصلی دارد، ابتدابا استفاده از کلاس Crawler توییت های صفحه ی مورد نظر استخراج می شود و سپس در یک فایل با فرمت csv که دارای پارامتر های  زیر است ذخیره می شود.
</p>

<div dir="rtl">
 
برای اجرای کلاس Crawler نیاز است یک سری پارامتر ها را به عتوان آرگومان ورودی بدهید:

پرچم -i: مشخص کننده Id صفحه مورد در تویتر برای استخراج توییت ها(اجباری)

پرچم -s: مشخص کننده تاریخ شروع بازه زمانی مورد نظر برای دریافت توییت ها

فرمت ورودی: YYYY-MM-DD

پرچم -e: مشخص کننده تاریخ پایان بازه زمانی مورد نظر

فرمت ورود : YYYY-MM-DD

پرچم -m: محدود کننده حد اکثر تعداد پیام های استخراج شده در بازه زمانی مورد نظر

پرچم -p: مسیر ذخیره فایل تویت های استخراج شده

پرچم -n؛ نام انتخابی برای ذخیره فایل
</div>

<div dir="rtl">
به عنوان مثال برای استخراج توییت های صفحه TasnimNews از تاریخ ۲۰۱۸/۰۶/۰۱ تا تاریخ ۲۰۱۸/۰۷/۰۱  در مسیر result ورودی باید بصورت زیر باشد:
 
`java -cp ProjectNews.jar ir.ac.um.ce.projectnews.crawler.Crawler -i Tasnimnews_Fa -s 2018-06-01 -e 2018-07-01 -p result/`
</div>

<p dir="rtl">
در مرحله بعد با استفاده از کلاس Searcher و Classifier  ابتدا سند های موجود را  پس از حذف Stop word ها نمایه گذاری می‌کنیم، سپس با استفاده از تعدادی کوئری که از Bag of Words مربوط به هر حوزه ساخته شده است اسناد را بازیابی و بر اساس میزان مرتبط بودن مرتب می‌کنیم
در نهایت کلمات پرتکرار این اسناد را بر اساس تعداد تکرار به عنوان کلمات کلیدی در نظر می‌گیریم.
</p>

 
