package com.avenueinfotech.location.utils;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewUtils {
    public static void setTypeface(Typeface typeface, View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childs = viewGroup.getChildCount();
            for (int i = 0; i < childs; i++) {
                setTypeface(typeface, viewGroup.getChildAt(i));
            }
        }
        if (view instanceof TextView) {
            ((TextView) view).setTypeface(typeface);
        }
    }
}
