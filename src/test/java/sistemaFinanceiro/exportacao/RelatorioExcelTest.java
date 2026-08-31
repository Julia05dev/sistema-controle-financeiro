package sistemaFinanceiro.exportacao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import sistemaFinanceiro.modelo.Lancamento;
import sistemaFinanceiro.modelo.RelatorioMensal;
import sistemaFinanceiro.modelo.enums.TipoCategoria;
import sistemaFinanceiro.modelo.enums.TipoLancamento;
import sistemaFinanceiro.modelo.enums.TipoMovimentacao;

class RelatorioExcelTest {

    @TempDir
    Path pastaTemporaria;

    @Test
    void deveGerarRelatorioExcel() throws IOException {
        Map<TipoCategoria, BigDecimal> receitasPorCategoria = new LinkedHashMap<>();
        receitasPorCategoria.put(TipoCategoria.EMPREGO, new BigDecimal("2000.00"));

        Map<TipoCategoria, BigDecimal> despesasPorCategoria = new LinkedHashMap<>();

        Lancamento lancamento = new Lancamento(
            1,
            TipoCategoria.EMPREGO,
            LocalDate.of(2026, 8, 15),
            TipoMovimentacao.PIX,
            TipoLancamento.RECEITA,
            new BigDecimal("2000.00")
        );

        RelatorioMensal relatorio = new RelatorioMensal(
            8,
            2026,
            new BigDecimal("2000.00"),
            BigDecimal.ZERO,
            receitasPorCategoria,
            despesasPorCategoria,
            List.of(lancamento)
        );

        RelatorioExcel exportador = new RelatorioExcel();
        Path arquivo = exportador.exportar(relatorio, pastaTemporaria);

        assertTrue(Files.exists(arquivo));
        assertTrue(Files.size(arquivo) > 0);

        try(InputStream entrada = Files.newInputStream(arquivo);
            Workbook workbook = WorkbookFactory.create(entrada)) {

            assertNotNull(
                workbook.getSheet("Resumo")
            );

            assertNotNull(
                workbook.getSheet("Lancamentos")
            );

            assertEquals(
                "RELATÓRIO MENSAL - 08/2026",
                workbook.getSheet("Resumo").getRow(0).getCell(0).getStringCellValue()
            );

            assertEquals(
                1,
                workbook.getSheet("Lancamentos").getRow(1).getCell(0).getNumericCellValue()
            );

            assertEquals(
                2000.00,
                workbook.getSheet("Lancamentos").getRow(1).getCell(5).getNumericCellValue()
            );
        }
    }
}