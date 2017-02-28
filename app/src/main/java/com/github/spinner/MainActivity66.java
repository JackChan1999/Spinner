package com.github.spinner;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
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
public class MainActivity66 extends Activity {

	private ListView lvList;
	private EditText etContent;
	private ArrayList<String> mList;
	private PopupWindow mPopup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main66);
		ImageView ivDrop = (ImageView) findViewById(R.id.iv_drop);
		etContent = (EditText) findViewById(R.id.et_content);

		ivDrop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDropDown();
			}
		});

		lvList = new ListView(this);

		
		mList = new ArrayList<String>();
		for (int i = 0; i < 200; i++) {
			mList.add("18801911161" + i);
		}

		lvList.setAdapter(new MyAdapter());

		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				etContent.setText(mList.get(position));
				mPopup.dismiss();
			}
		});
	}

	protected void showDropDown() {
		if (mPopup == null) {
			mPopup = new PopupWindow(lvList, etContent.getWidth(), 200, true);
			mPopup.setBackgroundDrawable(new ColorDrawable());
		}

		mPopup.showAsDropDown(etContent);
	}

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
				convertView = View.inflate(getApplicationContext(), R.layout.list_item66, null);
				holder = new ViewHolder();
				holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
				holder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tvContent.setText(getItem(position));
			holder.ivDelete.setOnClickListener(new OnClickListener() {

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
		public TextView tvContent;
		public ImageView ivDelete;
	}
}
