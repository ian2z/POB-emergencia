package modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="atendimento_20242370040")
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate data;
    private String triagem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    //JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "upa_id", nullable = false)
    private Upa upa;

    public Atendimento() {}

    public Atendimento(String dataString, String triagem, Paciente paciente, Upa upa) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.data = LocalDate.parse(dataString, formato);
        this.triagem = triagem;
        this.paciente = paciente;
        this.upa = upa;
        this.upa.adicionar(this);
        this.paciente.adicionar(this);
    }

    public Atendimento(String dataString, Paciente paciente, Upa upa) {
        this(dataString, "Não informada", paciente, upa);
    }

    public int getId() { return id; }
    public String getTriagem() { return triagem; }
    public Paciente getPaciente() { return paciente; }
    public Upa getUpa() { return upa; }
    public String getData() { return data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")); }

    @Override
    public String toString() {
        // Verificação defensiva para evitar o NullPointerException
        String nomePaciente = (paciente != null) ? paciente.getNome() : "DESCONHECIDO";
        String nomeUpa = (upa != null) ? upa.getNome() : "DESCONHECIDA";

        return "Id: " + id + " | Data: " + getData() + " | Triagem: " + triagem +
                " | Paciente: " + nomePaciente + " | Upa: " + nomeUpa;
    }
}