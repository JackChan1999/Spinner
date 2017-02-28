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
            mList.add("1880191116" + i);
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
                convertView = View.inflate(getApplicationContext(), R.layout.list_item, null);
                holder = new ViewHolder();
                holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
                holder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
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