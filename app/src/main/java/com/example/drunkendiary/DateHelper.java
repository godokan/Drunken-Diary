package com.example.drunkendiary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateHelper {

    private final SimpleDateFormat ymd = new SimpleDateFormat("yyyy/M/d", Locale.KOREA);
    private final SimpleDateFormat time = new SimpleDateFormat("H:m:s", Locale.KOREA);
    private final TimeZone timeZone = TimeZone.getTimeZone("Asia/Seoul");

    public String getNowDate() {
        ymd.setTimeZone(timeZone);
        Date now = Calendar.getInstance().getTime();
        return ymd.format(now);
    }

    public String getNowTime() {
        time.setTimeZone(timeZone);
        Date now = Calendar.getInstance().getTime();
        return time.format(now);
    }

    public String makeStringDate(Date date) {
        ymd.setTimeZone(timeZone);
        return ymd.format(date);
    }

    public String makeStringTime(Date date) {
        time.setTimeZone(timeZone);
        return time.format(date);
    }
}
