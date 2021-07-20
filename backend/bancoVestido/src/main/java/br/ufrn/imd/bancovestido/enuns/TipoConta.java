package br.ufrn.imd.bancovestido.enum;
public enum TipoConta {
    CONTA_CORRENTE("Conta Corrente"),
    CONTA_POUPANCA("Conta Poupança");

    private String descricao;

    TipoConta(String descricao) {
        this.descricao = descricao;
    }
}
