package com.kanban;

import com.kanban.controller.BoardController;
import com.kanban.model.Board;
import com.kanban.util.BoardPersistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private BoardController boardController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

        // FXML 로드
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kanban/view/MainView.fxml"));
        Parent root = loader.load();

        // 컨트롤러 가져오기
        boardController = loader.getController();

        // Board 데이터 로드
        Board loadedBoard = BoardPersistence.loadBoard();
        boardController.setBoard(loadedBoard);

        // Scene & Stage 세팅
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Kanban To-Do Board");
        primaryStage.setScene(scene);
        primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (boardController != null) {
            Board currentBoard = boardController.getBoard();
            BoardPersistence.saveBoard(currentBoard);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
