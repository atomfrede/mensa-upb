package de.atomfrede.android.mensa.upb.data;

import java.io.Serializable;

public class Soup extends AbstractMeal implements Serializable {

    public Soup() {
        this.type = Type.SOUP;
    }

    @Override
    public Type getType() {
        return Type.SOUP;
    }
}
