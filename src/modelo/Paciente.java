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
    public List<Atendimento> getAtendimentos() {
        return atendimentos;
    }
    public void adicionar(Atendimento a) {
        this.atendimentos.add(a);
    }

    @Override
    public String toString() {
        return "Paciente [CPF=" + cpf + ", Nome=" + nome + ", Total de atendimentos=" + atendimentos.size() +"]";

    }
}


