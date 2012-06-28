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
package de.atomfrede.android.mensa.fragment;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockListFragment;

import de.atomfrede.android.mensa.adapter.MenuListAdapter;
import de.atomfrede.android.mensa.data.DailyMeal;

public class DailyMealListFragment extends SherlockListFragment {

	public static DailyMealListFragment newInstance(DailyMeal meal) {
		DailyMealListFragment newFragment = new DailyMealListFragment();
		newFragment.meal = meal;
		return newFragment;
	}

	DailyMeal meal;

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		MenuListAdapter menuListAdapter = new MenuListAdapter(this.getActivity(), android.R.layout.simple_list_item_1, meal.getMenues());
		setListAdapter(menuListAdapter);
		
	}
}
