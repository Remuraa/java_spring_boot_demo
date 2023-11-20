package uemura.java_spring_boot_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uemura.java_spring_boot_demo.domais.entity.PropertyEntity;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Integer> {

    List<PropertyEntity> findAllByYear(int year);
}
