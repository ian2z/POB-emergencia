package appconsole;

import java.util.List;
import modelo.Atendimento;
import modelo.Paciente;
import modelo.Upa;
import requisito.FachadaAtendimento;
import requisito.FachadaPaciente;
import requisito.FachadaUpa;

public class Listar {

    public Listar(){
        FachadaPaciente fachadaPaciente = new FachadaPaciente();
        FachadaAtendimento fachadaAtendimento = new FachadaAtendimento();
        FachadaUpa fachadaUpa = new FachadaUpa();

        System.out.println("\n---listagem de pacientes:");
        List<Paciente> pacientes = fachadaPaciente.listarPacientes();
        for(Paciente p: pacientes){
            System.out.println(p);
        }

        System.out.println("\n---listagem de atendimentos:");
        List<Atendimento> atendimentos = fachadaAtendimento.listarAtendimentos();
        for(Atendimento a: atendimentos){
            System.out.println(a);
        }

        System.out.println("\n---listagem de UPAs:");
        List<Upa> upas = fachadaUpa.listarUpas();
        for(Upa u: upas){
            System.out.println(u);
        }
    }

    public static void main(String[] args) {
        new Listar();
    }
}