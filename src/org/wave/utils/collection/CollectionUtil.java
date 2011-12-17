/*
   Copyright 2011 Benedito Barbosa Ribeiro Neto/Christian Linhares Peixoto/Mauricio da Silva Marinho

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.wave.utils.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * Agrupa metodos estaticos com o objetivo de facilitar a manipulacao de colecoes.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 * 
 */
public class CollectionUtil {

	/**
	 * Converte uma serie de argumentos em uma lista. Cada argumento da serie ocupara a mesma posicao na lista retornada.
	 * 
	 * @param varargs
	 * @return Lista de argumentos.
	 * @exception IllegalArgumentException
	 *                se a serie de argumentos for nula.
	 */
	public static <T> List<T> convert(T... varargs) {
		if (varargs == null) {
			throw new IllegalArgumentException();
		}

		List<T> list = new ArrayList<T>();

		for (int i = 0; i < varargs.length; i++) {
			list.add(varargs[i]);
		}

		return list;
	}

}
