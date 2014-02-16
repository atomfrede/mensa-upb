package de.atomfrede.android.mensa.upb.data;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyMeal implements Serializable {

    private List<Meal> meals = new ArrayList<>();
    private List<SideDish> sideDishes = new ArrayList<>();
    private List<Soup> soups = new ArrayList<>();
    private List<Desert>deserts = new ArrayList<>();

    private Date date;

    public DailyMeal addMeal(Meal meal) {
        meals.add(meal);
        return this;
    }

    public DailyMeal addSideDisch(SideDish sideDish) {
        sideDishes.add(sideDish);
        return this;
    }

    public DailyMeal addSoup(Soup soup) {
        soups.add(soup);
        return this;
    }

    public DailyMeal addDesert(Desert desert) {
        deserts.add(desert);
        return this;
    }
    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public List<SideDish> getSideDishes() {
        return sideDishes;
    }

    public void setSideDishes(List<SideDish> sideDishes) {
        this.sideDishes = sideDishes;
    }

    public List<Desert> getDeserts() {
        return deserts;
    }

    public void setDeserts(List<Desert> deserts) {
        this.deserts = deserts;
    }

    public Date getDate() {
        return date;
    }

    public List<Soup> getSoups() {
        return soups;
    }

    public void setSoups(List<Soup> soups) {
        this.soups = soups;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
