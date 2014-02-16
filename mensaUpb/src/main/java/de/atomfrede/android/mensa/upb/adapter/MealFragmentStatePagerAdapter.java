package de.atomfrede.android.mensa.upb.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.text.DateFormat;

import de.atomfrede.android.mensa.upb.DailyMenuListFragment_;
import de.atomfrede.android.mensa.upb.contants.Locations;
import de.atomfrede.android.mensa.upb.data.Mealplans;
import de.atomfrede.android.mensa.upb.data.WeeklyMeal;

/**
 * Created by fred on 16.02.14.
 */
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
                return DateFormat.getDateInstance().format(Mealplans.getInstance().getMensa().getMeals().get(min).getDate());
            case Locations.HOTSPOT:
                min = Math.min(Mealplans.getInstance().getHotspot().getMeals().size()-1, position);
                return DateFormat.getDateInstance().format(Mealplans.getInstance().getHotspot().getMeals().get(min).getDate());
        }

        return "Position "+position;
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
