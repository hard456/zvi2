package cz.jpalcut.aos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ModalFilter {

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
                newArray[i][j] = getMostCommonElement(items);
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
                        if(k == i && l == j){
                            continue;
                        }
                        if (Utils.isItemOfArray(height, width, k, l)) {
                            items.add(array[k][l]);
                        }
                    }
                }

                newArray[i][j] = getMostCommonElement(items);
                items.clear();
            }
        }
        return newArray;
    }

    private int getMostCommonElement(List<Integer> items){
        Collections.sort(items);

        int maxCount = 1, item = items.get(0), currCount = 1;
        for (int i = 1; i < items.size(); i++) {
            if (Objects.equals(items.get(i), items.get(i - 1)))
                currCount++;
            else {
                if (currCount > maxCount) {
                    maxCount = currCount;
                    item = items.get(i-1);
                }
                currCount = 1;
            }
        }

        if (currCount > maxCount)
        {
            item = items.get(items.size()-1);
        }

        return item;
    }

}
