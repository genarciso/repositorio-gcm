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

class TestTransferenciaOperation {
    @Autowired
    private ContaService contaService;

    @Autowired
    private PessoaService pessoaService;

    private Conta contaInicio;

    private Conta contaFinal;

    private Pessoa pessoaInicio;

    private Pessoa pessoaFinal;

    public TestTransferenciaOperation(){

    }

    @BeforeEach
    private void transferPreparation() throws ResourceNotFoundException, InvalidValueException {
        MockitoAnnotations.initMocks(this);
        Pessoa pessoaInicio = new Pessoa();
        pessoaInicio.setNome("André Ricardo");
        pessoaInicio.setDataNascimento(new Date());

        pessoaInicio = this.pessoaService.save(pessoaInicio);
        this.pessoaInicio = this.pessoaService.findOne(pessoaInicio.getId());

        Conta contaInicio = new Conta();
        contaInicio.setPessoa(pessoaInicio);
        contaInicio.setNumeroConta("1208");
        contaInicio.setSaldo(new BigDecimal(6000));

        contaInicio = this.contaService.save(contaInicio);
        this.contaInicio = this.contaService.findOne(contaInicio.getNumeroConta());

        Pessoa pessoaFinal = new Pessoa();
        pessoaFinal.setNome("Gabriel Estevam");
        pessoaFinal.setDataNascimento(new Date());

        pessoaFinal = this.pessoaService.save(pessoaFinal);
        this.pessoaFinal = this.pessoaService.findOne(pessoaFinal.getId());

        Conta contaFinal = new Conta();
        contaFinal.setPessoa(pessoaFinal);
        contaFinal.setNumeroConta("1712");
        contaFinal.setSaldo(new BigDecimal(4000));

        contaFinal = this.contaService.save(contaFinal);
        this.contaFinal = this.contaService.findOne(contaFinal.getNumeroConta());
    }

    @Test
    public void transferindoValorCorretoContaPoupanca() throws ResourceNotFoundException, InvalidValueException {
        BigDecimal valor = new BigDecimal(400);
        this.contaInicio.setTipoConta(TipoConta.CONTA_POUPANCA);
        this.contaService.transferencia(this.contaInicio.getNumeroConta(), this.contaFinal.getNumeroConta(), valor);

        this.contaInicio = this.contaService.findOne(this.contaInicio.getNumeroConta());
        this.contaFinal = this.contaService.findOne( this.contaFinal.getNumeroConta() );

        BigDecimal novoValorContaInicio = new BigDecimal(5600);
        BigDecimal novoValorContaFinal = new BigDecimal(4400);

        Assertions.assertEquals(novoValorContaInicio.doubleValue(), this.contaInicio.getSaldo().doubleValue());
        Assertions.assertEquals(novoValorContaFinal.doubleValue(), this.contaFinal.getSaldo().doubleValue());
    }

    @Test
    public void transferindoValorCorretoContaCorrente() throws ResourceNotFoundException, InvalidValueException {
        BigDecimal valor = new BigDecimal(400);
        this.contaInicio.setTipoConta(TipoConta.CONTA_CORRENTE);
        this.contaService.transferencia(this.contaInicio.getNumeroConta(), this.contaFinal.getNumeroConta(), valor);

        this.contaInicio = this.contaService.findOne(this.contaInicio.getNumeroConta());
        this.contaFinal = this.contaService.findOne( this.contaFinal.getNumeroConta() );

        BigDecimal novoValorContaInicio = new BigDecimal(5600);
        BigDecimal novoValorContaFinal = new BigDecimal(4400);

        Assertions.assertEquals(novoValorContaInicio.doubleValue(), this.contaInicio.getSaldo().doubleValue());
        Assertions.assertEquals(novoValorContaFinal.doubleValue(), this.contaFinal.getSaldo().doubleValue());
    }

    @Test
    public void transferindoValorCorretoContaBonus() throws ResourceNotFoundException, InvalidValueException {
        BigDecimal valor = new BigDecimal(400);
        this.contaInicio.setTipoConta(TipoConta.CONTA_BONUS);
        this.contaService.transferencia(this.contaInicio.getNumeroConta(), this.contaFinal.getNumeroConta(), valor);

        this.contaInicio = this.contaService.findOne(this.contaInicio.getNumeroConta());
        this.contaFinal = this.contaService.findOne( this.contaFinal.getNumeroConta() );

        BigDecimal novoValorContaInicio = new BigDecimal(5600);
        BigDecimal novoValorContaFinal = new BigDecimal(4400);

        Assertions.assertEquals(novoValorContaInicio.doubleValue(), this.contaInicio.getSaldo().doubleValue());
        Assertions.assertEquals(novoValorContaFinal.doubleValue(), this.contaFinal.getSaldo().doubleValue());
    }


}
