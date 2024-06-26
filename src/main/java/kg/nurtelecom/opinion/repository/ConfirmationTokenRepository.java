package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.ConfirmationToken;
import kg.nurtelecom.opinion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String confirmationToken);
    Optional<ConfirmationToken> findByUser(User user);
}
