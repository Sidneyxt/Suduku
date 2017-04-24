package com.xiaoying.test.apiserver.Suduku;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sidney on 2017/4/22.
 */
public class Number {
    private Integer realValue;
    private List<Integer> possibleValue;

    public Number(){
        possibleValue = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            possibleValue.add(i);
        }
    }
    public Number(Integer realValue) {
        this.realValue = realValue;
    }

    public Integer getRealValue() {
        return realValue;
    }

    public void setRealValue(Integer realValue) {
        this.realValue = realValue;
    }

    public List<Integer> getPossibleValue() {
        return possibleValue;
    }

    public void setPossibleValue(List<Integer> possibleValue) {
        this.possibleValue = possibleValue;
    }
}
