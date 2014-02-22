package de.atomfrede.android.mensa.upb.data;


import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeeklyMeal implements Serializable {

    private List<DailyMeal> meals = new ArrayList<>();

    public WeeklyMeal addMeal(DailyMeal meal) {
        meals.add(meal);
        return this;
    }
    public List<DailyMeal> getMeals() {
        return meals;
    }

    public void setMeals(List<DailyMeal> meals) {
        this.meals = meals;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static WeeklyMeal fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, WeeklyMeal.class);
    }
}
