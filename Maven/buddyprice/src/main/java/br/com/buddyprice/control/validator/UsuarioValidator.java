package br.com.buddyprice.control.validator;

import java.util.HashMap;
import java.util.Map;

import br.com.buddyprice.control.UsuarioController;
import br.com.buddyprice.model.Usuario;
import br.com.vexillum.control.validator.Validator;
import br.com.vexillum.util.EncryptUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;

public class UsuarioValidator extends Validator {

	public UsuarioValidator(Map<String, Object> mapData) {
		super(mapData);
	}

	public Return validateRegisterUser() {
		Return ret = super.validateSave();
		ret.concat(equalsEmail());
		ret.concat(equalsPassWord());
		ret.concat(existsEmail());
		return ret;
	}

	public Return validateUpdateInformation() {
		return validateModel();
	}

	private Return existsEmail() {
		Return ret = new Return(true);
		HashMap<String, Object> data = new HashMap<String, Object>();

		data.put(
				"sql",
				"FROM Usuario u WHERE u.email = '"
						+ ((Usuario) entity).getEmail() + "'");

		UsuarioController controller = getUserController(data);
		if (!controller.searchByHQL().getList().isEmpty()) {
			ret.concat(creatReturn("email",
					getValidationMessage("email", "exists", true)));
		}
		return ret;
	}

	private UsuarioController getUserController(HashMap<String, Object> data) {
		UsuarioController controller = SpringFactory.getController(
				"usuarioController", UsuarioController.class, data);
		return controller;
	}

	public Return equalsEmail() {
		Return ret = equalsFields(((Usuario) entity).getEmail(),
				(String) mapData.get("email2"));
		if (!ret.isValid()) {
			ret.concat(creatReturn("email2",
					getValidationMessage("email2", "equalsEmail", false)));
		}
		return ret;
	}

	public Return equalsPassWord() {
		Return ret = equalsFields(((Usuario) entity).getPassword(),
				(String) mapData.get("pass2"));
		if (!ret.isValid()) {
			ret.concat(creatReturn("pass2",
					getValidationMessage("pass2", "equalsPassWord", false)));
		}
		return ret;
	}

	public Return validateChangePasswordUser() {
		Return ret = new Return(true);
		String password = ((Usuario) entity).getPassword();
		String actualPassword = (String) mapData.get("actualPassword");
		String newPassword = (String) mapData.get("newPassword");
		String confirmNewPassword = (String) mapData.get("confirmNewPassword");

		if (actualPassword == null || actualPassword.equalsIgnoreCase(""))
			ret.concat(creatReturn("actualPassword",
					getValidationMessage("", "notnull", false)));
		if (newPassword == null || newPassword.equalsIgnoreCase(""))
			ret.concat(creatReturn("newPassword",
					getValidationMessage("", "notnull", false)));
		if (confirmNewPassword == null || confirmNewPassword.equalsIgnoreCase(""))
			ret.concat(creatReturn("confirmNewPassword",
					getValidationMessage("", "notnull", false)));
		if (!equalsFields(password,
				EncryptUtils.encryptOnSHA512(actualPassword)).isValid())
			ret.concat(creatReturn("actualPassword",
					getValidationMessage("actualPassword", "equals", false)));
		if (!equalsFields(newPassword, confirmNewPassword).isValid())
			ret.concat(creatReturn("confirmNewPassword",
					getValidationMessage("password", "equals", false)));

		if (newPassword != null && newPassword.length() < 6)
			ret.concat(creatReturn("newPassword",
					getValidationMessage("password", "min", false) + " 6"));
		if (confirmNewPassword != null && confirmNewPassword.length() < 6)
			ret.concat(creatReturn("confirmNewPassword",
					getValidationMessage("password", "min", false) + " 6"));

		return ret;
	}
}
