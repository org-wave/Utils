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
package org.wave.utils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.wave.utils.enums.ErrorEnum;


/**
 * Agrupa metodos estaticos com o objetivo de facilitar o uso de reflexao.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 * 
 */
public class ReflectionUtil {

	private ReflectionUtil() {

	}

	/**
	 * Indica que um determinado atributo e estatico.
	 * 
	 * @param field
	 * @return true se o atributo e estatico.
	 */
	public static boolean isStatic(Field field) {
		return Modifier.isStatic(field.getModifiers());
	}

	/**
	 * Indica que um determinado atributo e transiente.
	 * 
	 * @param field
	 * @return true se o atributo e transiente.
	 */
	public static boolean isTransient(Field field) {
		return Modifier.isTransient(field.getModifiers());
	}

	/**
	 * Indica que um determinado atributo e persistente. Um atributo e persistente quando nao e estatico nem transiente.
	 * 
	 * @param field
	 * @return true se o atributo e persistente.
	 */
	public static boolean isPersistent(Field field) {
		return !isStatic(field) && !isTransient(field);
	}

	/**
	 * Retorna os atributos persistentes de uma determinada classe.
	 * 
	 * @param klass
	 * @return Lista de atributos.
	 * @see #isPersistent(Field)
	 */
	public static List<Field> getPersistentFields(Class<?> klass) {
		List<Field> fields = new ArrayList<Field>();

		for (Field field : klass.getDeclaredFields()) {
			if (isPersistent(field)) {
				fields.add(field);
			}
		}

		return fields;
	}

	/**
	 * Indica que a classe implementa uma determinada interface.
	 * 
	 * @param klass
	 * @param interfaceClass
	 * @return true se a classe implementa a interface.
	 */
	public static boolean implementz(Class<?> klass, Class<?> interfaceClass) {
		Class<?>[] interfaces = klass.getInterfaces();
		for (Class<?> i : interfaces) {
			if (i.equals(interfaceClass)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Retorna o valor de um determinado atributo da instancia.
	 * 
	 * @param field
	 * @param instance
	 * @return null se {@link IllegalArgumentException} ou {@link IllegalAccessException} for capturada.
	 */
	public static <T> Object get(Field field, T instance) {
		try {
			boolean accessible = field.isAccessible();
			field.setAccessible(Boolean.TRUE);

			Object value = field.get(instance);

			field.setAccessible(accessible);
			return value;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Atribui valor a um determinado atributo da instancia.
	 * 
	 * @param value
	 * @param field
	 * @param instance
	 */
	public static <T> void set(Object value, Field field, T instance) {
		try {
			boolean accessible = field.isAccessible();
			field.setAccessible(Boolean.TRUE);

			field.set(instance, value);

			field.setAccessible(accessible);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Copia o valor de cada atributo do objeto de origem para o objeto de destino.
	 * 
	 * @param origin
	 * @param target
	 * @exception IllegalArgumentException
	 *                se a classe do objeto de origem for diferente da classe do objeto de destino.
	 */
	public static <T> void copy(T origin, T target) {
		Class<?> originClass = origin.getClass();
		Class<?> targetClass = target.getClass();

		if (!originClass.equals(targetClass)) {
			throw new IllegalArgumentException(ErrorEnum.DIFFERENT_CLASSES.getMessage());
		}

		List<Field> fields = getPersistentFields(targetClass);
		for (Field field : fields) {
			Object value = get(field, origin);
			set(value, field, target);
		}
	}

	/**
	 * Retorna o tipo dos elementos de um determinado atributo.
	 * 
	 * @param field
	 * @return Tipo dos elementos de uma colecao.
	 * @exception IllegalArgumentException
	 *                se o atributo nao for uma colecao.
	 * @see #isCollection(Class)
	 */
	public static Class<?> getTypeOfElements(Field field) {
		if (!isCollection(field.getType())) {
			throw new IllegalArgumentException(ErrorEnum.NOT_COLLECTION_FIELD.getMessage());
		}

		ParameterizedType type = (ParameterizedType) field.getGenericType();

		return (Class<?>) type.getActualTypeArguments()[0];
	}

	/**
	 * Indica que uma determinada classe e colecao
	 * 
	 * @param klass
	 * @return true se a classe e colecao.
	 */
	public static boolean isCollection(Class<?> klass) {
		boolean isArrayList = klass.equals(ArrayList.class);
		boolean isList = klass.equals(List.class);
		boolean isCollection = klass.equals(Collection.class);

		return isArrayList || isList || isCollection;
	}

	/**
	 * Indica que a classe esta anotada por uma determinada anotacao.
	 * 
	 * @param klass
	 * @param annotationClass
	 * @return true se a classe esta anotada pela anotacao.
	 */
	public static boolean isAnnotated(Class<?> klass, Class<? extends Annotation> annotationClass) {
		return klass.getAnnotation(annotationClass) != null;
	}

	/**
	 * Indica que o atributo esta anotado por uma determinada anotacao.
	 * 
	 * @param field
	 * @param annotationClass
	 * @return true se o atributo esta anotado pela anotacao.
	 */
	public static boolean isAnnotated(Field field, Class<? extends Annotation> annotationClass) {
		return field.getAnnotation(annotationClass) != null;
	}

	/**
	 * Indica que o metodo esta anotado por uma determinada anotacao.
	 * 
	 * @param method
	 * @param annotationClass
	 * @return true se o metodo esta anotado pela anotacao.
	 */
	public static boolean isAnnotated(Method method, Class<? extends Annotation> annotationClass) {
		return method.getAnnotation(annotationClass) != null;
	}

	/**
	 * Retorna o atributo de uma determinada classe.
	 * 
	 * @param fieldName
	 * @param klass
	 * @return null se o atributo nao for encontrado.
	 */
	public static Field getField(String fieldName, Class<?> klass) {
		for (Field field : klass.getDeclaredFields()) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}

		return null;
	}

	/**
	 * Retorna o metodo de uma determinada classe.
	 * 
	 * @param methodName
	 * @param klass
	 * @return null se o metodo nao for encontrado.
	 */
	public static Method getMethod(String methodName, Class<?> klass) {
		for (Method method : klass.getDeclaredMethods()) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}

		return null;
	}

	/**
	 * Indica que a classe possui um construtor padrao sem argumentos. Construtores privados tambem sao considerados.
	 * 
	 * @param klass
	 * @return true se a classe possui um construtor.
	 */
	public static boolean hasConstructor(Class<?> klass) {
		try {
			return klass.getDeclaredConstructor() != null;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Indica que a classe possui um determinado atributo.
	 * 
	 * @param klass
	 * @param fieldName
	 * @return true se a classe possui o atributo.
	 */
	public static boolean hasField(Class<?> klass, String fieldName) {
		Field[] fields = klass.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Indica que a classe possui um determinado metodo.
	 * 
	 * @param klass
	 * @param methodName
	 * @return true se a classe possui o metodo.
	 */
	public static boolean hasMethod(Class<?> klass, String methodName) {
		Method[] methods = klass.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return true;
			}
		}

		return false;
	}

}
