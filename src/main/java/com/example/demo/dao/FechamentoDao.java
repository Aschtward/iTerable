package com.example.demo.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.models.FechamentoModel;
import com.example.demo.repository.FechamentoModelRepository;

@Component
public class FechamentoDao {
	
	@Autowired
	FechamentoModelRepository repository;
	
	public void adicionarPagamento(String valor, String forma) {
		Optional<FechamentoModel> fM = repository.findByDia(LocalDate.now());
		if(fM.isPresent()) {
			if(forma.equals("cartao")) {
				fM.get().setCartao(fM.get().getCartao().add(new BigDecimal(valor)));
				repository.save(fM.get());
				return;
			}
			fM.get().setDinheiro(fM.get().getDinheiro().add(new BigDecimal(valor)));
			repository.save(fM.get());
			return;
		}
		if(forma.equals("cartao")) {
			FechamentoModel fE = new FechamentoModel(LocalDate.now(), BigDecimal.ZERO, new BigDecimal(valor));
			repository.save(fE);
			return;
		}
		FechamentoModel fE = new FechamentoModel(LocalDate.now(), new BigDecimal(valor), BigDecimal.ZERO);
		repository.save(fE);
	}

}
