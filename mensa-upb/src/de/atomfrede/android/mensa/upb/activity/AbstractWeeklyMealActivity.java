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

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.PageIndicator;

import de.atomfrede.android.mensa.R;
import de.atomfrede.android.mensa.upb.MensaConstants;
import de.atomfrede.android.mensa.upb.data.*;
import de.atomfrede.android.mensa.upb.fragment.DailyMealListFragment;

public abstract class AbstractWeeklyMealActivity extends SherlockFragmentActivity {

	public static String TAG = "AbstractWeeklyMealActivity";

	protected String[] weekdays;

	protected WeekdayPagerAdapter mAdapter;
	protected ViewPager mPager;
	protected PageIndicator mIndicator;
	protected AlertDialog mDialog;
	protected SharedPreferences settings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weekly_meal);
		weekdays = getResources().getStringArray(R.array.weekdays_short);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		settings = getSharedPreferences(MensaConstants.MENSA_PREFS, LocationSelectionActivity.MODE_PRIVATE);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	protected abstract void reloadData();

	protected void showAboutDialog() {
		Dialog dialog = new Dialog(this);

		dialog.setContentView(R.layout.about_dialog);
		dialog.setTitle(getResources().getString(R.string.menu_about) + " " + getResources().getString(R.string.app_name));
		dialog.setCancelable(true);

		Button feedbackButton = (Button) dialog.findViewById(R.id.feedbackButton);
		feedbackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendFeedbackMail();
			}
		});

		String app_ver = "";
		try {
			app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			Log.v(TAG, e.getMessage());
		}

		TextView versionName = (TextView) dialog.findViewById(R.id.textView1);
		versionName.setText("Version " + app_ver);
		dialog.show();
	}

	protected void sendFeedbackMail() {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"atomfrede@gmail.com"});

		startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.feedback_provide_by)));
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
			Intent hotspotIntent = new Intent(this, BistroMainActivity.class);
			startActivity(hotspotIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.location, menu);
		return true;
	}

	/**
	 * This methods selects either today, or if today is weekend selects monday
	 * on inital display of the meal overview. Keep in mind: SUNDAY = 1 MONDAY =
	 * 2 TUESDAY = 3 WEDNESDAY = 4 THURSDAY = 5 FRIDAY = 6 SATURDAY = 7
	 * 
	 * But the pager adapter start with 0!
	 */
	protected void selectInitialDay() {
		Calendar today = Calendar.getInstance();
		int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case 2:
			mIndicator.setCurrentItem(0);
			break;
		case 3:
			mIndicator.setCurrentItem(1);
			break;
		case 4:
			mIndicator.setCurrentItem(2);
			break;
		case 5:
			mIndicator.setCurrentItem(3);
			break;
		case 6:
			mIndicator.setCurrentItem(4);
			break;
		default:
			mIndicator.setCurrentItem(0);
			break;
		}
	}

	protected abstract void showOpeningTimes();

	public static class WeekdayPagerAdapter extends FragmentPagerAdapter {

		private String[] weekdays;
		private int location;

		public WeekdayPagerAdapter(FragmentManager fm, String[] weekdays, int location) {
			super(fm);
			this.weekdays = weekdays;
			this.location = location;
		}

		@Override
		public Fragment getItem(int tab) {
			switch (location) {
			case MensaConstants.LOC_MENSA:
				return getDataFragment(MealPlan.getInstance().getMensaMeal(), tab);
			case MensaConstants.LOC_HOT_SPOT:
				return getDataFragment(MealPlan.getInstance().getHotspotMeal(), tab);
			case MensaConstants.LOC_PUB:
				return getDataFragment(MealPlan.getInstance().getPubMeal(), tab);
			default:
				return null;
			}
		}

		@Override
		public int getCount() {
			return weekdays.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (location) {
			case MensaConstants.LOC_MENSA:
				return weekdays[position] + " " + MealPlan.getInstance().getMensaMeal().getMeals().get(position).getShortDate();
			case MensaConstants.LOC_HOT_SPOT:
				return weekdays[position] + " " + MealPlan.getInstance().getHotspotMeal().getMeals().get(position).getShortDate();
			case MensaConstants.LOC_PUB:
				return weekdays[position] + " " + MealPlan.getInstance().getPubMeal().getMeals().get(position).getShortDate();
			default:
				return weekdays[position];
			}
		}

		private DailyMealListFragment getDataFragment(WeeklyMeal weeklyMeal, int tab) {
			switch (tab) {
			case 0:
				return DailyMealListFragment.newInstance(weeklyMeal.getMeals().get(tab));
			case 1:
				return DailyMealListFragment.newInstance(weeklyMeal.getMeals().get(tab));
			case 2:
				return DailyMealListFragment.newInstance(weeklyMeal.getMeals().get(tab));
			case 3:
				return DailyMealListFragment.newInstance(weeklyMeal.getMeals().get(tab));
			case 4:
				return DailyMealListFragment.newInstance(weeklyMeal.getMeals().get(tab));
			default:
				return DailyMealListFragment.newInstance(weeklyMeal.getMeals().get(0));
			}
		}
	}
}
