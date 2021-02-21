package com.oopclass.breadapp.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.oopclass.breadapp.config.StageManager;
import com.oopclass.breadapp.models.Cancel;
import com.oopclass.breadapp.services.impl.CancelService;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
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
public class CancelController implements Initializable {

    @FXML
    private Label ProductsId;

    @FXML
    private TextField itemName;

    @FXML
    private TextField size;

    @FXML
    private TextField price;

    @FXML
    private TextField otherDesc;

    @FXML
    private DatePicker dor;

    @FXML
    private DatePicker created;

    @FXML
    private Button saveProducts;
    
    @FXML
    private Button back;

    @FXML
    private DatePicker timeUpdated;

    @FXML
    private TableView<Cancel> productTable;

    @FXML
    private TableColumn<Cancel, Long> colProductsId;

    @FXML
    private TableColumn<Cancel, String> colItemName;

    @FXML
    private TableColumn<Cancel, String> colSize;

    @FXML
    private TableColumn<Cancel, String> colPrice;

    @FXML
    private TableColumn<Cancel, LocalDate> colDOR;

    @FXML
    private TableColumn<Cancel, String> colOtherDesc;

    @FXML
    private TableColumn<Cancel, LocalDate> colCreatedAt;

    @FXML
    private TableColumn<Cancel, LocalDate> colTimeUpdated;

    @FXML
    private TableColumn<Cancel, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private CancelService cancelService;

    private ObservableList<Cancel> cancelList = FXCollections.observableArrayList();

//    @FXML
//    private void exit(ActionEvent event) {
//        Platform.exit();
//    }
    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    
    @FXML
    private void returnHome (ActionEvent event){
        stageManager.switchScene(FxmlView.RESERVATION);
    }

    @FXML
    private void saveProducts(ActionEvent event) {

        if (validate("Item Name", getItemName(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("Size", getSize(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("Price", getPrice(), "([a-zA-Z]{3,30}\\s*)+")) {

            if (ProductsId.getText() == null || "".equals(ProductsId.getText())) {
                if (true) {

                    Cancel cancel = new Cancel();
                    cancel.setItemName(getItemName());
                    cancel.setSize(getSize());
                    cancel.setPrice(getPrice());
                    cancel.setOtherDesc(getOtherDesc());
                    cancel.setDor(getDor());
                    cancel.setCreatedAt(getCreatedAt());
                    cancel.setTimeUpdated(getTimeUpdated());

                    Cancel newCancel = cancelService.save(cancel);

                    saveAlert(newCancel);
                }

            } else {
                Cancel cancel = cancelService.find(Long.parseLong(ProductsId.getText()));
                cancel.setItemName(getItemName());
                cancel.setSize(getSize());
                cancel.setPrice(getPrice());
                cancel.setOtherDesc(getOtherDesc());
                cancel.setDor(getDor());
                cancel.setCreatedAt(getCreatedAt());
                cancel.setTimeUpdated(getTimeUpdated());
                Cancel updatedCancel = cancelService.update(cancel);
                updateAlert(updatedCancel);
            }

            clearFields();
            loadCancelDetails();
        }

    }

    @FXML
    private void deleteProduct(ActionEvent event) {
        List<Cancel> cancels = productTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            cancelService.deleteInBatch(cancels);
        }

        loadCancelDetails();
    }

    private void clearFields() {
        ProductsId.setText(null);
        itemName.clear();
        size.clear();
        otherDesc.clear();
        price.clear();
        dor.getEditor().clear();
        created.getEditor().clear();
        timeUpdated.getEditor().clear();


    }

    private void saveAlert(Cancel cancel) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cancel saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The cancel " + cancel.getItemName() + " " + cancel.getSize() + " has been created and \n" + " id is " + cancel.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Cancel cancel) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cancel updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The cancel " + cancel.getItemName() + " " + cancel.getSize() + " has been updated.");
        alert.showAndWait();
    }

    public String getItemName() {
        return itemName.getText();
    }

    public String getSize() {
        return size.getText();
    }

    public String getPrice() {
        return price.getText();
    }

    public LocalDate getDor() {
        return dor.getValue();
    }

    public LocalDate getCreatedAt() {
        return created.getValue();
    }

    public LocalDate getTimeUpdated() {
        return timeUpdated.getValue();
    }

    public String getOtherDesc() {
        return otherDesc.getText();
    }


    /*
	 *  Set All cancelTable column properties
     */
    private void setColumnProperties() {
        /* Override date format in table
		 * colDOB.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
			 String pattern = "dd/MM/yyyy";
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

        colProductsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDOR.setCellValueFactory(new PropertyValueFactory<>("dor"));
        colOtherDesc.setCellValueFactory(new PropertyValueFactory<>("OtherDesc"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colTimeUpdated.setCellValueFactory(new PropertyValueFactory<>("timeUpdated"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<Cancel, Boolean>, TableCell<Cancel, Boolean>> cellFactory
            = new Callback<TableColumn<Cancel, Boolean>, TableCell<Cancel, Boolean>>() {
        @Override
        public TableCell<Cancel, Boolean> call(final TableColumn<Cancel, Boolean> param) {
            final TableCell<Cancel, Boolean> cell = new TableCell<Cancel, Boolean>() {
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
                            Cancel cancel = getTableView().getItems().get(getIndex());
                            updateCancel(cancel);
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

                private void updateCancel(Cancel cancel) {
                    ProductsId.setText(Long.toString(cancel.getId()));
                    itemName.setText(cancel.getItemName());
                    size.setText(cancel.getSize());
                    price.setText(cancel.getPrice());
                    otherDesc.setText(cancel.getOtherDesc());
                    dor.setValue(cancel.getDor());
                    created.setValue(cancel.getCreatedAt());
                    
                    timeUpdated.setValue(cancel.getTimeUpdated());
                    

                }
            };
            return cell;
        }
    };

    /*
	 *  Add All cancels to observable list and update table
     */
    private void loadCancelDetails() {
        cancelList.clear();
        cancelList.addAll(cancelService.findAll());

        productTable.setItems(cancelList);
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

        productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

//        // Add all cancels into table
        loadCancelDetails();
    }
}
