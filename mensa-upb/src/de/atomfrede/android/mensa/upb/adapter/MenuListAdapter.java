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
package de.atomfrede.android.mensa.upb.adapter;

import java.util.List;

import android.content.Context;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.atomfrede.android.mensa.R;
import de.atomfrede.android.mensa.upb.data.Menu;

public class MenuListAdapter extends ArrayAdapter<Menu> {

	List<Menu> values;
	Context context;

	public MenuListAdapter(Context context, List<Menu> values) {
		super(context, R.layout.mensa_row_layout, values);
		this.context = context;
		this.values = values;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.mensa_row_layout, parent, false);
		TextView titleTxt = (TextView) rowView.findViewById(R.id.title_txt);
		TextView mealTxt = (TextView) rowView.findViewById(R.id.meal_txt);
		TextView counterTxt = (TextView) rowView.findViewById(R.id.counter_txt);
		TextView priceTxt = (TextView) rowView.findViewById(R.id.price_txt);
		Menu currentMenu = values.get(position);
		titleTxt.setText(currentMenu.getName());
		mealTxt.setText(getMenuText(currentMenu));

		String counterTypeText = getCounterTypeText(currentMenu);
		if (counterTypeText == null || counterTypeText.equals(""))
			counterTxt.setVisibility(View.GONE);
		counterTxt.setText(counterTypeText);

		String priceText = getPriceTxt(currentMenu);
		if (priceText == null || priceText.equals(""))
			priceTxt.setVisibility(View.GONE);
		priceTxt.setText(priceText);

		return rowView;
	}

	private String getPriceTxt(Menu currentMenu) {
		StringBuilder sb = new StringBuilder("");

		if (currentMenu.getPrice() != null && !currentMenu.getPrice().equals(""))
			sb.append(currentMenu.getPrice());

		return sb.toString();

	}

	private String getCounterTypeText(Menu currentMenu) {
		StringBuilder sb = new StringBuilder("");

		if (currentMenu.getAtCounter() != null && !currentMenu.getAtCounter().equals(""))
			sb.append(currentMenu.getAtCounter()).append("");

		if (currentMenu.getType() != null && !currentMenu.getType().equals(""))
			sb.append(currentMenu.getType());

		return sb.toString();
	}

	private String getMenuText(Menu currentMenu) {
		StringBuilder sb = new StringBuilder("");
		if (currentMenu.getText() != null)
			sb.append(currentMenu.getText()).append("\n");

		if (!currentMenu.getSideDishes().isEmpty()) {
			int counter = 0;
			for (String sideDish : currentMenu.getSideDishes()) {
				counter++;
				if (sideDish != null && !sideDish.equals(""))
					sb.append(sideDish);
				if (counter < currentMenu.getSideDishes().size())
					sb.append("\n");
			}
		}

		return sb.toString();
	}

}
