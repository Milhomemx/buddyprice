package br.com.buddyprice.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.Usuario;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.model.Category;
import br.com.vexillum.util.Return;

@Service
@Scope("prototype")
public class LoginController extends GenericControl<Usuario> {

	public LoginController() {
		super(Usuario.class);
	}

	public Usuario getUser(String email, String password) {
		Usuario usuario = getUserByMail(email);
		if (usuario != null && usuario.getPassword().equalsIgnoreCase(password)) {
			return usuario;
		}
		return null;
	}

	public Usuario getUserByMail(String email) {
		String hql = "FROM Usuario as u where email ='" + email + "'";
		data.put("sql", hql);
		Return ret = searchByHQL();
		if (ret.getList().isEmpty())
			return null;
		return (Usuario) ret.getList().get(0);
	}

	public Category getCategoryUser(Usuario user) {
		String hql = "from Category where id in (select u.category.id from UserBasic as u where id ='"
				+ user.getId() + "')";
		data.put("sql", hql);
		Return retCategory = searchByHQL();
		return (Category) retCategory.getList().get(0);
	}

}
