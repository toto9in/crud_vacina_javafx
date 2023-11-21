module javafxvacina {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.graphics;
    

    opens javafxvacina to javafx.fxml;
    opens javafxvacina.models to javafx.base, javafx.graphics;
    opens javafxvacina.models.dao to javafx.base, javafx.graphics;
    exports javafxvacina;
}
