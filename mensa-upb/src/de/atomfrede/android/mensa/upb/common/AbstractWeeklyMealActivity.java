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

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.PageIndicator;

import de.atomfrede.android.mensa.R;
import de.atomfrede.android.mensa.upb.MensaConstants;
import de.atomfrede.android.mensa.upb.data.meals.MealPlan;
import de.atomfrede.android.mensa.upb.data.meals.WeeklyMeal;

public abstract class AbstractWeeklyMealActivity extends AbstractMealActivity {

	public static String TAG = "AbstractWeeklyMealActivity";

	protected String[] weekdays;

	protected WeekdayPagerAdapter mAdapter;
	protected ViewPager mPager;
	protected PageIndicator mIndicator;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weekly_meal);
		weekdays = getResources().getStringArray(R.array.weekdays_short);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
	}

	protected abstract void reloadData();

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