package de.atomfrede.android.mensa.upb.data;

import java.util.List;

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
	WokMeal wokMeal;

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

	public WokMeal getWokMeal() {
		return wokMeal;
	}

	public void setWokMeal(WokMeal wokMeal) {
		this.wokMeal = wokMeal;
	}

	public List<StandardMeal> getOneWaySnacks() {
		return oneWaySnacks;
	}

	public void setOneWaySnacks(List<StandardMeal> oneWaySnacks) {
		this.oneWaySnacks = oneWaySnacks;
	}

}
