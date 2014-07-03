package de.atomfrede.android.mensa.upb.loader;

public class Quadruple<F, S, T, Fo> {
    public final F first;
    public final S second;
    public final T third;
    public final Fo fourth;

    public Quadruple(F first, S second, T third, Fo fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public int hashCode() {
        int result = 17;
        result = 31 * result + first.hashCode();
        result = 31 * result + second.hashCode();
        result = 31 * result + third.hashCode();
        result = 31 * result + fourth.hashCode();
        return result;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Quadruple)) return false;
        final Quadruple<F, S, T, Fo> other;
        try {
            other = (Quadruple<F, S, T, Fo>) o;
        } catch (ClassCastException e) {
            return false;
        }
        return first.equals(other.first) && second.equals(other.second) && third.equals(other.third) && fourth.equals(other.fourth);
    }
}
