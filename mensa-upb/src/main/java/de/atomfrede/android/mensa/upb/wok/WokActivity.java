/*
 *  Copyright 2012 Frederik Hahne
 *  
 *  This file is part of Mensa UPB.
 *
 *  Mensa UPB is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Mensa UPB is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Mensa UPB.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.atomfrede.android.mensa.upb.wok;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import de.atomfrede.android.mensa.R;
import de.atomfrede.android.mensa.upb.common.AbstractMealActivity;

public class WokActivity extends AbstractMealActivity {

	static final String TAG = "WokActivity";

	protected AlertDialog mDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.standard_layout);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		getSupportActionBar().setTitle(getResources().getString(R.string.wok_title));
		
		Fragment wokListFragment = new WokListFragment();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.fragment_holder, wokListFragment);
		ft.commit();
	}
	
	protected void showOpeningTimes() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setCancelable(true);
		builder.setTitle(R.string.title_opening_times);
		String[] onewaySnackOpeningTimes = getResources().getStringArray(R.array.one_way_snack_opening_times);
		builder.setMessage(onewaySnackOpeningTimes[0]);

		mDialog = builder.create();
		mDialog.show();
	}

	
}
