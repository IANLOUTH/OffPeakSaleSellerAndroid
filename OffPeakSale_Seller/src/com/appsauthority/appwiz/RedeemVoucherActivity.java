package com.appsauthority.appwiz;

import com.appsauthority.appwiz.custom.BaseActivity;
import com.offpeaksale.seller.R;

import android.os.Bundle;

public class RedeemVoucherActivity extends BaseActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.redeem_voucher);
	}
}
