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
package de.atomfrede.android.mensa.data;

import java.util.ArrayList;
import java.util.List;

public class DailyMeal {

	String date;
	String weekday;
	List<Menu> menues;

	public void addMenu(Menu menu) {
		if (menues == null)
			menues = new ArrayList<Menu>();
		menues.add(menu);
	}

	public String getDate() {
		return date;
	}
	
	public String getShortDate(){
		String[] dates = date.split("\\.");
		return dates[0]+"."+dates[1];
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}

	public List<Menu> getMenues() {
		if (menues == null)
			menues = new ArrayList<Menu>();
		return menues;
	}

	public void setMenues(List<Menu> menues) {
		this.menues = menues;
	}

}
