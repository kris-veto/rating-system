package com.aone.assignment01;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Controller {

    private Main mainApp;
    private String currentFxml;

    @FXML
    private Button switchButton;
    @FXML
    private TextField idField;
    @FXML
    private ComboBox<Integer> italianValue;
    @FXML
    private ComboBox<Integer> indianValue;
    @FXML
    private ComboBox<Integer> japaneseValue;
    @FXML
    private ComboBox<Integer> mexicanValue;
    @FXML
    private Label message;
    // table elements
    @FXML
    private TableView<Model> modelTable;
    @FXML
    private TableColumn<Model, String> idColumn;
    @FXML
    private TableColumn<Model, Integer> italianColumn;
    @FXML
    private TableColumn<Model, Integer> indianColumn;
    @FXML
    private TableColumn<Model, Integer> japaneseColumn;
    @FXML
    private TableColumn<Model, Integer> mexicanColumn;
    // bar chart elements
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton italyRadioButton;
    @FXML
    private RadioButton indiaRadioButton;
    @FXML
    private RadioButton japanRadioButton;
    @FXML
    private RadioButton mexicoRadioButton;

    private final ObservableList<Integer> list1 = FXCollections.observableArrayList(1, 2, 3, 4, 5); //ComboBox menu

    @FXML
    protected void showMenuOne() {  // setting menu to comboBox
        italianValue.setItems(list1);
    }
    @FXML
    protected void showMenuTwo() { // setting menu to comboBox
        indianValue.setItems(list1);
    }
    @FXML
    protected void showMenuThree() { // setting menu to comboBox
        japaneseValue.setItems(list1);
    }
    @FXML
    protected void showMenuFour() { // setting menu to comboBox
        mexicanValue.setItems(list1);
    }
    //inserting data to the table and data base
    private ObservableList<Model> modelData = FXCollections.observableArrayList();
    @FXML
    private void addRate() {
        String id = idField.getText();
        Integer italian = italianValue.getValue();
        Integer japanese = japaneseValue.getValue();
        Integer indian = indianValue.getValue();
        Integer mexican = mexicanValue.getValue();

        //check if the fields are empty
        if(id.isEmpty()||italian==null||japanese==null||indian==null||mexican==null){
            //if not all the fields are input
            message.setText("Please fill in all info.");
        } else {
            Model ratingtable = new Model(id, italian, japanese, indian, mexican);
            modelData.add(ratingtable);

            try (Connection connection = dbConnector.connect()) {
                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO ratingtable (id, italian_food, indian_food, japanese_food, mexican_food) VALUES ('" + id
                        + "', " + italian + ", " + indian + ", " + japanese + ", " + mexican + ")");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            clearFields();
        }
    }
    private void clearFields() {
        idField.clear();
        italianValue.setValue(null);
        japaneseValue.setValue(null);
        indianValue.setValue(null);
        mexicanValue.setValue(null);
    }

    // deleting data
    @FXML
    private void deleteRate() {

        Model selectedRateId = modelTable.getSelectionModel().getSelectedItem();
        if(selectedRateId == null){
            message.setText("Please select an item to delete.");
        }else {
            modelData.remove(selectedRateId);

            try (Connection connection = dbConnector.connect()) {
                Statement statement = connection.createStatement();
                statement.executeUpdate("DELETE FROM ratingtable WHERE id = " + selectedRateId.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void initData(Main mainApp, String currentFxml) { // initializes the controller with a reference to the main application and the current FXML file name.
        this.mainApp = mainApp;
        this.currentFxml = currentFxml;
        initialize();
    }



    @FXML
    public void initialize() {

        if (currentFxml != null) {
            if (currentFxml.equals("graphic-view.fxml")) {

                switchButton.setText("Go to Table Information");
                switchButton.setOnAction(this::showTableView); //switch scene
            } else if (currentFxml.equals("table-view.fxml")) {

                switchButton.setText("Go to Graphic");
                switchButton.setOnAction(this::showGraphicView); //switch scene
                showMenuOne();    // show options in ComboBoxes
                showMenuTwo();
                showMenuThree();
                showMenuFour();
                showRatings(); // READ  showing ratings in the table
                //configureRadioButtonHandlers();
                // Initialize the table
                idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                italianColumn.setCellValueFactory(new PropertyValueFactory<>("italian"));
                japaneseColumn.setCellValueFactory(new PropertyValueFactory<>("japanese"));
                indianColumn.setCellValueFactory(new PropertyValueFactory<>("indian"));
                mexicanColumn.setCellValueFactory(new PropertyValueFactory<>("mexican"));
                //link the table with the data list
                modelTable.setItems(modelData);
                modelTable.setPlaceholder(new Label("No rows to display"));
            }
        }
    }
    DatabaseConnector dbConnector = new DatabaseConnector();
    private void showRatings() { // --------->>>>>> show data from table "ratingtable" from database
        try (Connection connection = dbConnector.connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ratingsdb.ratingtable"); // READ 1

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                int italian = resultSet.getInt("italian_food");
                int indian = resultSet.getInt("indian_food");
                int japanese = resultSet.getInt("japanese_food");
                int mexican = resultSet.getInt("mexican_food");

                Model rates = new Model(id, italian, indian, japanese, mexican);
                modelData.add(rates);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleItalyRadioButton(ActionEvent event) {
        if (italyRadioButton.isSelected()) {
            indiaRadioButton.setSelected(false);
            japanRadioButton.setSelected(false);
            mexicoRadioButton.setSelected(false);
            showGraphic("Italy");
        }
    }

    @FXML
    private void handleIndiaRadioButton(ActionEvent event) {
        if (indiaRadioButton.isSelected()) {
            italyRadioButton.setSelected(false);
            japanRadioButton.setSelected(false);
            mexicoRadioButton.setSelected(false);
            showGraphic("India");
        }
    }

    @FXML
    private void handleJapanRadioButton(ActionEvent event) {
        if (japanRadioButton.isSelected()) {
            italyRadioButton.setSelected(false);
            indiaRadioButton.setSelected(false);
            mexicoRadioButton.setSelected(false);
            showGraphic("Japan");
        }
    }

    @FXML
    private void handleMexicoRadioButton(ActionEvent event) {
        if (mexicoRadioButton.isSelected()) {
            italyRadioButton.setSelected(false);
            indiaRadioButton.setSelected(false);
            japanRadioButton.setSelected(false);
            showGraphic("Mexico");
        }
    }
    // READ 2
    private final ObservableList<String> yAxisLabels = FXCollections.observableArrayList("1", "2", "3", "4", "5");
    private void showGraphic(String country){
        try (Connection connection = dbConnector.connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ratingsdb.ratingtable");

            // Map para contar el número de ocurrencias de cada puntaje
            Map<Integer, Integer> ratingCounts = new HashMap<>();
            ratingCounts.put(1, 0);
            ratingCounts.put(2, 0);
            ratingCounts.put(3, 0);
            ratingCounts.put(4, 0);
            ratingCounts.put(5, 0);

            // Contar las ocurrencias de cada puntaje según el país seleccionado
            while (resultSet.next()) {
                int rating = 0;

                switch (country) {
                    case "Italy":
                        rating = resultSet.getInt("italian_food");
                        break;
                    case "India":
                        rating = resultSet.getInt("indian_food");
                        break;
                    case "Japan":
                        rating = resultSet.getInt("japanese_food");
                        break;
                    case "Mexico":
                        rating = resultSet.getInt("mexican_food");
                        break;
                }

                // Incrementar el contador para el puntaje correspondiente
                ratingCounts.put(rating, ratingCounts.get(rating) + 1);
            }

            // Crear la serie de datos para el gráfico
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (Map.Entry<Integer, Integer> entry : ratingCounts.entrySet()) {
                series.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
            }

            // Configurar el eje Y con etiquetas de puntajes
            CategoryAxis yAxis = new CategoryAxis();
            yAxis.setCategories(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
            barChart.getYAxis().setLabel("Number of Ratings");  // Etiqueta para el eje Y
            barChart.setAnimated(false);
//            barChart.setYAxis(yAxis);

            // Limpiar y añadir la serie de datos al gráfico
            barChart.getData().clear();
            barChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showGraphicView(ActionEvent event) {
        try {
            mainApp.showScene("graphic-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void showTableView(ActionEvent event){

        try {
            mainApp.showScene("table-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}