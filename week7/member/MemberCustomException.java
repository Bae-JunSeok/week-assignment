package study.likelionbeweekly.week7.member;

import com.fasterxml.jackson.databind.ser.Serializers;
import study.likelionbeweekly.week7.exception.BaseErrorCode;
import study.likelionbeweekly.week7.exception.CustomException;

public class MemberCustomException extends CustomException {
    public MemberCustomException(BaseErrorCode errorCode) {
        super(errorCode);
    }

    public static class MemberNotFoundException extends MemberCustomException {
        public MemberNotFoundException() {
            super(MemberErrorCode.MEMBER_NOT_FOUND);
        }
    }

    public static class DuplicatedEmailException extends MemberCustomException {
        public DuplicatedEmailException() {
            super(MemberErrorCode.DUPLICATE_EMAIL);
        }
    }

    public static class IncorrectPasswordException extends MemberCustomException {
        public IncorrectPasswordException() {
            super(MemberErrorCode.INVALID_IN_PASSWORD);
        }
    }
}
