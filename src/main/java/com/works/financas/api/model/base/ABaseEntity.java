package com.works.financas.api.model.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;


/*
 * 
 * A auditoria de JPA permite registrar quem, o que e quando para cada objeto de entidade.
 *  Passos necessários
 *  1.) Anotações (@CreatedDate, @CreatedBy, @LastModifiedDate and @LastModifiedBy. @Entity, @EntityListeners, @MappedSuperclass)
 *  2.) Adicionar nova classe de configuração que permita a auditoria de JPA (anotação @EnableJpaAuditing)
 * 
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) //listener usado para acionar a captura de informações de auditoria
public abstract class ABaseEntity<U> {

	 @JsonIgnore
	 @CreatedBy
	 @Column(name = "created_by", updatable = false)
	 protected String createdBy;

	 @JsonIgnore
	 @CreatedDate
	 @Column(name = "created_date", updatable = false)
	 private Date createdDate;

	 @JsonIgnore // ignorar atributo na serialização
	 @LastModifiedBy
	 protected String lastModifiedBy;

	 @JsonIgnore
	 @LastModifiedDate
	 protected Date lastModifiedDate;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date creationDate) {
		this.createdDate = creationDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public long getEntityIdentifier() {
		// TODO Auto-generated method stub
		return 0;
	}
}
