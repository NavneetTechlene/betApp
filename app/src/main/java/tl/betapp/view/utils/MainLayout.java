package tl.betapp.view.utils;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tl.betapp.R;
import tl.betapp.view.widgets.RegularTextView;


public class MainLayout {

	public ImageView mLeftIb, mRightIb;
	public RegularTextView mTitleTv;
	public ProgressBar loadingPb;
	public RelativeLayout actionBarLayout;

	public MainLayout(View view) {
		actionBarLayout = (RelativeLayout) view.findViewById(R.id.headerLayoutTop);
		mLeftIb = (ImageView) view.findViewById(R.id.back_button);
		mRightIb = (ImageView) view.findViewById(R.id.right_button);
		mTitleTv = (RegularTextView) view.findViewById(R.id.header_tv);
		//loadingPb = (ProgressBar) view.findViewById(R.id.loading_h_pb);
	}

	public void setHeaderValues(int leftId, String title, int rightId) {
		if (leftId != 0) {
			mLeftIb.setImageResource(leftId);
			mLeftIb.setVisibility(View.VISIBLE);
		} else {
			mLeftIb.setVisibility(View.INVISIBLE);
		}

		if (title != null) {
			mTitleTv.setText(title);
			mTitleTv.setVisibility(View.VISIBLE);
		}

		if (rightId != 0) {
			mRightIb.setImageResource(rightId);
			mRightIb.setVisibility(View.VISIBLE);
		} else {
			mRightIb.setVisibility(View.INVISIBLE);
		}
	}

	public void enableRightButton(boolean status) {
		if (status) {
			//mRightIb1.setImageResource(R.drawable.icon_arrw_right_active_white_xhdpi);
			//mRightIb1.setAlpha(1f);
			mRightIb.setEnabled(true);
		} else {
			//mRightIb1.setImageResource(R.drawable.icon_arrw_right_inactive_white_xhdpi);
			//mRightIb1.setAlpha(0.5f);
			mRightIb.setEnabled(false);
		}
	}

	public void setListenerItI(OnClickListener left, OnClickListener right) {
		mLeftIb.setOnClickListener(left);
		mRightIb.setOnClickListener(right);
	}
}
