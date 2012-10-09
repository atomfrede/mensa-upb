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
package de.atomfrede.android.mensa.upb.data.meals;

import java.util.ArrayList;
import java.util.List;

public class WeeklyMeal {

	String weekOfYear;
	String startDay;
	String endDay;
	List<DailyMeal> meals;

	public void addMeal(DailyMeal dailyMeal) {
		if (meals == null)
			meals = new ArrayList<DailyMeal>();
		meals.add(dailyMeal);
	}

	public String getWeekOfYear() {
		return weekOfYear;
	}

	public void setWeekOfYear(String weekOfYear) {
		this.weekOfYear = weekOfYear;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public List<DailyMeal> getMeals() {
		if (meals == null)
			meals = new ArrayList<DailyMeal>();
		return meals;
	}

	public void setMeals(List<DailyMeal> meals) {
		this.meals = meals;
	}
}
