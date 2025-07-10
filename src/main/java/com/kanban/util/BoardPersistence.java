package com.kanban.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kanban.model.Board;

import java.io.File;
import java.io.IOException;

public class BoardPersistence {

    private static final String FILE_PATH = "kanban_board.json";

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    /**
     * Board를 JSON으로 저장
     */
    public static void saveBoard(Board board) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), board);
            System.out.println("Board 저장 완료: " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Board 저장 실패: " + e.getMessage());
        }
    }

    /**
     * JSON 파일에서 Board를 불러오기
     */
    public static Board loadBoard() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("저장된 Board 파일 없음 → 기본 보드 생성");
            return Board.createDefaultBoard();
        }

        try {
            return mapper.readValue(file, Board.class);
        } catch (IOException e) {
            System.err.println("Board 로드 실패 → 기본 보드 반환: " + e.getMessage());
            return Board.createDefaultBoard();
        }
    }
}
