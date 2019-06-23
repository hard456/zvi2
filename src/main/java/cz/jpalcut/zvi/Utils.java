package cz.jpalcut.zvi;

import org.apache.commons.math3.complex.Complex;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.List;

/**
 * Třída obsahující užitečné metody pro aplikaci
 */
public class Utils {

    /**
     * Převede RGB na stupeň šedi
     *
     * @param value int
     * @return int
     */
    private static int convertRGBToGrayLevel(int value) {
        int r = (value >> 16) & 0xFF;
        int g = (value >> 8) & 0xFF;
        int b = (value & 0xFF);
        return (r + g + b) / 3;
    }

    /**
     * Vytvoří Complex[][] z BufferedImage
     *
     * @param image BufferedImage
     * @return Complex[][]
     */
    public static Complex[][] create2DComplexArray(BufferedImage image) {
        Complex[][] array = new Complex[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                array[i][j] = Complex.valueOf(convertRGBToGrayLevel(image.getRGB(j, i)), 0);
            }
        }
        return array;
    }

    /**
     * Vytvoří 3D matici z pole obrázků
     *
     * @param images pole obrázků
     * @return 3D matice
     */
    public static int[][][] create3DArray(BufferedImage[] images) {
        int[][][] array = new int[images.length][][];

        for (int i = 0; i < images.length; i++) {
            array[i] = create2DArray(images[i]);
        }

        return array;
    }

    /**
     * Vytvoří 2D pole z obrázku
     *
     * @param image obrázek
     * @return 2D pole
     */
    public static int[][] create2DArray(BufferedImage image) {
        int[][] array = new int[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                array[i][j] = convertRGBToGrayLevel(image.getRGB(j, i));
            }
        }
        return array;
    }

    /**
     * Vytvoří Complex[][] z double[][] s počtem řádku (height) a počtem sloupců (height)
     *
     * @param array  double[][]
     * @param width  int
     * @param height int
     * @return Complex[][]
     */
    public static Complex[][] arrayToComplexArray(double[][] array, int width, int height) {
        Complex[][] complex = new Complex[height][width];
        int sum = getMatrixSum(array);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                complex[i][j] = Complex.valueOf(array[i][j]).divide(sum);
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (complex[i][j] == null) {
                    complex[i][j] = new Complex(0.0, 0.0);
                }
                if (complex[i][j].isNaN()) {
                    complex[i][j] = new Complex(0.1, 0.0);
                }
            }
        }
        return complex;
    }

    /**
     * Spočítá součet pole
     *
     * @param array double[][]
     * @return int
     */
    public static int getMatrixSum(double[][] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                sum += array[i][j];
            }
        }
        return sum;
    }

    /**
     * Převede stupeň šedi na RGB
     *
     * @param value int
     * @return int
     */
    public static int convertGrayLevelToRGB(int value) {
        return ((value << 16) + (value << 8) + value);
    }

    /**
     * Ověří zda-li je hodnota mocnina 2
     *
     * @param value int
     * @return true - je mocnina 2, false - není mocnina 2
     */
    public static boolean isNumberPowerOfTwo(int value) {
        for (int i = 0; i < value; i++) {
            if (Math.pow(2, i) == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vyparsuje double[][] z String
     *
     * @param input String
     * @return double[][]
     */
    public static double[][] parseArray(String input) {
        String[] rows = input.split(";");
        String[] numbers;

        if (rows.length == 0) {
            return null;
        }

        double[][] filter = new double[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            numbers = rows[i].split(" ");
            filter[i] = new double[numbers.length];
            for (int j = 0; j < numbers.length; j++) {
                if (isDoubleNumber(numbers[j])) {
                    filter[i][j] = Double.parseDouble(numbers[j]);
                } else if (isIntegerNumber(numbers[j])) {
                    filter[i][j] = Integer.parseInt(numbers[j]);
                } else {
                    return null;
                }
            }
        }
        return filter;
    }

    /**
     * Ověří zda-li je vstup double
     *
     * @param input String
     * @return true - vstup je double, false - vstup není doble
     */
    public static boolean isDoubleNumber(String input) {
        try {
            Double.parseDouble(input);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Ověří zda-li je vstup Integer
     *
     * @param input String
     * @return true - vstup je Integer, false - vstup není int
     */
    public static boolean isIntegerNumber(String input) {
        try {
            Integer.parseInt(input);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Vyparsuje Double ze String
     *
     * @param input String
     * @return Double
     */
    public static Double parseDoubleNumber(String input) {
        Double number;
        if (isDoubleNumber(input)) {
            number = Double.parseDouble(input);
        } else if (isIntegerNumber(input)) {
            number = (double) Integer.parseInt(input);
        } else {
            return number = null;
        }
        return number;
    }

    /**
     * Vydělí hodnoty matice Complex[][] počtem prvků matice
     *
     * @param matrix Complex[][]
     * @return Complex[][]
     */
    public static Complex[][] divideMatrix(Complex[][] matrix) {
        int number = matrix.length * matrix[0].length;
        Complex[][] newMatrix = new Complex[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                newMatrix[i][j] = new Complex(matrix[i][j].getReal() / number, matrix[i][j].getImaginary() / number);
            }
        }
        return newMatrix;
    }

    /**
     * Ověří jestli je prvek uvnitř pole
     *
     * @param height výška
     * @param width  šířka
     * @param i      pozice y
     * @param j      pozice y
     * @return true - je uvnitř matice
     */
    public static boolean isItemOfArray(int height, int width, int i, int j) {
        return i >= 0 && i < height && j >= 0 && j < width;
    }

    /**
     * Převede matici na BufferedImage
     *
     * @param image BufferedImage
     * @param array matice
     * @return BufferedImage
     */
    public static BufferedImage arrayToBufferedImage(BufferedImage image, int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                image.setRGB(j, i, Utils.convertGrayLevelToRGB(array[i][j]));
            }
        }
        return image;
    }

    /**
     * Ověří stejnou velikost obrázků
     *
     * @param images pole obrázků
     * @return true - obrázky mají stejnou velikost
     */
    public static boolean haveImagesSameSize(BufferedImage[] images) {
        for (int i = 1; i < images.length; i++) {
            if (images[0].getWidth() != images[i].getWidth() || images[0].getHeight() != images[i].getHeight()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vytvoří clone BufferedImage
     *
     * @param bi BufferedImage
     * @return clone BufferedImage
     */
    public static BufferedImage cloneBufferedImage(BufferedImage bi) {
        if (bi == null) {
            return null;
        }
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * Vrátí průměr seznamu prvků
     *
     * @param items seznam prvků
     * @return průměr
     */
    public static double getAverage(List<Integer> items) {
        double sum = 0;
        for (Integer item : items) {
            sum += item;
        }
        return sum / items.size();
    }

    /**
     * Doplnění matice do velikosti 2^n
     *
     * @param complexes matice
     * @return doplněná matice
     */
    public static Complex[][] fillingMatrixToPowTwo(Complex[][] complexes) {
        int height = complexes.length;
        int width = complexes[0].length;

        if (!isNumberPowerOfTwo(height)) {
            height = getNextPowOfTwoNumber(height);
        }

        if (!isNumberPowerOfTwo(width)) {
            width = getNextPowOfTwoNumber(width);
        }

        Complex[][] newArray = new Complex[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (j >= complexes[0].length || i >= complexes.length) {
                    newArray[i][j] = new Complex(0.0, 0.0);
                } else {
                    newArray[i][j] = complexes[i][j];
                }
            }
        }
        return newArray;
    }

    /**
     * Vrátí první možnou hodnotu 2^n větší než value
     *
     * @param value hodnota
     * @return hodnta 2^n
     */
    public static int getNextPowOfTwoNumber(int value) {
        int start = 1;
        int number;
        while (true) {
            number = (int) Math.pow(2, start);
            if (number > value) {
                return number;
            }
            start++;
        }
    }

    /**
     * Přepíše hodnot second BufferedImage do first BufferedImage
     *
     * @param first  BufferedImage
     * @param second BufferedImage
     * @return BufferedImage
     */
    public static BufferedImage restrictBufferedImage(BufferedImage first, BufferedImage second) {

        for (int i = 0; i < first.getHeight(); i++) {
            for (int j = 0; j < first.getWidth(); j++) {
                first.setRGB(j, i, second.getRGB(j, i));
            }
        }
        return first;
    }

    /**
     * Vloží kruh do matice
     *
     * @param matrix matice
     * @param inside vnitřní nebo vnější kruh
     * @param radius poloměr kruhu
     * @return matice
     */
    public static Complex[][] insertCircle(Complex[][] matrix, boolean inside, int radius) {
        int height = matrix.length;
        int width = matrix[0].length;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (Math.pow(j - width / 2, 2.0) + Math.pow(i - height / 2, 2.0) < Math.pow(radius, 2.0) && inside) {
                    matrix[i][j] = new Complex(0.0, 0.0);
                } else if (Math.pow(j - width / 2, 2.0) + Math.pow(i - height / 2, 2.0) > Math.pow(radius, 2.0) && !inside) {
                    matrix[i][j] = new Complex(0.0, 0.0);
                }
            }
        }
        return matrix;
    }

    /**
     * Vycentrování FFT na úrovni matice
     *
     * @param matrix matice
     * @return Complex[][]
     */
    public static Complex[][] centerFFTMatrix(Complex[][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;
        Complex[][] newMatrix = new Complex[height][width];

        for (int i = 0; i < height / 2; i++) {
            for (int j = 0; j < width / 2; j++) {
                newMatrix[i][j] = matrix[height / 2 + i][width / 2 + j];
                newMatrix[height / 2 + i][width / 2 + j] = matrix[i][j];
                newMatrix[i][width / 2 + j] = matrix[height / 2 + i][j];
                newMatrix[height / 2 + i][j] = matrix[i][width / 2 + j];
            }
        }

        return newMatrix;
    }

    /**
     * Parsování celého čísla
     *
     * @param number číslo
     * @return Integer
     */
    public static Integer parseInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            return null;
        }
    }

}
