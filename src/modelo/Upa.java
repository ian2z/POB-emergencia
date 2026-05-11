package modelo;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Upa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;

    @OneToMany(mappedBy = "upa", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private List<Atendimento> atendimentos = new ArrayList<>();

    public Upa() {}

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
        List<Integer> ids = new ArrayList<>();
        for (Atendimento a : atendimentos){
            ids.add(a.getId());
        }
        return "Upa [Nome=" + nome + ", Atendimentos=" + ids + "]";
    }
}