import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import ap3.campeonato.dao.JogoDAO;
import ap3.campeonato.dao.CampeonatoDAO;
import ap3.campeonato.model.Campeonato;
import ap3.campeonato.model.Jogo;
import ap3.campeonato.model.ResumoTime;

@ManagedBean
public class JogoBean {

	private Jogo jogo = new Jogo();
	private CampeonatoDAO campeonatoDAO = new CampeonatoDAO();
	private List<Campeonato> campeonatoList = null;
	private List<Jogo> listaJogos;
	private List<ResumoTime> resumoTimes;
	/*private String timeFiltro;*/
	private List<Jogo> jogosFiltrados;
	private List<String> listaTimes;
	private String timeSelecionado;


	private Integer campeonatoSelecionadoId;

	
	public JogoBean() {
		jogo.setCampeonato(new Campeonato());
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	public List<Campeonato> getCampeonatoList() {
		return campeonatoDAO.listarTodos();
	}

	public void setCampeonatoList(List<Campeonato> campeonatoList) {
		this.campeonatoList = campeonatoList;
	}

	public Integer getCampeonatoSelecionadoId() {
		return campeonatoSelecionadoId;
	}

	public void setCampeonatoSelecionadoId(Integer id) {
		this.campeonatoSelecionadoId = id;
	}
	
	public String getTimeSelecionado() {
	    return timeSelecionado;
	}

	public void setTimeSelecionado(String timeSelecionado) {
	    this.timeSelecionado = timeSelecionado;
	}

	public List<Jogo> getListaJogos() {
	    if (listaJogos == null) {
	        listaJogos = JogoDAO.listar(); // esse método precisa estar no DAO
	    }
	    return listaJogos;
	}

	public List<ResumoTime> getResumoTimes() {
	    if (resumoTimes == null) gerarResumo();
	    return resumoTimes;
	}

	public List<Jogo> getJogosFiltrados() {
		return jogosFiltrados;
	}

	public void setJogosFiltrados(List<Jogo> jogosFiltrados) {
	    this.jogosFiltrados = jogosFiltrados;
	}
	
	
	public void salvar() {
		Campeonato campeonatoSelecionado = null;
		
		if (jogo.getTime1() != null && jogo.getTime1().equalsIgnoreCase(jogo.getTime2())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de validação Time 1 e Time 2 não podem ser iguais", "Time 1 e Time 2 não podem ser iguais"));
			return;
		}
		
		// Buscar Campeonato selecionado por ID
		if(campeonatoSelecionadoId > 0) {
			campeonatoSelecionado = campeonatoDAO.buscarPorId(campeonatoSelecionadoId);
			jogo.setCampeonato(campeonatoSelecionado);
		}
		

		jogo.setDataCadastro(new Date());
		JogoDAO.salvar(jogo);

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Jogo salvo com sucesso!"));

		jogo = new Jogo();
		jogo.setCampeonato(new Campeonato());
	}
	
	public void excluir(Jogo jogo) {
	    JogoDAO.excluir(jogo.getId());
	    listaJogos = null; // Força recarregar a lista
	    FacesContext.getCurrentInstance().addMessage(null,
	        new FacesMessage("Jogo excluído com sucesso!"));
	}
	
	public void onRowEdit(RowEditEvent<Jogo> event) {
	    Jogo jogoEditado = event.getObject();
	    JogoDAO.atualizar(jogoEditado);
	    
	    FacesContext.getCurrentInstance().addMessage(null,
	        new FacesMessage("Jogo atualizado com sucesso: " + jogoEditado.getTime1() + " x " + jogoEditado.getTime2()));
	}
	
	public void onRowCancel(RowEditEvent<Jogo> event) {
	    FacesContext.getCurrentInstance().addMessage(null,
	        new FacesMessage(FacesMessage.SEVERITY_INFO, "Edição cancelada", ""));
	}
	
	public void gerarResumo() {
		
		System.out.println("ok");
		
	    List<Jogo> jogos = JogoDAO.listar();
	    Map<String, ResumoTime> mapa = new HashMap<String, ResumoTime>();

	    // 1. Percorre todos os jogos
	    for (Jogo jogo : jogos) { 
	    	
	    	//2. Recupera Nome dos times
	        String t1 = jogo.getTime1();
	        String t2 = jogo.getTime2();
	        
	        //3. Recupera Gols de cada time
	        int g1 = jogo.getGolsTime1();
	        int g2 = jogo.getGolsTime2();

	        //4. Verifica se o time já possui um resumo criado, caso tenha é recuperado, caso não um resumo p/  o time é criado
	        ResumoTime r1 = mapa.get(t1);
	        if (r1 == null) {
	            r1 = new ResumoTime(t1);
	            mapa.put(t1, r1);
	        }

	        ResumoTime r2 = mapa.get(t2);
	        if (r2 == null) {
	            r2 = new ResumoTime(t2);
	            mapa.put(t2, r2);
	        }

	        
	        
	        // Setar Gols marcados de cada time 
	        r1.addGolsMarcados(g1);
	        r1.addGolsSofridos(g2);
	        r2.addGolsMarcados(g2);
	        r2.addGolsSofridos(g1);
	        
	        // Define o resultado da partida, vitória ou derrota e adiciona os pontos ao(s) time(s)
	        if (g1 > g2) {
	            r1.addVitoria(); r1.addPontos(3);
	            r2.addDerrota();
	        } else if (g2 > g1) {
	            r2.addVitoria(); r2.addPontos(3);
	            r1.addDerrota();
	        } else {
	            r1.addEmpate(); r1.addPontos(1);
	            r2.addEmpate(); r2.addPontos(1);
	        }
	    }

	    resumoTimes = new ArrayList<>(mapa.values());
	}

	// Preenche os times com base nos jogos existentes
	public List<String> getListaTimes() {
	    if (listaTimes == null) {
	        listaTimes = new ArrayList<>();
	        List<Jogo> jogos = JogoDAO.listar();
	        for (Jogo j : jogos) {
	            if (!listaTimes.contains(j.getTime1())) listaTimes.add(j.getTime1());
	            if (!listaTimes.contains(j.getTime2())) listaTimes.add(j.getTime2());
	        }
	    }
	    return listaTimes;
	}

	public void buscarPorTime() {
	    if (timeSelecionado != null && !timeSelecionado.isEmpty()) {
	        jogosFiltrados = JogoDAO.buscarPorTime(timeSelecionado);
	    }
	}
	
}
