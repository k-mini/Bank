package shop.mtcoding.bank.handler.ex;

// 추후에 사용할 예쩡
public class CustomForbiddenException extends RuntimeException {

    public CustomForbiddenException(String message) {
        super(message);
    }
}
