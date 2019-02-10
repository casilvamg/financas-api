package com.works.financas.api.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.persistence.CascadeType;

import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Rendimento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@NotNull(message = "Date de vencimento é obrigatória")
	//@DateTimeFormat(pattern = "dd/MM/yyyy")
	//@Temporal(TemporalType.DATE)
	//private Date dataVencimento;
	
	@NotNull(message = "Date de Vencimento é obrigatória")
	//@DateTimeFormat(pattern = "dd/MM/yyyy")
	//@Temporal(TemporalType.DATE)
	private LocalDate dataVencimento;

	@NotNull(message = "Valor é obrigatório")
	@DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0,01")
	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor;
	
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorComJuros;
	
	@NotNull(message = "Juros é obrigatório")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal juros;
	
	@NotNull(message = "IR é obrigatório")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal IR;
	
	@Transient
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal rendimentoPct;
	
	@Transient
	private int mes_corrente;

	// bi-directional many-to-one association to Site
	@ManyToOne(cascade = CascadeType.REFRESH, optional=true)
	//@ManyToOne
	@JsonIgnoreProperties({"descricao","dataAdesao","valor","tipo","tipoir","rendimentos"})
	@JoinColumn(name = "codigo")
	private Investimento investimento;

	public Long getId() {
		return id;
	}

	public void setId(Long codigo) {
		this.id = codigo;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public BigDecimal getJuros() {
		return juros;
	}

	public void setJuros(BigDecimal juros) {
		this.juros = juros;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorComJuros() {
		
		if (this.getIR() == null || this.getJuros() == null ) {
			return this.valorComJuros;
		}
		
		//if (this.getInvestimento().getTipo().getDescricao().equals("Poupança")) {
			return this.getValor().add(this.getJuros()).subtract(this.getIR());
		//}
		//else
			//return valorComJuros;
	}

	public void setValorComJuros(BigDecimal valorComJuros) {
		this.valorComJuros = valorComJuros;
	}

	public Investimento getInvestimento() {  
		return investimento;
	}

	public void setInvestimento(Investimento investimento) {
		this.investimento = investimento;
	}
	
	public BigDecimal getRendimentoPct() {
		if (this.getJuros() == null || this.getValor() == null) {
			return rendimentoPct;
		}
		
		return this.getJuros().multiply(new BigDecimal(100)).divide(this.getValor(), 3, RoundingMode.CEILING);
	}

	public void setRendimentoPct(BigDecimal rendimentoPct) {
		this.rendimentoPct = rendimentoPct;
	}

	public BigDecimal getIR() {
		return IR;
	}

	public void setIR(BigDecimal iR) {
		IR = iR;
	}
	
	public int getMes_corrente() {
		return mes_corrente;
	}

	public void setMes_corrente(int mes_corrente) {
		this.mes_corrente = mes_corrente;
	}
}
