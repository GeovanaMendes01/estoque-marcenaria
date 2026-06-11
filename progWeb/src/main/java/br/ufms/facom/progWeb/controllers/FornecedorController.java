package br.ufms.facom.progWeb.controllers;

import br.ufms.facom.progWeb.models.Fornecedor;
import br.ufms.facom.progWeb.service.FornecedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String cadastro(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        return "fornecedores/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Fornecedor fornecedor, Model model) {
        try {
            service.salvarFornecedor(fornecedor);
            return "redirect:/fornecedores";
        } catch (IllegalArgumentException e) {
            // CNPJ duplicado: volta pro formulário mostrando o erro em vez de quebrar a página
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("fornecedor", fornecedor);
            return "fornecedores/form";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("fornecedor", service.getFornecedor(id));
        return "fornecedores/form";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Fornecedor dadosNovos, Model model) {
        try {
            service.atualizarFornecedor(id, dadosNovos);
            return "redirect:/fornecedores";
        } catch (IllegalArgumentException e) {
            // CNPJ duplicado: volta pro formulário mostrando o erro em vez de quebrar a página
            model.addAttribute("erro", e.getMessage());
            dadosNovos.setId(id);
            model.addAttribute("fornecedor", dadosNovos);
            return "fornecedores/form";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.excluirFornecedor(id);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/fornecedores";
    }
}