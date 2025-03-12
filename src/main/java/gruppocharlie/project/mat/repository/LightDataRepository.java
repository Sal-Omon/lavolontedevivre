package gruppocharlie.project.mat.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gruppocharlie.project.mat.model.LightData;

@Repository
public interface LightDataRepository extends JpaRepository<LightData, Long> {
}