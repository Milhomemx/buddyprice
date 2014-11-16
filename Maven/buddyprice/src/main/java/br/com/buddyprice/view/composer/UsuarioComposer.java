package br.com.buddyprice.view.composer;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import br.com.buddyprice.control.UsuarioController;
import br.com.buddyprice.model.Usuario;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.com.vexillum.view.CRUDComposer;

/**
 * @author Natan
 * Classe responsável pelo compositor da abstração do Usuário.
 * Permite criação, ativação e manutenção da conta.
 * Este Composer se diferencia do ProfileComposer pelo fato de que o Usuario será suprirá as necessidades de cadastro, autenticação e ativação.
 */
@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class UsuarioComposer extends CRUDComposer<Usuario, UsuarioController> {

	String email2 = new String();
	String pass2 = new String();
	
	private String thisContextPath;
	
	public String getThisContextPath() {
		return thisContextPath;
	}

	public void setThisContextPath(String thisContextPath) {
		this.thisContextPath = thisContextPath;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getPass2() {
		return pass2;
	}

	public void setPass2(String pass2) {
		this.pass2 = pass2;
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		setThisContextPath(getContextPath());
		loadBinder();
	}

	@Override
	public UsuarioController getControl() {
		return SpringFactory.getController("usuarioController",
				UsuarioController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}

	/**
	 * @return
	 * Verifica se é administrador.
	 */
	public static boolean isAdministrator() {
		if (isLogged()) {
		}
		return true;
	}

	/**
	 * Redireciona à tela de edição da conta.
	 */
	public static void redirectToEdit() {
		Executions.sendRedirect("../user/edit.zul");
	}

	/**
	 * Redireciona à página principal.
	 */
	public static void redirectToRegister() {
		Executions.sendRedirect("pages/user/");
	}

	/**
	 * Redireciona à tela de login.
	 */
	public static void redirectToLogin() {
		Executions.sendRedirect("/pages/user/login.zul?sucess=true");
	}

	/**
	 * @return
	 * Verifica se existe um ID de usuário informado no HttpRequest.
	 */
	public static Boolean haveIdOnRequest() {
		String id = Executions.getCurrent().getParameter("id");
		return ((id != null && Integer.parseInt(id) >= 0));
	}

	/**
	 * @param id
	 * @return
	 * Busca a entidade Usuario conforme ID colhido.
	 */
	@SuppressWarnings("unchecked")
	public boolean initUserById(String id) {
		entity.setId(Long.parseLong(id));
		List<Usuario> list = (List<Usuario>) getControl().doAction(
				"searchByCriteria").getList();
		if (list != null && !list.isEmpty()) {
			entity = list.get(0);
			return true;
		}
		return false;
	}

	/**
	 * Registra usuário e direciona para a tela de login.
	 */
	public void register() {
		Return retRegister = new Return(true);
		retRegister = getControl().doAction("registerUser");
		if (retRegister.isValid()) {
			resetEntity();
			redirectToLogin();
		}
		treatReturn(retRegister);
	}

	/**
	 * Limpa o atributo entity.
	 */
	public void resetEntity() {
		entity = getEntityObject();
		loadBinder();
	}

	@Override
	public Usuario getEntityObject() {
		return new Usuario();
	}
}
