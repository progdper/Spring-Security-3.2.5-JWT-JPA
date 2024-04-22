package com.example.demo.service_Model;

import com.example.demo.domain_DB.Board;
import com.example.demo.domain_DB.User;
import com.example.demo.dto_DBdata.request.BoardRequest;
import com.example.demo.repository_ORM_jpa.BoardRepository;
import com.example.demo.repository_ORM_jpa.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

//    public Board save(BoardCreateRequest request) {
//        return boardRepository.save(request.toEntity());
//    }

    public void saveBoard(String title, String content, Long userId) {
        boardRepository.save(
                Board.builder()
                        .title(title)
                        .content(content)
                        .userId(userRepository.findById(userId).get())
                        .build()
        );
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board findById(long id) {
        return boardRepository.findById(id)
                .orElse(null);
    }

    @Transactional
    public Board update(long id, User userId, BoardRequest boardRequest) {

        Board board = boardRepository.findByIdAndUserId(id, userId)
                .orElse(null);

        if(board == null) {
            return null;
        }

        board.update(boardRequest.getTitle(), boardRequest.getContent());

        return board;

    }

    @Transactional
    public String delete(long id, User userId) {

        Board board = boardRepository.findByIdAndUserId(id, userId)
                .orElse(null);

        if(board == null) {
            return null;
        }

        boardRepository.delete(board);

        return "success";

    }


}
