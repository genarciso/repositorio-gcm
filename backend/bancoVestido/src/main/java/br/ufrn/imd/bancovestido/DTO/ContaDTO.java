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
    private TipoConta tipoConta;
    private BigDecimal saldo;
    private PessoaDTO pessoa;
}
