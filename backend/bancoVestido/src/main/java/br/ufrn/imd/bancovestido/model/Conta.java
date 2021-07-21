package br.ufrn.imd.bancovestido.model;

import br.ufrn.imd.bancovestido.enuns.TipoConta;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "conta")
@EqualsAndHashCode(of = {"id"})
public class Conta {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "tipo_conta")
    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta = TipoConta.CONTA_CORRENTE;

    @Column(name = "saldo")
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "numero_conta", unique = true)
    private String numeroConta = "";

    @Column(name = "data_criacao")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date dataCriacao = new Date();

    @Column(name = "data_ultima_edicao")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date dataUltimaEdicao = new Date();

    @Column(name = "versao")
    @Version
    private Long versao = 0L;

    @JoinColumn(name = "id_pessoa")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pessoa pessoa;

    public static String[] ignoreProperties = {"id", "dataCriacao", "dataUltimaEdicao", "versao", "pessoa"};
}
