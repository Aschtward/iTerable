package com.example.demo.dao;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.models.ProductModel;
import com.example.demo.models.Produto;

@Component
public class ProductModelDao {


	@Autowired
	private ProdutoDao pDao;

	public ProductModel addProduto(String id, int i) {
		Produto produto = pDao.buscarProduto(id);
		ProductModel pModel = new ProductModel();
		pModel.setProduto(produto);
		pModel.setQuantidade(i);
		pModel.setTotal(produto.getPrice().multiply(new BigDecimal(i)));
		return pModel;
	}

}
