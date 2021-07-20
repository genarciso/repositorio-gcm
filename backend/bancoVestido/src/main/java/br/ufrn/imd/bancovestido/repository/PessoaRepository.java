package br.ufrn.imd.bancovestido.repository;

import br.ufrn.imd.bancovestido.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
