package br.ufms.facom.progWeb.controllers;

import br.ufms.facom.progWeb.models.Baixa;
import br.ufms.facom.progWeb.service.BaixaService;
import br.ufms.facom.progWeb.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/baixas")
public class BaixaController {

    private final BaixaService service;
    private final ProdutoService produtoService;

    public BaixaController(BaixaService service, ProdutoService produtoService) {
        this.service = service;
        this.produtoService = produtoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("baixas", service.getBaixas());
        return "baixas/index";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        // Passa a lista de produtos pro formulário
        model.addAttribute("produtos", produtoService.getProdutos());
        return "baixas/cadastro";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Baixa baixa) {
        service.registrarBaixa(baixa);
        return "redirect:/baixas";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluirBaixa(id);
        return "redirect:/baixas";
    }
}