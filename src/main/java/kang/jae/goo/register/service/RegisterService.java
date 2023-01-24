package kang.jae.goo.register.service;

import kang.jae.goo.config.utils.SecurityUtils;
import kang.jae.goo.register.mapper.RegisterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service

public class RegisterService {

    private final RegisterMapper registerMapper;

    public RegisterService(RegisterMapper registerMapper) {
        this.registerMapper = registerMapper;
    }

    public Map<String, Object> registerSave(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        try {
            map.put("user_id", SecurityUtils.CurrentUserName());
            registerMapper.InsertItem(map);
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
