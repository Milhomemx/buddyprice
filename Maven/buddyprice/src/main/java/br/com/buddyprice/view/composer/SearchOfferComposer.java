
package br.com.buddyprice.view.composer;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;

import br.com.buddyprice.model.Oferta;
import br.com.vexillum.util.Return;

/**
 * @author Natan
 * Classe responsável pelo compositor da abstração Oferta. Busca as ofertas conforme palavras-chave.
 */
@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class SearchOfferComposer extends ViewOfferComposer {

	
	private Grid resultList;

	private String searchField;

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		loadBinder();
	}
	
	/**
	 * @return
	 * Busca a(s) oferta(s) conforme palavra chave informada. Monta e exibe uma lista em ordem alfabética.
	 */
	@SuppressWarnings("unchecked")
	public Return searchOffers() {
		Return ret = getControl().doAction("searchOffers");
		if (ret.getList() != null && !ret.getList().isEmpty()) {
			setListEntity((List<Oferta>) ret.getList());
			loadBinder();
			resultList.setVisible(true);
		} else {
			resultList.setVisible(false);
		}

		return ret;
	}
	
	/**
	 * @param oferta
	 * Redireciona para a tela de visualização da oferta.
	 */
	public void redirectToView(Oferta oferta) {

		session.setAttribute("oferta", oferta);
		Executions.sendRedirect("view.zul");
	}

	/**
	 * @param oferta
	 * Redireciona para a tela de alteração da oferta.
	 */
	public void redirectToUpdate(Oferta oferta) {
		session.setAttribute("oferta", oferta);
		Executions.sendRedirect("include.zul");

	}


}
