package de.atomfrede.android.mensa.upb.loader;

import android.util.Log;
import android.util.Pair;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.atomfrede.android.mensa.upb.contants.Locations;
import de.atomfrede.android.mensa.upb.contants.Urls;
import de.atomfrede.android.mensa.upb.data.AbstractMeal;
import de.atomfrede.android.mensa.upb.data.DailyMeal;
import de.atomfrede.android.mensa.upb.data.Desert;
import de.atomfrede.android.mensa.upb.data.Meal;
import de.atomfrede.android.mensa.upb.data.Mealplans;
import de.atomfrede.android.mensa.upb.data.SideDish;
import de.atomfrede.android.mensa.upb.data.Soup;
import de.atomfrede.android.mensa.upb.data.WeeklyMeal;

public class Loader {

    public final static String TAG = "Loader";

    public static WeeklyMeal reloadWeeklyMeal(int location) throws IOException {
        switch (location){
            case Locations.MENSA:
                WeeklyMeal m = loadByUrl(Urls.MENSA);
                Mealplans.getInstance().setMensa(m);
                return m;
            case Locations.PUB:
                WeeklyMeal pub = loadByUrl(Urls.PUB);
                Mealplans.getInstance().setPub(pub);
                return pub;
            case Locations.HOTSPOT:
                WeeklyMeal hotspot = loadByUrl(Urls.HOTSPOT);
                Mealplans.getInstance().setHotspot(hotspot);
                return hotspot;
            case Locations.HAMM:
                WeeklyMeal hamm = loadByUrl(Urls.HAMM);
                Mealplans.getInstance().setBasilica(hamm);
                return hamm;
            case Locations.LIPPSTADT:
                WeeklyMeal lipp = loadByUrl(Urls.LIPPSTADT);
                Mealplans.getInstance().setAtrium(lipp);
                return lipp;
        }
        return null;
    }

    private static WeeklyMeal loadByUrl(final String url) throws IOException {
        WeeklyMeal weeklyMeal = new WeeklyMeal();
        Document doc;
        doc = Jsoup.connect(url).timeout(0).get();

        Elements days = doc.select(".scrollContainer > .panel");

        for(Element day:days) {
            String dayText = day.id();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd_MM_yyyy");
            DateTime dt = formatter.parseDateTime(dayText);

            DailyMeal mealOfTheDay = new DailyMeal();
            mealOfTheDay.setDate(dt.toDate());

            Map<Integer, List<Quadruple<String, String, String, String>>> foods = extractFood(day);

            List<Quadruple<String, String, String, String>> meals = foods.get(0);
            List<Quadruple<String, String, String, String>> sideDishes = foods.get(1);
            List<Quadruple<String, String, String, String>> soups = foods.get(2);
            List<Quadruple<String, String, String, String>> deserts = foods.get(3);

            mealOfTheDay = addMeals(mealOfTheDay, meals);
            mealOfTheDay = addSideDishes(mealOfTheDay, sideDishes);
            mealOfTheDay = addSoups(mealOfTheDay, soups);
            mealOfTheDay = addDeserts(mealOfTheDay, deserts);

            weeklyMeal.addMeal(mealOfTheDay);

        }

        return weeklyMeal;
    }

    private static DailyMeal addMeals(DailyMeal mealOfTheDay, List<Quadruple<String, String, String, String>> meals) {
        if(meals != null) {
            for (Quadruple<String, String, String, String> meal : meals) {
                mealOfTheDay.addMeal(MealFactory.createMeal(meal));
            }
        }

        return mealOfTheDay;
    }

    private static DailyMeal addSideDishes(DailyMeal mealOfTheDay, List<Quadruple<String, String, String, String>> sideDishes) {
        if(sideDishes != null) {
            for (Quadruple<String, String, String, String> sideDish : sideDishes) {
                mealOfTheDay.addSideDisch(MealFactory.createSideDish(sideDish));
            }
        }
        return mealOfTheDay;
    }

    private static DailyMeal addSoups(DailyMeal mealOfTheDay, List<Quadruple<String, String, String, String>> soups) {
        if(soups != null) {
            for (Quadruple<String, String, String, String> soup : soups) {
                mealOfTheDay.addSoup(MealFactory.createSoup(soup));
            }
        }
        return mealOfTheDay;
    }

    private static DailyMeal addDeserts(DailyMeal mealOfTheDay, List<Quadruple<String, String, String, String>> deserts) {
        if(deserts != null) {
            for (Quadruple<String, String, String, String> desert : deserts) {
                mealOfTheDay.addDesert(MealFactory.createDesert(desert));
            }
        }
        return mealOfTheDay;
    }

    private static Map<Integer, List<Quadruple<String, String, String, String>>> extractFood(Element day) {
        HashMap<Integer, List<Quadruple<String, String, String, String>>> meals = new HashMap<>();
        Elements foods = day.select(".food_txt");
        int counter = 0;

        for(Element food:foods) {
            List<Quadruple<String, String, String, String>> elems = extractFoodElement(food);
            meals.put(counter, elems);
            counter++;
        }

        return meals;
    }

    private static List<Quadruple<String, String, String, String>> extractFoodElement(Element food) {
        List<Quadruple<String, String, String, String>> foodElements = new ArrayList<>();
        Elements speiseplans = food.select(".speiseplan");

        for(Element plan:speiseplans) {
            String mealElement = plan.select("tbody tr th").first().text();
            String markerElement = "";
            String priceElement = "";
            String addOnsElement = "";

            Elements zutatImg = plan.select("tbody tr th.zutat img");
            if(zutatImg != null) {
                String title = zutatImg.attr("title");
                if(title.trim().equals("")) {
                    markerElement = "";
                } else {
                    markerElement = title;
                }
            }

            priceElement = plan.select("tbody tr td").get(0).text();
            if(!plan.select("tbody tr td a").isEmpty()) {
                addOnsElement = plan.select("tbody tr td a").get(0).text();
            } else {
                addOnsElement = "";
            }

            foodElements.add(new Quadruple<>(mealElement, markerElement, priceElement, addOnsElement));
        }

        return foodElements;
    }
}
