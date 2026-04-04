package modelo;

public class Atendimento {
    private int id;
    private String data;
    private Paciente paciente;
    private Upa upa;

    public Atendimento(String data, Paciente paciente, Upa upa) {
        this.id = id;
        this.data = data;
        this.paciente = paciente;
        this.upa = upa;

        //configurando o relacionamento bilateral
        // Chama o metodo que já existe na sua classe Upa
        this.upa.adicionar(this);
        // Certifique-se de que a classe Paciente também tenha um metodo adicionar()
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

    public int getId() { return id; }

    @Override
    public String toString() {
        return "Id: " + id + " | " + "Data: " + data + " | " + "Paciente: " + paciente.getCpf() + " | " + "Upa: " + upa.getNome();
    }

}
