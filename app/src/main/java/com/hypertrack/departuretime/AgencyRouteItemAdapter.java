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
public class AgencyRouteItemAdapter extends BaseAdapter {


    Context context;
    List<AgencyRouteItem> rowItems;

    public AgencyRouteItemAdapter(Context context, List<AgencyRouteItem> rowItems) {
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
        TextView code;
        TextView routeDirectionCode;
        TextView routeDirectionName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.agency_route_list_item, null);
            holder = new ViewHolder();

            holder.routeName = (TextView) convertView.findViewById(R.id.route_name);
            holder.code = (TextView) convertView.findViewById(R.id.route_code);
            holder.routeDirectionName = (TextView) convertView.findViewById(R.id.route_direction_name);
            holder.routeDirectionCode = (TextView) convertView.findViewById(R.id.route_direction_code);

            AgencyRouteItem row_pos = rowItems.get(position);

            holder.routeName.setText(row_pos.getName());
            holder.code.setText(row_pos.getCode());
            holder.routeDirectionName.setText(row_pos.getRouteDirectionName() );
            holder.routeDirectionCode.setText(row_pos.getRouteDirectionCode() );

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

}
