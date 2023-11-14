package com.example.demo.type;

public enum NotificationType {
    MODIFY_MATCHING("신청한 매칭글이 수정되었습니다."),
    DELETE_MATCHING("신청한 매칭글이 삭제되었습니다."),
    REQUEST_APPLY("주최한 매칭에 참가 신청을 원하는 회원이 있습니다."),
    ACCEPT_APPLY("신청한 매칭글의 주최자가 참가 신청을 수락하였습니다."),
    CANCEL_APPLY("주최한 매칭에 참가 신청을 한 회원이 참가 취소를 하였습니다."),
    WEATHER("우천이 예상됩니다. 매칭 참가 취소를 원하시면 취소를 진행해주세요."),
    MATCHING_CLOSED("매칭에 성공하였습니다. 채팅방이 활성화됩니다."),
    MATCHING_FAILED("매칭에 실패하였습니다.");

    private String message;


    NotificationType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
