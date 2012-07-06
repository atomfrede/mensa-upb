package de.atomfrede.android.mensa.data;

public class MealPlan {

	private static final MealPlan instance = new MealPlan();
	
	public static MealPlan getInstance() {
        return instance;
    }
	
	WeeklyMeal mensaMeal, hotspotMeal, pubMeal;
	Quicklunch mensulaQuicklunch;
	StandardMeal wokMeal, oneWaySnackMeal;

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

	public Quicklunch getMensulaQuicklunch() {
		return mensulaQuicklunch;
	}

	public void setMensulaQuicklunch(Quicklunch mensulaQuicklunch) {
		this.mensulaQuicklunch = mensulaQuicklunch;
	}

	public StandardMeal getWokMeal() {
		return wokMeal;
	}

	public void setWokMeal(StandardMeal wokMeal) {
		this.wokMeal = wokMeal;
	}

	public StandardMeal getOneWaySnackMeal() {
		return oneWaySnackMeal;
	}

	public void setOneWaySnackMeal(StandardMeal oneWaySnackMeal) {
		this.oneWaySnackMeal = oneWaySnackMeal;
	}

}
