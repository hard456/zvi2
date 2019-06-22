package cz.jpalcut.aos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Konzervativní filtr - pepř a sůl
 */
public class ConservativeFilter {

    /**
     * Filtrace 4-okolím
     *
     * @param array matice hodnot obrázku
     * @return vyfiltrovaná matice
     */
    public int[][] processDirectFilter(int[][] array) {
        int height = array.length;
        int width = array[0].length;
        int max, min;

        int[][] newArray = new int[height][width];
        List<Integer> items = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (Utils.isItemOfArray(height, width, i, j - 1)) {
                    items.add(array[i][j - 1]);
                }
                if (Utils.isItemOfArray(height, width, i, j + 1)) {
                    items.add(array[i][j + 1]);
                }
                if (Utils.isItemOfArray(height, width, i - 1, j)) {
                    items.add(array[i - 1][j]);
                }
                if (Utils.isItemOfArray(height, width, i + 1, j)) {
                    items.add(array[i + 1][j]);
                }
                max = Collections.max(items);
                min = Collections.min(items);

                if (newArray[i][j] > max) {
                    newArray[i][j] = max;
                } else if (newArray[i][j] < min) {
                    newArray[i][j] = min;
                } else {
                    newArray[i][j] = array[i][j];
                }
                items.clear();
            }
        }
        return newArray;
    }

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
        int max, min;

        int[][] newArray = new int[height][width];
        List<Integer> items = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                for (int k = i - area / 2; k <= i + area / 2; k++) {
                    for (int l = j - area / 2; l <= j + area / 2; l++) {
                        if (k != i && l != j && Utils.isItemOfArray(height, width, k, l)) {
                            items.add(array[k][l]);
                        }
                    }
                }
                max = Collections.max(items);
                min = Collections.min(items);

                if (newArray[i][j] > max) {
                    newArray[i][j] = max;
                } else if (newArray[i][j] < min) {
                    newArray[i][j] = min;
                } else {
                    newArray[i][j] = array[i][j];
                }
                items.clear();
            }
        }
        return newArray;
    }

}
