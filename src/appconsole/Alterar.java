package appconsole;

import java.util.List;
import modelo.Atendimento;
import requisito.FachadaAtendimento;

public class Alterar {

    public Alterar() {
        System.out.println("Removendo um relacionamento (Atendimento)...");

        FachadaAtendimento fachadaAtendimento = new FachadaAtendimento();

        try {
            //buscamos atendimentos específicos por CPF
            List<Atendimento> atendimentos = fachadaAtendimento.consultarAtendimentosPorCpfPaciente("13567915644");
            
            if (!atendimentos.isEmpty()) {
                Atendimento atendimento = atendimentos.get(0);
                fachadaAtendimento.excluirAtendimento(atendimento.getId());
                System.out.println("Atendimento removido com sucesso!");
            } else {
                System.out.println("Atendimento não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao alterar: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Alterar();
    }
}