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
