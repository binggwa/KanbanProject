package com.kanban.model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private ObservableList<Column> columns;

    // 기본 생성자
    public Board() {
        this.columns = FXCollections.observableArrayList();
    }

    // 초기 컬럼을 생성하는 유틸 생성자
    public static Board createDefaultBoard() {
        Board board = new Board();
        board.addColumn(new Column("To Do"));
        board.addColumn(new Column("In Progress"));
        board.addColumn(new Column("Done"));
        return board;
    }

    // JavaFX UI 연동용
    public ObservableList<Column> getColumns() {
        return columns;
    }

    public void setColumns(ObservableList<Column> columns) {
        this.columns = columns;
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    public void removeColumn(Column column) {
        columns.remove(column);
    }

    // JSON 저장/불러오기 변환 지원
    public List<Column> getColumnList() {
        return new ArrayList<>(columns);
    }

    public void setColumnList(List<Column> columnList) {
        this.columns = FXCollections.observableArrayList(columnList);
    }

    @Override
    public String toString() {
        return "Board{" + "columns = " + columns + '}';
    }
}