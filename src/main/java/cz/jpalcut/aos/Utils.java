package cz.jpalcut.aos;

import org.apache.commons.math3.complex.Complex;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

/**
 * Třída obsahující užitečné metody pro aplikaci
 */
public class Utils {

    /**
     * Převede RGB na stupeň šedi
     * @param value int
     * @return int
     */
    private static int convertRGBToGrayLevel(int value){
        int r = (value >> 16) & 0xFF;
        int g = (value >> 8) & 0xFF;
        int b = (value & 0xFF);
        return  (r + g + b) / 3;
    }

    /**
     * Vytvoří Complex[][] z BufferedImage
     * @param image BufferedImage
     * @return Complex[][]
     */
    public static Complex[][] create2DComplexArray(BufferedImage image){
        Complex[][] array = new Complex[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++){
            for (int j = 0; j < image.getWidth(); j++){
                array[i][j] = Complex.valueOf(convertRGBToGrayLevel(image.getRGB(j, i)),0);
            }
        }
        return array;
    }


    public static int[][][] create3DArray(BufferedImage[] images){
        int[][][] array = new int[images.length][][];

        for (int i = 0; i < images.length; i++){
            array[i] = create2DArray(images[i]);
        }

        return array;
    }

    public static int[][] create2DArray(BufferedImage image){
        int[][] array = new int[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++){
            for (int j = 0; j < image.getWidth(); j++){
                array[i][j] = convertRGBToGrayLevel(image.getRGB(j, i));
            }
        }
        return array;
    }
    /**
     * Vytvoří Complex[][] z double[][] s počtem řádku (height) a počtem sloupců (height)
     * @param array double[][]
     * @param width int
     * @param height int
     * @return Complex[][]
     */
    public static Complex[][] arrayToComplexArray(double[][] array, int width, int height){
        Complex[][] complex = new Complex[height][width];
        int sum = getMatrixSum(array);
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[i].length; j++){
                complex[i][j] = Complex.valueOf(array[i][j]).divide(sum);
            }
        }
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                if(complex[i][j] == null){
                    complex[i][j] = new Complex(0.0,0.0);
                }
                if(complex[i][j].isNaN()){
                    complex[i][j] = new Complex(0.1,0.0);
                }
            }
        }
        return complex;
    }

    /**
     * Spočítá součet pole
     * @param array double[][]
     * @return int
     */
    public static int getMatrixSum(double[][] array){
        int sum = 0;
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[i].length; j++){
                sum += array[i][j];
            }
        }
        return sum;
    }

    /**
     * Převede stupeň šedi na RGB
     * @param value int
     * @return int
     */
    public static int convertGrayLevelToRGB(int value){
        return ((value << 16) + (value << 8) + value);
    }

    /**
     * Ověří zda-li je hodnota mocnina 2
     * @param value int
     * @return true - je mocnina 2, false - není mocnina 2
     */
    public static boolean isNumberPowerOfTwo(int value){
        for (int i = 0; i < value; i++){
            if(Math.pow(2,i) == value){
                return true;
            }
        }
        return false;
    }

    /**
     * Vyparsuje double[][] z String
     * @param input String
     * @return double[][]
     */
    public static double[][] parseArray(String input){
        String[] rows = input.split(";");
        String[] numbers;

        if(rows.length == 0){
            return null;
        }

        double[][] filter = new double[rows.length][];

        for (int i = 0; i < rows.length; i++){
            numbers = rows[i].split(" ");
            filter[i] = new double[numbers.length];
            for (int j = 0; j < numbers.length; j++){
                if(isDoubleNumber(numbers[j])){
                    filter[i][j] = Double.parseDouble(numbers[j]);
                }
                else if(isIntegerNumber(numbers[j])){
                    filter[i][j] = Integer.parseInt(numbers[j]);
                }
                else{
                    return null;
                }
            }
        }
        return filter;
    }

    /**
     * Ověří zda-li je vstup double
     * @param input String
     * @return true - vstup je double, false - vstup není doble
     */
    public static boolean isDoubleNumber(String input){
        try {
            Double.parseDouble(input);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * Ověří zda-li je vstup Integer
     * @param input String
     * @return true - vstup je Integer, false - vstup není int
     */
    public static boolean isIntegerNumber(String input){
        try {
            Integer.parseInt(input);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * Vyparsuje Double ze String
     * @param input String
     * @return Double
     */
    public static Double parseDoubleNumber(String input){
        Double number;
        if(isDoubleNumber(input)){
            number = Double.parseDouble(input);
        }
        else if(isIntegerNumber(input)){
            number = (double)Integer.parseInt(input);
        }
        else{
            return number = null;
        }
        return number;
    }

    /**
     * Vydělí hodnoty matice Complex[][] počtem prvků matice
     * @param matrix Complex[][]
     * @return Complex[][]
     */
    public static Complex[][] divideMatrix(Complex[][] matrix){
        int number = matrix.length * matrix[0].length;
        Complex[][] newMatrix = new Complex[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                newMatrix[i][j] = new Complex(matrix[i][j].getReal()/number,matrix[i][j].getImaginary()/number);
            }
        }
        return newMatrix;
    }

    public static boolean isItemOfArray(int height, int width, int i, int j) {
        return i >= 0 && i < height && j >= 0 && j < width;
    }

    public static BufferedImage arrayToBufferedImage(BufferedImage image, int[][] array){
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[0].length; j++){
//                if(array[i][j] > 256 || array[i][j] < 0){
//                    System.out.println(array[i][j]);
//                }
                image.setRGB(j, i, Utils.convertGrayLevelToRGB(array[i][j]));
            }
        }
        return image;
    }

    public static boolean haveImagesSameSize(BufferedImage[] images){
        for (int i = 1; i < images.length; i++){
            if(images[0].getWidth() != images[i].getWidth() || images[0].getHeight() != images[i].getHeight()){
                return false;
            }
        }
        return true;
    }

    public static BufferedImage cloneBufferedImage(BufferedImage bi){
            if(bi == null){
                return null;
            }
            ColorModel cm = bi.getColorModel();
            boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
            WritableRaster raster = bi.copyData(null);
            return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static double getAverage(List<Integer> items){
        double sum = 0;
        for (Integer item : items) {
            sum += item;
        }
        return sum/items.size();
    }

}
