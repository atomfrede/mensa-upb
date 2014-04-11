package de.atomfrede.android.mensa.upb.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AbstractMeal implements Serializable {

    public enum Marker {
        @SerializedName("KALORIENARM")
        KALORIENARM,
        @SerializedName("FETTARM")
        FETTARM,
        @SerializedName("VEGETARISCH")
        VEGETARISCH,
        @SerializedName("VEGAN")
        VEGAN,
        @SerializedName("LACTOSEFREI")
        LACTOSEFREI,
        @SerializedName("GLUTENFREI")
        GLUTENFREI,
        @SerializedName("NONE")
        NONE
    }

    public enum Type {
        @SerializedName("SEPERATOR")
        SEPERATOR,
        @SerializedName("MEAL")
        MEAL,
        @SerializedName("SIDEDISH")
        SIDEDISH,
        @SerializedName("SOUP")
        SOUP,
        @SerializedName("DESERT")
        DESERT
    }

    protected String title;

    protected Type type;

    protected Marker marker;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
