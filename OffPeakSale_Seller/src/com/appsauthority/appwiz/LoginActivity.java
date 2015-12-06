package com.appsauthority.appwiz;

import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.appauthority.appwiz.fragments.SlidingMenuActivity;
import com.appauthority.appwiz.interfaces.ForgotPWDCaller;
import com.appauthority.appwiz.interfaces.UserLoginCaller;
import com.appsauthority.appwiz.custom.BaseActivity;
import com.appsauthority.appwiz.models.Country;
import com.appsauthority.appwiz.models.Profile;
import com.appsauthority.appwiz.models.Retailer;
import com.appsauthority.appwiz.utils.Constants;
import com.appsauthority.appwiz.utils.Helper;
import com.appsauthority.appwiz.utils.ServiceHandler;
import com.appsauthority.appwiz.utils.Utils;
import com.offpeaksale.seller.R;

public class LoginActivity extends BaseActivity implements UserLoginCaller,
		ForgotPWDCaller {

	private Activity activity;

	LinearLayout llLogin, llForgotPwd;

	EditText etLoginEmailId, etLoginPWD, sp_country, etEmailForgotPwd;
	TextView tvLogin, tvForgotPwd, tvShowLogin, tvRegister;

	Button btnForgotPwd, btnLogin;
	private SharedPreferences spref;
	Retailer retailer;
	private ArrayAdapter<String> adapter;
	private ArrayList<Country> countryList = new ArrayList<Country>();
	private ArrayList<String> countryNameList = new ArrayList<String>();
	private ArrayList<String> countrySearchList = new ArrayList<String>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.dialog_login);
		activity = this;
		context = this;
		spref = PreferenceManager.getDefaultSharedPreferences(activity);
		retailer = Helper.getSharedHelper().reatiler;
		initializeLoginRegisterView();
		new AsyncGetCountries().execute();
	}

	void initializeLoginRegisterView() {
		llLogin = (LinearLayout) findViewById(R.id.llLogin);
		llForgotPwd = (LinearLayout) findViewById(R.id.llForgotPwd);

		tvLogin = (TextView) findViewById(R.id.tvLogin);
		etLoginEmailId = (EditText) findViewById(R.id.etLoginEmailId);
		etLoginPWD = (EditText) findViewById(R.id.etLoginPWD);
		sp_country = (EditText) findViewById(R.id.sp_country);

		tvForgotPwd = (TextView) findViewById(R.id.tvForgotPwd);
		tvShowLogin = (TextView) findViewById(R.id.tvShowLogin);
		etEmailForgotPwd = (EditText) findViewById(R.id.etEmailForgotPwd);

		tvRegister = (TextView) findViewById(R.id.tvRegister);

		btnForgotPwd = (Button) findViewById(R.id.btnForgotPwd);

		btnLogin = (Button) findViewById(R.id.btnLogin);

		llForgotPwd.setVisibility(View.GONE);

		tvForgotPwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				llLogin.setVisibility(View.GONE);
				llForgotPwd.setVisibility(View.VISIBLE);
				etEmailForgotPwd.setVisibility(View.VISIBLE);
				btnForgotPwd.setVisibility(View.VISIBLE);
				tvForgotPwd.setVisibility(View.GONE);
				tvShowLogin.setVisibility(View.VISIBLE);
			}
		});

		tvRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		tvShowLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				llLogin.setVisibility(View.VISIBLE);
				etEmailForgotPwd.setVisibility(View.GONE);
				btnForgotPwd.setVisibility(View.GONE);
				tvForgotPwd.setVisibility(View.VISIBLE);
				tvShowLogin.setVisibility(View.GONE);
			}
		});

		etLoginEmailId.setBackgroundDrawable(Helper.getSharedHelper()
				.getGradientDrawableEditText(retailer.getHeaderColor()));
		etLoginPWD.setBackgroundDrawable(Helper.getSharedHelper()
				.getGradientDrawableEditText(retailer.getHeaderColor()));
		sp_country.setBackgroundDrawable(Helper.getSharedHelper()
				.getGradientDrawableEditText(retailer.getHeaderColor()));
		etEmailForgotPwd.setBackgroundDrawable(Helper.getSharedHelper()
				.getGradientDrawableEditText(retailer.getHeaderColor()));
		btnLogin.setTextColor(Color.parseColor("#"
				+ retailer.getRetailerTextColor()));
		btnLogin.setBackgroundDrawable(Helper.getSharedHelper()
				.getGradientDrawable(retailer.getHeaderColor()));

		btnForgotPwd.setTextColor(Color.parseColor("#"
				+ retailer.getRetailerTextColor()));
		btnForgotPwd.setBackgroundDrawable(Helper.getSharedHelper()
				.getGradientDrawable(retailer.getHeaderColor()));
		tvLogin.setTextColor(Color.parseColor("#"
				+ retailer.getRetailerTextColor()));

		tvLogin.setTypeface(Helper.getSharedHelper().normalFont);
		tvRegister.setTypeface(Helper.getSharedHelper().normalFont);
		tvForgotPwd.setTypeface(Helper.getSharedHelper().normalFont);

		etLoginEmailId.setTypeface(Helper.getSharedHelper().normalFont);
		etLoginPWD.setTypeface(Helper.getSharedHelper().normalFont);
		etEmailForgotPwd.setTypeface(Helper.getSharedHelper().normalFont);

		btnLogin.setTypeface(Helper.getSharedHelper().boldFont);
		btnForgotPwd.setTypeface(Helper.getSharedHelper().boldFont);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String emailid = etLoginEmailId.getText().toString();
				String password = etLoginPWD.getText().toString();
				String country = sp_country.getText().toString();
				String errorMsg = null;
				if (!Helper.getSharedHelper().isEmailValid(emailid)) {
					errorMsg = "Invalid email id";
				} else if (password == null || password.length() == 0) {
					errorMsg = "Enter password";
				} else if (country == null || country.length() == 0) {
					errorMsg = "Select Country";
				}
				if (errorMsg != null) {
					Toast.makeText(LoginActivity.this, errorMsg,
							Toast.LENGTH_LONG).show();
				} else {

					LoginHandler loginHandler = new LoginHandler(emailid,
							password, getCountryCode(country),
							LoginActivity.this);
					loginHandler.login();
				}
			}
		});
		btnForgotPwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String emailid = etEmailForgotPwd.getText().toString();
				String errorMsg = null;
				if (!Helper.getSharedHelper().isEmailValid(emailid)) {
					errorMsg = "Invalid email id";
				}
				if (errorMsg != null) {
					Toast.makeText(LoginActivity.this, errorMsg,
							Toast.LENGTH_LONG).show();
				} else {
					ForgotPasswordHandler forgotPWDHandler = new ForgotPasswordHandler(
							emailid, LoginActivity.this);
					forgotPWDHandler.getPassword();
				}
			}
		});

	}

	public void countryPressed(View v) {
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_countries);
		dialog.setTitle("Countries");

		final EditText editTextSearch = (EditText) dialog
				.findViewById(R.id.editTextSearch);

		final ListView listViewCountries = (ListView) dialog
				.findViewById(R.id.listViewCountries);

		adapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.list_row_text, android.R.id.text1, countryNameList);

		listViewCountries.setAdapter(adapter);

		listViewCountries.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg) {

				if (countrySearchList.size() > 0) {
					sp_country.setText(countrySearchList.get(position));
				} else {
					sp_country.setText(countryNameList.get(position));
				}
				String countryId = getCountryCode(sp_country.getText()
						.toString());

				dialog.dismiss();
			}

		});
		editTextSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				countrySearchList.clear();
				for (int i = 0; i < countryNameList.size(); i++) {
					if (countryNameList.get(i).toLowerCase(Locale.ENGLISH)
							.contains(s)) {
						countrySearchList.add(countryNameList.get(i));

					}
				}

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						ArrayAdapter<String> adapterSearch = new ArrayAdapter<String>(
								getApplicationContext(),
								R.layout.list_row_text, android.R.id.text1,
								countrySearchList);
						adapter.notifyDataSetChanged();
						listViewCountries.setAdapter(adapterSearch);

					}
				});

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		dialog.show();
	}

	void setSpinnerAdapter() {
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, R.array.gender_values) {
			public View getView(int position, View convertView,
					android.view.ViewGroup parent) {

				TextView v = (TextView) super.getView(position, convertView,
						parent);
				v.setTypeface(Helper.getSharedHelper().normalFont);
				return v;
			}

			public View getDropDownView(int position, View convertView,
					android.view.ViewGroup parent) {
				TextView v = (TextView) super.getView(position, convertView,
						parent);
				v.setTypeface(Helper.getSharedHelper().normalFont);

				return v;
			}
		};
		// adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}

	private void populateCountries() {

		for (int i = 0; i < countryList.size(); i++) {
			countryNameList.add(countryList.get(i).getCountryName());
		}

	}

	private String getCountryCode(String name) {
		String countryCode = "";
		for (Country country : countryList) {
			if (country.getCountryName().equalsIgnoreCase(name)) {
				countryCode = country.getCountryCode();
				break;
			}
		}
		return countryCode;
	}

	private String getCountryName(String code) {
		String countryName = "";
		for (Country country : countryList) {
			if (country.getCountryCode().equalsIgnoreCase(code)) {
				countryName = country.getCountryName();
				break;
			}
		}
		return countryName;
	}

	Boolean parseConries(String json) {
		Boolean status = false;
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(json);
			countryList.clear();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject country = (JSONObject) jsonArray.get(i);
				Country cat = new Country();
				cat.setCountryCode(country.getString("countryCode"));
				cat.setCountryName(country.getString("countryName"));
				countryList.add(cat);
			}
			status = true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = false;
		}

		// sqliteHelper.openDataBase();
		// for (int i = 0; i < countryList.size(); i++) {
		// sqliteHelper.insertOrReplaceCountry(countryList
		// .get(i));
		// }
		// sqliteHelper.close();

		return status;
	}

	private class AsyncGetCountries extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			String json = spref.getString(Constants.KEY_GET_COUNTRY_INFO, "");
			if (!json.equalsIgnoreCase("")) {
				Boolean stats = parseConries(json);
				if (stats) {
					populateCountries();
				}
			} else {
				showLoadingDialog();
			}

		}

		@Override
		protected Boolean doInBackground(Void... arg0) {

			if (Utils.hasNetworkConnection(context)) {

				Boolean status = false;
				try {
					ServiceHandler jsonParser = new ServiceHandler();
					String json = jsonParser.makeServiceCall(
							Constants.URL_GET_COUNTRIES, ServiceHandler.GET);
					if (json != null) {

						spref.edit()
								.putString(Constants.KEY_GET_COUNTRY_INFO, json)
								.commit();
						status = parseConries(json);
					} else {
						status = false;
					}
				} catch (Exception e) {
					return false;
				}
				return status;
			} else {

				// try {
				// sqliteHelper.openDataBase();
				// countryList = sqliteHelper.getCountries();
				// sqliteHelper.close();
				// } catch (Exception e) {
				// }

				return false;
			}

		}

		@Override
		protected void onPostExecute(Boolean result) {

			populateCountries();

			dismissLoadingDialog();

		}
	}

	@Override
	public void loggedIn(Boolean isSucess, String msg, Profile userProfile) {
		// TODO Auto-generated method stub
		if (isSucess) {
			spref.edit().putString(Constants.KEY_EMAIL, userProfile.getEmail())
					.commit();
			Intent intent = new Intent(getApplicationContext(),
					SlidingMenuActivity.class);

			startActivity(intent);
			finish();
		} else {
			Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void passwordSent(Boolean isSucess, String msg) {
		// TODO Auto-generated method stub
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
}
