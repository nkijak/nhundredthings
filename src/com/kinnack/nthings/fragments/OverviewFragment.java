package com.kinnack.nthings.fragments;

import com.kinnack.nthings.R;
import com.kinnack.nthings.model.History;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OverviewFragment extends Fragment {
	
	public static OverviewFragment newInstance(History history) {
		return new OverviewFragment();
	}
	
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater_, ViewGroup container_,
			Bundle savedInstanceState_) {
		view = inflater_.inflate(R.layout.overview, container_, false);
		return view;
	}
}
