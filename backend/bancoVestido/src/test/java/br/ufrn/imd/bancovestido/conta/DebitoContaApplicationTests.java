package br.ufrn.imd.bancovestido.conta;

import br.ufrn.imd.bancovestido.BancoVestidoApplication;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {BancoVestidoApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DebitoContaApplicationTests {
}
