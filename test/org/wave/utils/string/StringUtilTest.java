package org.wave.utils.string;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.wave.utils.string.StringUtil;

public class StringUtilTest {

	@Test
	public void deveRetornarHumanCaseQuandoONomeDeUmMetodoIniciarComCaractereMaiusculo() {
		assertEquals("nome de um metodo", StringUtil.toHumanCase("NomeDeUmMetodo"));
	}

	@Test
	public void deveRetornarHumanCaseQuandoEmUmNomeDeMetodoHouverUmCaractereMaiusculoAntecedidoDeUmNaoMaiusculo() {
		assertEquals("nome de um metodo", StringUtil.toHumanCase("nomeDeUmMetodo"));
	}

	@Test
	public void deveRetornarHumanCaseQuandoEmUmNomeDeMetodoHouverUmCaractereMaiusculoSucedidoDeUmMinusculo() {
		assertEquals("nome de um metodo", StringUtil.toHumanCase("nomeDeUmMetodo"));
	}

	@Test
	public void deveRetornarHumanCaseQuandoEmUmNomeDeMetodoHouverUmCaractereMaiusculoAntecedidoDeUmNaoMaiusculoESucedidoDeUmMaiusculo() {
		assertEquals("nome de um metodo e alfanumerico", StringUtil.toHumanCase("nomeDeUmMetodoEAlfanumerico"));
	}

	@Test
	public void deveRetornarHumanCaseQuandoONomeDeUmMetodoForMinusculo() {
		assertEquals("nomedeummetodo", StringUtil.toHumanCase("nomedeummetodo"));
	}

	@Test
	public void deveRetornarHumanCaseQuandoONomeDeUmMetodoForMaiusculo() {
		assertEquals("NOMEDEUMMETODO", StringUtil.toHumanCase("NOMEDEUMMETODO"));
	}

	@Test
	public void deveRetornarHumanCaseQuandoEmUmNomeDeMetodoHouverSigla() {
		assertEquals("nome de um metodo SIGLA", StringUtil.toHumanCase("nomeDeUmMetodoSIGLA"));
	}

}
