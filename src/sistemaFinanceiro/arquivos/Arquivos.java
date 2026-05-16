package sistemaFinanceiro.arquivos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Arquivos {
    public static void main(String[] args) throws IOException{
        File arquivo = new File("C:\\Users\\User\\Desktop\\Sistema financeiro\\src\\sistemaFinanceiro\\arquivos\\arq.txt");

        if(!arquivo.exists())
            arquivo.createNewFile();
        
        Pessoa pessoa1 = new Pessoa();
        Pessoa pessoa2 = new Pessoa();
        Pessoa pessoa3 = new Pessoa();

        pessoa1.setNome("Julia");
        pessoa1.setEmail("julia@gmail");
        pessoa1.setIdade(20);

        pessoa2.setNome("Alexandre");
        pessoa2.setEmail("amordaminhavida@gmail");
        pessoa2.setIdade(20);
        
        pessoa3.setNome("Bernardo");
        pessoa3.setEmail("zeRuela@gmail");
        pessoa3.setIdade(14);

        FileWriter escreverNoArquivo = new FileWriter(arquivo);

        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(pessoa1);
        pessoas.add(pessoa2);
        pessoas.add(pessoa3);

        for (Pessoa p : pessoas) 
            escreverNoArquivo.write(p.getNome() + " ; " + p.getIdade() + " anos ; " + p.getEmail() + "\n");
        
        escreverNoArquivo.flush();
        escreverNoArquivo.close();
    }
}
