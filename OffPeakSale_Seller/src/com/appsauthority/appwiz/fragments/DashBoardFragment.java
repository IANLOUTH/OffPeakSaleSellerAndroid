package com.appsauthority.appwiz.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.appauthority.appwiz.fragments.SlidingMenuActivity;
import com.appsauthority.appwiz.RedeemVoucherActivity;
import com.appsauthority.appwiz.models.Profile;
import com.appsauthority.appwiz.models.UserDetailObject;
import com.appsauthority.appwiz.utils.Constants;
import com.appsauthority.appwiz.utils.HTTPHandler;
import com.appsauthority.appwiz.utils.Helper;
import com.google.gson.Gson;
import com.offpeaksale.seller.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DashBoardFragment extends Fragment {

	private View root;
	Button ActivateQr;
	private SharedPreferences spref;
	TextView tvMerchantAccount, tvMerchantName1, tvMerchantUserIdText, tvMerchantUserId, tvMerchantText, tvMerchantName,
			tvMerchantLocationText, tvMerchantLocation, tvMerchantStartDateText, tvMerchantStartDate,
			tvMerchantEndDateText, tvMerchantEndDate, tvRedeemedNumber, tvRedeemed, tvSoldNumber, tvSold,
			tvProgressText;
	ProgressBar progressBar;
	ProgressBar progressBarDownload;
	RelativeLayout rootLayout;

	private DatePicker datePicker;
	private Calendar calendar;
	private int year, month, day;
	private String mEndDate;
	TextView vDatePicker;
	boolean isDateselectionOk=false;
	public DashBoardFragment(SlidingMenuActivity slidingMenuActivity) {
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.dahsboard_fragment, null);
		spref = PreferenceManager.getDefaultSharedPreferences(getActivity());
		initializeViews();

		return root;
	}

	private void initializeViews() {
		rootLayout = (RelativeLayout) root.findViewById(R.id.llForm);

		ActivateQr = (Button) root.findViewById(R.id.Activate);

		tvMerchantAccount = (TextView) root.findViewById(R.id.tvMerchantAccount);
		tvMerchantName1 = (TextView) root.findViewById(R.id.tvMerchantName1);
		tvMerchantUserIdText = (TextView) root.findViewById(R.id.tvMerchantUserIdText);
		tvMerchantUserId = (TextView) root.findViewById(R.id.tvMerchantUserId);
		tvMerchantText = (TextView) root.findViewById(R.id.tvMerchantText);
		tvMerchantName = (TextView) root.findViewById(R.id.tvMerchantName);
		tvMerchantLocationText = (TextView) root.findViewById(R.id.tvMerchantLocationText);
		tvMerchantLocation = (TextView) root.findViewById(R.id.tvMerchantLocation);
		tvMerchantStartDateText = (TextView) root.findViewById(R.id.tvMerchantStartDateText);
		tvMerchantStartDate = (TextView) root.findViewById(R.id.tvMerchantStartDate);
		tvMerchantEndDateText = (TextView) root.findViewById(R.id.tvMerchantEndDateText);
		tvMerchantEndDate = (TextView) root.findViewById(R.id.tvMerchantEndDate);

		tvRedeemedNumber = (TextView) root.findViewById(R.id.tvRedeemedNumber);
		tvRedeemed = (TextView) root.findViewById(R.id.tvRedeemed);
		tvSoldNumber = (TextView) root.findViewById(R.id.tvSoldNumber);
		tvSold = (TextView) root.findViewById(R.id.tvSold);
		tvProgressText = (TextView) root.findViewById(R.id.tvProgressText);

		progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
		progressBarDownload = (ProgressBar) root.findViewById(R.id.progressBarDownload);

		try {
			ActivateQr.setTypeface(Helper.getSharedHelper().boldFont);
			tvMerchantAccount.setTypeface(Helper.getSharedHelper().boldFont);
			tvMerchantName1.setTypeface(Helper.getSharedHelper().normalFont);
			tvMerchantUserIdText.setTypeface(Helper.getSharedHelper().boldFont);
			tvMerchantUserId.setTypeface(Helper.getSharedHelper().normalFont);
			tvMerchantText.setTypeface(Helper.getSharedHelper().boldFont);
			tvMerchantName.setTypeface(Helper.getSharedHelper().normalFont);
			tvMerchantLocationText.setTypeface(Helper.getSharedHelper().boldFont);
			tvMerchantLocation.setTypeface(Helper.getSharedHelper().normalFont);
			tvMerchantStartDateText.setTypeface(Helper.getSharedHelper().boldFont);
			tvMerchantStartDate.setTypeface(Helper.getSharedHelper().normalFont);
			tvMerchantEndDateText.setTypeface(Helper.getSharedHelper().boldFont);
			tvMerchantEndDate.setTypeface(Helper.getSharedHelper().normalFont);
			tvRedeemedNumber.setTypeface(Helper.getSharedHelper().normalFont);
			tvRedeemed.setTypeface(Helper.getSharedHelper().normalFont);
			tvSoldNumber.setTypeface(Helper.getSharedHelper().normalFont);
			tvSold.setTypeface(Helper.getSharedHelper().normalFont);
			tvProgressText.setTypeface(Helper.getSharedHelper().normalFont);
			ActivateQr.setBackgroundDrawable(
					Helper.getSharedHelper().getGradientDrawable(Helper.getSharedHelper().reatiler.getButton_color()));
			ActivateQr.setTextColor(Color.parseColor("#" + Helper.getSharedHelper().reatiler.getRetailerTextColor()));

			tvRedeemed.setTextColor(Color.parseColor("#" + Helper.getSharedHelper().reatiler.getHeaderColor()));
			tvSold.setTextColor(Color.parseColor("#" + Helper.getSharedHelper().reatiler.getHeaderColor()));
			// GradientDrawable bgShape = (GradientDrawable) progressBar
			// .getProgressDrawable();
			// bgShape.setColor(Color.parseColor("#"
			// + Helper.getSharedHelper().reatiler.getHeaderColor()));
			progressBar.getProgressDrawable().setColorFilter(
					Color.parseColor("#" + Helper.getSharedHelper().reatiler.getHeaderColor()), Mode.SRC_IN);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ActivateQr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), RedeemVoucherActivity.class);
				startActivity(intent);
			}
		});

		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);

		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		
		populateSetDate( year, month, day, tvMerchantEndDate);

		tvMerchantStartDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				setDate(v);
				
			}
		});
		tvMerchantEndDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				setDate(v);
				
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		new GetSellerInfo().execute();
	}

	class GetSellerInfo extends AsyncTask<String, Void, JSONObject> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressBarDownload.setVisibility(View.VISIBLE);
		}

		@Override
		protected JSONObject doInBackground(String... params) {

			JSONObject param = new JSONObject();

			try {
				String email = spref.getString(Constants.KEY_EMAIL, "");
				param.put("retailerId", "MerchantA1");
				param.put("email", email);
				param.put("startDate", tvMerchantStartDate.getText().toString());
				param.put("endDate", tvMerchantEndDate.getText().toString());
				Log.e("Email: ", "Email  ::" + spref.getString("KEY_EMAIL", ""));
				JSONObject jsonObject = HTTPHandler.defaultHandler().doPost(Constants.URL_SELLER_ACCOUNT, param);

				return jsonObject;

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}

		@Override
		protected void onPostExecute(JSONObject json) {
			super.onPostExecute(json);
			progressBarDownload.setVisibility(View.GONE);
			rootLayout.setVisibility(View.VISIBLE);
			if (json != null) {
				if (json.has("errorCode")) {
					try {
						String errorCode = json.getString("errorCode");
						if (errorCode != null && errorCode.equals("1")) {

							Gson gson = new Gson();
							UserDetailObject userDetail = gson.fromJson(json.toString(), UserDetailObject.class);
							Profile userProfile = userDetail.userProfile;
							setTextViews(userProfile);
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void setTextViews(Profile userProfile) {

		tvMerchantName1.setText(userProfile.company_name);
		tvMerchantUserId.setText(userProfile.getPhone());
		tvMerchantName.setText(userProfile.getEmail());
		tvMerchantLocation.setText(userProfile.getCountry());

		// tvMerchantMonth.setText(userProfile.company_name);

		// tvRedeemed.setText(userProfile.company_name);
		// tvSold.setText(userProfile.company_name);
		setProgress(userProfile.orders_redeemed, userProfile.orders_sold);
	}

	void setProgress(String redeemed, String sold) {
		try {
			int percentage = (int) ((Float.parseFloat(redeemed) / Float.parseFloat(sold)) * 100);
			progressBar.setProgress(percentage);
			tvProgressText.setText(percentage);
			tvRedeemedNumber.setText(redeemed);
			tvSoldNumber.setText(sold);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void setDate(View view) {
		vDatePicker=(TextView)view;
		DialogFragment newFragment = new SelectDateFragment();
		newFragment.show(getFragmentManager(), "DatePicker");
	}

	public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar calendar = Calendar.getInstance();
			int yy = calendar.get(Calendar.YEAR);
			int mm = calendar.get(Calendar.MONTH);
			int dd = calendar.get(Calendar.DAY_OF_MONTH);
			DatePickerDialog dialog=new DatePickerDialog(getActivity(), this, yy, mm, dd);
			 dialog.getDatePicker().setMaxDate(new Date().getTime());
//			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
			return dialog;
		}

		public void onDateSet(DatePicker view, int yy, int mm, int dd) {
			
			populateSetDate(yy, mm+1, dd,vDatePicker);
		}

	}

	public void populateSetDate(int year, int month, int day, TextView vPicker) {
		String str_date = day+"-"+month+"-"+year;
		
		if(vPicker==tvMerchantEndDate)
		{
			mEndDate=str_date;
			isDateselectionOk=true;
		}
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			try {
				  Date date1 = sdf.parse(str_date);
		          Date date2 = sdf.parse(mEndDate);
		          System.out.println(sdf.format(date1));
		            System.out.println(sdf.format(date2));

		          if(date2.before(date1)){
		                Toast.makeText(getActivity(), "Start date should be before than end date", Toast.LENGTH_SHORT).show();
		              //  setDate(tvMerchantStartDate);
		                isDateselectionOk=false;
		            }else
		            {
		            	isDateselectionOk=true;
		            }
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 if(isDateselectionOk){
			 vPicker.setText(str_date);
			 vPicker.setTextColor(Color.BLACK);
			 new GetSellerInfo().execute();
		 }
		 
			 
		/*Log.e("MONTH", str_date);
		SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-mm-dd",Locale.UK);
		SimpleDateFormat  writeFormat = new SimpleDateFormat("dd MMM yyyy",Locale.UK);

        try {
         Date   date = readFormat.parse(str_date);
         vPicker.setText(""+writeFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

		
	}

}
