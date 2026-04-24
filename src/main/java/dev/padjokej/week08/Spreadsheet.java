package dev.padjokej.week08;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Spreadsheet extends Application {
    private static final int SIZE = 9;
    private static final Pattern VALUE_RX =
            Pattern.compile("\\d+");
    private static final Pattern FORMULA_RX =
            Pattern.compile("=([A-Ia-i]\\d)([/*+-])([A-Ia-i]\\d)");

    private final List<Cell> cells;
    private final ObjectProperty<Cell> selectedCellProperty;

    public static void main(String[] args) {
        launch(args);
    }

    public Spreadsheet() {
        List<Cell> cells = new ArrayList<>(SIZE * SIZE);
        for (int r = 0; r < SIZE; ++r)
            for (int c = 0; c < SIZE; ++c)
                cells.add(new Cell(c, r, 0));
        this.cells = cells;
        this.selectedCellProperty = new SimpleObjectProperty<>(cells.getFirst());
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setPadding(new Insets(5));

        // Add column/row headers
        for (int r = 1; r <= SIZE; ++r)
            gridPane.add(new Label(String.valueOf(r)), 0, r);
        for (int c = 1; c <= SIZE; ++c)
            gridPane.add(new Label(String.valueOf((char) ('A' + c - 1))), c, 0);

        // Add cell buttons
        for (Cell cell : cells) {
            Button cellButton = new Button(String.valueOf(cell.getValue()));
            cellButton.setPrefWidth(70);
            cellButton.setOnAction(e -> selectedCellProperty.set(cell));
            gridPane.add(cellButton, cell.getColumn() + 1, cell.getRow() + 1);

            // TODO: add observer to the cell, and update button's text with new value
            cell.addObserver(c -> cellButton.setText(String.valueOf(cell.getValue())));
        }

        // Add selected cell name and edit field
        Label selectedCellName = new Label();
        selectedCellName.textProperty().bind(selectedCellProperty.map(Cell::getName));
        gridPane.add(selectedCellName, 0, SIZE + 1);

        TextField formulaField = new TextField(selectedCellProperty.get().getFormulaString());
        formulaField.setPrefColumnCount(3);
        formulaField.setOnAction(e -> setFormula(selectedCellProperty.get(), formulaField.getText()));
        selectedCellProperty.addListener((p, o, n) -> formulaField.setText(n.getFormulaString()));
        gridPane.add(formulaField, 1, SIZE + 1, SIZE, 1);

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setFormula(Cell cell, String newContent) {
        Matcher valueM = VALUE_RX.matcher(newContent);
        Matcher formulaM = FORMULA_RX.matcher(newContent);
        if (valueM.matches()) {
            // Constant value
            int value = Integer.parseInt(newContent);
            cell.setFormula(newContent, List.of(), (x, y) -> value);
        } else if (formulaM.matches()) {
            // Formula
            Cell c1 = parseCell(formulaM.group(1));
            Cell c2 = parseCell(formulaM.group(3));
            IntBinaryOperator fun = parseFun(formulaM.group(2));
            cell.setFormula(newContent, List.of(c1, c2), fun);
        } else
            // Nothing valid
            java.awt.Toolkit.getDefaultToolkit().beep();
    }

    private Cell parseCell(String cellName) {
        int c = Character.toUpperCase(cellName.charAt(0)) - 'A';
        int r = Integer.parseInt(cellName.substring(1)) - 1;
        return cells.get(r * SIZE + c);
    }

    private IntBinaryOperator parseFun(String op) {
        return switch (op.charAt(0)) {
            case '+' -> Integer::sum;
            case '-' -> (x, y) -> x - y;
            case '*' -> (x, y) -> x * y;
            case '/' -> (x, y) -> x / y;
            default -> throw new Error();
        };
    }
}
