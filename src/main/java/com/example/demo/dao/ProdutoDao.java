package com.example.demo.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.models.Produto;
import com.example.demo.repository.ProductRepository;

@Component
public class ProdutoDao {

	@Autowired
	private ProductRepository repository;

	public void adicionarNovoProduto(String name, String value, String quantidade) {
		Produto produto = new Produto();
		produto.setName(name);
		produto.setPrice(new BigDecimal(value));
		produto.setQuantity(Integer.valueOf(quantidade));
		repository.save(produto);
	}

	public void removerProduto(String name) {
		Optional<Produto> produto = repository.findByName(name);
		if (produto != null) {
			repository.delete(produto.get());
		}
	}

	public void aumentarQuantidade(int i, String name) {
		Optional<Produto> produto = repository.findByName(name);
		if (produto != null) {
			produto.get().setQuantity(produto.get().getQuantity() + i);
			repository.save(produto.get());
		}
	}
	
	public void trocarPreco(String preco,String name) {
		Optional<Produto> produto = repository.findByName(name);
		if (produto != null) {
			produto.get().setPrice(new BigDecimal(preco));;
			repository.save(produto.get());
		}
	}

	public Produto buscarProduto(String id) {
		Optional<Produto> produto = repository.findById(Long.valueOf(id));
		if (produto != null) {
			return produto.get();
		}
		return null;
	}

	public boolean venderProduto(String id, int i) {
		Optional<Produto> produto = repository.findById(Long.valueOf(id));
		produto.get().setQuantity(produto.get().getQuantity() - i);
		if (produto.get().getQuantity() >= 0) {
			repository.save(produto.get());
			return true;
		}
		return false;
	}

	public List<Produto> listar() {
		return repository.findAll();
	}
}
