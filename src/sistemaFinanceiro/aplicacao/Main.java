package sistemaFinanceiro.aplicacao;
import java.util.*;
import sistemaFinanceiro.modelo.*;
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

    public static int lerOpcao(int tamanho, Scanner scanner){
        int opcao = scanner.nextInt();

        while(opcao > tamanho || opcao < 0){
            System.out.println("Escolha uma opcao valida!");
            opcao = scanner.nextInt();
            scanner.nextLine();
        }

        return opcao;
    }
    public static void main(String[] args) {
        Carteira carteira = new Carteira();
        int controle;
        Scanner scanner = new Scanner(System.in);   
        do{
            System.out.println("-------------------------------------------------------------");
            System.out.println
            ("ESCOLHA UMA OPÇÃO:\n1- fazer um lancamento\n2- remover um lancamento\n3- calcular saldo\n4- mostrar lancamentos\n5- filtrar lancamentos\n0- SAIR");
            System.out.println("-------------------------------------------------------------");

            controle = lerOpcao(5, scanner);
            switch(controle){
                case 1:{
                    //TIPO (receita/despesa)
                    System.out.println("Selecione o tipo:"); 
                    System.out.println("1- Receita\n2- Despesa");
                    System.out.println();
                    int tp = scanner.nextInt();
                    String tipo = null;
                    String categoria = null;
                    String meioDeMovimentacao = null;
                    System.out.println("selecione a categoria:");  
                    switch(tp){
                        case 1:{    //receita
                            tipo = "receita";
                            System.out.println("\t1- emprego\n\t2- freelance\n\t3- presente\n");
                            int catA = scanner.nextInt();
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
                            int c2 = scanner.nextInt();
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
                                }
                            }
                            break;
                        }
                        case 2:{
                            tipo = "despesa";
                            System.out.println("\t1- Mercado\n\t2- Contas\n\t3- Beleza\n\t4- Lazer\n\t5- Farmacia\n");
                            int catB = scanner.nextInt();
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
                            int c2 = scanner.nextInt();
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
                    double valor = scanner.nextDouble();   
                    scanner.nextLine(); 
                    System.out.println("-------------------------------------------------------------");
                    
                    //DATA
                    System.out.println("Data (formato dd/MM/yyyy):");
                    System.out.println();
                    LocalDate data = lerData(scanner);   
                    
                    //criando o lançamento
                    carteira.criaLancamento(categoria, data, meioDeMovimentacao, tipo, valor);
                    break;   
                }
                case 2:{
                    if(carteira.mostraLancamentos()){
                        System.out.println("Informe o id do lancamento a ser removido:");
                        int idRemove = scanner.nextInt();
                        scanner.nextLine();
                        if(carteira.removeLancamento(idRemove)){
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
                    double saldo = carteira.calculaSaldo();
                    System.out.format("Saldo atual: R$%.2f\n", saldo);
                    System.out.println();
                    break;
                }
                case 4:{
                    if(!carteira.mostraLancamentos()){
                        System.out.println("Nenhum lancamento cadatrado!"); 
                    }
                    break;
                }
                case 5:{
                    System.out.println("qual filtro voce deseja usar?");
                    System.out.println("1- pela data\n2- por mes e ano");
                    int filtro = scanner.nextInt();
                    switch(filtro){
                        case 1:{
                            System.out.println("Por favor, informe:\nDia:");
                            int dia = scanner.nextInt();
                            System.out.println("Mes:");
                            int mes = scanner.nextInt();
                            System.out.println("Ano:");
                            int ano = scanner.nextInt();

                            carteira.filtraLancamentos(ano, mes, dia);
                            break;
                        }
                        case 2:{
                            System.out.println("Por favor, informe:\nMes:");
                            int mes = scanner.nextInt();
                            System.out.println("Ano:");
                            int ano = scanner.nextInt();

                            carteira.filtrarLancamentosMes(ano, mes);
                        }
                    }
                }

            }
        }while(controle != 0);
        scanner.close();
    }
}
