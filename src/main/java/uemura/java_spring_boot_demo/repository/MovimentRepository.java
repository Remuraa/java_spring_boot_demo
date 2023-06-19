package uemura.java_spring_boot_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uemura.java_spring_boot_demo.domais.entity.MovimentEntity;
import uemura.java_spring_boot_demo.enums.IrMovimentEnum;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface MovimentRepository extends JpaRepository<MovimentEntity, Integer> {

    List<MovimentEntity> findAllByMovimentDateBetweenAndMoviment(LocalDate startDate, LocalDate endDate, IrMovimentEnum moviment);
}
