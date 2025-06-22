package ap3.campeonato.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ap3.campeonato.model.Campeonato;
import ap3.campeonato.util.JPAUtil;

public class CampeonatoDAO {

    public List<Campeonato> listarTodos() {
    	EntityManager em = JPAUtil.criarEntityManager();
        List<Campeonato> lista = em.createQuery("FROM Campeonato", Campeonato.class).getResultList();
        em.close();
        return lista;
    }

    public void salvar(Campeonato campeonato) {
    	EntityManager em = JPAUtil.criarEntityManager();
        em.getTransaction().begin();
        try {
            if (campeonato.getId() == null) {
                em.persist(campeonato);
            } else {
                em.merge(campeonato);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar Campeonato: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    public Campeonato buscarPorId(Integer id) {
    	EntityManager em = JPAUtil.criarEntityManager();
        Campeonato c = em.find(Campeonato.class, id);
        em.close();
        return c;
    }


}