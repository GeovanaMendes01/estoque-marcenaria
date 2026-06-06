package br.ufms.facom.progWeb.controllers;

import br.ufms.facom.progWeb.models.Fornecedor;
import br.ufms.facom.progWeb.repositories.FornecedorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// Controlador MVC responsável pelas rotas de Fornecedor
@Controller
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorRepository fornecedorRepository;

    // Injeção de dependência via construtor
    public FornecedorController(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    // Lista todos os fornecedores
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        return "fornecedores/lista";
    }

    // Exibe o formulário para cadastrar um novo fornecedor
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        return "fornecedores/form";
    }

    // Salva um fornecedor novo ou editado e redireciona para a lista
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Fornecedor fornecedor) {
        fornecedorRepository.save(fornecedor);
        return "redirect:/fornecedores";
    }

    // Carrega os dados do fornecedor para edição
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado: " + id));
        model.addAttribute("fornecedor", fornecedor);
        return "fornecedores/form";
    }

    // Exclui o fornecedor pelo id e redireciona para a lista
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        fornecedorRepository.deleteById(id);
        return "redirect:/fornecedores";
    }
}
