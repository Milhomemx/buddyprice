package br.com.buddyprice.view.composer;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;

import br.com.buddyprice.model.Estabelecimento;
import br.com.vexillum.util.Return;

/**
 * @author Natan
 * Classe responsável pelo compositor da abstração Estabelecimento. Busca os estabelecimentos conforme palavras-chave.
 */
@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class SearchEstablishmentComposer extends ViewEstablishmentComposer {

	private Grid resultList;

	private String searchField;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		loadBinder();
	}

	/**
	 * @return
	 * Busca o(s) estabelecimento(s) conforme palavra chave informada. Monta e exibe uma lista em ordem alfabética.
	 */
	@SuppressWarnings("unchecked")
	public Return searchEstablishments() {
		Return ret = getControl().doAction("searchEstablishments");
		if (ret.getList() != null && !ret.getList().isEmpty()) {
			setListEntity((List<Estabelecimento>) ret.getList());
			loadBinder();
			resultList.setVisible(true);
		} else {
			resultList.setVisible(false);
		}

		return ret;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	/**
	 * @param estabelecimento
	 * Redireciona para a tela de visualização.
	 */
	public void redirectToView(Estabelecimento estabelecimento) {

		session.setAttribute("estabelecimento", estabelecimento);
		Executions.sendRedirect("view.zul");
	}

	/**
	 * @param estabelecimento
	 * Redireciona para a tela de edição.
	 */
	public void redirectToUpdate(Estabelecimento estabelecimento) {
		session.setAttribute("estabelecimento", estabelecimento);
		Executions.sendRedirect("include.zul");

	}

}
