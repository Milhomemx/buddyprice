package br.com.buddyprice.view.composer;

import java.io.File;

import org.springframework.context.annotation.Scope;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Image;

import br.com.buddyprice.view.attachments.AttachmentMedia;
import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.control.util.Attachment;
import br.com.vexillum.model.UserBasic;
import br.com.vexillum.view.GenericComposer;

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
		loadBinder();
	}
	
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


	public static void redirectToConfig() {
		Executions.sendRedirect("/pages/configuration/");
	}

	
	public static void redirectToUser() {
		Executions.sendRedirect("/pages/user/edit.zul");
	}
	
	public static void redirectToDash() {
		Executions.sendRedirect("/pages/dashboard/");
	}

	public static void redirectToEstablishment() {
		Executions.sendRedirect("/pages/establishments/");
	}
	
	public static void redirectToProduct() {
		Executions.sendRedirect("/pages/products/");
	}	
	
	public static void redirectToOffer() {
		Executions.sendRedirect("/pages/offers/");
	}	
		
	public static void redirectToFriend() {
		Executions.sendRedirect("/pages/friends/");
	}	

	

}
