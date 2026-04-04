package modelo;

import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private String cpf;
    private String nome;
    private List<Atendimento> atendimentos = new ArrayList<>();

    public Paciente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public List<Atendimento> getAtendimentos() { return atendimentos; }
    public void adicionar(Atendimento a) { this.atendimentos.add(a); }

    @Override
    public String toString() {
        List<Integer> ids = new ArrayList<>();
        for (Atendimento a : atendimentos){
            ids.add(a.getId());
        }

        return "Paciente [CPF=" + cpf + ", Nome=" + nome + ", Atendimentos=" + ids +"]";

    }

    // Metodo para achar um atendimento específico dentro da lista do paciente
    public Atendimento localizar(int idAtendimento) {
        for(Atendimento a : atendimentos){
            if(a.getId() == idAtendimento) {
                return a;
            }
        }
        return null;
    }

    // Metodo para quebrar o relacionamento (remover da lista)
    public void remover(Atendimento a) { this.atendimentos.remove(a); }
}


