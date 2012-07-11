/*
 *  Copyright 2012 Frederik Hahne
 *  
 *  This file is part of Mensa UPB.
 *
 *  Mensa UPB is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Mensa UPB is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Mensa UPB.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.atomfrede.android.mensa.upb.snack;

import java.util.List;

import android.content.Context;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.atomfrede.android.mensa.R;
import de.atomfrede.android.mensa.upb.common.StandardMeal;

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
