package br.ufrn.imd.bancovestido.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PessoaDTO extends TipoDTO {
    private String nome;
    private Date dataNascimento;
}
