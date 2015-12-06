package com.appsauthority.appwiz;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.appauthority.appwiz.interfaces.OrderHistoryCaller;
import com.appsauthority.appwiz.models.OrderDetailResponseObject;
import com.appsauthority.appwiz.utils.Constants;
import com.appsauthority.appwiz.utils.HTTPHandler;
import com.google.gson.Gson;

public class OrderDetailDataHandler {
	OrderHistoryCaller caller;
	private SharedPreferences spref;
	String retailerMail;
	String transactionId;

	public OrderDetailDataHandler(OrderHistoryCaller caller,
			Activity activity,String retailerMail,String transactionId) {
		this.caller = caller;
		this.retailerMail = retailerMail;
		this.transactionId = transactionId;
		spref = PreferenceManager.getDefaultSharedPreferences(activity);
	}

	public void getOrderDetail() {
		new AyncOrderDetail().execute();
	}

	private final class AyncOrderDetail extends
			AsyncTask<Void, Void, JSONObject> {

		@Override
		protected void onPreExecute() {
			
		}

		@Override
		protected JSONObject doInBackground(Void... params) {

			JSONObject param = new JSONObject();

			try {
				String emailId = spref.getString(Constants.KEY_EMAIL, "");
//				emailId = "pendyala.bhargavi@gmail.com";
				param.put(Constants.PARAM_RETAILER_ID, Constants.RETAILER_ID);
				param.put(Constants.PARAM_EMAIL, emailId);
				param.put("retailerMail", retailerMail);
				param.put("transactionId", transactionId);
				JSONObject jsonObject = HTTPHandler.defaultHandler().doPost(
						Constants.URL_VILIDATE_VOUCHER, param);

				return jsonObject;

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}

		@Override
		protected void onPostExecute(JSONObject json) {
			if (json != null) {
				if (json.has("errorCode")) {
					try {
						String errorCode = json.getString("errorCode");
						if (errorCode != null && errorCode.equals("1")) {
							Gson gson = new Gson();
							OrderDetailResponseObject res = gson
									.fromJson(json.toString(),
											OrderDetailResponseObject.class);
							if (caller != null && res != null) {
								caller.orderDetailDownloaded(res);
							}
						} else {

						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block

						e.printStackTrace();
						if (caller != null ) {
							caller.orderDetailDownloaded(null);
						}
					}
				}
			} else {
				if (caller != null ) {
					caller.orderDetailDownloaded(null);
				}
			}
		}
	}
}
