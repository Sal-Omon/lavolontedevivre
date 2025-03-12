package gruppocharlie.project.mat.repository;

import src.main.java.gruppocharlie.project.mat.model.SoilData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoilDataRepository extends JpaRepository<SoilData, Long> {
}