package br.com.buddyprice.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;

import br.com.buddyprice.control.TypeAttributeExtraController;
import br.com.buddyprice.model.TipoAtributoExtra;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;

@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class TypeAttributeExtraComposer extends
		BaseComposer<TipoAtributoExtra, TypeAttributeExtraController> {

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		loadBinder();
	}

	@Override
	public TypeAttributeExtraController getControl() {
		return SpringFactory.getController("typeAttributeExtraController",
				TypeAttributeExtraController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}
	
	@Override
	public TipoAtributoExtra getEntityObject() {
		return new TipoAtributoExtra();
	}
	
	@Override
	public Return saveEntity() {
		Return ret = super.saveEntity();
		reloadListaTipoAtributo();
		return ret;
	}

	private void reloadListaTipoAtributo() {
		BaseComposer<?, ?> thisParentComposer = getParentComposer();
		if(thisParentComposer != null){
			((CommonEntityDatedHasAtrrExtComposer<?, ?>)thisParentComposer).initiListaTipoAtributoExtra();
			loadBinderParentComposer();
			loadBinder();
		}
	}

}
