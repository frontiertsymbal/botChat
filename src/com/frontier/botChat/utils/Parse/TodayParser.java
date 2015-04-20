package com.frontier.botChat.utils.Parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TodayParser {

    public static List<UsdToday> parse(String json) {

        List<UsdToday> list = new ArrayList<>();
        JSONArray jsonarray = null;

        try {
            jsonarray = new JSONArray(json);

            for(int i=0; i<jsonarray.length(); i++) {
                JSONObject obj = jsonarray.getJSONObject(i);

                String ccy = obj.getString("ccy");
                double buy = Double.valueOf(obj.getString("buy"));
                double sale = Double.valueOf(obj.getString("sale"));

               list.add(new UsdToday(ccy, buy, sale));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
            return list;
    }
}
