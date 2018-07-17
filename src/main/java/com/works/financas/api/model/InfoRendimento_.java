package com.works.financas.api.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(InfoRendimento.class)
public abstract class InfoRendimento_ {

	public static volatile SingularAttribute<InfoRendimento, BigDecimal> valorResgate;
	public static volatile SingularAttribute<InfoRendimento, BigDecimal> rendPerc;
	public static volatile SingularAttribute<InfoRendimento, BigDecimal> IR;
	public static volatile SingularAttribute<InfoRendimento, Long> id;
	public static volatile SingularAttribute<InfoRendimento, BigDecimal> saldoBruto;
	public static volatile SingularAttribute<InfoRendimento, BigDecimal> rendBruto;
	public static volatile SingularAttribute<InfoRendimento, Investimento> investimento;
	public static volatile SingularAttribute<InfoRendimento, String> previsaoResgate;

}

