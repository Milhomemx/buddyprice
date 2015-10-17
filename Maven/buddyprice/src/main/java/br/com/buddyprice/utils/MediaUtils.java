package br.com.buddyprice.utils;

import java.io.File;

import org.zkoss.image.AImage;
import org.zkoss.zul.Image;

import br.com.buddyprice.model.Estabelecimento;
import br.com.buddyprice.model.Produto;
import br.com.buddyprice.model.Usuario;
import br.com.buddyprice.view.attachments.AttachmentMedia;
import br.com.buddyprice.view.attachments.AttachmentMediaEstablishment;
import br.com.buddyprice.view.attachments.AttachmentMediaProduct;
import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.control.util.Attachment;

public class MediaUtils {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setImageUser(Image comp, Usuario user) {
		Attachment att = new AttachmentMedia();
		try {
			File image = att.getAttachment("image_profile", user);
			if (image != null) {
				comp.setContent(new AImage(image));
			}
		} catch (Exception e) {
			new ExceptionManager(e).treatException();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setImageProduct(Image comp, Produto produto) {
		Attachment att = new AttachmentMediaProduct();
		try {
			File image = att.getAttachment("image_product", produto);
			if (image != null) {
				comp.setContent(new AImage(image));
			}
		} catch (Exception e) {
			new ExceptionManager(e).treatException();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setImageProduct(Image comp, Estabelecimento estabelecimento) {
		Attachment att = new AttachmentMediaEstablishment();
		try {
			File image = att.getAttachment("image_profile", estabelecimento);
			if (image != null) {
				comp.setContent(new AImage(image));
			}
		} catch (Exception e) {
			new ExceptionManager(e).treatException();
		}
	}
}
