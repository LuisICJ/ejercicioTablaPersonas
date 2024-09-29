module site.luisicj.ejerciciotablapersonas {
    requires javafx.controls;
    requires javafx.fxml;
    opens site.luisicj.ejerciciotablapersonas to javafx.fxml;
    exports site.luisicj.ejerciciotablapersonas;
}