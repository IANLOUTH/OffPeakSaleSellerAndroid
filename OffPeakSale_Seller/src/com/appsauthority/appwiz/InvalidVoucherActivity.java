package com.appsauthority.appwiz;

import com.appauthority.appwiz.fragments.SlidingMenuActivity;
import com.appsauthority.appwiz.custom.BaseActivity;
import com.appsauthority.appwiz.models.OrderObject;
import com.appsauthority.appwiz.utils.Constants;
import com.appsauthority.appwiz.utils.Helper;
import com.appsauthority.appwiz.utils.Constants.DrawerItemType;
import com.offpeaksale.seller.R;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InvalidVoucherActivity extends BaseActivity {

	Boolean isValid;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.invalid_voucher);

		RelativeLayout headerView = (RelativeLayout) findViewById(R.id.healderView);
		ImageView back = (ImageView) findViewById(R.id.imageViewOverflow);
		TextView header = (TextView) findViewById(R.id.textViewHeader);
		back.setBackgroundResource(R.drawable.backbutton);
		header.setText("Invalid Voucher");
		TextView voucherStatus = (TextView) findViewById(R.id.voucherStatus);
		TextView voucherStatusDate = (TextView) findViewById(R.id.voucherStatusDate);
		Button btnOk = (Button) findViewById(R.id.btnOk);

		Intent intent = getIntent();
		String msg = intent.getStringExtra("msg");
		isValid = intent.getBooleanExtra("isValide", false);
		voucherStatus.setText(msg);
		
		if (isValid) {
			voucherStatus
					.setTextColor(Color.parseColor("#"
							+ Helper.getSharedHelper().reatiler
									.getHeaderColor()));
		} else {
			voucherStatus.setTextColor(getResources().getColor(
					R.color.invalid_msg_color));
			
		}
		try {
			headerView.setBackgroundColor(Color.parseColor("#"
					+ Helper.getSharedHelper().reatiler.getHeaderColor()));
			header.setTypeface(Helper.getSharedHelper().boldFont);
			voucherStatus.setTypeface(Helper.getSharedHelper().normalFont);
			voucherStatusDate.setTypeface(Helper.getSharedHelper().normalFont);
			header.setTextColor(Color.parseColor("#"
					+ Helper.getSharedHelper().reatiler.getRetailerTextColor()));
			// tvQRCode.setTextColor(Color.parseColor("#"
			// + Helper.getSharedHelper().reatiler.getRetailerTextColor()));
			headerView.setBackgroundColor(Color.parseColor("#"
					+ Helper.getSharedHelper().reatiler.getHeaderColor()));
			
			btnOk.setBackgroundDrawable(Helper.getSharedHelper()
					.getGradientDrawable(
							Helper.getSharedHelper().reatiler.getHeaderColor()));
			btnOk.setTextColor(Color.parseColor("#"
					+ Helper.getSharedHelper().reatiler.getRetailerTextColor()));
			btnOk.setTypeface(Helper.getSharedHelper().normalFont);
			voucherStatus.setTypeface(Helper.getSharedHelper().boldFont);
			header.setTypeface(Helper.getSharedHelper().boldFont);

		} catch (Exception e) {
			// TODO: handle exception
		}
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				closeActivity();
			}
		});
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				closeActivity();
			}
		});

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// your code
			if (isValid) {
				closeActivity();
				return true;
			}

		}

		return super.onKeyDown(keyCode, event);
	}
	void closeActivity(){
		if (isValid) {
			Intent intent = new Intent(context, SlidingMenuActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}else{
			finish();
		}
	}
}
