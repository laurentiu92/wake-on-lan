package com.wake.on.lan.controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.wake.on.lan.model.WakeRequestBody;
import com.wake.on.lan.service.WakeOnLanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;


@RestController
@RequestMapping("/pc")
public class WakeOnLanController {

    @Autowired
    private WakeOnLanService wakeOnLanService;

    @PostMapping(value = "/wake" ,produces = "application/json")
    public ResponseEntity<String> pcWake(@RequestBody WakeRequestBody body){
        return wakeOnLanService.pcWake(body);
    }

    @PostMapping(value = "/heartbeat" , produces = "application/json")
    public ResponseEntity<String> pcHeartBeat(@RequestBody WakeRequestBody body) {
        return wakeOnLanService.pcHeartbeat(body);
    }
    
}
