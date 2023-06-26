package shop.mtcoding.bank.temp;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.transaction.TransactionRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;

@DataJpaTest
public class RepoTest extends DummyObject {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void dataSetting() {
        User ssar = userRepository.save(newUser("ssar", "쌀"));
        User cos = userRepository.save(newUser("cos", "코스,"));

        Account ssarAccount = accountRepository.save(newAccount(1111L, ssar));
        Account cosAccount = accountRepository.save(newAccount(2222L, cos));
        em.clear();
    }

    @Test
    public void 영속성테스트() {

        Account ssarAccount = accountRepository.findByUser_id(1L).get(0);
        System.out.println("테스트 : ssarAccount 잔액 " + ssarAccount.getBalance());

        ssarAccount.setBalance(900L);

        Transaction withdrawTransaction = transactionRepository
                .save(newWithdrawTransaction(ssarAccount, accountRepository));
        System.out.println("테스트 : ssarAccount 잔액 " + ssarAccount.getBalance());
        System.out.println("테스트 : 트랜잭션 로그 " + withdrawTransaction.getWithdrawAcountBalance());
    }
}
