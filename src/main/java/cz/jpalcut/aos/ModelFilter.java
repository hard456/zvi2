package cz.jpalcut.aos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ModelFilter {

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
                medianArray[i][j] = getMostCommonElement(items);
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
                medianArray[i][j] = getMostCommonElement(items);
                items.clear();
            }
        }
        return medianArray;
    }

    public int getMostCommonElement(List<Integer> items){
        Collections.sort(items);

        int max_count = 1, res = items.get(0), curr_count = 1;
        for (int i = 1; i < items.size(); i++) {
            if (Objects.equals(items.get(i), items.get(i - 1)))
                curr_count++;
            else {
                if (curr_count > max_count) {
                    max_count = curr_count;
                    res = items.get(i-1);
                }
                curr_count = 1;
            }
        }

        if (curr_count > max_count)
        {
            res = items.get(items.size()-1);
        }

        return res;
    }

}
