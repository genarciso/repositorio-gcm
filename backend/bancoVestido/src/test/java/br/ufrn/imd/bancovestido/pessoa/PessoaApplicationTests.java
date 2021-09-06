package br.ufrn.imd.bancovestido.pessoa;

import br.ufrn.imd.bancovestido.BancoVestidoApplication;
import br.ufrn.imd.bancovestido.exception.ResourceNotFoundException;
import br.ufrn.imd.bancovestido.model.Pessoa;
import br.ufrn.imd.bancovestido.service.PessoaService;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.util.Date;

@SpringBootTest(classes = {BancoVestidoApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PessoaApplicationTests {

    @Autowired
    private PessoaService pessoaService;

    private Pessoa pessoa;

    @BeforeEach
    public void setUp() throws ResourceNotFoundException{
        MockitoAnnotations.initMocks(this);
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Gabriel");
        pessoa.setDataNascimento(new Date());

        this.pessoa = pessoaService.save(pessoa);


    }

    @AfterTestClass
    public void tearDown() {
        this.pessoaService.findAll().forEach(it -> {
            try {
                this.pessoaService.delete(it.getId());
            } catch (ResourceNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    @Test
    public void verificarSalvarPessoas() throws ResourceNotFoundException {
        Pessoa pessoaMock = pessoa;
        pessoa = pessoaService.save(pessoaMock);
        Assertions.assertNotNull(pessoa.getId());


    }

    @Test
    public void procurarPessoa() throws ResourceNotFoundException {
        Assertions.assertNotNull(pessoaService.findOne(pessoa.getId()));
    }

    @Test
    public void verificarRemoverPessoas() throws ResourceNotFoundException {
        pessoaService.delete(pessoa.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            pessoaService.findOne(pessoa.getId());
        });
    }



}
