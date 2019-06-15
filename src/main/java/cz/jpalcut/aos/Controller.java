package cz.jpalcut.aos;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
    public Button maxMinButton;
    public AnchorPane minPain;
    public ChoiceBox minAreaSelect;
    public ChoiceBox maxAreaSelect;

    @FXML
    Button FFTButton, IFFTButton, convolutionButton, deconvolutionButton;

    @FXML
    TextField convolutionMask, deconvolutionMask, thresholdField;

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

    List<File> fileList;

    @FXML
    protected void initialize() {
        choiceBox.setItems(FXCollections.observableArrayList("Průměrování v okolí bodu", "Modální filtrace", "Mediánová filtrace", "Průměr sledů snímků", "Filtrace maximem", "Filtrace minimem"));
        choiceBox.getSelectionModel().select(0);

        choiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> drawForm(newValue.intValue())
        );
        averagePain.setVisible(true);
    }

    private void drawForm(int method){
        switch (method) {
            case 0:
                averagePain.setVisible(true);
                modalPain.setVisible(false);
                medianPain.setVisible(false);
                averageImagePain.setVisible(false);
                maxPain.setVisible(false);
                minPain.setVisible(false);
                break;
            case 1:
                modalPain.setVisible(true);
                averagePain.setVisible(false);
                medianPain.setVisible(false);
                averageImagePain.setVisible(false);
                maxPain.setVisible(false);
                minPain.setVisible(false);
                break;
            case 2:
                medianPain.setVisible(true);
                averagePain.setVisible(false);
                modalPain.setVisible(false);
                averageImagePain.setVisible(false);
                maxPain.setVisible(false);
                minPain.setVisible(false);
                break;
            case 3:
                averageImagePain.setVisible(true);
                averagePain.setVisible(false);
                modalPain.setVisible(false);
                medianPain.setVisible(false);
                maxPain.setVisible(false);
                minPain.setVisible(false);
                break;
            case 4:
                maxPain.setVisible(true);
                averagePain.setVisible(false);
                modalPain.setVisible(false);
                medianPain.setVisible(false);
                averageImagePain.setVisible(false);
                minPain.setVisible(false);
            case 5:
                minPain.setVisible(true);
                averagePain.setVisible(false);
                modalPain.setVisible(false);
                medianPain.setVisible(false);
                averageImagePain.setVisible(false);
                maxPain.setVisible(false);
            default:
                averagePain.setVisible(true);
                modalPain.setVisible(false);
                medianPain.setVisible(false);
                averageImagePain.setVisible(false);
                maxPain.setVisible(false);
                minPain.setVisible(false);
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
//            if (!Utils.isNumberPowerOfTwo(bufferedImage.getWidth()) || !Utils.isNumberPowerOfTwo(bufferedImage.getHeight())) {
//                bufferedImage = null;
//                imageView.setImage(null);
//                disableAllButtons();
//                openMI.setDisable(false);
//                setStatus("Výška nebo šířka obrázku nemá velikost 2^n.", "RED");
//            } else {
                showImage();
//                disableAllButtons();
//                FFTButton.setDisable(false);
//                saveAsMI.setDisable(false);
//                openMI.setDisable(false);
//                setStatus("Obrázek byl načten.", "GREEN");
//            }
            matrixFFT = null;
            matrixIFFT = null;
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

        if(fileList == null){
            return;
        }
        images = new BufferedImage[fileList.size()];

        for (int i = 0; i < fileList.size(); i++){
            try {
                images[i] = ImageIO.read(fileList.get(i));
            } catch (IOException e) {
                setStatus("Nastala chyba při načtení obrázku.", "RED");
            }
        }
        averageImageButton.setDisable(false);
    }

    /**
     * Uložení obrázku
     */
    public void saveAs(){
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg",
                    "*.jpeg", "*.bmp");
            fileChooser.getExtensionFilters().addAll(extFilter);

            fileChooser.setTitle("Uložit obrázek");
            fileChooser.setInitialFileName(String.valueOf(new Random().nextInt()*1000));
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
        Task task = new Task<Void>() {
            @Override
            public Void call() {
        Complex[][] matrix;
        FFT fft = new FFT(true);
        matrix = fft.compute(matrixFFT);
        matrixIFFT = matrix.clone();
        bufferedImage = fft.createIFFTImage(bufferedImage, matrix);
        showImage();
        IFFTButton.setDisable(true);
        FFTButton.setDisable(false);
        convolutionButton.setDisable(true);
        deconvolutionButton.setDisable(true);
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            enableButtonsAfterIFFT();
            setStatus("Byla provedena IFFT.", "GREEN");
        });
        disableAllButtons();
        setStatus("Provádí se IFFT.", "BLUE");
        new Thread(task).start();


    }

    /**
     * Aplikace FFT na obrázek
     */
    public void useFFT() {
        Task task = new Task<Void>() {
            @Override
            public Void call() {
                Complex[][] matrix;
                FFT fft = new FFT(false);
                if(matrixIFFT != null){
                    matrix = matrixIFFT;
                    matrix = Utils.divideMatrix(matrix);
                }
                else{
                    matrix = Utils.create2DComplexArray(bufferedImage);
                }

                matrix = fft.compute(matrix);
                matrixFFT = matrix.clone();
                bufferedImage = fft.createFFTImage(bufferedImage, matrix);
                bufferedImage = fft.centerFFTImage(bufferedImage);
                showImage();
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            enableButtonsAfterFFT();
            setStatus("Byla provedena FFT.", "GREEN");
        });
        disableAllButtons();
        setStatus("Provádí se FFT.", "BLUE");
        new Thread(task).start();
    }

    /**
     * Zobrazí přenásobený FFT obrázek maskou, na kterou se aplikuje FFT
     */
    public void useFilterConvolution() {

        double[][] filter = Utils.parseArray(convolutionMask.getText());

        if (filter == null) {
            setStatus("Filtr nebyl definován správně.", "RED");
            return;
        }

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                Complex[][] complexes;
                complexes = Utils.arrayToComplexArray(filter, bufferedImage.getWidth(), bufferedImage.getHeight());
                FFT fft = new FFT(false);
                complexes = fft.compute(complexes);
                complexes = fft.convolution(matrixFFT, complexes);
                matrixFFT = complexes.clone();
                bufferedImage = fft.createFFTImage(bufferedImage, complexes);
                bufferedImage = fft.centerFFTImage(bufferedImage);
                showImage();
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            enableButtonsAfterFilter();
            setStatus("Byla provedena konvoluce.", "GREEN");
        });
        disableAllButtons();
        setStatus("Provádí se konvoluce.", "BLUE");
        new Thread(task).start();
    }

    /**
     * Zobrazí vydělený FFT obrázek maskou, na kterou se aplikuje FFT
     */
    public void useFilterDeconvolution() {
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
                Complex[][] complexes;
                complexes = Utils.arrayToComplexArray(filter, bufferedImage.getWidth(), bufferedImage.getHeight());
                FFT fft = new FFT(false);
                complexes = fft.compute(complexes);
                complexes = fft.deconvolution(matrixFFT, complexes, threshold);
                matrixFFT = complexes.clone();
                bufferedImage = fft.createFFTImage(bufferedImage, complexes);
                bufferedImage = fft.centerFFTImage(bufferedImage);
                showImage();
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            enableButtonsAfterFilter();
            setStatus("Byla provedena dekonvoluce.", "GREEN");
        });
        disableAllButtons();
        setStatus("Provádí se dekonvoluce.", "BLUE");
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
     * @param text String
     * @param color String
     */
    private void setStatus(String text, String color) {
        statusText.setText(text);
        statusText.setTextFill(Color.web(color));
    }

    /**
     * Zablokování všech tlačítek
     */
    private void disableAllButtons() {
        openMI.setDisable(true);
        saveAsMI.setDisable(true);
        FFTButton.setDisable(true);
        IFFTButton.setDisable(true);
        convolutionButton.setDisable(true);
        deconvolutionButton.setDisable(true);
    }

    /**
     * Odblokování tlačítek po provedení filtru
     */
    private void enableButtonsAfterFilter() {
        IFFTButton.setDisable(false);
        openMI.setDisable(false);
        saveAsMI.setDisable(false);
        convolutionButton.setDisable(false);
        deconvolutionButton.setDisable(false);
    }

    /**
     * Odblokování tlačítek po použití FFT
     */
    private void enableButtonsAfterFFT(){
        openMI.setDisable(false);
        saveAsMI.setDisable(false);
        IFFTButton.setDisable(false);
        convolutionButton.setDisable(false);
        deconvolutionButton.setDisable(false);
    }

    /**
     * Odblokování tlačítek po použití IFFT
     */
    private void enableButtonsAfterIFFT(){
        openMI.setDisable(false);
        saveAsMI.setDisable(false);
        FFTButton.setDisable(false);
    }

    public void testAction(){
//        int[][] matrix = Utils.create2DArray(bufferedImage);
//        AverageFilter averageFilter = new AverageFilter();
//        MedianFilter medianFilter = new MedianFilter();
//        ModalFilter modalFilter = new ModalFilter();
//        matrix = medianFilter.processAreaFilter(matrix,9);
//        bufferedImage = Utils.arrayToBufferedImage(bufferedImage, matrix);
//        showImage();
//        yolo.setVisible(false);
    }

    public int getAreaSizeFromSelect(int index){
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

    public void useAverageFilter(){
        tmp = Utils.cloneBufferedImage(bufferedImage);

        int area = getAreaSizeFromSelect(averageAreaSelect.getSelectionModel().selectedIndexProperty().getValue());
        int[][] matrix = Utils.create2DArray(bufferedImage);
        AverageFilter averageFilter = new AverageFilter();

        if(area == 0){
            matrix = averageFilter.processDirectFilter(matrix);
        }
        else{
            matrix = averageFilter.processAreaFilter(matrix,area);
        }

        bufferedImage = Utils.arrayToBufferedImage(bufferedImage, matrix);
        backButton.setDisable(false);
        showImage();
    }

    public void useModalFilter(){
        tmp = Utils.cloneBufferedImage(bufferedImage);

        int area = getAreaSizeFromSelect(modalAreaSelect.getSelectionModel().selectedIndexProperty().getValue());
        int[][] matrix = Utils.create2DArray(bufferedImage);
        ModalFilter modalFilter = new ModalFilter();

        if(area == 0){
            matrix = modalFilter.processDirectFilter(matrix);
        }
        else{
            matrix = modalFilter.processAreaFilter(matrix,area);
        }

        bufferedImage = Utils.arrayToBufferedImage(bufferedImage, matrix);
        backButton.setDisable(false);
        showImage();
    }

    public void useMedianFilter(){
        tmp = Utils.cloneBufferedImage(bufferedImage);

        int area = getAreaSizeFromSelect(medianAreaSelect.getSelectionModel().selectedIndexProperty().getValue());
        int[][] matrix = Utils.create2DArray(bufferedImage);
        MedianFilter medianFilter = new MedianFilter();

        if(area == 0){
            matrix = medianFilter.processDirectFilter(matrix);
        }
        else{
            matrix = medianFilter.processAreaFilter(matrix,area);
        }

        bufferedImage = Utils.arrayToBufferedImage(bufferedImage, matrix);
        backButton.setDisable(false);
        showImage();
    }

    public void useAverageImagesFilter(){
        if(images == null || images.length < 2){
            setStatus("Počet obrázků musí být více než jedna.", "RED");
        }
        else if (!Utils.haveImagesSameSize(images)){
            setStatus("Obrázky mají rozdílnou velikost.", "RED");
        }
        else{
            AverageImagesFilter filter = new AverageImagesFilter();
            bufferedImage = images[0];
            Utils.arrayToBufferedImage(bufferedImage,filter.processFilter(Utils.create3DArray(images)));
        }
        showImage();
        averageImageButton.setDisable(true);
    }

    public void actionBack(){
        bufferedImage = tmp;
        backButton.setDisable(true);
        showImage();
    }

    public void useMaxFilter(){
        tmp = Utils.cloneBufferedImage(bufferedImage);

        int area = getAreaSizeFromSelect(maxAreaSelect.getSelectionModel().selectedIndexProperty().getValue());
        int[][] matrix = Utils.create2DArray(bufferedImage);
        MaxMinFilter maxMinFilter = new MaxMinFilter(true);

        if(area == 0){
            matrix = maxMinFilter.processDirectFilter(matrix);
        }
        else{
            matrix = maxMinFilter.processAreaFilter(matrix,area);
        }

        bufferedImage = Utils.arrayToBufferedImage(bufferedImage, matrix);
        backButton.setDisable(false);
        showImage();
    }

    public void useMinFilter(){
        tmp = Utils.cloneBufferedImage(bufferedImage);

        int area = getAreaSizeFromSelect(minAreaSelect.getSelectionModel().selectedIndexProperty().getValue());
        int[][] matrix = Utils.create2DArray(bufferedImage);
        MaxMinFilter maxMinFilter = new MaxMinFilter(false);

        if(area == 0){
            matrix = maxMinFilter.processDirectFilter(matrix);
        }
        else{
            matrix = maxMinFilter.processAreaFilter(matrix,area);
        }

        bufferedImage = Utils.arrayToBufferedImage(bufferedImage, matrix);
        backButton.setDisable(false);
        showImage();
    }

}
