package de.atomfrede.android.mensa.upb;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import de.atomfrede.android.mensa.upb.adapter.MealFragmentStatePagerAdapter;
import de.atomfrede.android.mensa.upb.contants.CacheKeys;
import de.atomfrede.android.mensa.upb.contants.Locations;
import de.atomfrede.android.mensa.upb.data.DailyMeal;
import de.atomfrede.android.mensa.upb.data.Mealplans;
import de.atomfrede.android.mensa.upb.data.WeeklyMeal;
import de.atomfrede.android.mensa.upb.loader.Loader;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

@EFragment(R.layout.fragment_weekly_meal)
public class WeeklyMealFragment extends Fragment {

    public static WeeklyMealFragment_ newInstance(int location) {
        WeeklyMealFragment_ f = new WeeklyMealFragment_();

        Bundle args = new Bundle();
        args.putInt("location", location);
        f.setArguments(args);

        return f;
    }

    protected MealFragmentStatePagerAdapter mAdapter;

    private WeeklyMeal weeklyMeal;

    @FragmentArg("location")
    public int location;

    @ViewById(R.id.refresh_progress_bar)
    protected ProgressBar loadingProgressbar;

    @ViewById(R.id.warning_wrapper)
    protected RelativeLayout warningWrapper;

    @ ViewById(R.id.pager_title_strip)
    protected PagerTabStrip pagerTabStrip;

    @ViewById(R.id.pager)
    protected ViewPager mPager;

    protected SharedPreferences sp;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData(false);
    }

    @UiThread
    public void onDataLoaded() {
        try {
            restoreSubtitle();

            mAdapter = new MealFragmentStatePagerAdapter(getActivity().getSupportFragmentManager(), location);
            mPager.setAdapter(mAdapter);

            pagerTabStrip.setDrawFullUnderline(true);
            pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.pager_title_strip_indicator));

            loadingProgressbar.setVisibility(View.GONE);

            selectInitialDay();
            mPager.setVisibility(View.VISIBLE);
            mPager.setSaveEnabled(false);
            mPager.invalidate();
            mAdapter.notifyDataSetChanged();

            ((MainActivity_) getActivity()).setRefreshActionButtonState(false);
        } catch (IllegalStateException ste) {
            Log.e("WeeklyMealFragment", "Error on data loaded CB. ", ste);
        }
    }

    @UiThread
    public void onDataFailed() {
        Crouton.makeText(getActivity(), R.string.error_during_load, Style.ALERT).show();
        loadingProgressbar.setVisibility(View.GONE);
        warningWrapper.setVisibility(View.VISIBLE);

        ((MainActivity_)getActivity()).setRefreshActionButtonState(false);
    }

    @OptionsItem(R.id.menu_refresh)
    public void reloadData() {
        Log.d("menu", "reload data");
        loadingProgressbar.setVisibility(View.VISIBLE);
        warningWrapper.setVisibility(View.GONE);

        mPager.setVisibility(View.GONE);

        loadData(true);
    }

    @Background
    public void loadData(boolean force) {
        try {
            WeeklyMeal fromCache = getFromCache();

            if(fromCache == null || force) {
                weeklyMeal = Loader.reloadWeeklyMeal(location);
            } else {
                weeklyMeal = fromCache;
            }

            cacheWeeklyMeal();
            onDataLoaded();
        } catch(IOException ioe) {
            Log.e("Loader", "Cant Load data.", ioe);
            onDataFailed();

        }
    }

    private void restoreSubtitle() {
        switch (location) {
            case Locations.MENSA:
                ((MainActivity_) getActivity()).restoreSubtitle(getString(R.string.title_mensa));
                break;
            case Locations.HOTSPOT:
                ((MainActivity_) getActivity()).restoreSubtitle(getString(R.string.title_hotspot));
                break;
            case Locations.PUB:
                ((MainActivity_) getActivity()).restoreSubtitle(getString(R.string.title_pub));
                break;
            case Locations.HAMM:
                ((MainActivity_) getActivity()).restoreSubtitle(getString(R.string.title_hamm));
                break;
            case Locations.LIPPSTADT:
                ((MainActivity_) getActivity()).restoreSubtitle(getString(R.string.title_lippstadt));
                break;
            case Locations.FORUM:
                ((MainActivity_) getActivity()).restoreSubtitle(getString(R.string.title_forum));
                break;
        }
    }
    private WeeklyMeal getFromCache() {
        WeeklyMeal meal = null;
        boolean updateRequired = updateRequired();


        if(updateRequired) {
            return null;
        }

        switch (location) {
            case Locations.MENSA:
                if(sp.contains(CacheKeys.MENSA)) {
                    String json = sp.getString(CacheKeys.MENSA, null);
                    if(json != null) {
                        meal = WeeklyMeal.fromJson(json);
                        Mealplans.getInstance().setMensa(meal);
                    }
                }
                break;
            case Locations.HOTSPOT:
                if(sp.contains(CacheKeys.HOTSPOT)) {
                    String json = sp.getString(CacheKeys.HOTSPOT, null);
                    if(json != null) {
                        meal = WeeklyMeal.fromJson(json);
                        Mealplans.getInstance().setHotspot(meal);
                    }
                }
                break;
            case Locations.PUB:
                if(sp.contains(CacheKeys.PUB)) {
                    String json = sp.getString(CacheKeys.PUB, null);
                    if(json != null) {
                        meal = WeeklyMeal.fromJson(json);
                        Mealplans.getInstance().setPub(meal);
                    }
                }
                break;
            case Locations.HAMM:
                if(sp.contains(CacheKeys.HAMM)) {
                    String json = sp.getString(CacheKeys.HAMM, null);
                    if(json != null) {
                        meal = WeeklyMeal.fromJson(json);
                        Mealplans.getInstance().setBasilica(meal);
                    }
                }
                break;

            case Locations.LIPPSTADT:
                if(sp.contains(CacheKeys.LIPPSTADT)) {
                    String json = sp.getString(CacheKeys.LIPPSTADT, null);
                    if(json != null) {
                        meal = WeeklyMeal.fromJson(json);
                        Mealplans.getInstance().setAtrium(meal);
                    }
                }
                break;
            case Locations.FORUM:
                if(sp.contains(CacheKeys.FORUM)) {
                    String json = sp.getString(CacheKeys.FORUM, null);
                    if(json != null) {
                        meal = WeeklyMeal.fromJson(json);
                        Mealplans.getInstance().setForum(meal);
                    }
                }
                break;
        }

        return meal;
    }

    private boolean updateRequired() {
        switch (location) {
            case Locations.MENSA:
                return updateRequired(CacheKeys.LAST_UPDATE_MENSA);
            case Locations.HOTSPOT:
                return updateRequired(CacheKeys.LAST_UPDATE_HOTSPOT);
            case Locations.PUB:
                return updateRequired(CacheKeys.LAST_UPDATE_PUB);
            case Locations.HAMM:
                return updateRequired(CacheKeys.LAST_UPDATE_HAMM);
            case Locations.LIPPSTADT:
                return updateRequired(CacheKeys.LAST_UPDATE_LIPPSTADT);
            case Locations.FORUM:
                return updateRequired(CacheKeys.LAST_UPDATE_FORUM);
            default:
                return true;
        }

    }

    private boolean updateRequired(String cacheKey) {
        if(!sp.contains(cacheKey)) {
            return true;
        }

        int lastUpdate = sp.getInt(cacheKey, -1);

        if (lastUpdate > 2000) {
            return true;
        }

        return CacheValueUtil.getCacheValue() > lastUpdate;
    }
    private void cacheWeeklyMeal() {
        switch (location) {
            case Locations.MENSA:
                sp.edit().putString(CacheKeys.MENSA, weeklyMeal.toJson()).commit();
                sp.edit().putInt(CacheKeys.LAST_UPDATE_MENSA, CacheValueUtil.getCacheValue()).commit();
                break;
            case Locations.HOTSPOT:
                sp.edit().putString(CacheKeys.HOTSPOT, weeklyMeal.toJson()).commit();
                sp.edit().putInt(CacheKeys.LAST_UPDATE_HOTSPOT, CacheValueUtil.getCacheValue()).commit();
                break;
            case Locations.PUB:
                sp.edit().putString(CacheKeys.PUB, weeklyMeal.toJson()).commit();
                sp.edit().putInt(CacheKeys.LAST_UPDATE_PUB, CacheValueUtil.getCacheValue()).commit();
                break;
            case Locations.HAMM:
                sp.edit().putString(CacheKeys.HAMM, weeklyMeal.toJson()).commit();
                sp.edit().putInt(CacheKeys.LAST_UPDATE_HAMM, CacheValueUtil.getCacheValue()).commit();
                break;
            case Locations.LIPPSTADT:
                sp.edit().putString(CacheKeys.LIPPSTADT, weeklyMeal.toJson()).commit();
                sp.edit().putInt(CacheKeys.LAST_UPDATE_LIPPSTADT, CacheValueUtil.getCacheValue()).commit();
                break;
            case Locations.FORUM:
                sp.edit().putString(CacheKeys.FORUM, weeklyMeal.toJson()).commit();
                sp.edit().putInt(CacheKeys.LAST_UPDATE_FORUM, CacheValueUtil.getCacheValue()).commit();
                break;
            default:
                break;
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity_) activity).onSectionAttached(location);

        //((MainActivity_) activity).restoreSubtitle();
    }

    protected void selectInitialDay() {
       int counter = -1;
       Calendar today =  Calendar.getInstance();
       for(DailyMeal meal:weeklyMeal.getMeals()) {
           counter++;
           Date d = meal.getDate();
           Calendar mealDate = Calendar.getInstance();
           mealDate.setTime(d);

           int dayOfMonthOfMeal = mealDate.get(Calendar.DAY_OF_MONTH);
           int dayOfMonthOfToday = today.get(Calendar.DAY_OF_MONTH);

           if(dayOfMonthOfMeal == dayOfMonthOfToday) {
                mPager.setCurrentItem(counter);
           }
       }
    }
}
