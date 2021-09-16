package br.ufrn.imd.bancovestido.conta;

import br.ufrn.imd.bancovestido.BancoVestidoApplication;
import br.ufrn.imd.bancovestido.enuns.TipoConta;
import br.ufrn.imd.bancovestido.exception.InvalidValueException;
import br.ufrn.imd.bancovestido.exception.ResourceNotFoundException;
import br.ufrn.imd.bancovestido.model.Conta;
import br.ufrn.imd.bancovestido.model.Pessoa;
import br.ufrn.imd.bancovestido.service.ContaService;
import br.ufrn.imd.bancovestido.service.PessoaService;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest(classes = {BancoVestidoApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCreditoOperation {

    @Autowired
    private ContaService contaService;

    @Autowired
    private PessoaService pessoaService;

    private Conta conta;
    private Pessoa pessoa;

    @BeforeEach
    public void setUp() throws ResourceNotFoundException, InvalidValueException {
        MockitoAnnotations.initMocks(this);

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("André Ricardo");
        pessoa.setDataNascimento(new Date());

        this.pessoa = this.pessoaService.save(pessoa);

        Conta conta = new Conta();
        conta.setPessoa(this.pessoa);
        conta.setNumeroConta("1000");
        conta.setSaldo(new BigDecimal(6000));

        this.conta  = this.contaService.save(conta);
    }

    @AfterEach
    public void tearDown() throws ResourceNotFoundException {
        this.contaService.delete(this.conta.getNumeroConta());
        this.pessoaService.delete(this.pessoa.getId());
    }

    @Test
    public void creditandoContaPoupanca() throws ResourceNotFoundException, InvalidValueException {
        BigDecimal valor = new BigDecimal(500);
        this.conta.setTipoConta(TipoConta.CONTA_POUPANCA);
        this.contaService.save(this.conta);
        this.contaService.credito(this.conta.getNumeroConta(), valor, 100);
        this.conta = this.contaService.findOne(this.conta.getNumeroConta());
        Assertions.assertEquals(new BigDecimal(6500).doubleValue(), this.conta.getSaldo().doubleValue());
    }

    @Test
    public void creditandoContaCorrente() throws ResourceNotFoundException, InvalidValueException {
        BigDecimal valor = new BigDecimal(500);
        this.conta.setTipoConta(TipoConta.CONTA_CORRENTE);
        this.contaService.save(this.conta);
        this.contaService.credito(this.conta.getNumeroConta(), valor, 100);
        this.conta = this.contaService.findOne(this.conta.getNumeroConta());
        Assertions.assertEquals(new BigDecimal(6500).doubleValue(), this.conta.getSaldo().doubleValue());
    }

    @Test
    public void creditandoContaBonus() throws ResourceNotFoundException, InvalidValueException {
        BigDecimal valor = new BigDecimal(500);
        this.conta.setTipoConta(TipoConta.CONTA_BONUS);
        this.conta.setPontuacao(100);
        this.contaService.save(this.conta);
        this.contaService.credito(this.conta.getNumeroConta(), valor, 100);
        this.conta = this.contaService.findOne(this.conta.getNumeroConta());
        Assertions.assertEquals(new BigDecimal(6500).doubleValue(), this.conta.getSaldo().doubleValue());
        Assertions.assertEquals(105, this.conta.getPontuacao());
    }






}
