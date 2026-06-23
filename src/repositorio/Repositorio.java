package repositorio;

import jakarta.persistence.EntityManager;
import util.Util;

public abstract class Repositorio<T> {
    protected static EntityManager manager;

    public static void abrir() {
        if (manager == null) {
            Util.conectar();
            manager = Util.getManager();
        }
    }

    public static void fechar() {
        if (manager != null) {
            Util.desconectar();
            manager = null;
        }
    }

    public static void iniciarTransacao() {
        if (!manager.getTransaction().isActive()) {
            manager.getTransaction().begin();
        }
    }

    public static void confirmarTransacao() {
        if (manager.getTransaction().isActive()) {
            manager.getTransaction().commit();
        }
    }

    public static void cancelarTransacao() {
        if (manager.getTransaction().isActive()) {
            manager.getTransaction().rollback();
        }
    }

    public void criar(T obj) {
        manager.persist(obj);
    }

    public T atualizar(T obj) {
        return manager.merge(obj);
    }

    public void apagar(T obj) {
        manager.remove(obj);
    }

    public abstract T ler(Object chave);
}