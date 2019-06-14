package cz.jpalcut.aos;

import java.util.ArrayList;
import java.util.List;

public class AverageFilter {

    public int[][] processDirectFilter(int[][] array) {
        int height = array.length;
        int width = array[0].length;

        int[][] medianArray = new int[height][width];
        List<Integer> items = new ArrayList<>();

        for (int i = 0; i < array[0].length; i++) {
            for (int j = 0; j < array.length; j++) {
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
                medianArray[i][j] = getAverage(items);
                items.clear();
            }
        }
        return medianArray;
    }

    public int[][] processIndirectFilter(int[][] array) {
        int height = array.length;
        int width = array[0].length;

        int[][] medianArray = new int[height][width];
        List<Integer> items = new ArrayList<>();

        for (int i = 0; i < array[0].length; i++) {
            for (int j = 0; j < array.length; j++) {

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

                if (Utils.isItemOfArray(height, width, i + 1, j + 1)) {
                    items.add(array[i + 1][j + 1]);
                }
                if (Utils.isItemOfArray(height, width, i - 1, j - 1)) {
                    items.add(array[i - 1][j - 1]);
                }
                if (Utils.isItemOfArray(height, width, i + 1, j - 1)) {
                    items.add(array[i + 1][j - 1]);
                }
                if (Utils.isItemOfArray(height, width, i - 1, j + 1)) {
                    items.add(array[i - 1][j + 1]);
                }

                medianArray[i][j] = getAverage(items);

                items.clear();
            }
        }
        return medianArray;
    }

    public int getAverage(List<Integer> items) {
        int sum = 0;
        for (int i = 0; i < items.size(); i++) {
            sum += items.get(i);
        }
        return sum / items.size();
    }

}
