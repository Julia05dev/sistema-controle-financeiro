package sistemaFinanceiro.arquivos;

import java.util.*;
import java.io.*;

public class LerArquivoTxt {
    public static void main(String[] args) throws FileNotFoundException{
        FileInputStream entradaArquivos = 
            new FileInputStream(new File("C:\\Users\\User\\Desktop\\Sistema financeiro\\src\\sistemaFinanceiro\\arquivos\\arq.txt"));

        Scanner lerArquivo = new Scanner(entradaArquivos, "UTF-8");

        while(lerArquivo.hasNext()){
            String linha = lerArquivo.nextLine();
            System.out.println(linha);
        }
    }
}
