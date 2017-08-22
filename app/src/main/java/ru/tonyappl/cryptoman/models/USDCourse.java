package ru.tonyappl.cryptoman.models;

import java.util.List;

public class USDCourse {

    private List<Value> value;

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }
}
