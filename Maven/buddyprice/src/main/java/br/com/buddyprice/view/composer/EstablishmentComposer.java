package br.com.buddyprice.view.composer;

import java.io.File;
import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlBasedComponent;
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

/**
 * @author Natan
 * Classe responsável pelo compositor da abstração Estabelecimento. Cadastra e altera os daddos relacionados ao mesmo.
 */
@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class EstablishmentComposer extends
		CommonEntityDatedHasAtrrExtComposer<Estabelecimento, EstablishmentController> {

	private Media fotoEstabelecimento;

	public Media getFotoEstabelecimento() {
		return fotoEstabelecimento;
	}

	public void setFotoEstabelecimento(Media fotoEstabelecimento) {
		this.fotoEstabelecimento = fotoEstabelecimento;
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if (session.getAttribute("estabelecimento") != null) {
			setEntity((Estabelecimento) session.getAttribute("estabelecimento"));
			session.setAttribute("estabelecimento", null);
			initListAtributosExtras();
			showAlterImageEstablishment();
		} else if(arg.containsKey("nomeEstabelecimento")) {
			getEntity().setNome((String) arg.get("nomeEstabelecimento"));
			((HtmlBasedComponent) getComponentById("fldEndereco")).focus();
		} else {
			showCreateImageEstablishment();
		}
		loadBinder();
	}

	private void showCreateImageEstablishment() {
		Component foto = getComponentById("foto_create");
		if (foto != null)
			foto.setVisible(true);
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
		if (media != null) {
			Return uploadImage = uploadImage(media);
			if (uploadImage.isValid()) {
				Component comp = getComponentById("imagePanel");
				showImageEstablishment((Image) comp, entity);
				treatReturn(uploadImage);
			}
		}
	}

	private Return uploadImage(Media media) {
		ImageValidator val = new ImageValidator(media);
		Return ret = val.upload();
		if (ret.isValid()) {
			AttachmentMediaEstablishment att = new AttachmentMediaEstablishment();
			att.uploadAttachment(media, "image_establishment", entity);
			// Executions.sendRedirect("");
		}
		return ret;
	}

	public void uploadEstablishmentImage(UploadEvent event) throws IOException {
		Media media = event.getMedia();
		ImageValidator val = new ImageValidator(media);
		Return ret = val.upload();
		if (ret.isValid()) {
			setFotoEstabelecimento(media);
			Component image = getComponentById("imagePanel");
			if (image != null) {
				((Image) image).setContent((org.zkoss.image.Image) media);
			}
		} else {
			treatReturn(ret);
		}
	}

	@Override
	public Return saveEntity() {
		Estabelecimento estabelecimento = entity;
		Return ret = super.saveEntity();
		if(ret.isValid() && getParentComposer() instanceof OfferComposer){
			estabelecimento = (Estabelecimento) getControl().getEstablishmentId(ret.getSerializable()).getList().get(0);
			if(getComponentById("modalEstabelecimento") != null){
				getComponentById("modalEstabelecimento").detach();
				((OfferComposer)getParentComposer()).getEntity().setEstabelecimento(estabelecimento);
				getParentComposer().loadBinder();
			} else {
				setEntity(estabelecimento);
				if (ret.isValid() && fotoEstabelecimento != null)
					ret.concat(uploadImage(getFotoEstabelecimento()));
			}
		}
		return ret;
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
	 * Direciona para a tela de edição do estabelecimento
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
		if (entity != null) {
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
	 * Exibe botão para alteração da imagem.
	 */
	private void showAlterImageEstablishment() {
		Component foto = getComponentById("foto");
		foto.setVisible(true);
	}

	@Override
	public Estabelecimento getEntityObject() {
		return new Estabelecimento();
	}
	
}
