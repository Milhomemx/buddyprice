package br.com.buddyprice.view.composer;

import java.io.File;

import org.springframework.context.annotation.Scope;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Image;

import br.com.buddyprice.view.attachments.AttachmentMedia;
import br.com.buddyprice.view.validator.ImageValidator;
import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.control.util.Attachment;
import br.com.vexillum.model.UserBasic;
import br.com.vexillum.util.Return;
import br.com.vexillum.view.GenericComposer;

/**
 * @author Natan
 * Classe responsável pelo compositor da da barra lateral esquerda do DashBoard.
 * Exibe botões do menu e permite a edição da foto de perfil.
 */
@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings({ "serial", "rawtypes" })
public class LeftSidebarComposer extends
		GenericComposer {

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Image img = (Image) getComponentById("igmProfileUser");
		if(img != null)
			showImageProfile(img);
		  String page = Executions.getCurrent().getDesktop().getRequestPath();
          if (!page.equalsIgnoreCase("/pages/user/edit.zul")){
        	  getComponentById("photoButton").detach();
          }
        	  
		loadBinder();
	}
	
	/**
	 * @param event
	 * Permite que o usuário altere a foto de perfil.
	 */
	@SuppressWarnings({ "unchecked" })
	public void changeProfileImage(UploadEvent event) {
		Media media = event.getMedia();
		ImageValidator val = new ImageValidator(media);
		Return ret = val.upload();
		if (ret.isValid()) {
			Attachment att = new AttachmentMedia();
			att.uploadAttachment(media, "image_profile", getUserInSession());
			Executions.sendRedirect("");
		}
		treatReturn(ret);
	}
	
	/**
	 * @param comp
	 * Exibe a imagem de perfil do usuário.
	 */
	@SuppressWarnings({ "unchecked" })
	public void showImageProfile(Image comp) {
		Attachment att = new AttachmentMedia();
		try {
			UserBasic userInSession = getUserInSession();
			if(userInSession != null){
				File image = att.getAttachment("image_profile", userInSession);
				if (image != null) {
					comp.setContent(new AImage(image));
				}
			}
		} catch (Exception e) {
			new ExceptionManager(e).treatException();
		}
	}


	/**
	 * Direciona para o menu "Configurações".
	 */
	public static void redirectToConfig() {
		Executions.sendRedirect("/pages/configuration/");
	}

	/**
	 * Direciona para o menu "Usuário".
	 */
	public static void redirectToUser() {
		Executions.sendRedirect("/pages/user/edit.zul");
	}
	
	/**
	 * Direciona para o menu "Dashboard".
	 */
	public static void redirectToDash() {
		Executions.sendRedirect("/pages/dashboard/");
	}
	/**
	 * Direciona para o menu "Estabelecimento".
	 */
	public static void redirectToEstablishment() {
		Executions.sendRedirect("/pages/establishments/");
	}
	/**
	 * Direciona para o menu "Produtos".
	 */
	public static void redirectToProduct() {
		Executions.sendRedirect("/pages/products/");
	}	
	/**
	 * Direciona para o menu "Ofertas".
	 */
	public static void redirectToOffer() {
		Executions.sendRedirect("/pages/offers/");
	}	
	/**
	 * Direciona para o menu "Camaradas".
	 */	
	public static void redirectToFriend() {
		Executions.sendRedirect("/pages/friends/");
	}	

	

}
