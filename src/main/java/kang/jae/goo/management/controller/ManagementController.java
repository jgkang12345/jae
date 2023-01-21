package kang.jae.goo.management.controller;

import kang.jae.goo.management.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/management")
@RestController
public class ManagementController {

    private final ManagementService managementService;

    public ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @PostMapping("/doSearch")
    public Map<String, Object> doSearch(@RequestBody Map<String, Object> map) {
        return managementService.doSearch(map);
    }

    @PostMapping("/deleteItem")
    public Map<String, Object> deleteItem(@RequestBody Map<String, Object> map) {
        return managementService.deleteItem(map);
    }

    @PostMapping("/updateItem")
    public Map<String, Object> updateItem(@RequestBody Map<String, Object> map) {
        return managementService.updateItem(map);
    }
}
