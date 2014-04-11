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
                return loadMensa();
            case Locations.PUB:
                return loadPub();
            case Locations.HOTSPOT:
                return loadHotSpot();
            case Locations.HAMM:
                return loadBasilica();
            case Locations.LIPPSTADT:
                return loadAtrium();
        }
        return null;
    }

    private static WeeklyMeal loadAtrium() throws IOException {
        Log.d(TAG, "Load Basilica.");
        WeeklyMeal atrium = new WeeklyMeal();

        Document doc;
        doc = Jsoup.connect(Urls.LIPPSTADT).timeout(0).get();

        Elements days = doc.select(".scrollContainer > .panel");

        for(Element day:days) {
            String dayText = day.id();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd_MM_yyyy");
            DateTime dt = formatter.parseDateTime(dayText);

            DailyMeal mealOfTheDay = new DailyMeal();
            mealOfTheDay.setDate(dt.toDate());

            Map<Integer, List<Pair<String, String>>> foods = extractFood(day);

            List<Pair<String, String>> meals = foods.get(0);
            List<Pair<String, String>> sideDishes = foods.get(1);
            List<Pair<String, String>> soups = foods.get(2);
            List<Pair<String, String>> deserts = foods.get(3);

            mealOfTheDay = addMeals(mealOfTheDay, meals);
            mealOfTheDay = addSideDishes(mealOfTheDay, sideDishes);
            mealOfTheDay = addSoups(mealOfTheDay, soups);
            mealOfTheDay = addDeserts(mealOfTheDay, deserts);

            atrium.addMeal(mealOfTheDay);

        }

        Mealplans.getInstance().setAtrium(atrium);
        return atrium;
    }

    private static WeeklyMeal loadBasilica() throws IOException {
        Log.d(TAG, "Load Basilica.");
        WeeklyMeal basilica = new WeeklyMeal();

        Document doc;
        doc = Jsoup.connect(Urls.HAMM).timeout(0).get();

        Elements days = doc.select(".scrollContainer > .panel");

        for(Element day:days) {
            String dayText = day.id();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd_MM_yyyy");
            DateTime dt = formatter.parseDateTime(dayText);

            DailyMeal mealOfTheDay = new DailyMeal();
            mealOfTheDay.setDate(dt.toDate());

            Map<Integer, List<Pair<String, String>>> foods = extractFood(day);

            List<Pair<String, String>> meals = foods.get(0);
            List<Pair<String, String>> sideDishes = foods.get(1);
            List<Pair<String, String>> soups = foods.get(2);
            List<Pair<String, String>> deserts = foods.get(3);

            mealOfTheDay = addMeals(mealOfTheDay, meals);
            mealOfTheDay = addSideDishes(mealOfTheDay, sideDishes);
            mealOfTheDay = addSoups(mealOfTheDay, soups);
            mealOfTheDay = addDeserts(mealOfTheDay, deserts);

            basilica.addMeal(mealOfTheDay);

        }

        Mealplans.getInstance().setBasilica(basilica);
        return basilica;
    }

    private static WeeklyMeal loadPub() throws IOException {
        Log.d(TAG, "Load Pub.");
        WeeklyMeal pub = new WeeklyMeal();

        Document doc;
        doc = Jsoup.connect(Urls.PUB).timeout(0).get();

        Elements days = doc.select(".scrollContainer > .panel");

        for(Element day:days) {
            String dayText = day.id();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd_MM_yyyy");
            DateTime dt = formatter.parseDateTime(dayText);

            DailyMeal mealOfTheDay = new DailyMeal();
            mealOfTheDay.setDate(dt.toDate());

            Map<Integer, List<Pair<String, String>>> foods = extractFood(day);

            List<Pair<String, String>> meals = foods.get(0);
            List<Pair<String, String>> sideDishes = foods.get(1);
            List<Pair<String, String>> soups = foods.get(2);
            List<Pair<String, String>> deserts = foods.get(3);

            mealOfTheDay = addMeals(mealOfTheDay, meals);
            mealOfTheDay = addSideDishes(mealOfTheDay, sideDishes);
            mealOfTheDay = addSoups(mealOfTheDay, soups);
            mealOfTheDay = addDeserts(mealOfTheDay, deserts);

            pub.addMeal(mealOfTheDay);

        }

        Mealplans.getInstance().setPub(pub);
        return pub;
    }

    private static WeeklyMeal loadHotSpot() throws IOException {
        Log.d(TAG, "Load Hotspot.");
        WeeklyMeal hotspot = new WeeklyMeal();

        Document doc;
        doc = Jsoup.connect(Urls.HOTSPOT).timeout(0).get();

        Elements days = doc.select(".scrollContainer > .panel");

        for(Element day:days) {
            String dayText = day.id();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd_MM_yyyy");
            DateTime dt = formatter.parseDateTime(dayText);

            DailyMeal mealOfTheDay = new DailyMeal();
            mealOfTheDay.setDate(dt.toDate());

            Map<Integer, List<Pair<String, String>>> foods = extractFood(day);

            List<Pair<String, String>> meals = foods.get(0);
            List<Pair<String, String>> sideDishes = foods.get(1);
            List<Pair<String, String>> soups = foods.get(2);
            List<Pair<String, String>> deserts = foods.get(3);

            mealOfTheDay = addMeals(mealOfTheDay, meals);
            mealOfTheDay = addSideDishes(mealOfTheDay, sideDishes);
            mealOfTheDay = addSoups(mealOfTheDay, soups);
            mealOfTheDay = addDeserts(mealOfTheDay, deserts);

            hotspot.addMeal(mealOfTheDay);

        }

        Mealplans.getInstance().setHotspot(hotspot);
        return hotspot;
    }

    private static WeeklyMeal loadMensa() throws IOException{
        WeeklyMeal mensaMeal = new WeeklyMeal();

        Document doc;
        doc = Jsoup.connect(Urls.MENSA).timeout(0).get();

        Elements days = doc.select(".scrollContainer > .panel");

        for(Element day:days) {
            String dayText = day.id();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd_MM_yyyy");
            DateTime dt = formatter.parseDateTime(dayText);

            DailyMeal mealOfTheDay = new DailyMeal();
            mealOfTheDay.setDate(dt.toDate());

            Map<Integer, List<Pair<String, String>>> foods = extractFood(day);

            List<Pair<String, String>> meals = foods.get(0);
            List<Pair<String, String>> sideDishes = foods.get(1);
            List<Pair<String, String>> soups = foods.get(2);
            List<Pair<String, String>> deserts = foods.get(3);

            mealOfTheDay = addMeals(mealOfTheDay, meals);
            mealOfTheDay = addSideDishes(mealOfTheDay, sideDishes);
            mealOfTheDay = addSoups(mealOfTheDay, soups);
            mealOfTheDay = addDeserts(mealOfTheDay, deserts);

            mensaMeal.addMeal(mealOfTheDay);

        }

        Mealplans.getInstance().setMensa(mensaMeal);
        return mensaMeal;
    }

    private static DailyMeal addMeals(DailyMeal mealOfTheDay, List<Pair<String, String>> meals) {
        for(Pair<String, String> meal:meals) {
            Meal m = new Meal();
            m.setTitle(meal.first);

            if(!meal.second.trim().equals("")) {
                m.setMarker(AbstractMeal.Marker.valueOf(meal.second.toUpperCase()));
            } else {
                m.setMarker(AbstractMeal.Marker.NONE);
            }

            mealOfTheDay.addMeal(m);
        }

        return mealOfTheDay;
    }

    private static DailyMeal addSideDishes(DailyMeal mealOfTheDay, List<Pair<String, String>> sideDishes) {
        for(Pair<String, String> sideDish:sideDishes) {
            SideDish m = new SideDish();
            m.setTitle(sideDish.first);

            if(!sideDish.second.trim().equals("")) {
                m.setMarker(AbstractMeal.Marker.valueOf(sideDish.second.toUpperCase()));
            } else {
                m.setMarker(AbstractMeal.Marker.NONE);
            }

            mealOfTheDay.addSideDisch(m);
        }

        return mealOfTheDay;
    }

    private static DailyMeal addSoups(DailyMeal mealOfTheDay, List<Pair<String, String>> soups) {
        for(Pair<String, String> soup:soups) {
            Soup m = new Soup();
            m.setTitle(soup.first);

            if(!soup.second.trim().equals("")) {
                m.setMarker(AbstractMeal.Marker.valueOf(soup.second.toUpperCase()));
            } else {
                m.setMarker(AbstractMeal.Marker.NONE);
            }

            mealOfTheDay.addSoup(m);
        }

        return mealOfTheDay;
    }

    private static DailyMeal addDeserts(DailyMeal mealOfTheDay, List<Pair<String, String>> deserts) {
        for(Pair<String, String> desert:deserts) {
            Desert m = new Desert();
            m.setTitle(desert.first);

            if(!desert.second.trim().equals("")) {
                m.setMarker(AbstractMeal.Marker.valueOf(desert.second.toUpperCase()));
            } else {
                m.setMarker(AbstractMeal.Marker.NONE);
            }

            mealOfTheDay.addDesert(m);
        }

        return mealOfTheDay;
    }

    private static Map<Integer, List<Pair<String, String>>> extractFood(Element day) {
        HashMap<Integer, List<Pair<String, String>>> meals = new HashMap<>();
        Elements foods = day.select(".food_txt");
        int counter = 0;

        for(Element food:foods) {
            List<Pair<String, String>> elems = extractFoodElement(food);
            meals.put(counter, elems);
            counter++;
        }

        return meals;
    }

    private static List<Pair<String, String>> extractFoodElement(Element food) {
        List<Pair<String, String>> foodElements = new ArrayList<>();
        Elements speiseplans = food.select(".speiseplan");

        //Log.e("Speiseplan ", speiseplans+"");

        for(Element plan:speiseplans) {
            String mealElement = plan.select("tbody tr th").first().text();
            String markerElement = "";


            Elements zutatImg = plan.select("tbody tr th.zutat img");
            if(zutatImg != null) {
                String title = zutatImg.attr("title");
                if(title.trim().equals("")) {
                    markerElement = "";
                } else {
                    markerElement = title;
                }
            }

            foodElements.add(new Pair<>(mealElement, markerElement));
        }

        return foodElements;
    }
}
