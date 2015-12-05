package com.appsauthority.appwiz;

import com.appsauthority.appwiz.custom.BaseActivity;
import com.appsauthority.appwiz.utils.Helper;
import com.offpeaksale.seller.R;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InvalidVoucherActivity extends BaseActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.invalid_voucher);
		
		RelativeLayout headerView = (RelativeLayout) findViewById(R.id.headerView);
		ImageView back=(ImageView)findViewById(R.id.imageViewOverflow);
		TextView header=(TextView)findViewById(R.id.textViewHeader);
		back.setBackgroundResource(R.drawable.backbutton);
		header.setText("Invalid Voucher");
		TextView voucherStatus=(TextView)findViewById(R.id.voucherStatus);
		TextView voucherStatusDate=(TextView)findViewById(R.id.voucherStatusDate);
		try {
			header.setTypeface(Helper.getSharedHelper().boldFont);
			voucherStatus.setTypeface(Helper.getSharedHelper().normalFont);
			voucherStatusDate.setTypeface(Helper.getSharedHelper().normalFont);
			header
					.setTextColor(Color.parseColor("#"
							+ Helper.getSharedHelper().reatiler
									.getRetailerTextColor()));
			//tvQRCode.setTextColor(Color.parseColor("#"
				//	+ Helper.getSharedHelper().reatiler.getRetailerTextColor()));
			headerView.setBackgroundColor(Color.parseColor("#"
					+ Helper.getSharedHelper().reatiler.getHeaderColor()));
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
