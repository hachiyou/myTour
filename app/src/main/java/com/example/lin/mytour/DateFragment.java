package com.example.lin.mytour;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Lin on 9/14/2017.
 */

/**
 * {@link Fragment} that displays a list of places that will be visited on a particular day.
 */
public class DateFragment extends Fragment {


    public DateFragment() {
        // Required empty public constructor;
    }

    /**
     * Instantiate a {@link DateFragment} object that takes in a number
     *
     * @param day represents the nth day since the trip started.
     * @return a new {@link DateFragment} object
     */
    public static DateFragment newInstance(int day) {
        Bundle args = new Bundle();

        // put the identifier "days_of_trip" along with its value day in the arguments
        args.putInt("days_of_trip", day);

        DateFragment fragment = new DateFragment();

        // set the argument for this particular fragment.
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list, container, false);

        // Create a list of places
        final PlaceList<Place> places = new PlaceList<>();

        // Create a list of strings that holds the place definition
        ArrayList<String> tempPlaces = new ArrayList<>();

        // Read a text file based on which day it is in the trip, and get the place definitions from that file
        switch (this.getArguments().getInt("days_of_trip")) {
            // First Day
            case 1:
                tempPlaces = readTextFile(R.raw.day_1_places);
                break;
            // Second Day
            case 2:
                tempPlaces = readTextFile(R.raw.day_2_places);
                break;
            // More can be added.
            // Default will have no places.
            default:
                break;
        }
        // Add the places to the list of Place
        for (int i = 0; i < tempPlaces.size(); i++) {
            places.addPlace(new Place(tempPlaces.get(i)), places);
        }
        // Create an {@link DateAdapter}, whose data source is a list of {@link Place}s. The
        // adapter knows how to create list items for each item in the list.
        DateAdapter dateAdapter = new DateAdapter(getActivity(), places);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // list.xml layout file.
        ListView listView = rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link DateAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Place} in the list.
        listView.setAdapter(dateAdapter);

        // Set a click listener to show the map when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    // Convert the address obtained from the current selected place to an Uri object
                    Uri geoLocation = Uri.parse("geo:0,0?q=" + Uri.encode(places.get(i).getAddress()));

                    // Create the intent
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoLocation);

                    // Start the intent
                    startActivity(mapIntent);
                } catch (ActivityNotFoundException e) {
                    // Catch the error if there are no apps that can handle the intent.
                    Toast.makeText(getActivity(), "No Application can handle this request.", Toast.LENGTH_LONG).show();
                }
            }
        });


        return rootView;
    }

    /**
     * Process a text file
     *
     * @param resourceID represents an ID correlate to a text file in the raw folder
     * @return a list of String that represents the place definition
     */
    private ArrayList<String> readTextFile(int resourceID) {
        // Create an InputStream from a text file with the resource id being passed in.
        InputStream iStream = getResources().openRawResource(resourceID);
        // Create a list of String to hold place definitions
        ArrayList<String> results = new ArrayList<>();
        try {
            // Create a reader that reads the InputStream with a encoding UTF-8
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(iStream, "UTF-8"));
            String str;
            // If there are more lines in the reader
            while ((str = bufferedReader.readLine()) != null) {
                // add the line to the list;
                results.add(str);
            }
        } catch (IOException ex) {
            // Catch any IOException and handle it
            ex.printStackTrace();
        }
        return results;
    }
}
