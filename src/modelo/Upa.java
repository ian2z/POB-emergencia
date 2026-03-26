package modelo;

import java.util.ArrayList;
import java.util.List;

public class Upa {
    private String nome;
    private List<Atendimento> atendimentos = new ArrayList<>();

    public Upa(String nome) {
        this.nome = nome;
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

    // Método importante para o relacionamento bilateral
    public void adicionar(Atendimento a) {
        this.atendimentos.add(a);
    }

    public void remover(Atendimento a) {
        this.atendimentos.remove(a);
    }

    @Override
    public String toString() {
        return "Upa [Nome=" + nome + ", Total de Atendimentos=" + atendimentos.size() + "]";
    }
}