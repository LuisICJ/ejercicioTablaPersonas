package site.luisicj.ejerciciotablapersonas;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import javafx.scene.layout.HBox;
import static javafx.scene.control.TableView.TableViewSelectionModel;

/**
 * TableViewAddDeleteRows - es la clase principal
 */
public class TableViewAddDeleteRows extends Application {
    /**
     * Campos para rellenar los datos
     */
    private TextField fNameField;
    private TextField lNameField;
    private DatePicker dobField;
    private TableView<Person> table;
    private Button addBtn;

    /**
     * Mtodo principal
     * @param args - parámetros
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Lanza la aplicación
     * @param stage - la ventana
     */
    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) {
        fNameField = new TextField();
        lNameField = new TextField();
        dobField = new DatePicker();
        table = new TableView<>(PersonTableUtil.getPersonList());
        // Turn on multi-row selection for the TableView
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.MULTIPLE);
        // Add columns to the TableView
        table.getColumns().addAll(PersonTableUtil.getIdColumn(), PersonTableUtil.getFirstNameColumn(), PersonTableUtil.getLastNameColumn(), PersonTableUtil.getBirthDateColumn());
        GridPane newDataPane  = this.getNewPersonDataPane();
        Button restoreBtn = new Button("Restore Rows");
        restoreBtn.setTooltip(new Tooltip("Restaurar las filas"));
        restoreBtn.setOnAction(e -> restoreRows());
        Button deleteBtn = new Button("Delete Selected Rows");
        deleteBtn.setTooltip(new Tooltip("Eliminar las filas seleccionadas"));
        deleteBtn.setOnAction(e -> deleteSelectedRows());
        VBox root = new VBox(newDataPane, new HBox(restoreBtn, deleteBtn), table);
        root.setSpacing(5);
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Adding/Deleting Rows in a TableViews");
        stage.show();
    }

    /**
     * Crea el panel para rellenar los datos
     * @return GridPane
     */
    public GridPane getNewPersonDataPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(5);
        pane.addRow(0, new Label("First Name:"), fNameField);
        pane.addRow(1, new Label("Last Name:"), lNameField);
        pane.addRow(2, new Label("Birth Date:"), dobField);
        addBtn = new Button("Add");
        addBtn.setDisable(true);

        fNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateForm();
        });

        lNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateForm();
        });

        dobField.valueProperty().addListener((observable, oldValue, newValue) -> {
            validateForm();
        });

        addBtn.setTooltip(new Tooltip("Añadir persona"));
        addBtn.setOnAction(e -> addPerson());
        // Add the "Add" button
        pane.add(addBtn, 2, 0);
        return pane;
    }

    /**
     * validateForm - Valida el formulario
     */
    private void validateForm() {
        boolean isValid = !fNameField.getText().isEmpty()
                && !lNameField.getText().isEmpty()
                && dobField.getValue() != null;

        if (isValid) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                LocalDate.parse(dobField.getValue().toString(), formatter);
            } catch (DateTimeParseException e) {
                isValid = false;
            }
        }

        addBtn.setDisable(!isValid); // Habilitamos o deshabilitamos el botón según sea válido el formulario
    }

    /**
     * deleteSelectedRows - Borra las filas seleccionadas
     */
    public void deleteSelectedRows() {
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        if (tsm.isEmpty()) {
            System.out.println("Please select a row to delete.");
            return;
        }
        // Get all selected row indices in an array
        ObservableList<Integer> list = tsm.getSelectedIndices();
        Integer[] selectedIndices = new Integer[list.size()];
        selectedIndices = list.toArray(selectedIndices);
        // Sort the array
        Arrays.sort(selectedIndices);
        // Delete rows (last to first)
        for(int i = selectedIndices.length - 1; i >= 0; i--) {
            tsm.clearSelection(selectedIndices[i].intValue());
            table.getItems().remove(selectedIndices[i].intValue());
        }
    }

    /**
     * restoreRows - Restaura las filas
     */
    public void restoreRows() {
        table.getItems().clear();
        table.getItems().addAll(PersonTableUtil.getPersonList());
    }

    /**
     * getPerson - Crea una persona
     * @return Person
     */
    public Person getPerson() {
        return new Person(fNameField.getText(), lNameField.getText(), dobField.getValue());
    }

    /**
     * addPerson - Añade una persona
     */
    public void addPerson() {
        Person p = getPerson();
        table.getItems().add(p);
        clearFields();
    }

    /**
     * clearFields - Limpia los campos
     */
    public void clearFields() {
        fNameField.setText(null);
        lNameField.setText(null);
        dobField.setValue(null);
    }

}
