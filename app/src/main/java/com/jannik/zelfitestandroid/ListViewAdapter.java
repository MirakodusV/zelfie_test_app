package com.jannik.zelfitestandroid;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jan on 28.12.2017.
 */

public class ListViewAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<String> type;
    private ArrayList<String> setup;

    public ListViewAdapter(Activity context, ArrayList<String> type, ArrayList<String> setup) {
        super();
        this.context = context;
        this.type = type;
        this.setup = setup;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return type.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    private class ViewHolder {
        TextView txtViewType;
        TextView txtViewSetup;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            // get both textview items so we can set the holder with both text view items and the "convert" the view
            convertView = inflater.inflate(R.layout.listitem_row, null);
            holder = new ViewHolder();
            holder.txtViewType = (TextView) convertView.findViewById(R.id.type);
            holder.txtViewSetup = (TextView) convertView.findViewById(R.id.setup);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtViewType.setText(type.get(position));
        holder.txtViewSetup.setText(setup.get(position));

        // change color
        changeTextColor(holder);

        return convertView;
    }

    private void changeTextColor(ViewHolder holder) {
        String type = (String)holder.txtViewType.getText();
        if (type.equals("knock-knock")) {

            holder.txtViewType.setTextColor(Color.RED);
            holder.txtViewSetup.setTextColor(Color.RED);
        }
        else {
            holder.txtViewType.setTextColor(Color.parseColor("#006400"));
            holder.txtViewSetup.setTextColor(Color.parseColor("#006400"));
        }
    }
}
