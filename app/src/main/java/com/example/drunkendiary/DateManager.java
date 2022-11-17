package com.example.drunkendiary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateManager {

    private SimpleDateFormat ymd = new SimpleDateFormat("yyyy/M/d", Locale.KOREA);
    private SimpleDateFormat time = new SimpleDateFormat("H:m:s", Locale.KOREA);

    public String getNowDate() {
        Date now = Calendar.getInstance().getTime();
        return ymd.format(now);
    }

    public String getNowTime() {
        Date now = Calendar.getInstance().getTime();
        return time.format(now);
    }

    public String makeStringDate(Date date) {
        return ymd.format(date);
    }

    public String makeStringTime(Date date) {
        return time.format(date);
    }
}
