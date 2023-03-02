package com.example.demo.models;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;

@Entity
public class MesaModel {
	
	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	private Long id;
	@Column
	private int number;
	@OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
	@JoinTable(name = "TB_MESA_PROD", joinColumns = @JoinColumn(name = "mesa_id"), inverseJoinColumns = @JoinColumn(name = "productmodel_id"))
	private List<ProductModel> product;
	@Column
	private BigDecimal total;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public List<ProductModel> getProduct() {
		return product;
	}
	public void setProduct(List<ProductModel> product) {
		this.product = product;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	
}
