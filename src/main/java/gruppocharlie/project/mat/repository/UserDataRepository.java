package gruppocharlie.project.mat.repository;

import gruppocharlie.project.mat.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    Optional<UserData> findByUsername(String username);
}

