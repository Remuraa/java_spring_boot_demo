package uemura.java_spring_boot_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uemura.java_spring_boot_demo.domais.SalesEntity;

@Repository
public interface SalesRepository extends JpaRepository<SalesEntity, Long> {
}

