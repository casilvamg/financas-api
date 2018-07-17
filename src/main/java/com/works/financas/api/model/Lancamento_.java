package com.works.financas.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Lancamento.class)
public abstract class Lancamento_ extends com.works.financas.api.model.base.ABaseEntity_ {

	public static volatile SingularAttribute<Lancamento, Long> codigo;
	public static volatile SingularAttribute<Lancamento, SituacaoLancamento> situacao;
	public static volatile SingularAttribute<Lancamento, String> observacao;
	public static volatile SingularAttribute<Lancamento, LocalDate> dataPagamento;
	public static volatile SingularAttribute<Lancamento, LocalDate> dataVencimento;
	public static volatile SingularAttribute<Lancamento, BigDecimal> valor;
	public static volatile SingularAttribute<Lancamento, String> email;
	public static volatile SingularAttribute<Lancamento, String> descricao;
	public static volatile SingularAttribute<Lancamento, StatusLancamento> status;

}

