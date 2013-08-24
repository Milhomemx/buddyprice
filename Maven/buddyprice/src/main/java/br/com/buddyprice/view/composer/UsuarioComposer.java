package br.com.buddyprice.view.composer;

import java.io.File;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Image;

import br.com.buddyprice.control.UsuarioController;
import br.com.buddyprice.model.Usuario;
import br.com.buddyprice.view.attachments.AttachmentMedia;
import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.control.util.Attachment;
import br.com.vexillum.model.UserBasic;
import br.com.vexillum.util.ReflectionUtils;
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

	public void registerUser() {
		treatReturn(getControl().registerUser());
//		redirectToDash();
	}

	public void loginUser() {
		// treatReturn(getControl().loginUser());
		redirectToDash();

	}

	@SuppressWarnings("rawtypes")
	public static void showImageProfile(Image comp) {
		Attachment att = new AttachmentMedia();
		try {
			File image = att.getAttachment("avatar", getUserInSession());
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

	public static void redirectToUser(Long id) {
		Executions.sendRedirect("../user/edit.zul?id=" + id);
	}

	public static void redirectToDash() {
		Executions.sendRedirect("../dashboard/index.zul?id="
				+ getUserInSession().getId());
	}

	public static void redirectToUserSession() {
		redirectToUser(getUserInSession().getId());
	}

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
	public static UserBasic getUserInSession() {
		Usuario a = new Usuario();
		a.setId(1l);
				return (UserBasic) a;
	}
	@Override
	public Usuario getEntityObject() {
		return new Usuario();
	}

}
