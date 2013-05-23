package br.com.buddyprice.control;

import java.util.HashMap;

import br.com.buddyprice.model.Usuario;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;

public class UsuarioController extends GenericControl<Usuario> {

	public UsuarioController(HashMap<String, Object> data) {
		super(data);
	}
	
	public Return registerUser() {
		Return regReturn = new Return(true);
		regReturn.concat(doAction("save"));
		if (regReturn.isValid()){
			//TODO email?
			regReturn.addMessage(new Message(null, "Conta Criada!"));
		}
		return regReturn;

		
	}
}
