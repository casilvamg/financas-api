package com.works.financas.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.works.financas.api.model.base.ABaseEntity;

@Entity
public class Investimento extends ABaseEntity<String> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	//@NotEmpty(message = "Descrição é obrigatória") 
	@Size(max = 60, message = "A descrição não pode conter mais de 60 caracteres")
	private String descricao;
	
	//@NotBlank(message = "Nome é uma informação obrigatória.")
	private String nome;

	//@NotNull(message = "Date de adesão é obrigatória")
	//@DateTimeFormat(pattern = "dd/MM/yyyy")
	//@Temporal(TemporalType.DATE)
	/// Date dataAdesao;
	
	@NotNull(message = "Date de adesão é obrigatória")
	//@DateTimeFormat(pattern = "dd/MM/yyyy")
	//@Temporal(TemporalType.DATE)
	private LocalDate dataAdesao;
	
	
	@NotNull(message = "Valor é obrigatório")
	@DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0,01")
	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor;
	
	@Enumerated(EnumType.STRING)
	private TipoInvestimento tipo;
	
	@Enumerated(EnumType.STRING)
	private TipoIR tipoir;

	@OneToMany(mappedBy = "investimento")
	private List<Rendimento> rendimentos;

	
	public TipoInvestimento getTipo() {
		return tipo;
	}

	public void setTipo(TipoInvestimento tipo) {
		this.tipo = tipo;
	}

	public List<Rendimento> getRendimentos() {
		return rendimentos;
	}

	public void setRendimentos(List<Rendimento> rendimentos) {
		this.rendimentos = rendimentos;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDate getDataAdesao() {
		return dataAdesao;
	}

	public void setDataAdesao(LocalDate dataAdesao) {
		this.dataAdesao = dataAdesao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public TipoIR getTipoir() {
		return tipoir;
	}

	public void setTipoir(TipoIR tipoir) {
		this.tipoir = tipoir;
	}
	
	@JsonIgnore
	@Override
	public long getEntityIdentifier() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//public boolean hasRendimento() {
		//if (this.getRendimentos().isEmpty()) {
			//return false;
		//}
		//else {
		//	return true;
		//}
	//}
}

