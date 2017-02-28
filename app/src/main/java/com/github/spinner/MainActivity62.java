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
public class MainActivity62 extends Activity implements OnClickListener,
		OnItemClickListener {
	private EditText mEtInput;
	private ImageView mIvArrow;

	private List<String> mDatas;

	private PopupWindow mWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mEtInput = (EditText) findViewById(R.id.et_input);
		mIvArrow = (ImageView) findViewById(R.id.iv_arrow);

		mIvArrow.setOnClickListener(this);

		// 模拟数据
		mDatas = new ArrayList<String>();
		for (int i = 0; i < 200; i++) {
			mDatas.add("18801911161" + i + "");
		}
	}

	@Override
	public void onClick(View v) {
		if (v == mIvArrow) {
			clickArrow();
		}
	}

	private void clickArrow() {
		// 点击箭头，需要弹出显示list数据的层

		if (mWindow == null) {
			// contentView：显示的view
			// width height:popup宽和高
			ListView contentView = new ListView(this);

			// 设置数据
			contentView.setAdapter(new MyAdapter());// ---》adapter---》List《数据》

			// 设置item的点击事件
			contentView.setOnItemClickListener(this);

			contentView.setBackgroundResource(R.drawable.listview_background);
			int width = mEtInput.getWidth();
			int height = 280;

			mWindow = new PopupWindow(contentView, width, height);
			// 设置获取焦点
			mWindow.setFocusable(true);

			// 设置边缘点击收起
			mWindow.setOutsideTouchable(true);
			mWindow.setBackgroundDrawable(new ColorDrawable());

		}
		mWindow.showAsDropDown(mEtInput);
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
				convertView = View.inflate(MainActivity62.this, R.layout.item, null);
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
		mEtInput.setText(data);
		mEtInput.setSelection(data.length());

		// 隐藏popu
		mWindow.dismiss();
	}

}
