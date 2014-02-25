package br.com.buddyprice.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;

import br.com.buddyprice.model.Oferta;

@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class ViewOfferComposer extends OfferComposer {

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if(session.getAttribute("oferta")!=null)
			setEntity((Oferta) session.getAttribute("oferta"));
			session.setAttribute("oferta", null);
		loadBinder();
	}

}
