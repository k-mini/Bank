package shop.mtcoding.bank.temp;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

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

    // @Sql("classpath:db/teardown.sql")
    @BeforeEach
    public void dataSetting() {
        autoincrementReset();
        User ssar = userRepository.save(newUser("ssar", "쌀"));
        User cos = userRepository.save(newUser("cos", "코스,"));
        Account ssarAccount = accountRepository.save(newAccount(1111L, ssar));
        Account cosAccount = accountRepository.save(newAccount(2222L, cos));
        em.clear();
    }

    @Test
    public void 영속성테스트() {
        System.out.println("테스트 : 영속성테스트 시작 ===================================");
        List<Account> accounts = accountRepository.findAll();
        System.out.println("테스트 시작전 : 영속성테스트 계좌 정보: =====================");
        System.out.println("테스트 : " + accounts);
        List<User> users = userRepository.findAll();
        System.out.println("테스트 시작전: 영속성테스트 유저 정보: =======================");
        System.out.println(users);

        Account ssarAccount = accountRepository.findByUser_id(1L).get(0);
        System.out.println("테스트 : ssarAccount 잔액 " + ssarAccount.getBalance());

        ssarAccount.setBalance(900L);

        Transaction withdrawTransaction = transactionRepository
                .save(newWithdrawTransaction(ssarAccount, accountRepository));
        System.out.println("테스트 : ssarAccount 잔액 " + ssarAccount.getBalance());
        System.out.println("테스트 : 트랜잭션 로그 " + withdrawTransaction.getWithdrawAcountBalance());
    }

    @Test
    public void 자식에서_부모바꾸기() {
        System.out.println("테스트 : 자식에서 부모바꾸기 시작 ===================================");
        Account ssarAccount = accountRepository.findByUser_id(1L).get(0);
        System.out.println("테스트 : ssarAccount 잔액 " + ssarAccount.getBalance());

        ssarAccount.setUser(null);
        accountRepository.save(ssarAccount);

        List<Account> accounts = accountRepository.findAll();
        System.out.println("테스트 : 계좌 정보: =====================");
        System.out.println("테스트 : " + accounts);
        List<User> users = userRepository.findAll();
        System.out.println("테스트 : 유저 정보: =======================");
        System.out.println(users);
    }

    @Test
    public void 부모에서_자식바꾸기() {
        List<Account> accounts1 = accountRepository.findAll();
        System.out.println("테스트 시작전 : 부모에서_자식바꾸기 계좌 정보: =====================");
        System.out.println("테스트 : " + accounts1);
        List<User> users1 = userRepository.findAll();
        System.out.println("테스트 시작전: 부모에서_자식바꾸기 유저 정보: =======================");
        System.out.println(users1);

        User ssar = userRepository.findById(1L).get();

        // ssar.setAccount(null);
        userRepository.save(ssar);

        List<Account> accounts = accountRepository.findAll();
        System.out.println("테스트 : 부모에서_자식바꾸기 계좌 정보: =====================");
        System.out.println("테스트 : " + accounts);
        List<User> users = userRepository.findAll();
        System.out.println("테스트 : 부모에서_자식바꾸기 유저 정보: =======================");
        System.out.println(users);
    }

    private void autoincrementReset() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE account_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE transaction_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }
}
