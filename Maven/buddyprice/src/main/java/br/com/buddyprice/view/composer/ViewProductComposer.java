package br.com.buddyprice.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;

import br.com.buddyprice.model.Produto;

/**
 * @author Natan
 * Exibe a "consulta detalhada" da abstração Produto. 
 */
@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class ViewProductComposer extends ProductComposer {

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if(session.getAttribute("produto")!=null)
			setEntity((Produto) session.getAttribute("produto"));
			session.setAttribute("produto", null);
		loadBinder();
	}

}
