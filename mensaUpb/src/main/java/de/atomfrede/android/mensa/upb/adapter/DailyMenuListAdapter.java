package de.atomfrede.android.mensa.upb.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
        TextView markerTxt = (TextView) rowView.findViewById(R.id.menu_marker);
        ImageView markerImage = (ImageView) rowView.findViewById(R.id.marker_image);

        if (item.getType() == AbstractMeal.Type.SEPERATOR) {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.pinned_section_header));
        }
        titleTxt.setText(values.get(position).getTitle());



        AbstractMeal.Marker marker = values.get(position).getMarker();

        if(marker != null) {
            if(values.get(position).getMarker() != AbstractMeal.Marker.NONE) {
                markerTxt.setText(values.get(position).getMarker().toString().toLowerCase());
            } else {
                markerTxt.setVisibility(View.GONE);
            }

            switch (values.get(position).getMarker()) {
                case NONE:
                    markerImage.setVisibility(View.GONE);
                    break;
                case FETTARM:
                    markerImage.setImageResource(R.drawable.ic_fettarm);
                    break;
                case GLUTENFREI:
                    markerImage.setImageResource(R.drawable.ic_glutenfrei);
                    break;
                case KALORIENARM:
                    markerImage.setImageResource(R.drawable.ic_kalorienarm);
                    break;
                case LACTOSEFREI:
                    markerImage.setImageResource(R.drawable.ic_lactosefrei);
                    break;
                case VEGAN:
                    markerImage.setImageResource(R.drawable.ic_vegan);
                    break;
                case VEGETARISCH:
                    markerImage.setImageResource(R.drawable.ic_vegetarisch);
                    break;
            }
        } else {
            markerImage.setVisibility(View.GONE);
            markerTxt.setVisibility(View.GONE);
        }

        return rowView;
    }
}
