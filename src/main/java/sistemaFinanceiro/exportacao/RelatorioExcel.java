package sistemaFinanceiro.exportacao;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import sistemaFinanceiro.modelo.Lancamento;
import sistemaFinanceiro.modelo.RelatorioMensal;
import sistemaFinanceiro.modelo.enums.TipoCategoria;

public class RelatorioExcel {

    public Path exportar(RelatorioMensal relatorio) throws IOException {
        return exportar(relatorio, Paths.get("relatorios"));
    }

    public Path exportar(RelatorioMensal relatorio, Path pastaDestino) throws IOException {
        if(relatorio == null)
            throw new IllegalArgumentException("O relatorio nao pode ser nulo.");

        if(pastaDestino == null)
            throw new IllegalArgumentException("A pasta de destino nao pode ser nula.");

        Files.createDirectories(pastaDestino);

        String nomeArquivo = String.format(
            "relatorio-mensal-%02d-%d.xlsx",
            relatorio.getMes(),
            relatorio.getAno()
        );

        Path arquivo = pastaDestino.resolve(nomeArquivo);

        try(Workbook workbook = new XSSFWorkbook()) {
            CellStyle estiloTitulo = criarEstiloTitulo(workbook);
            CellStyle estiloSecao = criarEstiloSecao(workbook);
            CellStyle estiloCabecalho = criarEstiloCabecalho(workbook);
            CellStyle estiloRotulo = criarEstiloRotulo(workbook);
            CellStyle estiloMoeda = criarEstiloMoeda(workbook);
            CellStyle estiloData = criarEstiloData(workbook);

            criarAbaResumo(
                workbook,
                relatorio,
                estiloTitulo,
                estiloSecao,
                estiloCabecalho,
                estiloRotulo,
                estiloMoeda
            );

            criarAbaLancamentos(
                workbook,
                relatorio,
                estiloCabecalho,
                estiloMoeda,
                estiloData
            );

            try(OutputStream saida = Files.newOutputStream(arquivo)) {
                workbook.write(saida);
            }
        }

        return arquivo.toAbsolutePath();
    }

    private void criarAbaResumo(Workbook workbook, RelatorioMensal relatorio, CellStyle estiloTitulo, CellStyle estiloSecao, CellStyle estiloCabecalho, CellStyle estiloRotulo, CellStyle estiloMoeda) {
        Sheet sheet = workbook.createSheet("Resumo");

        criarLinhaMesclada(
            sheet,
            0,
            String.format(
                "RELATÓRIO MENSAL - %02d/%d",
                relatorio.getMes(),
                relatorio.getAno()
            ),
            estiloTitulo
        );

        Row periodo = sheet.createRow(2);
        criarCelula(periodo, 0, "Período", estiloRotulo);

        criarCelula(
            periodo,
            1,
            String.format("%02d/%d", relatorio.getMes(), relatorio.getAno()),
            null
        );

        adicionarValorResumo(
            sheet,
            3,
            "Total de receitas",
            relatorio.getTotalReceitas(),
            estiloRotulo,
            estiloMoeda
        );

        adicionarValorResumo(
            sheet,
            4,
            "Total de despesas",
            relatorio.getTotalDespesas(),
            estiloRotulo,
            estiloMoeda
        );

        adicionarValorResumo(
            sheet,
            5,
            "Saldo do período",
            relatorio.getSaldo(),
            estiloRotulo,
            estiloMoeda
        );

        int proximaLinha = escreverCategorias(
            sheet,
            7,
            "RECEITAS POR CATEGORIA",
            relatorio.getReceitasPorCategoria(),
            "Nenhuma receita no período.",
            estiloSecao,
            estiloCabecalho,
            estiloMoeda
        );

        escreverCategorias(
            sheet,
            proximaLinha + 1,
            "DESPESAS POR CATEGORIA",
            relatorio.getDespesasPorCategoria(),
            "Nenhuma despesa no período.",
            estiloSecao,
            estiloCabecalho,
            estiloMoeda
        );

        sheet.setColumnWidth(0, 32 * 256);
        sheet.setColumnWidth(1, 20 * 256);
    }

    private int escreverCategorias(Sheet sheet, int linhaInicial, String titulo, Map<TipoCategoria, BigDecimal> valores, String mensagemVazia, CellStyle estiloSecao, CellStyle estiloCabecalho, CellStyle estiloMoeda) {
        criarLinhaMesclada(
            sheet,
            linhaInicial,
            titulo,
            estiloSecao
        );

        Row cabecalho = sheet.createRow(linhaInicial + 1);
        criarCelula(cabecalho, 0, "Categoria", estiloCabecalho);
        criarCelula(cabecalho, 1, "Valor", estiloCabecalho);

        int linhaAtual = linhaInicial + 2;

        if(valores.isEmpty()) {
            Row linha = sheet.createRow(linhaAtual);
            criarCelula(linha, 0, mensagemVazia, null);

            sheet.addMergedRegion(
                new CellRangeAddress(linhaAtual, linhaAtual, 0, 1)
            );

            return linhaAtual + 1;
        }

        for(Map.Entry<TipoCategoria, BigDecimal> resultado : valores.entrySet()) {
            Row linha = sheet.createRow(linhaAtual);

            criarCelula(
                linha,
                0,
                resultado.getKey().toString(),
                null
            );

            Cell celulaValor = linha.createCell(1);
            celulaValor.setCellValue(resultado.getValue().doubleValue());
            celulaValor.setCellStyle(estiloMoeda);

            linhaAtual++;
        }

        return linhaAtual;
    }

    private void criarAbaLancamentos(Workbook workbook, RelatorioMensal relatorio, CellStyle estiloCabecalho, CellStyle estiloMoeda, CellStyle estiloData) {
        Sheet sheet = workbook.createSheet("Lancamentos");

        String[] colunas = {
            "ID",
            "Data",
            "Tipo",
            "Categoria",
            "Meio de movimentação",
            "Valor"
        };

        Row cabecalho = sheet.createRow(0);

        for(int coluna = 0; coluna < colunas.length; coluna++)
            criarCelula(cabecalho, coluna, colunas[coluna], estiloCabecalho);

        int numeroLinha = 1;

        if(relatorio.getLancamentos().isEmpty()) {
            Row linha = sheet.createRow(numeroLinha);
            criarCelula(linha, 0, "Nenhum lançamento no período.", null);

            sheet.addMergedRegion(
                new CellRangeAddress(numeroLinha, numeroLinha, 0, 5)
            );
        }else{
            for(Lancamento lancamento : relatorio.getLancamentos()) {
                Row linha = sheet.createRow(numeroLinha);

                linha.createCell(0).setCellValue(lancamento.getId());

                Cell celulaData = linha.createCell(1);
                celulaData.setCellValue(java.sql.Date.valueOf(lancamento.getData()));
                celulaData.setCellStyle(estiloData);

                linha.createCell(2).setCellValue(lancamento.getTipo().toString());
                linha.createCell(3).setCellValue(lancamento.getCategoria().toString());
                linha.createCell(4).setCellValue(lancamento.getmeioDeMovimentacao().toString());

                Cell celulaValor = linha.createCell(5);
                celulaValor.setCellValue(lancamento.getValor().doubleValue());
                celulaValor.setCellStyle(estiloMoeda);

                numeroLinha++;
            }
        }

        int ultimaLinha;

        if(relatorio.getLancamentos().isEmpty())
            ultimaLinha = 0;
        else
            ultimaLinha = numeroLinha - 1;

        sheet.setAutoFilter(
            new CellRangeAddress(0, ultimaLinha, 0, 5)
        );

        sheet.createFreezePane(0, 1);

        sheet.setColumnWidth(0, 10 * 256);
        sheet.setColumnWidth(1, 14 * 256);
        sheet.setColumnWidth(2, 15 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        sheet.setColumnWidth(4, 24 * 256);
        sheet.setColumnWidth(5, 16 * 256);
    }

    private void adicionarValorResumo(Sheet sheet, int numeroLinha, String rotulo, BigDecimal valor, CellStyle estiloRotulo, CellStyle estiloMoeda) {
        Row linha = sheet.createRow(numeroLinha);

        criarCelula(
            linha,
            0,
            rotulo,
            estiloRotulo
        );

        Cell celulaValor = linha.createCell(1);
        celulaValor.setCellValue(valor.doubleValue());
        celulaValor.setCellStyle(estiloMoeda);
    }

    private void criarLinhaMesclada(Sheet sheet, int numeroLinha, String texto, CellStyle estilo) {
        Row linha = sheet.createRow(numeroLinha);

        for(int coluna = 0; coluna <= 1; coluna++) {
            Cell celula = linha.createCell(coluna);
            celula.setCellStyle(estilo);
        }

        linha.getCell(0).setCellValue(texto);

        sheet.addMergedRegion(
            new CellRangeAddress(numeroLinha, numeroLinha, 0, 1)
        );
    }

    private void criarCelula(Row linha, int coluna, String valor, CellStyle estilo) {
        Cell celula = linha.createCell(coluna);
        celula.setCellValue(valor);

        if(estilo != null)
            celula.setCellStyle(estilo);
    }

    private CellStyle criarEstiloTitulo(Workbook workbook) {
        CellStyle estilo = workbook.createCellStyle();

        Font fonte = workbook.createFont();
        fonte.setBold(true);
        fonte.setColor(IndexedColors.WHITE.getIndex());
        fonte.setFontHeightInPoints((short) 16);

        estilo.setFont(fonte);
        estilo.setAlignment(HorizontalAlignment.CENTER);
        estilo.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return estilo;
    }

    private CellStyle criarEstiloSecao(Workbook workbook) {
        CellStyle estilo = workbook.createCellStyle();

        Font fonte = workbook.createFont();
        fonte.setBold(true);

        estilo.setFont(fonte);
        estilo.setAlignment(HorizontalAlignment.CENTER);
        estilo.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return estilo;
    }

    private CellStyle criarEstiloCabecalho(Workbook workbook) {
        CellStyle estilo = workbook.createCellStyle();

        Font fonte = workbook.createFont();
        fonte.setBold(true);
        fonte.setColor(IndexedColors.WHITE.getIndex());

        estilo.setFont(fonte);
        estilo.setAlignment(HorizontalAlignment.CENTER);
        estilo.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estilo.setBorderTop(BorderStyle.THIN);
        estilo.setBorderBottom(BorderStyle.THIN);
        estilo.setBorderLeft(BorderStyle.THIN);
        estilo.setBorderRight(BorderStyle.THIN);

        return estilo;
    }

    private CellStyle criarEstiloRotulo(Workbook workbook) {
        CellStyle estilo = workbook.createCellStyle();

        Font fonte = workbook.createFont();
        fonte.setBold(true);

        estilo.setFont(fonte);

        return estilo;
    }

    private CellStyle criarEstiloMoeda(Workbook workbook) {
        CellStyle estilo = workbook.createCellStyle();
        DataFormat formato = workbook.createDataFormat();

        estilo.setDataFormat(
            formato.getFormat("R$ #,##0.00")
        );

        return estilo;
    }

    private CellStyle criarEstiloData(Workbook workbook) {
        CellStyle estilo = workbook.createCellStyle();
        DataFormat formato = workbook.createDataFormat();

        estilo.setDataFormat(
            formato.getFormat("dd/mm/yyyy")
        );

        return estilo;
    }
}