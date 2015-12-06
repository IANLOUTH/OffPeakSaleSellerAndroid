package com.appsauthority.appwiz;

import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;

import com.appauthority.appwiz.fragments.SlidingMenuActivity;
import com.appauthority.appwiz.interfaces.OrderHistoryCaller;
import com.appsauthority.appwiz.custom.BaseActivity;
import com.appsauthority.appwiz.models.OrderDetailResponseObject;
import com.appsauthority.appwiz.utils.Helper;
import com.offpeaksale.seller.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RedeemVoucherActivity extends BaseActivity implements
		OrderHistoryCaller {

	Button btnScan, btnValidate;
	EditText etVoucherCode;
	TextView tvEnterVoucherCode, tvOr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.redeem_voucher);
		RelativeLayout v = (RelativeLayout) findViewById(R.id.healderView);

		TextView textViewHeader = (TextView) v
				.findViewById(R.id.textViewHeader);
		ImageView imageViewOverflow = (ImageView) findViewById(R.id.imageViewOverflow);
		tvEnterVoucherCode = (TextView) findViewById(R.id.tvEnterVoucherCode);
		tvOr = (TextView) findViewById(R.id.tvOr);
		btnScan = (Button) findViewById(R.id.btnScan);
		btnValidate = (Button) findViewById(R.id.btnValidate);
		etVoucherCode = (EditText) findViewById(R.id.etVoucherCode);
		textViewHeader.setText("Redeem Voucher");

		try {
			v.setBackgroundColor(Color.parseColor("#"
					+ Helper.getSharedHelper().reatiler.getHeaderColor()));
			btnScan.setBackgroundDrawable(Helper.getSharedHelper()
					.getGradientDrawable(
							Helper.getSharedHelper().reatiler.getHeaderColor()));
			btnValidate
					.setBackgroundDrawable(Helper.getSharedHelper()
							.getGradientDrawable(
									Helper.getSharedHelper().reatiler
											.getHeaderColor()));
			textViewHeader
					.setTextColor(Color.parseColor("#"
							+ Helper.getSharedHelper().reatiler
									.getRetailerTextColor()));
			etVoucherCode
					.setBackgroundDrawable(Helper.getSharedHelper()
							.getGradientDrawableEditText(
									Helper.getSharedHelper().reatiler
											.getHeaderColor()));
			textViewHeader.setTypeface(Helper.getSharedHelper().boldFont);

			btnScan.setTextColor(Color.parseColor("#"
					+ Helper.getSharedHelper().reatiler.getRetailerTextColor()));
			btnValidate
					.setTextColor(Color.parseColor("#"
							+ Helper.getSharedHelper().reatiler
									.getRetailerTextColor()));
			etVoucherCode.setTextColor(Color.BLACK);
			btnScan.setTypeface(Helper.getSharedHelper().normalFont);
			btnValidate.setTypeface(Helper.getSharedHelper().normalFont);
			etVoucherCode.setTypeface(Helper.getSharedHelper().normalFont);
			tvOr.setTypeface(Helper.getSharedHelper().normalFont);
			tvEnterVoucherCode.setTypeface(Helper.getSharedHelper().normalFont);
		} catch (Exception e) {
			// TODO: handle exception
		}

		imageViewOverflow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();

			}
		});

		if (Helper.getSharedHelper().reatiler.appIconColor != null
				&& Helper.getSharedHelper().reatiler.appIconColor
						.equalsIgnoreCase("black")) {
			imageViewOverflow
					.setBackgroundResource(R.drawable.backbutton_black);
		} else {
			imageViewOverflow.setBackgroundResource(R.drawable.backbutton);
		}

		btnValidate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String voucherCode = etVoucherCode.getText().toString();
				if (voucherCode.length() == 0) {
					Toast.makeText(RedeemVoucherActivity.this,
							"Enter Voucher Code", Toast.LENGTH_LONG).show();
				} else {
					validateVoucher(voucherCode);
				}
			}
		});
		btnScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				IntentIntegrator.initiateScan(RedeemVoucherActivity.this,
						R.layout.capture, R.id.viewfinder_view,
						R.id.preview_view, true);
			}
		});
	}

	void validateVoucher(String voucherCode) {
		OrderDetailDataHandler orderDetailHandler = new OrderDetailDataHandler(
				this, this, "", voucherCode);
		orderDetailHandler.getOrderDetail();
	}

	@Override
	public void orderDetailDownloaded(OrderDetailResponseObject orderDetailObj) {
		// TODO Auto-generated method stub
		if (orderDetailObj.errorCode != null
				&& orderDetailObj.errorCode.equalsIgnoreCase("1")) {
			Intent intent = new Intent(context, OrderDetailActivity.class);
			intent.putExtra("orderObj", orderDetailObj.data);
			startActivity(intent);
		} else {
			Intent intent = new Intent(context, InvalidVoucherActivity.class);
			intent.putExtra("isValide", false);
			intent.putExtra("msg", orderDetailObj.errorMessage);
			startActivity(intent);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case IntentIntegrator.REQUEST_CODE:
			IntentResult scanResult = IntentIntegrator.parseActivityResult(
					requestCode, resultCode, data);
			if (scanResult == null) {
				return;
			}
			final String result = scanResult.getContents();
			if (result != null) {
				validateVoucher(result);

			}
			break;
		default:
		}
	}
}
