package com.example.bimfacedemo.controller;

import com.example.bimfacedemo.entity.FoderMessage;
import com.example.bimfacedemo.service.FoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by cb
 * @description 文件夹信息增删查改功能呢
 * @date 2019/7/24
 */
@RequestMapping("/foder")
@RestController
public class FoderController {

    @Autowired
    FoderService foderService;

    @PostMapping("/foderMessage")
    public Map<String, Object> addFoderMessage(@RequestBody FoderMessage foderMessage){
        Map<String, Object> result = new HashMap<>();
        result = foderService.addFoderMessage(foderMessage);
        return result;
    }

    @PutMapping("/foderMessage")
    public Map<String, Object> updateFoderMessage(@RequestBody FoderMessage foderMessage){
        Map<String, Object> result = new HashMap<>();
        result = foderService.updateFoderMessage(foderMessage);
        return result;
    }

    @GetMapping("/foderMessage/{id}")
    public Map<String, Object> getFoderMessageById(@PathVariable Integer id){
        Map<String, Object> result = new HashMap<>();
        result = foderService.getFoderMessageById(id);
        return result;
    }

    @GetMapping("/foderMessage")
    public Map<String, Object> getFoderMessageAll(){
        Map<String, Object> result = new HashMap<>();
        result = foderService.getFoderMessageAll();
        return result;
    }

    @DeleteMapping("/foderMessage/{id}")
    public Map<String, Object> deleteFoderMessageById(@PathVariable Integer id){
        Map<String, Object> result = new HashMap<>();
        result = foderService.deleteFoderMessageById(id);
        return result;
    }
}
