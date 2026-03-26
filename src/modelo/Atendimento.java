package modelo;

public class Atendimento {
    private Integer id;
    private String data;
    private Paciente paciente;
    private Upa upa;

    public Atendimento(Integer id, String data, Paciente paciente, Upa upa) {
        this.id = id;
        this.data = data;
        this.paciente = paciente;
        this.upa = upa;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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

    @Override
    public String toString() {
        return "ID: " + id + " | " + "Data: " + data + " | " + "Paciente: " + paciente + " | " + "Upa: " + upa;
    }

}
