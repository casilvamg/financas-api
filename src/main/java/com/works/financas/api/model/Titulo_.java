package com.works.financas.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Titulo.class)
public abstract class Titulo_ extends com.works.financas.api.model.base.ABaseEntity_ {

	public static volatile SingularAttribute<Titulo, Long> codigo;
	public static volatile SingularAttribute<Titulo, SituacaoTitulo> situacao;
	public static volatile SingularAttribute<Titulo, LocalDate> dataVencimento;
	public static volatile SingularAttribute<Titulo, BigDecimal> valor;
	public static volatile SingularAttribute<Titulo, String> email;
	public static volatile SingularAttribute<Titulo, String> descricao;
	public static volatile SingularAttribute<Titulo, StatusTitulo> status;

}

