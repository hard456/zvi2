package cz.jpalcut.aos;

import java.util.ArrayList;
import java.util.List;

/**
 * Filtrace průměrováním bodu pomocí vybrané oblasti
 */
public class AverageFilter {

    /**
     * Průměrování 4-okolím
     *
     * @param array matice hodnot obrázku
     * @return vyfiltrovaná matice
     */
    public int[][] processDirectFilter(int[][] array) {
        int height = array.length;
        int width = array[0].length;

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
                items.add(array[i][j]);
                newArray[i][j] = getAverage(items);
                items.clear();
            }
        }
        return newArray;
    }

    /**
     * Průměrování přes vybranou oblast
     *
     * @param array matice hodnot obrázku
     * @param area  velikost oblasti
     * @return vyfiltrovaná matice
     */
    public int[][] processAreaFilter(int[][] array, int area) {
        int height = array.length;
        int width = array[0].length;

        int[][] newArray = new int[height][width];
        List<Integer> items = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                for (int k = i - area / 2; k <= i + area / 2; k++) {
                    for (int l = j - area / 2; l <= j + area / 2; l++) {
                        if (Utils.isItemOfArray(height, width, k, l)) {
                            items.add(array[k][l]);
                        }
                    }
                }

                newArray[i][j] = getAverage(items);

                items.clear();
            }
        }
        return newArray;
    }

    /**
     * Vrátí průměrnou hodnotu seznamu prvků
     *
     * @param items seznam prvků
     * @return průměr
     */
    private int getAverage(List<Integer> items) {
        int sum = 0;
        for (int i = 0; i < items.size(); i++) {
            sum += items.get(i);
        }
        return sum / items.size();
    }

}
