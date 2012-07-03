package de.atomfrede.android.mensa.upb.data;

public class MealPlan {

	private static final MealPlan instance = new MealPlan();
	
	public static MealPlan getInstance() {
        return instance;
    }
	
	WeeklyMeal mensaMeal, hotspotMeal, pubMeal;

	private MealPlan(){}
	
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

}
