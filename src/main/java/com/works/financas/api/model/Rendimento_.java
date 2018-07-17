package com.works.financas.api.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Rendimento.class)
public abstract class Rendimento_ {

	public static volatile SingularAttribute<Rendimento, BigDecimal> valorComJuros;
	public static volatile SingularAttribute<Rendimento, BigDecimal> rendimentoPct;
	public static volatile SingularAttribute<Rendimento, Date> dataVencimento;
	public static volatile SingularAttribute<Rendimento, BigDecimal> juros;
	public static volatile SingularAttribute<Rendimento, BigDecimal> valor;
	public static volatile SingularAttribute<Rendimento, BigDecimal> IR;
	public static volatile SingularAttribute<Rendimento, Long> id;
	public static volatile SingularAttribute<Rendimento, Investimento> investimento;

}

