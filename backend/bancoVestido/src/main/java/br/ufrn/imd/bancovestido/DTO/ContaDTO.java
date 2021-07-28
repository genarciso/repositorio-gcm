package br.ufrn.imd.bancovestido.DTO;

import br.ufrn.imd.bancovestido.enuns.TipoConta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ContaDTO {
    private Long id;
    private String numeroConta;
    private TipoConta tipoConta;
    private int pontuacao = 0;
    private BigDecimal saldo = BigDecimal.ZERO;
    private PessoaDTO pessoa;
}
