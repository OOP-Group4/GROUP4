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
import com.oopclass.breadapp.models.Rating;
import com.oopclass.breadapp.services.impl.RatingService;
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
public class RatingController implements Initializable {

    @FXML
    private Label ratingId;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField middleName;

    @FXML
    private DatePicker dor;

    @FXML
    private Button saveRating;
    
    @FXML
    private Button back;

    @FXML
    private RadioButton rbSatisfied;

    @FXML
    private RadioButton rbUnsatisfied;

    @FXML
    private TableView<Rating> ratingTable;

    @FXML
    private TableColumn<Rating, Long> colRatingId;

    @FXML
    private TableColumn<Rating, String> colFirstName;

    @FXML
    private TableColumn<Rating, String> colLastName;

    @FXML
    private TableColumn<Rating, String> colMiddleName;

    @FXML
    private TableColumn<Rating, LocalDate> colDOR;

    @FXML
    private TableColumn<Rating, String> colRating;

    @FXML
    private TableColumn<Rating, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private RatingService ratingService;

    private ObservableList<Rating> ratingList = FXCollections.observableArrayList();

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
    private void saveRatings(ActionEvent event) {

        if (validate("First Name", getFirstName(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("Last Name", getLastName(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("Middle Name", getMiddleName(), "([a-zA-Z]{3,30}\\s*)+")) {

            if (ratingId.getText() == null || "".equals(ratingId.getText())) {
                if (true) {

                    Rating rating = new Rating();
                    rating.setFirstName(getFirstName());
                    rating.setLastName(getLastName());
                    rating.setMiddleName(getMiddleName());
                    rating.setDor(getDor());
                    rating.setCustomerRating(getCustomerRating());

                    Rating newRating = ratingService.save(rating);

                    saveAlert(newRating);
                }

            } else {
                Rating rating = ratingService.find(Long.parseLong(ratingId.getText()));
                rating.setFirstName(getFirstName());
                rating.setLastName(getLastName());
                rating.setMiddleName(getMiddleName());
                rating.setDor(getDor());
                rating.setCustomerRating(getCustomerRating());
                Rating updatedRating = ratingService.update(rating);
                updateAlert(updatedRating);
            }

            clearFields();
            loadRatingDetails();
        }

    }

//    @FXML
//    private void deleteRatings(ActionEvent event) {
//        List<Rating> ratings = ratingTable.getSelectionModel().getSelectedItems();
//
//        Alert alert = new Alert(AlertType.CONFIRMATION);
//        alert.setTitle("Confirmation Dialog");
//        alert.setHeaderText(null);
//        alert.setContentText("Are you sure you want to delete selected?");
//        Optional<ButtonType> action = alert.showAndWait();
//
//        if (action.get() == ButtonType.OK) {
//            ratingService.deleteInBatch(ratings);
//        }
//
//        loadRatingDetails();
//    }

    private void clearFields() {
        ratingId.setText(null);
        firstName.clear();
        lastName.clear();
        middleName.clear();
        dor.getEditor().clear();
        rbSatisfied.setSelected(true);
        rbUnsatisfied.setSelected(true);

    }

    private void saveAlert(Rating rating) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Rating saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The rating " + rating.getFirstName() + " " + rating.getLastName() + " has been created and \n" + " id is " + rating.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Rating rating) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Rating updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The rating " + rating.getFirstName() + " " + rating.getLastName() + " has been updated.");
        alert.showAndWait();
    }

    public String getFirstName() {
        return firstName.getText();
    }

    public String getLastName() {
        return lastName.getText();
    }

    public String getMiddleName() {
        return middleName.getText();
    }

    public LocalDate getDor() {
        return dor.getValue();
    }

    public String getCustomerRating() {
        return rbSatisfied.isSelected() ? "Satisfied" : "Unsatisfied";
    }


    /*
	 *  Set All ratingTable column properties
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

        colRatingId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colMiddleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        colDOR.setCellValueFactory(new PropertyValueFactory<>("dor"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("customerRating"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<Rating, Boolean>, TableCell<Rating, Boolean>> cellFactory
            = new Callback<TableColumn<Rating, Boolean>, TableCell<Rating, Boolean>>() {
        @Override
        public TableCell<Rating, Boolean> call(final TableColumn<Rating, Boolean> param) {
            final TableCell<Rating, Boolean> cell = new TableCell<Rating, Boolean>() {
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
                            Rating rating = getTableView().getItems().get(getIndex());
                            updateRating(rating);
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

                private void updateRating(Rating rating) {
                    ratingId.setText(Long.toString(rating.getId()));
                    firstName.setText(rating.getFirstName());
                    lastName.setText(rating.getLastName());
                    middleName.setText(rating.getMiddleName());
                    dor.setValue(rating.getDor());
                    if (rating.getCustomerRating().equals("Satisfied")) {
                        rbSatisfied.setSelected(true);
                    } else {
                        rbUnsatisfied.setSelected(true);
                    }

                }
            };
            return cell;
        }
    };

    /*
	 *  Add All ratings to observable list and update table
     */
    private void loadRatingDetails() {
        ratingList.clear();
        ratingList.addAll(ratingService.findAll());

        ratingTable.setItems(ratingList);
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

        ratingTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

//        // Add all ratings into table
        loadRatingDetails();
    }
}
