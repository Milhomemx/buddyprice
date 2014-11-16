package br.com.buddyprice.control.validator;

import java.util.HashMap;
import java.util.Map;

import br.com.buddyprice.control.UsuarioController;
import br.com.buddyprice.model.Usuario;
import br.com.vexillum.control.validator.Validator;
import br.com.vexillum.util.EncryptUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;

/**
 * @author Natan
 * Validador da abstra��o Usu�rio. Valida todas as limita��es determinadas para os atributos do modelo,
 * bem como as valida��es de neg�cio (E-mail existe, tamanho da senha � maior ou igual a 6, etc).
 * Extende do Validator.
 */
public class UsuarioValidator extends Validator {

	public UsuarioValidator(Map<String, Object> mapData) {
		super(mapData);
	}

	/**
	 * @return
	 * Valida o registro do usu�rio. Verifica se os dois campos de e-mail s�o iguais, se os dois campos com o password s�o iguais e verifica se o e-mail j� existe no cadastro de usu�rios.
	 */
	public Return validateRegisterUser() {
		Return ret = super.validateSave();
		ret.concat(equalsEmail());
		ret.concat(equalsPassWord());
		ret.concat(existsEmail());
		return ret;
	}

	/**
	 * @return
	 * Valida a altera��o dos dados, conforme as regras de valida��o definidas no modelo Usuario.
	 */
	public Return validateUpdateInformation() {
		return validateModel();
	}

	/**
	 * @return
	 * Verifica exist�ncia do e-mail no banco de dados, com o intuito de impedir que dois usu�rio diferentes tenham o mesmo login.
	 */
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

	/**
	 * @param data
	 * @return
	 * Pega o UsuarioController relacionado.
	 */
	private UsuarioController getUserController(HashMap<String, Object> data) {
		UsuarioController controller = SpringFactory.getController(
				"usuarioController", UsuarioController.class, data);
		return controller;
	}

	/**
	 * @return
	 * Verifica se os dois e-mails informados s�o iguais.
	 */
	public Return equalsEmail() {
		Return ret = equalsFields(((Usuario) entity).getEmail(),
				(String) mapData.get("email2"));
		if (!ret.isValid()) {
			ret.concat(creatReturn("email2",
					getValidationMessage("email2", "equalsEmail", false)));
		}
		return ret;
	}

	/**
	 * @return
	 * Verifica se os dois passWords informados s�o iguais.
	 */
	public Return equalsPassWord() {
		Return ret = equalsFields(((Usuario) entity).getPassword(),
				(String) mapData.get("pass2"));
		if (!ret.isValid()) {
			ret.concat(creatReturn("pass2",
					getValidationMessage("pass2", "equalsPassWord", false)));
		}
		return ret;
	}

	/**
	 * @return
	 * Valida mudan�a de PassWord. Valida se a senha antiga � igual a informada, valida e os dois campos da nova senha s�o iguais e valida se a nova senha atende os requisitos de tamanho m�nimo.
	 */
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
		
		if(actualPassword != null){
			if (!equalsFields(password,	EncryptUtils.encryptOnSHA512(actualPassword)).isValid())
				ret.concat(creatReturn("actualPassword",
						getValidationMessage("actualPassword", "equals", false)));
		}
		if(confirmNewPassword != null){
			if (confirmNewPassword != null && !equalsFields(newPassword, confirmNewPassword).isValid())
				ret.concat(creatReturn("confirmNewPassword",
						getValidationMessage("password", "equals", false)));
		}

		if (newPassword != null && newPassword.length() < 6)
			ret.concat(creatReturn("newPassword",
					getValidationMessage("password", "min", false) + " 6"));
		if (confirmNewPassword != null && confirmNewPassword.length() < 6)
			ret.concat(creatReturn("confirmNewPassword",
					getValidationMessage("password", "min", false) + " 6"));

		return ret;
	}
}
