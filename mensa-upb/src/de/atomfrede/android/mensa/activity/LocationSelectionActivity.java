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
package de.atomfrede.android.mensa.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;

import de.atomfrede.android.mensa.MensaConstants;
import de.atomfrede.android.mensa.R;
import de.atomfrede.android.mensa.data.*;

public class LocationSelectionActivity extends SherlockListActivity {

	public static String TAG = "LocationSelectionActivity";

	String[] locations;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_selection);

		locations = getResources().getStringArray(R.array.locations);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, locations);
		setListAdapter(adapter);

		downloadData();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (position == 0) {
			Intent mensaIntent = new Intent(this, MensaMainActivity.class);
			startActivity(mensaIntent);
		}
		if (position == 1) {
			Intent bistroIntent = new Intent(this, BistroMainActivity.class);
			startActivity(bistroIntent);
		}
		if (position == 2) {
			Intent pubIntent = new Intent(this, PubMainActivity.class);
			startActivity(pubIntent);
		}
	}

	public void downloadData() {
		LoadAndParseXmlTask task = new LoadAndParseXmlTask();
		task.execute();

	}

	private class LoadAndParseXmlTask extends AsyncTask<Void, Integer, MealPlan> {

		@Override
		protected MealPlan doInBackground(Void... params) {
			try {
				MealPlan mealPlan = MealPlan.getInstance();
				WeeklyMeal mensaMeal = MealParser.parseXmlString(Loader.downloadXml(MensaConstants.MENSA_URL));
				mealPlan.setMensaMeal(mensaMeal);

				WeeklyMeal hotSpotMeal = MealParser.parseXmlString(Loader.downloadXml(MensaConstants.HOTSPOT_URL));
				mealPlan.setHotspotMeal(hotSpotMeal);
				
				WeeklyMeal pubMeal = MealParser.parseXmlString(Loader.downloadXml(MensaConstants.PUB_URL));
				mealPlan.setPubMeal(pubMeal);
				return mealPlan;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}
}
