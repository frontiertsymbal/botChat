package com.frontier.botChat.utils;

import android.util.Log;
import com.frontier.botChat.utils.Parse.TodayParser;
import com.frontier.botChat.utils.Parse.UsdToday;

import java.util.List;

public class GetCurrency {

    public static String getCurrency() {
        try {
            String currency = GetRequestToJSonString.getString(Const.TODAY_RATES_URL);
            if (currency != null) {
                Log.i(Const.LOG_TAG, currency);
            } else {
                Log.e(Const.LOG_TAG, "Currency json null string.");
            }
            List<UsdToday> list = TodayParser.parse(currency);
            String[] rate = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                rate[i] = list.get(i).getCurrency() + ": purchase "
                        + list.get(i).getPurchaseRate() + ", sale " + list.get(i).getSaleRate();
            }
            return rate[2] + "\n" + rate[1] + "\n" + rate[0];
        } catch (Exception e) {
            Log.e(Const.LOG_TAG, "Currency server error");
            e.printStackTrace();
        }
        return "Server error";
    }
}
