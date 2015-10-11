package br.com.buddyprice.control;

import java.util.ArrayList;
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
		Avaliacao avaliacao = getEvaluateFromUser();
		if(avaliacao != null){
			return delete(avaliacao);
		}
		avaliacao = getAvaliacaoEntity(true);
		return save(avaliacao);
	}
	
	public Return evaluateOfferNegative(){
		Avaliacao avaliacao = getEvaluateFromUser();
		if(avaliacao != null){
			return delete(avaliacao);
		}
		avaliacao = getAvaliacaoEntity(false);
		return save(avaliacao);
	}

	private Avaliacao getAvaliacaoEntity(Boolean valor) {
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setUsuario((Usuario) data.get("userLogged"));
		avaliacao.setOferta((Oferta) data.get("entity"));
		avaliacao.setValor(valor);
		return avaliacao;
	}
	
	private Avaliacao getEvaluateFromUser() {
		Return ret = new Return(true);
		HashMap<String, Object> data = new HashMap<String, Object>();

		data.put("sql",	"FROM Avaliacao a WHERE a.oferta = '" + getEntity().getId() + "' AND a.usuario = '" + ((Usuario)getData().get("userLogged")).getId() + "'");

		OfferController controller = SpringFactory.getController("offerController", OfferController.class, data);
		ret = controller.searchByHQL();
		if (!ret.getList().isEmpty()) {
			return (Avaliacao) ret.getList().iterator().next();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comentario> getCommentsFromOffer(){
		String sql = "FROM Comentario c WHERE c.oferta= '" + getEntity().getId() + "'";
		getData().put("sql", sql);
		
		CommentController controller = SpringFactory.getController("commentController", CommentController.class, data);
		Return ret = controller.searchByHQL();
		if (ret.getList() == null) {
			return new ArrayList<>();
		}
		return (List<Comentario>) ret.getList();
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