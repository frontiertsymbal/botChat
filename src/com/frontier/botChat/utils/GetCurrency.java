package com.frontier.botChat.utils;

import com.frontier.botChat.utils.Parse.TodayParser;
import com.frontier.botChat.utils.Parse.UsdToday;

import java.util.List;

public class GetCurrency {

    public static String getCurrency() {
        List<UsdToday> list = TodayParser.parse(GetRequestToJSonString.getString(Const.TODAY_RATES_URL));
        String[] rate = new String[3];
        for (int i = 0; i < list.size(); i++) {
            rate[i] = list.get(i).getCurrency() + ": purchase "
                    + list.get(i).getPurchaseRate() + ", sale " + list.get(i).getSaleRate();
        }
        return rate[2] + "\n" + rate[1] + "\n" + rate[0];
    }
}
