package br.com.buddyprice.view.composer;

import org.springframework.core.io.support.SpringFactoriesLoader;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import br.com.buddyprice.control.UsuarioController;
import br.com.buddyprice.model.Usuario;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.SpringFactory;
import br.com.vexillum.view.CRUDComposer;


@SuppressWarnings("serial")
public class UsuarioComposer extends CRUDComposer<Usuario, UsuarioController>{

	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		loadBinder();
	}
	
	@Override
	 public UsuarioController getControl() {
	 return SpringFactory.getController("usuarioController", UsuarioController.class, ReflectionUtils.prepareDataForPersistence(this));
	 }

	public void registerUser() {
		treatReturn(getControl().registerUser());
		
		Executions.sendRedirect("./pages/user/main.zul");
		

	}
	
	@Override
	 public Usuario getEntityObject() {
	 return new Usuario();
	 }

}
