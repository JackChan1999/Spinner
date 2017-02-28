package com.github.spinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
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
public class DemoActivity extends Activity implements OnItemClickListener {

	private SpinnerView mSpinnerView;

	private List<String> mDatas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);

		mSpinnerView = (SpinnerView) findViewById(R.id.sv);

		// 模拟数据
		mDatas = new ArrayList<String>();
		for (int i = 0; i < 200; i++) {
			mDatas.add("18801911161" + i);
		}

		mSpinnerView.setAdapter(new MyAdapter());

		mSpinnerView.setOnItemClickListener(this);

	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (mDatas != null) {
				return mDatas.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if (mDatas != null) {
				return mDatas.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {
				// 没有复用
				// 去加载布局
				convertView = View.inflate(DemoActivity.this, R.layout.item,
						null);
				// 初始化holder
				holder = new ViewHolder();
				// 设置标记
				convertView.setTag(holder);
				// 初始化item的view
				holder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
				holder.ivUser = (ImageView) convertView.findViewById(R.id.iv_user);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);

			} else {
				// 有复用
				holder = (ViewHolder) convertView.getTag();
			}

			// 给view设置数据
			final String data = mDatas.get(position);
			holder.tvTitle.setText(data);

			holder.ivDelete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mDatas.remove(data);
					// UI刷新
					notifyDataSetChanged();
				}
			});

			return convertView;
		}
	}

	class ViewHolder {
		ImageView ivUser;
		ImageView ivDelete;
		TextView tvTitle;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		// 设置输入框内容
		String data = mDatas.get(position);
		mSpinnerView.setText(data);
		mSpinnerView.setSelection(data.length());

		// 隐藏popu
		mSpinnerView.dismiss();
	}
}
