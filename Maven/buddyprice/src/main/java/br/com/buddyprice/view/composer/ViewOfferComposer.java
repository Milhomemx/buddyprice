package br.com.buddyprice.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;

import br.com.buddyprice.model.Oferta;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;

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
	

	public void publishRouteOnFacebook(){
		postStatusMessageFacebook(getPostMessage());
		showMessagePublish();
	}
	

	public void publishRouteOnTwitter(){
		updateStatusTwitter(getPostMessage());
		showMessagePublish();
	}
	

	private void showMessagePublish(){
		Return ret = new Return(true);
		ret.addMessage(new Message(null, "Publicado com sucesso!"));
		treatReturn(ret);
	}
	

	private String getPostMessage(){
		Oferta oferta = getEntity();
		String post = oferta.getUsuario() + " encontrou uma oferta!\n\n"+oferta.getProduto() + " por apenas " + oferta.getValor();
		return post;
	}
	

}
