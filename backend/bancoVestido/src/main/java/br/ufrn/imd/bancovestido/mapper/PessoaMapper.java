package br.ufrn.imd.bancovestido.mapper;

import br.ufrn.imd.bancovestido.DTO.PessoaDTO;
import br.ufrn.imd.bancovestido.model.Pessoa;

public class PessoaMapper {
    public static PessoaDTO map(Pessoa pessoa) {
        PessoaDTO pessoaDTO = new PessoaDTO();

        pessoaDTO.setId(pessoa.getId());
        pessoaDTO.setNome(pessoa.getNome());
        pessoaDTO.setDataNascimento(pessoa.getDataNascimento());

        return pessoaDTO;
    }
}
