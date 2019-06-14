package cz.jpalcut.aos;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MedianFilter {

    public int[][] processDirectFilter(int[][] array){
        int height = array.length;
        int width = array[0].length;

        int[][] medianArray = new int[height][width];
        List<Integer> items = new ArrayList<>();

        for (int i = 0; i < array[0].length; i++){
            for (int j = 0; j < array.length; j++){
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
                medianArray[i][j] = getMedian(items);
                items.clear();
            }
        }
        return medianArray;
    }

    public int[][] processIndirectFilter(int[][] array){
        int height = array.length;
        int width = array[0].length;

        int[][] medianArray = new int[height][width];
        List<Integer> items = new ArrayList<>();

        for (int i = 0; i < array[0].length; i++){
            for (int j = 0; j < array.length; j++){

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

                if(Utils.isItemOfArray(height, width, i+1, j+1)){
                    items.add(array[i+1][j+1]);
                }
                if(Utils.isItemOfArray(height, width, i-1, j-1)){
                    items.add(array[i-1][j-1]);
                }
                if(Utils.isItemOfArray(height, width, i+1, j-1)){
                    items.add(array[i+1][j-1]);
                }
                if(Utils.isItemOfArray(height, width, i-1, j+1)){
                    items.add(array[i-1][j+1]);
                }
                medianArray[i][j] = getMedian(items);
                items.clear();
            }
        }
        return medianArray;
    }

    public int getMedian(List<Integer> items){
        Collections.sort(items);
        if (items.size() %2 == 0) {
            return  (items.get((items.size()/2) - 1) + items.get(items.size()/2))/2;
        } else {
            return items.get(items.size()/2);
        }
    }

}
