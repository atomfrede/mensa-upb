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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

import de.atomfrede.android.mensa.R;
import de.atomfrede.android.mensa.upb.MensaConstants;
import de.atomfrede.android.mensa.upb.adapter.WeekdayPagerAdapter;

public class BistroMainActivity extends AbstractWeeklyMealActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setTitle(getResources().getString(R.string.bistro_title));
		
		mPager = (ViewPager) findViewById(R.id.pager);
		mAdapter = new WeekdayPagerAdapter(getSupportFragmentManager(), weekdays, MensaConstants.LOC_HOT_SPOT);
		mPager.setAdapter(mAdapter);

		TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(mPager);
		indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
		mIndicator = indicator;
		
		selectInitialDay();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			return true;
		case R.id.menu_mensa:
			Intent mensaIntent = new Intent(this, MensaMainActivity.class);
			startActivity(mensaIntent);
			return true;
		case R.id.menu_pub:
			Intent pubIntent = new Intent(this, PubMainActivity.class);
			startActivity(pubIntent);
			return true;
		case R.id.menu_hotspot:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
