package com.example.langyuye.materialdialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.langyuye.library.dialog.app.MaterialDialog;

/**
 * Created by langyuye on 17-9-30.
 */

public class LocationTestActivity extends AppCompatActivity {
    GestureDetectorCompat mGesture;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        mGesture=new GestureDetectorCompat(this,new MyGestureDetector());
    }
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int x=(int)e.getX();
            int y=(int)e.getY();
            View view= LayoutInflater.from(LocationTestActivity.this).inflate(R.layout.my_dialog_2,null);
            Toast.makeText(LocationTestActivity.this,"X:"+x+"，Y:"+y,Toast.LENGTH_SHORT).show();
            new MaterialDialog(LocationTestActivity.this)
                    .setDialogWidth(350)
                    .setAtLocation(x,y)
                    .setView(view)
                    .show();
            return true;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGesture.onTouchEvent(event);
    }
}
