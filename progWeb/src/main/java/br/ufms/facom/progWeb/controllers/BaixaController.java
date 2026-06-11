package br.ufms.facom.progWeb.controllers;

import br.ufms.facom.progWeb.models.Baixa;
import br.ufms.facom.progWeb.service.BaixaService;
import br.ufms.facom.progWeb.service.ProdutoService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @GetMapping("/exportar")
    public ResponseEntity<byte[]> exportar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim
    ) throws IOException {

        List<Baixa> baixas = service.getBaixasPorPeriodo(dataInicio, dataFim);

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Baixas");

        // Cabeçalho
        Row header = sheet.createRow(0);
        String[] colunas = {"Data", "Produto", "Quantidade"};
        for (int i = 0; i < colunas.length; i++) {
            header.createCell(i).setCellValue(colunas[i]);
        }

        // Dados
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int rowNum = 1;
        for (Baixa b : baixas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(b.getData() != null ? b.getData().format(formatter) : "");
            row.createCell(1).setCellValue(b.getProduto() != null ? b.getProduto().getNome() : "");
            row.createCell(2).setCellValue(b.getQuantidade() != null ? b.getQuantidade() : 0);
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
        headers.setContentDispositionFormData("attachment", "baixas.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(out.toByteArray());
    }
}