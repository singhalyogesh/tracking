package com.hypertrack.departuretime;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yogesh on 17/04/16.
 */
public class AgencyListItemAdapter extends BaseAdapter {

    Context context;
    List<AgencyListItem> rowItems;

    public AgencyListItemAdapter(Context context, List<AgencyListItem> rowItems) {
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
        TextView agencyName;
        TextView hasDirection;
        TextView mode;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.agency_list_item, null);
            holder = new ViewHolder();

            holder.agencyName = (TextView) convertView.findViewById(R.id.agency_name);
            holder.hasDirection = (TextView) convertView.findViewById(R.id.has_direction);
            holder.mode = (TextView) convertView.findViewById(R.id.mode);

            AgencyListItem row_pos = rowItems.get(position);

            holder.agencyName.setText(row_pos.getName());
            holder.hasDirection.setText("HasDirection : "+row_pos.getHasDirection());
            holder.mode.setText("Mode : "+row_pos.getMode());

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

}
