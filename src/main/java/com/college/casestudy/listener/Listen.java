package com.college.casestudy.listener;

import com.college.casestudy.model.parent.Account;
import javafx.scene.input.MouseEvent;

public interface Listen {

    /**
     *  Creates an interface that can be used
     *  for the [GridModelController] class
     */

    void onClickListener( MouseEvent event, Account account );
}
