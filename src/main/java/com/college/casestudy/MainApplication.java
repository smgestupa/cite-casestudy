package com.college.casestudy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start( Stage stage ) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader( MainApplication.class.getResource( "view/access-view.fxml" ) );
        Scene scene = new Scene( fxmlLoader.load() );
        stage.setScene( scene );
        stage.initStyle( StageStyle.UNDECORATED ); // Remove the menu bar
        stage.setResizable( false ); // Disables window resizability
        stage.centerOnScreen(); // Centers the program
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}