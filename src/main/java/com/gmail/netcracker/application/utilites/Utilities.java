package com.gmail.netcracker.application.utilites;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//TODO don't forget about conversation in jobs after Utilities fixing !!!
public class Utilities {
    //TODO удалить один парсер
    //deleted
//    public static Timestamp parseStringIntoDate(String str_date) {
//        try {
//            DateFormat formatter;
//            formatter = new SimpleDateFormat("yyyy-MM-dd");
//            java.util.Date date = formatter.parse(str_date);
//            Timestamp timestamp = new Timestamp(date.getTime());
//            return timestamp;
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static String parseDateToString(Date date) {
        if (date == null) {
            return "";
        }
        Format df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static String parseDateToStringWithSeconds(Date date) {
        if (date == null) {
            return "";
        }
        Format df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

//    public static String parseDateIntoString(Long date) {
//        if (date == null) {
//            return "";
//        }
//        Format df = new SimpleDateFormat("yyyy-MM-dd");
//        return df.format(new Date(date));
//    }

    public static Timestamp parseStringToTimestampWithSeconds(String stringDate) {
        if (stringDate != null) {
            try {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = formatter.parse(stringDate);
                Timestamp timestamp = new Timestamp(date.getTime());
                return timestamp;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static Timestamp parseStringToTimestamp(String stringDate) {
        if (stringDate != null) {
            try {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(stringDate);
                Timestamp timestamp = new Timestamp(date.getTime());
                return timestamp;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static Date parseStringToDate(String stringDate) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
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

    public static String getCurrentDateInString() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStamp = getCurrentTimeStamp().toString();
        Date dateTime = format.parse(timeStamp);
        String t = format.format(dateTime);
        return t;
    }

    public static Timestamp getCurrentTimeStamp() {
        Date today = new Date();
        return new Timestamp(today.getTime());
    }
}
