package com.github.spinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
 * Author：   陈冠杰
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：Spinner
 * Package_Name：com.github.spinner
 * Version：1.0
 * time：2016/2/28 19:37
 * des ：自定义组合控件，Spinner下拉选择框控件
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */

public class MainActivity56 extends Activity implements OnClickListener {

    private ImageView   iv_down_arrow;
    private EditText    et_input;
    private PopupWindow window;

    private ListView listView;

    private ArrayList<String> msgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main56);
        iv_down_arrow = (ImageView) findViewById(R.id.iv_down_arrow);
        et_input = (EditText) findViewById(R.id.et_input);
        iv_down_arrow.setOnClickListener(this);


        //准备数据
        msgList = new ArrayList<String>();
        for (int i = 0; i < 200; i++) {
            msgList.add(i + "aaaaaaaaaaaaa" + i);
        }
        //实例化ListView
        listView = new ListView(this);
        listView.setBackgroundResource(R.drawable.listview_background);

        //设置适配器
        listView.setAdapter(new MyAdapter());

        //设置点击某一条
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String msg = msgList.get(position);
                et_input.setText(msg);//把文本设置到输入框
                if (window != null && window.isShowing()) {
                    window.dismiss();
                    window = null;

                }

            }
        });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return msgList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //把布局文件-->View对象
            View view;
            ViewHolder holder;
            if (convertView != null) {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            } else {
                view = View.inflate(MainActivity56.this, R.layout.popupwindow_item, null);
                holder = new ViewHolder();
                holder.iv_user = (ImageView) view.findViewById(R.id.iv_user);
                holder.tv_msg = (TextView) view.findViewById(R.id.tv_msg);
                holder.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
                //把容器的结构关系和View关联
                view.setTag(holder);
            }

            //根据位置得到相对的信息
            final String msg = msgList.get(position);
            holder.tv_msg.setText(msg);

            //设置点击事件-当点击的时候删除当前的数据
            holder.iv_delete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    //1.从msgList移除这个对象
                    msgList.remove(msg);

                    //2.刷新页面
                    notifyDataSetChanged();//getCount()执行--》getView();

                }
            });

            return view;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }

    static class ViewHolder {
        ImageView iv_user;
        TextView  tv_msg;
        ImageView iv_delete;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_down_arrow://点击事件

                if (window == null) {
                    window = new PopupWindow(this);
                    window.setWidth(et_input.getWidth());
                    window.setHeight(200);
                    // 设置视图
                    window.setContentView(listView);//设置视图
                    //设置点击外边消掉PopupWindow
                    //				window.setOutsideTouchable(true);

                    window.setFocusable(true);
                }

                window.showAsDropDown(et_input, 0, 0);

                break;

            default:
                break;
        }

    }

}
