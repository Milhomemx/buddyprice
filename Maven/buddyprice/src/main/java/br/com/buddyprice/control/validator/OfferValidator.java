package br.com.buddyprice.control.validator;

import java.util.Map;

import br.com.vexillum.control.validator.Validator;
import br.com.vexillum.util.Return;

/**
 * @author Natan
 * Validador da abstra��o Oferta. Valida todas as limita��es determinadas para os atributos do modelo.
 * Extende do Validator.
 */
public class OfferValidator extends Validator {

	public OfferValidator(Map<String, Object> mapData) {
		
		super(mapData);
	}

	
	public Return validateSaveComentary() {
		Return ret = new Return(true);
		if(mapData.get("fldAvaliacao") == null){
			ret.concat(creatReturn("", getValidationMessage("comentario_avaliacao", "notNull", false)));
		} 
		if(mapData.get("fldComentario") == null || ((String) mapData.get("fldComentario")).isEmpty()){
			ret.concat(creatReturn("", getValidationMessage("comentario_comentario", "notNull", false)));
		}
		return ret;
	}
	
}
