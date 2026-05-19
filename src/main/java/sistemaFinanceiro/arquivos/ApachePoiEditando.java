package sistemaFinanceiro.arquivos;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

public class ApachePoiEditando {
    public static void main(String[] args) throws Exception{
        File file = new File("C:\\Users\\User\\Desktop\\Sistema financeiro\\src\\main\\java\\sistemaFinanceiro\\arquivos\\arquivo_excel.xls");
        FileInputStream entrada = new FileInputStream(file);

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(entrada);
        HSSFSheet planilha = hssfWorkbook.getSheetAt(0);

        Iterator<Row> linhaIterator = planilha.iterator();
        
        while(linhaIterator.hasNext()){
            Row linha = linhaIterator.next();

            int numCells = linha.getPhysicalNumberOfCells();

            Cell celula = linha.createCell(numCells);
            celula.setCellValue("5487,40");
        }

        entrada.close();
        FileOutputStream saida = new FileOutputStream(file);
        hssfWorkbook.write(saida);

        saida.flush();
        saida.close();

        System.out.println("Planilha atualizada!");
    }
}
