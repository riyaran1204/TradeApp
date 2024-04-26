package com.trade.TradeApp;

import com.trade.TradeApp.dao.TradeServiceDaoImpl;
import com.trade.TradeApp.model.Trade;
import com.trade.TradeApp.model.TradeResponse;
import com.trade.TradeApp.services.TradeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TradeAppApplicationTests {

	@Mock
	private TradeServiceDaoImpl tradeServiceDao;

	@InjectMocks
	private TradeService tradeService;

	@Test
	void contextLoads() {
	}

	@Test
	public void testProcessTradeWithLowerVersion() {
		Trade existingTrade = new Trade();
		existingTrade.setVersion(2);

		Trade newTrade = new Trade();
		newTrade.setVersion(1);

		when(tradeServiceDao.findVersionId(1)).thenReturn(Collections.singletonList(existingTrade));

		assertThrows(IllegalArgumentException.class, () -> tradeService.processTradeUpdate(newTrade));
	}
	@Test
	public void testProcessTradeWithMaturityDateBeforeToday() {
		Trade newTrade = new Trade();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		newTrade.setMaturityAge(calendar.getTime());
		assertThrows(IllegalArgumentException.class, () -> tradeService.processTradeUpdate(newTrade));
	}

	@Test
	public void testProcessTradeWithSameVersion() {
		// Arrange
		Trade existingTrade = new Trade();
		existingTrade.setVersion(1);

		Trade newTrade = new Trade();
		newTrade.setVersion(1);

		when(tradeServiceDao.findVersionId(1)).thenReturn(Collections.singletonList(existingTrade));
		TradeResponse result = tradeService.processTradeUpdate(newTrade);
		assertEquals(newTrade, result);
	}




}
