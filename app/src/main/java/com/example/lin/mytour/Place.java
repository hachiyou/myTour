package com.example.lin.mytour;

import android.icu.util.Calendar;
import android.icu.util.TimeZone;


/**
 * Created by Lin on 9/1/2017.
 */

/**
 * an object that defines a place
 */
public class Place implements Comparable {
    // Name of the place
    private String mName;
    // Address of the place
    private String mAddress;
    // Starting hour of the place
    private int mOpeningHour;
    // Closing hour of the place
    private int mClosingHour;
    // The days of week the place is opened.
    private boolean[] mOpeningDays;

    /**
     * Constructor that takes in separate clauses
     *
     * @param placeName   represent the name of the place
     * @param address     represent the address of the place
     * @param openingHour represent the starting hour of the place
     * @param closingHour represent the closing hour of the place
     * @param openingDays a list of 7 boolean that represent the days of week the place is opened
     */
    public Place(String placeName, String address, int openingHour, int closingHour, boolean[] openingDays) {
        mName = placeName;
        mAddress = address;
        mOpeningHour = openingHour;
        mClosingHour = closingHour;
        // if the openingDays is unreadable, then we assume the place is opened all week long.
        if (openingDays == null) {
            mOpeningDays = new boolean[]{true, true, true, true, true, true, true};
        } else {
            mOpeningDays = new boolean[7];
            for (int i = 0; i < openingDays.length; i++) {
                mOpeningDays[i] = openingDays[i];
            }
        }
    }

    /**
     * Constructor that takes in a string in forms of "PlaceName] [Address] [Opening Hour] [Closing Hour] [Opening Days"
     *
     * @param inputStream
     */
    public Place(String inputStream) {
        // Split the string based on "] ["
        String[] properties = inputStream.split("\\] \\[");
        mName = properties[0];
        mAddress = properties[1];
        mOpeningHour = Integer.parseInt(properties[2]);
        mClosingHour = Integer.parseInt(properties[3]);
        String[] openingDays = properties[4].split(", ");
        mOpeningDays = new boolean[7];
        for (int i = 0; i < 7; i++) {
            mOpeningDays[i] = Boolean.parseBoolean(openingDays[i]);
        }
    }

    /**
     * get the name of the place
     */
    public String getName() {
        return mName;
    }

    /**
     * get the address of the place
     */
    public String getAddress() {
        return mAddress;
    }

    /**
     * get the open hour of the place
     */
    public String getOpeningHour() {
        return timeFormatter(mOpeningHour);
    }

    /**
     * get the closing hour of the place
     */
    public String getClosingHour() {
        return timeFormatter(mClosingHour);
    }

    /**
     * check if the place is opened based on the Japanese Standard Time
     */
    public boolean isOpen() {
        TimeZone timeZone = TimeZone.getTimeZone("JST");
        Calendar calendar = Calendar.getInstance(timeZone);
        int currentTime = calendar.get(Calendar.HOUR_OF_DAY);
        if (mOpeningDays[calendar.get(Calendar.DAY_OF_WEEK) - 1]) {
            if (mOpeningHour / 100 < currentTime && mClosingHour / 100 > currentTime) return true;
            return false;
        } else return false;
    }

    @Override
    public int compareTo(Object o) {
        return this.mOpeningHour - ((Place) o).mOpeningHour;
    }

    /**
     * Format the time to a string in "TT:MM AM/PM"
     */
    private String timeFormatter(int time) {
        String timeStr = String.format("%04d", time);
        String hour = timeStr.substring(0, timeStr.length() / 2);
        String minute = timeStr.substring(timeStr.length() / 2);
        if (time / 100 < 12 || time / 100 == 24) {
            if (time / 100 == 24)
                hour = String.format("%04d", time - 2400).substring(0, timeStr.length() / 2);
            return hour + ":" + minute + " AM";
        } else {
            if (time / 100 > 12)
                hour = String.format("%04d", time - 1200).substring(0, timeStr.length() / 2);
            return hour + ":" + minute + " PM";
        }
    }
}
