package de.atomfrede.android.mensa.upb.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by fred on 15.02.14.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments;
    private final List<String> titles;

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<Fragment>();
        this.titles = new ArrayList<String>();
    }

    /**
     * Add fragment to the pager adapter.
     *
     * @param fragment Fragment to add.
     * @param title Title of fragment.
     */
    public void addPage(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }

    /**
     * Return the Fragment associated with a specified position.
     */
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    /**
     * Return the number of fragments available.
     */
    @Override
    public int getCount() {
        return fragments.size();
    }

    /**
     * Return the title string to describe the specified fragment.
     *
     * @param position The position of the title requested.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
