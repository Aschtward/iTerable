package com.example.demo.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.models.FechamentoModel;
import com.example.demo.repository.FechamentoModelRepository;

@Controller
public class FechamentoController {

	@Autowired
	private FechamentoModelRepository caixaRepository;
	
	@GetMapping("/fechamento")
	public ModelAndView getFechamentosCaixa() {
		ModelAndView f = new ModelAndView("fechamento");
		List<FechamentoModel> fechamentos = caixaRepository.findAll();
		BigDecimal dinheiro = BigDecimal.ZERO;
		BigDecimal cartao = new BigDecimal(0);
		for(FechamentoModel fechamento: fechamentos) {
			dinheiro = dinheiro.add(fechamento.getDinheiro());
			cartao = cartao.add(fechamento.getCartao());
		}
		f.addObject("dinheiro", dinheiro);
		f.addObject("cartao", cartao);
		f.addObject("fechamentos", fechamentos);
		return f;
	}
	
	
	@RequestMapping("/caixa")
	public ModelAndView getFechamentosCaixaPersonalizado(@RequestParam(name = "diaInicial") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate diaInicial,
            @RequestParam(name = "diaFinal") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate diaFinal) {
		ModelAndView f = new ModelAndView("fechamento");
		List<FechamentoModel> fechamentos = caixaRepository.findByDiaBetweenOrderByDia(diaInicial, diaFinal);
		BigDecimal dinheiro = new BigDecimal(0);
		BigDecimal cartao = new BigDecimal(0);
		for(FechamentoModel fechamento: fechamentos) {
			dinheiro = dinheiro.add(fechamento.getDinheiro());
			cartao = cartao.add(fechamento.getCartao());
		}
		f.addObject("dinheiro", dinheiro);
		f.addObject("cartao", cartao);
		f.addObject("fechamentos", fechamentos);
		return f;
	}
	
	@RequestMapping("/buscames")
	public ModelAndView getFechamentosCaixaPersonalizadomes(@RequestParam String anomes, @RequestParam String mes) {
		ModelAndView f = new ModelAndView("fechamento");
		LocalDate diaInicial = LocalDate.of(Integer.valueOf(anomes), Integer.valueOf(mes), 1);
		LocalDate diaFinal = diaInicial.withDayOfMonth(diaInicial.lengthOfMonth());
		List<FechamentoModel> fechamentos = caixaRepository.findByDiaBetweenOrderByDia(diaInicial, diaFinal);
		BigDecimal dinheiro = new BigDecimal(0);
		BigDecimal cartao = new BigDecimal(0);
		for(FechamentoModel fechamento: fechamentos) {
			dinheiro = dinheiro.add(fechamento.getDinheiro());
			cartao = cartao.add(fechamento.getCartao());
		}
		f.addObject("dinheiro", dinheiro);
		f.addObject("cartao", cartao);
		f.addObject("fechamentos", fechamentos);
		return f;
	}
	
	@RequestMapping("/buscaano")
	public ModelAndView getFechamentosCaixaPersonalizadoano(@RequestParam String anoinicialBusca, @RequestParam String anofinalbusca) {
		ModelAndView f = new ModelAndView("fechamento");
		LocalDate diaInicial = LocalDate.of(Integer.valueOf(anoinicialBusca), 1, 1);
		LocalDate diaFinal = LocalDate.of(Integer.valueOf(anofinalbusca), 12, 31);
		List<FechamentoModel> fechamentos = caixaRepository.findByDiaBetweenOrderByDia(diaInicial, diaFinal);
		BigDecimal dinheiro = new BigDecimal(0);
		BigDecimal cartao = new BigDecimal(0);
		for(FechamentoModel fechamento: fechamentos) {
			dinheiro = dinheiro.add(fechamento.getDinheiro());
			cartao = cartao.add(fechamento.getCartao());
		}
		f.addObject("dinheiro", dinheiro);
		f.addObject("cartao", cartao);
		f.addObject("fechamentos", fechamentos);
		return f;
	}
}
