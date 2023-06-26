package shop.mtcoding.bank.temp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class LongTest {

    @Test
    public void long_test3() throws Exception {
        // given
        Long v1 = 10000L;
        Long v2 = 10000L;

        // when

        // then
        assertThat(v1).isEqualTo(v2);
    }

    @Test
    public void long_test2() throws Exception {
        // given (2의8승 - 256 범위 (-128 ~ 127) )
        Long v1 = 1000L;
        long v2 = 1001L;
        if (v1 < v2) {
            System.out.println("테스트 : v1이 v2보다 작습니다");
        }

    }

    @Test
    public void long_test() throws Exception {
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

        Long number5 = Long.valueOf(128L);
        Long number6 = Long.valueOf(128L);
        if (number5 == number6) {
            System.out.println("테스트 : 두 숫자가 같습니다");
        } else {
            System.out.println("테스트 : 두 숫자가 다릅니다.");
        }
    }
}
