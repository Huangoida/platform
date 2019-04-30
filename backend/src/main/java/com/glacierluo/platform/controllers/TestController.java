package com.glacierluo.platform.controllers;

import com.glacierluo.platform.classes.Json;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/pay")
    public Json pay(){
        return new Json("ok", 200);
    }

    @GetMapping("/free")
    public Json free(){
        return new Json("ok", 200);
    }
}
