package de.atomfrede.android.mensa.upb.data;


import java.io.Serializable;

public class SideDish extends AbstractMeal implements Serializable {

    public SideDish() {
        this.type = Type.SIDEDISH;
    }

    @Override
    public Type getType() {
        return Type.SIDEDISH;
    }
}
