package ap3.campeonato.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import ap3.campeonato.dao.UsuarioDAO;
import ap3.campeonato.model.Usuario;

@ManagedBean
public class LoginBean {

	private String login;
	private String senha;

	public String login() {

		Usuario usuario = UsuarioDAO.buscarPorLogin(login);
		

		// Validação Usuário não existe
		if (usuario == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não encontrado", null));
			return null;
		}
		
		// Validação Senha incorreta
		if (!usuario.getSenha().equals(senha)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha incorreta", null));
			return null;
		}
		
		return "opcoes?faces-redirect=true";
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
