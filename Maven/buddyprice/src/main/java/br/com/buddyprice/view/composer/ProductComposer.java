package br.com.buddyprice.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;

import br.com.buddyprice.control.ProductController;
import br.com.buddyprice.model.Produto;
import br.com.buddyprice.view.attachments.AttachmentMediaProduct;
import br.com.buddyprice.view.validator.ImageValidator;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.com.vexillum.view.CRUDComposer;

@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class ProductComposer extends
		CRUDComposer<Produto, ProductController> {

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if (session.getAttribute("produto") != null) {
			setEntity((Produto) session.getAttribute("produto"));
			session.setAttribute("produto", null);
		}
		
		loadBinder();
	}

	@Override
	public ProductController getControl() {
		return SpringFactory.getController("productController",
				ProductController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}

	@Override
	public Return saveEntity() {
		Produto produto = entity;
		Return ret = super.saveEntity();
		if (ret.isValid()) {
			ret.concat(getControl().getProductId(produto));
			if (ret.isValid() && !ret.getList().isEmpty())
				produto = (Produto) ret.getList().get(0);
			session.setAttribute("produto", produto);
			Executions.sendRedirect("view.zul?sucess=true");

		}

		return ret;
	}

	public void changeProductImage(UploadEvent event) {
		Media media = event.getMedia();
		ImageValidator val = new ImageValidator(media);
		Return ret = val.upload();
		if (ret.isValid()) {
			AttachmentMediaProduct att = new AttachmentMediaProduct();
			att.uploadAttachment(media, "image_product", entity);
			Executions.sendRedirect("");
		}
		treatReturn(ret);
	}


	public void editEstablishmentProduct() {
		entity = (Produto) session.getAttribute("produto");
		callModalWindow("/pages/forms/modalProduct.zul");

	}

	public static void redirectToBack() {
		Executions.sendRedirect("../product/");
	}
	
//	public static void showImageEstablishment(Image comp) {
//		AttachmentMediaEstablishment att = new AttachmentMediaEstablishment();
//		try {
//			File image = att.getAttachment("image_establishment", getEstablishmentInSession());
//			if (image != null) {
//				comp.setContent(new AImage(image));
//			}
//		} catch (Exception e) {
//			new ExceptionManager(e).treatException();
//		}
//	}

	@Override
	public Produto getEntityObject() {
		return new Produto();
	}

}