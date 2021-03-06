package com.sealteamsix.personalcalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SealTeam6 on 4/1/2016.
 *
 * Customized listView adapter
 * Formats how data is displayed on row_layout.xml
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
        String TYPE = dataProvider.getType();
        if (TYPE.equals("Personal"))
            layoutHandler.BULLET.setImageResource(R.drawable.pink_circle);
        else if (TYPE.equals("Holiday"))
            layoutHandler.BULLET.setImageResource(R.drawable.yellow_circle);
        else if (TYPE.equals("Birthday"))
            layoutHandler.BULLET.setImageResource(R.drawable.purple_circle);
        else if (TYPE.equals("School"))
            layoutHandler.BULLET.setImageResource(R.drawable.blue_circle);
        else if (TYPE.equals("Work"))
            layoutHandler.BULLET.setImageResource(R.drawable.green_circle);
        else
            layoutHandler.BULLET.setImageResource(R.drawable.orange_circle);

       // Get time and check if it's all day
        if(dataProvider.getStart_hr() == -1) {
            layoutHandler.TIME.setText("All-Day");
        }

        // Format time accordingly for AM/PM
        else if(dataProvider.getStart_hr() == 12) {
            if(dataProvider.getEnd_hr() == 12) {
                layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr()) + ":" + String.format("%02d", dataProvider.getStart_min()) + "PM - " +
                        String.format("%02d", dataProvider.getEnd_hr()) + ":" + String.format("%02d", dataProvider.getEnd_min()) + "PM");
            }
            else if(dataProvider.getEnd_hr() > 12) {
                layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr()) + ":" + String.format("%02d", dataProvider.getStart_min()) + "PM - " +
                        String.format("%02d", dataProvider.getEnd_hr()-12) + ":" + String.format("%02d", dataProvider.getEnd_min()) + "PM");
            }
            else if (dataProvider.getEnd_hr() == 00) {
                layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr()) + ":" + String.format("%02d", dataProvider.getStart_min()) + "PM - " +
                        String.format("%02d", dataProvider.getEnd_hr() + 12) + ":" + String.format("%02d", dataProvider.getEnd_min()) + "AM");
            }
            else {
                layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr()) + ":" + String.format("%02d", dataProvider.getStart_min()) + "PM - " +
                        String.format("%02d", dataProvider.getEnd_hr()) + ":" + String.format("%02d", dataProvider.getEnd_min()) + "AM");
            }
        }

        else if(dataProvider.getStart_hr() == 00) {
            if (dataProvider.getEnd_hr() == 12) {
                layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr() + 12) + ":" + String.format("%02d", dataProvider.getStart_min()) + "AM - " +
                        String.format("%02d", dataProvider.getEnd_hr()) + ":" + String.format("%02d", dataProvider.getEnd_min()) + "PM");
            }
            else if (dataProvider.getEnd_hr() > 12) {
                layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr() + 12) + ":" + String.format("%02d", dataProvider.getStart_min()) + "AM - " +
                        String.format("%02d", dataProvider.getEnd_hr() - 12) + ":" + String.format("%02d", dataProvider.getEnd_min()) + "PM");
            }
            else if (dataProvider.getEnd_hr() == 00) {
                layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr() + 12) + ":" + String.format("%02d", dataProvider.getStart_min()) + "AM - " +
                        String.format("%02d", dataProvider.getEnd_hr() + 12) + ":" + String.format("%02d", dataProvider.getEnd_min()) + "AM");
            }
            else {
                layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr() + 12) + ":" + String.format("%02d", dataProvider.getStart_min()) + "AM - " +
                        String.format("%02d", dataProvider.getEnd_hr()) + ":" + String.format("%02d", dataProvider.getEnd_min()) + "AM");
            }
        }

        else if(dataProvider.getStart_hr() > 12) {
            if(dataProvider.getEnd_hr() == 12) {
                layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr()-12) + ":" + String.format("%02d", dataProvider.getStart_min()) + "PM - " +
                        String.format("%02d", dataProvider.getEnd_hr()) + ":" + String.format("%02d", dataProvider.getEnd_min()) + "PM");
            }
            else if(dataProvider.getEnd_hr() > 12) {
                layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr()-12) + ":" + String.format("%02d", dataProvider.getStart_min()) + "PM - " +
                        String.format("%02d", dataProvider.getEnd_hr()-12) + ":" + String.format("%02d", dataProvider.getEnd_min()) + "PM");
            }
            else if (dataProvider.getEnd_hr() == 00) {
                layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr()-12) + ":" + String.format("%02d", dataProvider.getStart_min()) + "PM - " +
                        String.format("%02d", dataProvider.getEnd_hr()+12) + ":" + String.format("%02d", dataProvider.getEnd_min()) + "AM");
            }
            else {
                layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr()-12) + ":" + String.format("%02d", dataProvider.getStart_min()) + "PM - " +
                        String.format("%02d", dataProvider.getEnd_hr()) + ":" + String.format("%02d", dataProvider.getEnd_min()) + "AM");
            }
        }

        else {
            if(dataProvider.getEnd_hr() == 12) {
                layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr()) + ":" + String.format("%02d", dataProvider.getStart_min()) + "AM - " +
                        String.format("%02d", dataProvider.getEnd_hr()) + ":" + String.format("%02d", dataProvider.getEnd_min()) + "PM");
            }
            else if(dataProvider.getEnd_hr() > 12) {
                layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr()) + ":" + String.format("%02d", dataProvider.getStart_min()) + "AM - " +
                        String.format("%02d", dataProvider.getEnd_hr()-12) + ":" + String.format("%02d", dataProvider.getEnd_min()) + "PM");
            }
            else if (dataProvider.getEnd_hr() == 00) {
                layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr()) + ":" + String.format("%02d", dataProvider.getStart_min()) + "PM - " +
                        String.format("%02d", dataProvider.getEnd_hr()+12) + ":" + String.format("%02d", dataProvider.getEnd_min()) + "AM");
            }
            else {
                layoutHandler.TIME.setText(String.format("%02d", dataProvider.getStart_hr()) + ":" + String.format("%02d", dataProvider.getStart_min()) + "AM - " +
                        String.format("%02d", dataProvider.getEnd_hr()) + ":" + String.format("%02d", dataProvider.getEnd_min()) + "AM");
            }
        }

        return row;
    }
}
