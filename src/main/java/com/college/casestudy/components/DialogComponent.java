package com.college.casestudy.components;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

import java.util.Optional;

public class DialogComponent {

    public void showInformationDialog( String headerText, String contentText ) {
        final Alert alert = new Alert( Alert.AlertType.INFORMATION ); // Set the alert type to information
        alert.setHeaderText( headerText ); // Add the custom header text
        alert.setContentText( contentText ); // Add the custom content text
        alert.initStyle( StageStyle.UNDECORATED ); // Remove the menu bar of the alert

        final DialogPane dialog = alert.getDialogPane(); // Will be used for styling the alert
        dialog.getStylesheets().add( getClass().getResource("/com/college/casestudy/style/dialog.css").toString() );
        dialog.getStyleClass().add( "dialog" ); // Add the [dialog] css class

        alert.showAndWait();
    }

    public void showWarningDialog( String headerText, String contentText ) {
        final Alert alert = new Alert( Alert.AlertType.WARNING ); // Set the alert type to warning
        alert.setHeaderText( headerText ); // Add the custom header text
        alert.setContentText( contentText ); // Add the custom content text
        alert.initStyle( StageStyle.UNDECORATED ); // Remove the menu bar of the alert

        final DialogPane dialog = alert.getDialogPane(); // Will be used for styling the alert
        dialog.getStylesheets().add( getClass().getResource("/com/college/casestudy/style/dialog.css").toString() );
        dialog.getStyleClass().add( "dialog" ); // Add the [dialog] css class

        alert.showAndWait();
    }

    public Optional<ButtonType> showConfirmationDialog( String contentText ) {
        final Alert alert = new Alert( Alert.AlertType.CONFIRMATION ); // Set the alert type to confirmation
        alert.setHeaderText( "Confirmation Required" );
        alert.setContentText( contentText ); // Add the custom content text
        alert.initStyle( StageStyle.UNDECORATED ); // Remove the menu bar of the alert

        final DialogPane dialog = alert.getDialogPane(); // Will be used for styling the alert
        dialog.getStylesheets().add( getClass().getResource("/com/college/casestudy/style/dialog.css").toString() );
        dialog.getStyleClass().add( "dialog" ); // Add the [dialog] css class

        // Return since this is a confirmation
        // dialog and needed to determine what
        // the user chose
        return alert.showAndWait();
    }
}
