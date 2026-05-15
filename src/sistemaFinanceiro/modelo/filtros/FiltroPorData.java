package sistemaFinanceiro.modelo.filtros;

import sistemaFinanceiro.modelo.Lancamento;
import java.time.*;
//import java.time.format.*;

public class FiltroPorData implements FiltroLancamento{
    private final int dia, mes, ano;
    public FiltroPorData(int dia, int mes, int ano){
        this. dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    @Override
    public boolean filtrar(Lancamento lancamento){
        //validando data completa (se for informada)
        if(dia != 0 && mes != 0 && ano != 0){
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
            try {
                LocalDate.of(ano, mes, dia);
            } catch (DateTimeException e) {
                return false;
            }
        }

        //verificando dia
        if(dia != 0){
            if (lancamento.getData().getDayOfMonth() != dia)
                return false;
        }
        //verificando mes
        if(mes != 0){
            if (lancamento.getData().getMonthValue() != mes)
                return false;
        }
        //verificando ano
        if(ano != 0){
            if(lancamento.getData().getYear() != ano)
                return false;
        }

        //se tudo for igual
        return true;
    }
}