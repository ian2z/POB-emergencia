package modelo;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity //identificando classe que se tornará uma tabela no banco
public class Paciente {

    @Id //identificando a chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //criando ID automatico
    private int id;

    @Column(unique = true) //CPF unico
    private String cpf;
    private String nome;

    // O "mappedBy" aponta pro nome do atributo que vai ficar lá na classe Atendimento.
    @OneToMany(mappedBy = "paciente", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private List<Atendimento> atendimentos = new ArrayList<>();

    public Paciente() {} //construtor vazio do JPA

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


