package br.com.buddyprice.control;

import java.util.ArrayList;
import java.util.List;

import br.com.buddyprice.model.AtributoExtraValor;
import br.com.buddyprice.model.annotations.AtributosExtras;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.model.CommonEntity;
import br.com.vexillum.model.ICommonEntity;
import br.com.vexillum.util.Return;

public class BaseController<E extends ICommonEntity> extends GenericControl<E> {

	public BaseController(Class<E> classEntity) {
		super(classEntity);
	}
	
	@SuppressWarnings("unchecked")
	public Return addAtributoExtra(){
		Return ret = new Return(true);
		AtributoExtraValor aev = (AtributoExtraValor) getData().get("atributoExtra");
		List<AtributoExtraValor> list = (List<AtributoExtraValor>) getData().get("atributosExtras");
		aev.setClassName(classEntity);
		list.add(aev);
		return ret;
	}
	
	public List<AtributoExtraValor> getAtributosExtras(){
		return getAtributosExtras((CommonEntity) getEntity());
	}

	@SuppressWarnings("unchecked")
	public List<AtributoExtraValor> getAtributosExtras(CommonEntity entity){
		if(!entity.getClass().isAnnotationPresent(AtributosExtras.class)){
			throw new UnsupportedOperationException("A entitidade deve estar anotada com @AtributosExtras");
		}
		if(entity.getId() != null){
			AtributoExtraValor atributo = new AtributoExtraValor(entity, entity.getClass());
			Return ret = searchByHQL(atributo);
			return (List<AtributoExtraValor>) ret.getList();
		}
		return new ArrayList<AtributoExtraValor>();
	}
	
	@Override
	public Return save(E entity) {
		Return ret = super.save(entity);
		if(ret.isValid()){
			saveAtributosExtra((Long) ret.getSerializable());
		}
		return ret;
	}
	
	public Return update(E entity) {
		Return ret = super.update(entity);
		if(ret.isValid()){
			saveAtributosExtra(entity);
		}
		return ret;
	}

	private void saveAtributosExtra(E entity){
		saveAtributosExtra(entity.getId());
	}
	
	@SuppressWarnings("unchecked")
	private void saveAtributosExtra(Long entity) {
		List<AtributoExtraValor> list = (List<AtributoExtraValor>) getData().get("atributosExtras");
		if(list != null && !list.isEmpty()){
			for (AtributoExtraValor atributoExtraValor : list) {
				if(atributoExtraValor.getIdEntidade() == null){
					atributoExtraValor.setIdEntidade(entity);
				}
				saveOrUpdate(atributoExtraValor);
			}
		}
	}
	
}
