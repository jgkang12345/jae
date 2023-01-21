package kang.jae.goo.management.service;

import kang.jae.goo.management.mapper.ManagementMapper;
import kang.jae.goo.register.dto.TransactionSlipDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagementService {
    private final ManagementMapper managementMapper;

    public ManagementService(ManagementMapper managementMapper) {
        this.managementMapper = managementMapper;
    }

    public Map<String, Object> doSearch(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<TransactionSlipDTO> list = managementMapper.doSearch(map);
            result.put("data", list);
            result.put("result", true);
        }
        catch (Exception e) {
            e.printStackTrace();
            result.put("result", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    public Map<String, Object> deleteItem(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        try {
            managementMapper.deleteItem(map);
            result.put("result", true);
        }
        catch (Exception e) {
            e.printStackTrace();
            result.put("result", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    public Map<String, Object> updateItem(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        try {
            managementMapper.updateItem(map);
            result.put("result", true);
        }
        catch (Exception e) {
            e.printStackTrace();
            result.put("result", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
