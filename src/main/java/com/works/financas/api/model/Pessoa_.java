package com.works.financas.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pessoa.class)
public abstract class Pessoa_ extends com.works.financas.api.model.base.ABaseEntity_ {

	public static volatile SingularAttribute<Pessoa, Long> codigo;
	public static volatile SingularAttribute<Pessoa, Boolean> ativo;
	public static volatile ListAttribute<Pessoa, Contato> contatos;
	public static volatile SingularAttribute<Pessoa, String> nome;

}

