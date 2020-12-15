package com.wake.on.lan.controller;

import com.wake.on.lan.model.WakeRequestBody;
import com.wake.on.lan.service.WakeOnLanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pc/wake")
public class WakeOnLanController {

    @Autowired
    private WakeOnLanService wakeOnLanService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<String> pcWake(@RequestBody WakeRequestBody body){
        return wakeOnLanService.pcWake(body);
    }
}
