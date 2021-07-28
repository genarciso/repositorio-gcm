package br.ufrn.imd.bancovestido.enuns;

public enum TipoConta {
    CONTA_CORRENTE("Conta Corrente"),
    CONTA_BONUS("Conta bônus"),
    CONTA_POUPANCA("Conta Poupança");

    private String descricao;

    TipoConta(String descricao) {
        this.descricao = descricao;
    }
}
