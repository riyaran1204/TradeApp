package com.trade.TradeApp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trade.TradeApp.dao.TradeServiceDaoImpl;
import com.trade.TradeApp.model.Trade;
import com.trade.TradeApp.model.TradeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TradeService {

    @Autowired
    TradeServiceDaoImpl tradeServiceDao;

    public void storeTrade(Trade request) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        mapper.setDateFormat(dateFormat);
        Trade trade = mapper.convertValue(request, Trade.class);
        request.setMaturityAge(trade.getMaturityAge());
        request.setCreatedDate(new Date());
        tradeServiceDao.save(request);
    }


    public TradeResponse processTradeUpdate(Trade trade) {
        TradeResponse tradeResponse = new TradeResponse();
        List<Trade> existingTrade = tradeServiceDao.findTradeById(trade.getTradeId());
        for (Trade finalExistingTrade : existingTrade) {
            if (trade.getTradeId().equalsIgnoreCase(finalExistingTrade.getTradeId())) {
                if (trade.getMaturityAge().before(new Date())) {
                    finalExistingTrade.setExpired("Y");
                    tradeResponse.setTradeId(trade.getTradeId());
                    tradeResponse.setMessage("Trade maturity date cannot be in the past " + trade.getTradeId());
                }
              else  if (existingTrade != null && trade.getVersion() < finalExistingTrade.getVersion()) {
                        tradeResponse.setTradeId(trade.getTradeId());
                        tradeResponse.setMessage("Lower version received for Trade ID " + trade.getTradeId());
                    }
                    else{
                        finalExistingTrade.setTradeId(trade.getTradeId());
                        finalExistingTrade.setVersion(trade.getVersion());
                        finalExistingTrade.setExpired(trade.getExpired());
                        finalExistingTrade.setCounterPartyId(trade.getCounterPartyId());
                        finalExistingTrade.setMaturityAge(trade.getMaturityAge());
                        finalExistingTrade.setBookId(trade.getBookId());
                        finalExistingTrade.setCreatedDate(new Date());
                        tradeResponse.setTradeId(trade.getTradeId());
                        tradeResponse.setMessage("Trade updated " + trade.getTradeId());
                    }
                    tradeServiceDao.saveTrade(finalExistingTrade);
                }
            }
            return tradeResponse;

    }
}

