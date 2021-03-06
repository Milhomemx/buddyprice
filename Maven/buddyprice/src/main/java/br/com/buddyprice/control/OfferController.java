package br.com.buddyprice.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.Avaliacao;
import br.com.buddyprice.model.Comentario;
import br.com.buddyprice.model.Oferta;
import br.com.buddyprice.model.Usuario;
import br.com.buddyprice.model.enums.AvaliacaoComentario;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;

/**
 * @author Natan
 * Controlador da abstração Oferta. Gere todos os cenários relacionado a ação de ofertar.
 * Extende do controle genérico.
 */
@Service
@Scope("prototype")
public class OfferController extends GenericControl<Oferta> {
	public OfferController() {
		super(Oferta.class);
	}

	/**
	 * @return
	 * Insere uma oferta.
	 */
	public Return InsertOffert() {
		Return regReturn = new Return(true);
		regReturn.concat(doAction("save"));
		if (regReturn.isValid()) {
			regReturn.addMessage(new Message(null, "Oferta criada!"));

		}
		return regReturn;

	}

	@Override
	public Return save() {
		return super.save();
	}

	/**
	 * @return
	 * Busca por ofertas conforme palavra chave relacionada ao produto ou palavra chave relacionada à oferta.
	 */
	public Return searchOffers() {
		Return ret = new Return(true);
		String searchKey = (String) data.get("searchField");
		if ((searchKey == null || searchKey.isEmpty())
				|| !(searchKey.indexOf("%") != 0)
				|| !(searchKey.indexOf("%") != searchKey.length() - 1)) {
			return ret;
		}
		String sql = "From Oferta where id in (select o.id from Oferta o, Estabelecimento e, Produto p where o.estabelecimento = e.id and o.produto = p.id  and (upper(p.nome) like  upper('%"
				+ searchKey
				+ "%') or upper(e.nome) like upper('%"
				+ searchKey
				+ "%')))";

		data.put("sql", sql);
		return super.searchByHQL();
	}

	public Return update() {

		return super.update();
	}

	public Return nullit() {
		Return ret = new Return(false);

		return ret;
	}
	
	public Return evaluateOfferPositive(){
		return evaluateOfferPositive(getEntity());
	}
	
	public Return evaluateOfferNegative(){
		return evaluateOfferNegative(getEntity());
	}
	
	public Return evaluateOfferPositive(Oferta oferta){
		Avaliacao avaliacao = getEvaluateFromUser(oferta);
		if(avaliacao != null){
			return delete(avaliacao);
		}
		avaliacao = getAvaliacaoEntity(true, oferta);
		return save(avaliacao);
	}
	
	public Return evaluateOfferNegative(Oferta oferta){
		Avaliacao avaliacao = getEvaluateFromUser(oferta);
		if(avaliacao != null){
			return delete(avaliacao);
		}
		avaliacao = getAvaliacaoEntity(false, oferta);
		return save(avaliacao);
	}

	private Avaliacao getAvaliacaoEntity(Boolean valor, Oferta oferta) {
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setUsuario((Usuario) data.get("userLogged"));
		avaliacao.setOferta(oferta);
		avaliacao.setValor(valor);
		return avaliacao;
	}
	
	private Avaliacao getEvaluateFromUser(Oferta oferta) {
		Return ret = new Return(true);
		HashMap<String, Object> data = new HashMap<String, Object>();

		data.put("sql",	"FROM Avaliacao a WHERE a.oferta = '" + oferta.getId() + "' AND a.usuario = '" + ((Usuario)getData().get("userLogged")).getId() + "'");

		OfferController controller = SpringFactory.getController("offerController", OfferController.class, data);
		ret = controller.searchByHQL();
		if (!ret.getList().isEmpty()) {
			return (Avaliacao) ret.getList().iterator().next();
		}
		return null;
	}
	
	public List<Comentario> getCommentsFromOffer(){
		return getCommentsFromOffer(getEntity());
	}
	
	@SuppressWarnings("unchecked")
	public List<Comentario> getCommentsFromOffer(Oferta oferta){
		if(oferta == null || oferta.getId() == null) return new ArrayList<>();
		String sql = "FROM Comentario c WHERE c.oferta= '" + oferta.getId() + "'";
		getData().put("sql", sql);
		
		CommentController controller = SpringFactory.getController("commentController", CommentController.class, data);
		Return ret = controller.searchByHQL();
		if (ret.getList() == null) {
			return new ArrayList<>();
		}
		List<Comentario> list = (List<Comentario>) ret.getList();
		Collections.sort(list, new Comparator<Comentario>() {
			@Override
			public int compare(Comentario o1, Comentario o2) {
				return o2.getData().compareTo(o1.getData());
			}
		});
		return list;
	}
	
	public Return saveComentary(){
		Comentario coment = new Comentario();
		coment.setUsuario((Usuario) getData().get("userLogged"));
		coment.setOferta(getEntity()); 
		coment.setComentario((String) getData().get("fldComentario"));
		coment.setAvaliacao((AvaliacaoComentario) getData().get("fldAvaliacao"));
		
		return save(coment);
	}

	public Return editComentary(){
		Comentario coment = (Comentario) getData().get("selectedComentario");
		return update(coment);
	}
	
	public Return deleteComentary(){
		Comentario coment = (Comentario) getData().get("selectedComentario");
		return delete(coment);
	}
	
}