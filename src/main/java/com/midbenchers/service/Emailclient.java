package com.midbenchers.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "emailservice")
public interface Emailclient {

    @PostMapping("/email/send")
     void sent(@RequestParam("to") String to,@RequestParam("subject") String subject ,@RequestParam("body") String body);

}
