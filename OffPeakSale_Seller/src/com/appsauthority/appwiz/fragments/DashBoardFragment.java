package com.appsauthority.appwiz.fragments;

import com.appauthority.appwiz.fragments.SlidingMenuActivity;
import com.offpeaksale.seller.R;
import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DashBoardFragment extends Fragment {

	private View root;
	public DashBoardFragment(SlidingMenuActivity slidingMenuActivity) {
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.dahsboard_fragment, null);
		return root;
	}
	
}
