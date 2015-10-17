package br.com.buddyprice.view.composer;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;

import br.com.buddyprice.model.Produto;
import br.com.vexillum.util.Return;

/**
 * @author Natan
 * Classe responsável pelo compositor do abstração Produto. Busca os estabelecimentos conforme palavras-chave.
 */
@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class SearchProductComposer extends ViewProductComposer {

	private Grid resultList;

	private String searchField;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		loadBinder();
	}

	/**
	 * @return
	 * Busca o(s) produto(s) conforme palavra chave informada. Monta e exibe uma lista em ordem alfabética.
	 */
	@SuppressWarnings("unchecked")
	public Return searchProducts() {
		Return ret = getControl().doAction("searchProducts");
		if (ret.getList() != null && !ret.getList().isEmpty()) {
			setListEntity((List<Produto>) ret.getList());
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
	 * @param produto
	 * Redireciona para a tela de visualização do produto.
	 */
	public void redirectToView(Produto produto) {

		session.setAttribute("produto", produto);
		Executions.sendRedirect("view.zul");
	}

	/**
	 * @param produto
	 * Redireciona para a tela de alteração do produto.
	 */
	public void redirectToUpdate(Produto produto) {
		session.setAttribute("produto", produto);
		Executions.sendRedirect("include.zul");

	}

	public void redirectToDelete(Produto produto){
		if(produto != null)
			setEntity(produto);
		showActionConfirmation("Deseja excluir este Estabelecimento?", "excluirProduto");
	}
	
	public Return excluirProduto(){
		Produto pro = entity;
		Return ret = getControl().doAction("delete");
		if(ret.isValid()){
			getListEntity().remove(pro);
		}
		loadBinder();
		return ret;
	}
	
}
