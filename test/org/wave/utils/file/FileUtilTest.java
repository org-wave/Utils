package org.wave.utils.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wave.utils.enums.ErrorEnum;
import org.wave.utils.file.FileUtil;


public class FileUtilTest {

	private File raiz;

	@Before
	public void setUp() {
		this.raiz = new File("raiz");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deveLancarExcecaoQuandoOArquivoForNuloException() {
		FileUtil.delete(null);
	}
	
	@Test
	public void deveLancarExcecaoQuandoOArquivoForNulo() {
		try {
			FileUtil.delete(null);			
		} catch (Exception e) {
			assertEquals(ErrorEnum.NULL_FILE.getMessage(), e.getMessage());
		}
	}

	@Test
	public void deveExcluirUmArquivoQuandoForArquivoSimples() throws IOException {
		File file = new File(this.raiz, "ArquivoSimples");
		assertTrue(file.createNewFile());

		assertTrue(FileUtil.delete(file));
	}

	@Test
	public void deveExcluirUmArquivoQuandoForDiretorio() {
		File file = new File(this.raiz, "diretorio");
		assertTrue(file.mkdir());

		assertTrue(FileUtil.delete(file));
	}

	@Test
	public void deveExcluirUmArquivoQuandoForDiretorioComArquivoSimples() throws IOException {
		File file = new File(this.raiz, "diretorio");
		assertTrue(file.mkdir());

		File arquivo = new File(file, "ArquivoSimples");
		assertTrue(arquivo.createNewFile());

		assertTrue(FileUtil.delete(file));
	}

	@Test
	public void deveExcluirUmArquivoQuandoForDiretorioComDiretorio() {
		File file = new File(this.raiz, "diretorio");
		assertTrue(file.mkdir());

		File diretorio = new File(file, "diretorio");
		assertTrue(diretorio.mkdir());

		assertTrue(FileUtil.delete(file));
	}

	@Test
	public void deveExcluirUmArquivoQuandoForDiretorioComDiretorioComArquivoSimples() throws IOException {
		File file = new File(this.raiz, "diretorio");
		assertTrue(file.mkdir());

		File diretorio = new File(file, "diretorio");
		assertTrue(diretorio.mkdir());

		File arquivo = new File(diretorio, "ArquivoSimples");
		assertTrue(arquivo.createNewFile());

		assertTrue(FileUtil.delete(file));
	}

	@Test
	public void naoDeveExcluirUmArquivoQuandoEsseNaoExistir() throws IOException {
		File file = new File(this.raiz, "ArquivoSimples");

		assertFalse(FileUtil.delete(file));
	}
	
	@After
	public void tearDown() {
		assertEquals(0, this.raiz.listFiles().length);
	}
	
}
