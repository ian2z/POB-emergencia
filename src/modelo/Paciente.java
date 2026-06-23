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
    //atributo de foto
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] foto;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atendimento> atendimentos = new ArrayList<>();

    public Paciente() {}

    public Paciente(String cpf, String nome, byte[] foto) {
        this.cpf = cpf;
        this.nome = nome;
        this.foto = foto;
    }

    public Paciente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public byte[] getFoto() { return foto; }
    public void setFoto (byte[] foto) { this.foto = foto; };
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