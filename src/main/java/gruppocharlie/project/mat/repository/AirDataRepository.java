package gruppocharlie.project.mat.repository;

import src.main.java.gruppocharlie.project.mat.model.AirData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirDataRepository extends JpaRepository<AirData, Long> {
}
