package com.example.carrentaldesktop;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

import java.sql.SQLException;
import java.util.Optional;

public class CarController {

    @FXML
    private TableColumn <Car, String> license_plate_numberCol;

    @FXML
    private TableColumn <Car, String> brandCol;

    @FXML
    private TableColumn <Car, String> modelCol;

    @FXML
    private TableColumn <Car, Integer> daily_costCol;

    @FXML
    private TableView <Car> carTable;

    private CarDB db;

    public void initialize()
    {
        license_plate_numberCol.setCellValueFactory(new PropertyValueFactory<>("license_plate_number"));
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        daily_costCol.setCellValueFactory(new PropertyValueFactory<>("daily_cost"));

        try {
            db = new CarDB();
            listaz();
        } catch (SQLException e) {
            Platform.runLater(() -> {
                hibakiir(e);
                Stage stage = (Stage) carTable.getScene().getWindow();
                stage.close();
            });
        }
    }

    private void listaz() throws SQLException {
        List<Car> carList = db.getCar();
        carTable.getItems().clear();
        carTable.getItems().addAll(carList);
    }

    @FXML
    public void deleteBtn(ActionEvent actionEvent) {
        if (carTable.getSelectionModel().getSelectedIndex() < 0) {
            alert("Törléshez előbb válasszon ki egy könyvet!");
            return;
        }
        Car deleted = carTable.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos, hogy törölni szeretnéd a kiválasztott könyvet?")) {
            return;
        }

        try {
            if (db.deleteCar(deleted)) {
                alert("Sikeres törlés!");
            } else {
                alert("Ismeretlen hiba történt a törlés során!");
            }
            listaz();
        } catch (SQLException e) {
            hibakiir(e);
        }
    }

    private void hibakiir(SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(e.getClass().toString());
        alert.setHeaderText(e.getMessage());
        alert.setContentText(e.getLocalizedMessage());
        alert.showAndWait();
    }

    protected boolean confirm(String s) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Biztos?");
        alert.setHeaderText(s);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get().equals(ButtonType.OK);
    }

    protected void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Figyelem!");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}