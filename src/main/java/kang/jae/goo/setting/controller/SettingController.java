package kang.jae.goo.setting.controller;

import kang.jae.goo.setting.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RequestMapping("/setting")
@RestController
public class SettingController {
    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @PostMapping("/doSearch")
    public Map<String, Object> doSearch(@RequestBody Map<String, Object> map) {
        return settingService.doSearch(map);
    }

    @PostMapping("/doSave")
    public Map<String, Object> doSave(@RequestBody Map<String, Object> map) {
        return settingService.doSave(map);
    }

    @PostMapping("/doDelete")
    public Map<String, Object> doDelete(@RequestBody Map<String, Object> map) {
        return settingService.doDelete(map);
    }
}
