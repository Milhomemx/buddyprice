package br.com.buddyprice.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;

import br.com.buddyprice.model.Estabelecimento;


/**
 * @author Natan
 * Exibe a "consulta detalhada" da abstração Estabelecimento. 
 */
@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class ViewEstablishmentComposer extends EstablishmentComposer {

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if(session.getAttribute("estabelecimento")!=null)
			setEntity((Estabelecimento) session.getAttribute("estabelecimento"));
			session.setAttribute("estabelecimento", null);
		loadBinder();
	}

}
