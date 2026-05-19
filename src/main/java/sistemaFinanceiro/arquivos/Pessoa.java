package sistemaFinanceiro.arquivos;

public class Pessoa {
    String nome;
    String email;
    int idade;
    
    @Override
    public String toString() {
        return "Pessoa [nome=" + nome + ", email=" + email + ", idade=" + idade + "]";
    }
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
