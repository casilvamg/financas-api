package com.works.financas.api.model.base;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ABaseEntity.class)
public abstract class ABaseEntity_ {

	public static volatile SingularAttribute<ABaseEntity, Date> createdDate;
	public static volatile SingularAttribute<ABaseEntity, Object> createdBy;
	public static volatile SingularAttribute<ABaseEntity, Date> lastModifiedDate;
	public static volatile SingularAttribute<ABaseEntity, Object> lastModifiedBy;
	public static volatile SingularAttribute<ABaseEntity, Long> entityIdentifier;

}

