package org.wave.utils.calendar;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;
import org.wave.utils.calendar.CalendarUtil;
import org.wave.utils.enums.CompareEnum;


public class CalendarUtilTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void deveLancarExcecaoQuandoADataForNula() {
		Calendar comparedCalendar = Calendar.getInstance();
		
		CalendarUtil.compare(null, comparedCalendar);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deveLancarExcecaoQuandoADataComparadaForNula() {
		Calendar calendar = Calendar.getInstance();
		
		CalendarUtil.compare(calendar, null);
	}

	@Test
	public void deveRetornarMenorQuandoOAnoDaDataForMenorQueOAnoDaDataComparada() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);

		Calendar comparedCalendar = Calendar.getInstance();

		assertEquals(CompareEnum.LESSER, CalendarUtil.compare(calendar, comparedCalendar));
	}

	@Test
	public void deveRetornarMenorQuandoOMesDaDataForMenorQueOMesDaDataComparada() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);

		Calendar comparedCalendar = Calendar.getInstance();

		assertEquals(CompareEnum.LESSER, CalendarUtil.compare(calendar, comparedCalendar));
	}

	@Test
	public void deveRetornarMenorQuandoODiaDaDataForMenorQueODiaDaDataComparada() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);

		Calendar comparedCalendar = Calendar.getInstance();

		assertEquals(CompareEnum.LESSER, CalendarUtil.compare(calendar, comparedCalendar));
	}

	@Test
	public void deveRetornarIgualQuandoOAnoMesEDiaDaDataForemIguaisAoAnoMesEDiaDaDataComparada() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -1);

		Calendar comparedCalendar = Calendar.getInstance();

		assertEquals(CompareEnum.EQUALS, CalendarUtil.compare(calendar, comparedCalendar));
	}

	@Test
	public void deveRetornarMaiorQuandoOAnoDaDataForMaiorQueOAnoDaDataComparada() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, 1);

		Calendar comparedCalendar = Calendar.getInstance();

		assertEquals(CompareEnum.GREATER, CalendarUtil.compare(calendar, comparedCalendar));
	}

	@Test
	public void deveRetornarMaiorQuandoOMesDaDataForMaiorQueOMesDaDataComparada() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);

		Calendar comparedCalendar = Calendar.getInstance();

		assertEquals(CompareEnum.GREATER, CalendarUtil.compare(calendar, comparedCalendar));
	}

	@Test
	public void deveRetornarMaiorQuandoODiaDaDataForMaiorQueODiaDaDataComparada() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);

		Calendar comparedCalendar = Calendar.getInstance();

		assertEquals(CompareEnum.GREATER, CalendarUtil.compare(calendar, comparedCalendar));
	}

}
