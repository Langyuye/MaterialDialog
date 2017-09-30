package com.langyuye.library.dialog.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ScreenUtlis
{
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }
    public static int getlocation(View view,Where where){
        int l=0;
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        if(where==Where.X){
            l = location[0];
        }
        else if(where==Where.Y){
            l = location[1];
        }
        return l;
    }
    public static void setLocation(Context context,View view,int x,int y){
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int screenWidth=ScreenUtlis.getScreenWidth(context);
        int screenHeight=ScreenUtlis.getScreenHeight(context);
        int statusBarSize=DesignUtils.getStatusBarHeight(context);
        int width=view.getMeasuredWidth();
        int height=view.getMeasuredHeight();
        y-=statusBarSize;
        if (x>=(screenWidth-width)){
            x-=width;
        }
        if (y>=(screenHeight-height)){
            y-=height;
        }
        ((RelativeLayout.LayoutParams)view.getLayoutParams()).setMargins(x,y,0,0);
    }
}
