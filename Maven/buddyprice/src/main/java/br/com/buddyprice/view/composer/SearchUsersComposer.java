package br.com.buddyprice.view.composer;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import br.com.buddyprice.model.Usuario;
import br.com.vexillum.util.Return;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class SearchUsersComposer extends UsuarioComposer {

	/**
	 * Lista de resultados.
	 */
	private Grid resultList;
//	private Listbox resultList;
	
	/**
	 * Mensagem que ser� exibida quando nenhuma rota for encontrada.
	 */
	private Label noResultMessage;
	
	/**
	 * Chave de pesquisa.
	 */
	private String searchField;
	
	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	/**
	 * Procura usu�rios baseado na chave de pesquisa, chamando a a��o correspondente no controlador.
	 * @return {@link Return}
	 * @see UserController#searchUsers()
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public Return searchUsers(){
		binder.saveAll();
		Return ret = getControl().doAction("searchUsers");
		if(ret.getList() != null && !ret.getList().isEmpty()){
			setListEntity((List<Usuario>) ret.getList());
			loadBinder();
			resultList.setVisible(true);
			noResultMessage.setVisible(false);
		} else {
			resultList.setVisible(false);
			noResultMessage.setVisible(true);
		}
		
		return ret;
	}
	
	/**
	 * Fazer a requisi��o de amizade a um usu�rio.
	 * @param comp Componente que cont�m o usu�rio.
	 */
	public void requestFriendship(Component comp) {
		Row row = (Row) super.getParent(comp, "row");
		Usuario user = row.getValue();
		setEntity(user);
		saveFriendship(getUserInSession(), (Usuario) getEntity());
	}
	
}
