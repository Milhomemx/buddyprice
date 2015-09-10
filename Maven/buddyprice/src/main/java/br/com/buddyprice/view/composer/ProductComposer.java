package br.com.buddyprice.view.composer;

import java.io.File;

import org.springframework.context.annotation.Scope;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Image;

import br.com.buddyprice.control.ProductController;
import br.com.buddyprice.model.Produto;
import br.com.buddyprice.view.attachments.AttachmentMediaProduct;
import br.com.buddyprice.view.validator.ImageValidator;
import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;

/**
 * @author Natan
 * Classe responsável pelo compositor da abstração Produto. Cadastra e altera os daddos relacionados ao mesmo.
 */
@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class ProductComposer extends
		CommonEntityDatedHasAtrrExtComposer<Produto, ProductController> {

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if (session.getAttribute("produto") != null) {
			setEntity((Produto) session.getAttribute("produto"));
			session.setAttribute("produto", null);
			initListAtributosExtras();
			showAlterProduct();		
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
			if(getComponentById("modalProduto") != null){
				getComponentById("modalProduto").detach();
				getParentComposer().loadBinder();
			} else {
				ret.concat(getControl().getProductId(produto));
				if (ret.isValid() && !ret.getList().isEmpty())
					produto = (Produto) ret.getList().get(0);
				session.setAttribute("produto", produto);
				Executions.sendRedirect("view.zul?sucess=true");
			}
		}

		return ret;
	}

	/**
	 * @param event
	 * Altera imagem do produto.
	 */
	public void changeProductImage(UploadEvent event) {
		Media media = event.getMedia();
		ImageValidator val = new ImageValidator(media);
		Return ret = val.upload();
		if (ret.isValid()) {
			AttachmentMediaProduct att = new AttachmentMediaProduct();
			att.uploadAttachment(media, "image_product", entity);
//			Executions.sendRedirect("");
		}
		treatReturn(ret);
	}


	/**
	 * Direciona para a tela de edição do produto.
	 */
	public void editEstablishmentProduct() {
		entity = (Produto) session.getAttribute("produto");
		callModalWindow("/pages/forms/modalProduct.zul");

	}

	/**
	 * Voltar para a tela anterior.
	 */
	public static void redirectToBack() {
		Executions.sendRedirect("../product/");
	}
	
	/**
	 * @param comp
	 * @param entity
	 * Exibe a imagem do produto.
	 */
	public static void showImageProduct(Image comp, Produto entity) {
		AttachmentMediaProduct att = new AttachmentMediaProduct();
		if(entity != null){
			try {
				File image = att.getAttachment("image_product", entity);
				if (image != null) {
					comp.setContent(new AImage(image));
				}
			} catch (Exception e) {
				new ExceptionManager(e).treatException();
			}
		}
	}
	
	/**
	 * Exibe o botão para a alteração da imagem.
	 */
	private void showAlterProduct(){
		Component foto = getComponentById("foto");
		foto.setVisible(true);
	}
	

	@Override
	public Produto getEntityObject() {
		return new Produto();
	}

}
