package sistemaFinanceiro.aplicacao;
import java.util.*;
import sistemaFinanceiro.servico.*;
import sistemaFinanceiro.modelo.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import sistemaFinanceiro.modelo.enums.TipoCategoria;
import sistemaFinanceiro.modelo.enums.TipoLancamento;
import sistemaFinanceiro.modelo.enums.TipoMovimentacao;

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
        return valor;
    }

    public static void cadastrandoLancamento(SistemaFinanceiro sistemaFinanceiro, Scanner scanner){

        System.out.println("Selecione o tipo:"); 
        System.out.println("1- Receita\n2- Despesa");
        System.out.println();
        int tp = lerIntervalo(2, 1, scanner);
        TipoLancamento tipo = null;
        TipoCategoria categoria = null;
        TipoMovimentacao meioDeMovimentacao = null;
     System.out.println("-------------------------------------------------------------");
        System.out.println("selecione a categoria:");  

        switch(tp){
        case 1 -> {    //receita
            tipo = TipoLancamento.RECEITA;
            System.out.println("\t1- emprego\n\t2- freelance\n\t3- presente\n");
            int catA = lerIntervalo(3, 1, scanner);
            System.out.println("-------------------------------------------------------------");

            switch(catA){
                case 1 -> categoria = TipoCategoria.EMPREGO;
                case 2 -> categoria = TipoCategoria.FREELANCE;
                case 3 -> categoria = TipoCategoria.PRESENTE;
            }

            //MEIO DE PAGAMENTO
            System.out.println("Selecione a forma de movimentacao:"); 
            System.out.println("1- Pix\n2- Dinheiro\n3- Transferencia");
            System.out.println();
            int c2 = lerIntervalo(3, 1, scanner);
            System.out.println("-------------------------------------------------------------");
            switch(c2){
                case 1 -> {
                    meioDeMovimentacao = TipoMovimentacao.PIX;
                }
                case 2 -> {
                    meioDeMovimentacao = TipoMovimentacao.DINHEIRO;
                }
                case 3 -> {
                    meioDeMovimentacao = TipoMovimentacao.TRANSFERENCIA;
                }
            }
        }
        case 2 -> {
            tipo = TipoLancamento.DESPESA;
            System.out.println("\t1- Mercado\n\t2- Contas\n\t3- Beleza\n\t4- Lazer\n\t5- Farmacia\n");
            int catB = lerIntervalo(5, 1, scanner);
            System.out.println("-------------------------------------------------------------");
            switch(catB){
                case 1 -> categoria = TipoCategoria.MERCADO;
                case 2 -> categoria = TipoCategoria.CONTAS;
                case 3 -> categoria = TipoCategoria.BELEZA;
                case 4 -> categoria = TipoCategoria.LAZER;
                case 5 -> categoria = TipoCategoria.FARMACIA;
            }

            //MEIO DE PAGAMENTO
            System.out.println("Selecione a forma de movimentacao"); 
            System.out.println("1- Debito\n2- Credito");
            System.out.println();
            int c2 = lerIntervalo(2, 1, scanner);
            System.out.println("-------------------------------------------------------------");
            switch(c2){
                case 1 -> 
                    meioDeMovimentacao = TipoMovimentacao.DEBITO;
                case 2 -> 
                    meioDeMovimentacao = TipoMovimentacao.CREDITO;
            }
        }
    }

    //VALOR
    System.out.println("valor (apenas o número sem nenhum símbolo):\n"); 
    double valor = lerDouble(scanner);   
    scanner.nextLine(); 
    System.out.println("-------------------------------------------------------------");
    
    //DATA
    System.out.println("Data (formato dd/MM/yyyy):");
    System.out.println();
    LocalDate data = lerData(scanner);   
    
    //criando o lançamento
    sistemaFinanceiro.criaLancamento(categoria, data, meioDeMovimentacao, tipo, valor);
    }

    public static void removendoLancamento(SistemaFinanceiro sistemaFinanceiro, Scanner scanner){
        if(!sistemaFinanceiro.mostraLancamentos().isEmpty()){
            mostrandoLancamentos(sistemaFinanceiro, scanner);
            System.out.println("Informe o id do lancamento a ser removido:");
            int idRemove = lerInt(scanner);
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

    public static void mostrandoLancamentos(SistemaFinanceiro sistemaFinanceiro, Scanner scanner){
        if(sistemaFinanceiro.mostraLancamentos().isEmpty()){
            System.out.println("Nenhum lancamento cadastrado!"); 
        }else{
            List<Lancamento> lancamentos = sistemaFinanceiro.mostraLancamentos();
            for (Lancamento l : lancamentos) {
                System.out.println(l);
            }
        }
    }
    public static void filtrandoLancamentos(SistemaFinanceiro sistemaFinanceiro, Scanner scanner){
        System.out.println("qual filtro voce deseja usar?");
        System.out.println("1- pela data\n2- por mes e ano");
        int filtro = lerIntervalo(2, 1, scanner);
        switch(filtro){
            case 1 -> {
                System.out.println("Por favor, informe:\nDia:");
                int dia = lerIntervalo(31, 1, scanner);
                System.out.println("Mes:");
                int mes = lerIntervalo(12, 1, scanner);
                System.out.println("Ano:");
                int ano = lerInt(scanner);

                List<Lancamento> lancamentos = sistemaFinanceiro.filtrarLancamentos(ano, mes, dia);
                if(lancamentos.isEmpty()){
                    System.out.println("Nenhum lancamento nessa data!");
                }else{
                    for (Lancamento l : lancamentos) {
                        System.out.println(l);
                    }
                }
            }
            case 2 -> {
                int dia = 1;
                System.out.println("Mes:");
                int mes = lerIntervalo(12, 1, scanner);
                System.out.println("Ano:");
                int ano = lerInt(scanner);

                List<Lancamento> lancamentos = sistemaFinanceiro.filtrarLancamentosMes(ano, mes, dia);
                if(lancamentos.isEmpty()){
                    System.out.println("Nenhum lancamento nessa data!");
                }else{
                    for (Lancamento l : lancamentos) {
                        System.out.println(l);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
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
    }
}
