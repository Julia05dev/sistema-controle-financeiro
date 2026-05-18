package sistemaFinanceiro.arquivos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ApachePoi {
    public static void main(String[] args) throws Exception{
        File file = new File("C:\\Users\\User\\Desktop\\Sistema financeiro\\src\\sistemaFinanceiro\\arquivos\\arquivo_excel.xls");
        if(!file.exists())
            file.createNewFile();

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

        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(pessoa1);
        pessoas.add(pessoa2);
        pessoas.add(pessoa3);

        
    }
}
