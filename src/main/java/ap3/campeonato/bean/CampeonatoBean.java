package ap3.campeonato.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import ap3.campeonato.dao.CampeonatoDAO;
import ap3.campeonato.model.Campeonato;

@ManagedBean
public class CampeonatoBean {

	private Campeonato campeonato;
	private CampeonatoDAO campeonatoDAO;

	public CampeonatoBean() {
		this.campeonato = new Campeonato();
		this.campeonatoDAO = new CampeonatoDAO();
	}

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}

	public String salvar() {
		try {
			campeonatoDAO.salvar(campeonato);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Campeonato salvo com sucesso!", null));
			this.campeonato = new Campeonato();
			return null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao salvar campeonato: " + e.getMessage(), null));
			e.printStackTrace();
			return null;
		}
	}
}
