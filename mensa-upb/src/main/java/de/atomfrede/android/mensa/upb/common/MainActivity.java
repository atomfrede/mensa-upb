package de.atomfrede.android.mensa.upb.common;

import java.util.*;

import android.app.Dialog;
import android.content.*;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.googlecode.androidannotations.annotations.*;

import de.atomfrede.android.mensa.R;
import de.atomfrede.android.mensa.upb.MensaConstants;
import de.atomfrede.android.mensa.upb.data.meals.*;
import de.atomfrede.android.mensa.upb.data.xml.Loader;
import de.atomfrede.android.mensa.upb.data.xml.MealParser;
import de.atomfrede.android.mensa.upb.wok.WokMeal;
import de.neofonie.mobile.app.android.widget.crouton.Crouton;
import de.neofonie.mobile.app.android.widget.crouton.Style;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends SherlockFragmentActivity {
	
	public static final String TAG = ("MainActivity");
	
	public SharedPreferences settings;
	public com.actionbarsherlock.view.Menu optionsMenu;
	
	@FragmentById(R.id.location_fragment)
	public LocationFragment locationFragment;

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
//		MenuInflater inflater = getSupportMenuInflater();
//		sinflater.inflate(R.menu.main, menu);
		optionsMenu = menu;
		return true;
	}
	
	@OptionsItem(R.id.menu_refresh)
	public void onRefreshClicked(){
		setRefreshActionButtonState(true);
		downloadData(true);
	}
	
	@OptionsItem(R.id.menu_about)
	public void showAboutDialog() {
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
	
	public void downloadData(boolean reload) {
		LoadAndParseXmlTask task = new LoadAndParseXmlTask();
		if (reload && usingWebauth()) {
			Crouton.makeText(this, R.string.crouton_info_webauth, Style.INFO);
			// as a first workaorund, when using webauth we don't force reload
			task.execute(false);
		} else {
			if (reload && !isOnline()) {
				Crouton.makeText(this, R.string.crouton_info_offline, Style.INFO).show();
				task.execute(false);
			} else {
				task.execute(reload);
			}
		}
	}
	
	public void setRefreshActionButtonState(boolean refreshing) {
		if (optionsMenu == null) {
			return;
		}

		final MenuItem refreshItem = optionsMenu.findItem(R.id.menu_refresh);
		if (refreshItem != null) {
			if (refreshing) {
				refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
			} else {
				refreshItem.setActionView(null);
			}
		}
	}
	
	protected void sendFeedbackMail() {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "atomfrede@gmail.com" });
		startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.feedback_provide_by)));
	}
	
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public boolean usingWebauth() {
		WifiManager wifiMgr = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
		if (wifiInfo != null) {
			String name = wifiInfo.getSSID();
			Log.d(TAG, "Wifi Name = " + name);
			return "webauth".equals(name);
		} else {
			return false;
		}
	}
	
	private int getWeekOfYear() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.WEEK_OF_YEAR);
	}
	
	private class LoadAndParseXmlTask extends AsyncTask<Boolean, Integer, MealPlan> {

		private WeeklyMeal loadMensaMeal(boolean reload) throws Exception {
			// Log.d(TAG, "Loading Mensa Meal");
			String mensaXml;
			if (reload)
				mensaXml = Loader.downloadXml(MensaConstants.MENSA_URL);
			else
				mensaXml = settings.getString(MensaConstants.MENSA_XML_KEY, "");
			// Log.d(TAG, "MensaXML is "+mensaXml);
			WeeklyMeal mensaMeal = MealParser.parseXmlString(mensaXml);
			settings.edit().putString(MensaConstants.MENSA_XML_KEY, mensaXml).commit();
			return mensaMeal;
		}

		private WeeklyMeal loadHotspotMeal(boolean reload) throws Exception {
			// Log.d(TAG, "Loading Hotspot Meal");
			String hotspotXml;
			if (reload)
				hotspotXml = Loader.downloadXml(MensaConstants.HOTSPOT_URL);
			else
				hotspotXml = settings.getString(MensaConstants.HOTSPOT_XML_KEY, "");
			// Log.d(TAG, "HotspotXML is "+hotspotXml);
			WeeklyMeal hotSpotMeal = MealParser.parseXmlString(hotspotXml);
			settings.edit().putString(MensaConstants.HOTSPOT_XML_KEY, hotspotXml).commit();
			return hotSpotMeal;
		}

		private WeeklyMeal loadPubMeal(boolean reload) throws Exception {
			// Log.d(TAG, "Loading PubMeal");
			String pubXml;
			if (reload)
				pubXml = Loader.downloadXml(MensaConstants.PUB_URL);
			else
				pubXml = settings.getString(MensaConstants.PUB_XML_KEY, "");
			// Log.d(TAG, "PubXML is "+pubXml);
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
			setRefreshActionButtonState(false);
		}

	}
}
