package de.atomfrede.android.mensa.upb;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;

import de.atomfrede.android.mensa.upb.contants.Locations;

@OptionsMenu(R.menu.menu_main)
@EActivity
public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private WeeklyMealFragment_ currentFragment;

    @OptionsMenuItem(R.id.menu_refresh)
    protected MenuItem refreshMenuItem;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private String subTitle;

    private int location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        location = position;
        FragmentManager fragmentManager = getSupportFragmentManager();
        currentFragment = WeeklyMealFragment.newInstance(position);
        fragmentManager.beginTransaction()
                .replace(R.id.container, currentFragment)
                .commit();


    }

    public void onSectionAttached(int number) {
        switch (number) {
            case Locations.MENSA:
                subTitle = getString(R.string.title_mensa);
                break;
            case Locations.PUB:
                subTitle = getString(R.string.title_pub);
                break;
            case Locations.HOTSPOT:
                subTitle = getString(R.string.title_hotspot);
                break;
            case Locations.HAMM:
                subTitle = getString(R.string.title_hamm);
                break;
            case Locations.LIPPSTADT:
                subTitle = getString(R.string.title_lippstadt);
                break;
        }

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        actionBar.setSubtitle(subTitle);
    }

    public void restoreSubtitle(String subTitle) {
        getSupportActionBar().setSubtitle(subTitle);
    }


    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
    //    if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
    //        getMenuInflater().inflate(R.menu.main, menu);
    //        restoreActionBar();
    //        return true;
    //    }
    //    return super.onCreateOptionsMenu(menu);
   //}

    @OptionsItem(R.id.menu_about)
    public void onMenuItemAbout() {
        showAboutDialog();
    }

    @OptionsItem(R.id.menu_opening)
    public void onMenuItemOpeningTimes() {
        showOpeningTimes();
    }

    @OptionsItem(R.id.menu_refresh)
    public void reloadData() {
        if(currentFragment != null) {
            currentFragment.reloadData();
        }
    }

    public void setRefreshActionButtonState(boolean refreshing) {
        if(refreshMenuItem != null) {
            if(refreshing) {
                refreshMenuItem.setActionView(R.layout.actionbar_indeterminate_progress);
            } else {
                refreshMenuItem.setActionView(null);
            }
        }
    }
    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      //  int id = item.getItemId();
        //if (id == R.id.menu_about) {
          //  showAboutDialog();
        //}
        //return super.onOptionsItemSelected(item);
    //}

    public void showOpeningTimes() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);

        builder.setTitle(R.string.opening_times_title);

        String[] openingTimes = getOpeningTimes();
        if(openingTimes.length == 1) {
            builder.setMessage(openingTimes[0]);
        } else {
            builder.setMessage(openingTimes[0]+"\n"+openingTimes[1]);
        }

        builder.create().show();
    }

    private String[] getOpeningTimes() {
        switch (location) {
            case Locations.MENSA:
                return getResources().getStringArray(R.array.mensa_opening_times);
            case Locations.PUB:
                return getResources().getStringArray(R.array.pub_opening_times);
            case Locations.HOTSPOT:
                return getResources().getStringArray(R.array.hotspot_opening_times);
            case Locations.HAMM:
                return getResources().getStringArray(R.array.hamm_opening_times);
            case Locations.LIPPSTADT:
                return getResources().getStringArray(R.array.lippstadt_opening_times);
        }

        return getResources().getStringArray(R.array.mensa_opening_times);
    }

    public void showAboutDialog() {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.about_dialog);
        dialog.setTitle(getResources().getString(R.string.menu_about) + " " + getResources().getString(R.string.app_name));
        dialog.setCancelable(true);

        Button feedbackButton = (Button) dialog.findViewById(R.id.feedbackButton);
        feedbackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendFeedbackMail();
            }
        });

        String app_ver = "";
        try {
            app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //Log.v(TAG, e.getMessage());
        }

        TextView versionName = (TextView) dialog.findViewById(R.id.textView1);
        versionName.setText("Version " + app_ver);

        ((TextView)dialog.findViewById(R.id.textView8)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)dialog.findViewById(R.id.textView9)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)dialog.findViewById(R.id.textView10)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)dialog.findViewById(R.id.textView11)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)dialog.findViewById(R.id.textView12)).setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();
    }

    public void sendFeedbackMail() {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "atomfrede@gmail.com" });

        startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.feedback_provide_by)));
    }
}
