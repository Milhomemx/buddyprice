package br.com.buddyprice.view.composer;

import java.io.File;

import org.springframework.context.annotation.Scope;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Image;

import br.com.buddyprice.control.EstablishmentController;
import br.com.buddyprice.model.Estabelecimento;
import br.com.buddyprice.view.attachments.AttachmentMedia;
import br.com.buddyprice.view.attachments.AttachmentMediaEstablishment;
import br.com.buddyprice.view.validator.ImageValidator;
import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.control.util.Attachment;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.com.vexillum.view.CRUDComposer;

/**
 * @author Natan
 * Classe respons�vel pelo compositor da abstra��o Estabelecimento. Cadastra e altera os daddos relacionados ao mesmo.
 */
@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class EstablishmentComposer extends
		CRUDComposer<Estabelecimento, EstablishmentController> {

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if (session.getAttribute("estabelecimento") != null) {
			setEntity((Estabelecimento) session.getAttribute("estabelecimento"));
			session.setAttribute("estabelecimento", null);
			showAlterImageEstablishment();
		}
		
		loadBinder();
	}

	@Override
	public EstablishmentController getControl() {
		return SpringFactory.getController("establishmentController",
				EstablishmentController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}



	/**
	 * @param event
	 * Altera a imagem do estabelecimento.
	 */
	public void changeEstablishmentImage(UploadEvent event) {
		Media media = event.getMedia();
		ImageValidator val = new ImageValidator(media);
		Return ret = val.upload();
		if (ret.isValid()) {
			AttachmentMediaEstablishment att = new AttachmentMediaEstablishment();
			att.uploadAttachment(media, "image_establishment", entity);
			//Executions.sendRedirect("");
		}
		treatReturn(ret);
	}

	/**
	 * Deleta foto do estabelecimento.
	 */
	@SuppressWarnings("unchecked")
	public void deleteProfileImage() {
		@SuppressWarnings("rawtypes")
		Attachment att = new AttachmentMedia();
		Return ret = new Return(true);
		if (ret.isValid()) {
			att.deleteAttachment("image_establishment", entity);
			Executions.sendRedirect("");
		}
		treatReturn(ret);
	}

	/**
	 * Direciona para a tela de edi��o do estabelecimento
	 */
	public void editEstablishment() {
		entity = (Estabelecimento) session.getAttribute("estabelecimento");
		callModalWindow("/pages/forms/modalEstablishment.zul");

	}

	/**
	 * Redireciona a tela.
	 */
	public static void redirectToBack() {
		Executions.sendRedirect("../establishment/");
	}
	
	/**
	 * @param comp
	 * @param entity
	 * Exibe a imagem do estabelecimento.
	 */
	public static void showImageEstablishment(Image comp, Estabelecimento entity) {
		AttachmentMediaEstablishment att = new AttachmentMediaEstablishment();
		if(entity != null){
			try {
				File image = att.getAttachment("image_establishment", entity);
				if (image != null) {
					comp.setContent(new AImage(image));
				}
			} catch (Exception e) {
				new ExceptionManager(e).treatException();
			}
		}
	}
	
	/**
	 * Exibe bot�o para altera��o da imagem.
	 */
	private void showAlterImageEstablishment(){
		Component foto = getComponentById("foto");
		foto.setVisible(true);
	}
	

	@Override
	public Estabelecimento getEntityObject() {
		return new Estabelecimento();
	}

}
