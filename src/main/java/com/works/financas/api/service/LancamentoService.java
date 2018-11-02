package com.works.financas.api.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.works.financas.api.dto.LancamentoEstatisticaPessoa;
import com.works.financas.api.mail.Mailer;
//import com.works.financas.api.mail.Mailer;
import com.works.financas.api.model.Lancamento;
import com.works.financas.api.repository.LancamentoRepository;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LancamentoService {
	
	private static final Logger logger = LoggerFactory.getLogger(LancamentoService.class);
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private Mailer mailer;
	
	 private static final String TIME_ZONE = "America/Sao_Paulo";
	
	 @Scheduled(cron = "* 02 14 * * *", zone = TIME_ZONE)
	//@Scheduled(fixedDelay = 1000 * 60 * 1)
	public void avisarSobreLancamentosVencidos() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Preparando envio de "
					+ "e-mails de aviso de lançamentos vencidos.");
		}
		
		List<Lancamento> vencidos = lancamentoRepository
				.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());
		
		if (vencidos.isEmpty()) {
			logger.info("Sem lançamentos vencidos para aviso.");
			
			return;
		}
		
		logger.info("Exitem {} lançamentos vencidos.", vencidos.size());
		
		//List<Usuario> destinatarios = usuarioRepository.findByPermissoesDescricao(DESTINATARIOS);
		
		mailer.avisarSobreLancamentosVencidos(vencidos, "casilvamg@gmail.com");
		
		logger.info("Envio de e-mail de aviso concluído.");
		
		
		System.out.println(">>>>>>>>>>>>>>> Método sendo executado...");
	}
	 
	@Scheduled(cron = "0 30 17 2 * *", zone = TIME_ZONE)
	public void cadastrarLancamentosParceladosMesCorrente() {
			
			if (logger.isDebugEnabled()) {
				logger.debug("Preparando cadastro de lançamentos em parcelas.");
			}
			
			List<Lancamento> lancs = lancamentoRepository
					.findByLancamentosMesAnteriorAndParcelaNotEqualZero();
			
			for (Lancamento lanc : lancs) {
								
				Lancamento lancSalvo = new Lancamento();
				BeanUtils.copyProperties(lanc, lancSalvo, "codigo");	
				
				lancSalvo.setParcela(lanc.getParcela() - 1);
				lancSalvo.setDataVencimento(lanc.getDataVencimento().plusMonths(1));
				lancSalvo.setDataPagamento(null);
												
				Lancamento l = lancamentoRepository.save(lancSalvo);
				System.out.println("Lancamento " + l.getCodigo() + " criado");
						
			}
			
			System.out.println(">>>>>>>>>>>>>>> Método executado...");
	}
	
	/*public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws Exception {
		List<LancamentoEstatisticaPessoa> dados = lancamentoRepository.porPessoa(inicio, fim);
		<String, Object> parametros = new HashMap<>();
		parametros.put("DT_INICIO", Date.valueOf(inicio));
		parametros.put("DT_FIM", Date.valueOf(fim));
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
				
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/relatorios/lancamento-por-pessoa.jasper");
		
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros,
				new JRBeanCollectionDataSource(dados));
		
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}*/

	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarLancamentoPeloCodigo(codigo);
		
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
		return lancamentoRepository.save(lancamentoSalvo);
	}
	
	//public void atualizarPropriedadeStatus(Long codigo, String status) {
		//Lancamento lancamentoSalvo = buscarLancamentoPeloCodigo(codigo);
		//lancamentoSalvo.setStatus(StatusLancamento.valueOf(status));
		//lancamentoRepository.save(lancamentoSalvo);
	//}
	
	private Lancamento buscarLancamentoPeloCodigo(Long codigo) {
		Optional<Lancamento> lancamentoSalvo = lancamentoRepository.findById(codigo);
		if (!lancamentoSalvo.isPresent()) {
			throw new IllegalArgumentException();
		}
		return lancamentoSalvo.get();
	}
}
