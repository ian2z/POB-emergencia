package modelo;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name="paciente_20242370040")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String cpf;
    private String nome;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atendimento> atendimentos = new ArrayList<>();

    public Paciente() {}

    public Paciente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public List<Atendimento> getAtendimentos() { return atendimentos; }

    public void adicionar(Atendimento a) { this.atendimentos.add(a); }
    public void remover(Atendimento a) { this.atendimentos.remove(a); }

    @Override
    public String toString() {
        List<Integer> ids = new ArrayList<>();
        for (Atendimento a : atendimentos) ids.add(a.getId());
        return "Paciente [CPF=" + cpf + ", Nome=" + nome + ", Atendimentos IDs=" + ids + "]";
    }
}