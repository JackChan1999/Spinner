# Spinner 自定义组合控件之下拉选择框

<img src="https://github.com/JackChen1999/Spinner/blob/master/art/spinner.jpg" width="400" />

## 项目概述

下拉选择框主要是通过在EditText 下用PopupWindow 动态显示ListView 控件来实现的。下拉选择框可以方便用户的输入效率，以此提升用户体验。点击EditText 控件右侧的倒三角形弹出如下可选项，点击
可选项，那么内容将自动填充到EditText 控件中，如图1-6 所示。另外，点击下拉框中条目右边的红色叉号可以删除当前的条目，效果图如图1-7 所示。

## 下拉选择框UI

下拉框的布局文件为activity_main.xml，这里的布局仅仅是在EditText 的右侧添加了一个倒三角图标。具体的代码如文件【1-6】所示：【文件1-6】Res/layout/activity_main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="match_parent"
                android:layout_height="match_parent">

    <RelativeLayout
        android:layout_width="200dp"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="150dp">

        <EditText
            android:id="@+id/et_content"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
        </EditText>

        <ImageView
            android:id="@+id/iv_drop"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentRight="true"
            android:layout_centerVertical="true"
            android:src="@drawable/down_arrow"/>
    </RelativeLayout>
</RelativeLayout>
```

点击倒三角弹出的ListView 条目布局文件为list_item.xml，具体代码如文件【1-7】所示：【文件1-7】res/layout/list_item

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="match_parent"
              android:layout_height="wrap_content"
              android:descendantFocusability="blocksDescendants"
              android:gravity="center_vertical"
              android:orientation="horizontal"
              android:padding="5dp">

    <ImageView
        android:id="@+id/imageView1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/user"/>

    <TextView
        android:id="@+id/tv_content"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:gravity="center"/>

    <ImageView
        android:id="@+id/iv_delete"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/delete"/>
</LinearLayout>
```

## 下拉选择框业务逻辑实现

下拉选择框MainActivity 界面，具体代码如文件【1-8】所示：【文件1-8】com/itheima/dropdown/MainActivity

```java
package com.github.spinner;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   AllenIverson
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：Spinner
 * Package_Name：com.github.spinner
 * Version：1.0
 * time：2016/2/28 17:18
 * des ：自定义组合控件，Spinner下拉选择框控件
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */

public class MainActivity extends Activity {
    private ListView          lvList;
    private EditText          etContent;
    private ArrayList<String> mList;
    private PopupWindow       mPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView ivDrop = (ImageView) findViewById(R.id.iv_drop);
        etContent = (EditText) findViewById(R.id.et_content);
        ivDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDropDown();
            }

        });

        initView();
    }

    //初始化listview 作为pw 的视图
    private void initView() {
        lvList = new ListView(this);
        // 初始化listview 数据
        mList = new ArrayList<String>();
        for (int i = 0; i < 200; i++) {
            mList.add("aaabbbccc" + i);
        }
        lvList.setAdapter(new MyAdapter());
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etContent.setText(mList.get(position));
                mPopup.dismiss();
            }
        });
    }

    // 下拉框的显示
    protected void showDropDown() {
        if (mPopup == null) {
            /**
             * 第一个参数lv:PopupWindow 显示的内容视图
             * 第二个参数et_number.getWidth()：PopupWindow 的宽度
             * 第三个参数200：PopupWindow 的高度
             */
            mPopup = new PopupWindow(lvList, etContent.getWidth(), 200, true);
            mPopup.setFocusable(true); //设置pw 可以响应焦点
            mPopup.setOutsideTouchable(true); //允许点击pw 以外的地方，让pw 隐藏
            //要让setOutsideTouchable(true)有效，必须给PopupWindow 设置背景
            mPopup.setBackgroundDrawable(new BitmapDrawable());
        }
        //设置PopupWindow 显示的位置
        mPopup.showAsDropDown(etContent);
    }

    //ListView 的适配器
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {

            return mList.size();
        }

        @Override
        public String getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.list_item, null);
                holder = new ViewHolder();
                holder.tvContent = (TextView) convertView
                        .findViewById(R.id.tv_content);
                holder.ivDelete = (ImageView) convertView
                        .findViewById(R.id.iv_delete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvContent.setText(getItem(position));
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(position);
                    MyAdapter.this.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

    static class ViewHolder {
        public TextView  tvContent;
        public ImageView ivDelete;
    }
}
```

## 知识点总结

1．PopupWindow 获取焦点能力较差，要让它内容获得焦点，需要通过setFocusable(true)方法来获得焦点，见MainActivity.java 第47 行。

2．PopupWindow 可以通过setOutsideTouchable(true)方法来实现点击除PopupWindow 以外的地方，让PopupWindow 销毁，但必须同时设置背景才有效，见MainActivity.java 第48~50 行。

3．当listview 无法响应onItemClick 点击事件时，有可能是listview 的条目布局中存在抢占焦点能力强的button、imagebutton 等控件，可以在布局中设置android:descendantFocusability="blocksDescendants"属性，以按
块区域的形式响应点击事件，来解决bug，见listview_item.xml 第7 行。