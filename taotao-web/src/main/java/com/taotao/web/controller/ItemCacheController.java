package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.common.service.RedisService;

@Controller
@RequestMapping("item/cache")
public class ItemCacheController {

    @Autowired
    private RedisService redisService;
    
    private static final String REDIS_KEY = "TAOTAO_WEB_ITEM_DATAIL_" ;

    /**
     * 接受商品ID，删除对应商品缓存数据
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}" , method = RequestMethod.POST)
    public ResponseEntity<Void> deleteCache(@PathVariable("itemId") long itemId){
        String key = REDIS_KEY  + itemId;
        System.out.println(key);
        try {
            this.redisService.del(key);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    
    
}
