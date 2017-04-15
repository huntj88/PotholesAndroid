package com.example.james.potholes.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.james.potholes.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by James on 4/13/2017.
 */

public class SavedPrefsUtils {

    public static String getLocalAccessToken(Context context)
    {
        SharedPreferences userDetails = context.getSharedPreferences("userdetails", MODE_PRIVATE);
        String token = userDetails.getString(context.getString(R.string.access_token), "");

        return token;
    }

    public static void setLocalAccessToken(Context context, String accessToken)
    {
        SharedPreferences userDetails = context.getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = userDetails.edit();
        editor.putString(context.getString(R.string.access_token), accessToken);
        editor.commit();
    }
}
