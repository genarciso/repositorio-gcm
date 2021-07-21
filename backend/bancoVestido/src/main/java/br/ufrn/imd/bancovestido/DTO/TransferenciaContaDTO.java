package br.ufrn.imd.bancovestido.DTO;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransferenciaContaDTO {
    public String idConta;
    public String idContaDestino;
    public BigDecimal valor;
}
