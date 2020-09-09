package com.estertz.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estertz.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
