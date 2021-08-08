package br.ufrn.imd.bancovestido.service;

import br.ufrn.imd.bancovestido.enuns.TipoConta;
import br.ufrn.imd.bancovestido.exception.InvalidValueException;
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

@Service
@RequiredArgsConstructor
public class ContaService {
    private final ContaRepository contaRepository;
    private final PessoaRepository pessoaRepository;

    public Conta findOne(String id) throws ResourceNotFoundException {
        return this.contaRepository.findByNumeroConta(id).orElseThrow(ResourceNotFoundException::new);
    }

    public List<Conta> findAll() {
        return this.contaRepository.findAll();
    }

    public BigDecimal getSaldo(String id) throws ResourceNotFoundException {
        Conta conta = findOne(id);
        return conta.getSaldo();
    }

    @Transactional
    public Conta save(Conta conta) throws ResourceNotFoundException, InvalidValueException {
        Conta contaBD = new Conta();

        if (conta.getId() != null) {
            contaBD = this.findOne(conta.getNumeroConta());
        } else {
            Pessoa pessoa = this.pessoaRepository.findById(conta.getPessoa().getId())
                    .orElseThrow(ResourceNotFoundException::new);
            contaBD.setPessoa(pessoa);

            if (conta.getTipoConta() == TipoConta.CONTA_BONUS) {
                conta.setPontuacao(10);
            }

            if (conta.getTipoConta() == TipoConta.CONTA_POUPANCA && conta.getSaldo().doubleValue() < 0) {
                throw new InvalidValueException("Poupança não pode ter um saldo negativo");

            }
        }

        BeanUtils.copyProperties(conta, contaBD, Conta.ignoreProperties);

        return this.contaRepository.save(contaBD);
    }

    @Transactional
    public void delete(String id) throws ResourceNotFoundException {
        Conta conta = this.findOne(id);
        this.contaRepository.delete(conta);
    }

    @Transactional
    public void debito(String id, BigDecimal valor) throws ResourceNotFoundException, InvalidValueException {
        Conta conta = this.findOne(id);
        if (conta.getTipoConta() == TipoConta.CONTA_POUPANCA && conta.getSaldo().subtract(valor).doubleValue() < 0) {
            throw new InvalidValueException("Valor insuficiente para debito");

        }
        conta.setSaldo(conta.getSaldo().subtract(valor));
        this.contaRepository.save(conta);
    }

    @Transactional
    public void credito(String id, BigDecimal valor, int valorPontuacao) throws ResourceNotFoundException {
        Conta conta = this.findOne(id);
        conta.setSaldo(conta.getSaldo().add(valor));
        if (conta.getTipoConta() == TipoConta.CONTA_BONUS) {
            conta.setPontuacao(conta.getPontuacao() + (int) (valor.doubleValue() / valorPontuacao));
        }
        this.contaRepository.save(conta);
    }

    @Transactional
    public void renderJuros(String id, float juros) throws ResourceNotFoundException, InvalidValueException {
        Conta conta = this.findOne(id);

        if (conta.getTipoConta() == TipoConta.CONTA_POUPANCA){
            BigDecimal porcentagem = new BigDecimal(Float.toString(juros/100));
            BigDecimal valor = conta.getSaldo().multiply(porcentagem);
            conta.setSaldo(conta.getSaldo().add(valor));
            this.contaRepository.save(conta);
        } else {
            throw new InvalidValueException("Operação válida apenas para conta poupança");
        }

    }

    @Transactional
    public void transferencia(String idConta, String idContaDestino, BigDecimal valor) throws ResourceNotFoundException, InvalidValueException {
        Conta conta = this.findOne(idConta);
        if (conta.getTipoConta() == TipoConta.CONTA_POUPANCA && conta.getSaldo().subtract(valor).doubleValue() < 0) {
            throw new InvalidValueException("Valor insuficiente para transferência");

        }

        if (conta.getSaldo().doubleValue() >= valor.doubleValue()) {
            debito(idConta, valor);
            credito(idContaDestino, valor, 150);
        } else {
            throw new InvalidValueException("Valor insuficiente para transferência");
        }
    }
}
