package cz.jpalcut.zvi;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.apache.commons.math3.complex.Complex;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Controller pro správu gui.fxml
 */
public class Controller {

    @FXML
    public ChoiceBox choiceBox, averageAreaSelect, modalAreaSelect, medianAreaSelect, minAreaSelect, maxAreaSelect,
            conservativeSelect, rotationMaskSelect;

    @FXML
    public AnchorPane averagePain, modalPain, medianPain, averageImagePain, maxPain, minPain, fftPain, ifftPain,
            inverseFilterPain, conservativePain, rotationMaskPain;

    @FXML
    public Button averageImageButton, ifftButton, fftButton, averageButton, modalButton, medianButton,
            averageChooseImageButton, maxFilterButton, minFilterButton, inverseFilterButton,
            conservativeButton, rotationMaskButton;
    public AnchorPane highLowPassPain;
    public ChoiceBox highLowPassSelect;
    public Button highLowPassButton;
    public TextField radiusTextField;

    @FXML
    TextField deconvolutionMask, thresholdField;

    @FXML
    MenuItem openMI, saveAsMI, backButton;

    @FXML
    ImageView imageView;

    @FXML
    Label statusText;

    private int width, height;

    private BufferedImage tmp, bufferedImage;

    private Complex[][] matrixFFT, matrixIFFT;

    private BufferedImage[] images;

    @FXML
    protected void initialize() {
        choiceBox.setItems(FXCollections.observableArrayList("Průměrování v okolí bodu", "Modální filtrace", "Mediánová filtrace",
                "Průměr sledů snímků", "Filtrace maximem", "Filtrace minimem", "FFT obrázku", "IFFT obrázku", "Inverzní filter",
                "Konzervativní filtr - pepř a sůl", "Filtrace rotováním masky", "Horní a dolní propust"));
        choiceBox.getSelectionModel().select(0);

        choiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> drawForm(newValue.intValue())
        );
        averagePain.setVisible(true);
    }

    /**
     * Vykreslení GUI podle select boxu
     *
     * @param method hodnota select boxu
     */
    private void drawForm(int method) {

        if (matrixFFT == null) {
            ifftButton.setDisable(true);
        } else {
            ifftButton.setDisable(false);
        }

        switch (method) {
            case 0:
                averagePain.setVisible(true);
                modalPain.setVisible(false);
                medianPain.setVisible(false);
                averageImagePain.setVisible(false);
                maxPain.setVisible(false);
                minPain.setVisible(false);
                fftPain.setVisible(false);
                ifftPain.setVisible(false);
                inverseFilterPain.setVisible(false);
                conservativePain.setVisible(false);
                rotationMaskPain.setVisible(false);
                highLowPassPain.setVisible(false);
                break;
            case 1:
                modalPain.setVisible(true);
                averagePain.setVisible(false);
                medianPain.setVisible(false);
                averageImagePain.setVisible(false);
                maxPain.setVisible(false);
                minPain.setVisible(false);
                fftPain.setVisible(false);
                ifftPain.setVisible(false);
                inverseFilterPain.setVisible(false);
                conservativePain.setVisible(false);
                rotationMaskPain.setVisible(false);
                highLowPassPain.setVisible(false);
                break;
            case 2:
                medianPain.setVisible(true);
                averagePain.setVisible(false);
                modalPain.setVisible(false);
                averageImagePain.setVisible(false);
                maxPain.setVisible(false);
                minPain.setVisible(false);
                fftPain.setVisible(false);
                ifftPain.setVisible(false);
                inverseFilterPain.setVisible(false);
                conservativePain.setVisible(false);
                rotationMaskPain.setVisible(false);
                highLowPassPain.setVisible(false);
                break;
            case 3:
                averageImagePain.setVisible(true);
                averagePain.setVisible(false);
                modalPain.setVisible(false);
                medianPain.setVisible(false);
                maxPain.setVisible(false);
                minPain.setVisible(false);
                fftPain.setVisible(false);
                ifftPain.setVisible(false);
                inverseFilterPain.setVisible(false);
                conservativePain.setVisible(false);
                rotationMaskPain.setVisible(false);
                highLowPassPain.setVisible(false);
                break;
            case 4:
                maxPain.setVisible(true);
                averagePain.setVisible(false);
                modalPain.setVisible(false);
                medianPain.setVisible(false);
                averageImagePain.setVisible(false);
                minPain.setVisible(false);
                fftPain.setVisible(false);
                ifftPain.setVisible(false);
                inverseFilterPain.setVisible(false);
                conservativePain.setVisible(false);
                rotationMaskPain.setVisible(false);
                highLowPassPain.setVisible(false);
                break;
            case 5:
                minPain.setVisible(true);
                averagePain.setVisible(false);
                modalPain.setVisible(false);
                medianPain.setVisible(false);
                averageImagePain.setVisible(false);
                maxPain.setVisible(false);
                fftPain.setVisible(false);
                ifftPain.setVisible(false);
                inverseFilterPain.setVisible(false);
                conservativePain.setVisible(false);
                rotationMaskPain.setVisible(false);
                highLowPassPain.setVisible(false);
                break;
            case 6:
                fftPain.setVisible(true);
                averagePain.setVisible(false);
                modalPain.setVisible(false);
                medianPain.setVisible(false);
                averageImagePain.setVisible(false);
                maxPain.setVisible(false);
                minPain.setVisible(false);
                ifftPain.setVisible(false);
                inverseFilterPain.setVisible(false);
                conservativePain.setVisible(false);
                rotationMaskPain.setVisible(false);
                highLowPassPain.setVisible(false);
                break;
            case 7:
                ifftPain.setVisible(true);
                fftPain.setVisible(false);
                averagePain.setVisible(false);
                modalPain.setVisible(false);
                medianPain.setVisible(false);
                averageImagePain.setVisible(false);
                maxPain.setVisible(false);
                minPain.setVisible(false);
                inverseFilterPain.setVisible(false);
                conservativePain.setVisible(false);
                rotationMaskPain.setVisible(false);
                highLowPassPain.setVisible(false);
                break;
            case 8:
                inverseFilterPain.setVisible(true);
                ifftPain.setVisible(false);
                fftPain.setVisible(false);
                averagePain.setVisible(false);
                modalPain.setVisible(false);
                medianPain.setVisible(false);
                averageImagePain.setVisible(false);
                maxPain.setVisible(false);
                minPain.setVisible(false);
                conservativePain.setVisible(false);
                rotationMaskPain.setVisible(false);
                highLowPassPain.setVisible(false);
                break;
            case 9:
                conservativePain.setVisible(true);
                inverseFilterPain.setVisible(false);
                ifftPain.setVisible(false);
                fftPain.setVisible(false);
                averagePain.setVisible(false);
                modalPain.setVisible(false);
                medianPain.setVisible(false);
                averageImagePain.setVisible(false);
                maxPain.setVisible(false);
                minPain.setVisible(false);
                rotationMaskPain.setVisible(false);
                highLowPassPain.setVisible(false);
                break;
            case 10:
                rotationMaskPain.setVisible(true);
                conservativePain.setVisible(false);
                inverseFilterPain.setVisible(false);
                ifftPain.setVisible(false);
                fftPain.setVisible(false);
                averagePain.setVisible(false);
                modalPain.setVisible(false);
                medianPain.setVisible(false);
                averageImagePain.setVisible(false);
                maxPain.setVisible(false);
                minPain.setVisible(false);
                highLowPassPain.setVisible(false);
                break;
            case 11:
                highLowPassPain.setVisible(true);
                rotationMaskPain.setVisible(false);
                conservativePain.setVisible(false);
                inverseFilterPain.setVisible(false);
                ifftPain.setVisible(false);
                fftPain.setVisible(false);
                averagePain.setVisible(false);
                modalPain.setVisible(false);
                medianPain.setVisible(false);
                averageImagePain.setVisible(false);
                maxPain.setVisible(false);
                minPain.setVisible(false);
                break;
            default:
                averagePain.setVisible(true);
                modalPain.setVisible(false);
                medianPain.setVisible(false);
                averageImagePain.setVisible(false);
                maxPain.setVisible(false);
                minPain.setVisible(false);
                fftPain.setVisible(false);
                ifftPain.setVisible(false);
                inverseFilterPain.setVisible(false);
                conservativePain.setVisible(false);
                rotationMaskPain.setVisible(false);
                highLowPassPain.setVisible(false);
                break;
        }
    }

    /**
     * Načtení obrázku
     */
    public void openImage() {
        File file;
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image", "*.jpg",
                "*.jpeg", "*.bmp", "*.png");

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                bufferedImage = ImageIO.read(file);
            } catch (IOException e) {
                setStatus("Nastala chyba při načtení obrázku.", "RED");
            }
            matrixFFT = null;
            matrixIFFT = null;
            fftButton.setDisable(false);
            ifftButton.setDisable(true);
            Utils.arrayToBufferedImage(bufferedImage, Utils.create2DArray(bufferedImage));
            showImage();
            setStatus("Obrázek byl načten.", "GREEN");
            backButton.setDisable(false);
            this.height = bufferedImage.getHeight();
            this.width = bufferedImage.getWidth();
        }

    }

    /**
     * Načtení více obrázků
     */
    public void openImages() {
        List<File> fileList = null;
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image", "*.jpg",
                "*.jpeg", "*.bmp", "*.png");

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extFilter);

        //List of files
        fileList = fileChooser.showOpenMultipleDialog(null);

        if (fileList == null) {
            return;
        }
        images = new BufferedImage[fileList.size()];

        for (int i = 0; i < fileList.size(); i++) {
            try {
                images[i] = ImageIO.read(fileList.get(i));
                setStatus("Načtení obrázků proběhlo.", "GREEN");
            } catch (IOException e) {
                setStatus("Nastala chyba při načtení obrázků.", "RED");
            }
        }
        averageImageButton.setDisable(false);
    }

    /**
     * Uložení obrázku
     */
    public void saveAs() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg",
                "*.jpeg", "*.bmp");
        fileChooser.getExtensionFilters().addAll(extFilter);

        fileChooser.setTitle("Uložit obrázek");
        fileChooser.setInitialFileName(String.valueOf(new Random().nextInt() * 1000));
        File saveFile = fileChooser.showSaveDialog(Main.getStage());
        if (saveFile != null) {
            try {
                BufferedImage saveImage = bufferedImage;
                ImageIO.write(saveImage, "png", saveFile);
                setStatus("Obrázek byl uložen.", "GREEN");
            } catch (IOException ex) {
                setStatus("Obrázek se nepodařilo uložit.", "RED");
            }
        }
    }

    /**
     * Aplikace inverzní FFT na matici
     */
    public void useInverseFFT() {

        if (bufferedImage == null) {
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

        ifftButton.setDisable(true);
        openMI.setDisable(true);
        saveAsMI.setDisable(true);
        backButton.setDisable(true);
        choiceBox.setDisable(true);

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                Complex[][] matrix;
                FFT fft = new FFT(true);
                matrix = matrixFFT.clone();
                matrix = fft.compute(matrix);
                matrixIFFT = matrix.clone();
                bufferedImage = fft.createIFFTImage(bufferedImage, matrix);

                if (matrix.length != height || matrix[0].length != width) {
                    bufferedImage = Utils.restrictBufferedImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB), bufferedImage);
                }

                showImage();
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            setStatus("Byla provedena IFFT.", "GREEN");
            fftButton.setDisable(false);
            openMI.setDisable(false);
            saveAsMI.setDisable(false);
            choiceBox.setDisable(false);
        });
        setStatus("Provádí se IFFT.", "BLUE");
        new Thread(task).start();
    }

    /**
     * Aplikace FFT na obrázek
     */
    public void useFFT() {

        if (bufferedImage == null) {
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

        fftButton.setDisable(true);
        openMI.setDisable(true);
        saveAsMI.setDisable(true);
        backButton.setDisable(true);
        choiceBox.setDisable(true);

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                Complex[][] matrix;
                FFT fft = new FFT(false);

                if (matrixIFFT != null) {
                    matrix = matrixIFFT.clone();
                    matrix = Utils.divideMatrix(matrix);
                } else {

                    matrix = Utils.create2DComplexArray(bufferedImage);
                    if (!Utils.isNumberPowerOfTwo(bufferedImage.getWidth()) || !Utils.isNumberPowerOfTwo(bufferedImage.getHeight())) {
                        matrix = Utils.fillingMatrixToPowTwo(matrix);
                    }


                }

                matrix = fft.compute(matrix);
                bufferedImage = new BufferedImage(matrix[0].length, matrix.length, BufferedImage.TYPE_INT_RGB);

                matrixFFT = matrix.clone();

                fft.createFFTImage(bufferedImage, matrix);
                bufferedImage = fft.centerFFTImage(bufferedImage);
                showImage();
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            setStatus("Byla provedena FFT.", "GREEN");
            ifftButton.setDisable(false);
            openMI.setDisable(false);
            saveAsMI.setDisable(false);
            choiceBox.setDisable(false);
        });
        setStatus("Provádí se FFT.", "BLUE");
        new Thread(task).start();
    }

    /**
     * Inverzní filtr
     */
    public void useInverseFilter() {

        if (bufferedImage == null) {
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

        disableDefaultUI();
        inverseFilterButton.setDisable(true);
        tmp = Utils.cloneBufferedImage(bufferedImage);
        clearFFTMatrix();

        double[][] filter = Utils.parseArray(deconvolutionMask.getText());

        if (filter == null) {
            setStatus("Filtr nebyl definován správně.", "RED");
            return;
        }
        Double threshold = Utils.parseDoubleNumber(thresholdField.getText());

        if (threshold == null) {
            setStatus("Práh nebyl definován správně.", "RED");
            return;
        }

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                Complex[][] mask, image;
                FFT fft = new FFT(false);

                image = Utils.create2DComplexArray(bufferedImage);

                if (!Utils.isNumberPowerOfTwo(bufferedImage.getWidth()) || !Utils.isNumberPowerOfTwo(bufferedImage.getHeight())) {
                    image = Utils.fillingMatrixToPowTwo(image);
                }

                mask = Utils.arrayToComplexArray(filter, image[0].length, image.length);

                image = fft.compute(image);

                mask = fft.compute(mask);
                mask = fft.deconvolution(image, mask, threshold);

                FFT ifft = new FFT(true);
                mask = ifft.compute(mask);
                bufferedImage = new BufferedImage(mask[0].length, mask.length, BufferedImage.TYPE_INT_RGB);
                ifft.createIFFTImage(bufferedImage, mask);

                if (mask.length != height || mask[0].length != width) {
                    bufferedImage = Utils.restrictBufferedImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB), bufferedImage);
                }

                showImage();
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            setStatus("Byl aplikován inverzní filter.", "GREEN");
            enableDefaultUI();
            inverseFilterButton.setDisable(false);
        });
        setStatus("Provádí se inverzní filter.", "BLUE");
        new Thread(task).start();
    }

    /**
     * Zobrazení obrázku
     */
    private void showImage() {
        if (bufferedImage != null) {
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(600);
            imageView.setFitHeight(600);
            imageView.setImage(image);
        }
    }

    /**
     * Nastavení statusu
     *
     * @param text  String
     * @param color String
     */
    private void setStatus(String text, String color) {
        statusText.setText(text);
        statusText.setTextFill(Color.web(color));
    }

    /**
     * Vrátí hodnotu podle indexu
     *
     * @param index index
     * @return int
     */
    private int getAreaSizeFromSelect(int index) {
        switch (index) {
            case 0:
                return 0;
            case 1:
                return 3;
            case 2:
                return 5;
            case 3:
                return 7;
            case 4:
                return 9;
            default:
                return 0;
        }
    }

    /**
     * Vrátí hodnotu podle indexu
     *
     * @param index index
     * @return int
     */
    private int getAreaSizeFromSelectWithoutDirect(int index) {
        switch (index) {
            case 0:
                return 3;
            case 1:
                return 5;
            case 2:
                return 7;
            case 3:
                return 9;
            default:
                return 0;
        }
    }

    /**
     * Filtrace průměrováním
     */
    public void useAverageFilter() {

        if (bufferedImage == null) {
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

        averageButton.setDisable(true);
        disableDefaultUI();
        clearFFTMatrix();
        tmp = Utils.cloneBufferedImage(bufferedImage);

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                int area = getAreaSizeFromSelect(averageAreaSelect.getSelectionModel().selectedIndexProperty().getValue());
                int[][] matrix = Utils.create2DArray(bufferedImage);
                AverageFilter averageFilter = new AverageFilter();

                if (area == 0) {
                    matrix = averageFilter.processDirectFilter(matrix);
                } else {
                    matrix = averageFilter.processAreaFilter(matrix, area);
                }

                bufferedImage = Utils.arrayToBufferedImage(bufferedImage, matrix);
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            backButton.setDisable(false);
            showImage();
            setStatus("Bylo provedeno průměrování v okolí bodu.", "GREEN");
            enableDefaultUI();
            averageButton.setDisable(false);
        });
        setStatus("Provádí se průměrování v okolí bodu.", "BLUE");
        new Thread(task).start();
    }

    /**
     * Modální filtrace
     */
    public void useModalFilter() {

        if (bufferedImage == null) {
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

        modalButton.setDisable(true);
        disableDefaultUI();
        clearFFTMatrix();
        tmp = Utils.cloneBufferedImage(bufferedImage);

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                int area = getAreaSizeFromSelect(modalAreaSelect.getSelectionModel().selectedIndexProperty().getValue());
                int[][] matrix = Utils.create2DArray(bufferedImage);
                ModalFilter modalFilter = new ModalFilter();

                if (area == 0) {
                    matrix = modalFilter.processDirectFilter(matrix);
                } else {
                    matrix = modalFilter.processAreaFilter(matrix, area);
                }

                bufferedImage = Utils.arrayToBufferedImage(bufferedImage, matrix);
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            showImage();
            setStatus("Byl proveden modální filter.", "GREEN");
            modalButton.setDisable(false);
            enableDefaultUI();
        });
        setStatus("Provádí se modální filter.", "BLUE");
        new Thread(task).start();
    }

    /**
     * Mediánový filtr
     */
    public void useMedianFilter() {

        if (bufferedImage == null) {
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

        medianButton.setDisable(true);
        disableDefaultUI();
        clearFFTMatrix();
        tmp = Utils.cloneBufferedImage(bufferedImage);

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                int area = getAreaSizeFromSelect(medianAreaSelect.getSelectionModel().selectedIndexProperty().getValue());
                int[][] matrix = Utils.create2DArray(bufferedImage);
                MedianFilter medianFilter = new MedianFilter();

                if (area == 0) {
                    matrix = medianFilter.processDirectFilter(matrix);
                } else {
                    matrix = medianFilter.processAreaFilter(matrix, area);
                }

                bufferedImage = Utils.arrayToBufferedImage(bufferedImage, matrix);
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            showImage();
            setStatus("Byla provedena mediánová filtrace.", "GREEN");
            enableDefaultUI();
            medianButton.setDisable(false);
        });
        setStatus("Provádí se mediánová filtrace.", "BLUE");
        new Thread(task).start();
    }

    /**
     * Průměrování sledu snímků
     */
    public void useAverageImagesFilter() {
        clearFFTMatrix();
        tmp = Utils.cloneBufferedImage(bufferedImage);

        if (images == null || images.length < 2) {
            setStatus("Počet obrázků musí být více než jedna.", "RED");
        } else if (!Utils.haveImagesSameSize(images)) {
            setStatus("Obrázky mají rozdílnou velikost.", "RED");
        } else {

            averageImageButton.setDisable(true);
            averageChooseImageButton.setDisable(true);
            disableDefaultUI();

            Task task = new Task<Void>() {
                @Override
                public Void call() {
                    AverageImagesFilter filter = new AverageImagesFilter();
                    bufferedImage = images[0];
                    Utils.arrayToBufferedImage(bufferedImage, filter.processFilter(Utils.create3DArray(images)));
                    return null;
                }
            };
            task.setOnSucceeded(event -> {
                showImage();
                setStatus("Byl proveden průmer sledu snímků.", "GREEN");
                enableDefaultUI();
                averageChooseImageButton.setDisable(false);
                height = bufferedImage.getHeight();
                width = bufferedImage.getWidth();
            });
            setStatus("Provádí se průměr sledu snímků.", "BLUE");
            new Thread(task).start();
        }

    }

    /**
     * Akce zpět
     */
    public void actionBack() {
        bufferedImage = tmp;
        this.height = tmp.getHeight();
        this.width = tmp.getWidth();
        backButton.setDisable(true);
        setStatus("Byla provedena akce zpět.", "GREEN");
        showImage();
    }

    /**
     * Filtrace maximem
     */
    public void useMaxFilter() {

        if (bufferedImage == null) {
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

        disableDefaultUI();
        maxFilterButton.setDisable(true);
        clearFFTMatrix();
        tmp = Utils.cloneBufferedImage(bufferedImage);

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                int area = getAreaSizeFromSelect(maxAreaSelect.getSelectionModel().selectedIndexProperty().getValue());
                int[][] matrix = Utils.create2DArray(bufferedImage);
                MaxMinFilter maxMinFilter = new MaxMinFilter(true);

                if (area == 0) {
                    matrix = maxMinFilter.processDirectFilter(matrix);
                } else {
                    matrix = maxMinFilter.processAreaFilter(matrix, area);
                }

                bufferedImage = Utils.arrayToBufferedImage(bufferedImage, matrix);
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            showImage();
            setStatus("Byla provedena filtrace maximem.", "GREEN");
            enableDefaultUI();
            maxFilterButton.setDisable(false);
        });
        setStatus("Provádí se filtrace maximem.", "BLUE");
        new Thread(task).start();
    }

    /**
     * Filtrace minimem
     */
    public void useMinFilter() {

        if (bufferedImage == null) {
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

        minFilterButton.setDisable(true);
        disableDefaultUI();

        clearFFTMatrix();
        tmp = Utils.cloneBufferedImage(bufferedImage);

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                int area = getAreaSizeFromSelect(minAreaSelect.getSelectionModel().selectedIndexProperty().getValue());
                int[][] matrix = Utils.create2DArray(bufferedImage);
                MaxMinFilter maxMinFilter = new MaxMinFilter(false);

                if (area == 0) {
                    matrix = maxMinFilter.processDirectFilter(matrix);
                } else {
                    matrix = maxMinFilter.processAreaFilter(matrix, area);
                }

                bufferedImage = Utils.arrayToBufferedImage(bufferedImage, matrix);
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            showImage();
            setStatus("Byla provedena filtrace minimem.", "GREEN");
            enableDefaultUI();
            minFilterButton.setDisable(false);
        });
        setStatus("Provádí se filtrace minimem.", "BLUE");
        new Thread(task).start();
    }

    /**
     * Vyčištění hodnot FFT
     */
    private void clearFFTMatrix() {
        fftButton.setDisable(false);
        matrixFFT = null;
        matrixIFFT = null;
    }

    /**
     * Konzervativní filtr
     */
    public void useConservativeFilter() {

        if (bufferedImage == null) {
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

        disableDefaultUI();
        conservativeButton.setDisable(true);
        clearFFTMatrix();
        tmp = Utils.cloneBufferedImage(bufferedImage);

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                int area = getAreaSizeFromSelect(conservativeSelect.getSelectionModel().selectedIndexProperty().getValue());
                int[][] matrix = Utils.create2DArray(bufferedImage);
                ConservativeFilter conservativeFilter = new ConservativeFilter();

                if (area == 0) {
                    matrix = conservativeFilter.processDirectFilter(matrix);
                } else {
                    matrix = conservativeFilter.processAreaFilter(matrix, area);
                }

                bufferedImage = Utils.arrayToBufferedImage(bufferedImage, matrix);
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            showImage();
            setStatus("Byla provedena konzervativní filtrace - pepř a sůl.", "GREEN");
            enableDefaultUI();
            conservativeButton.setDisable(false);
        });
        setStatus("Provádí se konzervativní filtrace - pepř a sůl.", "BLUE");
        new Thread(task).start();
    }

    /**
     * Filtrace rotační maskou
     */
    public void useRotationMaskFilter() {
        if (bufferedImage == null) {
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

        disableDefaultUI();
        rotationMaskButton.setDisable(true);
        clearFFTMatrix();
        tmp = Utils.cloneBufferedImage(bufferedImage);

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                int area = getAreaSizeFromSelectWithoutDirect(rotationMaskSelect.getSelectionModel().selectedIndexProperty().getValue());
                int[][] matrix = Utils.create2DArray(bufferedImage);
                RotationMaskFilter rotationMaskFilter = new RotationMaskFilter();

                matrix = rotationMaskFilter.processAreaFilter(matrix, area);

                bufferedImage = Utils.arrayToBufferedImage(bufferedImage, matrix);
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            showImage();
            setStatus("Byla provedena filtrace rotováním masky.", "GREEN");
            enableDefaultUI();
            rotationMaskButton.setDisable(false);
        });
        setStatus("Provádí se filtrace rotováním masky.", "BLUE");
        new Thread(task).start();
    }

    /**
     * Zablokování základních prvků UI
     */
    private void disableDefaultUI() {
        choiceBox.setDisable(true);
        openMI.setDisable(true);
        backButton.setDisable(true);
        saveAsMI.setDisable(true);
    }

    /**
     * Odblokování základních prvků UI
     */
    private void enableDefaultUI() {
        choiceBox.setDisable(false);
        openMI.setDisable(false);
        backButton.setDisable(false);
        saveAsMI.setDisable(false);
    }

    /**
     * Použití high pass and low pass filtru
     */
    public void useHighLowPassFilter() {
        if (bufferedImage == null) {
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

        Integer radius = Utils.parseInteger(radiusTextField.getText());

        if (radius == null) {
            setStatus("Poloměr musí být celé číslo.", "RED");
            return;
        }


        disableDefaultUI();
        highLowPassButton.setDisable(true);
        tmp = Utils.cloneBufferedImage(bufferedImage);
        clearFFTMatrix();


        boolean highPass = highLowPassSelect.getSelectionModel().selectedIndexProperty().getValue() == 0;

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                Complex[][] image;
                FFT fft = new FFT(false);

                image = Utils.create2DComplexArray(bufferedImage);

                if (!Utils.isNumberPowerOfTwo(bufferedImage.getWidth()) || !Utils.isNumberPowerOfTwo(bufferedImage.getHeight())) {
                    image = Utils.fillingMatrixToPowTwo(image);
                }

                image = fft.compute(image);

                image = Utils.centerFFTMatrix(image);
                image = Utils.insertCircle(image, highPass, radius);
                image = Utils.centerFFTMatrix(image);

                FFT ifft = new FFT(true);
                image = ifft.compute(image);

                bufferedImage = new BufferedImage(image[0].length, image.length, BufferedImage.TYPE_INT_RGB);
                ifft.createIFFTImage(bufferedImage, image);

                if (image.length != height || image[0].length != width) {
                    bufferedImage = Utils.restrictBufferedImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB), bufferedImage);
                }

                showImage();
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            setStatus("Byl aplikován filter horní nebo dolní propusti.", "GREEN");
            enableDefaultUI();
            highLowPassButton.setDisable(false);
        });
        setStatus("Provádí se filter horní nebo dolní propusti.", "BLUE");
        new Thread(task).start();
    }

}
