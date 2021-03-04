package com.oopclass.breadapp.views;

import java.util.ResourceBundle;


public enum FxmlView {

    RESERVATION {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("reservation.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Reservation.fxml";
        }
    }, CANCELLATION {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("cancellation.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Cancellation.fxml";
        }
    }, INSTALLATION {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("installation.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Installation.fxml";
        }
    },;


    public abstract String getTitle();

    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
