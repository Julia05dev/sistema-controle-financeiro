package sistemaFinanceiro.persistencia;
import java.io.*;
import java.time.*;
import java.util.*;

import sistemaFinanceiro.modelo.*;
import sistemaFinanceiro.modelo.enums.*;

public class PersistenciaCSV {
    File arquivo = new File("C:\\Users\\User\\Desktop\\Sistema financeiro\\src\\main\\java\\sistemaFinanceiro\\persistencia\\lancamentos.csv");    //conferir se o caminho funciona

    public void salvar(List<Lancamento> lancamentos) throws IOException{
        if(!arquivo.exists())
            arquivo.createNewFile();
        
        try(FileWriter escrita = new FileWriter(arquivo)){  //usando try-with-resources pra nao precisar ficar fechando o arquivo manualmente
            escrita.write("id;tipo;categoria;valor;data;movimentacao");
            escrita.write("\n");
            for (Lancamento l : lancamentos) {
                escrita.write(converterLancamentoPraLinhaCSV(l));
                escrita.write("\n");
            }
        }
    }

    public String converterLancamentoPraLinhaCSV(Lancamento l){
        String linhaCsv = String.valueOf(l.getId()) + ";" + l.getTipo().name() + ";" + l.getCategoria().name() + ";" + String.valueOf(l.getValor()) 
                            + ";" + l.getData().toString() + ";" + l.getmeioDeMovimentacao().name();
        return linhaCsv;
    }

    public List<Lancamento> carregarCsvPraLista() throws IOException{
        if(!arquivo.exists())
            System.out.println("arquivo inexistente!");
        List<Lancamento> lancamentos = new ArrayList<>();

        if(!arquivo.exists()){
            arquivo.createNewFile();
            return lancamentos;
        }
            
        try(BufferedReader lerLinha = new BufferedReader(new FileReader(arquivo))){
            lerLinha.readLine(); //ignora o cabeçalho
            String linha = lerLinha.readLine();

            while(linha != null){
                if(!linha.trim().isEmpty())
                    lancamentos.add(converteLinhaCsvPraLancamento(linha));
                
                linha = lerLinha.readLine();
            }
        }
        return lancamentos;
    }

    public Lancamento converteLinhaCsvPraLancamento(String linhaCsv) throws IOException{
        String[] elementos = linhaCsv.split("\\;", -1);
        //id - tipo - categoria - valor - data - meio de movimentacao
        if(elementos.length != 6)
            throw new IllegalArgumentException("Linha csv inválida: " + linhaCsv);
        
        int id = Integer.parseInt(elementos[0]);    //tem que ajustar o id (fica reiniciando sempre q fecha o codigo e gera id duplicado)
        TipoLancamento tipo = TipoLancamento.valueOf(elementos[1]);
        TipoCategoria categoria = TipoCategoria.valueOf(elementos[2]);
        double valor = Math.abs(Double.parseDouble(elementos[3]));

        LocalDate data =  LocalDate.parse(elementos[4]);

        TipoMovimentacao movimentacao = TipoMovimentacao.valueOf(elementos[5]);

        Lancamento lancamento = new Lancamento(id, categoria, data, movimentacao, tipo, valor);

        return lancamento;
    }
}