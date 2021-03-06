package de.atomfrede.android.mensa.upb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.hb.views.PinnedSectionListView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
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

    @ViewById(R.id.closed_wrapper)
    protected RelativeLayout closedWrapper;

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

        if(menu.getMeals().isEmpty() && menu.getSideDishes().isEmpty() && menu.getSoups().isEmpty() && menu.getDeserts().isEmpty()) {
            setupClosed();
        } else {
            setupMeals();
        }
    }

    private void setupMeals() {
        listView.setVisibility(View.VISIBLE);
        closedWrapper.setVisibility(View.GONE);

        List<AbstractMeal> meals = new ArrayList<>();

        if(!menu.getMeals().isEmpty()){
            meals.add(generateSeperator(getResources().getString(R.string.seperator_meals)));
            meals.addAll(menu.getMeals());
        }

        if(!menu.getSideDishes().isEmpty()) {
            meals.add(generateSeperator(getResources().getString(R.string.seperator_sidedishes)));
            meals.addAll(menu.getSideDishes());
        }

        if(!menu.getSoups().isEmpty()) {
            meals.add(generateSeperator(getResources().getString(R.string.seperator_soups)));
            meals.addAll(menu.getSoups());
        }

        if(!menu.getDeserts().isEmpty()) {
            meals.add(generateSeperator(getResources().getString(R.string.seperator_deserts)));
            meals.addAll(menu.getDeserts());
        }

        DailyMenuListAdapter menuListAdapter = new DailyMenuListAdapter(this.getActivity(), meals);
        listView.setAdapter(menuListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listItemClicked(i);
            }
        });
    }

    private void setupClosed() {
        listView.setVisibility(View.GONE);
        closedWrapper.setVisibility(View.VISIBLE);
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

    void listItemClicked(int pos) {

        AbstractMeal item = (AbstractMeal)listView.getItemAtPosition(pos);

        if(item != null && item.getType() != AbstractMeal.Type.SEPERATOR) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            MenuDetailFragment fragment = MenuDetailFragment.newInstance(item.getTitle(), item.getAllergeneList());
            fragment.show(fm, "fragment_edit_name");
        }



    }
}
