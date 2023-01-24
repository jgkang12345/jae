package kang.jae.goo.dashboard.service;

import kang.jae.goo.config.utils.SecurityUtils;
import kang.jae.goo.dashboard.mapper.DashboardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {
    private final DashboardMapper dashboardMapper;

    public DashboardService(DashboardMapper dashboardMapper) {
        this.dashboardMapper = dashboardMapper;
    }

    public Map<String, Object> ds_getChartData(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        try {
            map.put("user_id", SecurityUtils.CurrentUserName());
            List<Map<String, Object>> list = dashboardMapper.ds_getChartData(map);
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

    public Map<String, Object> ds_getAlldata(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        try {
            map.put("user_id", SecurityUtils.CurrentUserName());
            List<Map<String, Object>> list = dashboardMapper.ds_getAlldata(map);
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
}
