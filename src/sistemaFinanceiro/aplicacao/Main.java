package sistemaFinanceiro.aplicacao;
import java.util.*;
import sistemaFinanceiro.modelo.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main {
    public static LocalDate lerData (Scanner scanner){  
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
    public static void main(String[] args) {
        Carteira carteira = new Carteira();
        int controle;
        Scanner scanner = new Scanner(System.in);   //tem que tratar excessão
        do{
            System.out.println("-------------------------------------------------------------");
            System.out.println
            ("ESCOLHA UMA OPÇÃO:\n1- fazer um lancamento\n2- remover um lancamento\n3- calcular saldo\n4- mostrar lancamentos\n5- filtrar lancamentos\n0- SAIR");
            System.out.println("-------------------------------------------------------------");
            System.out.println();

            controle = scanner.nextInt();
            scanner.nextLine(); //consome o \n que fica no buffer
            switch(controle){
                case 1:{
                    System.out.println("informe os seguintes campos:");

                    System.out.println("categoria:");   //swich case aqui
                    //System.out.println("1- Mercado\n2- Beleza\n3- Lazer\n4- Papelaria\n5- Aluguel");
                    //int cat


                    String categoria = scanner.nextLine();
                    System.out.println("data (formato dd/MM/yyyy):");
                    LocalDate data = lerData(scanner);   
                    System.out.println("meio de pagamento:");   //swich case aqui



                    String meioDePagamento = scanner.nextLine();
                    System.out.println("tipo:");    //swich case aqui



                    String tipo = scanner.nextLine();
                    System.out.println("valor (apenas o número sem nenhum símbolo):");  //tratar entradas erradas
                    double valor = scanner.nextDouble();    //NAO TA LENDO
                    scanner.nextLine(); //consome o \n que fica no buffer

                    carteira.criaLancamento(categoria, data, meioDePagamento, tipo, valor);
                    System.out.println();
                    break;   
                }
                case 2:{
                    if(carteira.mostraLancamentos()){
                        System.out.println("Informe o id do lancamento a ser removido:");
                        carteira.mostraLancamentos();
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
                        break;
                    }else{
                        System.out.println("Seus lancamentos:");    //ta mostrando os lançamentos duas vezes
                        carteira.mostraLancamentos();
                        break;
                    }
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
