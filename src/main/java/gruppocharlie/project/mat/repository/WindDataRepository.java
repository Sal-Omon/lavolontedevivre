package gruppocharlie.project.mat.repository;

import src.main.java.gruppocharlie.project.mat.model.WindData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WindDataRepository extends JpaRepository<WindData, Long> {
}
