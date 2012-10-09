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

import java.util.*;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import de.atomfrede.android.mensa.R;
import de.atomfrede.android.mensa.upb.MensaConstants;
import de.atomfrede.android.mensa.upb.data.meals.*;
import de.atomfrede.android.mensa.upb.data.xml.Loader;
import de.atomfrede.android.mensa.upb.data.xml.MealParser;
import de.atomfrede.android.mensa.upb.hotspot.BistroMainActivity;
import de.atomfrede.android.mensa.upb.mensa.MensaMainActivity;
import de.atomfrede.android.mensa.upb.pub.PubMainActivity;
import de.atomfrede.android.mensa.upb.snack.OneWaySnackActivity;
import de.atomfrede.android.mensa.upb.wok.WokActivity;
import de.atomfrede.android.mensa.upb.wok.WokMeal;

public class LocationSelectionActivity extends SherlockListActivity {

	public static String TAG = "LocationSelectionActivity";

	private static final boolean refreshAlways = true;
	String[] locations;
	SharedPreferences settings;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_selection);

		locations = getResources().getStringArray(R.array.locations);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_loc, android.R.id.text1, locations);
		setListAdapter(adapter);

		settings = getSharedPreferences(MensaConstants.MENSA_PREFS, LocationSelectionActivity.MODE_PRIVATE);

		if (refreshAlways)
			downloadData(true);
		else
			downloadData(refreshRequired());
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
		if(position == 3){
			Intent wokIntent = new Intent(this, WokActivity.class);
			startActivity(wokIntent);
		}
		if (position == 4) {
			Intent onewaySnackIntent = new Intent(this, OneWaySnackActivity.class);
			startActivity(onewaySnackIntent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			downloadData(true);
			return true;
		case R.id.menu_about:
			showAboutDialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

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
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "atomfrede@gmail.com" });

		startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.feedback_provide_by)));
	}

	private boolean refreshRequired() {
		if (!settings.contains(MensaConstants.LAST_UPDATE_KEY))
			return true;

		int lastUpdate = settings.getInt(MensaConstants.LAST_UPDATE_KEY, -1);
		if (getWeekOfYear() > lastUpdate)
			return true;
		return false;

	}

	private int getWeekOfYear() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.WEEK_OF_YEAR);
	}

	public void downloadData(boolean reload) {
		LoadAndParseXmlTask task = new LoadAndParseXmlTask();
		task.execute(reload);
	}

	private class LoadAndParseXmlTask extends AsyncTask<Boolean, Integer, MealPlan> {

		private WeeklyMeal loadMensaMeal(boolean reload) throws Exception {
//			Log.d(TAG, "Loading Mensa Meal");
			String mensaXml;
			if (reload)
				mensaXml = Loader.downloadXml(MensaConstants.MENSA_URL);
			else
				mensaXml = settings.getString(MensaConstants.MENSA_XML_KEY, "");
//			Log.d(TAG, "MensaXML is "+mensaXml);
			WeeklyMeal mensaMeal = MealParser.parseXmlString(mensaXml);
			settings.edit().putString(MensaConstants.MENSA_XML_KEY, mensaXml).commit();
			return mensaMeal;
		}

		private WeeklyMeal loadHotspotMeal(boolean reload) throws Exception {
//			Log.d(TAG, "Loading Hotspot Meal");
			String hotspotXml;
			if (reload)
				hotspotXml = Loader.downloadXml(MensaConstants.HOTSPOT_URL);
			else
				hotspotXml = settings.getString(MensaConstants.HOTSPOT_XML_KEY, "");
//			Log.d(TAG, "HotspotXML is "+hotspotXml);
			WeeklyMeal hotSpotMeal = MealParser.parseXmlString(hotspotXml);
			settings.edit().putString(MensaConstants.HOTSPOT_XML_KEY, hotspotXml).commit();
			return hotSpotMeal;
		}

		private WeeklyMeal loadPubMeal(boolean reload) throws Exception {
//			Log.d(TAG, "Loading PubMeal");
			String pubXml;
			if (reload)
				pubXml = Loader.downloadXml(MensaConstants.PUB_URL);
			else
				pubXml = settings.getString(MensaConstants.PUB_XML_KEY, "");
//			Log.d(TAG, "PubXML is "+pubXml);
			WeeklyMeal pubMeal = MealParser.parseXmlString(pubXml);
			settings.edit().putString(MensaConstants.PUB_XML_KEY, pubXml).commit();

			return pubMeal;
		}

		@Override
		protected MealPlan doInBackground(Boolean... params) {
			try {
				MealPlan mealPlan = MealPlan.getInstance();
				boolean reload = params[0];
				mealPlan.setMensaMeal(loadMensaMeal(reload));
				mealPlan.setHotspotMeal(loadHotspotMeal(reload));
				mealPlan.setPubMeal(loadPubMeal(reload));

				if (mealPlan.getOneWaySnacks() == null || mealPlan.getOneWaySnacks().isEmpty()) {
					String[] oneWaySnacks = getResources().getStringArray(R.array.one_way_snacks);

					List<StandardMeal> oneWaySnackMeals = new ArrayList<StandardMeal>();

					for (String oneWaySnack : oneWaySnacks) {
						StandardMeal newMeal = new StandardMeal();
						String price = oneWaySnack.split("#")[1];
						String text = oneWaySnack.split("#")[0];
						newMeal.setPrice(price);
						newMeal.setText(text);

						oneWaySnackMeals.add(newMeal);
					}
					mealPlan.setOneWaySnacks(oneWaySnackMeals);
				}

				if (mealPlan.getWokMeals() == null || mealPlan.getWokMeals().isEmpty()) {
					String[] wokMeals = getResources().getStringArray(R.array.wok_meals);

					List<WokMeal> wokMealList = new ArrayList<WokMeal>();

					for (String wokMeal : wokMeals) {
						WokMeal newMeal = new WokMeal();
						String text = wokMeal.split("#")[0];
						String price = wokMeal.split("#")[1];
						String priceXxl = wokMeal.split("#")[2];

						newMeal.setText(text);
						newMeal.setPrice(price);
						newMeal.setPriceXXL(priceXxl);

						wokMealList.add(newMeal);
					}

					mealPlan.setWokMeals(wokMealList);
				}
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
