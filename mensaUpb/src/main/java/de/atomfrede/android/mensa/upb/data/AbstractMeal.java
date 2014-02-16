package de.atomfrede.android.mensa.upb.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AbstractMeal implements Serializable {

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

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
