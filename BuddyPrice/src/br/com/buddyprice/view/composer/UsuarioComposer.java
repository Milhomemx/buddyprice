package br.com.buddyprice.view.composer;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import br.com.buddyprice.control.UsuarioController;
import br.com.buddyprice.model.Usuario;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.view.composer.CRUDComposer;


@SuppressWarnings("serial")
public class UsuarioComposer extends CRUDComposer<Usuario, UsuarioController>{

	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		loadBinder();
	}
	
	@Override
	 public UsuarioController getControl() {
	 return new UsuarioController(ReflectionUtils.prepareDataForPersistence(this));
	 }

	public void registerUser() {
		treatReturn(getControl().registerUser());
		//validar sucesso do cadastro
		Executions.sendRedirect("/pages/filePosition.zul");

	}
	
	@Override
	 public Usuario getEntityObject() {
	 return new Usuario();
	 }

}
