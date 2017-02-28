package com.github.spinner;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
public class SpinnerView extends RelativeLayout implements OnClickListener {

	private EditText mEtInput;
	private ImageView mIvArrow;

	private PopupWindow mWindow;
	private ListAdapter mAdapter;
	private OnItemClickListener mListener;
	private ListView mContentView;

	public SpinnerView(Context context) {
		this(context, null);
	}

	public SpinnerView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// xml和class 绑定
		View.inflate(context, R.layout.spinner, this);

		mEtInput = (EditText) findViewById(R.id.et_input);
		mIvArrow = (ImageView) findViewById(R.id.iv_arrow);

		mIvArrow.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == mIvArrow) {
			clickArrow();
		}
	}

	public void setAdapter(ListAdapter adapter) {
		this.mAdapter = adapter;
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mListener = listener;
	}

	private void clickArrow() {
		// 点击箭头，需要弹出显示list数据的层

		if (mAdapter == null) {
			throw new RuntimeException("请调用setAapter()去设置数据");
		}

		if (mWindow == null) {
			// contentView：显示的view
			// width height:popup宽和高

			mContentView = new ListView(getContext());

			// 设置数据
			mContentView.setAdapter(mAdapter);// ---》adapter---》List《数据》

			mContentView.setBackgroundResource(R.drawable.listview_background);
			int width = mEtInput.getWidth();
			int height = 280;

			mWindow = new PopupWindow(mContentView, width, height);
			// 设置获取焦点
			mWindow.setFocusable(true);

			// 设置边缘点击收起
			mWindow.setOutsideTouchable(true);
			mWindow.setBackgroundDrawable(new ColorDrawable());
		}

		// 设置item的点击事件
		mContentView.setOnItemClickListener(mListener);

		mWindow.showAsDropDown(mEtInput);
	}

	public void setText(String data) {
		mEtInput.setText(data);
	}

	public void setSelection(int length) {
		mEtInput.setSelection(length);
	}

	public void dismiss() {
		if (mWindow != null) {
			mWindow.dismiss();
		}
	}

}
