package com.taotao.manage.controller;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;

@Controller
@RequestMapping("item")
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    /**
     * 新增商品
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc , @RequestParam("itemParams") String itemParams) {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("新增商品，item = {} ， desc = {}", item, desc , itemParams);
            }
            if (StringUtils.isEmpty(item.getTitle()) && StringUtils.isEmpty(String.valueOf(item.getPrice()))) {
                // 参数有误
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            boolean bool = this.itemService.saveItem(item , desc , itemParams);
            if (!bool) {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("新增商品失败，item = {}", item);
                }
                // 保存失败
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("新增商品成功，itemId = {}", item.getId());
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            LOGGER.error("新增商品出错！item = " + item.toString(), e);
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    
    /**
     * 查询商品列表
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemList(
            @RequestParam(value = "page" ,defaultValue = "1") Integer page , 
            @RequestParam(value = "rows " , defaultValue = "30")Integer rows){
        try {
            EasyUIResult result = this.itemService.queryItemList(page , rows);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            LOGGER.error("查询商品出错！page = " + page + "rows = " + rows, e);
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updataItem(Item item , @RequestParam("desc") String desc , @RequestParam("itemParams")String itemParams){
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("编辑商品，item = {} ， desc = {}", item, desc);
            }
            if (StringUtils.isEmpty(item.getTitle()) && StringUtils.isEmpty(String.valueOf(item.getPrice()))) {
                // 参数有误 400
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            boolean bool = this.itemService.updateItem(item, desc , itemParams);
            if (!bool) {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("编辑商品失败，item = {}", item);
                }
                //编辑失败 500
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("编辑商品成功，itemId = {}", item.getId());
            }
            //204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            LOGGER.error("编辑商品出错！item = " + item.toString(), e);
            e.printStackTrace();
        }
        //500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}