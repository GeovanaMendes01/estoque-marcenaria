package br.ufms.facom.progWeb.controllers;

import br.ufms.facom.progWeb.service.FornecedorService;
import br.ufms.facom.progWeb.models.Produto;
import br.ufms.facom.progWeb.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;
    private final FornecedorService fornecedorService;
    
    public ProdutoController(
            ProdutoService service,
            FornecedorService fornecedorService) {
            
        this.service = service;
        this.fornecedorService = fornecedorService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", service.getProdutos());
        return "produtos"; // renderiza produtos.html com a lista
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {

        model.addAttribute("fornecedores",
                fornecedorService.getFornecedores());

        return "cadastro";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto) {
        service.salvarProduto(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        model.addAttribute(
                "produto",
                service.getProduto(id)
        );

        model.addAttribute(
                "fornecedores",
                fornecedorService.getFornecedores()
        );

        return "editar";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Produto produto) {
        service.atualizarProduto(id, produto);
        return "redirect:/produtos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluirProduto(id);
        return "redirect:/produtos";
    }
}