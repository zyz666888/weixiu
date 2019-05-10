package com.idisfkj.hightcopywx.find.customercomplaint;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class CustomerComplaintListView extends ListView {

	public CustomerComplaintListView(Context context) {
		super(context);
	}

	public CustomerComplaintListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomerComplaintListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	

}
