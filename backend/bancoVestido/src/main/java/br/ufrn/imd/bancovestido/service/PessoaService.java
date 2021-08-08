package br.ufrn.imd.bancovestido.service;

import br.ufrn.imd.bancovestido.exception.ResourceNotFoundException;
import br.ufrn.imd.bancovestido.model.Pessoa;
import br.ufrn.imd.bancovestido.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {
    private final PessoaRepository pessoaRepository;

    public Pessoa findOne(Long id) throws ResourceNotFoundException {
        return this.pessoaRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public List<Pessoa> findAll() {
        return this.pessoaRepository.findAll();
    }

    @Transactional
    public Pessoa save(Pessoa pessoa) throws ResourceNotFoundException {
        Pessoa pessoaBD = new Pessoa();

        if (pessoa.getId() != null) {
            pessoaBD = this.findOne(pessoa.getId());
        }

        BeanUtils.copyProperties(pessoa, pessoaBD, Pessoa.ignoreProperties);

        return this.pessoaRepository.save(pessoaBD);
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException {
        Pessoa pessoa = this.findOne(id);
        this.pessoaRepository.delete(pessoa);
    }
}
