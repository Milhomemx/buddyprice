package br.com.buddyprice.control.validator;

import java.util.Map;

import br.com.buddyprice.model.Usuario;
import br.com.vexillum.control.validator.Validator;
import br.com.vexillum.util.Return;

public class UsuarioValidator extends Validator {

	public UsuarioValidator(Map<String, Object> mapData) {
		
		super(mapData);
	}

	
	public Return validateSave() {
		Return ret = super.validateSave();
		ret.concat(equalsEmail());
		ret.concat(equalsPassWord());
		return ret;
	};
	
	public Return equalsEmail(){
		Return ret = equalsFields(((Usuario)entity).getEmail(), (String)mapData.get("email2"));
		if(!ret.isValid()){
			ret.concat(creatReturn("email2", getValidationMessage("email2", "equalsEmail", false))); //fld ele coloca sozinho
		}
		return ret;	
	}
	
	public Return equalsPassWord(){
		Return ret = equalsFields(((Usuario)entity).getPassword(), (String)mapData.get("pass2"));
		if(!ret.isValid()){
			ret.concat(creatReturn("pass2", getValidationMessage("pass2", "equalsPass", false))); //fld ele coloca sozinho
		}
		return ret;	
	}
	
}
