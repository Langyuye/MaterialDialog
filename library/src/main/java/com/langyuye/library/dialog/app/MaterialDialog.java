package com.langyuye.library.dialog.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.langyuye.library.R;
import com.langyuye.library.dialog.adapter.ListViewChoiceAdapter;
import com.langyuye.library.dialog.adapter.ListViewClickAdapter;
import com.langyuye.library.dialog.app.listener.OnDialogItemClickListener;
import com.langyuye.library.dialog.app.listener.OnDialogClickListener;
import com.langyuye.library.dialog.app.listener.OnDialogMultipleChoiceListener;
import com.langyuye.library.dialog.app.listener.OnDialogSingleChoiceListener;
import com.langyuye.library.dialog.app.listener.OnDismissListener;
import com.langyuye.library.dialog.app.listener.OnItemClickListener;
import com.langyuye.library.dialog.utils.DesignUtils;
import com.langyuye.library.dialog.utils.ScreenUtlis;
import com.langyuye.library.dialog.view.DialogLayout;
import com.langyuye.library.dialog.view.RippleTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by langyuye on 17-9-16.
 */

public class MaterialDialog {
    public enum  Attributes {
        TRANSPARENT,DARK
    }
    public static final int POSITIVE=1;
    public static final int NEGATIVE=2;
    public static final int NEUTRAL=3;

    private PopupWindow popupWindow;
    private Context mContext;
    private View mView=null;
    private View childView=null;
    private TextView mTitle=null;
    private TextView mMessage=null;
    private LinearLayout mLayout=null;
    private OnDismissListener dismissListener;  //取消弹窗监听
    private boolean isCanceledOnTouchSide=true; //默认点击其他地方取消弹窗
    private int dialog_anim=R.style.custom_dialog_anim;  //弹窗动画
    private Attributes attribute=Attributes.TRANSPARENT; //弹窗弹出后，其他地方变暗，默认透明
    //设置背景
    private int backgroundColor=0;
    private int backgroundRes=0;
    private Drawable backgroundDrawable=null;

    //Dialog宽度=屏幕宽度*size
    private float size=0.85f;
    private int dialogWidth=0;
    private int x=-1;
    private int y=-1;
    //ColorAccent
    private int colorAccent=-1;
    private int mDialogGravity=Gravity.CENTER;  //窗体体位置

    private String title="";  //标题
    private float titleSize=18f;  //标题大小
    private boolean isTitleBold=true;  //标题粗体
    private int titleColor =Color.parseColor("#282828");  //标题颜色
    private int titleGravity=Gravity.START; //标题位置

    private String message="";  //提示信息
    private float messageSize=16f;//提示信息字体大小
    private int messageColor=Color.parseColor("#282828");  //提示信息字体颜色

    private List<String> mButton=new ArrayList<>();
    private List<OnDialogClickListener> mListener=new ArrayList<>();

    private float buttonSize=15.5f; //按钮字体大小
    private int buttonColor=0; //按钮字体颜色
    private boolean buttonBold=true; //按钮字体粗体

    private int iconSize=16;
    private float itemTextSize=14f;

    private List<String> values=new ArrayList<>();
    private SparseBooleanArray itemCheck=new SparseBooleanArray();
    private List<Integer> items=new ArrayList<>();
    private List<Boolean> isCheck=new ArrayList<>();

    private int choiceMode=ListView.CHOICE_MODE_NONE;
    private OnDialogItemClickListener onDialogItemClickListener;
    private OnDialogSingleChoiceListener onDialogSingleChoiceListener;
    private OnDialogMultipleChoiceListener onDialogMultipleChoiceListener;

    private String PositionText,NegativeText,NeutralText;
    private OnDialogClickListener PositionListener,NegativeListener,NeutralListener;

    public MaterialDialog(Context context){
        this.mContext=context;
    }
    public MaterialDialog(Context context, int animStyle){
        this.mContext=context;
        this.dialog_anim=animStyle;
    }
    public class Builder{
        Builder(final Context context, final Activity activity,int anim){
            if (colorAccent==-1){
                colorAccent=DesignUtils.getColorAccent(context);
                if (buttonColor==0){
                    buttonColor=colorAccent;
                }
            }
            else{
                if (buttonColor==0) {
                    buttonColor = colorAccent;
                }
            }
            View  v= LayoutInflater.from(context).inflate(R.layout.langyuye_dialog_layout, null,false);
            initView(v,context);
            popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.setFocusable(isCanceledOnTouchSide);
            popupWindow.setOutsideTouchable(isCanceledOnTouchSide);
            popupWindow.setAnimationStyle(anim);
            popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER,0,0);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (dismissListener!=null){
                        dismissListener.onDismiss();
                    }
                    initAttributes(activity,1f,false);
                }
            });
            initAttributes(activity,0.5f,true);
        }
        @SuppressLint("NewApi")
        private void initView(View view, final Context context){
            RelativeLayout mDialogLayout=(RelativeLayout) view.findViewById(R.id.dialog_layout_window);
            mLayout=(LinearLayout)view.findViewById(R.id.dialog_layout);
            LinearLayout titleView=(LinearLayout)view.findViewById(R.id.title_layout);
            DialogLayout contentView=(DialogLayout) view.findViewById(R.id.content_layout);
            RelativeLayout buttonView=(RelativeLayout)view.findViewById(R.id.button_layout);
            if (mView!=null){
                mLayout.setBackground(null);
                mLayout.addView(mView);
                view.findViewById(R.id.default_layout).setVisibility(View.GONE);
            }
            if (backgroundColor!=0){
                mLayout.setBackgroundColor(backgroundColor);
            }
            if (backgroundRes!=0){
                mLayout.setBackgroundResource(backgroundRes);
            }
            if (backgroundDrawable!=null){
                mLayout.setBackground(backgroundDrawable);
            }
            int screenWith= ScreenUtlis.getScreenWidth(context);
            if (dialogWidth!=0){
                mLayout.getLayoutParams().width=dialogWidth;
            }
            else {
                mLayout.getLayoutParams().width=(int)(screenWith*size);
            }
            mDialogLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isCanceledOnTouchSide){
                        dismiss();
                    }
                }
            });
            if (!title.isEmpty()){
                titleView.setPadding(30,10,30,10);
                titleView.setGravity(Gravity.CENTER_VERTICAL);
                mTitle=new TextView(context);
                mTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                mTitle.setGravity(titleGravity);
                mTitle.setTextColor(titleColor);
                mTitle.setTextSize(titleSize);
                mTitle.setSingleLine(true);
                mTitle.setEllipsize(TextUtils.TruncateAt.END);
                mTitle.setText(title);
                setBold(mTitle,isTitleBold);
                titleView.addView(mTitle);
            }
            if (!message.isEmpty()){
                contentView.setPadding(20,20,20,20);
                ScrollView scrollView=new ScrollView(context);
                scrollView.setOverScrollMode(ScrollView.OVER_SCROLL_NEVER);
                mMessage=new TextView(context);
                mMessage.setLineSpacing(1f,1.25f);
                mMessage.setTextSize(messageSize);
                mMessage.setTextColor(messageColor);
                mMessage.setText(message);
                scrollView.addView(mMessage);
                contentView.addView(scrollView);
            }
            else {
                if (childView!=null){
                    contentView.setPadding(20,20,20,20);
                    contentView.addView(childView);
                }else if (values.size()!=0){
                    ListView listView=new ListView(context);
                    listView.setPadding(0,20,0,20);
                    listView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
                    listView.setDividerHeight(0);
                    listView.setChoiceMode(choiceMode);
                    contentView.addView(getListView(context,listView,choiceMode));
                    listView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                }
            }
            if (mButton.size()!=0){
                LinearLayout linearLayout=new LinearLayout(context);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setPadding(20,0,20,0);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setGravity(Gravity.END);
                Collections.reverse(mButton);
                Collections.reverse(mListener);
                for (int i=0;i<mButton.size();i++){
                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1);
                    if (mButton.size()==1){
                        layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    }
                    View button=getButton(context,mButton.get(i),i,mListener.get(i));
                    linearLayout.addView(button,layoutParams);
                }
                buttonView.addView(linearLayout);
            }
            else {
                if (PositionText!=null){
                    View button=getButton(context,PositionText,POSITIVE,PositionListener);
                    buttonView.addView(button,getParams(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE));
                }
                if (NegativeText!=null){
                    View button=getButton(context,NegativeText,NEGATIVE,NegativeListener);
                    if (PositionText!=null){
                        buttonView.addView(button,getParams(RelativeLayout.LEFT_OF,POSITIVE));
                    }
                    else {
                        buttonView.addView(button,getParams(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE));
                    }
                }
                if (NeutralText!=null){
                    View button=getButton(context,NeutralText,NEUTRAL,NeutralListener);
                    buttonView.addView(button,getParams(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE));
                }
            }
            if (x!=-1&&y!=-1){
                ScreenUtlis.setLocation(context,mLayout,x,y);
            }
            else {
                addRule(mLayout);
            }
        }
        private View getListView(Context context,ListView listView,int mode){
            listView.setChoiceMode(mode);
            if (mode==ListView.CHOICE_MODE_NONE){
                ListViewClickAdapter adapter=new ListViewClickAdapter(context,values,itemTextSize);
                listView.setAdapter(adapter);
                adapter.setOnItemClickListener(new onItemClickListener(listView));
            }
            if (mode==ListView.CHOICE_MODE_SINGLE||mode==ListView.CHOICE_MODE_MULTIPLE){
                ListViewChoiceAdapter adapter=new ListViewChoiceAdapter(context,values,colorAccent,iconSize,itemTextSize);
                listView.setAdapter(adapter);
                if (items.size()!=0){
                    for (int i =0;i<items.size();i++){
                        listView.setItemChecked(items.get(i),isCheck.get(i));
                        itemCheck.append(items.get(i),isCheck.get(i));
                    }
                }
                adapter.setOnItemClickListener(new onItemClickListener(listView));
            }
            return listView;
        }
        private class onItemClickListener implements OnItemClickListener{
            private ListView mListView;
            public onItemClickListener(ListView listView){
                this.mListView=listView;
            }
            @Override
            public void onItemClick(View view, int position) {
                if (onDialogItemClickListener!=null&&choiceMode==ListView.CHOICE_MODE_NONE){
                    boolean isShow=onDialogItemClickListener.onItemClick(MaterialDialog.this,position);
                    if (isShow){
                        dismiss();
                    }
                }
                else if (onDialogSingleChoiceListener!=null&&choiceMode==ListView.CHOICE_MODE_SINGLE){
                    mListView.setItemChecked(position,true);
                    boolean isShow=onDialogSingleChoiceListener.onItemChoice(MaterialDialog.this,position);
                    if (isShow){
                        dismiss();
                    }
                    SparseBooleanArray booleanArray=mListView.getCheckedItemPositions();
                    if (booleanArray.get(position)){
                        itemCheck.append(position,false);
                    }
                    else {
                        itemCheck.append(position,true);
                    }
                }
                else if (onDialogMultipleChoiceListener!=null&&choiceMode==ListView.CHOICE_MODE_MULTIPLE){
                    SparseBooleanArray booleanArray=mListView.getCheckedItemPositions();
                    if (booleanArray.get(position)){
                        mListView.setItemChecked(position,false);
                        itemCheck.append(position,false);
                    }
                    else {
                        mListView.setItemChecked(position,true);
                        itemCheck.append(position,true);
                    }
                    boolean isShow=onDialogMultipleChoiceListener.onMultipleChoice(MaterialDialog.this,position,itemCheck);
                    if (isShow){
                        dismiss();
                    }
                }
                else {
                    dismiss();
                }
            }
        }
        private void addRule(View view){
            int mGravity1=-1;
            int mGravity2=-1;
            switch (mDialogGravity){
                case Gravity.TOP:
                    mGravity1=RelativeLayout.ALIGN_PARENT_TOP;
                    break;
                case Gravity.CENTER:
                    mGravity1=RelativeLayout.CENTER_IN_PARENT;
                    break;
                case Gravity.BOTTOM:
                    mGravity1=RelativeLayout.ALIGN_PARENT_BOTTOM;
                    break;
                case Gravity.LEFT:
                case Gravity.START:
                    mGravity1=RelativeLayout.ALIGN_PARENT_LEFT;
                    break;
                case Gravity.RIGHT:
                case Gravity.END:
                    mGravity1=RelativeLayout.ALIGN_PARENT_RIGHT;
                    break;
                case Gravity.CENTER_HORIZONTAL|Gravity.TOP:
                    mGravity1=RelativeLayout.CENTER_HORIZONTAL;
                    mGravity2=RelativeLayout.ALIGN_PARENT_TOP;
                    break;
                case Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM:
                    mGravity1=RelativeLayout.CENTER_HORIZONTAL;
                    mGravity2=RelativeLayout.ALIGN_PARENT_BOTTOM;
                    break;
                case Gravity.CENTER_VERTICAL|Gravity.START:
                case Gravity.CENTER_VERTICAL|Gravity.LEFT:
                    mGravity1=RelativeLayout.CENTER_VERTICAL;
                    mGravity2=RelativeLayout.ALIGN_PARENT_LEFT;
                    break;
                case Gravity.CENTER_VERTICAL|Gravity.END:
                case Gravity.CENTER_VERTICAL|Gravity.RIGHT:
                    mGravity1=RelativeLayout.CENTER_VERTICAL;
                    mGravity2=RelativeLayout.ALIGN_PARENT_RIGHT;
                    break;
            }
            if (mGravity1!=-1){
                ((RelativeLayout.LayoutParams)view.getLayoutParams()).addRule(mGravity1);
            }
            if (mGravity2!=-1){
                ((RelativeLayout.LayoutParams)view.getLayoutParams()).addRule(mGravity2);
            }

        }
        private RelativeLayout.LayoutParams getParams(int verb ,int subject){
            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(verb,subject);
            return layoutParams;
        }
        private View getButton(Context context, String string, final int position, final OnDialogClickListener listener){
            RippleTextView button=new RippleTextView(context);
            button.setId(position);
            button.setVisibility(string.isEmpty()? View.GONE : View.VISIBLE);
            button.setPadding(30,10,30,10);
            button.setGravity(Gravity.CENTER_HORIZONTAL);
            button.setTextColor(buttonColor);
            button.setTextSize(buttonSize);
            button.setText(string);
            setBold(button,buttonBold);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        boolean isDismiss=listener.onDialogClick(MaterialDialog.this,position,isShow());
                        if (isDismiss){
                            dismiss();
                        }
                    }
                    else {
                        dismiss();
                    }
                }
            });
            return button;
        }
        private void initAttributes(Activity activity,float alpha,boolean isShow){
            if (attribute!=Attributes.TRANSPARENT){
                if (isShow){
                    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                }
                else {
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                }
                if (attribute==Attributes.DARK){
                    WindowManager.LayoutParams params=activity.getWindow().getAttributes();
                    params.alpha=alpha;
                    activity.getWindow().setAttributes(params);
                }
            }
        }
    }
    private void setBold(TextView view,boolean isBold){
        TextPaint tp=view.getPaint();
        tp.setFilterBitmap(true);
        tp.setFakeBoldText(isBold);
    }
    public MaterialDialog show(){
        if (popupWindow==null){
           new Builder(mContext,(Activity)mContext,dialog_anim);
        }
        return this;
    }
    public MaterialDialog dismiss(){
        if (popupWindow!=null&&popupWindow.isShowing()){
            popupWindow.dismiss();
            popupWindow=null;
        }
        return this;
    }
    public boolean isShow(){
        return popupWindow!=null&&popupWindow.isShowing();
    }
    public MaterialDialog addView(View view){
        this.childView=view;
        return this;
    }
    public MaterialDialog setView(View view){
        this.mView=view;
        return this;
    }
    public View getView(){
        return mView;
    }
    public MaterialDialog setDialogSize(float size){
        if (size>1f){
            size=1f;
        }
        this.size=size;
        return this;
    }
    public MaterialDialog setDialogWidth(int width){
        this.dialogWidth=width;
        return this;
    }
    public float getDialogSize(){
        return size;
    }
    public float getDialogWidth(){
        return dialogWidth;
    }
    public MaterialDialog setAttributes(Attributes attribute){
        this.attribute=attribute;
        return this;
    }
    public Attributes getAttributes(){
        return attribute;
    }
    public MaterialDialog setCanceledOnTouchSide(boolean isCanceledOnTouchSide){
        this.isCanceledOnTouchSide=isCanceledOnTouchSide;
        return this;
    }
    public boolean isCanceledOnTouchSide(){
        return isCanceledOnTouchSide;
    }
    public void setOnDismissListener(OnDismissListener dismissListener){
        this.dismissListener=dismissListener;
    }
    public MaterialDialog setAnimationStyle(int animationStyle){
        this.dialog_anim=animationStyle;
        return this;
    }
    @SuppressLint("NewApi")
    public MaterialDialog setBackground(Drawable drawable){
        this.backgroundDrawable=drawable;
        if (mLayout!=null){
            mLayout.setBackground(drawable);
        }
        return this;
    }
    public MaterialDialog setBackgroundResource(int resId){
        this.backgroundRes=resId;
        if (mLayout!=null){
            mLayout.setBackgroundResource(resId);
        }
        return this;
    }
    public MaterialDialog setBackgroundColor(int color){
        this.backgroundColor=color;
        if (mLayout!=null){
            mLayout.setBackgroundColor(color);
        }
        return this;
    }
    public MaterialDialog setTitle(String title){
        this.title=title;
        if (mTitle!=null){
            mTitle.setText(title);
        }
        return this;
    }
    public String getTitle(){
        return title;
    }
    public MaterialDialog setTitleGravity(int gravity){
        this.titleGravity=gravity;
        if (mTitle!=null){
            mTitle.setGravity(Gravity.CENTER_VERTICAL|gravity);
        }
        return this;
    }
    public int getTitleGravity(){
        return titleGravity;
    }
    public MaterialDialog setTitleColor(int color){
        this.titleColor=color;
        if (mTitle!=null){
            mTitle.setTextColor(color);
        }
        return this;
    }
    public int getTitleColor(){
        return titleColor;
    }
    public MaterialDialog setTitleSize(float size){
        this.titleSize=size;
        if (mTitle!=null){
            mTitle.setTextSize(size);
        }
        return this;
    }
    public float getTitleSize(){
        return titleSize;
    }
    public MaterialDialog setTitleBold(boolean isBold){
        this.isTitleBold=isBold;
        if (mTitle!=null){
            setBold(mTitle,isBold);
        }
        return this;
    }
    public boolean isTitleBold(){
        return isTitleBold;
    }
    public MaterialDialog setMessage(String message){
        this.message=message;
        if (mMessage!=null){
            mMessage.setText(message);
        }
        return this;
    }
    public String getMessage(){
        return message;
    }
    public MaterialDialog setMessageColor(int color){
        this.messageColor=color;
        if (mMessage!=null){
            mMessage.setTextColor(color);
        }
        return this;
    }
    public int getMessageColor(){
        return messageColor;
    }
    public MaterialDialog setMessageSize(float size){
        this.messageSize=size;
        if (mMessage!=null){
            mMessage.setTextSize(size);
        }
        return this;
    }
    public float getMessageSize(){
        return messageSize;
    }
    public MaterialDialog addButton(String text, OnDialogClickListener listener){
        if (!text.isEmpty()){
            mButton.add(text);
            mListener.add(listener);
        }
        return this;
    }
    public MaterialDialog setPositionButton(String text, OnDialogClickListener listener){
        this.PositionText=text;
        this.PositionListener=listener;
        return this;
    }
    public MaterialDialog setNegativeButton(String text, OnDialogClickListener listener){
        this.NegativeText=text;
        this.NegativeListener=listener;
        return this;
    }
    public MaterialDialog setNeutralButton(String text, OnDialogClickListener listener){
        this.NeutralText=text;
        this.NeutralListener=listener;
        return this;
    }
    public MaterialDialog setButtonTextBold(boolean isBold){
        this.buttonBold=isBold;
        return this;
    }
    public boolean getButtonTextBold(){
        return buttonBold;
    }
    public MaterialDialog setButtonTextColor(int color){
        this.buttonColor=color;
        return this;
    }
    public int getButtonTextColor(){
        return buttonColor;
    }
    public float getButtonTextSize(){
        return buttonSize;
    }
    public MaterialDialog setButtonTextSize(float size){
        this.buttonSize=size;
        return this;
    }
    public MaterialDialog setItem(String[] data, OnDialogItemClickListener onDialogItemClickListener){
        this.values = Arrays.asList(data);
        this.onDialogItemClickListener=onDialogItemClickListener;
        return this;
    }
    public MaterialDialog setItem(List<String> data, OnDialogItemClickListener onDialogItemClickListener){
        this.values=data;
        this.onDialogItemClickListener=onDialogItemClickListener;
        return this;
    }
    public MaterialDialog setItemChecked(int item,boolean isChecked){
        items.add(item);
        isCheck.add(isChecked);
        return this;
    }
    public MaterialDialog addItemChecked(int item,boolean isChecked){
        items.add(item);
        isCheck.add(isChecked);
        return this;
    }
    public MaterialDialog setSingalChoiceItem(String[] data, OnDialogSingleChoiceListener onDialogSingleChoiceListener){
        this.choiceMode=ListView.CHOICE_MODE_SINGLE;
        this.values = Arrays.asList(data);
        this.onDialogSingleChoiceListener=onDialogSingleChoiceListener;
        return this;
    }
    public MaterialDialog setSingalChoiceItem(List<String> data, OnDialogSingleChoiceListener onDialogSingleChoiceListener){
        this.choiceMode=ListView.CHOICE_MODE_SINGLE;
        this.values=data;
        this.onDialogSingleChoiceListener=onDialogSingleChoiceListener;
        return this;
    }
    public MaterialDialog setMultipleChoiceItem(String[] data, OnDialogMultipleChoiceListener onDialogMultipleChoiceListener){
        this.choiceMode=ListView.CHOICE_MODE_MULTIPLE;
        this.values = Arrays.asList(data);
        this.onDialogMultipleChoiceListener=onDialogMultipleChoiceListener;
        return this;
    }
    public MaterialDialog setMultipleChoiceItem(List<String> data, OnDialogMultipleChoiceListener onDialogMultipleChoiceListener){
        this.choiceMode=ListView.CHOICE_MODE_MULTIPLE;
        this.values=data;
        this.onDialogMultipleChoiceListener=onDialogMultipleChoiceListener;
        return this;
    }
    public MaterialDialog setColorAccent(int color){
        this.colorAccent=color;
        return this;
    }
    public int getColorAccent(){
        return colorAccent;
    }
    public MaterialDialog setItemTextSize(float size){
        this.itemTextSize=size;
        return this;
    }
    public MaterialDialog setItemIconSize(int size){
        this.iconSize=size;
        return this;
    }
    public SparseBooleanArray getSelectPositions(){
        return itemCheck;
    }
    public MaterialDialog setGravity(int gravity){
        this.mDialogGravity=gravity;
        return this;
    }
    public int getGraivity(){
        return mDialogGravity;
    }
    public MaterialDialog setAtLocation(int x,int y){
        this.x=x;
        this.y=y;
        return this;
    }
}