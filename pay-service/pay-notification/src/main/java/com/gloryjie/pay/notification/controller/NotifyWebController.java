/* ------------------------------------------------------------------
 *   Product:      pay
 *   Module Name:  COMMON
 *   Package Name: com.gloryjie.pay.notification.controller
 *   Date Created: 2019/2/7
 * ------------------------------------------------------------------
 * Modification History
 * DATE            Name           Contact
 * ------------------------------------------------------------------
 * 2019/2/7      Jie            GloryJie@163.com
 */
package com.gloryjie.pay.notification.controller;

import com.gloryjie.pay.notification.dto.EventSubscriptionDto;
import com.gloryjie.pay.notification.enums.EventType;
import com.gloryjie.pay.notification.service.EventSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jie
 * @since
 */
@RestController
@RequestMapping("/web/app")
public class NotifyWebController {

    @Autowired
    private EventSubscriptionService subscriptionService;

    @GetMapping("/{appId}/notification")
    public Map<EventType, EventSubscriptionDto> getAllSubscribeEvent(@PathVariable("appId") Integer appId) {
        List<EventSubscriptionDto> dtoList = subscriptionService.queryAllSubscribeEvent(appId);
        return dtoList.stream().collect(Collectors.toMap(EventSubscriptionDto::getEventType, item -> item));
    }

    @PostMapping("/{appId}/notification")
    public EventSubscriptionDto addEvent(@PathVariable("appId") Integer appId, @Valid @RequestBody EventSubscriptionDto dto) {
        dto.setAppId(appId);
        return subscriptionService.subscribeEvent(dto);
    }

    @DeleteMapping("/{appId}/notification/{eventType}")
    public Boolean cancelEvent(@PathVariable("appId") Integer appId, @PathVariable("eventType") EventType eventType) {
        return subscriptionService.cancelSubscribeEvent(appId, eventType);
    }


}
