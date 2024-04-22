package com.example.demo.controller_RouteAndController;

import com.example.demo.config.jwt.TokenAuthenticationFilter;
import com.example.demo.config.jwt.TokenProvider;
import com.example.demo.domain_DB.Board;
import com.example.demo.domain_DB.User;
import com.example.demo.dto_DBdata.request.BoardRequest;
import com.example.demo.dto_DBdata.request.SignRequest;
import com.example.demo.dto_DBdata.response.BoardResponse;
import com.example.demo.returnCode.ErrorResponse;
import com.example.demo.returnCode.SuccessResponse;
import com.example.demo.service_Model.BoardService;
import com.example.demo.service_Model.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class BoardController {

    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final BoardService boardService;

    public Long verifyToken(HttpServletRequest headers) {
        String findToken = tokenAuthenticationFilter.parseJwt(headers);
        // doFilterInternal 에서 점검함에 제외 -> SecurityContextHolder 를 이용하여 userId 또는 User 를 가져오는 방법 확인 필요
//        if(findToken == null) {
//            return 0L;
//        }
//
//        boolean checkToken = tokenProvider.validToken(findToken);
//
//        if(!checkToken) {
//            return 0L;
//        }

        Long userId = tokenProvider.getUserIdFromAccessJwt(findToken);

        return userService.findUser(userId).getId();
    }



    @PostMapping("/create")
    public ResponseEntity<?> registryBoard(HttpServletRequest headers, @Valid @RequestBody BoardRequest boardRequest) {

        Long findUserId = verifyToken(headers);

//        if(findUserId==0) {   // token의 유효시간이 만료되는 경우를 처리한다 -> doFilterInternal 에서 throw new ServletException("토큰의 유효시간이 만료되었습니다.");  대처함
//            return ResponseEntity.ok(new ErrorResponse("E999", "토큰이 없거나 만료되었습니다."));
//        }

        boardService.saveBoard(boardRequest.getTitle(), boardRequest.getContent(), findUserId);

        return ResponseEntity.ok(new SuccessResponse("E101", "게시판 등록이 완료되었습니다."));

    }

    @GetMapping("/all")
    public ResponseEntity<?> readAllBoard() {
        List<BoardResponse> boardResponses = boardService.findAll()
                .stream()
                .map(BoardResponse::new)
                .toList();

        return ResponseEntity.ok(boardResponses);



    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readBoard(@PathVariable long id) {

        Board board = boardService.findById(id);

        if(board == null) {
            return ResponseEntity.ok(new ErrorResponse("E999", "게시판이 없습니다."));
        }

        return ResponseEntity.ok(new BoardResponse(board));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateBoard(HttpServletRequest headers, @PathVariable long id, @Valid @RequestBody BoardRequest boardRequest) {

        Long findUserId = verifyToken(headers);

        User setUser = userService.findUser(findUserId);

        Board board = boardService.update(id, setUser, boardRequest);

        if(board == null) {
            return ResponseEntity.ok(new ErrorResponse("E999", "게시판 수정 중에 오류가 발생했습니다."));
        }

        return ResponseEntity.ok(new BoardResponse(board));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delBoard(HttpServletRequest headers, @PathVariable long id) {

        Long findUserId = verifyToken(headers);

        User setUser = userService.findUser(findUserId);

        String board = boardService.delete(id, setUser);

        if(board == null) {
            return ResponseEntity.ok(new ErrorResponse("E999", "게시판 삭제 중에 오류가 발생했습니다."));
        }
        return ResponseEntity.ok("success");

    }

}
