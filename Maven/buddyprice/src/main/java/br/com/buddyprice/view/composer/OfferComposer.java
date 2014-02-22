
package br.com.buddyprice.view.composer;

import java.io.File;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Image;

import br.com.buddyprice.control.EstablishmentController;
import br.com.buddyprice.control.OfferController;
import br.com.buddyprice.control.ProductController;
import br.com.buddyprice.model.Estabelecimento;
import br.com.buddyprice.model.Oferta;
import br.com.buddyprice.model.Produto;
import br.com.buddyprice.model.Usuario;
import br.com.buddyprice.view.attachments.AttachmentMediaProduct;
import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.com.vexillum.view.CRUDComposer;

@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class OfferComposer extends CRUDComposer<Oferta, OfferController> {

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		loadBinder();
	}
	
	
	@Override
	public OfferController getControl() {
		return SpringFactory.getController("offerController",
				OfferController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Produto> getProdutos() {
		return (List<Produto>) SpringFactory
				.getController("productController", ProductController.class, null)
				.doAction("listAll").getList();
	}

	@SuppressWarnings("unchecked")
	public List<Estabelecimento> getEstabelecimentos() {
		return (List<Estabelecimento>) SpringFactory
				.getController("establishmentController", EstablishmentController.class, null)
				.doAction("listAll").getList();
	}

	@Override
	public Return saveEntity() {
		entity.setUsuario((Usuario) getUserInSession());
		Return ret = super.saveEntity();
		if(ret.isValid() && getUpdate()){
			Executions.sendRedirect("/pages/offers/view.zul");
		}
		return ret; 
	}
	
	public static void showImageOffer(Image comp, Oferta entity) {
		AttachmentMediaProduct att = new AttachmentMediaProduct();
		if(entity != null){
			try {
				File image = att.getAttachment("image_product", entity.getProduto());
				if (image != null) {
					comp.setContent(new AImage(image));
				}
			} catch (Exception e) {
				new ExceptionManager(e).treatException();
			}
		}
	}
	
	@Override
	public Oferta getEntityObject() {
		return new Oferta();
	}

}
