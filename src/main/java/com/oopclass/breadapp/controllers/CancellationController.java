/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oopclass.breadapp.controllers;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import com.oopclass.breadapp.config.StageManager;
import com.oopclass.breadapp.views.FxmlView;
import com.oopclass.breadapp.services.impl.CancellationService;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.util.Callback;
import javafx.scene.image.ImageView;

import com.oopclass.breadapp.models.Cancellation;
import com.oopclass.breadapp.models.Reservation;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author Herradura
 */
@Controller
public class CancellationController implements Initializable {

    @FXML
    private TextField fullName;
    @FXML
    private TextField reason;
    @FXML
    private TextField product;
    @FXML
    private DatePicker doc;
    @FXML
    private Button saveCancel;
    @FXML
    private Button deleteCancel;
    @FXML
    private Button openReservation;
    @FXML
    private Label cancellationId;
    @FXML
    private TableView<Cancellation> cancelTable;
    @FXML
    private TableColumn<Cancellation, Long> colId;
    @FXML
    private TableColumn<Cancellation, String> colFullname;
    @FXML
    private TableColumn<Cancellation, String> colReason;
    @FXML
    private TableColumn<Cancellation, String> colProduct;
    @FXML
    private TableColumn<Cancellation, LocalDate> colDOC;
    @FXML
    private TableColumn<Cancellation, Boolean> colEdit;
    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private CancellationService cancellationService;

   private ObservableList<Cancellation> cancellationList = FXCollections.observableArrayList();
   
    @FXML
    void openReservation(ActionEvent event) {
stageManager.switchScene(FxmlView.RESERVATION);
    }

    @FXML
    void saveCancel(ActionEvent event) {
        

        if (validate("Full Name", getFullName(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("Reason", getReason(), "([a-zA-Z]{3,30}\\s*)+")
                && emptyValidation("Product", product.getText().isEmpty()));
        {

            if (cancellationId.getText() == null || "".equals(cancellationId.getText())) {
                if (true) {

                    Cancellation cancellation = new Cancellation();
                    cancellation.setFullName(getFullName());
                    cancellation.setReason(getReason());
                    cancellation.setProduct(getProduct());
                    cancellation.setDoc(getDoc());
                    Cancellation newCancellation = cancellationService.save(cancellation);
                    saveAlert(newCancellation);
                }

            } else {
                Cancellation cancellation = cancellationService.find(Long.parseLong(cancellationId.getText()));
                cancellation.setFullName(getFullName());
                cancellation.setReason(getReason());
                cancellation.setProduct(getProduct());
                cancellation.setDoc(getDoc());
                Cancellation updateCancellation = cancellationService.update(cancellation);
//                updateAlert(updateCancellation);
            }

            clearFields();
            loadCancellationDetails();
        }

    }
@FXML
    private void deleteCancel(ActionEvent event) {
        List<Cancellation> cancellation = cancelTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            cancellationService.deleteInBatch(cancellation);
        }

        loadCancellationDetails();
    }
    private void clearFields() {
        cancellationId.setText(null);
        fullName.clear();
        product.clear();
        reason.clear();
        doc.getEditor().clear();

    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }

     private void saveAlert(Cancellation cancellation) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cancel saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The customer " + cancellation.getFullName() + " " + cancellation.getReason() + " has been created and \n" + cancellation.getProduct() + " has been updated. " + " Date of cancellation is " + cancellation.getDoc() + " " + " id is " + cancellation.getId() + ".");
        alert.showAndWait();
    }

    /*
	 *  Set All reservationTable column properties
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

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFullname.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        colDOC.setCellValueFactory(new PropertyValueFactory<>("doc"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<Cancellation, Boolean>, TableCell<Cancellation, Boolean>> cellFactory
            = new Callback<TableColumn<Cancellation, Boolean>, TableCell<Cancellation, Boolean>>() {
        @Override
        public TableCell<Cancellation, Boolean> call(final TableColumn<Cancellation, Boolean> param) {
            final TableCell<Cancellation, Boolean> cell;
            cell = new TableCell<Cancellation, Boolean>() {
                Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                final Button btnEdit = new Button();
//
                @Override
                public void updateItem(Boolean check, boolean empty) {
                    super.updateItem(check, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btnEdit.setOnAction(e -> {
                            Cancellation cancellation = getTableView().getItems().get(getIndex());
//                            updateCancellation(cancellation);
                        });

                        btnEdit.setStyle("-fx-background-color: transparent;");
                        ImageView iv = new ImageView();
                        iv.setImage(imgEdit);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        btnEdit.setGraphic(iv);

                        setGraphic(btnEdit);
//                        setAlignment(Pos.CENTER);
                        setText(null);
                    }
                }

                private void saveCancel(Cancellation cancellation) {
                    cancellationId.setText(Long.toString(cancellation.getId()));
                    fullName.setText(cancellation.getFullName());
                    reason.setText(cancellation.getReason());
                    product.setText(cancellation.getProduct());
                    doc.setValue(cancellation.getDoc());

                }
            };
            return cell;
        }
    };

    /*
	 *  Add All reservations to observable list and update table
     */
    private void loadCancellationDetails() {
        cancellationList.clear();
        cancellationList.addAll(cancellationService.findAll());

        cancelTable.setItems(cancellationList);
      
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
        Alert alert = new Alert(Alert.AlertType.WARNING);
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

    public String getFullName() {
        return fullName.getText();
    }

    public String getReason() {
        return reason.getText();
    }

    public String getProduct() {
        return product.getText();
    }

    public LocalDate getDoc() {
        return doc.getValue();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cancelTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

//        // Add all reservations into table
        loadCancellationDetails();
    }

}
