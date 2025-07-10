package com.kanban.controller;

import com.kanban.model.Board;
import com.kanban.model.Column;
import com.kanban.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class BoardController {

    @FXML
    private Button addNewColumn;

    @FXML
    private HBox columnContainer; // 컬럼들을 담을 컨테이너

    private Board board;

    /**
     * FXML 초기화
     */
    @FXML
    public void initialize() {
        columnContainer.setPrefHeight(400);
    }

    /**
     * Board 설정 및 UI 초기화
     */
    public void setBoard(Board board) {
        this.board = board;
        refreshUIFromBoard();
    }

    /**
     * 현재 Board 반환
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Board 데이터 기반으로 UI 갱신
     */
    private void refreshUIFromBoard() {
        columnContainer.getChildren().clear();

        if (board == null) return;

        for (Column column : board.getColumns()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kanban/view/ColumnView.fxml"));
                AnchorPane columnPane = loader.load();

                ColumnController columnController = loader.getController();
                columnController.setColumn(column);
                columnController.setBoardController(this); // 필요시 BoardController 넘겨줌

                // 컬럼에 포함된 Task들 UI에 추가
                for (Task task : column.getTasks()) {
                    FXMLLoader cardLoader = new FXMLLoader(getClass().getResource("/com/kanban/view/TaskCard.fxml"));
                    AnchorPane taskCard = cardLoader.load();

                    TaskCardController cardController = cardLoader.getController();
                    cardController.setTask(task);
                    cardController.setOnDeleteCallback(() -> {
                        column.removeTask(task);
                        refreshUIFromBoard();
                    });

                    columnController.addTaskCard(taskCard);
                }

                columnContainer.getChildren().add(columnPane);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 새 컬럼 추가 버튼 클릭 시
     */
    @FXML
    private void onAddColumnClicked() {
        Column newColumn = new Column("New Column");
        board.addColumn(newColumn);
        refreshUIFromBoard();
    }
}