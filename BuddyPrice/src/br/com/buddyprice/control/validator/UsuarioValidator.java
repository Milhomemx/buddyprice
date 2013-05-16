package br.com.buddyprice.control.validator;

import br.com.buddyprice.model.Usuario;
import br.com.vexillum.control.validator.Validator;

public class UsuarioValidator extends Validator<Usuario>{

	public UsuarioValidator(Usuario entity, String action) {
		super(entity, action);
	}

}
