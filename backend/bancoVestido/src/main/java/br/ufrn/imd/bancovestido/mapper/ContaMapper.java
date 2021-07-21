package br.ufrn.imd.bancovestido.mapper;

import br.ufrn.imd.bancovestido.DTO.ContaDTO;
import br.ufrn.imd.bancovestido.model.Conta;

public class ContaMapper {
    public static ContaDTO map(Conta conta) {
        ContaDTO contaDTO = new ContaDTO();

        contaDTO.setId(conta.getId());
        contaDTO.setNumeroConta(conta.getNumeroConta());
        contaDTO.setTipoConta(conta.getTipoConta());
        contaDTO.setSaldo(conta.getSaldo());
        contaDTO.setPessoa(PessoaMapper.map(conta.getPessoa()));

        return contaDTO;
    }

    public static Conta map(ContaDTO contaDTO) {
        Conta conta = new Conta();

        conta.setId(contaDTO.getId());
        conta.setNumeroConta(contaDTO.getNumeroConta());
        conta.setTipoConta(contaDTO.getTipoConta());
        conta.setSaldo(contaDTO.getSaldo());
        conta.setPessoa(PessoaMapper.map(contaDTO.getPessoa()));

        return conta;
    }
}
