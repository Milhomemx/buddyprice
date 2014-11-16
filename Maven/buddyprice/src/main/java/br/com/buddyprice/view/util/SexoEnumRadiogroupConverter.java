package br.com.buddyprice.view.util;

import br.com.vexillum.model.enums.Sexo;

/**
 * @author Natan
 * Classe conversora de radioGroups definidos no sistema para objetos.
 */
@SuppressWarnings("serial")
public class SexoEnumRadiogroupConverter extends EnumRadiogroupConverter<Sexo> {

	public SexoEnumRadiogroupConverter() {
		super(Sexo.class);		
	}

}
