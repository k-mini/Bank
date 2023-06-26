package shop.mtcoding.bank.domain.transaction;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.data.repository.query.Param;

import lombok.RequiredArgsConstructor;

interface Dao {
    List<Transaction> findTransactionList(@Param("accountId") Long accountId, @Param("gubun") String gubun,
            @Param("page") Integer page);
}

// Impl은 꼭 붙여줘야 하고, TransactionRepository가 앞에 붙어야 한다.
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements Dao {

    private final EntityManager em;

    // JPQL이 무엇인지
    // join fetch
    // outer join을 해야 하는지
    @Override
    public List<Transaction> findTransactionList(Long accountId, String gubun, Integer page) {
        // 동적쿼리 (gubun 값을 가지고 동적쿼리 = DEPOSIT, WITHDRAW, ALL)
        // JPQL
        String sql = "";
        sql += "select t from Transaction t ";

        // join 과정에서 fetch를 안하면 나중에 account 정보를 가져올 때 다시 쿼리가 발생하는데,
        // 이때 join과정에서 나온 모든 userid를 이용한 where in (userid1, userid2, userid3) 으로 값을 다
        // 가져온다.
        // 예시1. 트랜잭션의 deposit_account_id가 3인 값과 account 테이블을 inner 조인했다면 해도 이 때 나온
        // withdraw_account_id 값이 1,3이고 deposit_account_id는 당연히 3일 것이다. 결과 레코드값 중
        // account_id는 총 2개 존재한다.
        // 이러면 where account_id in (1,3)으로 account 관련 정보들을 가져온다.
        // select * from account_tb account0_ where account0_.id in ( 1, 3)
        // 예시2. inner 조인해서 나온 결과 레코드에
        // account_id값(withdraw_account_id,deposit_account_id)이
        // withdraw_account_id에서 1,2,3이 있고 deposit_account_id에서 3이라면,
        // select * from account_tb account0_ where account0_.id in ( 1, 2, 3)

        if (gubun.equals("WITHDRAW")) {
            sql += "join fetch t.withdrawAccount wa ";
            sql += "where t.withdrawAccount.id = :withdrawAccountId";
        } else if (gubun.equals("DEPOSIT")) {
            sql += "join fetch t.depositAccount da ";
            sql += "where t.depositAccount.id = :depositAccountId";
        } else { // gubun = ALL
            sql += "left join fetch t.withdrawAccount wa "; // 1, 3, 4, 5
            sql += "left join fetch t.depositAccount da "; //
            sql += "where t.withdrawAccount.id = :withdrawAccountId ";
            sql += "or ";
            sql += "t.depositAccount.id = :depositAccountId";
        }

        TypedQuery<Transaction> query = em.createQuery(sql, Transaction.class);

        if (gubun.equals("WITHDRAW")) {
            query = query.setParameter("withdrawAccountId", accountId);
        } else if (gubun.equals("DEPOSIT")) {
            query = query.setParameter("depositAccountId", accountId);
        } else {
            query = query.setParameter("withdrawAccountId", accountId);
            query = query.setParameter("depositAccountId", accountId);
        }

        query.setFirstResult(page * 5); // 5, 10, 15
        query.setMaxResults(5);

        return query.getResultList();
    }

}
