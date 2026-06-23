package fachada;

import java.util.List;
import modelo.Upa;
import repositorio.Repositorio;
import repositorio.RepositorioUpa;

public class FachadaUpa {
    private RepositorioUpa repUpa = new RepositorioUpa();

    public void cadastrarUpa(String nome) throws Exception {
        Repositorio.abrir();
        Repositorio.iniciarTransacao();
        try {
            if (nome == null || nome.trim().isEmpty()) {
                throw new Exception("Nome da UPA inválido.");
            }
            Upa u = repUpa.ler(nome);
            if (u != null) {
                throw new Exception("UPA com este nome já cadastrada.");
            }

            u = new Upa(nome);
            repUpa.criar(u);
            Repositorio.confirmarTransacao();
        } catch (Exception e) {
            Repositorio.cancelarTransacao();
            throw e;
        }
    }

    public List<Upa> listarUpas() {
        Repositorio.abrir();
        return repUpa.listarTodos();
    }
}