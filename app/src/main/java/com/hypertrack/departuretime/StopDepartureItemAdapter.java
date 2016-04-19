package com.hypertrack.departuretime;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Yogesh on 18/04/16.
 */
public class StopDepartureItemAdapter extends BaseAdapter {

    Context context;
    List<StopDepartureItem> rowItems;

    public StopDepartureItemAdapter(Context context, List<StopDepartureItem> rowItems) {
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
        TextView routeName;
        TextView routeCode;
        TextView routeDirectionCode;
        TextView routeDirectionName;
        TextView departureTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.stop_departure_list_item, null);
            holder = new ViewHolder();

            holder.routeName = (TextView) convertView.findViewById(R.id.departure_route_name);
            holder.routeCode = (TextView) convertView.findViewById(R.id.departure_route_code);
            holder.routeDirectionCode = (TextView) convertView.findViewById(R.id.route_direction_code);
            holder.routeDirectionName = (TextView) convertView.findViewById(R.id.route_direction_name);
            holder.departureTime = (TextView) convertView.findViewById(R.id.departure_time);

            StopDepartureItem row_pos = rowItems.get(position);

            holder.routeName.setText(row_pos.getRouteName());
            holder.routeCode.setText(row_pos.getRouteCode());
            holder.routeDirectionName.setText(row_pos.getRouteDirectionName());
            holder.routeDirectionCode.setText(row_pos.getRouteDirectionCode());
            holder.departureTime.setText(row_pos.getDepartureTime());

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }


}
