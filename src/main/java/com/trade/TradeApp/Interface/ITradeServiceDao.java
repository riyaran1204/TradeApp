package com.trade.TradeApp.Interface;

import com.trade.TradeApp.model.Trade;

import java.util.List;


public interface ITradeServiceDao  {

    void saveTrade(Trade trade);
    List<Trade> findTradeById(String tradeId);
    List<Trade> findVersionId(int versionId);


}
