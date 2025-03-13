package gruppocharlie.project.mat.repository;


import gruppocharlie.project.mat.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gruppocharlie.project.mat.model.LightData;

import java.util.List;

@Repository
public interface LightDataRepository extends JpaRepository<LightData, Long> {
    List<LightData> findByUser(UserData user);
}