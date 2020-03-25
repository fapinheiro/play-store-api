package com.nelioalves.cursomc.service;

import java.util.Calendar;
import java.util.Date;

import com.nelioalves.cursomc.domain.PagamentoBoleto;

import org.springframework.stereotype.Service;


@Service
public class BoletoService {

	public void preencherPagamentoBoleto(PagamentoBoleto pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
}