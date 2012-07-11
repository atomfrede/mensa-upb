package de.atomfrede.android.mensa.upb.data;

import java.util.List;

import de.atomfrede.android.mensa.upb.common.StandardMeal;
import de.atomfrede.android.mensa.upb.wok.WokMeal;

public class MealPlan {

	private static MealPlan instance = new MealPlan();

	public static MealPlan getInstance() {
		if (instance == null)
			instance = new MealPlan();
		return instance;
	}

	WeeklyMeal mensaMeal, hotspotMeal, pubMeal;
	Quicklunch mensulaQuicklunch;
	List<StandardMeal> oneWaySnacks;
	List<WokMeal> wokMeals;

	private MealPlan() {
	}

	public WeeklyMeal getMensaMeal() {
		return mensaMeal;
	}

	public void setMensaMeal(WeeklyMeal mensaMeal) {
		this.mensaMeal = mensaMeal;
	}

	public WeeklyMeal getHotspotMeal() {
		return hotspotMeal;
	}

	public void setHotspotMeal(WeeklyMeal hotspotMeal) {
		this.hotspotMeal = hotspotMeal;
	}

	public WeeklyMeal getPubMeal() {
		return pubMeal;
	}

	public void setPubMeal(WeeklyMeal pubMeal) {
		this.pubMeal = pubMeal;
	}

	public Quicklunch getMensulaQuicklunch() {
		return mensulaQuicklunch;
	}

	public void setMensulaQuicklunch(Quicklunch mensulaQuicklunch) {
		this.mensulaQuicklunch = mensulaQuicklunch;
	}

	public List<StandardMeal> getOneWaySnacks() {
		return oneWaySnacks;
	}

	public void setOneWaySnacks(List<StandardMeal> oneWaySnacks) {
		this.oneWaySnacks = oneWaySnacks;
	}

	public List<WokMeal> getWokMeals() {
		return wokMeals;
	}

	public void setWokMeals(List<WokMeal> wokMeals) {
		this.wokMeals = wokMeals;
	}

}
