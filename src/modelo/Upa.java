package modelo;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name="upa_20242370040")
public class Upa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;

    @OneToMany(mappedBy = "upa",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atendimento> atendimentos = new ArrayList<>();

    public Upa() {}

    public Upa(String nome) {
        this.nome = nome;
    }

    public String getNome() { return nome; }
    public List<Atendimento> getAtendimentos() { return atendimentos; }

    public void adicionar(Atendimento a) { this.atendimentos.add(a); }
    public void remover(Atendimento a) { this.atendimentos.remove(a); }

    @Override
    public String toString() {
        List<Integer> ids = new ArrayList<>();
        for (Atendimento a : atendimentos) ids.add(a.getId());
        return "Upa [Nome=" + nome + ", Atendimentos IDs=" + ids + "]";
    }
}