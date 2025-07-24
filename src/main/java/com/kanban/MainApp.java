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

    // JavaFX 애플리케이션이 시작될 때 호출
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

        // FXML 로드
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kanban/view/MainView.fxml"));
        Parent root = loader.load();        // FXML 파일을 파싱하여 UI 트리를 생성

        // FXML에 지정된 보드컨트롤러 인스턴스를 가져와 필드에 저장
        boardController = loader.getController();

        // JSON 파일에서 저장된 보드 데이터를 불러옴
        Board loadedBoard = BoardPersistence.loadBoard();

        // 불러온 보드 모델을 컨트롤러에 주입하여 UI를 초기화
        boardController.setBoard(loadedBoard);

        // Scene & Stage 세팅
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Kanban To-Do Board");

        // Stage에 Scene을 설정하여 UI를 연결
        primaryStage.setScene(scene);
        primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // JavaFX 애플리케이션이 종료될 때 호출
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
