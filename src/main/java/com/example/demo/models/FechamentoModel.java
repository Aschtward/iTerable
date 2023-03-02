package com.example.demo.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FechamentoModel {
	
	@Id
	private Long id;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column
	private LocalDate dia;
	@Column
	private BigDecimal dinheiro;
	@Column
	private BigDecimal cartao;
	
	public FechamentoModel() {
	}
	
	public FechamentoModel(LocalDate dia, BigDecimal dinheiro, BigDecimal cartao) {
		super();
		this.id = Long.parseLong(dia.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + 0000);
		this.dia = dia;
		this.dinheiro = dinheiro;
		this.cartao = cartao;
	}
	
	public LocalDate getDia() {
		return dia;
	}
	public void setDia(LocalDate dia) {
		this.dia = dia;
	}
	public BigDecimal getDinheiro() {
		return dinheiro;
	}
	public void setDinheiro(BigDecimal dinheiro) {
		this.dinheiro = dinheiro;
	}
	public BigDecimal getCartao() {
		return cartao;
	}
	public void setCartao(BigDecimal cartao) {
		this.cartao = cartao;
	}
}
