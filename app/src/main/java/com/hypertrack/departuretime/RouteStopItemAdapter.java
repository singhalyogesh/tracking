package com.hypertrack.departuretime;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yogesh on 18/04/16.
 */
public class RouteStopItemAdapter extends BaseAdapter {

    Context context;
    List<RouteStopItem> rowItems;

    public RouteStopItemAdapter(Context context, List<RouteStopItem> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    private class ViewHolder {
        TextView stopName;
        TextView stopCode;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.route_stop_list_item, null);
            holder = new ViewHolder();

            holder.stopName = (TextView) convertView.findViewById(R.id.stop_name);
            holder.stopCode = (TextView) convertView.findViewById(R.id.stop_code);

            RouteStopItem row_pos = rowItems.get(position);

            holder.stopName.setText(row_pos.getName());
            holder.stopCode.setText(row_pos.getCode());

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

}
