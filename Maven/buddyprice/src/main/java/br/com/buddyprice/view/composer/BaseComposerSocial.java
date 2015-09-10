package br.com.buddyprice.view.composer;

import org.zkoss.zk.ui.Component;

import br.com.vexillum.control.GenericControl;
import br.com.vexillum.model.ICommonEntity;
import br.com.vexillum.vexsocial.view.composer.SocialComposer;
import br.com.vexillum.view.CRUDComposer;

/**
 * @author Natan
 *
 * @param <E>
 * @param <G>
 * Classe que faz a integração do GenericControl de SocialComposer (Vexillium).
 */
@SuppressWarnings("serial")
public abstract class BaseComposerSocial<E extends ICommonEntity, G extends GenericControl<E>> extends SocialComposer<E, G> {

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
		setUserLogged(getUserInSession());
		setParentComposer((CRUDComposer<E, G>) arg.get("thisComposer"));
		if(haveIdOnRequest()){
			update = true;
			disableComponentsNoUpdatables(comp);
		}
	}


	
}
