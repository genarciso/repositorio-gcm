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

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest(classes = {BancoVestidoApplication.class})
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

    @AfterEach
    public void tearDown() throws ResourceNotFoundException {
        this.contaService.delete(this.contaFinal.getNumeroConta());
        this.pessoaService.delete(this.pessoaFinal.getId());
        this.contaService.delete(this.contaInicio.getNumeroConta());
        this.pessoaService.delete(this.pessoaInicio.getId());

    }

    @BeforeEach
    public void setUp() throws ResourceNotFoundException, InvalidValueException {
        MockitoAnnotations.initMocks(this);
        Pessoa pessoaInicio = new Pessoa();
        pessoaInicio.setNome("André Ricardo");
        pessoaInicio.setDataNascimento(new Date());

        this.pessoaInicio = this.pessoaService.save(pessoaInicio);

        Conta contaInicio = new Conta();
        contaInicio.setPessoa(this.pessoaInicio);
        contaInicio.setNumeroConta("1001");
        contaInicio.setSaldo(new BigDecimal(6000));

        this.contaInicio = this.contaService.save(contaInicio);

        Pessoa pessoaFinal = new Pessoa();
        pessoaFinal.setNome("Gabriel Estevam");
        pessoaFinal.setDataNascimento(new Date());

        this.pessoaFinal = this.pessoaService.save(pessoaFinal);

        Conta contaFinal = new Conta();
        contaFinal.setPessoa(this.pessoaFinal);
        contaFinal.setNumeroConta("1002");
        contaFinal.setSaldo(new BigDecimal(4000));

        this.contaFinal = this.contaService.save(contaFinal);
    }

    @Test
    public void transferindoValorCorretoContaPoupanca() throws ResourceNotFoundException, InvalidValueException {
        BigDecimal valor = new BigDecimal(400);
        this.contaInicio.setTipoConta(TipoConta.CONTA_POUPANCA);
        this.contaService.save(this.contaInicio);

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
        this.contaService.save(this.contaInicio);

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
        this.contaService.save(this.contaInicio);

        this.contaService.transferencia(this.contaInicio.getNumeroConta(), this.contaFinal.getNumeroConta(), valor);

        this.contaInicio = this.contaService.findOne(this.contaInicio.getNumeroConta());
        this.contaFinal = this.contaService.findOne( this.contaFinal.getNumeroConta() );

        BigDecimal novoValorContaInicio = new BigDecimal(5600);
        BigDecimal novoValorContaFinal = new BigDecimal(4400);

        Assertions.assertEquals(novoValorContaInicio.doubleValue(), this.contaInicio.getSaldo().doubleValue());
        Assertions.assertEquals(novoValorContaFinal.doubleValue(), this.contaFinal.getSaldo().doubleValue());
    }


}
