package gruppocharlie.project.mat.repository;

import gruppocharlie.project.mat.model.SoilData;
import gruppocharlie.project.mat.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoilDataRepository extends JpaRepository<SoilData, Long> {
    List<SoilData> findByUser(UserData user);
}