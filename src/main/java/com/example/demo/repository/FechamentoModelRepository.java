package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.FechamentoModel;

public interface FechamentoModelRepository extends JpaRepository<FechamentoModel, Long>{

	Optional<FechamentoModel> findByDia(LocalDate dia);

	List<FechamentoModel> findByDiaBetweenOrderByDia(LocalDate diaInicial,
			LocalDate diaFinal);
}
