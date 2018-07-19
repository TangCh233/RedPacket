package com.demo.controller;


import com.demo.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenfeixiang
 * @since 2018-07-05
 */
@Controller
@RequestMapping("/userRedPacket")
public class UserRedPacketController {

    @Autowired
    private UserRedPacketService userRedPacketService;

    @RequestMapping("/grapRedPacketByRedis")
    @ResponseBody
    public Map<String, Object> grepRedPacketByRedis(Integer redPacketId, Integer userId) {
        // 抢红包
        int result = userRedPacketService.grapRedPacketByRedis(redPacketId, userId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        boolean flag = result > 0;
        resultMap.put("success", flag);
        resultMap.put("message", flag?"抢红包成功":"抢红包失败");
        return resultMap;
    }

    @RequestMapping("/grapRedPacketForVersion")
    @ResponseBody
    public Map<String, Object> grepRedPacketForVersion(Integer redPacketId, Integer userId) {
        // 抢红包
        int result = userRedPacketService.grapRedPacketForVersion(redPacketId, userId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        boolean flag = result > 0;
        resultMap.put("success", flag);
        resultMap.put("message", flag?"抢红包成功":"抢红包失败");
        return resultMap;
    }

    @RequestMapping("/grapRedPacket")
    @ResponseBody
    public Map<String, Object> grepRedPacket(Integer redPacketId, Integer userId) {
        // 抢红包
        int result = userRedPacketService.grapRedPacket(redPacketId, userId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        boolean flag = result > 0;
        resultMap.put("success", flag);
        resultMap.put("message", flag?"抢红包成功":"抢红包失败");
        return resultMap;
    }

}

