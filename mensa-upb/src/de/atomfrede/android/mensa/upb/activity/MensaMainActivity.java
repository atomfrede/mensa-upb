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
package de.atomfrede.android.mensa.upb.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

import de.atomfrede.android.mensa.R;
import de.atomfrede.android.mensa.upb.MensaConstants;
import de.atomfrede.android.mensa.upb.data.MealParser;
import de.atomfrede.android.mensa.upb.data.MealPlan;

public class MensaMainActivity extends AbstractWeeklyMealActivity {

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setTitle(getResources().getString(R.string.mensa_title));

		mPager = (ViewPager) findViewById(R.id.pager);
		mAdapter = new WeekdayPagerAdapter(getSupportFragmentManager(), weekdays, MensaConstants.LOC_MENSA);
		mPager.setAdapter(mAdapter);

		TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(mPager);
		indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
		mIndicator = indicator;

		selectInitialDay();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (MealPlan.getInstance().getMensaMeal() == null) {
			// now reload the data 'cause we resume from somewhere and the
			// application was killed
			reloadData();
		}

	}
	
	@Override
	protected void reloadData() {
		try {
			MealPlan.getInstance().setMensaMeal(MealParser.parseXmlString(settings.getString(MensaConstants.MENSA_XML_KEY, "")));
		} catch (Exception e) {

		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			return true;
		case R.id.menu_information:
			showOpeningTimes();
			return true;
		case R.id.menu_mensa:
			return true;
		case R.id.menu_pub:
			Intent pubIntent = new Intent(this, PubMainActivity.class);
			startActivity(pubIntent);
			return true;
		case R.id.menu_hotspot:
			Intent hotspotIntent = new Intent(this, BistroMainActivity.class);
			startActivity(hotspotIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void showOpeningTimes() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setCancelable(true);
		builder.setTitle(R.string.title_opening_times);
		String[] mensaOpeningTimes = getResources().getStringArray(R.array.mensa_opening_times);
		builder.setMessage(mensaOpeningTimes[0]+"\n"+mensaOpeningTimes[1]);
		
		mDialog = builder.create();
		mDialog.show();
	}

}
