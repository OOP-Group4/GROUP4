package com.oopclass.breadapp.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.oopclass.breadapp.config.StageManager;
import com.oopclass.breadapp.models.Installation;
import com.oopclass.breadapp.services.impl.InstallationService;
import com.oopclass.breadapp.views.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Controller
public class InstallationController implements Initializable {

    @FXML
    private Label installationId;

    @FXML
    private DatePicker created;

    @FXML
    private DatePicker timeUpdated;

    @FXML
    private TextField services;

    @FXML
    private TextField mPayment;

    @FXML
    private DatePicker dor;

    @FXML
    private TextField dPayment;

    @FXML
    private Button saveInstallation;

    @FXML
    private Button returnHome;

    @FXML
    private Button deleteProduct;

    @FXML
    private TableView<Installation> installationTable;

    @FXML
    private TableColumn<Installation, Long> colInstallationId;

    @FXML
    private TableColumn<Installation, LocalDate> colCreated;

    @FXML
    private TableColumn<Installation, LocalDate> colUpdate;

    @FXML
    private TableColumn<Installation, String> colServices;

    @FXML
    private TableColumn<Installation, String> colMpayment;

    @FXML
    private TableColumn<Installation, LocalDate> colDOR;

    @FXML
    private TableColumn<Installation, String> colDpayment;

    @FXML
    private TableColumn<Installation, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private InstallationService installationService;

    private ObservableList<Installation> installationList = FXCollections.observableArrayList();

//    @FXML
//    private void exit(ActionEvent event) {
//        Platform.exit();
//    }
    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void returnHome(ActionEvent event) {
        stageManager.switchScene(FxmlView.RESERVATION);
    }

    @FXML
    private void saveInstallations(ActionEvent event) {

        if (validate("Services", getServices(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("MPayment", getMOP(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("DPayment", getDownPayment(), "([a-zA-Z]{3,30}\\s*)+")) {

            if (installationId.getText() == null || "".equals(installationId.getText())) {
                if (true) {

                    Installation installation = new Installation();
                    installation.setMOP(getMOP());
                    installation.setDownPayment(getDownPayment());
                    installation.setServices(getServices());
                    installation.setDOR(getDOR());
                    installation.setCreatedAt(getCreatedAt());
                    installation.setUpdatedAt(getUpdatedAt());

                    Installation newInstallation = installationService.save(installation);

                    saveAlert(newInstallation);
                }

            } else {
                Installation installation = installationService.find(Long.parseLong(installationId.getText()));
                installation.setMOP(getMOP());
                installation.setDownPayment(getDownPayment());
                installation.setServices(getServices());
                installation.setDOR(getDOR());
                installation.setCreatedAt(getCreatedAt());
                installation.setUpdatedAt(getUpdatedAt());

                Installation updatedInstallation = installationService.update(installation);
                updateAlert(updatedInstallation);
            }

            clearFields();
            loadInstallationDetails();
        }

}

    @FXML
    private void deleteProduct(ActionEvent event) {
        List<Installation> installations = installationTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            installationService.deleteInBatch(installations);
        }

        loadInstallationDetails();
    }

    private void clearFields() {
        installationId.setText(null);
        mPayment.clear();
        dPayment.clear();
        services.clear();
        dor.getEditor().clear();
        created.getEditor().clear();
        timeUpdated.getEditor().clear();

    }

    private void saveAlert(Installation installation) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Installation saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The installation " + installation.getServices() + " " + installation.getMOP() + " has been created and \n" + " id is " + installation.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Installation installation) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Installation updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The installation " + installation.getServices() + " " + installation.getMOP() + " has been updated.");
        alert.showAndWait();
    }

    public LocalDate getCreatedAt() {
        return created.getValue();
    }

    public LocalDate getUpdatedAt() {
        return timeUpdated.getValue();
    }

    public String getServices() {
        return services.getText();

    }

    public String getMOP() {
        return mPayment.getText();
    }

    public String getDownPayment() {
        return dPayment.getText();
    }

    public LocalDate getDOR() {
        return dor.getValue();
    }

    /*
	 *  Set All installationTable column properties
     */
    private void setColumnProperties() {
        /* Override date format in table
		  colTimeUpdated.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
			 String pattern = "HH:mm:ss";
			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 }));*/

        colInstallationId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCreated.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("UpdatedAt"));
        colServices.setCellValueFactory(new PropertyValueFactory<>("services"));
        colMpayment.setCellValueFactory(new PropertyValueFactory<>("MOP"));
        colDpayment.setCellValueFactory(new PropertyValueFactory<>("downPayment"));
        colDOR.setCellValueFactory(new PropertyValueFactory<>("DOR"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<Installation, Boolean>, TableCell<Installation, Boolean>> cellFactory
            = new Callback<TableColumn<Installation, Boolean>, TableCell<Installation, Boolean>>() {
        @Override
        public TableCell<Installation, Boolean> call(final TableColumn<Installation, Boolean> param) {
            final TableCell<Installation, Boolean> cell;
            cell = new TableCell<Installation, Boolean>() {
                Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                final Button btnEdit = new Button();

                @Override
                public void updateItem(Boolean check, boolean empty) {
                    super.updateItem(check, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btnEdit.setOnAction(e -> {
                            Installation installation = getTableView().getItems().get(getIndex());
                            updateInstallation(installation);
                        });

                        btnEdit.setStyle("-fx-background-color: transparent;");
                        ImageView iv = new ImageView();
                        iv.setImage(imgEdit);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        btnEdit.setGraphic(iv);

                        setGraphic(btnEdit);
                        setAlignment(Pos.CENTER);
                        setText(null);
                    }
                }

                private void updateInstallation(Installation installation) {
                    installationId.setText(Long.toString(installation.getId()));
                    created.setValue(installation.getCreatedAt());
                    timeUpdated.setValue(installation.getUpdatedAt());
                    services.setText(installation.getServices());
                    mPayment.setText(installation.getMOP());
                    dPayment.setText(installation.getDownPayment());
                    dor.setValue(installation.getDOR());

                }
            };
            return cell;
        }
    };

    /*
	 *  Add All installations to observable list and update table
     */
    private void loadInstallationDetails() {
        installationList.clear();
        installationList.addAll(installationService.findAll());

        installationTable.setItems(installationList);
    }

    /*
	 * Validations
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (field.equals("Role")) {
            alert.setContentText("Please Select " + field);
        } else {
            if (empty) {
                alert.setContentText("Please Enter " + field);
            } else {
                alert.setContentText("Please Enter Valid " + field);
            }
        }
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        installationTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

//        // Add all installations into table
        loadInstallationDetails();
    }
}
