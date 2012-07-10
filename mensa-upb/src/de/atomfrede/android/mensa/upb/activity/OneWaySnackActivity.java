package de.atomfrede.android.mensa.upb.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import de.atomfrede.android.mensa.R;
import de.atomfrede.android.mensa.upb.adapter.SnackListAdapter;
import de.atomfrede.android.mensa.upb.data.MealPlan;

public class OneWaySnackActivity extends SherlockListActivity {

	static final String TAG = "OneWaySnackActivity";

	protected AlertDialog mDialog;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_selection);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		getSupportActionBar().setTitle(getResources().getString(R.string.one_way_title));

		setListAdapter(new SnackListAdapter(this, MealPlan.getInstance().getOneWaySnacks()));
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			return true;
		case R.id.menu_about:
			showAboutDialog();
			return true;
		case R.id.menu_information:
			showOpeningTimes();
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
	
	protected void showOpeningTimes() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setCancelable(true);
		builder.setTitle(R.string.title_opening_times);
		String[] onewaySnackOpeningTimes = getResources().getStringArray(R.array.one_way_snack_opening_times);
		builder.setMessage(onewaySnackOpeningTimes[0]);

		mDialog = builder.create();
		mDialog.show();
	}
}
