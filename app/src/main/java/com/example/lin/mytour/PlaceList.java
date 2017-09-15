package com.example.lin.mytour;

import java.util.ArrayList;

/**
 * Created by Lin on 9/14/2017.
 */

public class PlaceList<E> extends ArrayList<E> {

    // Add new places and sort them base on their opening hours
    public static void addPlace(Place newPlace, ArrayList<Place> places) {
        int index = binaryInsert(newPlace, places, 0, places.size());
        places.add(index, newPlace);
    }

    // Find the insert position of a new place to a list of places by performing a binary search.
    private static int binaryInsert(Place newPlace, ArrayList<Place> places, int start, int end) {
        int median = (start + end) / 2;
        if (start >= end) return start;
        if (newPlace.compareTo(places.get(median)) > 0) {
            return binaryInsert(newPlace, places, median + 1, end);
        } else return binaryInsert(newPlace, places, start, median);
    }
}
