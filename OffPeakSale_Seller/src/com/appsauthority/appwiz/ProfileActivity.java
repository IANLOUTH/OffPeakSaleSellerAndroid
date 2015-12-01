package com.appsauthority.appwiz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appauthority.appwiz.fragments.SlidingMenuActivity;
import com.appauthority.appwiz.interfaces.ForgotPWDCaller;
import com.appauthority.appwiz.interfaces.RedeemRewadsCaller;
import com.appauthority.appwiz.interfaces.ShippingChargeCaller;
import com.appauthority.appwiz.interfaces.UserLoginCaller;
import com.appauthority.appwiz.interfaces.UserProfileCaller;
import com.appsauthority.appwiz.custom.BaseActivity;
import com.appsauthority.appwiz.models.Country;
import com.appsauthority.appwiz.models.PaypalTokenRequest;
import com.appsauthority.appwiz.models.Product;
import com.appsauthority.appwiz.models.Profile;
import com.appsauthority.appwiz.models.Retailer;
import com.appsauthority.appwiz.utils.Constants;
import com.appsauthority.appwiz.utils.HTTPHandler;
import com.appsauthority.appwiz.utils.Helper;
import com.appsauthority.appwiz.utils.ServiceHandler;
import com.appsauthority.appwiz.utils.Utils;
import com.google.gson.Gson;
import com.offpeaksale.consumer.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends BaseActivity implements
		ShippingChargeCaller, UserProfileCaller, RedeemRewadsCaller,
		UserLoginCaller, ForgotPWDCaller {
	private Activity activity;
	private TextView textViewHeader;
	private ImageView imageViewOverflow;

	private ArrayList<Country> countryList = new ArrayList<Country>();
	private ArrayList<String> countryNameList = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private ArrayList<String> countrySearchList = new ArrayList<String>();

	private SimpleDateFormat sdf;

	private Context context;
	private Product product;
	private PaypalTokenRequest payPalTokenRequest;
	private Intent intent;
	private Retailer retailer;
	Boolean isProceedtoCheckout;
	private SharedPreferences spref;

	// TextView tvRedeemlbl, tvRedeem, tvNoRewardsForCOD;

	TextView tvCashCreditValue, tvTotalRewardsValue, tvResultMsg;
	EditText etRewards;
	Button buttonCountries;

	// LinearLayout llRedeem;

	Boolean isCOD;

	// Login
	LinearLayout llLoginForm, llForm;

	TextView tvCreatePwd;

	LinearLayout llLogin, llForgotPwd;
	EditText etLoginEmailId, etLoginPWD, etEmailForgotPwd;

	TextView tvLogin, tvForgotPwd, tvNeedHelp;

	Button btnForgotPwd, btnLogin;
	private static final String TAG = "ProfileActivity";
	TextView close;

	void initializeLoginRegisterView() {
		llLogin = (LinearLayout) llLoginForm.findViewById(R.id.llLogin);
		llForgotPwd = (LinearLayout) llLoginForm.findViewById(R.id.llForgotPwd);

		tvLogin = (TextView) llLoginForm.findViewById(R.id.tvLogin);
		etLoginEmailId = (EditText) llLoginForm
				.findViewById(R.id.etLoginEmailId);
		etLoginPWD = (EditText) llLoginForm.findViewById(R.id.etLoginPWD);

		tvForgotPwd = (TextView) llLoginForm.findViewById(R.id.tvForgotPwd);
		etEmailForgotPwd = (EditText) llLoginForm
				.findViewById(R.id.etEmailForgotPwd);

		tvNeedHelp = (TextView) llLoginForm.findViewById(R.id.tvHelp);

		btnForgotPwd = (Button) llLoginForm.findViewById(R.id.btnForgotPwd);
		btnLogin = (Button) llLoginForm.findViewById(R.id.btnLogin);
		 close=(TextView)findViewById(R.id.close);
		 buttonCountries=(Button)findViewById(R.id.sp_country);
		
		tvForgotPwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				textViewHeader.setText("Forgot Password");
				close.setVisibility(View.VISIBLE);
				llLogin.setVisibility(View.GONE);
				llForgotPwd.setVisibility(View.VISIBLE);
				etEmailForgotPwd.setVisibility(View.VISIBLE);
				btnForgotPwd.setVisibility(View.VISIBLE);
				tvForgotPwd.setVisibility(View.GONE);
			}
		});

		tvNeedHelp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				textViewHeader.setText("Settings");
				close.setVisibility(View.VISIBLE);
				llLoginForm.setVisibility(View.GONE);
				llForm.setVisibility(View.VISIBLE);
				
			}
		});

		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showLoginForm();
			}
		});


		etLoginEmailId.setBackgroundDrawable(Helper.getSharedHelper()
				.getGradientDrawableEditText(retailer.getHeaderColor()));
		etLoginPWD.setBackgroundDrawable(Helper.getSharedHelper()
				.getGradientDrawableEditText(retailer.getHeaderColor()));
		buttonCountries.setBackgroundDrawable(Helper.getSharedHelper()
				.getGradientDrawableEditText(retailer.getHeaderColor()));
		btnLogin.setTextColor(Color.parseColor("#"
				+ retailer.getRetailerTextColor()));
		btnLogin.setBackgroundDrawable(Helper.getSharedHelper()
				.getGradientDrawable(retailer.getHeaderColor()));

		btnForgotPwd.setTextColor(Color.parseColor("#"
				+ retailer.getRetailerTextColor()));
		btnForgotPwd.setBackgroundDrawable(Helper.getSharedHelper()
				.getGradientDrawable(retailer.getHeaderColor()));

		tvLogin.setTypeface(Helper.getSharedHelper().normalFont);
		tvNeedHelp.setTypeface(Helper.getSharedHelper().normalFont);
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
				String errorMsg = null;
				if (!Helper.getSharedHelper().isEmailValid(emailid)) {
					errorMsg = "Invalid email id";
				} else if (password == null || password.length() == 0) {
					errorMsg = "Enter password";
				}
				if (errorMsg != null) {
					Toast.makeText(ProfileActivity.this, errorMsg,
							Toast.LENGTH_LONG).show();
				} else {

					LoginHandler loginHandler = new LoginHandler(emailid,
							password, ProfileActivity.this);
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
					Toast.makeText(ProfileActivity.this, errorMsg,
							Toast.LENGTH_LONG).show();
				} else {
					ForgotPasswordHandler forgotPWDHandler = new ForgotPasswordHandler(
							emailid, ProfileActivity.this);
					forgotPWDHandler.getPassword();
				}
			}
		});
		Boolean isLoggedIn = spref.getBoolean(Constants.KEY_IS_USER_LOGGED_IN,
				false);
		loggedin(isLoggedIn);

	}

	void showLoginForm() {
		textViewHeader.setText("Merchant Login");
		close.setVisibility(View.GONE);
		llForm.setVisibility(View.GONE);
		llLoginForm.setVisibility(View.VISIBLE);
		llLogin.setVisibility(View.VISIBLE);
		etEmailForgotPwd.setVisibility(View.GONE);
		btnForgotPwd.setVisibility(View.GONE);
		tvForgotPwd.setVisibility(View.VISIBLE);

	}

	void loggedin(Boolean status) {
		if (status) {
			try {
				if (!intent.getStringExtra("FROM").equalsIgnoreCase("ESHOP")) {
					finish();
					return;
				}
			} catch (Exception e) {

			}
			llForm.setVisibility(View.VISIBLE);
			llLoginForm.setVisibility(View.GONE);

			if (Utils.isProfileAvailable(context)) {
				setUpFields();
			}
		} else {
			showLoginForm();

		}
	}


	@SuppressWarnings("deprecation")
	@SuppressLint("SimpleDateFormat")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_profile);
		activity = this;
		context = this;


		retailer = Helper.getSharedHelper().reatiler;

		llLoginForm = (LinearLayout) findViewById(R.id.llLoginForm);
		llForm = (LinearLayout) findViewById(R.id.llForm);

		spref = PreferenceManager.getDefaultSharedPreferences(activity);

		textViewHeader = (TextView) findViewById(R.id.textViewHeader);
		imageViewOverflow = (ImageView) findViewById(R.id.imageViewOverflow);
		sdf = new SimpleDateFormat("dd MMM yyyy");

		Boolean isOff = spref.getBoolean(Constants.KEY_PN, false);

		try {

			int colorOn = Color.parseColor("#" + retailer.getHeaderColor());
			int colorOff = 0xFF666666;
			int colorDisabled = 0xFF333333;
			StateListDrawable thumbStates = new StateListDrawable();
			thumbStates.addState(new int[] { android.R.attr.state_checked },
					new ColorDrawable(colorOn));
			thumbStates.addState(new int[] { -android.R.attr.state_enabled },
					new ColorDrawable(colorDisabled));
			thumbStates.addState(new int[] {}, new ColorDrawable(colorOff)); 
		} catch (Exception e) {
		}

		textViewHeader.setText("Merchant Login");
		imageViewOverflow.setVisibility(View.INVISIBLE);
		try {
			setHeaderTheme(activity, retailer.getRetailerTextColor(),
					retailer.getHeaderColor());

		} catch (Exception e) {
		}

		intent = getIntent();
		try {
			isCOD = intent.getBooleanExtra("isCod", false);
			if (intent.getStringExtra("FROM").equalsIgnoreCase("ESHOP")) {
				product = (Product) intent.getSerializableExtra("product");

			} else {
			}
		} catch (Exception e) {
		}

		if (Utils.isProfileAvailable(context)) {
			setUpFields();
		}
		setFont();

		initializeLoginRegisterView();
		new AsyncGetCountries().execute();

	}

	DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int yr, int monthOfYear,
				int dayOfMonth) {

			Calendar calendar = Calendar.getInstance();
			calendar.set(yr, monthOfYear, dayOfMonth);

			String dateString = sdf.format(calendar.getTime());

		}
	};

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

	void setFont() {
		try {
			textViewHeader.setTypeface(Helper.getSharedHelper().boldFont);

		} catch (Exception e) {

		}
	}

	private void setUpFields() {

		Profile profile;
		String json = spref.getString(Constants.KEY_PROFILE_INFO, "");
		if (json.equalsIgnoreCase("")) {
			return;
		} else {
			Gson gson = new Gson();
			profile = gson.fromJson(json, Profile.class);
		}

		if (profile == null) {
			return;
		}
	}

	public String createJsonString() {
		String jsonstr = "[";
		try {
			List<Product> products = Helper.getSharedHelper().shoppintCartList;

			for (int i = 0; i < products.size(); i++) {
				Product p = products.get(i);
				JSONObject json = new JSONObject();
				json.put(Constants.PARAM_PRODUCTID_FOR_TOKEN, p.getId());
				json.put(Constants.PARAM_QUANTITY, p.getQty());
				jsonstr = jsonstr + json.toString();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		jsonstr = jsonstr + "]";
		return jsonstr;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		String emailId = spref.getString(Constants.KEY_EMAIL, "");
		if (!emailId.equalsIgnoreCase("")) {
			new UserProfileDataHandler(emailId, this);
		}
	}

	@Override
	public void shippingChargeUpdated() {
		// TODO Auto-generated method stub

	}


	@Override
	public void userProfileFetched(String rewardsPoints) {
		// TODO Auto-generated method stub
		// tvRedeem.setText(Helper.getSharedHelper().rewardPoints);
	}

	@Override
	public void rewadsPointsRedeemed(Boolean isSucess, String msg) {
		// TODO Auto-generated method stub
		if (isSucess) {
			tvResultMsg.setTextColor(Color.BLACK);
		} else {
			tvResultMsg.setTextColor(Color.RED);
		}
		tvResultMsg.setText(msg);
		tvResultMsg.setVisibility(View.VISIBLE);
		// tvRewardsValue.setText(Helper.getSharedHelper().redeemPoints);
	}

	@Override
	public void loggedIn(Boolean isSucess, String msg, Profile userProfile) {
		// TODO Auto-generated method stub
		if (isSucess) {

			Gson gson = new Gson();
			String json = gson.toJson(userProfile);
			spref.edit().putString(Constants.KEY_PROFILE_INFO, json).commit();
			spref.edit().putBoolean(Constants.KEY_IS_USER_LOGGED_IN, true)
					.commit();
			spref.edit().putString(Constants.KEY_EMAIL, userProfile.getEmail())
					.commit();

			Utils.setProfile(context, true);
			loggedin(true);
		}
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public void passwordSent(Boolean isSucess, String msg) {
		// TODO Auto-generated method stub
		if (isSucess) {
			showLoginForm();
		}
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
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


				return false;
			}

		}

		@Override
		protected void onPostExecute(Boolean result) {

			populateCountries();

			dismissLoadingDialog();

		}
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
					buttonCountries.setText(countrySearchList.get(position));
				} else {
					buttonCountries.setText(countryNameList.get(position));
				}
				String countryId = getCountryCode(buttonCountries.getText()
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
}
