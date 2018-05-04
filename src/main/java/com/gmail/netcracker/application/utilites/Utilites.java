package com.gmail.netcracker.application.utilites;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilites {

    //TODO удалить один парсер
    public static Timestamp parseStringIntoDate(String str_date) {
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = formatter.parse(str_date);
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseDateIntoString(Date date) {
        if (date == null) {
            return "";
        }
        Format df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static String parseDateIntoString(Long date) {
        if (date == null) {
            return "";
        }
        Format df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date(date));
    }

    public static Timestamp parseTime(String str_date) {
        if (str_date != null) {
            try {
                DateFormat formatter;
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = formatter.parse(str_date);
                Timestamp timestamp = new Timestamp(date.getTime());
                return timestamp;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static int parseStringToInt(String srt) {
        int value = 0;
        try {
            value = Integer.parseInt(srt);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return value;
    }
}
