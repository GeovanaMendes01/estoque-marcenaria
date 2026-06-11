package br.ufms.facom.progWeb.controllers;

import br.ufms.facom.progWeb.service.FornecedorService;
import br.ufms.facom.progWeb.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProdutoService produtoService;
    private final FornecedorService fornecedorService;

    public HomeController(ProdutoService produtoService, FornecedorService fornecedorService) {
        this.produtoService = produtoService;
        this.fornecedorService = fornecedorService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalProdutos",    produtoService.getProdutos().size());
        model.addAttribute("totalFornecedores", fornecedorService.getFornecedores().size());
        model.addAttribute("totalEmAlerta",    produtoService.contarProdutosEmAlerta());
        return "index";
    }
}