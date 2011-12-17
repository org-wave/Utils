package org.wave.utils.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import org.junit.Test;
import org.wave.utils.enums.ErrorEnum;
import org.wave.utils.reflection.ReflectionUtil;
import org.wave.utils.reflection.examples.ClasseComAtEntity;
import org.wave.utils.reflection.examples.ClasseComAtributosPadrao;
import org.wave.utils.reflection.examples.ClasseComConstrutorPadrao;
import org.wave.utils.reflection.examples.ClasseComConstrutorPadraoPrivado;
import org.wave.utils.reflection.examples.ClasseComEquals;
import org.wave.utils.reflection.examples.ClasseComId;
import org.wave.utils.reflection.examples.ClasseNaoSerializavel;
import org.wave.utils.reflection.examples.ClasseSemAtEntity;
import org.wave.utils.reflection.examples.ClasseSemConstrutorPadrao;
import org.wave.utils.reflection.examples.ClasseSemEquals;
import org.wave.utils.reflection.examples.ClasseSerializavel;


@SuppressWarnings("unused")
public class ReflectionUtilTest {

	private String atributo;

	private static String atributoEstatico;

	private String atributoNaoEstatico;

	private transient String atributoTransiente;

	private String atributoNaoTransiente;

	private static transient String atributoEstaticoETransiente;

	private String atributoDoTipoString;

	private Collection<String> colecao;

	@Id
	private Long id;

	@Test
	public void deveRetornarVerdadeiroSeOAtributoForEstatico() {
		try {
			assertTrue(ReflectionUtil.isStatic(this.getClass().getDeclaredField("atributoEstatico")));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deveRetornarFalsoSeOAtributoNaoForEstatico() {
		try {
			assertFalse(ReflectionUtil.isStatic(this.getClass().getDeclaredField("atributoNaoEstatico")));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deveRetornarVerdadeiroSeOAtributoForTransiente() {
		try {
			assertTrue(ReflectionUtil.isTransient(this.getClass().getDeclaredField("atributoTransiente")));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deveRetornarFalsoSeOAtributoNaoForTransiente() {
		try {
			assertFalse(ReflectionUtil.isTransient(this.getClass().getDeclaredField("atributoNaoTransiente")));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deveRetornarVerdadeiroSeOAtributoForPersistente() {
		try {
			assertTrue(ReflectionUtil.isPersistent(this.getClass().getDeclaredField("atributo")));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deveRetornarFalsoSeOAtributoNaoForPersistente() {
		try {
			assertFalse(ReflectionUtil.isPersistent(this.getClass().getDeclaredField("atributoEstatico")));
			assertFalse(ReflectionUtil.isPersistent(this.getClass().getDeclaredField("atributoTransiente")));
			assertFalse(ReflectionUtil.isPersistent(this.getClass().getDeclaredField("atributoEstaticoETransiente")));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deveRetornarOsAtributosPersistentesDeUmaClasse() {
		List<Field> fields = ReflectionUtil.getPersistentFields(ReflectionUtilTest.class);

		assertEquals("atributo", fields.get(0).getName());
		assertEquals("atributoNaoEstatico", fields.get(1).getName());
		assertEquals("atributoNaoTransiente", fields.get(2).getName());
		assertEquals("atributoDoTipoString", fields.get(3).getName());
		assertEquals("colecao", fields.get(4).getName());
		assertEquals("id", fields.get(5).getName());

		assertEquals(6, fields.size());
	}

	@Test
	public void deveRetornarVerdadeiroSeAClasseForSerializable() {
		assertTrue(ReflectionUtil.implementz(ClasseSerializavel.class, java.io.Serializable.class));
	}

	@Test
	public void deveRetornarFalsoSeAClasseNaoForSerializable() {
		assertFalse(ReflectionUtil.implementz(ClasseNaoSerializavel.class, java.io.Serializable.class));
	}

	@Test
	public void deveRetornarUmValorDeUmAtributoDeUmaClasse() throws SecurityException, NoSuchFieldException, InstantiationException, IllegalAccessException {
		String valor = "Teste";

		Field field = ReflectionUtilTest.class.getDeclaredField("atributo");
		ReflectionUtilTest instance = ReflectionUtilTest.class.newInstance();
		instance.atributo = valor;

		assertEquals(valor, ReflectionUtil.get(field, instance));
	}

	@Test
	public void deveAtribuirUmValorAUmAtributoDeUmaClasse() throws SecurityException, NoSuchFieldException, InstantiationException, IllegalAccessException {
		Field field = ReflectionUtilTest.class.getDeclaredField("atributo");
		ReflectionUtilTest instance = ReflectionUtilTest.class.newInstance();
		ReflectionUtil.set("Teste", field, instance);

		assertNotNull(field.get(instance));
	}

	@Test(expected = IllegalArgumentException.class)
	public void deveLancarUmaExcecaoSeAsInstanciasForemDeClassesDiferentesException() {
		ClasseComAtributosPadrao origin = new ClasseComAtributosPadrao();
		String target = new String();

		ReflectionUtil.copy(origin, target);		
	}
	
	@Test
	public void deveLancarUmaExcecaoSeAsInstanciasForemDeClassesDiferentes() {
		ClasseComAtributosPadrao origin = new ClasseComAtributosPadrao();
		String target = new String();
		
		try {
			ReflectionUtil.copy(origin, target);				
		} catch (Exception e) {
			assertEquals(ErrorEnum.DIFFERENT_CLASSES.getMessage(), e.getMessage());
		}
	}

	@Test
	public void deveCopiarOValorDosAtributosDeUmaInstanciaParaOutraDaMesmaClasse() {
		String stringValue = "Origem";
		Integer integerValue = 1000;
		BigDecimal bigDecimalValue = BigDecimal.valueOf(1000);
		Boolean booleanValue = Boolean.TRUE;
		byte[] byteValue = new byte[integerValue];

		ClasseComAtributosPadrao origin = new ClasseComAtributosPadrao();
		origin.setStringField(stringValue);
		origin.setIntegerField(integerValue);
		origin.setBigDecimalField(bigDecimalValue);
		origin.setBooleanField(booleanValue);
		origin.setByteField(byteValue);

		ClasseComAtributosPadrao target = new ClasseComAtributosPadrao();
		target.setStringField("Destino");

		ReflectionUtil.copy(origin, target);

		assertEquals(origin.getStringField(), target.getStringField());
		assertEquals(origin.getIntegerField(), target.getIntegerField());
		assertEquals(origin.getLongField(), target.getLongField());
		assertEquals(origin.getBigDecimalField(), target.getBigDecimalField());
		assertEquals(origin.getBooleanField(), target.getBooleanField());
		assertEquals(origin.getCalendarField(), target.getCalendarField());
		assertEquals(origin.getByteField(), target.getByteField());
	}

	@Test(expected = IllegalArgumentException.class)
	public void deveLancarUmaExcecaoSeOAtributoNaoForUmaColecaoException() {
		Field field = this.getField("atributo", ReflectionUtilTest.class);

		ReflectionUtil.getTypeOfElements(field);
	}
	
	@Test
	public void deveLancarUmaExcecaoSeOAtributoNaoForUmaColecao() {
		Field field = this.getField("atributo", ReflectionUtilTest.class);
	
		try {
			ReflectionUtil.getTypeOfElements(field);			
		} catch (Exception e) {
			assertEquals(ErrorEnum.NOT_COLLECTION_FIELD.getMessage(), e.getMessage());
		}
	}

	@Test
	public void deveRetornarOTipoDosElementosDeUmaColecao() {
		Field field = this.getField("colecao", ReflectionUtilTest.class);

		assertEquals(String.class, ReflectionUtil.getTypeOfElements(field));
	}

	@Test
	public void deveRetornarVerdadeiroSeAClasseForUmaColecao() {
		assertTrue(ReflectionUtil.isCollection(ArrayList.class));
		assertTrue(ReflectionUtil.isCollection(List.class));
		assertTrue(ReflectionUtil.isCollection(Collection.class));
	}

	@Test
	public void deveRetornarFalsoSeAClasseNaoForUmaColecao() {
		assertFalse(ReflectionUtil.isCollection(Set.class));
		assertFalse(ReflectionUtil.isCollection(Map.class));
	}

	@Test
	public void deveRetornarFalsoQuandoUmaClasseNaoTiverUmaDeterminadaAnotacao() {
		assertFalse(ReflectionUtil.isAnnotated(ClasseSemAtEntity.class, Entity.class));
	}

	@Test
	public void deveRetornarVerdadeiroQuandoUmaClasseTiverUmaDeterminadaAnotacao() {
		assertTrue(ReflectionUtil.isAnnotated(ClasseComAtEntity.class, Entity.class));
	}

	@Test
	public void deveRetornarFalsoQuandoUmAtributoNaoTiverUmaDeterminadaAnotacao() {
		assertFalse(ReflectionUtil.isAnnotated(ReflectionUtil.getField("id", ReflectionUtilTest.class), Version.class));
	}

	@Test
	public void deveRetornarVerdadeiroQuandoUmAtributoTiverUmaDeterminadaAnotacao() {
		assertTrue(ReflectionUtil.isAnnotated(ReflectionUtil.getField("id", ReflectionUtilTest.class), Id.class));
	}
	
	@Test
	public void deveRetornarFalsoQuandoUmMetodoNaoTiverUmaDeterminadaAnotacao() {
		assertFalse(ReflectionUtil.isAnnotated(ReflectionUtil.getMethod("getField", ReflectionUtilTest.class), Test.class));
	}
	
	@Test
	public void deveRetornarVerdadeiroQuandoUmMetodoTiverUmaDeterminadaAnotacao() {
		assertTrue(ReflectionUtil.isAnnotated(ReflectionUtil.getMethod("deveRetornarVerdadeiroQuandoUmMetodoTiverUmaDeterminadaAnotacao", ReflectionUtilTest.class), Test.class));
	}

	@Test
	public void deveRetornarFalsoQuandoUmaClasseNaoTiverUmDeterminadoAtributo() {
		assertFalse(ReflectionUtil.hasField(ClasseSerializavel.class, "id"));
	}

	@Test
	public void deveRetornarVerdadeiroQuandoUmaClasseTiverUmDeterminadoAtributo() {
		assertTrue(ReflectionUtil.hasField(ClasseComId.class, "id"));
	}

	@Test
	public void deveRetornarUmDeterminadoAtributoDeUmaClasse() {
		assertNotNull(ReflectionUtil.getField("atributo", ReflectionUtilTest.class));
	}
	
	@Test
	public void deveRetornarUmDeterminadoMetodoDeUmaClasse() {
		assertNotNull(ReflectionUtil.getMethod("deveRetornarUmDeterminadoMetodoDeUmaClasse", ReflectionUtilTest.class));
	}

	@Test
	public void deveRetornarFalsoQuandoUmaClasseNaoTiverUmConstrutorPadrao() {
		assertFalse(ReflectionUtil.hasConstructor(ClasseSemConstrutorPadrao.class));
	}

	@Test
	public void deveRetornarVerdadeiroQuandoUmaClasseTiverUmConstrutorPadrao() {
		assertTrue(ReflectionUtil.hasConstructor(ClasseComConstrutorPadrao.class));
	}

	@Test
	public void deveRetornarVerdadeiroQuandoUmaClasseTiverUmConstrutorPadraoPrivado() {
		assertTrue(ReflectionUtil.hasConstructor(ClasseComConstrutorPadraoPrivado.class));
	}

	@Test
	public void deveRetornarFalsoQuandoUmaClasseNaoTiverUmDeterminadoMetodo() {
		assertFalse(ReflectionUtil.hasMethod(ClasseSemEquals.class, "equals"));
	}

	@Test
	public void deveRetornarVerdadeiroQuandoUmaClasseTiverUmDeterminadoMetodo() {
		assertTrue(ReflectionUtil.hasMethod(ClasseComEquals.class, "equals"));
	}

	private Field getField(String fieldName, Class<?> klass) {
		try {
			return klass.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		return null;
	}

}
