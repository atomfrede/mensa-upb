package de.atomfrede.android.mensa.upb.adapter;

import java.util.List;

import android.content.Context;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.atomfrede.android.mensa.R;
import de.atomfrede.android.mensa.upb.data.StandardMeal;

public class SnackListAdapter extends ArrayAdapter<StandardMeal> {

	List<StandardMeal> values;
	Context context;

	public SnackListAdapter(Context context, List<StandardMeal> values) {
		super(context, R.layout.one_way_row_layout, values);
		this.context = context;
		this.values = values;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.one_way_row_layout, parent, false);

		TextView titleTxtView = (TextView)rowView.findViewById(R.id.meal_title);
		TextView priceTxtView = (TextView)rowView.findViewById(R.id.meal_price);
		
		titleTxtView.setText(values.get(position).getText());
		priceTxtView.setText(values.get(position).getPrice());
		return rowView;
	}
}
