package ap3.campeonato.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


import ap3.campeonato.model.Jogo;
import ap3.campeonato.util.JPAUtil;

public class JogoDAO {

	public static void salvar(Jogo jogo) {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(jogo);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public static void atualizar(Jogo jogo) {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(jogo);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public static void excluir(Integer id) {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			em.getTransaction().begin();
			Jogo jogo = em.find(Jogo.class, id);
			if (jogo != null) {
				em.remove(jogo);
			}
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public static List<Jogo> listar() {
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			TypedQuery<Jogo> query = em.createQuery("SELECT j FROM Jogo j", Jogo.class);
			return query.getResultList();
		} finally {
			em.close();
		}
	}
	
	public static List<Jogo> buscarPorTime(String time) {
		EntityManager em = JPAUtil.criarEntityManager();
	    List<Jogo> resultados = em.createNamedQuery("Jogo.buscarPorTime", Jogo.class)
	                              .setParameter("time", time)
	                              .getResultList();

	    em.close();
	    return resultados;
	}

}
