package shop.mtcoding.bank.domain.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import shop.mtcoding.bank.domain.user.User;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // jpa query method
    // select * from account where number = :number
    // checkpoint : 리팩토링 해야함!! (계좌 소유자 확인시에 쿼리가 두번 나가기 때문에 join fetch)
    Optional<Account> findByNumber(Long number);

    // jpa query method
    // select * from account where user_id = :id
    List<Account> findByUser_id(Long id);

}
