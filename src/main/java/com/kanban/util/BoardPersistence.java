package com.kanban.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kanban.model.Board;
import com.kanban.model.Column;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BoardPersistence {
    private static final String FILE_PATH = "board.json";               // 저장될 파일 이름, 실행 디렉토리에 board.json 파일이 생성/사용
    private static final ObjectMapper mapper = new ObjectMapper()       // Jackson의 ObjectMapper 인스턴스를 생성
            .registerModule(new JavaTimeModule());                      // Java 8의 LocalDate 처리할 수 있도록 JavaTimeModule을 등록

    /**
     * 보드를 JSON 파일로 저장
     */
    public static void saveBoard(Board board) {
        try {
            // Board 내부의 columns를 List로 꺼내어 저장
            // Jackson은 ObservableList는 바로 처리하지 못하므로, 일반 List 형태로 꺼냄
            List<Column> columns = board.getColumnList();
            // ObjectMapper를 통해 columns 리스트를 JSON 형식으로 파일에 저장, 보기 좋게 정렬된(JSON Pretty Format) 형태로 저장
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), columns);
        } catch (IOException e) {
            // 저장 중 에러가 발생하면 콘솔에 메시지를 출력하고 스택 트레이스를 기록
            System.err.println("Error saving board: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * JSON 파일로부터 보드를 로드
     * 파일이 없거나 오류가 있으면 기본 보드를 반환
     */
    public static Board loadBoard() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            // 파일이 없으면 기본 보드를 생성
            return Board.createDefaultBoard();
        }

        try {
            // JSON을 List<Column>으로 로드
            List<Column> columns = mapper.readValue(file, new TypeReference<List<Column>>() {});    // board.json 파일의 내용을 읽어서 List<Column>으로 역직렬화
            Board board = new Board();
            board.setColumnList(columns); // 로드한 컬럼 리스트를 ObservableList로 설정
            return board;
        } catch (IOException e) {
            // 파일 읽기 실패 시 로그 출력, 기본 보드로 대체
            System.err.println("Error loading board: " + e.getMessage());
            e.printStackTrace();
            return Board.createDefaultBoard();
        }
    }
}
