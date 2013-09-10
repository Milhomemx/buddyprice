
package br.com.buddyprice.view.composer;

import java.io.File;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Image;

import br.com.buddyprice.control.UsuarioController;
import br.com.buddyprice.model.Usuario;
import br.com.buddyprice.view.attachments.AttachmentMedia;
import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.control.util.Attachment;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.com.vexillum.view.CRUDComposer;

@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class UsuarioComposer extends CRUDComposer<Usuario, UsuarioController> {

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		loadBinder();
	}
	String email2 = new String();

	String pass2 = new String();
	
	public String getPass2() {
		return pass2;
	}

	public void setPass2(String pass2) {
		this.pass2 = pass2;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	@Override
	public UsuarioController getControl() {
		return SpringFactory.getController("usuarioController",
				UsuarioController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}

	public Return registerUser() {
			if(getControl().registerUser().isValid())
				redirectToLogin();
		return getControl().registerUser();
		
	}

	public void loginUser() {
		// treatReturn(getControl().loginUser());
		redirectToDash();

	}

	@SuppressWarnings("rawtypes")
	public static void showImageProfile(Image comp) {
		Attachment att = new AttachmentMedia();
		try {
			File image = att.getAttachment("image_profile", getUserInSession());
			if (image != null) {
				comp.setContent(new AImage(image));
			}
		} catch (Exception e) {
			new ExceptionManager(e).treatException();
		}
	}

	public static boolean isAdministrator() {
		if (isLogged()) {
		}
		return true;
	}

	public static void redirectToEdit() {
		Executions.sendRedirect("../user/edit.zul");
	}
	
	public static void redirectToRegister() {
		Executions.sendRedirect("pages/user/");
	}

	
	public static void redirectToConfig() {
		Executions.sendRedirect("../configuration/");
	}

	public static void redirectToDash() {
		Executions.sendRedirect("../dashboard/index.zul");
	}

	public static void redirectToLogin() {
		Executions.sendRedirect("/pages/user/login.zul?sucess=true");
	}

	
//	public static void redirectToUserSession() {
//		redirectToEdit(getUserInSession().getId());
//	}

	public static Boolean haveIdOnRequest() {
		String id = Executions.getCurrent().getParameter("id");
		return ((id != null && Integer.parseInt(id) >= 0));
	}
	

	@SuppressWarnings("unchecked")
	public boolean initUserById(String id) {
		entity.setId(Long.parseLong(id));
		List<Usuario> list = (List<Usuario>) getControl().doAction(
				"searchByCriteria").getList();
		if (list != null && !list.isEmpty()) {
			entity = list.get(0);
			return true;
		}
		return false;
	}
	@Override
	public Usuario getEntityObject() {
		return new Usuario();
	}

}
