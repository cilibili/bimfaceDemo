package com.example.bimfacedemo.service;

import com.example.bimfacedemo.entity.FoderMessage;

import java.util.Map;

/**
 * @author by cb
 * @description TODO
 * @date 2019/7/24
 */
public interface FoderService {
    Map<String, Object> addFoderMessage(FoderMessage foderMessage);

    Map<String, Object> updateFoderMessage(FoderMessage foderMessage);

    Map<String, Object> getFoderMessageById(Integer id);

    Map<String, Object> getFoderMessageAll();

    Map<String, Object> deleteFoderMessageById(Integer id);
}
