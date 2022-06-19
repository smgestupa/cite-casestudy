package com.college.casestudy.controller;

import com.college.casestudy.components.DialogComponent;
import com.college.casestudy.controller.grid.GridModelController;
import com.college.casestudy.listener.Listen;
import com.college.casestudy.model.Node;
import com.college.casestudy.model.parent.Account;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class DashboardViewController implements Initializable {

    // Dashboard screen
    @FXML private BorderPane BP_dashboardScreen;
    private double xOffset; // Access screen X position
    private double yOffset; // Access screen Y position

    // Labels
    @FXML private Label L_fullName, L_class;

    // ImageView
    @FXML private ImageView IV_accountImage, IV_noResults, IV_loading;

    // Search field
    @FXML private TextField TF_searchProfile;

    // Combo boxes & choices
    final private ObservableList<String> classification = FXCollections.observableArrayList( "All", "Student", "Teacher" );
    final private ObservableList<String> algorithm = FXCollections.observableArrayList( "Unsorted", "Access Code", "First Name", "Last Name", "Pre-order", "Post-order" );

    @FXML private ComboBox<String> CB_sortByClassification, CB_sortByAlgorithm;

    // Grid
    @FXML private GridPane GP_grid;

    // Loading view
    @FXML private VBox VB_loadingLeft;

    // Account information panel
    @FXML private StackPane SP_accountInformation;
    @FXML private Label L_accInfoClass, L_accInfoAccessCode,
                        L_accInfoFirstName, L_accInfoLastName,
                        L_accInfoIndex6, L_accInfoIndex6Value,
                        L_accInfoIndex7, L_accInfoIndex7Value,
                        L_accInfoStudentCampus, L_accInfoStudentCampusValue;

    // Initialized data
    private Queue<Account> accounts;
    private List<Account> savedAccountsList; // This will be used to manipulate grid results, instead of [accounts]
    private LinkedList<Object> accountInfo; // Current user account information as a LinkedList object
    private Node root = null; // Will be used for Binary Tree traversal sorting

    // Listener
    private Listen listener; // Used to listen for MouseEvents from a grid model

    // Application Components
    final private DialogComponent dialog = new DialogComponent();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize choices of ComboBox objects
        CB_sortByClassification.getItems().addAll( classification );
        CB_sortByAlgorithm.getItems().addAll( algorithm );

        // Always select the first index
        CB_sortByClassification.getSelectionModel().select( 0 );
        CB_sortByAlgorithm.getSelectionModel().select( 0 );

        // FXMLLoader loads this method before
        // [initData()], which is why we need to
        // wrap this with [Platform.runLater()]
        Platform.runLater( () -> {
            L_fullName.setText( accountInfo.get( 4 ).toString() + " " + accountInfo.get( 5 ).toString() );
            L_class.setText( (boolean) accountInfo.get( 0 ) ? "STUDENT" : "TEACHER" );

            // Change image depending on account classification
            if ( (boolean) accountInfo.get( 0 ) ) {
                final Image studentIcon = new Image( getClass().getResource("/com/college/casestudy/assets/profile/student-icon.png").toString() );
                IV_accountImage.setImage( studentIcon );
            } else {
                final Image teacherIcon = new Image( getClass().getResource("/com/college/casestudy/assets/profile/teacher-icon.png").toString() );
                IV_accountImage.setImage( teacherIcon );
            }

            // Instantiate saved accounts ArrayList
            savedAccountsList = new ArrayList<>( accounts );


            // Initialize the models inside the grid
            initModel();
            VB_loadingLeft.setVisible( false ); // Hide the loading icon on the left side
        } );
    }

    public void initData( Queue<Account> accounts, Object[] accountInfo ) {
        this.accounts = accounts;
        this.accountInfo = new LinkedList<>( Arrays.asList( accountInfo ) );
    }


    /**
     *  General methods
     */

    public AccessViewController logoutFromDashboard() {
        try {
            final Stage currStage = (Stage) BP_dashboardScreen.getScene().getWindow(); // Get current stage
            // Load [access-view.fxml] file
            final FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/com/college/casestudy/view/access-view.fxml") );
            final Scene newScene = new Scene( fxmlLoader.load() ); // Set the scene, once [access-view.fxml] file was loaded

            final AccessViewController accessViewController = fxmlLoader.getController(); // Get this .fxml file's controller

            final Queue<Account> newAccountsList = new ArrayDeque<>( accounts ); // Create a new Queue object, for re-initialization of [accounts] object

            // Re-initialize [accounts] Queue object
            // in the access view screen
            accessViewController.initData( newAccountsList );

            currStage.setScene( newScene ); // Switch the current scene, with the new scene
            currStage.centerOnScreen(); // Center the new scene
            currStage.show(); // Show the new scene

            return accessViewController;
        } catch ( Exception err ) {
            err.printStackTrace(); // Verbose error stack trace
            System.err.println( "Warning! An error occurred in logoutFromDashboard() method: " + err.getMessage() ); // Summary error message
        }

        return null;
    }

    public void closeAccInfoPanel() {
        SP_accountInformation.setVisible( false );
    }

    public void deleteMyAccount() {
        final int accessCode = (int) accountInfo.get( 1 ); // Get the current account's access code

        // Will be used to get the user's
        // choice when shown with the
        // confirmation dialog
        final Optional<ButtonType> result = dialog.showConfirmationDialog( "You are about to delete your account." );
        if ( result.get() == ButtonType.OK ) { // If the player confirms to delete, then remove the specific account
            final AccessViewController accessViewController = logoutFromDashboard(); // Get this .fxml file's controller

            final Queue<Account> newAccountsList = new ArrayDeque<>(); // Create a new Queue object, for re-initialization of [accounts] object
            for ( Account account : accounts ) { // Loop for each account
                if ( (int) account.getAccountInfo()[1] == accessCode ) continue; // Skip if the account has the same access code
                newAccountsList.add( account ); // Add the account
            }

            // Re-initialize [accounts] Queue object
            // in the access view screen
            accessViewController.initData( newAccountsList );

            logoutFromDashboard(); // Go back to access view
            dialog.showInformationDialog( "Account successfully deleted", "If you want to log-in, please register again." );
        }
    }


    /**
     *  Initialize-related methods
     */

    public void initModel() { // Initialize account models to grid
        IV_loading.setVisible( true ); // Show the loading icon
        GP_grid.getChildren().clear(); // Clear the grid, just in case

        // Check if the search field is empty,
        // then we add all elements of [accounts]
        // to the [savedAccountsList] variable
        if ( TF_searchProfile.getText().trim().isEmpty() ) savedAccountsList = new ArrayList<>( accounts );
        final List<Account> resultingAccounts = new ArrayList<>( savedAccountsList );


        // Asynchronously load grid models
        Thread asyncThread = new Thread( () -> {
            final long firstRecord = System.currentTimeMillis();

            // Sort by classification
            sortByClassification( resultingAccounts );

            // Check if no results found, before
            // sorting by algorithm for efficiency
            if ( resultingAccounts.size() == 0 ) {
                IV_noResults.setVisible( true );
                IV_loading.setVisible( false );
                return; // Stop this method from running
            }

            // Sort by algorithm
            sortByAlgorithm( resultingAccounts );

            // Initialize listener
            listener = new Listen() {
                @Override
                public void onClickListener( MouseEvent event, Account account ) {
                    if ( event.getButton().equals( MouseButton.PRIMARY ) ) {
                        if (event.getClickCount() == 2 ) { // If the user double-clicked on a grid model
                            // Store all account information in an
                            // object array for convenience
                            final Object[] accountInfo = account.getAccountInfo();

                            // Set the label values for non-independent indexes
                            L_accInfoAccessCode.setText( accountInfo[1].toString() );
                            L_accInfoFirstName.setText( accountInfo[4].toString() );
                            L_accInfoLastName.setText( accountInfo[5].toString() );

                            // Check if student or teacher
                            if ( (boolean) accountInfo[0] ) { // Set necessary attributes if the account is a student
                                // Set the label class for student
                                L_accInfoClass.setText( "STUDENT" );

                                // Set the labels and values based on index 6
                                L_accInfoIndex6.setText( "Year Level" );
                                L_accInfoIndex6Value.setText( accountInfo[6].toString() );

                                // Set the labels and values based on index 6
                                L_accInfoIndex7.setText( "Program" );
                                L_accInfoIndex7Value.setText( accountInfo[7].toString() );

                                // Set the value for student campus
                                L_accInfoStudentCampus.setVisible( true ); // Make the campus label visible, just in case
                                L_accInfoStudentCampusValue.setVisible( true ); // Make this also visible
                                L_accInfoStudentCampusValue.setText( accountInfo[8].toString() );
                            } else { // else, set necessary attributes for teachers
                                // Set the class for teacher
                                L_accInfoClass.setText( "TEACHER" );

                                // Set the labels and values based on index 6
                                L_accInfoIndex6.setText( "College" );
                                L_accInfoIndex6Value.setText( accountInfo[6].toString() );

                                // Set the labels and values based on index 6
                                L_accInfoIndex7.setText( "Campus" );
                                L_accInfoIndex7Value.setText( accountInfo[7].toString() );

                                // Hide this to avoid the duplication of
                                // campus label
                                L_accInfoStudentCampus.setVisible( false );
                                L_accInfoStudentCampusValue.setVisible( false );
                            }

                            SP_accountInformation.setVisible( true ); // Show the account information panel
                        }
                    }
                }
            };

            // Initialize grid
            Platform.runLater( () -> initGrid( resultingAccounts ) );
            System.out.println( "\n\t\t Grid initialization run-time: " + ( System.currentTimeMillis() - firstRecord ) + "ms\n" );
        } );

        asyncThread.setDaemon( true );
        asyncThread.start(); // Start the async thread
    }

    private void initGrid( List<Account> resultingAccounts ) {
        int column = 0; // Grid current column
        int row = 1; // Grid current row

        try {
            for ( Account account : resultingAccounts ) {
                final FXMLLoader modelLoader = new FXMLLoader(); // Will be used to load [grid-model.fxml]
                modelLoader.setLocation( getClass().getResource("/com/college/casestudy/grid_component/grid-model.fxml") );

                final VBox accountModel = modelLoader.load(); // Load the model

                final GridModelController gridModelController = modelLoader.getController(); // Get the model controller
                gridModelController.initData( account, listener ); // Initialize data inside the model, and pass [listener]

                // If the current column is filled,
                // reset and move to next row
                if ( column == 4 ) {
                    column = 0;
                    row++;
                }

                // Initialize this grid model
                GP_grid.add( accountModel, column++, row );
                GP_grid.setMinWidth( Region.USE_COMPUTED_SIZE );
                GP_grid.setPrefWidth( Region.USE_COMPUTED_SIZE );
                GP_grid.setMaxWidth( Region.USE_PREF_SIZE );

                GP_grid.setMinHeight( Region.USE_COMPUTED_SIZE );
                GP_grid.setPrefHeight( Region.USE_COMPUTED_SIZE );
                GP_grid.setMaxHeight( Region.USE_PREF_SIZE );

                // Add margin to all side of this grid model
                GridPane.setMargin( accountModel, new Insets( 14 ) );
            }
        } catch ( Exception err ) {
            err.printStackTrace();
            System.err.println( "Warning! An error occurred in initGrid() method: " + err.getMessage() ); // Summary error message
        }

        IV_noResults.setVisible( false ); // Hide the no results icon
        IV_loading.setVisible( false ); // Hide the loading icon
    }

    private void initTree( List<Account> referenceList ) {
        root = null; // Clear the root binary tree

        // Add every existing accounts into the Binary Tree
        for ( Account account : referenceList ) {
            root = addNode( root, account );
        }
    }

    private Node addNode( Node current, Account account ) {
        if ( current == null ) return new Node( account );

        // Use the access codes for comparisons
        final int newValue = (int) account.getAccountInfo()[1];
        final int currentValue = (int) current.value.getAccountInfo()[1];

        // If the new value is less than, place it on the left node
        if ( newValue < currentValue ) current.left = addNode( current.left, account );
        else if ( newValue > currentValue ) current.right = addNode( current.right, account ); // If bigger, then on the right node

        // If same, then return the current node
        return current;
    }


    /**
     *  Sorting methods
     */

    public void searchQuery() {
        // Will be used as basis for regex
        final String keywordRegex = TF_searchProfile.getText().trim().toLowerCase().replaceAll( "( +)", "|" );
        final Pattern separateKeywords = Pattern.compile( keywordRegex ); // Used to find pattern from a regex

        // Clear [savedAccountsList] to get new results
        savedAccountsList.clear();

        // Check if the search field is empty,
        // then copy the elements from [accounts]
        // instead, for efficiency
        if ( keywordRegex.isEmpty() ) {
            savedAccountsList.addAll( accounts );
        } else {
            accounts.forEach( account -> {
                final Object[] accountInfo = account.getAccountInfo();
                final boolean isStudent = ( boolean ) accountInfo[0];

                // Will be used for regexp finding
                final boolean accessCodeMatch = separateKeywords.matcher( accountInfo[1].toString().toLowerCase() ).find();
                final boolean firstNameMatch = separateKeywords.matcher( accountInfo[4].toString().toLowerCase() ).find();
                final boolean lastNameMatch = separateKeywords.matcher( accountInfo[5].toString().toLowerCase() ).find();
                final boolean collegeMatch = !isStudent && separateKeywords.matcher( accountInfo[6].toString().toLowerCase() ).find();
                final boolean programMatch = isStudent && separateKeywords.matcher( accountInfo[7].toString().toLowerCase() ).find();

                // Use if-condition to satisfy both
                // Student and Teacher classes
                final boolean campusMatch;
                if ( isStudent ) {
                    // Use [.matches()], since regexp can find
                    // access codes without complete, making this
                    // algorithm inefficient
                    campusMatch = separateKeywords.matcher( accountInfo[8].toString().toLowerCase() ).matches();
                } else campusMatch = separateKeywords.matcher( accountInfo[6].toString().toLowerCase() ).matches();

                // If a match is found, add it onto the fresh
                // [savedAccountsList] list
                if ( accessCodeMatch || firstNameMatch || lastNameMatch || collegeMatch || programMatch || campusMatch )
                    savedAccountsList.add( account );
            } );
        }

        // Check if no results found, before
        // initializing the model to save time
        if ( savedAccountsList.size() == 0 ) {
            GP_grid.getChildren().clear(); // Clear grid
            IV_noResults.setVisible( true );
            IV_loading.setVisible( false );
            return; // Stop this method from running
        }

        initModel(); // Initialize model
    }

    private void sortByClassification( List<Account> referenceList ) {
        // Store the index to a variable for
        // readability
        final int sortByClassificationIndex = CB_sortByClassification.getSelectionModel().getSelectedIndex();

        // Check sort by classification
        if ( sortByClassificationIndex == 1 ) // Show only student models
            referenceList.removeIf( account -> !(boolean) account.getAccountInfo()[0] ); // Remove if teacher
        else if ( sortByClassificationIndex == 2 ) // Show only teacher models
            referenceList.removeIf( account -> (boolean) account.getAccountInfo()[0] ); // Remove if student
    }

    private void sortByAlgorithm( List<Account> referenceList ) {
        // Store the index to a variable for
        // readability
        final int sortByAlgorithmIndex = CB_sortByAlgorithm.getSelectionModel().getSelectedIndex();

        // Check sort by algorithm
        switch ( sortByAlgorithmIndex ) {
            case 1:
                radixSort( referenceList, referenceList.size(), getMaxValue( referenceList ) );
                break;
            case 2:
                bubbleSort( referenceList, referenceList.size(), 4 );
                break;
            case 3:
                bubbleSort( referenceList, referenceList.size(), 5 );
                break;
            case 4:
                initTree( referenceList ); // Initialize binary tree
                preOrder( referenceList );
                break;
            case 5:
                initTree( referenceList ); // Initialize binary tree
                postOrder( referenceList );
                break;
            default:
                break;
        }
    }

    private int getMaxValue( List<Account> referenceList ) {
        int maxValue = 0; // Create a temporary value variable

        for ( Account account : referenceList ) { // Loop to determine what is the maximum value in the List object
            final int value = (int) account.getAccountInfo()[1]; // Get the access code value
            if ( value > maxValue ) maxValue = value; // If greater than [maxValue], set the new value
        }

        return maxValue;
    }

    private void bubbleSort( List<Account> referenceList, int arraySize, int sortIndex ) {
        final long firstRecord = System.currentTimeMillis();

        // Use the bubble sort algorithm for
        // sorting strings
        // Time Complexity: O(n^2)
        for ( int x = 0; x < arraySize; x++ ) {
            for ( int y = 0; y < arraySize - 1; y++ ) {
                final String leftValue = referenceList.get( y ).getAccountInfo()[ sortIndex ].toString().toLowerCase();
                final String rightValue = referenceList.get( y + 1 ).getAccountInfo()[ sortIndex ].toString().toLowerCase();
                if ( leftValue.compareTo( rightValue ) > 0 ) { // Check if left value is greater than the right
                    final Account tempAccount = referenceList.get( y );
                    referenceList.set( y, referenceList.get( y + 1 ) );
                    referenceList.set( y + 1, tempAccount );
                }
            }
        }

        System.out.println( "\n\t\t Bubble Sort algorithm run-time: " + ( System.currentTimeMillis() - firstRecord ) + "ms\n" );
    }

    private void radixSort( List<Account> referenceList, int arraySize, int maxValue ) {
        final long firstRecord = System.currentTimeMillis();

        // Use the radix sort algorithm for
        // sorting integers
        // Time Complexity: O(nd)
        for ( int exp = 1; maxValue / exp > 0; exp *= 10 ) {
            countSort( referenceList, arraySize, exp );
        }

        System.out.println( "\n\t\t Radix Sort algorithm run-time: " + ( System.currentTimeMillis() - firstRecord ) + "ms\n" );
    }

    private void countSort( List<Account> referenceList, int arraySize, int exp ) {
        final Account[] result = new Account[ arraySize ];
        final int[] count = new int[10];

        for ( int i = 0; i < arraySize; i++ ) {
            count[ ( (int) referenceList.get( i ).getAccountInfo()[1] / exp ) % 10 ]++;
        }

        for ( int i = 1; i < 10; i++ ) {
            count[ i ] += count[ i - 1 ];
        }

        for ( int i = arraySize - 1; i >= 0; i-- ) {
            result[ count[ ( (int) referenceList.get( i ).getAccountInfo()[1] / exp ) % 10 ] - 1 ] = referenceList.get( i );
            count[ ( (int) referenceList.get( i ).getAccountInfo()[1] / exp ) % 10 ]--;
        }

        referenceList.clear();
        referenceList.addAll( Arrays.asList( result ) );
    }

    private void preOrder( List<Account> referenceList ) {
        final long firstRecord = System.currentTimeMillis();

        referenceList.clear();
        preOrder( root, referenceList );

        System.out.println( "\n\t\t Pre-order sorting algorithm run-time: " + ( System.currentTimeMillis() - firstRecord ) + "ms\n" );
    }

    private void preOrder( Node node, List<Account> referenceList ) {
        if ( node == null ) return;

        referenceList.add( node.value );
        preOrder( node.left, referenceList );
        preOrder( node.right, referenceList );
    }

    private void postOrder( List<Account> referenceList ) {
        final long firstRecord = System.currentTimeMillis();

        referenceList.clear();
        postOrder( root, referenceList );

        System.out.println( "\n\t\t Post-order sorting algorithm run-time: " + ( System.currentTimeMillis() - firstRecord ) + "ms\n" );
    }

    private void postOrder( Node node, List<Account> referenceList ) {
        if ( node == null ) return;

        postOrder( node.left, referenceList );
        postOrder( node.right, referenceList );
        referenceList.add( node.value );
    }


    /**
     * Menu bar-related methods
     */

    @FXML
    private void onCloseProgram() {
        // Will be used to get the user's
        // choice when shown with the
        // confirmation dialog
        final Optional<ButtonType> result = dialog.showConfirmationDialog( "You are about to close the program." );
        if ( result.get() == ButtonType.OK ) System.exit(0); // If the player confirms, then exit the program
    }

    @FXML
    private void onMinimizeProgram() {
        final Stage stage = ( Stage ) BP_dashboardScreen.getScene().getWindow(); // Get the access screen window
        stage.setIconified( true ); // Minimize the access screen window
    }

    @FXML
    private void onClickMenuBar( MouseEvent event ) { // Is used alongside onDragMenuBar() for dragging the window through the custom menu bar
        xOffset = BP_dashboardScreen.getScene().getWindow().getX() - event.getScreenX();
        yOffset = BP_dashboardScreen.getScene().getWindow().getY() - event.getScreenY();
    }

    @FXML
    void onDragMenuBar( MouseEvent event ) { // Is used alongside onClickMenuBar() for dragging the window through the custom title bar
        BP_dashboardScreen.getScene().getWindow().setX( event.getScreenX() + xOffset );
        BP_dashboardScreen.getScene().getWindow().setY( event.getScreenY() + yOffset );
    }
}
