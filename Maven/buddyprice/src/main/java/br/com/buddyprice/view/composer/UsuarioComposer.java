package br.com.buddyprice.view.composer;

import java.io.File;

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
import br.com.vexillum.util.SpringFactory;
import br.com.vexillum.view.CRUDComposer;


@SuppressWarnings("serial")
public class UsuarioComposer extends CRUDComposer<Usuario, UsuarioController>{

	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		loadBinder();
	}
	
	@Override
	 public UsuarioController getControl() {
	 return SpringFactory.getController("usuarioController", UsuarioController.class, ReflectionUtils.prepareDataForPersistence(this));
	 }

	public void registerUser()  {
		treatReturn(getControl().registerUser());
		redirectToUserSession();
	}
		public void loginUser() {
//			treatReturn(getControl().loginUser());
			redirectToUserSession();

			
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
	
		public static void redirectToUser(Long id) {
			Executions.sendRedirect("../dashboard/index.zul?id=" + id);
		}

		public static void redirectToUserSession() {
			redirectToUser(getUserInSession().getId());
		}
	
	@Override
	 public Usuario getEntityObject() {
	 return new Usuario();
	 }

}
