package br.com.buddyprice.view.composer;

import org.zkoss.zk.ui.Component;

import br.com.vexillum.control.GenericControl;
import br.com.vexillum.model.ICommonEntity;
import br.com.vexillum.vexsocial.view.composer.SocialComposer;

/**
 * @author Natan
 *
 * @param <E>
 * @param <G>
 * Classe que faz a integração do GenericControl de SocialComposer (Vexillium).
 */
@SuppressWarnings("serial")
public abstract class BaseComposerSocial<E extends ICommonEntity, G extends GenericControl<E>> extends SocialComposer<E, G> {

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		setUserLogged(getUserInSession());
		if(haveIdOnRequest()){
			update = true;
			disableComponentsNoUpdatables(comp);
		}
	}


	
}
