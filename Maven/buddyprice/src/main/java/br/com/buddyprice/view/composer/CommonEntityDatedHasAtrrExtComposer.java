package br.com.buddyprice.view.composer;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import br.com.buddyprice.control.AttributeExtraController;
import br.com.buddyprice.control.TypeAttributeExtraController;
import br.com.buddyprice.model.AtributoExtra;
import br.com.buddyprice.model.CommonEntityDated;
import br.com.buddyprice.model.Estabelecimento;
import br.com.buddyprice.model.Produto;
import br.com.buddyprice.model.TipoAtributoExtra;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.model.ICommonEntity;
import br.com.vexillum.util.HibernateUtils;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;

@SuppressWarnings("serial")
public abstract class CommonEntityDatedHasAtrrExtComposer<E extends ICommonEntity, G extends GenericControl<E>>
		extends BaseComposer<E, G> {

	private List<TipoAtributoExtra> listTipoAtributoExtra;

	private TipoAtributoExtra tipoAtributoSelected;

	private AtributoExtra atributoExtra;

	private CommonEntityDatedHasAtrrExtComposer<E, G> parentComposer;

	public CommonEntityDatedHasAtrrExtComposer<E, G> getParentComposer() {
		return parentComposer;
	}

	public void setParentComposer(
			CommonEntityDatedHasAtrrExtComposer<E, G> parentComposer) {
		this.parentComposer = parentComposer;
	}

	public AtributoExtra getAtributoExtra() {
		return atributoExtra;
	}

	public void setAtributoExtra(AtributoExtra atributoExtra) {
		this.atributoExtra = atributoExtra;
	}

	public TipoAtributoExtra getTipoAtributoSelected() {
		return tipoAtributoSelected;
	}

	public void setTipoAtributoSelected(TipoAtributoExtra tipoAtributoSelected) {
		this.tipoAtributoSelected = tipoAtributoSelected;
	}

	public List<TipoAtributoExtra> getListTipoAtributoExtra() {
		return listTipoAtributoExtra;
	}

	public void setListTipoAtributoExtra(
			List<TipoAtributoExtra> listTipoAtributoExtra) {
		this.listTipoAtributoExtra = listTipoAtributoExtra;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		initVariaveisLocais();
		loadBinder();
	}
	
	@SuppressWarnings("unchecked")
	protected void initListAtributosExtras() {
		List<AtributoExtra> atributos = null;
		if(entity instanceof Estabelecimento){
			atributos = ((Estabelecimento)entity).getAtributoExtras();
			((Estabelecimento)entity).setAtributoExtras(HibernateUtils.transaformBagInList(atributos));
		}
		if(entity instanceof Produto){
			atributos = ((Produto)entity).getAtributoExtras();
			((Produto)entity).setAtributoExtras(HibernateUtils.transaformBagInList(atributos));
		}
	}
	
	protected void resetListAtributoExtra(){
		List<AtributoExtra> atributos = getControlAttributeExtra().getAtributosByEntity((CommonEntityDated) entity);
		if(entity instanceof Estabelecimento){
			((Estabelecimento)entity).setAtributoExtras(atributos);
		}
		if(entity instanceof Produto){
			((Produto)entity).setAtributoExtras(atributos);
		}
	}

	public TypeAttributeExtraController getControlTypeAttributeExtraController() {
		return SpringFactory.getController("typeAttributeExtraController",
				TypeAttributeExtraController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}

	private void initVariaveisLocais() {
		if (tipoAtributoSelected == null) {
			tipoAtributoSelected = new TipoAtributoExtra();
		}
		if (atributoExtra == null) {
			atributoExtra = new AtributoExtra();
		}
		initiListaTipoAtributoExtra();
	}

	@SuppressWarnings("unchecked")
	protected void initiListaTipoAtributoExtra() {
		setListTipoAtributoExtra((List<TipoAtributoExtra>) getControlTypeAttributeExtraController()
				.listAll().getList());
	}

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

	public AttributeExtraController getControlAttributeExtra() {
		return SpringFactory.getController("attributeExtraController",
				AttributeExtraController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}

	public void addNovoElemento() {

	}
	
	public void editarElemento(){
		
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
	}
	
	private void getSelectedEntityFromListbox() {
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

	public void removerElemento() {

	}

	public void novoTipoAtributo() {
		callModalWindow("/pages/forms/modalTipoatributo.zul");
	}
}