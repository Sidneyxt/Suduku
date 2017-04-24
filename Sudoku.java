package com.xiaoying.test.apiserver.Suduku;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * Created by Sidney on 2017/4/22.
 */
public class Sudoku {
    public static final String SEPARATOR = "_";
    static Number[][] result = new Number[9][9];
    static Set<String> unclear = new HashSet<>();
    static Set<String> isClear = new HashSet<>();



    static Integer getPointValue(Integer x){
        return x - (x % 3);
    }

    //初始化
    static void init(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //还没有明确的点
                unclear.add(i + SEPARATOR + j);
                //每个点上的确定数字 或者是可能的数字
                result[i][j] = new Number();
            }
        }
    }

    static void count() throws Exception {
        for (int t = 0; t < 10; t++) {
            Iterator<String> iterator = unclear.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                Integer x = Integer.parseInt(next.split(SEPARATOR)[0]);
                Integer y = Integer.parseInt(next.split(SEPARATOR)[1]);
                if (isClear.contains(x + SEPARATOR + y)) {
                    continue;
                }
                List<Integer> possibleValue = new ArrayList<>();
                possibleValue.addAll(result[x][y].getPossibleValue());

                for (int i = 0; i < 9; i++) {
                    if (i == y) {
                        continue;
                    }
                    Number number = result[x][i];
                    if (number.getRealValue() != null) {
                        possibleValue.remove(number.getRealValue());
                    }
                    if (number.getPossibleValue() != null) {
                        possibleValue.removeAll(number.getPossibleValue());
                    }
                    if (CollectionUtils.isEmpty(possibleValue)) {
                        break;
                    }
                }
                if (possibleValue.size() == 1) {
                    addValue(x, y, possibleValue.get(0));
                    continue;
                }

                possibleValue = new ArrayList<>();
                possibleValue.addAll(result[x][y].getPossibleValue());
                for (int i = 0; i < 9; i++) {
                    if (i == x) {
                        continue;
                    }
                    List<Integer> possibleValue1 = result[i][y].getPossibleValue();
                    if (possibleValue1 != null) {
                        possibleValue.removeAll(possibleValue1);
                    }
                    if (CollectionUtils.isEmpty(possibleValue)) {
                        break;
                    }
                }
                if (possibleValue.size() == 1) {
                    addValue(x, y, possibleValue.get(0));
                    continue;
                }
                possibleValue = new ArrayList<>();
                possibleValue.addAll(result[x][y].getPossibleValue());

                a : for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        Integer pointXTemp = getPointValue(x) + i;
                        Integer pointYTemp = getPointValue(y) + j;
                        if (pointXTemp.equals(x) && pointYTemp.equals(j)) {
                            continue;
                        }
                        List<Integer> possibleValue1 = result[pointXTemp][pointYTemp].getPossibleValue();
                        if (possibleValue1 != null) {
                            possibleValue.removeAll(possibleValue1);
                        }
                        if (CollectionUtils.isEmpty(possibleValue)) {
                            break a;
                        }
                    }
                }
                if (possibleValue.size() == 1) {
                    addValue(x, y, possibleValue.get(0));
                }

            }
        }
    }

    static void addValue(Integer x, Integer y, Integer value) throws Exception {
        isClear.add(x + SEPARATOR + y);
        Integer pointX = getPointValue(x);
        Integer pointY = getPointValue(y);
        result[x][y] = new Number(value);
        //同x 的其他位置都不可能是该值
        for (int i = 0; i < 9; i++) {
            List<Integer> possibleValue = result[x][i].getPossibleValue();
            if (possibleValue != null) {
                possibleValue.remove(value);
                if (possibleValue.size() == 1) {
                    addValue(x, i, possibleValue.get(0));
                }
            }
        }
        //同y 的其他位置都不可能是该值
        for (int i = 0; i < 9; i++) {
            List<Integer> possibleValue = result[i][y].getPossibleValue();
            if (possibleValue != null) {
                possibleValue.remove(value);
                if (possibleValue.size() == 1) {
                    addValue(i, y, possibleValue.get(0));
                }
            }
        }

        //一个九宫格 的其他位置都不可能是该值
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Integer tempX = pointX + i;
                Integer tempY = pointY + j;
                List<Integer> possibleValue = result[tempX][tempY].getPossibleValue();
                if (possibleValue != null) {
                    possibleValue.remove(value);
                    if (possibleValue.size() == 1) {
                        addValue(tempX, tempY, possibleValue.get(0));
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        init();

        try {
            addValue(0, 4, 2);
            addValue(1, 0, 4);
            addValue(1, 1, 3);
            addValue(1, 3, 5);
            addValue(1, 7, 8);
            addValue(2, 2, 1);
            addValue(2, 4, 7);
            addValue(2, 6, 4);
            addValue(3, 4, 3);
            addValue(3, 7, 2);
            addValue(4, 0, 5);
            addValue(4, 2, 4);
            addValue(4, 3, 6);
            addValue(4, 5, 2);
            addValue(4, 6, 9);
            addValue(4, 8, 3);
            addValue(5, 1, 9);
            addValue(5, 4, 4);
            addValue(6, 2, 7);
            addValue(6, 4, 8);
            addValue(6, 6, 5);
            addValue(7, 1, 5);
            addValue(7, 5, 3);
            addValue(7, 7, 9);
            addValue(8, 4, 5);
            count();

            for (int i = 0; i < 9; i++) {
                System.out.println("");
                if (i % 3 == 0) {
                    System.out.println("");
                }
                for (int j = 0; j < 9; j++) {
                    if (j % 3 == 0) {
                        System.out.print("\t");
                    }
                    if (result[i][j].getRealValue() == null) {
                        result[i][j].setRealValue(0);
                    }
                    System.out.print(result[i][j].getRealValue() + "\t");
                }
            }
            System.out.println("");
            for (String s : unclear) {
                if (!isClear.contains(s)) {
                    String[] split = s.split(SEPARATOR);
                    Integer x = Integer.parseInt(split[0]);
                    Integer y = Integer.parseInt(split[1]);
                    System.out.println(JSON.toJSONString( x + SEPARATOR + y + " : " +result[x][y].getPossibleValue()));
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
