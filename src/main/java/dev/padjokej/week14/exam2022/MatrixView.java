package dev.padjokej.week14.exam2022;

import java.util.StringJoiner;

public final class MatrixView {
    private final int[] array;

    private final int rStride, cStride, rows, columns;

    public static MatrixView ofRow(int[] row, int rows) {
        return new MatrixView(row, 0, 1, rows, row.length);
    }

    public static MatrixView ofColumn(int[] column, int columns) {
        return new MatrixView(column, 1, 0, column.length, columns);
    }

    public MatrixView(int[] array,
                      int rStride, int cStride,
                      int rows, int columns) {
        this.array = array;

        this.rStride = rStride;
        this.cStride = cStride;

        this.rows = rows;
        this.columns = columns;
    }

    public int get(int r, int c) {
        if (r >= rows || r < 0
                || c >= columns || c < 0) throw new IndexOutOfBoundsException();

        int i = r * rStride + c * cStride;

        return array[i];
    }

    public MatrixView transposed() {
        return new MatrixView(array, cStride, rStride, columns, rows);
    }

    @Override
    public String toString() {
        StringJoiner matrix = new StringJoiner(",\n ", "[", "]");

        for (int r = 0; r < rows; r++) {
            StringJoiner row = new StringJoiner(", ", "[", "]");

            for (int c = 0; c < columns; c++) {
                row.add(Integer.toString(get(r, c)));
            }
            matrix.add(row.toString());
        }

        return matrix.toString();
    }
}
