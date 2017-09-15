package com.example.lin.mytour;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lin on 9/14/2017.
 */

/**
 * {@link DateAdapter} is an {@link ArrayAdapter} that can provide the layout for each list item
 * based on a data source, which is a list of {@link Place} objects.
 */
public class DateAdapter extends ArrayAdapter {
    /**
     * Create a new {@link DateAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param places  is the list of {@link Place}s to be displayed.
     */
    public DateAdapter(Activity context, ArrayList<Place> places) {
        super(context, 0, places);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // Get the {@link Place} object located at this position in the list
        Place current = (Place) getItem(position);

        // Find the container view in the list_item.xml layout with the ID container.
        View container = convertView.findViewById(R.id.container);
        // Check if the place is currently open or not.
        // If yes, then set the background to reflect the place being open.
        if (current.isOpen()) container.setBackgroundResource(R.drawable.open_notice);

        // Find the TextView in the list_item.xml layout with the ID store_name.
        TextView storeName = convertView.findViewById(R.id.store_name);
        // Get the name from the currentPlace object and set this text on the store name text field;
        storeName.setText(current.getName());

        // Find the TextView in the list_item.xml layout with the ID store_address.
        TextView storeAddress = convertView.findViewById(R.id.store_address);
        // Get the address from the currentPlace object and set this text on the store address text field;
        storeAddress.setText(current.getAddress());

        // Find the TextView in the list_item.xml layout with the ID store_opening_hours.
        TextView openingHours = convertView.findViewById(R.id.store_opening_hours);
        // Get the opening hours and closing hours from the currentPlace object,
        // and set this text on the store open hours text field;
        openingHours.setText(current.getOpeningHour() + " - " + current.getClosingHour());

        // Return the whole list item layout so that it can be shown in the ListView.
        return convertView;
    }
}
