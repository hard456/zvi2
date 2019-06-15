package cz.jpalcut.aos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MedianFilter {

    public int[][] processDirectFilter(int[][] array){
        int height = array.length;
        int width = array[0].length;

        int[][] newArray = new int[height][width];
        List<Integer> items = new ArrayList<>();

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                if(Utils.isItemOfArray(height, width, i, j-1)){
                    items.add(array[i][j-1]);
                }
                if(Utils.isItemOfArray(height, width, i, j+1)){
                    items.add(array[i][j+1]);
                }
                if(Utils.isItemOfArray(height, width, i-1, j)){
                    items.add(array[i-1][j]);
                }
                if(Utils.isItemOfArray(height, width, i+1, j)){
                    items.add(array[i+1][j]);
                }
                newArray[i][j] = getMedian(items);
                items.clear();
            }
        }
        return newArray;
    }

    public int[][] processAreaFilter(int[][] array, int area){
        int height = array.length;
        int width = array[0].length;

        int[][] newArray = new int[height][width];
        List<Integer> items = new ArrayList<>();

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){

                for (int k = i - area / 2; k <= i + area / 2; k++){
                    for (int l = j - area / 2; l <= j + area / 2; l++){
                        if (k != i && l != j && Utils.isItemOfArray(height, width, k, l)) {
                            items.add(array[k][l]);
                        }
                    }
                }

                newArray[i][j] = getMedian(items);
                items.clear();
            }
        }
        return newArray;
    }

    private int getMedian(List<Integer> items){
        Collections.sort(items);
        if (items.size() %2 == 0) {
            return  (items.get((items.size()/2) - 1) + items.get(items.size()/2))/2;
        } else {
            return items.get(items.size()/2);
        }
    }

}