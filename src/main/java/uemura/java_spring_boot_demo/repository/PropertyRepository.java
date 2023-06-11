package uemura.java_spring_boot_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uemura.java_spring_boot_demo.domais.entity.MovimentEntity;

@Repository
public interface PropertyRepository extends JpaRepository<MovimentEntity, Integer> {

}
