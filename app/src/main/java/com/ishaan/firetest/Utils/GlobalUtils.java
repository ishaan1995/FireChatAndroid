package com.ishaan.firetest.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ishaan on 3/9/16.
 */
public class GlobalUtils {

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
