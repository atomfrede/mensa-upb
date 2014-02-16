package de.atomfrede.android.mensa.upb;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hb.views.PinnedSectionListView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import de.atomfrede.android.mensa.upb.adapter.DailyMenuListAdapter;
import de.atomfrede.android.mensa.upb.data.AbstractMeal;
import de.atomfrede.android.mensa.upb.data.DailyMeal;
import de.atomfrede.android.mensa.upb.data.WeeklyMeal;

@EFragment(R.layout.fragment_menu_list)
public class DailyMenuListFragment extends Fragment {

    public static DailyMenuListFragment_ newInstance(DailyMeal menu) {
        DailyMenuListFragment_ newFragment = new DailyMenuListFragment_();
        newFragment.menu = menu;
        return newFragment;
    }

    DailyMeal menu;

    @ViewById(R.id.mealList)
    protected PinnedSectionListView listView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null && menu == null) {
            menu = (DailyMeal) savedInstanceState.getSerializable("menu");
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        List<AbstractMeal> meals = new ArrayList<>();
        meals.add(generateSeperator(getResources().getString(R.string.seperator_meals)));
        meals.addAll(menu.getMeals());
        meals.add(generateSeperator(getResources().getString(R.string.seperator_sidedishes)));
        meals.addAll(menu.getSideDishes());
        meals.add(generateSeperator(getResources().getString(R.string.seperator_soups)));
        meals.addAll(menu.getSoups());
        meals.add(generateSeperator(getResources().getString(R.string.seperator_deserts)));
        meals.addAll(menu.getDeserts());
        DailyMenuListAdapter menuListAdapter = new DailyMenuListAdapter(this.getActivity(), meals);
        listView.setAdapter(menuListAdapter);
//		setRetainInstance(true);
    }

    private AbstractMeal generateSeperator(String title) {
        AbstractMeal seperator = new AbstractMeal();
        seperator.setTitle(title);
        seperator.setType(AbstractMeal.Type.SEPERATOR);
        return seperator;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("menu", menu);
    }
}
