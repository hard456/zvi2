package cz.jpalcut.aos;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
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

    public ChoiceBox choiceBox;
    public AnchorPane averagePain;
    public ChoiceBox averageAreaSelect;
    public AnchorPane modalPain;
    public ChoiceBox modalAreaSelect;
    public AnchorPane medianPain;
    public ChoiceBox medianAreaSelect;
    public AnchorPane averageImagePain;
    public Button averageImageButton;
    public MenuItem backButton;
    public AnchorPane maxPain;
    public AnchorPane minPain;
    public ChoiceBox minAreaSelect;
    public ChoiceBox maxAreaSelect;
    public AnchorPane fftPain;
    public AnchorPane ifftPain;
    public AnchorPane inverseFilterPain;
    public Button ifftButton;
    public Button fftButton;
    public AnchorPane conservativePain;
    public ChoiceBox conservativeSelect;
    public AnchorPane rotationMaskPain;
    public ChoiceBox rotationMaskSelect;

    @FXML
    TextField deconvolutionMask, thresholdField;

    @FXML
    MenuItem openMI, saveAsMI;

    @FXML
    ImageView imageView;

    @FXML
    Label statusText;


    private BufferedImage tmp;

    private BufferedImage bufferedImage;

    private Complex[][] matrixFFT, matrixIFFT;

    private BufferedImage[] images;

    private List<File> fileList;

    @FXML
    protected void initialize() {
        choiceBox.setItems(FXCollections.observableArrayList("Průměrování v okolí bodu", "Modální filtrace", "Mediánová filtrace",
                "Průměr sledů snímků", "Filtrace maximem", "Filtrace minimem", "FFT obrázku", "IFFT obrázku", "Inverzní filter", "Konzervativní filtr - pepř a sůl", "Filtrace rotováním masky"));
        choiceBox.getSelectionModel().select(0);

        choiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> drawForm(newValue.intValue())
        );
        averagePain.setVisible(true);
    }

    private void drawForm(int method) {

        if(matrixFFT == null){
            ifftButton.setDisable(true);
        }
        else{
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
        }

    }

    public void openImages() {
        fileList = null;
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

        if(bufferedImage == null){
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

        if (!Utils.isNumberPowerOfTwo(bufferedImage.getWidth()) || !Utils.isNumberPowerOfTwo(bufferedImage.getHeight())) {
            setStatus("Výška nebo šířka obrázku nemá velikost 2^n.", "RED");
            return;
        }

        tmp = Utils.cloneBufferedImage(bufferedImage);

        if (matrixFFT == null) {
            setStatus("Inverzní FFT lze provést jen po použití FFT.", "RED");
        }

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                Complex[][] matrix;
                FFT fft = new FFT(true);
                matrix = matrixFFT.clone();
                matrix = fft.compute(matrix);
                matrixIFFT = matrix.clone();
                bufferedImage = fft.createIFFTImage(bufferedImage, matrix);
                showImage();
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            setStatus("Byla provedena IFFT.", "GREEN");
            backButton.setDisable(false);
            ifftButton.setDisable(true);
            fftButton.setDisable(false);
        });
        setStatus("Provádí se IFFT.", "BLUE");
        new Thread(task).start();
    }

    /**
     * Aplikace FFT na obrázek
     */
    public void useFFT() {

        if(bufferedImage == null){
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

        if (!Utils.isNumberPowerOfTwo(bufferedImage.getWidth()) || !Utils.isNumberPowerOfTwo(bufferedImage.getHeight())) {
            setStatus("Výška nebo šířka obrázku nemá velikost 2^n.", "RED");
            return;
        }

        tmp = Utils.cloneBufferedImage(bufferedImage);

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
                }

                matrix = fft.compute(matrix);
                matrixFFT = matrix.clone();
                fft.createFFTImage(bufferedImage, matrix);
                bufferedImage = fft.centerFFTImage(bufferedImage);
                showImage();
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            setStatus("Byla provedena FFT.", "GREEN");
            backButton.setDisable(false);
            fftButton.setDisable(true);
            ifftButton.setDisable(false);
        });
        setStatus("Provádí se FFT.", "BLUE");
        new Thread(task).start();
    }

    /**
     * Zobrazí vydělený FFT obrázek maskou, na kterou se aplikuje FFT
     */
    public void useInverseFilter() {

        if(bufferedImage == null){
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

        if (!Utils.isNumberPowerOfTwo(bufferedImage.getWidth()) || !Utils.isNumberPowerOfTwo(bufferedImage.getHeight())) {
            setStatus("Výška nebo šířka obrázku nemá velikost 2^n.", "RED");
            return;
        }

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
                mask = Utils.arrayToComplexArray(filter, bufferedImage.getWidth(), bufferedImage.getHeight());
                FFT fft = new FFT(false);

                image = Utils.create2DComplexArray(bufferedImage);
                image = fft.compute(image);

                mask = fft.compute(mask);
                mask = fft.deconvolution(image, mask, threshold);

                FFT ifft = new FFT(true);
                mask = ifft.compute(mask);
                ifft.createIFFTImage(bufferedImage, mask);

                showImage();
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            setStatus("Byl aplikován inverzní filter.", "GREEN");
            backButton.setDisable(false);
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

    public void testAction() {
//        int[][] matrix = Utils.create2DArray(bufferedImage);
//        AverageFilter averageFilter = new AverageFilter();
//        MedianFilter medianFilter = new MedianFilter();
//        ModalFilter modalFilter = new ModalFilter();
//        matrix = medianFilter.processAreaFilter(matrix,9);
//        bufferedImage = Utils.arrayToBufferedImage(bufferedImage, matrix);
//        showImage();
//        yolo.setVisible(false);
    }

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

    private int getAreaSizeFromSelectWithouDirect(int index) {
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

    public void useAverageFilter() {

        if(bufferedImage == null){
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

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
            backButton.setDisable(false);
        });
        setStatus("Provádí se průměrování v okolí bodu.", "BLUE");
        new Thread(task).start();
    }

    public void useModalFilter() {

        if(bufferedImage == null){
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

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
            backButton.setDisable(false);
        });
        setStatus("Provádí se modální filter.", "BLUE");
        new Thread(task).start();
    }

    public void useMedianFilter() {

        if(bufferedImage == null){
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

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
            backButton.setDisable(false);
        });
        setStatus("Provádí se mediánová filtrace.", "BLUE");
        new Thread(task).start();
    }

    public void useAverageImagesFilter() {
        clearFFTMatrix();
        tmp = Utils.cloneBufferedImage(bufferedImage);

        if (images == null || images.length < 2) {
            setStatus("Počet obrázků musí být více než jedna.", "RED");
        } else if (!Utils.haveImagesSameSize(images)) {
            setStatus("Obrázky mají rozdílnou velikost.", "RED");
        } else {

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
                backButton.setDisable(false);
                averageImageButton.setDisable(true);
            });
            setStatus("Provádí se průměr sledu snímků.", "BLUE");
            new Thread(task).start();
        }
    }

    public void actionBack() {
//        if(matrixIFFT != null && matrixFFT != null){
//            fftButton.setDisable(false);
//            matrixIFFT = null;
//            ifftButton.setDisable(true);
//        }
//        else if(matrixIFFT != null){
//            matrixIFFT = null;
//            ifftButton.setDisable(false);
//            fftButton.setDisable(true);
//        }
//        else if(matrixFFT != null){
//            matrixFFT = null;
//            fftButton.setDisable(false);
//        }
        bufferedImage = tmp;
        backButton.setDisable(true);
        setStatus("Byla provedena akce zpět.", "GREEN");
        showImage();
    }

    public void useMaxFilter() {

        if(bufferedImage == null){
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

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
            backButton.setDisable(false);
        });
        setStatus("Provádí se filtrace maximem.", "BLUE");
        new Thread(task).start();
    }

    public void useMinFilter() {

        if(bufferedImage == null){
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

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
            backButton.setDisable(false);
        });
        setStatus("Provádí se filtrace minimem.", "BLUE");
        new Thread(task).start();
    }

    private void clearFFTMatrix(){
        fftButton.setDisable(false);
        matrixFFT = null;
        matrixIFFT = null;
    }

    public void useConservativeFilter() {

        if(bufferedImage == null){
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

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
            backButton.setDisable(false);
        });
        setStatus("Provádí se konzervativní filtrace - pepř a sůl.", "BLUE");
        new Thread(task).start();
    }

    public void useRotationMaskFilter() {
        if(bufferedImage == null){
            setStatus("Obrázek nebyl načten.", "RED");
            return;
        }

        clearFFTMatrix();
        tmp = Utils.cloneBufferedImage(bufferedImage);

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                int area = getAreaSizeFromSelectWithouDirect(rotationMaskSelect.getSelectionModel().selectedIndexProperty().getValue());
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
            backButton.setDisable(false);
        });
        setStatus("Provádí se filtrace rotováním masky.", "BLUE");
        new Thread(task).start();
    }
}
