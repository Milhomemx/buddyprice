package br.com.buddyprice.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import br.com.buddyprice.control.EstablishmentController;
import br.com.buddyprice.control.UsuarioController;
import br.com.buddyprice.model.Estabelecimento;
import br.com.buddyprice.model.Usuario;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.com.vexillum.view.CRUDComposer;

@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class LeftSidebarComposer extends
		CRUDComposer<Usuario, UsuarioController> {

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		loadBinder();
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
