package com.kanban.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class BoardController {
    @FXML
    private Button addNewColumn;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox rootVBox;  // VBox 전체 UI

    private HBox columnContainer; // 동적으로 컬럼들을 넣을 컨테이너

    @FXML
    public void initialize() {
        // 초기화 시 ScrollPane 내부에 컬럼을 담을 HBox를 동적으로 생성
        columnContainer = new HBox(20); // 컬럼 사이 간격
        columnContainer.setPrefHeight(400);
        scrollPane.setContent(columnContainer);
    }

    @FXML
    private void onAddColumnClicked() {
        try {
            // ColumnView.fxml 파일을 불러와 하나의 컬럼으로 추가
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ColumnView.fxml"));
            AnchorPane column = loader.load();  // AnchorPane 또는 VBox 등 ColumnView의 루트 타입
            columnContainer.getChildren().add(column);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}