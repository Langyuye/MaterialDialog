package com.langyuye.library.dialog.utils;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.Context;
import android.graphics.Color;

import com.langyuye.library.R;

/**
 * Created by Langyuye on 2017/3/28.
 */
public class DesignUtils
{
	public static float getActionBarSize(Context context){
        TypedArray actionbarSizeTypedArray = context.obtainStyledAttributes(new int[] { R.attr.actionBarSize });
        return actionbarSizeTypedArray.getDimension(0, 0);
    }
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
	public static int getColorPrimary(Context context){
		TypedArray colorPrimaryTypedArray = context.obtainStyledAttributes(new int[] { R.attr.colorPrimary });
		return colorPrimaryTypedArray.getColor(0,Color.parseColor("#282828"));
	}
    public static int getColorPrimaryDark(Context context){
        TypedArray colorPrimaryTypedArray = context.obtainStyledAttributes(new int[] { R.attr.colorPrimaryDark });
        return colorPrimaryTypedArray.getColor(0,Color.parseColor("#282828"));
    }
    public static int getColorAccent(Context context){
        TypedArray colorAceentTypedArray = context.obtainStyledAttributes(new int[] { R.attr.colorAccent });
        return colorAceentTypedArray.getColor(0, Color.parseColor("#282828"));
    }
    public static ColorStateList createColorStateList(int normal, int pressed, int focused, int unable) {
        int[] colors = new int[] { pressed, focused, normal, focused, unable, normal };
        int[][] states = new int[6][];
        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        states[2] = new int[] { android.R.attr.state_enabled };
        states[3] = new int[] { android.R.attr.state_focused };
        states[4] = new int[] { android.R.attr.state_window_focused };
        states[5] = new int[] {};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }
}
