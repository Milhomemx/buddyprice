package br.com.buddyprice.control;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.Usuario;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.control.manager.EmailManager;
import br.com.vexillum.model.Category;
import br.com.vexillum.util.EncryptUtils;
import br.com.vexillum.util.Return;

@Service
@Scope("prototype")
public class UsuarioController extends GenericControl<Usuario> {
	public UsuarioController() {
		super(Usuario.class);
	}

	public Return updateInformation() {
		return update();
	}

	public Return deactivate() {
		entity.setActive(false);
		entity.setVerificationCode(generateVerificationCode());
		Return ret = super.update();
		if(ret.isValid()){
			sendValidationEmailAccount(entity.getEmail(), entity.getVerificationCode(), "Reative sua conta BuddyPrice!");
		}
		return ret;
	}

	public Return nullit() {
		Return ret = new Return(false);

		return ret;
	}

	public Return changePasswordUser() {
		Return ret = new Return(true);
		entity.setPassword(EncryptUtils.encryptOnSHA512((String) data
				.get("newPassword")));
		ret.concat(update());
		return ret;
	}

	public Usuario getUserByCode(String code) {
		String hql = "from Usuario where verificationCode ='" + code + "'";
		data.put("sql", hql);
		Return retCode = searchByHQL();
		if (retCode.getList() != null && !retCode.getList().isEmpty())
			return (Usuario) retCode.getList().get(0);
		return null;
	}

	public Return registerUser() {
		Return retRegister = new Return(true);
		entity = novoUsuarioBuddyPrice();
		retRegister.concat(save());
		if (retRegister.isValid()) {
			sendValidationEmailAccount(entity.getEmail(),
					entity.getVerificationCode(), "Conta criada com sucesso, Bem vindo(a) a BuddyPrice!");
		}
		return retRegister;
	}

	//TODO Colocar para carregar o arquivo com HTML do email de acordo com o tipo de mensagem se ativacao ou reativacao
	private void sendValidationEmailAccount(String emailAdres, String code, String subject) {
		EmailManager email = new EmailManager("HtmlEmail");
		// String message = loadHmtlForEmail();
		String link = "<a href='http://localhost:8080/buddyprice/pages/user/validateaccount.zul?code="
				+ code + "'>aqui</a>";
		String message = "Clique " + link;
		email.sendEmail(emailAdres, subject, message);
	}

	public String loadHmtlForEmail() {
		String aux = "";
		try {
			String absolutePath = "D:/Program Files/JBoss/jboss-as-7.1.1.Final/standalone/deployments/buddyprice.war/resources/email/ConfirmRegistering.html";
			List<String> lines = Files.readAllLines(Paths.get(absolutePath),
					StandardCharsets.UTF_8);
			for (String string : lines) {
				aux += string;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aux;
	}

	private Usuario novoUsuarioBuddyPrice() {
		Usuario user = entity;
		user.setPassword(EncryptUtils.encryptOnSHA512(entity.getPassword()));
		user.setCategory(buscaCategoriaByName("ROLE_USER"));
		user.setVerificationCode(generateVerificationCode());
		user.setActive(false);
		return user;
	}

	private Category buscaCategoriaByName(String name) {
		String hql = "from Category where name ='" + name + "'";
		data.put("sql", hql);
		Return retCategory = searchByHQL();
		return (Category) retCategory.getList().get(0);
	}

	private String generateVerificationCode() {
		Random rand = new Random();
		String code = "";
		do {
			for (int i = 0; i < 10; i++) {
				int number = rand.nextInt(10);
				code += number;
			}
		} while (getUserByCode(code) != null);
		return code;
	}

	public Return validateAccountUser() {
		String code = (String) data.get("code");
		Return retValid = new Return(true);
		entity = getUserByCode(code);
		if (entity == null) {
			retValid.setValid(false);
			return retValid;
		}
		entity.setVerificationCode(null);
		entity.setActive(true);
		retValid.concat(update());
		return retValid;
	}

}