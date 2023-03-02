package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Produto;

public interface ProductRepository extends JpaRepository<Produto, Long>{

	Optional<Produto> findByName(String name);
}
