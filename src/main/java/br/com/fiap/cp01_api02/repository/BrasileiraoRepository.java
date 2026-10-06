package br.com.fiap.cp01_api02.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.cp01_api02.model.Brasileirao;

public interface BrasileiraoRepository extends JpaRepository<Brasileirao, Long> {
    
}
