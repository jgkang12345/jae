package kang.jae.goo.setting.service;

import kang.jae.goo.register.dto.TransactionSlipDTO;
import kang.jae.goo.setting.mapper.SettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SettingService {
    private final SettingMapper settingMapper;

    public SettingService(SettingMapper settingMapper) {
        this.settingMapper = settingMapper;
    }

    public Map<String, Object> doSave(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        try {
            map.put("saveGb", "U");
            if (null == map.get("seq") || "".equals(map.get("seq"))) {
                map.put("saveGb", "I");
            }
            String saveGb = map.get("saveGb").toString();
            map.put("user_id", "jgkang");
            switch (saveGb)
            {
                case "I":
                    int item_no = settingMapper.SelectDno(map);
                    map.put("item_id", "variation_item_" + String.valueOf(item_no));
                    settingMapper.InsertItem(map);
                    break;

                case "U":
                    settingMapper.UpdateItem(map);
                    break;
            }
            result.put("result", true);
        }
        catch (Exception e) {
            e.printStackTrace();
            result.put("result", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    public Map<String, Object> doSearch(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Map<String, Object>> list = settingMapper.doSearch(map);
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

    public Map<String, Object> doDelete(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        try {
            settingMapper.doDelete(map);
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
