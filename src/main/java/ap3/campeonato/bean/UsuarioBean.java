package ap3.campeonato.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;

import ap3.campeonato.dao.UsuarioDAO;
import ap3.campeonato.model.Usuario;

@ManagedBean
public class UsuarioBean {
	
	 private Usuario usuario = new Usuario();

	    public void salvar() {
	        UsuarioDAO.salvar(usuario);  // chamada estática
	        usuario = new Usuario();
	    }

	    public List<Usuario> getUsuarios() {
	        return UsuarioDAO.listar();  // chamada estática
	    }

	    public Usuario getUsuario() {
	        return usuario;
	    }

	    public void setUsuario(Usuario usuario) {
	        this.usuario = usuario;
	    }
}
