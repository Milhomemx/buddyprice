package br.com.buddyprice.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;

import br.com.buddyprice.control.EstablishmentController;
import br.com.buddyprice.model.Estabelecimento;
import br.com.buddyprice.view.attachments.AttachmentMedia;
import br.com.buddyprice.view.attachments.AttachmentMediaEstablishment;
import br.com.buddyprice.view.validator.ImageValidator;
import br.com.vexillum.control.util.Attachment;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.com.vexillum.view.CRUDComposer;

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
			getComponentById("inserir").setVisible(true);
		}
		
		loadBinder();
	}

	@Override
	public EstablishmentController getControl() {
		return SpringFactory.getController("establishmentController",
				EstablishmentController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}

	@Override
	public Return saveEntity() {
		Estabelecimento estabelecimento = entity;
		Return ret = super.saveEntity();
		if (ret.isValid()) {
			ret.concat(getControl().getEstablishmentId(estabelecimento));
			if (ret.isValid() && !ret.getList().isEmpty())
				estabelecimento = (Estabelecimento) ret.getList().get(0);
			session.setAttribute("estabelecimento", estabelecimento);
			Executions.sendRedirect("view.zul?sucess=true");

		}

		return ret;
	}

	public void changeEstablishmentImage(UploadEvent event) {
		Media media = event.getMedia();
		ImageValidator val = new ImageValidator(media);
		Return ret = val.upload();
		if (ret.isValid()) {
			AttachmentMediaEstablishment att = new AttachmentMediaEstablishment();
			att.uploadAttachment(media, "image_establishment", entity);
			Executions.sendRedirect("");
		}
		treatReturn(ret);
	}

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

	public void editEstablishment() {
		entity = (Estabelecimento) session.getAttribute("estabelecimento");
		callModalWindow("/pages/forms/modalEstablishment.zul");

	}

	public static void redirectToBack() {
		Executions.sendRedirect("../establishment/");
	}

	@Override
	public Estabelecimento getEntityObject() {
		return new Estabelecimento();
	}

}
