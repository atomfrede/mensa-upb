package de.atomfrede.android.mensa.upb.loader;


import de.atomfrede.android.mensa.upb.data.AbstractMeal;
import de.atomfrede.android.mensa.upb.data.Desert;
import de.atomfrede.android.mensa.upb.data.Meal;
import de.atomfrede.android.mensa.upb.data.SideDish;
import de.atomfrede.android.mensa.upb.data.Soup;

public class MealFactory {

    public static Meal createMeal(Quadruple<String, String, String, String> data) {
        Meal m = new Meal();
        fillWithData(m, data);
        return m;
    }

    public static Soup createSoup(Quadruple<String, String, String, String> data) {
        Soup m = new Soup();
        fillWithData(m, data);
        return m;
    }

    public static SideDish createSideDish(Quadruple<String, String, String, String> data) {
        SideDish m = new SideDish();
        fillWithData(m, data);
        return m;
    }

    public static Desert createDesert(Quadruple<String, String, String, String> data) {
        Desert m = new Desert();
        fillWithData(m, data);
        return m;
    }

    private static AbstractMeal fillWithData(AbstractMeal m, Quadruple<String, String, String, String> data) {
        m.setTitle(data.first);

        if (!data.second.trim().equals("")) {
            m.setMarker(toMarker(data.second));
        } else {
            m.setMarker(AbstractMeal.Marker.NONE);
        }

        String rawPrices = data.third;
        String[] prices =  rawPrices.split(":");
        String amount = prices[1];
        String[] singlePrices = amount.split("/");

        m.setStudentPrice(singlePrices[0]);
        m.setPrice(singlePrices[1]);

        if (singlePrices.length > 2) {
            m.setGuestPrice(singlePrices[2]);
        } else {
            m.setGuestPrice("");
        }


        if(rawPrices.contains("Preis pro 100 g")) {
            m.setPricePerWeight(true);
        } else {
            m.setPricePerWeight(false);
        }

        m.setAllergeneList(data.fourth);

        return m;
    }

    private static AbstractMeal.Marker toMarker(String value) {

        switch (value) {
            case "lactosefrei":
                return AbstractMeal.Marker.LACTOSEFREI;
            case "vegetarisch":
                return AbstractMeal.Marker.VEGETARISCH;
            case "glutenfrei":
                return AbstractMeal.Marker.GLUTENFREI;
            case "fettarm":
                return AbstractMeal.Marker.FETTARM;
            case "vital food":
                return AbstractMeal.Marker.VITAL;
            case "kalorienarm":
                return AbstractMeal.Marker.KALORIENARM;
            case "vegan":
                return AbstractMeal.Marker.VEGAN;
            default:
                return AbstractMeal.Marker.NONE;
        }
    }
}
