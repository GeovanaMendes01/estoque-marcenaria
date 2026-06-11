package br.ufms.facom.progWeb.controllers;

import br.ufms.facom.progWeb.models.Produto;
import br.ufms.facom.progWeb.service.FornecedorService;
import br.ufms.facom.progWeb.service.ProdutoService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

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
        return "produtos";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("fornecedores", fornecedorService.getFornecedores());
        return "cadastro";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto, Model model) {
        if (produto.getFornecedor() == null || produto.getFornecedor().getId() == null) {
            model.addAttribute("erro", "Selecione um fornecedor existente para o produto.");
            model.addAttribute("produto", produto);
            model.addAttribute("fornecedores", fornecedorService.getFornecedores());
            return "cadastro";
        }

        service.salvarProduto(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("produto", service.getProduto(id));
        model.addAttribute("fornecedores", fornecedorService.getFornecedores());
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

    @GetMapping("/exportar")
    public ResponseEntity<byte[]> exportar() throws IOException {

        List<Produto> produtos = service.getProdutos();

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Produtos");

        // Cabeçalho
        Row header = sheet.createRow(0);
        String[] colunas = {"Nome", "Descrição", "Quantidade", "Preço (R$)", "Fornecedor"};
        for (int i = 0; i < colunas.length; i++) {
            header.createCell(i).setCellValue(colunas[i]);
        }

        // Dados
        int rowNum = 1;
        for (Produto p : produtos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(p.getNome() != null ? p.getNome() : "");
            row.createCell(1).setCellValue(p.getDescricao() != null ? p.getDescricao() : "");
            row.createCell(2).setCellValue(p.getQuantidade() != null ? p.getQuantidade() : 0);
            row.createCell(3).setCellValue(p.getPreco() != null ? p.getPreco() : 0.0);
            row.createCell(4).setCellValue(
                    p.getFornecedor() != null ? p.getFornecedor().getNome() : "Sem fornecedor"
            );
        }

        // Ajusta largura das colunas automaticamente
        for (int i = 0; i < colunas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        ));
        headers.setContentDispositionFormData("attachment", "produtos.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(out.toByteArray());
    }
}