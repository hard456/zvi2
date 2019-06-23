package cz.jpalcut.zvi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Filtrace rotováním masky
 */
public class RotationMaskFilter {

    /**
     * Filtrování přes vybranou oblast
     *
     * @param array matice hodnot obrázku
     * @param area  velikost oblasti
     * @return vyfiltrovaná matice
     */
    public int[][] processAreaFilter(int[][] array, int area) {
        int height = array.length;
        int width = array[0].length;
        double avg;
        List<Integer> average = new ArrayList<>();
        List<Double> dispersion = new ArrayList<>();
        List<Integer> items;

        int[][] newArray = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                for (int k = i - area / 2; k <= i + area / 2; k++) {
                    for (int l = j - area / 2; l <= j + area / 2; l++) {
                        items = getAreaItems(array, area, k, l);
                        avg = Utils.getAverage(items);
                        average.add((int) avg);
                        dispersion.add(getDispersion(avg, items));
                        items.clear();
                    }
                }

                newArray[i][j] = average.get(getMinPosition(dispersion));
                average.clear();
                dispersion.clear();
            }
        }
        return newArray;
    }

    /**
     * Vrátí seznam prvků oblasti podle pozice x a y
     *
     * @param array matice hodnot obrázku
     * @param area  velikost oblasti
     * @param k     y pozice
     * @param l     x pozice
     * @return prvky oblasti
     */
    public List<Integer> getAreaItems(int[][] array, int area, int k, int l) {
        List<Integer> items = new ArrayList<>();
        for (int i = k - area / 2; i <= k + area / 2; i++) {
            for (int j = l - area / 2; j <= l + area / 2; j++) {
                if (Utils.isItemOfArray(array.length, array[0].length, i, j)) {
                    items.add(array[i][j]);
                }
            }
        }
        return items;
    }

    /**
     * Vrátí hodnotu rozptylu
     *
     * @param avg   průměr
     * @param items seznam prvků
     * @return rozptyl
     */
    public double getDispersion(double avg, List<Integer> items) {
        double sum = 0;
        for (Integer item : items) {
            sum += Math.pow(item - avg, 2.0);
        }
        return sum / items.size();
    }

    /**
     * Vrátí pozici s nejmenší hodnotou rozptylu
     *
     * @param items seznam prvků
     * @return nejmenší rozptyl
     */
    public int getMinPosition(List<Double> items) {
        double value = Collections.min(items);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == value) {
                return i;
            }
        }
        return 0;
    }

}
