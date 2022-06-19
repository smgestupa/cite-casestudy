package com.college.casestudy.controller;

import com.college.casestudy.model.Student;
import com.college.casestudy.model.parent.Account;
import com.college.casestudy.components.DialogComponent;
import com.college.casestudy.model.Teacher;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class AccessViewController implements Initializable {

    // Access screen
    @FXML private BorderPane BP_accessScreen;
    private double xOffset; // Access screen X position
    private double yOffset; // Access screen Y position

    // Access view pages
    @FXML private HBox HB_accessPage; // Welcome page
    @FXML private AnchorPane AP_loginPage; // Login page
    @FXML private VBox VB_registerStudentPage, VB_registerTeacherPage; // Register pages

    // Login page fields & inputs
    @FXML private List<TextField> loginInputs; // Store text fields for ease of use
    @FXML private TextField TF_usernameLogin, TF_codeLogin;
    @FXML private PasswordField PF_passwordLogin;

    // Register page fields & inputs (Student)
    @FXML private List<TextField> studentRegisterInputs; // Store text fields for ease of use
    @FXML private TextField TF_firstNameStudent,
            TF_lastNameStudent, TF_programStudent,
            TF_yearLevelStudent, TF_usernameStudent,
            TF_studentCode;
    @FXML private PasswordField PF_passwordStudent;
    @FXML private ComboBox<String> CB_campusStudent;

    // Register page fields & inputs (Teacher)
    @FXML private List<TextField> teacherRegisterInputs; // Store text fields for ease of use
    @FXML private TextField TF_firstNameTeacher,
            TF_lastNameTeacher, TF_collegeTeacher,
            TF_usernameTeacher,TF_teacherCode;
    @FXML private PasswordField PF_passwordTeacher;
    @FXML private ComboBox<String> CB_campusTeacher;

    // List of accounts
    private Queue<Account> accounts;

    // Application Components
    final private DialogComponent dialog = new DialogComponent();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Instantiate [accounts] list;
        // will be used for storing
        // registered accounts
        accounts = new ArrayDeque<>();

        // Pre-made accounts
        accounts.add( new Teacher( 69696, "testingJohnny", "21412", "johnny", "beans", "CITE","Manila" ) );
        accounts.add( new Student( 213125, "newAccountLmao", "5621", "Test", "Lmaooao", 2,"Quezon City", "Manila" ) );
        accounts.add( new Student( 1245, "personB", "pErSoNbee", "Person", "B", 3,"BS Computer Science", "Manila" ) );
        accounts.add( new Teacher( 2011358, "TeacherF", "1234", "Johnny", "Beans", "CITE","Manila" ) );
        accounts.add( new Teacher( 2011359, "TeacherA", "5673", "Mark", "Villafuente", "CITE","Manila" ) );
        accounts.add( new Teacher( 2011360, "TeacherB", "4173", "John", "Marshal", "GEC","Quezon" ) );
        accounts.add( new Teacher( 2011361, "TeacherC", "026123", "Anthony", "Manzano", "MATH","Quezon" ) );
        accounts.add( new Teacher( 2011363, "TeacherE", "21412",  "Johnny",   "Bravo", "CITE","Quezon" ) );
        accounts.add( new Student( 2021002, "PersonB", "PERSONA", "Eru",   "Chitanda", 4,"BS Civil Engineering", "Quezon" ) );
        accounts.add( new Student( 2021003, "PersonC", "reeeeee", "Gojou",   "Satoru", 3,"BS Computer Science", "Manila" ) );
        accounts.add( new Student( 2021004, "PersonD", "raaaaaa", "Eru",   "Nakano", 1,"BS Computer Science", "Manila" ) );
        accounts.add( new Student( 2021005, "PersonE", "1234", "Nino", "Nakano", 1,"BS Industrial Engineering", "Manila" ) );
        accounts.add( new Student( 2021006, "PersonF", "12134", "Itsuki", "Nakano", 3,"BS Industrial Engineering", "Quezon" ) );
        accounts.add( new Student( 2021007, "PersonG", "24124", "Andrei", "Barles", 4,"BS Accounting", "Manila" ) );
        accounts.add( new Student( 2021008, "PersonH", "56234", "Miku", "Nakano", 1,"BS Civil Engineering", "Quezon" ) );
        accounts.add( new Student( 2021009, "PersonI", "96712", "Karl", "Marx", 2,"BS Computer Science", "Manila" ) );

        // Store all inputs from login page
        // for convenience and efficiency
        loginInputs = Arrays.asList( TF_usernameLogin, TF_codeLogin, PF_passwordLogin );

        // Do the same when storing inputs from
        // the login page
        studentRegisterInputs = Arrays.asList( TF_firstNameStudent, TF_lastNameStudent,
                                                TF_programStudent, TF_yearLevelStudent,
                                                TF_usernameStudent, TF_studentCode,
                                                PF_passwordStudent );

        // Do the same when storing inputs from
        // the login page
        teacherRegisterInputs = Arrays.asList( TF_firstNameTeacher, TF_lastNameTeacher,
                                                TF_collegeTeacher, TF_usernameTeacher,
                                                TF_teacherCode, PF_passwordTeacher );

        // Initialize Combo Box options
        final String[] campusLoc = { "Manila", "Quezon City" }; // Campus locations

        CB_campusStudent.setItems( FXCollections.observableArrayList( campusLoc ) ); // For register (student) page
        CB_campusStudent.setPromptText( campusLoc[0] );

        CB_campusTeacher.setItems( FXCollections.observableArrayList( campusLoc ) ); // For register (teacher) page
        CB_campusTeacher.setPromptText( campusLoc[0] );
    }

    public void initData( Queue<Account> accounts ) {
        this.accounts = accounts;
    }


    /**
     * Pages related methods
     */

    @FXML
    protected void toAccessPage() {
        HB_accessPage.toFront(); // Set the access page to front
    }

    @FXML
    protected void toLoginPage() {
        // Check if [error.css] is applied, if it is
        // we will remove it to reset the style
        final String errorCSS = this.getClass().getResource("/com/college/casestudy/style/errors.css").toExternalForm();
        if ( AP_loginPage.getStylesheets().contains( errorCSS ) ) AP_loginPage.getStylesheets().removeAll( errorCSS );

        // Reset all fields & inputs to blank
        for ( final TextField field : loginInputs ) {
            field.setText( "" );
        }

        AP_loginPage.toFront(); // Set the login page to front
    }

    @FXML
    protected void toRegisterStudentPage() {
        // Check if [error.css] is applied, if it is
        // we will remove it to reset the style
        final String errorCSS = this.getClass().getResource("/com/college/casestudy/style/errors.css").toExternalForm();
        if ( VB_registerStudentPage.getStylesheets().contains( errorCSS ) ) VB_registerStudentPage.getStylesheets().removeAll( errorCSS );

        // Reset all fields & inputs to blank
        for ( final TextField field : studentRegisterInputs ) {
            field.setText( "" );
        }
        CB_campusStudent.getSelectionModel().select( 0 ); // Reset to first choice / index 0

        VB_registerStudentPage.toFront(); // Set the register (student) page to front
    }

    @FXML
    protected void toRegisterTeacherPage() {
        // Check if [error.css] is applied, if it is
        // we will remove it to reset the style
        final String errorCSS = this.getClass().getResource("/com/college/casestudy/style/errors.css").toExternalForm();
        if ( VB_registerTeacherPage.getStylesheets().contains( errorCSS ) ) VB_registerTeacherPage.getStylesheets().removeAll( errorCSS );

        // Reset all fields & inputs to blank
        for ( final TextField field : teacherRegisterInputs ) {
            field.setText( "" );
        }
        CB_campusTeacher.getSelectionModel().select( 0 ); // Reset to first choice / index 0

        VB_registerTeacherPage.toFront(); // Set the register (teacher) page to front
    }


    /**
     * Login page-related methods
     */

    @FXML
    void onLogin() {
        boolean inputsFilled = true; // Assume all inputs are filled
        final String errorCSS = this.getClass().getResource("/com/college/casestudy/style/errors.css").toExternalForm();

        for ( final TextField input : loginInputs ) {
            if ( input.getText().trim().isEmpty() ) { // Check if this input is not filled
                inputsFilled = false; // Turn to false since this input isn't filled
                AP_loginPage.getStylesheets().addAll( errorCSS ); // Add the [error.css]
                input.getStyleClass().add( "input-field-missing" ); // Add the css class for unfilled fields
            } else input.getStyleClass().removeAll( "input-field-missing" ); // If filled, then remove the css class just in case
        }

        if ( inputsFilled ) checkBeforeLogin(); // If inputs are filled, check first
    }

    @FXML
    void checkBeforeLogin() {
        // If [accounts] has no data, then
        // that means no accounts have been
        // created yet
        if ( accounts.size() == 0 ) {
            // Show a warning dialog
            dialog.showWarningDialog( "No user data found", "You should register instead, it's easy!" );
            return;
        } else {
            final int accessCode = Integer.parseInt( TF_codeLogin.getText().trim() );
            final String username = TF_usernameLogin.getText().trim();
            final String password = PF_passwordLogin.getText().trim();

            // Loop to check if account exists
            for ( final Account account : accounts ) {
                final Object[] accountInfo = account.getAccountInfo(); // Store the object array for convenience and efficiency
                if ( ( int ) accountInfo[1] == accessCode ) { // If exists
                    if ( accountInfo[2].equals( username ) && accountInfo[3].equals( password ) ) { // Check if username and password match
                        loginSuccess( accountInfo ); // Show contents of array if match
                        return;
                    }
                }
            }
        }

        // Show a warning dialog if
        // no credentials don't match
        dialog.showWarningDialog( "Username, password or access code is invalid", "You should register instead, it's easy!" );
    }

    @FXML
    void loginSuccess( Object[] accountInfo ) {
        final boolean student = ( boolean ) accountInfo[0]; // Check if the account is student

        try {
            final Stage currStage = (Stage) BP_accessScreen.getScene().getWindow(); // Get current stage
            // Load [dashboard-view.fxml] file
            final FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/com/college/casestudy/view/dashboard-view.fxml") );
            final Scene newScene = new Scene( fxmlLoader.load() ); // Set the scene, once [dashboard-view.fxml] file was loaded

            // We need to get the controller,
            // in order to initialize/pass
            // data to another controller
            final DashboardViewController newSceneController = fxmlLoader.getController(); // Get the controller of the new scene
            newSceneController.initData( accounts, accountInfo ); // Pass [accounts] and [accountInfo] variables for initialization

            currStage.setScene( newScene ); // Switch the current scene, with the new scene
            currStage.centerOnScreen(); // Center the new scene
            currStage.show(); // Show the new scene
        } catch ( Exception err ) {
            err.printStackTrace(); // Verbose error stack trace
            System.err.println( "Warning! An error occurred in loginSuccess() method: " + err.getMessage() ); // Summary error message
        }

        if ( student ) printOutStudentArray( accountInfo );
        else printOutTeacherArray( accountInfo ); // If not student, then print out teacher
    }

    @FXML
    void printOutStudentArray( Object[] accountInfo ) {
        System.out.println();
        System.out.println( "\t\t--------------------------------------------------" );
        System.out.println( "\t\t|                                                |" );
        System.out.println( "\t\t|            Student Data Information            |" );
        System.out.println( "\t\t|                                                |" );
        System.out.println( "\t\t--------------------------------------------------" );
        System.out.println();

        System.out.println( "\t\t\t\t-=-  -=-  -=-  -=-  -=-  -=-  -=-" );

        System.out.println();
        System.out.println( "\t\t> First Name: " + accountInfo[4] );
        System.out.println( "\t\t> Last Name: " + accountInfo[5] );
        System.out.println( "\t\t> Student Code: " + accountInfo[1] );
        System.out.println( "\t\t> Year Level: " + accountInfo[6] );
        System.out.println( "\t\t> Program: " + accountInfo[7] );
        System.out.println( "\t\t> Campus: " + accountInfo[8] );

        System.out.println();
    }

    @FXML
    void printOutTeacherArray( Object[] accountInfo ) {
        System.out.println();
        System.out.println( "\t\t--------------------------------------------------" );
        System.out.println( "\t\t|                                                |" );
        System.out.println( "\t\t|            Teacher Data Information            |" );
        System.out.println( "\t\t|                                                |" );
        System.out.println( "\t\t--------------------------------------------------" );
        System.out.println();

        System.out.println( "\t\t\t\t-=-  -=-  -=-  -=-  -=-  -=-  -=-" );

        System.out.println();
        System.out.println( "\t\t> First Name: " + accountInfo[4] );
        System.out.println( "\t\t> Last Name: " + accountInfo[5] );
        System.out.println( "\t\t> Teacher Code: " + accountInfo[1] );
        System.out.println( "\t\t> College: " + accountInfo[6] );
        System.out.println( "\t\t> Campus: " + accountInfo[7] );

        System.out.println();
    }

    /**
     * Register (student) page-related methods
     */

    @FXML
    void onRegisterStudent() {
        boolean inputsFilled = true; // Assume all inputs are filled
        final String errorCSS = this.getClass().getResource("/com/college/casestudy/style/errors.css").toExternalForm();

        // Loop to check if an input isn't filled
        for ( final TextField input : studentRegisterInputs ) {
            if ( input.getText().trim().isEmpty() ) { // Check if this input is not filled
                inputsFilled = false; // Turn to false since this input isn't filled
                VB_registerStudentPage.getStylesheets().addAll( errorCSS ); // Add the [error.css] file
                input.getStyleClass().add( "input-field-missing" ); // Add the css class for unfilled fields
            } else input.getStyleClass().removeAll( "input-field-missing" ); // If filled, then remove the css class just in case
        }

        if ( inputsFilled ) checkBeforeRegister( true ); // If inputs are filled, check before registering
    }

    @FXML
    void onRegisterTeacher() {
        boolean inputsFilled = true; // Assume all inputs are filled
        final String errorCSS = this.getClass().getResource("/com/college/casestudy/style/errors.css").toExternalForm();

        // Loop to check if an input isn't filled
        for ( final TextField input : teacherRegisterInputs ) {
            if ( input.getText().trim().isEmpty() ) { // Check if this input is not filled
                inputsFilled = false; // Turn to false since this input isn't filled
                VB_registerTeacherPage.getStylesheets().addAll( errorCSS ); // Add the [error.css] file
                input.getStyleClass().add( "input-field-missing" ); // Add the css class for unfilled fields
            } else input.getStyleClass().removeAll( "input-field-missing" ); // If filled, then remove the css class just in case
        }

        if ( inputsFilled ) checkBeforeRegister( false ); // If inputs are filled, check before registering
    }


    /**
     * General register page-related methods
     */

    @FXML
    void checkBeforeRegister( boolean student ) {
        if ( student ) checkForStudent(); // If student, check for student
        else checkForTeacher(); // If not, then check for teacher
    }

    @FXML
    void checkForStudent() {
        boolean isDuplicate = false; // Assume the account is not a duplicate
        final int studentCode = Integer.parseInt( TF_studentCode.getText().trim() ); // For checking and adding

        // If an account exist, check
        // for duplicated info
        if ( accounts.size() != 0 ) {
            // Loop to check for any duplications
            for ( final Account account : accounts ) {
                if ( ( int ) account.getAccountInfo()[ 1 ] == studentCode ) { // We need access code to be unique
                    isDuplicate = true;
                    break; // Stop the loop if there is a duplicate
                }
            }
        }

        if ( isDuplicate ) {
            // If duplicate, show a
            // warning dialog
            dialog.showWarningDialog( "Duplicate account found", "You should log in instead." );
            return;
        }

        // Store values from input & fields to variables for clarity
        final String username = TF_usernameStudent.getText().trim();
        final String password = PF_passwordStudent.getText();
        final String firstName = TF_firstNameStudent.getText().trim();
        final String lastName = TF_lastNameStudent.getText().trim();
        final int yearLevel = Integer.parseInt( TF_yearLevelStudent.getText().trim() );
        final String program = TF_programStudent.getText().trim();
        final String campus = CB_campusStudent.getSelectionModel().getSelectedItem();

        // If no duplicate, add a Student model to
        // the Account list object
        accounts.add( new Student( studentCode, username,
                password, firstName, lastName,
                yearLevel, program, campus) );

        // Show an information dialog if
        // new Student model was added
        dialog.showInformationDialog( "Student account created successfully", "You can now log in with your credentials." );

        // Go to login page after clicking
        // ok to the dialog
        toLoginPage();
    }

    @FXML
    void checkForTeacher() {
        boolean isDuplicate = false; // Assume the account is not a duplicate
        final int teacherCode = Integer.parseInt( TF_teacherCode.getText().trim() ); // For checking and adding

        // If an account exist, check
        // for duplicated info
        if ( accounts.size() != 0 ) {
            // Loop to check for any duplications
            for ( final Account account : accounts ) {
                if ( ( int ) account.getAccountInfo()[ 1 ] == teacherCode ) { // We need access code to be unique
                    isDuplicate = true;
                    break; // Stop the loop if there is a duplicate
                }
            }
        }

        if ( isDuplicate ) {
            // If duplicate, show a
            // warning dialog
            dialog.showWarningDialog( "Duplicate account found", "You should log in instead." );
            return;
        }

        // Store values from input & fields to variables for clarity
        final String username = TF_usernameTeacher.getText().trim();
        final String password = PF_passwordTeacher.getText();
        final String firstName = TF_firstNameTeacher.getText().trim();
        final String lastName = TF_lastNameTeacher.getText().trim();
        final String college = TF_collegeTeacher.getText().trim();
        final String campus = CB_campusTeacher.getSelectionModel().getSelectedItem();

        accounts.add( new Teacher( teacherCode, username,
                password, firstName, lastName,
                college, campus ) );

        dialog.showInformationDialog( "Teacher account created successfully", "You can now log in with your credentials." );

        // Go to login page after clicking
        // ok to the dialog
        toLoginPage();
    }


    /**
     * Menu bar-related methods
     */

    @FXML
    void onCloseProgram() {
        // Will be used to get the user's
        // choice when shown with the
        // confirmation dialog
        final Optional<ButtonType> result = dialog.showConfirmationDialog( "You are about to close the program." );
        if ( result.get() == ButtonType.OK ) System.exit(0); // If the player confirms, then exit the program
    }

    @FXML
    void onMinimizeProgram() {
        final Stage stage = ( Stage ) BP_accessScreen.getScene().getWindow(); // Get the access screen window
        stage.setIconified( true ); // Minimize the access screen window
    }

    @FXML
    void onClickMenuBar( MouseEvent event ) { // Is used alongside onDragMenuBar() for dragging the window through the custom menu bar
        xOffset = BP_accessScreen.getScene().getWindow().getX() - event.getScreenX();
        yOffset = BP_accessScreen.getScene().getWindow().getY() - event.getScreenY();
    }

    @FXML
    void onDragMenuBar( MouseEvent event ) { // Is used alongside onClickMenuBar() for dragging the window through the custom title bar
        BP_accessScreen.getScene().getWindow().setX( event.getScreenX() + xOffset );
        BP_accessScreen.getScene().getWindow().setY( event.getScreenY() + yOffset );
    }
}