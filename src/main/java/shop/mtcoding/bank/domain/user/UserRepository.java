package shop.mtcoding.bank.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // save - 이미 만들어져 있음.

    // select * from user where username = ?
    Optional<User> findByUsername(String username); // Jpa NameQuery 작동

}
