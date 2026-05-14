package br.ufms.facom.progWeb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProdutoController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/produtos")
    public String produtos() {
        return "produtos";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }

    @GetMapping("/editar")
    public String editar() {
        return "editar";
    }

    @GetMapping("/excluir")
    public String excluir() {
        return "excluir";
    }
}