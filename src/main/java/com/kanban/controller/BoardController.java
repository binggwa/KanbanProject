package com.kanban.controller;

import com.kanban.model.Board;
import com.kanban.model.Column;
import com.kanban.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * BoardController: 보드 전체를 관리하는 JavaFX 컨트롤러
 * MainView.fxml에 연결되어 있음
 * 컬럼을 생성
 */
public class BoardController {

    @FXML
    private Button addNewColumn;    // "Add New Column" 버튼 (FXML에서 연결됨)

    @FXML
    private HBox columnContainer; // 컬럼들을 담을 컨테이너

    private Board board;

    /**
     * FXML 로딩 직후 자동으로 호출되는 초기화 메서드
     * 기본 레이아웃 설정 등 초기 구성 가능
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

    // Task ID를 기반으로 해당 Task를 전체 보드에서 탐색
    public Task findTaskById(String taskId) {
        for (Column col : board.getColumns()) {     // 모든 컬럼 순회
            for (Task task : col.getTasks()) {      // 컬럼 내 태스크 순회
                if (task.getId().equals(taskId)) {  // ID 일치 여부 확인
                    return task;
                }
            }
        }
        return null;
    }

    // 특정 Task를 다른 컬럼으로 이동시키는 메서드
    public void moveTaskToColumn(Task task, Column targetColumn) {
        for (Column col : board.getColumns()) {
            if (col.getTasks().contains(task)) {
                col.removeTask(task);       // 원래 위치에서 제거
                break;
            }
        }
        targetColumn.addTask(task);         // 타켓컬럼에 추가
    }

    /**
     * Board 데이터 기반으로 UI 갱신
     */
    public void refreshUIFromBoard() {
        columnContainer.getChildren().clear();      // 기존 컬럼들 제거

        if (board == null) return;

        for (Column column : board.getColumns()) {
            try {
                // ColumnView.fxml을 로드
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kanban/view/ColumnView.fxml"));
                AnchorPane columnPane = loader.load();      // Column UI 로딩

                // 컨트롤러 연결
                ColumnController columnController = loader.getController();
                columnController.setColumn(column);         // 컬럼 데이터 설정
                columnController.setBoardController(this);  // 역참조 설정

                // UI에 컬럼 추가
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