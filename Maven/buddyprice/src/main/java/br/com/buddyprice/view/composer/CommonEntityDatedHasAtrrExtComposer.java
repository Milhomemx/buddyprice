package br.com.buddyprice.view.composer;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Image;

import br.com.buddyprice.control.BaseController;
import br.com.buddyprice.model.AtributoExtraValor;
import br.com.vexillum.model.ICommonEntity;
import br.com.vexillum.util.Return;

@SuppressWarnings("serial")
public abstract class CommonEntityDatedHasAtrrExtComposer<E extends ICommonEntity, G extends BaseController<E>>
		extends BaseComposer<E, G> {

	private List<AtributoExtraValor> atributosExtras;
	
	private AtributoExtraValor atributoExtra = new AtributoExtraValor();
	
	private AtributoExtraValor selectedAtributoExtra = new AtributoExtraValor();

	public List<AtributoExtraValor> getAtributosExtras() {
		return atributosExtras;
	}

	public void setAtributosExtras(List<AtributoExtraValor> atributosExtras) {
		this.atributosExtras = atributosExtras;
	}

	public AtributoExtraValor getAtributoExtra() {
		return atributoExtra;
	}

	public void setAtributoExtra(AtributoExtraValor atributoExtra) {
		this.atributoExtra = atributoExtra;
	}

	public AtributoExtraValor getSelectedAtributoExtra() {
		return selectedAtributoExtra;
	}

	public void setSelectedAtributoExtra(AtributoExtraValor selectedAtributoExtra) {
		this.selectedAtributoExtra = selectedAtributoExtra;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		initListAtributosExtras();
	}
	
	protected void initListAtributosExtras() {
		setAtributosExtras(getControl().getAtributosExtras());
	}
	
//	protected void resetListAtributoExtra(){
//		List<AtributoExtra> atributos = getControlAttributeExtra().getAtributosByEntity((CommonEntityDated) entity);
//		if(entity instanceof Estabelecimento){
//			((Estabelecimento)entity).setAtributoExtras(atributos);
//		}
//		if(entity instanceof Produto){
//			((Produto)entity).setAtributoExtras(atributos);
//		}
//	}

	public void showHidePanelAtributoExtra() {
		Image image = (Image) getComponentById("imageAtrrExtra");
		Component component = getComponentById("panelAtrrExtra");
		if (component != null) {
			if (component.isVisible()) {
				component.setVisible(false);
				image.setSrc("/images/icon/down.png");
			} else {
				component.setVisible(true);
				image.setSrc("/images/icon/up.png");
			}
		}
	}
	
	public Return adicionarAtributoExtra(){
		Return ret;
		ret = getControl().addAtributoExtra();
//		if(!ret.isValid()){
//			treatReturn(ret);
//			ret.setValid(false);
//		} else {
//			
//		}
		if(ret.isValid()){
			resetAtributoExtra();
		}
		loadBinder();
		return ret;
	}

	protected void resetAtributoExtra() {
		setAtributoExtra(new AtributoExtraValor());
	}
	
	public void editarElemento(){
		callModalWindow("/teste");
	}

	public Return removerElemento() {
		Return ret;
		ret = getControl().removeAtributoExtra();
		loadBinder();
		return ret;
	}
	
	/*public AttributeExtraController getControlAttributeExtra() {
		return SpringFactory.getController("attributeExtraController",
				AttributeExtraController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}

	public void addNovoElemento() {

	}
	
	

	public void salvarElemento() {
		Return ret = new Return(true);
		Produto produto = null; Estabelecimento estabelecimento = null;
		if (entity.getId() != null) {
			if (entity instanceof Estabelecimento) {
				atributoExtra.setEstabelecimento((Estabelecimento) entity);
				estabelecimento = (Estabelecimento) entity;
			}
			if (entity instanceof Produto) {
				atributoExtra.setProduto((Produto) entity);
				produto = (Produto) entity;
			}
			ret.concat(getControlAttributeExtra().save(atributoExtra));
			if(produto != null){
				produto.setAtributoExtras(getControlAttributeExtra().getAtributosByEntity(produto));
			}
			if(estabelecimento != null){
				estabelecimento.setAtributoExtras(getControlAttributeExtra().getAtributosByEntity(estabelecimento));
			}
		} else {
			ret.setValid(false);
			ret.setMessages(new ArrayList<Message>());
			ret.getMessages()
					.add(new Message(null,
							"Salve o objeto antes de adicionar atributos extras!!"));
		}
		treatReturn(ret);
	}*/
	
	/*private void getSelectedEntityFromListbox() {
		Listbox listbox = (Listbox) getComponentById("atributoExtrasList");
		int index = 0;
		if (listbox != null) {
			Listitem selectedItem = listbox.getSelectedItem();
			if (selectedItem != null) {
				index = selectedItem.getIndex();
				AtributoExtra ant = (AtributoExtra) listbox.getModel().getElementAt(
						index);
				atributoExtra = ant;
			}
		}
	}

	public void novoTipoAtributo() {
		callModalWindow("/pages/forms/modalTipoatributo.zul");
	}*/
}