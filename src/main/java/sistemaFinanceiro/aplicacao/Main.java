package sistemaFinanceiro.aplicacao;
import java.util.*;

import sistemaFinanceiro.servico.*;
import sistemaFinanceiro.modelo.*;
import sistemaFinanceiro.modelo.enums.TipoCategoria;
import sistemaFinanceiro.modelo.enums.TipoLancamento;
import sistemaFinanceiro.modelo.enums.TipoMovimentacao;

import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Main {
    public static LocalDate lerData (Scanner scanner){  
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        LocalDate data = null;
        while(data == null){
            String input = scanner.nextLine();
            try{
                data = LocalDate.parse(input, formatter);
            }catch(DateTimeParseException e){
                System.out.println("Formato inválido. Use o formato dd/MM/yyyy");
                data = null;
            }
        }
        return data;
    }

    public static int lerIntervalo(int max, int min, Scanner scanner){
        int opcao = lerInt(scanner);

        while(opcao > max || opcao < min){
            System.out.printf("Digite um valor entre %d e %d!\n", min, max);
            opcao = lerInt(scanner);
        }

        return opcao;
    }

    public static int lerInt(Scanner scanner){
        while(!scanner.hasNextInt()){
            System.out.println("Digite um valor valido!");
            scanner.next();
        }

        int valor = scanner.nextInt();
        return valor;
    }

    public static double lerDouble(Scanner scanner){
        while(!scanner.hasNextDouble()){
            System.out.println("Digite um valor valido!");
            scanner.next();
        }

        double valor = scanner.nextDouble();
        //scanner.next();
        return valor;
    }

    public static void cadastrandoLancamento(SistemaFinanceiro sistemaFinanceiro, Scanner scanner) throws SQLException{

        System.out.println("Selecione o tipo:"); 
        System.out.println("1- " + TipoLancamento.RECEITA);
        System.out.println("2- " + TipoLancamento.DESPESA);
        //System.out.println("1- Receita\n2- Despesa");
        System.out.println();
        int tp = lerIntervalo(2, 1, scanner);
        TipoLancamento tipo = TipoLancamento.fromInt(tp);
        TipoCategoria categoria = null;
        TipoMovimentacao meioDeMovimentacao = null;
        System.out.println("-------------------------------------------------------------");
        System.out.println("selecione a categoria:");  

  
        if(tipo == TipoLancamento.RECEITA){
            //System.out.println("\t1- emprego\n\t2- freelance\n\t3- presente\n");
            System.out.println("1- " + TipoCategoria.EMPREGO);
            System.out.println("2- " + TipoCategoria.FREELANCE);
            System.out.println("3- " + TipoCategoria.PRESENTE);
            System.out.println();
            int catA = lerIntervalo(3, 1, scanner);
            System.out.println("-------------------------------------------------------------");
            
            categoria = TipoCategoria.fromIntReceita(catA);

            //MEIO DE PAGAMENTO
            System.out.println("Selecione a forma de movimentacao:"); 
            //System.out.println("1- Pix\n2- Dinheiro\n3- Transferencia");
            System.out.println("1- " + TipoMovimentacao.PIX);
            System.out.println("2- " + TipoMovimentacao.DINHEIRO);
            System.out.println("3- " + TipoMovimentacao.TRANSFERENCIA);
            System.out.println();

            int c2 = lerIntervalo(3, 1, scanner);
            System.out.println("-------------------------------------------------------------");
            meioDeMovimentacao = TipoMovimentacao.fromIntReceita(c2);
        }

        else if(tipo == TipoLancamento.DESPESA){
            //System.out.println("\t1- Mercado\n\t2- Contas\n\t3- Beleza\n\t4- Lazer\n\t5- Farmacia\n");
            System.out.println("1- " + TipoCategoria.MERCADO);
            System.out.println("2- " + TipoCategoria.CONTAS);
            System.out.println("3- " + TipoCategoria.BELEZA);
            System.out.println("4- " + TipoCategoria.LAZER);
            System.out.println("5- " + TipoCategoria.FARMACIA);
            System.out.println();

            int catB = lerIntervalo(5, 1, scanner);
            System.out.println("-------------------------------------------------------------");
            categoria = TipoCategoria.fromIntDespesa(catB);

            //MEIO DE PAGAMENTO
            System.out.println("Selecione a forma de movimentacao"); 
            //System.out.println("1- Debito\n2- Credito\n3- Pix\n4- Dinheiro\n5- Transferencia");
            System.out.println("1- " + TipoMovimentacao.DEBITO);
            System.out.println("2- " + TipoMovimentacao.CREDITO);
            System.out.println("3- " + TipoMovimentacao.PIX);
            System.out.println("4- " + TipoMovimentacao.DINHEIRO);
            System.out.println("5- " + TipoMovimentacao.TRANSFERENCIA);
            System.out.println();

            int c2 = lerIntervalo(5, 1, scanner);
            System.out.println("-------------------------------------------------------------");
            meioDeMovimentacao = TipoMovimentacao.fromIntDespesa(c2);
        }

        //VALOR
        System.out.println("valor (apenas o número sem nenhum símbolo):\n"); 
        double valor = lerDouble(scanner);   
        while(valor <= 0){
            System.out.println("favor digitar o valor sem símbolos!");
            valor = lerDouble(scanner);
        }
        scanner.nextLine(); 
        System.out.println("-------------------------------------------------------------");
        
        //DATA
        System.out.println("Data (formato dd/MM/yyyy):");
        System.out.println();
        LocalDate data = lerData(scanner);   
        
        //criando o lançamento
        sistemaFinanceiro.criaLancamento(categoria, data, meioDeMovimentacao, tipo, valor);
    }

    public static void removendoLancamento(SistemaFinanceiro sistemaFinanceiro, Scanner scanner) throws SQLException{
        if(!sistemaFinanceiro.mostraLancamentos().isEmpty()){
            mostrandoLancamentos(sistemaFinanceiro, scanner);
            System.out.println("Informe o id do lancamento a ser removido:");
            int idRemove = lerIntervalo(Integer.MAX_VALUE, 1, scanner);
            scanner.nextLine();
            if(sistemaFinanceiro.removeLancamento(idRemove)){
                System.out.println("Lancamento removido com sucesso!");
                System.out.println();
            }else{
                System.out.println("ID não encontrado");
                System.out.println();
            }      
        }else{
            System.out.println("nenhum lancamento cadastrado!");
        }
    }

    public static void calculandoSaldo(SistemaFinanceiro sistemaFinanceiro){
        double saldo = sistemaFinanceiro.calculaSaldo();
        System.out.format("Saldo atual: R$%.2f\n", saldo);
        System.out.println();
    }

    public static void mostrandoLancamentos(SistemaFinanceiro sistemaFinanceiro, Scanner scanner) throws SQLException{
        if(sistemaFinanceiro.mostraLancamentos().isEmpty()){
            System.out.println("Nenhum lancamento cadastrado!"); 
        }else{
            List<Lancamento> lancamentos = sistemaFinanceiro.mostraLancamentos();
            for (Lancamento l : lancamentos) {
                System.out.println(l);
            }
        }
    }

    //metodo auxiliar pra definir categoria (a depender do tipo)
    public static TipoCategoria escolherCategoria(Scanner scanner, TipoLancamento tipo){
        System.out.println("selecione a categoria desejada");
        if(tipo == TipoLancamento.RECEITA){
            System.out.println("1- " + TipoCategoria.EMPREGO);
            System.out.println("2- " + TipoCategoria.FREELANCE);
            System.out.println("3- " + TipoCategoria.PRESENTE);
            System.out.println();

            int categoria = lerIntervalo(3, 1, scanner);
            //System.out.println("-------------------------------------------------------------");
            return TipoCategoria.fromIntReceita(categoria);
        }else{
            System.out.println("1- " + TipoCategoria.MERCADO);
            System.out.println("2- " + TipoCategoria.CONTAS);
            System.out.println("3- " + TipoCategoria.BELEZA);
            System.out.println("4- " + TipoCategoria.LAZER);
            System.out.println("5- " + TipoCategoria.FARMACIA);
            System.out.println();

            int categoria = lerIntervalo(5, 1, scanner);
            //System.out.println("-------------------------------------------------------------");
            return TipoCategoria.fromIntDespesa(categoria);
        }
    }

    //metodo auxiliar pra definir o meio de movimentacao (a depender do tipo)
    public static TipoMovimentacao escolherMovimentacao(Scanner scanner, TipoLancamento tipo){
        System.out.println("selecione o meio de movimentaçao desejado");
        if(tipo == TipoLancamento.RECEITA){
            System.out.println("1- " + TipoMovimentacao.PIX);
            System.out.println("2- " + TipoMovimentacao.DINHEIRO);
            System.out.println("3- " + TipoMovimentacao.TRANSFERENCIA);
            System.out.println();

            int movimentacao = lerIntervalo(3, 1, scanner);
            //System.out.println("-------------------------------------------------------------");
            return TipoMovimentacao.fromIntReceita(movimentacao);
        }else{
            System.out.println("1- " + TipoMovimentacao.DEBITO);
            System.out.println("2- " + TipoMovimentacao.CREDITO);
            System.out.println("3- " + TipoMovimentacao.PIX);
            System.out.println("4- " + TipoMovimentacao.DINHEIRO);
            System.out.println("5- " + TipoMovimentacao.TRANSFERENCIA);
            System.out.println();

            int movimentacao = lerIntervalo(5, 1, scanner);
            //System.out.println("-------------------------------------------------------------");
            return TipoMovimentacao.fromIntDespesa(movimentacao);
        }
    }

    public static void filtrandoLancamentos(SistemaFinanceiro sistemaFinanceiro, Scanner scanner){
        System.out.println("qual filtro voce deseja usar?");
        System.out.println("1- data\n2- tipo\n3- categoria\n4- movimentaçao");
        int filtro = lerIntervalo(4, 1, scanner);
        System.out.println();

        switch(filtro){
            case 1 -> {
                System.out.println("Por favor, informe:\nDia:");    
                int dia = lerIntervalo(31, 1, scanner);
                System.out.println("Mes:");
                int mes = lerIntervalo(12, 1, scanner);
                System.out.println("Ano:");
                int ano = lerIntervalo(Integer.MAX_VALUE, 1, scanner);
                System.out.println();

                List<Lancamento> lancamentos = sistemaFinanceiro.filtrarPorData(dia, mes, ano);
                if(lancamentos.isEmpty()){
                    System.out.println("Nenhum lancamento nessa data!");
                }else{
                    for (Lancamento l : lancamentos) {
                        System.out.println(l);
                    }
                }
            }
            case 2 -> {
                System.out.println("selecione o tipo de lancamento");
                System.out.println("1- " + TipoLancamento.RECEITA);
                System.out.println("2- " + TipoLancamento.DESPESA);
                System.out.println();
                int tp = lerIntervalo(2, 1, scanner);
                List<Lancamento> lancamentos = sistemaFinanceiro.filtrarPorTipo(TipoLancamento.fromInt(tp));
                if(lancamentos.isEmpty())
                    System.out.println("nenhuma " + TipoLancamento.fromInt(tp) + " cadastrada");
                else{
                    for (Lancamento l : lancamentos) { 
                        System.out.println(l);
                    }
                }
            }
            case 3 -> {
                System.out.println("selecione o tipo de lancamento");
                System.out.println("1- " + TipoLancamento.RECEITA);
                System.out.println("2- " + TipoLancamento.DESPESA);
                System.out.println();
                int tp = lerIntervalo(2, 1, scanner);

                TipoCategoria categoria = escolherCategoria(scanner, TipoLancamento.fromInt(tp));
                List<Lancamento> lancamentos = sistemaFinanceiro.filtrarPorCategoria(categoria);

                if(lancamentos.isEmpty())
                    System.out.println("nenhuma " + TipoLancamento.fromInt(tp) + " cadastrada nessa categoria.");
                else{
                    for(Lancamento l : lancamentos)
                        System.out.println(l);
                }
            }
            case 4-> {
                System.out.println("selecione o tipo de lancamento");
                System.out.println("1- " + TipoLancamento.RECEITA);
                System.out.println("2- " + TipoLancamento.DESPESA);
                System.out.println();
                int tp = lerIntervalo(2, 1, scanner);

                TipoMovimentacao movimentacao = escolherMovimentacao(scanner, TipoLancamento.fromInt(tp));
                List<Lancamento> lancamentos = sistemaFinanceiro.filtrarPorMovimentacao(TipoLancamento.fromInt(tp), movimentacao);

                if(lancamentos.isEmpty())
                    System.out.println("nenhuma " + TipoLancamento.fromInt(tp) + " cadastrada com esse meio de movimentaçao.");
                else{
                    for (Lancamento l : lancamentos) {
                        System.out.println(l);
                    }
                }
            }

        }
    }

    public static void main(String[] args){
        try{
            SistemaFinanceiro sistemaFinanceiro = new SistemaFinanceiro();
            int controle;
            Scanner scanner = new Scanner(System.in);   
            do{
                System.out.println("-------------------------------------------------------------");
                System.out.println
                ("ESCOLHA UMA OPÇÃO:\n1- fazer um lancamento\n2- remover um lancamento\n3- calcular saldo\n4- mostrar lancamentos\n5- filtrar lancamentos\n0- SAIR");
                System.out.println("-------------------------------------------------------------");

                controle = lerIntervalo(5, 0, scanner);
                switch(controle){
                    case 1 -> {
                        cadastrandoLancamento(sistemaFinanceiro, scanner);
                    }
                    case 2 -> {
                        removendoLancamento(sistemaFinanceiro, scanner);
                    }
                    case 3 -> {
                        calculandoSaldo(sistemaFinanceiro);
                    }
                    case 4 -> {
                        mostrandoLancamentos(sistemaFinanceiro, scanner);
                    }
                    case 5 -> {
                        filtrandoLancamentos(sistemaFinanceiro, scanner);
                    }
                }
            }while(controle != 0);
            scanner.close();
        }catch(SQLException e){
            System.out.println("Erro ao acessar banco de dados!");
            e.printStackTrace();
        }
    }
}
