package br.ufrn.imd.bancovestido.repository;

import br.ufrn.imd.bancovestido.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
}
