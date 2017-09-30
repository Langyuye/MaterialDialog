package com.example.langyuye.materialdialog;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.langyuye.library.dialog.app.MaterialDialog;
import com.langyuye.library.dialog.app.listener.OnDialogClickListener;
import com.langyuye.library.dialog.app.listener.OnDialogItemClickListener;
import com.langyuye.library.dialog.app.listener.OnDialogMultipleChoiceListener;
import com.langyuye.library.dialog.app.listener.OnDialogSingleChoiceListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> list=new ArrayList<>();
    SwitchCompat mDark;
    SwitchCompat mCancel;
    SwitchCompat mBold;
    RadioGroup mGravity;
    SwitchCompat mBbold;
    RadioGroup mButton;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i=0;i<10;i++){
            list.add("ITEM "+i);
        }
        init();
    }
    private void init(){
        mDark=findViewById(R.id.dark);
        mCancel=findViewById(R.id.cancel);
        mBold=findViewById(R.id.bold);
        mGravity=findViewById(R.id.gravity);
        mBbold=findViewById(R.id.bbold);
        mButton=findViewById(R.id.button);
    }
    /*
     dialog.setTitleColor()　　//设置标题字体颜色
     dialog.setTitleSize() 　//设置标题字体大小
     dialog.setMessageColor() 　//设置Message字体颜色
     dialog.setMessageSize() 　//设置Message字体大小
     dialog.setColorAccent()  //设置通用的颜色，比如按钮颜色，单选复选按钮颜色
     dialog.setButtonTextColor()  //设置按钮字体颜色
     dialog.setButtonTextSize()　　//设置按钮字体大小
     dialog.setButtonTextBold()　　//设置按钮字体粗体
     dialog.setItemTextSize()  //设置列表字体大小
     dialog.setItemIconSize()  //设置列表图标大小
     dialog.setBackground()  //通过drawable设置窗体背景
     dialog.setBackgroundColor() //通过color设置窗体背景
     dialog.setBackgroundResource() //通过资源文件设置窗体背景
     dialog.setDialogWidth(size)  //设置窗体宽度范围０－１，屏幕宽度*size
     dialog.setOnDismissListener(new OnDismissListener);  //监听窗口取消
    * */
    public void CustomDialog(View view){
        MaterialDialog dialog=new MaterialDialog(MainActivity.this);
        dialog.setAtLocation(0,0);
        //标题
        dialog.setTitle("标题");
        //设置标题位置
        if (((RadioButton)mGravity.getChildAt(0)).isChecked()){
            dialog.setTitleGravity(Gravity.START);//　或者Gravity.START
        }
        else if (((RadioButton)mGravity.getChildAt(1)).isChecked()){
            dialog.setTitleGravity(Gravity.CENTER);
        }
        else if (((RadioButton)mGravity.getChildAt(2)).isChecked()){
            dialog.setTitleGravity(Gravity.END); //　或者Gravity.RIGHT
        }
        //标题粗体
        dialog.setTitleBold(mBold.isChecked());
        //Message
        dialog.setMessage("这里是提示内容，不要在意内容是什么");
        //窗口背景样色
        dialog.setAttributes(mDark.isChecked()? MaterialDialog.Attributes.DARK : MaterialDialog.Attributes.TRANSPARENT);
        //不可自动取消
        dialog.setCanceledOnTouchSide(!mCancel.isChecked());
        //按钮字体粗体
        dialog.setButtonTextBold(mBbold.isChecked());
        //添加按钮
        if (((RadioButton)mButton.getChildAt(0)).isChecked()){
            //不添加按钮监听事件，则用null表示，会自动触发dismiss事件
            dialog.addButton("B1",null);
            dialog.addButton("B２",null);
            dialog.addButton("B３",null);
            dialog.addButton("B4",null);
        }
        else if (((RadioButton)mButton.getChildAt(1)).isChecked()){
            dialog.setPositionButton("B1", new OnDialogClickListener() {
                @Override
                public boolean onDialogClick(MaterialDialog dialog, int id, boolean isShow) {
                    //id=MaterialDialog.POSITIVE;
                    return false; //返回true时会自动触发自动取消dismiss，否则相反
                }
            });
            dialog.setNegativeButton("B2", new OnDialogClickListener() {
                @Override
                public boolean onDialogClick(MaterialDialog dialog, int id, boolean isShow) {
                    return false;
                }
            });
            dialog.setNeutralButton("B3", new OnDialogClickListener() {
                @Override
                public boolean onDialogClick(MaterialDialog dialog, int id, boolean isShow) {
                    return true;
                }
            });
        }

        //初始化参数后再显示
        dialog.show();
    }
    public void addView(View view){
        ImageView imageView=new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher);
        new MaterialDialog(this)
                .setTitle("这是一个自定义View的Dialog")
                .setAttributes(MaterialDialog.Attributes.DARK)
                .addView(imageView)
                .setPositionButton("button",null)
                .show();
    }
    public void setView(View view){
        View layout= LayoutInflater.from(this).inflate(R.layout.my_dialog,null);
        new MaterialDialog(this)
                .setAttributes(MaterialDialog.Attributes.DARK)
                .setView(layout)
                .show();
    }
    public void setAnim(View view){
        /*new MaterialDialog(this,R.style.my_dialog_anim)
                .setAttributes(MaterialDialog.Attributes.DARK)
                .setTitle("标题")
                .setMessage("这是一个自定义动画的Dialog")
                .show();*/
        new MaterialDialog(this)
                .setAnimationStyle(R.style.my_dialog_anim)
                .setAttributes(MaterialDialog.Attributes.DARK)
                .setTitle("标题")
                .setMessage("这是一个自定义动画的Dialog")
                .show();
    }
    public void item(View view){
        new MaterialDialog(this)
                .setTitle("列表框")
                .setAttributes(MaterialDialog.Attributes.TRANSPARENT)
                .setItem(list, new OnDialogItemClickListener() {
                    @Override
                    public boolean onItemClick(MaterialDialog dialog, int position) {
                        Toast.makeText(MainActivity.this,list.get(position),Toast.LENGTH_SHORT).show();
                        return true;
                    }
                })
                .setPositionButton("取消",null)
                .show();
    }
    public void singleChoice(View view){
        new MaterialDialog(this)
                .setTitle("单选框")
                .setAttributes(MaterialDialog.Attributes.DARK)
                .setItemChecked(0,true)//默认选中
                .setSingalChoiceItem(list, new OnDialogSingleChoiceListener() {
                    @Override
                    public boolean onItemChoice(MaterialDialog dialog, int position) {
                        Toast.makeText(MainActivity.this,list.get(position),Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .setPositionButton("取消",null)
                .show();
    }
    public void multipleChoice(View view){
        new MaterialDialog(this)
                .setTitle("单选框")
                .setAttributes(MaterialDialog.Attributes.DARK)
                //批量添加默认选中
                .addItemChecked(0,true)
                .addItemChecked(3,true)
                .addItemChecked(6,true)
                .setMultipleChoiceItem(list, new OnDialogMultipleChoiceListener() {
                    @Override
                    public boolean onMultipleChoice(MaterialDialog dialog, int position, SparseBooleanArray isCheckArray) {

                        return false;
                    }
                })
                .setPositionButton("确定", new OnDialogClickListener() {
                    @Override
                    public boolean onDialogClick(MaterialDialog dialog, int id, boolean isShow) {
                        SparseBooleanArray sparseBooleanArray=dialog.getSelectPositions(); //获取已经选中的值
                        StringBuilder sb=new StringBuilder();
                        for (int i =0;i<sparseBooleanArray.size();i++){
                            int position=sparseBooleanArray.keyAt(i);  //哪个位置
                            boolean value=sparseBooleanArray.get(i);  //是否选中
                            sb.append(position)
                                    .append("：")
                                    .append(value)
                                    .append(i==sparseBooleanArray.size()-1 ? "" :"\n");
                        }
                        Toast.makeText(MainActivity.this,sb.toString(),Toast.LENGTH_SHORT).show();
                        return true;
                    }
                })
                .show();
    }
    public void LocationDialog(View view){
        startActivity(new Intent(this,LocationTestActivity.class));
    }
}
