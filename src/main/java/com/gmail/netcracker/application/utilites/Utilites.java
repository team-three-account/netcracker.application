package com.gmail.netcracker.application.utilites;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilites {

    public static Timestamp parseStringIntoDate(String str_date) {
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = formatter.parse(str_date);
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseDateIntoString(Date date) {
        Format df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }
}
