package kang.jae.goo.dashboard.controller;

import kang.jae.goo.dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RequestMapping("/dashboard")
@RestController
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @PostMapping("/ds_getChartData")
    public Map<String, Object> ds_getChartData(@RequestBody Map<String, Object> map) {
        return dashboardService.ds_getChartData(map);
    }

    @PostMapping("/ds_getAlldata")
    public Map<String, Object> ds_getAlldata(@RequestBody Map<String, Object> map) {
        return dashboardService.ds_getAlldata(map);
    }
}
