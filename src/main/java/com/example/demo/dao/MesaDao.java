package com.example.demo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.models.MesaModel;
import com.example.demo.models.ProductModel;
import com.example.demo.models.Produto;
import com.example.demo.repository.MesaRepository;
import com.example.demo.repository.ProductModelRepository;

@Component
public class MesaDao {

	@Autowired
	private MesaRepository repository;
	@Autowired
	private ProductModelDao pDao;
	@Autowired
	private ProdutoDao produtoDao;
	@Autowired
	private FechamentoDao fechamentoDao;
	@Autowired
	private ProductModelRepository repoProdModel;

	public void criarMesa(String number) {
		MesaModel mesa = new MesaModel();
		mesa.setNumber(Integer.valueOf(number));
		mesa.setProduct(null);
		mesa.setTotal(new BigDecimal(0));
		mesa.setProduct(new ArrayList<ProductModel>());
		repository.save(mesa);
	}

	public void adicionarProdutoMesa(String number, String id, int i) {
		if (i > 0) {
			Optional<MesaModel> mesa = repository.findByNumber(Integer.valueOf(number));
			Map<Long, Object> mapeamento = mesa.get().getProduct().stream()
					.collect(Collectors.toMap(ProductModel::getRefId, produto -> produto));
			if (mapeamento.containsKey(Long.valueOf(id))) {
				int index = mesa.get().getProduct().indexOf(mapeamento.get(Long.valueOf(id)));
				BigDecimal valor = mesa.get().getProduct().get(index).getProduto().getPrice()
						.multiply(new BigDecimal(i));
				int novaQuantidade = mesa.get().getProduct().get(index).getQuantidade() + i;
				mesa.get().getProduct().get(index).setTotal(mesa.get().getProduct().get(index).getTotal().add(valor));
				mesa.get().getProduct().get(index).setQuantidade(novaQuantidade);
				mesa.get().setTotal(mesa.get().getTotal().add(valor));
				repository.save(mesa.get());
				return;
			}
			ProductModel pModel = pDao.addProduto(id, i);
			mesa.get().getProduct().add(pModel);
			mesa.get().setTotal(mesa.get().getTotal().add(pModel.getTotal()));
			repoProdModel.save(pModel);
			repository.save(mesa.get());
		}
	}

	public void removerProdutoMesa(String number, String id) {
		Optional<MesaModel> mesa = repository.findByNumber(Integer.valueOf(number));
		Map<Long, Object> mapeamento = mesa.get().getProduct().stream()
				.collect(Collectors.toMap(ProductModel::getId, produto -> produto));
		if (mapeamento.containsKey(Long.valueOf(id))) {
			int i = mesa.get().getProduct().indexOf(mapeamento.get(Long.valueOf(id)));
			mesa.get().setTotal(
					mesa.get().getTotal().subtract(mesa.get().getProduct().get(i).getProduto().getPrice()));
			mesa.get().getProduct().get(i).setQuantidade(mesa.get().getProduct().get(i).getQuantidade() - 1);
			mesa.get().getProduct().get(i).setTotal(mesa.get().getProduct().get(i).getTotal()
					.subtract(mesa.get().getProduct().get(i).getProduto().getPrice()));
			produtoDao.aumentarQuantidade(1, mesa.get().getProduct().get(i).getProduto().getName());
			if (mesa.get().getProduct().get(i).getQuantidade() == 0) {
				mesa.get().getProduct().remove(i);
			}
			repository.save(mesa.get());
		}
	}

	public void pagarMesa(String number, String valor, String forma) {
		Optional<MesaModel> mesa = repository.findByNumber(Integer.valueOf(number));
		mesa.get().setTotal(mesa.get().getTotal().subtract(new BigDecimal(valor)));
		if (mesa.get().getTotal().compareTo(BigDecimal.ZERO) == 0) {
			mesa.get().getProduct().clear();
		}
		fechamentoDao.adicionarPagamento(valor, forma);
		repository.save(mesa.get());
	}

	public List<ProductModel> listarProdutosMesa(String number) {
		Optional<MesaModel> mesa = repository.findByNumber(Integer.valueOf(number));
		return mesa.get().getProduct();
	}

	public Optional<MesaModel> buscarMesa(String number) {
		return repository.findByNumber(Integer.valueOf(number));
	}

	public List<MesaModel> listarMesa() {
		return repository.findAll();
	}

}
