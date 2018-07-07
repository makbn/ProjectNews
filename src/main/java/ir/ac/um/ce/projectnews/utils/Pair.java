package ir.ac.um.ce.projectnews.utils;

public class Pair {

    public String first;
    public Long second;

    public Pair(String first, Long second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return first + ": " + second;
    }
}
