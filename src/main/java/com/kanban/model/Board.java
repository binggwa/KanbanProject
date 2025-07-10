package com.kanban.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private ObservableList<Column> columns;

    /**
     * 기본 생성자
     */
    public Board() {
        this.columns = FXCollections.observableArrayList();
    }

    /**
     * 초기 컬럼을 가진 기본 보드 생성
     */
    public static Board createDefaultBoard() {
        Board board = new Board();
        board.addColumn(new Column("To Do"));
        board.addColumn(new Column("In Progress"));
        board.addColumn(new Column("Done"));
        return board;
    }

    public ObservableList<Column> getColumns() {
        return columns;
    }

    public void setColumns(ObservableList<Column> columns) {
        this.columns = columns;
    }

    /**
     * JSON 직렬화를 위해 List 형태로 반환
     */
    public List<Column> getColumnList() {
        return new ArrayList<>(columns);
    }

    /**
     * JSON 역직렬화를 위해 List를 ObservableList로 변환
     */
    public void setColumnList(List<Column> columnList) {
        this.columns = FXCollections.observableArrayList(columnList);
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    public void removeColumn(Column column) {
        columns.remove(column);
    }

    @Override
    public String toString() {
        return "Board{" +
                "columns=" + columns +
                '}';
    }
}
