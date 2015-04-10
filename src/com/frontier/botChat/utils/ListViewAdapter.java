package com.frontier.botChat.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.frontier.botChat.R;
import com.frontier.botChat.User;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<User> {

    private final LayoutInflater inflater;

    public ListViewAdapter(Context context, List objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        switch (getItem(position).getType()) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            if (getItemViewType(position) == 0 && getItem(position) != null) {

                convertView = inflater.inflate(R.layout.lv_item_system, parent, false);
                TextView sysMessage = (TextView) convertView.findViewById(R.id.systemMessage);
                sysMessage.setText(getItem(position).getMessage());
            }
            if (getItemViewType(position) == 1 && getItem(position) != null) {

                convertView = inflater.inflate(R.layout.lv_item_user, parent, false);
                TextView userMessage = (TextView) convertView.findViewById(R.id.userMessage);
                userMessage.setText(getItem(position).getMessage());
            }
            if (getItemViewType(position) == 2 && getItem(position) != null) {

                convertView = inflater.inflate(R.layout.lv_item_bot, parent, false);
                TextView botMessage = (TextView) convertView.findViewById(R.id.botMessage);
                botMessage.setText(getItem(position).getMessage());
            }
        } else {
            User item = getItem(position);
            if (getItemViewType(position) == 0 && getItem(position) != null) {
                TextView sysMessage = (TextView) convertView.findViewById(R.id.systemMessage);
                sysMessage.setText(item.getMessage());
            }
            if (getItemViewType(position) == 1 && getItem(position) != null) {
                TextView userMessage = (TextView) convertView.findViewById(R.id.userMessage);
                userMessage.setText(item.getMessage());
            }
            if (getItemViewType(position) == 2 && getItem(position) != null) {
                TextView botMessage = (TextView) convertView.findViewById(R.id.botMessage);
                botMessage.setText(item.getMessage());
            }
        }

        return convertView;
    }
}
