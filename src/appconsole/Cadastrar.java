package appconsole;

import requisito.FachadaAtendimento;
import requisito.FachadaPaciente;
import requisito.FachadaUpa;

public class Cadastrar {

    public Cadastrar(){
        System.out.println("cadastrando objetos...");

        FachadaUpa fachadaUpa = new FachadaUpa();
        FachadaPaciente fachadaPaciente = new FachadaPaciente();
        FachadaAtendimento fachadaAtendimento = new FachadaAtendimento();

        try {
            //criando persistencias de UPA primeiro, para gerar os IDs
            fachadaUpa.cadastrarUpa("Mangabeira");
            fachadaUpa.cadastrarUpa("Bessa");
            fachadaUpa.cadastrarUpa("Bancários");

            //criando Paciente e Atendimento
            fachadaPaciente.cadastrarPaciente("13567915644", "João Carvalho", null);
            fachadaAtendimento.cadastrarAtendimento("29-03-2026", "Dor de cabeça intensa", "13567915644", "Mangabeira");

            fachadaPaciente.cadastrarPaciente("45847563216", "Sara Lopes", null);
            fachadaAtendimento.cadastrarAtendimento("29-03-2026", "Ânsia de Võmito", "45847563216", "Mangabeira");

            fachadaPaciente.cadastrarPaciente("16589754310", "Ramon Nobre", null); // Corrigido CPF para 11 dígitos para passar na validação
            fachadaAtendimento.cadastrarAtendimento("10-05-2026", "Fratura", "16589754310", "Bancários");

            fachadaPaciente.cadastrarPaciente("95675499830", "Omar Gomez", null);
            fachadaAtendimento.cadastrarAtendimento("06-04-2026", "Dores musculares", "95675499830", "Bancários");

            //exemplo Mário com mais de uma visita
            fachadaPaciente.cadastrarPaciente("22222222222", "Mario Castro", null);

            fachadaAtendimento.cadastrarAtendimento("01-01-2026", "Forte dores no peito", "22222222222", "Bessa");
            fachadaAtendimento.cadastrarAtendimento("02-01-2026", "Volta após medicação", "22222222222", "Bessa");
            fachadaAtendimento.cadastrarAtendimento("03-01-2026", "Dor de cabeça intensa", "22222222222", "Mangabeira");

            System.out.println("Cadastro finalizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Cadastrar();
    }
}