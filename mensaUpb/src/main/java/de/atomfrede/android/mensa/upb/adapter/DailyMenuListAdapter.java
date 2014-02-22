package de.atomfrede.android.mensa.upb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hb.views.PinnedSectionListView;

import java.util.List;

import de.atomfrede.android.mensa.upb.R;
import de.atomfrede.android.mensa.upb.data.AbstractMeal;

public class DailyMenuListAdapter extends ArrayAdapter<AbstractMeal> implements PinnedSectionListView.PinnedSectionListAdapter {

    List<AbstractMeal> values;
    Context context;

    public DailyMenuListAdapter(Context context, List<AbstractMeal> values) {
        super(context, R.layout.mensa_row_layout, values);
        this.context = context;
        this.values = values;
    }


    @Override
    public int getViewTypeCount() {
        return AbstractMeal.Type.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType().ordinal();
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == AbstractMeal.Type.SEPERATOR.ordinal();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        AbstractMeal item = getItem(position);

        View rowView = inflater.inflate(R.layout.mensa_row_layout, parent, false);
        TextView titleTxt = (TextView) rowView.findViewById(R.id.menu_title);

        if (item.getType() == AbstractMeal.Type.SEPERATOR) {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.pinned_section_header));
        }
        titleTxt.setText(values.get(position).getTitle());
        return rowView;
    }
}
