package de.atomfrede.android.mensa.upb.adapter;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Locale;

import de.atomfrede.android.mensa.upb.DailyMenuListFragment_;
import de.atomfrede.android.mensa.upb.contants.Locations;
import de.atomfrede.android.mensa.upb.data.Mealplans;
import de.atomfrede.android.mensa.upb.data.WeeklyMeal;

public class MealFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private int location;

    public MealFragmentStatePagerAdapter(FragmentManager fm, int location) {
        super(fm);
        this.location = location;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int tab) {
        switch (location){
            case Locations.MENSA:
                return getDataFragment(Mealplans.getInstance().getMensa(), tab);
            case Locations.HOTSPOT:
                return getDataFragment(Mealplans.getInstance().getHotspot(), tab);
            case Locations.HAMM:
                return getDataFragment(Mealplans.getInstance().getBasilica(), tab);
            case Locations.PUB:
                return getDataFragment(Mealplans.getInstance().getPub(), tab);
            case Locations.LIPPSTADT:
                return getDataFragment(Mealplans.getInstance().getAtrium(), tab);
            case Locations.FORUM:
                return getDataFragment(Mealplans.getInstance().getForum(), tab);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        switch (location) {
            case Locations.MENSA:
                return Mealplans.getInstance().getMensa().getMeals().size();
            case Locations.HOTSPOT:
                return Mealplans.getInstance().getHotspot().getMeals().size();
            case Locations.HAMM:
                return Mealplans.getInstance().getBasilica().getMeals().size();
            case Locations.PUB:
                return Mealplans.getInstance().getPub().getMeals().size();
            case Locations.LIPPSTADT:
                return Mealplans.getInstance().getAtrium().getMeals().size();
            case Locations.FORUM:
                return Mealplans.getInstance().getForum().getMeals().size();
            default:
                return 5;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int min = 0;
        switch (location) {
            case Locations.MENSA:
                min = Math.min(Mealplans.getInstance().getMensa().getMeals().size()-1, position);
                return new SimpleDateFormat(getDateFormat()).format(Mealplans.getInstance().getMensa().getMeals().get(min).getDate());
            case Locations.HOTSPOT:
                min = Math.min(Mealplans.getInstance().getHotspot().getMeals().size()-1, position);
                return new SimpleDateFormat(getDateFormat()).format(Mealplans.getInstance().getHotspot().getMeals().get(min).getDate());
            case Locations.PUB:
                min = Math.min(Mealplans.getInstance().getPub().getMeals().size()-1, position);
                return new SimpleDateFormat(getDateFormat()).format(Mealplans.getInstance().getPub().getMeals().get(min).getDate());
            case Locations.HAMM:
                min = Math.min(Mealplans.getInstance().getBasilica().getMeals().size()-1, position);
                return new SimpleDateFormat(getDateFormat()).format(Mealplans.getInstance().getBasilica().getMeals().get(min).getDate());
            case Locations.LIPPSTADT:
                min = Math.min(Mealplans.getInstance().getAtrium().getMeals().size()-1, position);
                return new SimpleDateFormat(getDateFormat()).format(Mealplans.getInstance().getAtrium().getMeals().get(min).getDate());
            case Locations.FORUM:
                min = Math.min(Mealplans.getInstance().getAtrium().getMeals().size()-1, position);
                return new SimpleDateFormat(getDateFormat()).format(Mealplans.getInstance().getForum().getMeals().get(min).getDate());
        }

        return "Position "+position;
    }


    @SuppressLint("NewApi")
    private String getDateFormat() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if(currentapiVersion >= 18) {
            return DateFormat.getBestDateTimePattern(Locale.getDefault(), "EEddMMM");
        } else {
            if(Locale.getDefault().getLanguage() == Locale.ENGLISH.getLanguage()) {
                return "EEE, MMM dd";
            } else if(Locale.getDefault().getLanguage() == Locale.GERMANY.getLanguage()) {
                return "EEE, dd. MMM";
            }
        }

        return "EEE, MMM dd";
    }

    private DailyMenuListFragment_ getDataFragment(WeeklyMeal meal, int tab) {
        int min = Math.min(meal.getMeals().size()-1, tab);

        switch (tab) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return DailyMenuListFragment_.newInstance(meal.getMeals().get(min));
            default:
                return DailyMenuListFragment_.newInstance(meal.getMeals().get(0));

        }
    }
}
