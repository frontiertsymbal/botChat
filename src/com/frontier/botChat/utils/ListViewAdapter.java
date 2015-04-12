package com.frontier.botChat.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
        return getItem(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItem(position) != null) {
            if (convertView == null) {
                switch (getItemViewType(position)) {
                    case Const.TYPE_SYSTEM:
                        convertView = inflater.inflate(R.layout.lv_item_system, parent, false);
                        TextView sysMessage = (TextView) convertView.findViewById(R.id.systemMessage);
                        sysMessage.setText(getItem(position).getMessage());
                        break;
                    case Const.TYPE_USER:
                        convertView = inflater.inflate(R.layout.lv_item_user, parent, false);
                        TextView userMessage = (TextView) convertView.findViewById(R.id.userMessage);
                        userMessage.setText(getItem(position).getMessage());
                        break;
                    case Const.TYPE_BOT:
                        convertView = inflater.inflate(R.layout.lv_item_bot, parent, false);
                        TextView botMessage = (TextView) convertView.findViewById(R.id.botMessage);
                        botMessage.setText(getItem(position).getMessage());
                        break;
                    case Const.TYPE_WEATHER:
                        convertView = inflater.inflate(R.layout.lv_item_weather, parent, false);
                        TextView weatherText = (TextView) convertView.findViewById(R.id.weatherText);
                        weatherText.setText(getItem(position).getMessage());
                        ImageView weatherImage = (ImageView) convertView.findViewById(R.id.weatherImage);
                        int image = convertView.getResources().getIdentifier("_"
                                + getItem(position).getImageId(), "drawable", getContext().getPackageName());
                        weatherImage.setImageResource(image);
                        break;
                }
            } else {
                User item = getItem(position);
                switch (getItemViewType(position)) {
                    case Const.TYPE_SYSTEM:
                        TextView sysMessage = (TextView) convertView.findViewById(R.id.systemMessage);
                        sysMessage.setText(item.getMessage());
                        break;
                    case Const.TYPE_USER:
                        TextView userMessage = (TextView) convertView.findViewById(R.id.userMessage);
                        userMessage.setText(item.getMessage());
                        break;
                    case Const.TYPE_BOT:
                        TextView botMessage = (TextView) convertView.findViewById(R.id.botMessage);
                        botMessage.setText(item.getMessage());
                        break;
                    case Const.TYPE_WEATHER:
                        TextView weatherText = (TextView) convertView.findViewById(R.id.weatherText);
                        weatherText.setText(getItem(position).getMessage());
                        ImageView weatherImage = (ImageView) convertView.findViewById(R.id.weatherImage);
                        int image = convertView.getResources().getIdentifier("_"
                                + getItem(position).getImageId(), "drawable", getContext().getPackageName());
                        weatherImage.setImageResource(image);
                        break;
                }
            }
        }
        return convertView;
    }
}
