package com.example.bimfacedemo.service.serviceImpl;

import com.example.bimfacedemo.entity.FoderMessage;
import com.example.bimfacedemo.service.FoderService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by cb
 * @description TODO
 * @date 2019/7/24
 */
@Service
public class FoderServiceImpl implements FoderService {
    @Override
    public Map<String, Object> addFoderMessage(FoderMessage foderMessage) {
        HashMap<String, Object> result = new HashMap<>();
        return result;
    }

    @Override
    public Map<String, Object> updateFoderMessage(FoderMessage foderMessage) {
        HashMap<String, Object> result = new HashMap<>();
        return result;
    }

    @Override
    public Map<String, Object> getFoderMessageById(Integer id) {
        HashMap<String, Object> result = new HashMap<>();
        return result;
    }

    @Override
    public Map<String, Object> getFoderMessageAll() {
        HashMap<String, Object> result = new HashMap<>();
        return result;
    }

    @Override
    public Map<String, Object> deleteFoderMessageById(Integer id) {
        HashMap<String, Object> result = new HashMap<>();
        return result;
    }
}
