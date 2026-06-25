package appconsole;

import requisito.FachadaPaciente;

public class Apagar {

    public Apagar() {
        System.out.println("Apagando um objeto com relacionamentos (Paciente)...");

        FachadaPaciente fachadaPaciente = new FachadaPaciente();

        try {
            fachadaPaciente.excluirPaciente("45847563216");
            System.out.println("Paciente e seus atendimentos vinculados foram apagados com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao apagar: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Apagar();
    }
}