package br.com.buddyprice.control.validator;

import java.util.Map;

import br.com.vexillum.control.validator.Validator;
import br.com.vexillum.util.Return;

/**
 * @author Natan
 * Validador da abstra��o Amizade. Valida todas as limita��es determinadas para os atributos do modelo.
 * Extende do Validator.
 */
public class FriendshipValidator extends Validator {

	public FriendshipValidator(Map<String, Object> mapData) {
		
		super(mapData);
	}

	
	public Return validateSave() {
		Return ret = super.validateSave();
		return ret;
	}
	
}
