package de.atomfrede.android.mensa.upb.data;

import java.io.Serializable;

public class Desert extends AbstractMeal implements Serializable  {

    public Desert() {
        this.type = Type.DESERT;
    }

    @Override
    public Type getType() {
        return Type.DESERT;
    }
}
