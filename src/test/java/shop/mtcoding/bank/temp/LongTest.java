package shop.mtcoding.bank.temp;

import org.junit.jupiter.api.Test;

public class LongTest {

    @Test
    public void _test() throws Exception {
        Long number1 = 1111L;
        Long number2 = 1111L;
        Integer number3 = 1;
        Integer number4 = 1;
        if (number1 == number2) {
            System.out.println("테스트 : 동일합니다.");
        } else {
            System.out.println("테스트 : 동일하지 않습니다.");
        }

        Long amount1 = 100L;
        Long amount2 = 1000L;

        if (amount1 < amount2) {
            System.out.println("테스트 : amount1이 작습니다");
        } else {
            System.out.println("테스트 : amount1이 큽니다.");
        }
    }
}
