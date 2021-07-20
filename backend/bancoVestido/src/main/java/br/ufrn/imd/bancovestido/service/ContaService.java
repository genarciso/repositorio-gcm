package br.ufrn.imd.bancovestido.service;

import br.ufrn.imd.bancovestido.exception.ResourceNotFoundException;
import br.ufrn.imd.bancovestido.model.Conta;
import br.ufrn.imd.bancovestido.model.Pessoa;
import br.ufrn.imd.bancovestido.repository.ContaRepository;
import br.ufrn.imd.bancovestido.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ContaService {
    private final ContaRepository contaRepository;
    private final PessoaRepository pessoaRepository;

    public Conta findOne(Long id) throws ResourceNotFoundException {
        return this.contaRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public List<Conta> findAll() {
        return this.contaRepository.findAll();
    }

    public BigDecimal getSaldo(Long id) throws ResourceNotFoundException {
        Conta conta = findOne(id);
        return conta.getSaldo();
    }

    @Transactional
    public Conta save(Conta conta) throws ResourceNotFoundException {
        Conta contaBD = new Conta();

        if (conta.getId() != null) {
            contaBD = this.findOne(conta.getId());
        } else {
            Pessoa pessoa = this.pessoaRepository.findById(conta.getPessoa().getId())
                    .orElseThrow(ResourceNotFoundException::new);
            contaBD.setPessoa(pessoa);
        }

        BeanUtils.copyProperties(conta, contaBD, Conta.ignoreProperties);

        return this.contaRepository.save(contaBD);
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException {
        Conta conta = this.findOne(id);
        this.contaRepository.delete(conta);
    }

    @Transactional
    public void credito(Long id, BigDecimal valor) throws ResourceNotFoundException {
        Conta conta = this.findOne(id);
        conta.setSaldo(conta.getSaldo().add(valor));
        this.contaRepository.save(conta);
    }
}
