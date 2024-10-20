package com.beemer.unofficial.fromis9.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val message: String?
) {
    /**
     * 400 Bad Request
     * 클라이언트가 잘못된 요청을 보냈을 때 발생하는 에러 코드
     * ex) 필수 요청 파라미터가 누락된 경우
     */
    INVALID_FIELD(HttpStatus.BAD_REQUEST, "잘못된 필드입니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 파라미터입니다."),


    /**
     * 401 Unauthorized
     * 클라이언트가 인증되지 않은 상태에서 보호된 리소스에 접근하려고 할 때 발생하는 에러 코드
     * ex) 로그인하지 않은 사용자가 로그인이 필요한 페이지에 접근할 때
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),


    /**
     * 404 Not Found
     * 클라이언트가 요청한 리소스를 찾을 수 없을 때 발생하는 에러 코드
     * ex) 존재하지 않는 데이터를 조회할 때
     */
    ALBUM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 앨범이 존재하지 않습니다."),
    SONG_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 곡이 존재하지 않습니다."),
    VIDEO_NOT_FOUND(HttpStatus.NOT_FOUND, "영상이 존재하지 않습니다."),
    PLATFORM_NOT_FOUND(HttpStatus.NOT_FOUND, "플랫폼이 존재하지 않습니다."),
    PORTAL_NOT_FOUND(HttpStatus.NOT_FOUND, "포털이 존재하지 않습니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버가 존재하지 않습니다."),
    SSAID_NOT_FOUND(HttpStatus.NOT_FOUND, "SSAID가 존재하지 않습니다."),


    /**
     * 409 Conflict
     * 클라이언트의 요청이 서버의 상태와 충돌이 발생했을 때 발생하는 에러 코드
     * ex) 이미 존재하는 데이터를 생성하려고 할 때
     */
    VIDEO_ALREADY_EXISTS(HttpStatus.CONFLICT, "영상이 이미 존재합니다."),


    /**
     * 500 Internal Server Error
     * 서버에 오류가 발생했을 때 발생하는 에러 코드
     * ex) 서버에서 처리되지 않은 예외가 발생했을 때
     */
    FCM_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "FCM 메시지 전송에 실패했습니다."),
}