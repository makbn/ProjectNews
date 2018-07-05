package ir.ac.um.ce.projectnews.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeHelper {
    public static String getLastDayOfMonth(int year, int month) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = sdf.parse(year + "-" + (month < 10 ? ("0" + month) : month) + "-01");

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);

            Date lastDayOfMonth = calendar.getTime();

            return sdf.format(lastDayOfMonth);
        } catch (Exception ex) {
            return null;
        }
    }
}
