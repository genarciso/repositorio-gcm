package br.ufrn.imd.bancovestido;

import br.ufrn.imd.bancovestido.enuns.TipoConta;
import br.ufrn.imd.bancovestido.exception.InvalidValueException;
import br.ufrn.imd.bancovestido.exception.ResourceNotFoundException;
import br.ufrn.imd.bancovestido.model.Conta;
import br.ufrn.imd.bancovestido.model.Pessoa;
import br.ufrn.imd.bancovestido.service.ContaService;
import br.ufrn.imd.bancovestido.service.PessoaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest(classes = {BancoVestidoApplicationTests.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCreditoOperation {

    @Autowired
    private ContaService contaService;

    @Autowired
    private PessoaService pessoaService;

    private Conta conta;
    private Pessoa pessoa;

    public TestCreditoOperation(){

    }

    @BeforeEach
    private void transferPreparation() throws ResourceNotFoundException, InvalidValueException {
        MockitoAnnotations.initMocks(this);
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("André Ricardo");
        pessoa.setDataNascimento(new Date());

        pessoa = this.pessoaService.save(pessoa);
        this.pessoa = this.pessoaService.findOne(pessoa.getId());

        Conta conta = new Conta();
        conta.setPessoa(pessoa);
        conta.setNumeroConta("1208");
        conta.setSaldo(new BigDecimal(6000));

        conta = this.contaService.save(conta);
        this.conta = this.contaService.findOne(conta.getNumeroConta());
    }

    @Test
    public void creditandoContaPoupanca() throws ResourceNotFoundException {
        BigDecimal valor = new BigDecimal(500);
        this.conta.setTipoConta(TipoConta.CONTA_POUPANCA);
        this.contaService.credito(this.conta.getNumeroConta(), valor, 100);
        this.conta = this.contaService.findOne(this.conta.getNumeroConta());
        Assertions.assertEquals(new BigDecimal(6500).doubleValue(), this.conta.getSaldo().doubleValue());
    }

    @Test
    public void creditandoContaCorrente() throws ResourceNotFoundException {
        BigDecimal valor = new BigDecimal(500);
        this.conta.setTipoConta(TipoConta.CONTA_CORRENTE);
        this.contaService.credito(this.conta.getNumeroConta(), valor, 100);
        this.conta = this.contaService.findOne(this.conta.getNumeroConta());
        Assertions.assertEquals(new BigDecimal(6500).doubleValue(), this.conta.getSaldo().doubleValue());
    }

    @Test
    public void creditandoContaBonus() throws ResourceNotFoundException {
        BigDecimal valor = new BigDecimal(500);
        this.conta.setTipoConta(TipoConta.CONTA_BONUS);
        this.conta.setPontuacao(100);
        this.contaService.credito(this.conta.getNumeroConta(), valor, 100);
        this.conta = this.contaService.findOne(this.conta.getNumeroConta());
        Assertions.assertEquals(new BigDecimal(6500).doubleValue(), this.conta.getSaldo().doubleValue());
        Assertions.assertEquals(200, this.conta.getPontuacao());
    }






}
