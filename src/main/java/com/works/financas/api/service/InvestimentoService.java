package com.works.financas.api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.works.financas.api.dto.InvestimentoRendimentoResgate;
import com.works.financas.api.model.Investimento;
import com.works.financas.api.model.TipoInvestimento;
import com.works.financas.api.repository.InvestimentoRepository;
import com.works.financas.api.repository.RendimentoRepository;


@Service
public class InvestimentoService {
	
	@Autowired
	private InvestimentoRepository InvestimentoRepository;
	
	@Autowired
	private RendimentoRepository rendimentoRepository;

	public Investimento atualizar(Long codigo, Investimento Investimento) {
		Investimento invSalvo = buscarInvestimentoPeloCodigo(codigo);
		
		BeanUtils.copyProperties(Investimento, invSalvo, "codigo");
		return InvestimentoRepository.save(invSalvo);
	}

	//public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		//Investimento pessoaSalva = buscarInvestimentoPeloCodigo(codigo);
		//.setAtivo(ativo);
		//InvestimentoRepository.save(pessoaSalva);
	//}
	
	public Investimento buscarInvestimentoPeloCodigo(Long codigo) {
		Optional<Investimento> investimentoSalvo = InvestimentoRepository.findById(codigo);
		if (!investimentoSalvo.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return investimentoSalvo.get();
	}
	
	public InvestimentoRendimentoResgate buscarValorLiquidoResgate(Investimento investimento) {
		
		InvestimentoRendimentoResgate invResgate = new InvestimentoRendimentoResgate();
		invResgate.setNome(investimento.getNome());
		
		if (investimento.getTipo().equals(TipoInvestimento.FUNDOS)) {									
			long qdadeDias = InvestimentoService.dataDiferencaDias(investimento.getDataAdesao());			
			BigDecimal aliquotaIR = InvestimentoService.getAliquotaIR(investimento, qdadeDias);					
			BigDecimal valorResgateComJuros = rendimentoRepository.getResgateBruto(investimento.getCodigo());
			BigDecimal valorAntecComeCotas = rendimentoRepository.getComeCotas(investimento.getCodigo());
			BigDecimal valorInicial = investimento.getValor();
			BigDecimal descontoIR = valorResgateComJuros.subtract(valorInicial).multiply(aliquotaIR).divide(new BigDecimal("100"));			
			invResgate.setResgate(valorResgateComJuros.subtract(valorAntecComeCotas.subtract(descontoIR)));			
		}
		else if (investimento.getTipo().equals(TipoInvestimento.PREVIDENCIA)) {		
			long qdadeDias = InvestimentoService.dataDiferencaDias(investimento.getDataAdesao());			
			BigDecimal aliquotaIR = InvestimentoService.getAliquotaIR(investimento, qdadeDias);			
			BigDecimal valorResgateComJuros = rendimentoRepository.getResgateBruto(investimento.getCodigo());
			BigDecimal valorInicial = investimento.getValor();
			BigDecimal descontoIR = valorResgateComJuros.subtract(valorInicial).multiply(aliquotaIR).divide(new BigDecimal("100"));			
			invResgate.setResgate(valorResgateComJuros.subtract(descontoIR));		
		}
		else if (investimento.getTipo().equals(TipoInvestimento.POUPANCA)) {
			BigDecimal resgateBruto = rendimentoRepository.getResgateBruto(investimento.getCodigo());
			invResgate.setResgate(resgateBruto);
		}
		else if (investimento.getTipo().equals(TipoInvestimento.CAPITALIZACAO)) {
			
			long qdadeMeses = InvestimentoService.dataDiferencaMeses(investimento.getDataAdesao());	
			BigDecimal resgatePerc = null;
									
				switch ((int)qdadeMeses) {
					case 1:
						resgatePerc = new BigDecimal("89.929");
						break;
					case 2:
						resgatePerc = new BigDecimal("90.474"); 
						break;
					case 3:
						resgatePerc = new BigDecimal("91.022");
						break;
					case 4:
						resgatePerc = new BigDecimal("91.573");
						break;
					case 5:
						resgatePerc = new BigDecimal("92.127");
						break;
					case 6:
						resgatePerc = new BigDecimal("96.082");
						break;
					case 7:
						resgatePerc = new BigDecimal("96.757");
						break;
					case 8:
						resgatePerc = new BigDecimal("97.535");
						break;
					case 9:
						resgatePerc = new BigDecimal("98.515");
						break;
					case 10:
						resgatePerc = new BigDecimal("99.008");
						break;
					case 11:
						resgatePerc = new BigDecimal("99.503");
						break;
					case 12:
						resgatePerc = new BigDecimal("100.000");
						break;
				}
			
			BigDecimal resgateBruto = rendimentoRepository.getResgateBruto(investimento.getCodigo());
			
			BigDecimal resgateLiquido =  resgateBruto.multiply(resgatePerc).divide(new BigDecimal("100"));
			
			invResgate.setResgate(resgateLiquido);
		}
		return invResgate;	
	}
	
	public static long dataDiferencaDias(LocalDate inicio) {					
		LocalDate hoje = LocalDate.now();		
		long intervalo = ChronoUnit.DAYS.between(inicio, hoje); // Calcula a diferença de dias entre as duas datas	
	    return intervalo;
	}
	
	public static long dataDiferencaMeses(LocalDate inicio) {					
		LocalDate hoje = LocalDate.now();		
		long intervalo = ChronoUnit.MONTHS.between(inicio, hoje); // Calcula a diferença de meses entre as duas datas	
	    return intervalo;
	}
	
	public static BigDecimal getAliquotaIR(Investimento inv, long qdadeDias) {
		
		BigDecimal retorno = null;
		
		if (inv.getTipo().equals(TipoInvestimento.FUNDOS)) {			
			if (qdadeDias <= 180) {
				retorno = new BigDecimal("22.5");
			}
			else if (qdadeDias > 180 && qdadeDias <= 360) {
				retorno = new BigDecimal("20");
			}			
			else if (qdadeDias > 361 && qdadeDias <= 720) {
				retorno = new BigDecimal("17.5");
			}
			else {
				retorno = new BigDecimal("15");
			}			
		}
		else if (inv.getTipo().equals(TipoInvestimento.PREVIDENCIA)) {			
			if (qdadeDias <= 730) {
				retorno = new BigDecimal("35");
			}
			else if (qdadeDias > 730 && qdadeDias <= 1460) {
				retorno = new BigDecimal("30");
			}			
			else if (qdadeDias > 1460 && qdadeDias <= 2190) {
				retorno = new BigDecimal("25");
			}
			else if (qdadeDias > 2190 && qdadeDias <= 2920) {
				retorno = new BigDecimal("20");
			}
			else if (qdadeDias > 2920 && qdadeDias <= 3650) {
				retorno = new BigDecimal("15");
			}
			else {
				retorno = new BigDecimal("10");
			}
		}
		return retorno;
	}
}