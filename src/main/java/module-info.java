module com.college.casestudy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.college.casestudy to javafx.fxml;
    exports com.college.casestudy;
    exports com.college.casestudy.controller;
    opens com.college.casestudy.controller to javafx.fxml;
    exports com.college.casestudy.controller.grid;
    opens com.college.casestudy.controller.grid to javafx.fxml;
}