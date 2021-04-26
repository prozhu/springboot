package com.open.fix.controller;

import com.alibaba.fastjson.JSON;
import com.open.fix.model.AdjustStockVo;
import com.open.fix.service.StockService;
import com.zc.springboot.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.SocketHandler;

@Slf4j
@RestController
public class StockController extends BaseController {

	@PostMapping("adjust/stock.do")
	public Object stock(@RequestBody  String[] param) throws  Exception{
		StockService stockTest = new StockService();
		AdjustStockVo data = stockTest.adjustStock(param);
		return data;
	}


	@PostMapping("adjust/dingding.do")
	public Object stock() throws  Exception{
		int num = 1 / 0;
		return null;
	}

}
