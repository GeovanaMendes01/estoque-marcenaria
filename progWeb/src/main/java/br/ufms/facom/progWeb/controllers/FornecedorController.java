package br.ufms.facom.progWeb.controllers;

import br.ufms.facom.progWeb.models.Fornecedor;
import br.ufms.facom.progWeb.service.FornecedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService service;

    public FornecedorController(FornecedorService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("fornecedores", service.getFornecedores());
        return "fornecedores/index";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "fornecedores/cadastro";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Fornecedor fornecedor) {
        service.salvarFornecedor(fornecedor);
        return "redirect:/fornecedores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("fornecedor", service.getFornecedor(id));
        return "fornecedores/editar";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Fornecedor dadosNovos) {
        service.atualizarFornecedor(id, dadosNovos);
        return "redirect:/fornecedores";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluirFornecedor(id);
        return "redirect:/fornecedores";
    }
}