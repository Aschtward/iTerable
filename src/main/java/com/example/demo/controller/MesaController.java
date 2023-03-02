package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.dao.MesaDao;
import com.example.demo.dao.ProdutoDao;
import com.example.demo.models.MesaModel;

@Controller
public class MesaController {

	@Autowired
	private MesaDao mDao;

	@Autowired
	private ProdutoDao pDao;

	@GetMapping("mesa/{number}")
	public ModelAndView mostrarMesa(@PathVariable("number") String number) {
		Optional<MesaModel> m = mDao.buscarMesa(number);
		ModelAndView modelAndView = new ModelAndView("mesa");
		if (m.get().getTotal() == null) {
			m.get().setTotal(new BigDecimal(0));
		}
		modelAndView.addObject("total", m.get().getTotal());
		modelAndView.addObject("mesas", m.get());
		modelAndView.addObject("produtos", m.get().getProduct());
		modelAndView.addObject("todosProdutos", pDao.listar());
		return modelAndView;
	}

	@GetMapping("/")
	public ModelAndView listarMesas() {
		ModelAndView modelAndView = new ModelAndView("index");
		List<MesaModel> mesas = mDao.listarMesa();
		if (mesas.isEmpty()) {
			mesas = new ArrayList<MesaModel>();
			for (int i = 0; i < 30; i++) {
				mDao.criarMesa(String.valueOf(i + 1));
			}
		}
		modelAndView.addObject("mesa", mesas);
		return modelAndView;
	}

	@RequestMapping("mesa/remover/{number}/{id}")
	public RedirectView removerProduto(@PathVariable("number") String number, @PathVariable("id") String id) {
		mDao.removerProdutoMesa(number, id);
		return new RedirectView("/mesa/" + number);
	}
	
	@RequestMapping("mesa/adicionar/{number}/{id}")
	public RedirectView adicionarProduto(@PathVariable("number") String number, @PathVariable("id") String id) {
		mDao.adicionarProdutoMesa(number, id,1);
		return new RedirectView("/mesa/" + number);
	}

	@PostMapping("mesa/{number}")
	public RedirectView addProdutoMesa(@RequestParam String name, @RequestParam String quantidade,
			@PathVariable("number") String number) {
		mDao.adicionarProdutoMesa(number, name, Integer.valueOf(quantidade));
		return new RedirectView("/mesa/" + number);
	}
	
	@PostMapping("mesa/pagar/{number}")
	public RedirectView pagarMesa(@PathVariable("number") String number, @RequestParam String valor, @RequestParam String forma) {
		if(valor.contains(",")) {
			valor = valor.replace(",", ".");
		}
		mDao.pagarMesa(number, valor, forma);
		return new RedirectView("/mesa/" + number);
	}
}
