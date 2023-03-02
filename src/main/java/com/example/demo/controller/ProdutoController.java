package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.dao.ProdutoDao;

@Controller
public class ProdutoController {
	
	@Autowired
	ProdutoDao proDAO;
	
	@GetMapping("/cadastrarProdutos")
	public RedirectView cadastrarProduto(){
		return new RedirectView("/listarProdutos");
	}
	
	@PostMapping("/criarProduto")
	public RedirectView cadastrarProdutos(@RequestParam String name, @RequestParam String quantidade, @RequestParam String value) {
		if(value.contains(",")) {
			value = value.replace(",", ".");
		}
		proDAO.adicionarNovoProduto(name, value, quantidade);
		return new RedirectView("/listarProdutos");
	}
	
	@GetMapping("/listarProdutos")
	public ModelAndView listarProdutos() {
		ModelAndView prodView = new ModelAndView("listarProdutos");
		prodView.addObject("produtos", proDAO.listar());
		return prodView;
	}
	
	@RequestMapping("/alterar")
	public RedirectView adicionarQuantidade(@RequestParam String name, @RequestParam String quantidade, @RequestParam String preco) {
		if(preco.contains(",")) {
			preco = preco.replace(",", ".");
		}
		if(Integer.valueOf(quantidade) > 0) {
			proDAO.aumentarQuantidade(Integer.valueOf(quantidade), name);
		}
		proDAO.trocarPreco(preco, name);
		return new RedirectView("/listarProdutos");
	}

}
