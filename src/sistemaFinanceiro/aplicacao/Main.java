package sistemaFinanceiro.aplicacao;
import java.util.*;
import sistemaFinanceiro.modelo.*;
import sistemaFinanceiro.servico.*;
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
        return valor;
    }

    public static void cadastrandoLancamento(Scanner scanner){
        //metodo vai estar aqui. A main começa em baixo, ou seja, estao na mesma classe mas em escopos diferentes 
        //as variaveis foram incializadas na main
    }
    public static void main(String[] args) {
        SistemaFinanceiro SistemaFinanceiro = new SistemaFinanceiro();
        int controle;
        Scanner scanner = new Scanner(System.in);   
        do{
            System.out.println("-------------------------------------------------------------");
            System.out.println
            ("ESCOLHA UMA OPÇÃO:\n1- fazer um lancamento\n2- remover um lancamento\n3- calcular saldo\n4- mostrar lancamentos\n5- filtrar lancamentos\n0- SAIR");
            System.out.println("-------------------------------------------------------------");

            controle = lerIntervalo(5, 0, scanner);
            switch(controle){
                case 1:{
                    //TIPO (receita/despesa)
                    System.out.println("Selecione o tipo:"); 
                    System.out.println("1- Receita\n2- Despesa");
                    System.out.println();
                    int tp = lerIntervalo(2, 1, scanner);
                    String tipo = null;
                    String categoria = null;
                    String meioDeMovimentacao = null;
                    System.out.println("selecione a categoria:");  
                    switch(tp){
                        case 1:{    //receita
                            tipo = "receita";
                            System.out.println("\t1- emprego\n\t2- freelance\n\t3- presente\n");
                            int catA = lerIntervalo(3, 1, scanner);
                            System.out.println("-------------------------------------------------------------");

                            switch(catA){
                                case 1:{
                                    categoria = "emprego";
                                    break;
                                }
                                case 2:{
                                    categoria = "freelance";
                                    break;
                                }
                                case 3:{
                                    categoria = "presente";
                                    break;
                                }
                            }

                            //MEIO DE PAGAMENTO
                            System.out.println("Selecione a forma de movimentacao:"); 
                            System.out.println("1- Pix\n2- Transferencia\n3- Deposito");
                            System.out.println();
                            int c2 = lerIntervalo(3, 1, scanner);
                            System.out.println("-------------------------------------------------------------");
                            switch(c2){
                                case 1:{
                                    meioDeMovimentacao = "pix";
                                    break;
                                }
                                case 2:{
                                    meioDeMovimentacao = "transferencia";
                                    break;
                                }
                                case 3:{
                                    meioDeMovimentacao = "deposito";
                                    break;
                                }
                            }
                            break;
                        }
                        case 2:{
                            tipo = "despesa";
                            System.out.println("\t1- Mercado\n\t2- Contas\n\t3- Beleza\n\t4- Lazer\n\t5- Farmacia\n");
                            int catB = lerIntervalo(5, 1, scanner);
                            System.out.println("-------------------------------------------------------------");
                            switch(catB){
                                case 1: {
                                    categoria = "mercado";
                                    break;
                                }
                                case 2: {
                                    categoria = "contas";
                                    break;
                                }
                                case 3: {
                                    categoria = "beleza";
                                    break;
                                }
                                case 4: {
                                    categoria = "lazer";
                                    break;
                                }
                                case 5: {
                                    categoria = "farmacia";
                                    break;
                                }
                            }

                            //MEIO DE PAGAMENTO
                            System.out.println("Selecione a forma de movimentacao"); 
                            System.out.println("1- Debito\n2- Credito");
                            System.out.println();
                            int c2 = lerIntervalo(2, 1, scanner);
                            System.out.println("-------------------------------------------------------------");
                            switch(c2){
                                case 1:{
                                    meioDeMovimentacao = "debito";
                                    break;
                                }
                                case 2:{
                                    meioDeMovimentacao = "credito";
                                    break;
                                }
                            }
                            break;
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
                    SistemaFinanceiro.criaLancamento(categoria, data, meioDeMovimentacao, tipo, valor);
                    break;   
                }
                case 2:{
                    if(SistemaFinanceiro.mostraLancamentos()){
                        System.out.println("Informe o id do lancamento a ser removido:");
                        int idRemove = lerInt(scanner);
                        scanner.nextLine();
                        if(SistemaFinanceiro.removeLancamento(idRemove)){
                            System.out.println("Lancamento removido com sucesso!");
                            System.out.println();
                            break;
                        }else{
                            System.out.println("ID não encontrado");
                            System.out.println();
                            break;
                        }      
                    }else{
                        System.out.println("nenhum lancamento cadastrado!");
                        break;
                    }
                }
                case 3:{
                    double saldo = SistemaFinanceiro.calculaSaldo();
                    System.out.format("Saldo atual: R$%.2f\n", saldo);
                    System.out.println();
                    break;
                }
                case 4:{
                    if(!SistemaFinanceiro.mostraLancamentos()){
                        System.out.println("Nenhum lancamento cadastrado!"); 
                    }
                    break;
                }
                case 5:{
                    System.out.println("qual filtro voce deseja usar?");
                    System.out.println("1- pela data\n2- por mes e ano");
                    int filtro = lerIntervalo(2, 1, scanner);
                    switch(filtro){
                        case 1:{
                            System.out.println("Por favor, informe:\nDia:");
                            int dia = lerIntervalo(31, 1, scanner);
                            System.out.println("Mes:");
                            int mes = lerIntervalo(12, 1, scanner);
                            System.out.println("Ano:");
                            int ano = lerInt(scanner);

                            SistemaFinanceiro.filtrarLancamentos(ano, mes, dia);
                            break;
                        }
                        case 2:{
                            System.out.println("Por favor, informe:\nMes:");
                            int mes = lerIntervalo(12, 1, scanner);
                            System.out.println("Ano:");
                            int ano = lerInt(scanner);

                            SistemaFinanceiro.filtrarLancamentosMes(ano, mes);
                        }
                    }
                }

            }
        }while(controle != 0);
        scanner.close();
    }
}
