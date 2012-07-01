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
package de.atomfrede.android.mensa.adapter;

import android.support.v4.app.*;
import de.atomfrede.android.mensa.MensaConstants;
import de.atomfrede.android.mensa.data.*;
import de.atomfrede.android.mensa.fragment.DailyMealListFragment;

public class WeekdayPagerAdapter extends FragmentPagerAdapter {

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
		switch(location){
		case MensaConstants.LOC_MENSA:
			return weekdays[position]+" "+MealPlan.getInstance().getMensaMeal().getMeals().get(position).getShortDate();
		case MensaConstants.LOC_HOT_SPOT:
			return weekdays[position]+" "+MealPlan.getInstance().getHotspotMeal().getMeals().get(position).getShortDate();
		case MensaConstants.LOC_PUB:
			return weekdays[position]+" "+MealPlan.getInstance().getPubMeal().getMeals().get(position).getShortDate();
		default:
			return weekdays[position];
		}
	}
	
	private DailyMealListFragment getDataFragment(WeeklyMeal weeklyMeal, int tab){
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
