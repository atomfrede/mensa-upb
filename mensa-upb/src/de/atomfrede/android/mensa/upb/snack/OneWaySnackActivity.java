package de.atomfrede.android.mensa.upb.snack;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import de.atomfrede.android.mensa.R;
import de.atomfrede.android.mensa.upb.common.AbstractMealActivity;

public class OneWaySnackActivity extends AbstractMealActivity {

	static final String TAG = "OneWaySnackActivity";

	protected AlertDialog mDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.standard_layout);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		getSupportActionBar().setTitle(getResources().getString(R.string.one_way_title));

		Fragment snackListFragment = new SnackListFragment();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.fragment_holder, snackListFragment);
		ft.commit();
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
