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
package de.atomfrede.android.mensa.upb.common;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.*;

import com.actionbarsherlock.app.SherlockFragment;
import com.googlecode.androidannotations.annotations.*;

import de.atomfrede.android.mensa.R;
import de.atomfrede.android.mensa.upb.data.meals.MealPlan;
import de.atomfrede.android.mensa.upb.hotspot.BistroMainActivity;
import de.atomfrede.android.mensa.upb.mensa.MensaMainActivity;
import de.atomfrede.android.mensa.upb.pub.PubMainActivity;
import de.atomfrede.android.mensa.upb.snack.OneWaySnackActivity;
import de.atomfrede.android.mensa.upb.wok.WokActivity;
import de.neofonie.mobile.app.android.widget.crouton.Crouton;
import de.neofonie.mobile.app.android.widget.crouton.Style;

@EFragment(R.layout.fragment_location_list)
public class LocationFragment extends Fragment {

	public String[] locations;
	public boolean isPhoneUI = true;

	@ViewById(R.id.location_list)
	public ListView locationList;

	@AfterViews
	public void setupList() {
		locations = getResources().getStringArray(R.array.locations);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item_loc, android.R.id.text1, locations);
		locationList.setAdapter(adapter);
		addListItemClickListener();

	}

	public void mensaRequested() {
		if (isPhoneUI) {
			if (MealPlan.getInstance() != null && MealPlan.getInstance().getMensaMeal() != null && MealPlan.getInstance().getMensaMeal().getMeals() != null) {
				Intent mensaIntent = new Intent(this.getActivity(), MensaMainActivity.class);
				startActivity(mensaIntent);
			} else {
				Crouton.makeText(this.getActivity(), R.string.crouton_error_mensa_failed, Style.ALERT).show();
			}
		}
	}

	public void hotspotRequested() {
		if (isPhoneUI) {
			if (MealPlan.getInstance() != null && MealPlan.getInstance().getHotspotMeal() != null && MealPlan.getInstance().getHotspotMeal().getMeals() != null) {
				Intent bistroIntent = new Intent(this.getActivity(), BistroMainActivity.class);
				startActivity(bistroIntent);
			} else {
				Crouton.makeText(this.getActivity(), R.string.crouton_error_hotspot_failed, Style.ALERT).show();
			}
		}
	}

	public void pubRequested() {
		if (isPhoneUI) {
			if (MealPlan.getInstance() != null && MealPlan.getInstance().getPubMeal() != null && MealPlan.getInstance().getPubMeal().getMeals() != null) {
				Intent pubIntent = new Intent(this.getActivity(), PubMainActivity.class);
				startActivity(pubIntent);
			} else {
				Crouton.makeText(this.getActivity(), R.string.crouton_error_pub_failed, Style.ALERT).show();
			}
		}
	}

	public void wokRequested() {
		if (isPhoneUI) {
			Intent wokIntent = new Intent(this.getActivity(), WokActivity.class);
			startActivity(wokIntent);
		}
	}

	public void snackRequested() {
		if (isPhoneUI) {
			Intent onewaySnackIntent = new Intent(this.getActivity(), OneWaySnackActivity.class);
			startActivity(onewaySnackIntent);
		}
	}

	public void addListItemClickListener() {
		locationList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (position == 0) {
					mensaRequested();
				}
				if (position == 1) {
					hotspotRequested();
				}
				if (position == 2) {
					pubRequested();
				}
				if (position == 3) {
					wokRequested();
				}
				if (position == 4) {
					snackRequested();
				}

			}
		});
	}
}
