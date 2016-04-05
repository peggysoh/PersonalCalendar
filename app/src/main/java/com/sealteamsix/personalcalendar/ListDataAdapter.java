package com.sealteamsix.personalcalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SealTeam6 on 4/1/2016.
 */
public class ListDataAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public ListDataAdapter(Context context, int resource) {
        super(context, resource);
    }


    static class LayoutHandler {
        TextView DATE, NAME, TIME;
        ImageButton BULLET;
    }


    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }


    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }


    @Override
    public boolean isEnabled(int arg0) {
        return true;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        LayoutHandler layoutHandler;

        /*View.OnClickListener saveView = new View.OnClickListener() {
            @SuppressLint("NewApi")
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Clicked", Toast.LENGTH_LONG).show();
                v.callOnClick();
            }
        };*/

        if(row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            layoutHandler = new LayoutHandler();
            layoutHandler.DATE = (TextView) row.findViewById(R.id.text_date);
            layoutHandler.NAME = (TextView) row.findViewById(R.id.text_event_name);
            layoutHandler.TIME = (TextView) row.findViewById(R.id.text_time);
            layoutHandler.BULLET = (ImageButton) row.findViewById(R.id.image_bullet);
            row.setTag(layoutHandler);
        }
        else {
            layoutHandler = (LayoutHandler) row.getTag();
        }

        DataProvider dataProvider = (DataProvider) this.getItem(position);
        layoutHandler.DATE.setText(String.format("%02d", dataProvider.getMonth()) + "/" + String.format("%02d", dataProvider.getDate()) + "/" + String.format("%02d", dataProvider.getYear()));
        layoutHandler.NAME.setText(dataProvider.getName());
        if(dataProvider.getStart_hr() == -1) {
            layoutHandler.TIME.setText("All-Day");
        }
        else {
            layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr()) + ":" + String.format("%02d", dataProvider.getStart_min()) + " - " +
                    String.format("%02d", dataProvider.getEnd_hr()) + ":" + String.format("%02d", dataProvider.getEnd_min()));
        }

        return row;
    }
}
