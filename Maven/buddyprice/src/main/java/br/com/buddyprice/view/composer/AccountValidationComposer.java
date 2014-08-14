package br.com.buddyprice.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import br.com.buddyprice.control.UsuarioController;
import br.com.buddyprice.model.Usuario;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.SpringFactory;
import br.com.vexillum.view.CRUDComposer;

@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class AccountValidationComposer extends
		CRUDComposer<Usuario, UsuarioController> {

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		code = Executions.getCurrent().getParameter("code");
		super.doAfterCompose(comp);
		if (code != null)
			validateAccount();
		loadBinder();
	}

	private void validateAccount() {
		if (getControl().doAction("validateAccountUser").isValid()) {
			Executions.sendRedirect("/pages/user/login.zul?validationsucess=true");
		} else {
			Executions.sendRedirect("/pages/user/login.zul?validationfailure=true");
		}
	}

	@Override
	public UsuarioController getControl() {
		return SpringFactory.getController("usuarioController",
				UsuarioController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}

	@Override
	public Usuario getEntityObject() {
		return new Usuario();
	}
}
