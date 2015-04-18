package com.frontier.botChat.utils;

import android.util.Log;
import com.frontier.botChat.utils.Parse.TodayParser;
import com.frontier.botChat.utils.Parse.UsdToday;

import java.util.List;

public class GetCurrency {

    public static String getCurrency() {
        String currency = GetRequestToJSonString.getString(Const.TODAY_RATES_URL);
        Log.i(Const.LOG_TAG, currency);
        List<UsdToday> list = TodayParser.parse(currency);
        String[] rate = new String[3];
        for (int i = 0; i < list.size(); i++) {
            rate[i] = list.get(i).getCurrency() + ": purchase "
                    + list.get(i).getPurchaseRate() + ", sale " + list.get(i).getSaleRate();
        }
        return rate[2] + "\n" + rate[1] + "\n" + rate[0];
    }
}
