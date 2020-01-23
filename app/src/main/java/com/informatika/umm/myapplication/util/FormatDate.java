package com.informatika.umm.myapplication.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * MADE_Submission_2
 * created by : Robin Nusantara on 1/12/2020 01 2020
 * 15:11 Sun
 **/
public class FormatDate {

    public static String getFormatReleaseDate(String releaseDate) {
        releaseDate = releaseDate != null ? releaseDate : "2020-01-12";
        SimpleDateFormat oldPattern = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newPattern = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = oldPattern.parse(releaseDate);
            if (date != null) {
                calendar.setTime(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newPattern.format(calendar.getTime());
    }

}