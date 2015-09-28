package br.com.buddyprice.view.composer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Row;

import br.com.vexillum.control.FriendshipController;
import br.com.vexillum.model.Friendship;
import br.com.vexillum.util.HibernateUtils;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.com.buddyprice.model.Usuario;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class FriendshipComposer extends BaseComposer<Friendship, FriendshipController> {

	/**
	 * Usuário que representa o dono da amizade.
	 */
	private Usuario owner;
	
	/**
	 * Usuário que representa um amigo.
	 */
	private Usuario friend;
	
	/**
	 * Lista de requisições de amizades pendentes (Foram feitas ao usuário).
	 */
	private List<Friendship> pendents;
	
	/**
	 * Lista de requisições de amizades em aberto (O usuário requisitou).
	 */
	private List<Friendship> openRequests;
	
	/**
	 * Lista de rusuários que são amigos.
	 */
	private List<Usuario> UsuarioFriends;
	
	/**
	 * Chave de busca para a pesquisa de amigos.
	 */
	private String searchKey;
	
	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public Usuario getOwner() {
		return owner;
	}

	public void setOwner(Usuario owner) {
		this.owner = owner;
	}

	public Usuario getFriend() {
		return friend;
	}

	public void setFriend(Usuario friend) {
		this.friend = friend;
	}
	
	public List<Friendship> getPendents() {
		return pendents;
	}

	public void setPendents(List<Friendship> pendents) {
		this.pendents = pendents;
	}

	public List<Friendship> getOpenRequests() {
		return openRequests;
	}

	public void setOpenRequests(List<Friendship> openRequests) {
		this.openRequests = openRequests;
	}
	
	public List<Usuario> getUsuarioFriends() {
		return UsuarioFriends;
	}

	public void setUsuarioFriends(List<Usuario> UsuarioFriends) {
		this.UsuarioFriends = UsuarioFriends;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		initLists();
		loadBinder();
	}
	
	/**
	 * Inicializa a lista de amigos, chamando a ção correspondete no controlador.
	 * @see FriendsController#searchAllFriends();
	 */
	protected void initLists() {
		setListEntity(getAllFriends());
		initUsuarioFriends();
		HibernateUtils.initialize(getListEntity());
	}
	
	private void initUsuarioFriends() {
		setUsuarioFriends(new ArrayList<Usuario>());
		if(getListEntity()!= null){
			for (Friendship friendship : getListEntity()) {
				if(friendship.getOwner().equals(getUserInSession())){
					getUsuarioFriends().add((Usuario) HibernateUtils.materializeProxy(friendship.getFriend()));
				} else {
					getUsuarioFriends().add((Usuario) HibernateUtils.materializeProxy(friendship.getOwner()));
				}
			}
		}
	}

	/**
	 * Deleta uma amizade.
	 * @return {@link Return}
	 */
	public Return deleteFriendship(){
		return getControl().doAction("delete");
	}
	
	/**
	 * Aceita uma requisição de amizade, chamando a ação correspondente no controlador.
	 * @return
	 * @see FriendshipController#activeFriend();
	 */
	public Return acceptFriendship(){
		return getControl().doAction("activeFriend");
	}
	
	/**
	 * Pega a lista de todos os amigos de um usuário.
	 * @return Lista de amigos.
	 */
	@SuppressWarnings("unchecked")
	protected List<Friendship> getAllFriends(){
		FriendshipController controller = getControl();
		controller.getData().put("user", getUserInSession());
		return (List<Friendship>) controller.doAction("searchAllFriends").getList();
	}
	
	/**
	 * Pega a lista de todas as requisições de amizade pendentes.
	 * @return Lista de requisições pendentes.
	 */
	@SuppressWarnings("unchecked")
	protected List<Friendship> getListPendents(){
		FriendshipController controller = getControl();
		controller.getData().put("user", getUserInSession());
		return (List<Friendship>) controller.doAction("searchPendentRequests").getList();
	}
	
	/**
	 * Pega a lista de todas as requisições em aberto.
	 * @return Lista de requisições em aberto.
	 */
	@SuppressWarnings("unchecked")
	protected List<Friendship> getListOpenRequests(){
		FriendshipController controller = getControl();
		controller.getData().put("user", getUserInSession());
		return (List<Friendship>) controller.doAction("searchFriendRequests").getList();
	}
	
	/**
	 * Recusa uma amizade.
	 * @param comp Componente onde a {@link Friendship} está.
	 */
	public void refuseFriendship(Component comp){
		Row row = (Row) super.getParent(comp, "row");
		Friendship fri = null;
		if(row.getValue() instanceof Friendship){
			fri = row.getValue();
		} else {
			Usuario user = row.getValue();
			for (Friendship friendship : getListEntity()) {
				if(friendship.getFriend().equals(user) || friendship.getOwner().equals(user)){
					fri = friendship;
					break;
				}
			}
		}
		
		setEntity(fri);

		showRefuseFriendshipConfirmation(fri);
	}
	
	/**
	 * Exibe mensagem de confirmação no momento de recusar/deletar uma amizade.
	 * @param fri {@link Friendship}.
	 */
	@SuppressWarnings("unchecked")
	private void showRefuseFriendshipConfirmation(Friendship fri){
		showWindowConfirmation(getProperty("refusefriendship_message"), getRefuseFriendshipEventListener(fri));
	}
	
	/**
	 * Monsta evento que dispara ação de recusar/deletar uma amizade no controlador.
	 * @param fri {@link Friendship}
	 * @return Evento a ser disparado.
	 */
	@SuppressWarnings("rawtypes")
	private EventListener getRefuseFriendshipEventListener(final Friendship fri){
		EventListener evt = new EventListener() {
			public void onEvent(Event evt) throws InterruptedException {
				if (evt.getName().equals("onOK")) {
					if(doAction("deleteFriendship")){
						excludeFrendshipFromLists(fri);
						initUsuarioFriends();
						loadBinder();
					}
				}
			}
		};
		return evt;
	}
	
	/**
	 * Remove uma amizade das lista já carregadas.
	 * @param fri
	 */
	private void excludeFrendshipFromLists(Friendship fri){
		if(getPendents() != null) getPendents().remove(fri);
		if(getOpenRequests() != null) getOpenRequests().remove(fri);
		if(getListEntity() != null) getListEntity().remove(fri);
	}
	
	/**
	 * Aceita uma amizade chamando a ação correspondente no controlador.
	 * @param comp Componente onde a {@link Friendship} está.
	 * @see FriendsController#activeFriend();
	 */
	public void acceptFriendship(Component comp){
		Row row = (Row) super.getParent(comp, "row");
		Friendship fri = row.getValue();
		setEntity(fri);
		
		if(doAction("acceptFriendship")){
			getOpenRequests().remove(fri);
			loadBinder();
		}
	}
	
	/**
	 * Pesquisa os amigos de um usuário baseados na {@link FriendshipComposer#searchKey}.
	 * @return {@link Return}
	 */
	@SuppressWarnings("unchecked")
	public Return searchFriends(){
		FriendshipController controller = getControl();
		controller.getData().put("user", getUserInSession());
		
		Return ret = controller.doAction("searchFriends");
		if(ret.getList() != null && !ret.getList().isEmpty()){
			setListEntity((List<Friendship>) ret.getList());
			loadBinder();
		}
		return ret;
	}
	
	@Override
	public FriendshipController getControl() {
		return SpringFactory.getController("friendshipController", FriendshipController.class, ReflectionUtils.prepareDataForPersistence(this));
	}

	@Override
	public Friendship getEntityObject() {
		return new Friendship();
	}

}
