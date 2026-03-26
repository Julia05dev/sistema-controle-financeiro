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
        int controle = 6;
        Scanner scanner = new Scanner(System.in);   //tem que tratar excessão
        while(controle != 0){
            System.out.println
            ("ESCOLHA UMA OPÇÃO:\n1- fazer um lançamento\n2- remover um lançamento\n3- calcular saldo\n4- mostrar lançamentos\n5- filtrar lançamentos");
            controle = scanner.nextInt();
            scanner.nextLine(); //consome o \n que fica no buffer
            switch(controle){
                case 1: System.out.println("informe os seguintes campos:");
                System.out.println("categoria:");
                String categoria = scanner.nextLine();
                System.out.println("data (formato dd/MM/yyyy):");
                LocalDate data = lerData(scanner);   
                System.out.println("meio de pagamento:");
                String meioDePagamento = scanner.nextLine();
                System.out.println("tipo:");
                String tipo = scanner.nextLine();
                System.out.println("valor (apenas o número sem nenhum símbolo):");  //tratar entradas erradas
                double valor = scanner.nextDouble();
                scanner.nextLine(); //consome o \n que fica no buffer

                carteira.criaLancamento(categoria, data, meioDePagamento, tipo, valor);
                break;
                //CONFERIR A LÓGICA DO CASE 2 FIZ NA PRESSA
                case 2: System.out.println("Informe o id do lançamento a ser removido:");
                carteira.mostraLancamentos();
                int idRemove = scanner.nextInt();
                scanner.nextLine();
                if(carteira.removeLancamento(idRemove)){
                    break;
                }else{
                    System.out.println("Não existe nenhum lançamento com esse id");
                    break;
                }
            }
        }
        scanner.close();
    }
}
