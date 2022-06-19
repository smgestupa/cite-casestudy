package com.college.casestudy.controller.grid;

import com.college.casestudy.listener.Listen;
import com.college.casestudy.model.parent.Account;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GridModelController {

    // Labels
    @FXML private Label L_modelAccessCode, L_modelFirstName, L_modelLastName;

    // ImageView
    @FXML private ImageView IV_profileImage;

    // Grid Model
    private Account account;

    // Listener
    private Listen listener;


    @FXML
    private void clickGridModel( MouseEvent event ) {
        // This will be used to check if the
        // grid model was clicked
        listener.onClickListener( event, account );
    }

    public void initData( Account account, Listen listener ) {
        this.account = account;
        this.listener = listener;

        // Store the Object array to this variable for ease of use
        final Object[] accountInfo = account.getAccountInfo();

        // Replace placeholder values in the
        // grid model
        L_modelAccessCode.setText( accountInfo[ 1 ].toString() );
        L_modelFirstName.setText( accountInfo[ 4 ].toString() );
        L_modelLastName.setText( accountInfo[ 5 ].toString() );

        // Wrap to boolean, since value from
        // the first uses a boolean data type
        if ( (boolean) accountInfo[ 0 ] ) {
            // Create an Image object, find the
            // relative path of the student icon,
            // and set it  as the  profile image
            final Image studentImage = new Image( getClass().getResource("/com/college/casestudy/assets/profile/student-icon.png").toString() );
            IV_profileImage.setImage( studentImage );
        } else {
            // Create an Image object, find the
            // relative path of the teacher icon,
            // and set it  as the  profile image
            final Image teacherImage = new Image( getClass().getResource("/com/college/casestudy/assets/profile/teacher-icon.png").toString() );
            IV_profileImage.setImage( teacherImage );
        }
    }
}
