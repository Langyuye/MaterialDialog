# MaterialDialog
这是一个MD风格的Dialog，兼容低版本

#### build.gradle配置   
```Java
allprojects {
    repositories {
        jcenter()
        maven{
            url "https://raw.githubusercontent.com/Langyuye/MavenRepository/master"
        }
    }
}
```
```Java
dependencies {
    compile 'com.langyuye.library:dialog:1.0.1'
}
```
#### 新增方法
```Java
dialog.setAtLocation(x,y) //设置坐标
//变更
dialog.setDialogSize(size)  //设置窗体宽度范围０－１，屏幕宽度*size
dialog.setDialogWidth(width)　//宽度
}
```
#### 构造方法
```Java
MaterialDialog dialog=new MaterialDialog(MainActivity.this);
dialog.show();

//或者
new MaterialDialog(MainActivity.this)
　　.show();
```
#### 设置标题及属性
```Java
dialog.setTitle("Title");
dialog.setTitleGravity(Gravity.CENTER); //标题位置(Gracity.LEFT,Gravity.CENTER,Gravity.RIGHT)　居左｜居中｜居右
dialog.setTitleColor(color)　　//设置标题字体颜色
dialog.setTitleSize(float) 　//设置标题字体大小
dialog.setTitleBold(true)　　//设置粗体
```
#### 设置Message及属性
```Java
dialog.setMessage("Message");
dialog.setMessageColor(color) 　//设置Message字体颜色
dialog.setMessageSize(float) 　//设置Message字体大小
```
#### 设置窗口样色
```Java
dialog.setAttributes(MaterialDialog.Attributes.DARK); //默认MaterialDialog.Attributes.TRANSPARENT
```
#### 设置Dialog动画
```Java
new MaterialDialog(this,R.style.my_dialog_anim);
//或者
dialog.setAnimationStyle(R.style.my_dialog_anim);
```
#### 自定义View
```Java
dialog.addView(view);
```
#### 自定义布局
```Java
dialog.setView(view);
```
#### 其他属性
```Java
dialog.setBackground(drawable)  //通过drawable设置窗体背景
dialog.setBackgroundColor(color) //通过color设置窗体背景
dialog.setBackgroundResource(resId) //通过资源文件设置窗体背景
dialog.setOnDismissListener(new OnDismissListener);  //监听窗口取消
dialog.setCanceledOnTouchSide(boolean) //弹窗不可取消
dialog.setColorAccent(color)  //设置通用的颜色，比如按钮颜色，单选复选按钮颜色
dialog.setButtonTextColor(color)  //设置按钮字体颜色
dialog.setButtonTextSize(float)　　//设置按钮字体大小
dialog.setButtonTextBold(boolean)　　//设置按钮字体粗体
dialog.setItemTextSize(float)  //设置列表字体大小
dialog.setItemIconSize(int)  //设置列表图标大小
```
#### 设置按钮
```Java
//MD风格排版
dialog.setPositionButton("B1", new OnDialogClickListener());
dialog.setNegativeButton("B2", new OnDialogClickListener());
dialog.setNeutralButton("B3", new OnDialogClickListener());
/*
从右到左并排排版
不添加按钮监听事件，则用null表示，会自动触发dismiss事件
*/
dialog.addButton("B1",null);
dialog.addButton("B２",null);
dialog.addButton("B３",null);
dialog.addButton("B4",null);

//按钮点击事件
OnDialogClickListener listener=new OnDialogClickListener() {
      @Override
      public boolean onDialogClick(MaterialDialog dialog, int id, boolean isShow) {
            return false; //返回true时自动触发dismiss
      }
};
```
#### 列表框
```Java
dialog.setItem(list, new OnDialogItemClickListener() {
      @Override
      public boolean onItemClick(MaterialDialog dialog, int position) {
           return true; //返回true时自动触发dismiss
      }
})
```
#### 单选框
```Java
dialog.setItemChecked(position,boolean) //默认选中状态
dialog.setSingalChoiceItem(list, new OnDialogSingleChoiceListener() {
      @Override
      public boolean onItemChoice(MaterialDialog dialog, int position) {
            return false;//返回true时自动触发dismiss
      }
})
```
#### 复选框
```Java
dialog.addItemChecked(position,boolean) //默认选中状态，可多次添加
dialog.setMultipleChoiceItem(list, new OnDialogMultipleChoiceListener() {
      @Override
      public boolean onMultipleChoice(MaterialDialog dialog, int position, SparseBooleanArray isCheckArray) {
      　　　　return false;//返回true时自动触发dismiss
      }
})
```
