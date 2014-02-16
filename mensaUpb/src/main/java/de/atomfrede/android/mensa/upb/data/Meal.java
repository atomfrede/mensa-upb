package de.atomfrede.android.mensa.upb.data;

import java.io.Serializable;

public class Meal extends AbstractMeal implements Serializable {

    public Meal() {
        this.type = Type.MEAL;
    }

    @Override
    public Type getType() {
        return Type.MEAL;
    }
}
