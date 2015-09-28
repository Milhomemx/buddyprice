package br.com.buddyprice.control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.Usuario;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.control.manager.EmailManager;
import br.com.vexillum.model.Category;
import br.com.vexillum.util.EncryptUtils;
import br.com.vexillum.util.Return;

/**
 * @author Natan
 * Controlador da abstração Usuário. Gere todos os cenários relacionados ao perfil do usuário (criação de contas, alteração de atributos, etc).
 * Extende do controle genérico.
 */
@Service
@Scope("prototype")
public class UsuarioController extends GenericControl<Usuario> {
	public UsuarioController() {
		super(Usuario.class);
	}

	/**
	 * @return
	 * Sobe os dados alterados pelo usuário.
	 */
	public Return updateInformation() {
		return update();
	}

	/* (non-Javadoc)
	 * @see br.com.vexillum.control.GenericControl#deactivate()
	 * Desativa a conta do usuário e envia um e-mail para reativação.
	 */
	public Return deactivate() {
		entity.setActive(false);
		entity.setVerificationCode(generateVerificationCode());
		Return ret = super.update();
		if (ret.isValid()) {
			sendValidationEmailAccount(entity.getEmail(),
					entity.getVerificationCode(),
					"Reative sua conta BuddyPrice!");
		}
		return ret;
	}

	/**
	 * @return
	 * Limpa o retorno e faz um <i>refresh</i> na entidade.
	 */
	public Return nullit() {
		Return ret = new Return(false);

		return ret;
	}

	/**
	 * @return
	 * Altera o PassWord do usuário. Encripta o novo PassWord antes de salvá-lo.
	 */
	public Return changePasswordUser() {
		Return ret = new Return(true);
		entity.setPassword(EncryptUtils.encryptOnSHA512((String) data
				.get("newPassword")));
		ret.concat(update());
		return ret;
	}

	/**
	 * @param code
	 * @return
	 * Retorna um usuário através do ID informado.
	 */
	public Usuario getUserByCode(String code) {
		String hql = "from Usuario where verificationCode ='" + code + "'";
		data.put("sql", hql);
		Return retCode = searchByHQL();
		if (retCode.getList() != null && !retCode.getList().isEmpty())
			return (Usuario) retCode.getList().get(0);
		return null;
	}

	/**
	 * @return
	 * Cria um novo usuário, conforme parâmetros informados. Retorna mensagem de sucesso da criação.
	 */
	public Return registerUser() {
		Return retRegister = new Return(true);
		entity = novoUsuarioBuddyPrice();
		retRegister.concat(save());
		if (retRegister.isValid()) {
			sendValidationEmailAccount(entity.getEmail(),
					entity.getVerificationCode(),
					"Conta criada com sucesso, Bem vindo(a) a BuddyPrice!");
		}
		return retRegister;
	}

	/**
	 * @param emailAdres
	 * @param code
	 * @param subject
	 * Envia um e-mail de validação para o Usuário ativar sua própria conta.
	 * Este recurso permite que seja possível validar a autenticidade do e-mail informado.
	 */
	private void sendValidationEmailAccount(String emailAdres, String code,
			String subject) {
		EmailManager email = new EmailManager("HtmlEmail");
		String link = (String) data.get("thisContextPath") + "/pages/user/validateaccount.zul?code=" + code;
		String initialPage = (String) data.get("thisContextPath");
		String message = loadHmtlForEmail("ConfirmRegistering.html", link, initialPage);
		email.sendEmail(emailAdres, subject, message);
	}

	/**
	 * @param name
	 * @param link
	 * @param initialPage
	 * @return
	 * Carrega o HTML para o envio do e-mail do método sendValidationEmailAccount
	 */
	public String loadHmtlForEmail(String name, String link, String initialPage) {
		StringBuilder aux = new StringBuilder();
		String folder = "/resources/email/";
		String absolutePath = data.get("thisContextPath") + folder	+ name;
		Document doc;
		try {
			doc = Jsoup.connect(absolutePath).get();
			doc.getElementById("link").attr("href", link);
			doc.getElementById("initialpage").attr("href", initialPage);
			aux.append(doc.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return aux.toString();
	}
	
	/**
	 * @param name
	 * @return
	 * Carrega um arquivo HTML para a construção do e-mail a ser enviado pelo método loadHmtlForEmail.
	 */
	public String getHtmlFile(String name){
		StringBuilder contentBuilder = new StringBuilder();
		try {
			String aux = "/resources/email/";
		    BufferedReader in = new BufferedReader(new FileReader(aux + name));
		    String str;
		    while ((str = in.readLine()) != null) {
		        contentBuilder.append(str);
		    }
		    in.close();
		} catch (IOException e) {
		}
		return contentBuilder.toString();
	}

	/**
	 * @return
	 * Cria um novo usuário.
	 */
	private Usuario novoUsuarioBuddyPrice() {
		Usuario user = entity;
		user.setPassword(EncryptUtils.encryptOnSHA512(entity.getPassword()));
		user.setCategory(buscaCategoriaByName("ROLE_USER"));
		user.setVerificationCode(generateVerificationCode());
		user.setActive(false);
		return user;
	}

	/**
	 * @param name
	 * @return
	 * Retorna a categoria do usuário pela pesquisa do nome do mesmo.
	 */
	private Category buscaCategoriaByName(String name) {
		String hql = "from Category where name ='" + name + "'";
		data.put("sql", hql);
		Return retCategory = searchByHQL();
		return (Category) retCategory.getList().get(0);
	}

	/**
	 * @return
	 * Gera o código de verificação do usuário para validação da autenticidade feita pelo método validateAccountUser.
	 */
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

	/**
	 * @return
	 * Valida se o código fornecido via parâmetro get HTTP request é igual ao parâmetro esperado pela relação de contas inativas.
	 */
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
	
	public Return searchUsers(){
		Return ret = new Return(true);
		String searchKey = (String) data.get("searchField");
		Usuario userLogged =  (Usuario) data.get("userLogged");
		if(searchKey == null || searchKey.isEmpty() || !(searchKey.indexOf("%") != 0) || !(searchKey.indexOf("%") != searchKey.length() - 1)){
			return ret;
		}
		String sql = "FROM Usuario WHERE (name like '" + searchKey + "%' OR email like '" + searchKey + "%' OR cidade like '" + searchKey + "%') "
					+ "AND id <> " + userLogged.getId() + " AND active = true ORDER BY name";
		data.put("sql", sql);
		return super.searchByHQL();
	}

}