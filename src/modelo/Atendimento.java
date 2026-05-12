package modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String data;
    private String triagem;

    //gerando coluna de chave estrangeira para o relacionamento de paciente atendimento com paciente
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "upa_id")
    private Upa upa;

    public Atendimento() {} //construtor vazio obrigatorio do JPA

    public Atendimento(String data, String triagem, Paciente paciente, Upa upa) {
        this.data = data;
        this.triagem = triagem;
        this.paciente = paciente;
        this.upa = upa;

        this.upa.adicionar(this);
        this.paciente.adicionar(this);
    }

    public Upa getUpa() {
        return upa;
    }

    public void setUpa(Upa upa) {
        this.upa = upa;
    }

    public void setData(String data){
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setTriagem(String triagem) {
        this.triagem = triagem;
    }

    public String getTriagem() {
        return triagem;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public int getId() { return id; }

    @Override
    public String toString() {
        return "Id: " + id + " | " + "Data: " + data + " | " + "Paciente: " + paciente.getCpf() + " | " + "Upa: " + upa.getNome();
    }

}
