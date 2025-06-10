package net.supercoding.backend.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 비밀번호 틀릴 때 or 로그인 실패 시 (403 에러가 떠서 401로 고정, 403은 인증되었음을 전제로 하기에 401이 적절하기 때문)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED) // 401
                .body(Map.of("error", "이메일 또는 비밀번호가 일치하지 않습니다."));
    }
}
