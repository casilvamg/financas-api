package com.works.financas.api.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Investimento.class)
public abstract class Investimento_ extends com.works.financas.api.model.base.ABaseEntity_ {

	public static volatile SingularAttribute<Investimento, Long> codigo;
	public static volatile SingularAttribute<Investimento, TipoInvestimento> tipo;
	public static volatile SingularAttribute<Investimento, Date> dataAdesao;
	public static volatile SingularAttribute<Investimento, BigDecimal> valor;
	public static volatile SingularAttribute<Investimento, String> nome;
	public static volatile SingularAttribute<Investimento, String> descricao;

}

