package sistemaFinanceiro.persistencia;
import java.io.*;
import java.time.*;
import java.util.*;

import sistemaFinanceiro.modelo.*;
import sistemaFinanceiro.modelo.enums.*;

public class PersistenciaCSV {
    File arquivo = new File("persistencia/lancamentos.csv");    //conferir se o caminho funciona

    public String converterLancamentoPraLinhaCSV(Lancamento l){
        String linhaCsv = String.valueOf(l.getId()) + ";" + l.getTipo().name() + ";" + l.getCategoria().name() + ";" + String.valueOf(l.getValor()) 
                            + ";" + l.getData().toString() + ";" + l.getmeioDeMovimentacao().name();
        return linhaCsv;
    }
    
    public void salvar(List<Lancamento> lancamentos) throws Exception{
        if(!arquivo.exists())
            arquivo.createNewFile();
        
        FileWriter escrita = new FileWriter(arquivo);

        escrita.write("id;tipo;categoria;valor;data;movimentacao");
        escrita.write("\n");
        for (Lancamento l : lancamentos) {
            escrita.write(converterLancamentoPraLinhaCSV(l));
            escrita.write("\n");
        }
        escrita.close();
    }

    public List<Lancamento> carregarCsvPraLista() throws Exception{
        if(!arquivo.exists())
            System.out.println("arquivo inexistente!");
        List<Lancamento> lancamentos = new ArrayList<>();

        if(!arquivo.exists()){
            arquivo.createNewFile();
            return lancamentos;
        }
            

        BufferedReader lerLinha = new BufferedReader(new FileReader(arquivo));
        String linha = lerLinha.readLine(); //ignora o cabeçalho

        while(linha != null){
            if(!linha.trim().isEmpty())
                lancamentos.add(converteLinhaCsvPraLancamento(linha));
            
            linha = lerLinha.readLine();
        }
        lerLinha.close();
        return lancamentos;
    }

    public Lancamento converteLinhaCsvPraLancamento(String linhaCsv){
        String[] elementos = linhaCsv.split("\\;", -1);
        //id - tipo - categoria - valor - data - meio de movimentacao

        //int id = Integer.parseInt(elementos[0]);    //tenho que rever a logica do ID
        TipoLancamento tipo = TipoLancamento.valueOf(elementos[1]);
        TipoCategoria categoria = TipoCategoria.valueOf(elementos[2]);
        double valor = Double.parseDouble(elementos[3]);

        LocalDate data =  LocalDate.parse(elementos[4]);

        TipoMovimentacao movimentacao = TipoMovimentacao.valueOf(elementos[5]);

        Lancamento lancamento = new Lancamento(categoria, data, movimentacao, tipo, valor);
        return lancamento;
    }

    /*converterLinhaCsvParaLancamento
- recebe 1 linha String do CSV
- faz split(";", -1)
- converte id, enum, valor e data
- cria um Lancamento
- retorna esse Lancamento */

/*carregarListaDoCsv
- abre o arquivo
- lê linha por linha
- ignora o cabeçalho
- chama converterLinhaCsvParaLancamento
- adiciona cada Lancamento numa List
- retorna a List<Lancamento> */

}