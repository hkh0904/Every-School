package com.everyschool.callservice.api.controller.FCM;


import com.everyschool.callservice.api.controller.FCM.request.FCMNotificationRequest;
import com.everyschool.callservice.api.service.FCM.FCMNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/call-service/v1/calls")
public class FCMNotificationController {
    private final FCMNotificationService fcmNotificationService;

    @PostMapping()
    public String sendNotificationByToken(@RequestBody FCMNotificationRequest request) {
        return fcmNotificationService.sendNotificationByToken(request);
    }
}