package de.atomfrede.android.mensa.upb.data;

/**
 * Created by fred on 16.02.14.
 */
public class Mealplans {

    private static final Mealplans instance = new Mealplans();

    public static Mealplans getInstance() {
        return instance;
    }

    private Mealplans(){}

    private WeeklyMeal mensa;
    private WeeklyMeal hotspot;
    private WeeklyMeal basilica;
    private WeeklyMeal pub;

    public WeeklyMeal getMensa() {
        return mensa;
    }

    public void setMensa(WeeklyMeal mensa) {
        this.mensa = mensa;
    }

    public WeeklyMeal getHotspot() {
        return hotspot;
    }

    public void setHotspot(WeeklyMeal hotspot) {
        this.hotspot = hotspot;
    }

    public WeeklyMeal getBasilica() {
        return basilica;
    }

    public void setBasilica(WeeklyMeal basilica) {
        this.basilica = basilica;
    }

    public WeeklyMeal getPub() {
        return pub;
    }

    public void setPub(WeeklyMeal pub) {
        this.pub = pub;
    }
}
