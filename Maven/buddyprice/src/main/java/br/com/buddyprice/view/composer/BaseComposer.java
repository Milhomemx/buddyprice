package br.com.buddyprice.view.composer;

import org.zkoss.zk.ui.Component;

import br.com.vexillum.control.GenericControl;
import br.com.vexillum.model.ICommonEntity;
import br.com.vexillum.view.CRUDComposer;

@SuppressWarnings("serial")
public abstract class BaseComposer<E extends ICommonEntity, G extends GenericControl<E>>
		extends CRUDComposer<E, G> {

	private CRUDComposer<E, G> parentComposer;

	public CRUDComposer<E, G> getParentComposer() {
		return parentComposer;
	}

	public void setParentComposer(CRUDComposer<E, G> parentComposer) {
		this.parentComposer = parentComposer;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		setParentComposer((CRUDComposer<E, G>) arg.get("thisComposer"));
	}
	
	protected void loadBinderParentComposer() {
		getParentComposer().loadBinder();
	}

}
