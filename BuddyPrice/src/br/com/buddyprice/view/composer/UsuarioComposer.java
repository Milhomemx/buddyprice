package br.com.buddyprice.view.composer;

import br.com.buddyprice.control.UsuarioController;
import br.com.buddyprice.model.Usuario;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.view.composer.CRUDComposer;


public class UsuarioComposer extends CRUDComposer<Usuario> {

	@Override
	public GenericControl<Usuario> getControl() {
		UsuarioController a = new UsuarioController(ReflectionUtils.prepareDataForPersistence(this));
		return a;
	}

	@Override
	public Usuario getObjetoDominio() {
		return new Usuario();
	}

}
