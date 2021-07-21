package br.ufrn.imd.bancovestido.DTO;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransferenciaContaDTO {
    public Long idConta;
    public Long idContaDestino;
    public BigDecimal valor;
}
