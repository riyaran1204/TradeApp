package com.trade.TradeApp.controller;

import com.trade.TradeApp.model.Trade;
import com.trade.TradeApp.model.TradeResponse;
import com.trade.TradeApp.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trade")
public class TradeController {


    @Autowired
    TradeService tradeService;

    @PostMapping("/storeTrade")
    public ResponseEntity<Trade> bookOrder(@RequestBody Trade request)
    {
        tradeService.storeTrade(request);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/updateTrade")
    public TradeResponse updateTrade(@RequestBody Trade trade)  {
        TradeResponse tradeResponse= null;
        try {
             tradeResponse= tradeService.processTradeUpdate(trade);
            return tradeResponse;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tradeResponse;
    }
}
