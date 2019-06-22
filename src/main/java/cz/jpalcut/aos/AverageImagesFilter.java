package cz.jpalcut.aos;

/**
 * Průměrování hodnot přes více snímků
 */
public class AverageImagesFilter {

    /**
     * Zpracování filtru
     *
     * @param array pole více snímků
     * @return vyfiltrovaná matice
     */
    public int[][] processFilter(int[][][] array) {
        int height = array[0].length;
        int width = array[0][0].length;
        int sum;

        int[][] newArray = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sum = 0;
                for (int[][] anArray : array) {
                    sum += anArray[i][j];
                }

                newArray[i][j] = sum / array.length;
            }
        }
        return newArray;
    }

}
