package sistemaFinanceiro.arquivos;

import java.util.*;
import java.io.*;

public class LerArquivoTxt {
    public static void main(String[] args) throws FileNotFoundException{
        FileInputStream entradaArquivos = 
            new FileInputStream(new File("C:\\Users\\User\\Desktop\\Sistema financeiro\\src\\main\\java\\sistemaFinanceiro\\arquivos\\arq.txt"));

        Scanner lerArquivo = new Scanner(entradaArquivos, "UTF-8");

        List<Pessoa> pessoas = new ArrayList<>();

        while(lerArquivo.hasNext()){
            String linha = lerArquivo.nextLine();

            if(linha != null && !linha.isEmpty()){
                String[] dados = linha.split("\\;");

                Pessoa pessoa = new Pessoa();
                pessoa.setNome(dados[0]);
                pessoa.setIdade(Integer.parseInt(dados[1]));
                pessoa.setEmail(dados[2]);

                pessoas.add(pessoa);
            }
        }

        for (Pessoa p : pessoas) {
            System.out.println(p);
        }   
    }
}
