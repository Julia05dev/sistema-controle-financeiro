package sistemaFinanceiro.arquivos;

import java.util.*;
import java.io.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

public class ApachePoi2 {
    public static void main(String[] args) throws Exception{
        FileInputStream entrada = new FileInputStream(new File("C:\\Users\\User\\Desktop\\Sistema financeiro\\src\\main\\java\\sistemaFinanceiro\\arquivos\\arquivo_excel.xls"));
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(entrada);  //prepara o arquivo pra ler
        HSSFSheet planilha = hssfWorkbook.getSheetAt(0);    //pega a primeira planilha (um arquivo excel pode ter varias planilhas)
        
        Iterator<Row> linhaIterator = planilha.iterator();

        List<Pessoa> pessoas = new ArrayList<>();
        while(linhaIterator.hasNext()){     //enquanto tiver linha no arquivo excel
            Row linha = linhaIterator.next();   //dados da pessoa na linha
            Iterator<Cell> celula = linha.iterator();   //pra cada linha percorremos as celulas

            Pessoa pessoa = new Pessoa();
            while(celula.hasNext()){
                Cell cell = celula.next();

                switch(cell.getColumnIndex()){  //pega o indice da celula
                    case 0 -> {
                        pessoa.setNome(cell.getStringCellValue());
                    }
                    case 1 ->{
                        pessoa.setEmail(cell.getStringCellValue()); 
                    }
                    case 2 ->{
                        pessoa.setIdade(Double.valueOf(cell.getNumericCellValue()).intValue());
                    }
                }
            }
            pessoas.add(pessoa);
        }
        entrada.close();
        for (Pessoa p : pessoas) {
            System.out.println(p);
        }
    }   
}
