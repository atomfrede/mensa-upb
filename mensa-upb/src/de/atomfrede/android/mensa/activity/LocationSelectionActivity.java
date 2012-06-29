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

import java.util.Calendar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuInflater;

import de.atomfrede.android.mensa.MensaConstants;
import de.atomfrede.android.mensa.R;
import de.atomfrede.android.mensa.data.*;

public class LocationSelectionActivity extends SherlockListActivity {

	public static String TAG = "LocationSelectionActivity";
	
	private static final boolean refreshAlways = true;
	String[] locations;
	SharedPreferences settings;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_selection);

		locations = getResources().getStringArray(R.array.locations);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, locations);
		setListAdapter(adapter);

		settings = getSharedPreferences(MensaConstants.MENSA_PREFS, LocationSelectionActivity.MODE_PRIVATE);
		
		if(refreshAlways || refreshRequired())
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
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
	}

	private boolean refreshRequired(){
		if(!settings.contains(MensaConstants.LAST_UPDATE_KEY))
			return true;
		
		int lastUpdate = settings.getInt(MensaConstants.LAST_UPDATE_KEY, -1);
		if(getWeekOfYear() > lastUpdate)
			return true;
		return false;
		
	}
	
	private int getWeekOfYear(){
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.WEEK_OF_YEAR);
	}
	
	public void downloadData() {
		LoadAndParseXmlTask task = new LoadAndParseXmlTask();
		task.execute();
		//Log.d(TAG, "Week of year "+getWeekOfYear());
		//settings.edit().putInt(MensaConstants.LAST_UPDATE_KEY, getWeekOfYear()).commit();
	}

	private class LoadAndParseXmlTask extends AsyncTask<Void, Integer, MealPlan> {

		@Override
		protected MealPlan doInBackground(Void... params) {
			try {
				MealPlan mealPlan = MealPlan.getInstance();
				
				String mensaXml = Loader.downloadXml(MensaConstants.MENSA_URL);
				WeeklyMeal mensaMeal = MealParser.parseXmlString(mensaXml);
				mealPlan.setMensaMeal(mensaMeal);
				settings.edit().putString(MensaConstants.MENSA_XML_KEY, mensaXml).commit();

				String hotspotXml = Loader.downloadXml(MensaConstants.HOTSPOT_URL); 
				WeeklyMeal hotSpotMeal = MealParser.parseXmlString(hotspotXml);
				mealPlan.setHotspotMeal(hotSpotMeal);
				settings.edit().putString(MensaConstants.HOTSPOT_XML_KEY, hotspotXml).commit();
				
				String pubXml = Loader.downloadXml(MensaConstants.PUB_URL);
				WeeklyMeal pubMeal = MealParser.parseXmlString(pubXml);
				mealPlan.setPubMeal(pubMeal);
				settings.edit().putString(MensaConstants.PUB_XML_KEY, pubXml).commit();
				
				return mealPlan;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onPostExecute(MealPlan result) {
			settings.edit().putInt(MensaConstants.LAST_UPDATE_KEY, getWeekOfYear()).commit();
		}

	}
}
