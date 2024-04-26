package com.trade.TradeApp.dao;

import com.trade.TradeApp.Interface.ITradeServiceDao;
import com.trade.TradeApp.model.Trade;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public  class TradeServiceDaoImpl  extends AbstractDao<Trade> implements ITradeServiceDao {

    private static final Map<Integer, Trade> trades = new HashMap<>();

    @Override
    public void saveTrade(Trade trade) {
         save(trade);
    }

    @Override
    public List<Trade> findTradeById(String tradeId) {
      return   findByProperty("tradeId",tradeId,Trade.class);
    }



    @Override
    public List<Trade> findVersionId(int versionId) {
        return   findByProperty("version",versionId,Trade.class);
    }


}
