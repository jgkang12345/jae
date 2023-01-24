package kang.jae.goo.management.controller;

import kang.jae.goo.management.dto.Excel_Upload_DTO;
import kang.jae.goo.management.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @GetMapping("/selectItemSetting")
    public Map<String, Object> selectItemSetting() {
        return managementService.selectItemSetting();
    }

    @PostMapping("/mg_excelDownload")
    public void excelDownload(@RequestBody Map<String, Object> map, HttpServletResponse response) throws IOException {
        managementService.excelDownload(map, response);
    }

    @PostMapping("/mg_excelUpload")
    public Map<String, Object> mg_excelUpload(@ModelAttribute Excel_Upload_DTO dto) {
        return managementService.mg_excelUpload(dto);
    }
}
