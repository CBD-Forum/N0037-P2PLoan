package cn.edu.fudan.bclab.hackathon.util;

/**
 * Created by lomo on 2017/5/11.
 */
public class Pair<E extends Object, F extends Object> {

    private E first;
    private F second;

    public Pair(E first, F second) {
        this.first = first;
        this.second = second;
    }

    public E getFirst() {
        return first;
    }

    public void setFirst(E first) {
        this.first = first;
    }

    public F getSecond() {
        return second;
    }

    public void setSecond(F second) {
        this.second = second;
    }
}
