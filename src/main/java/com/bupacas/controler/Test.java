package com.bupacas.controler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
@RestController
@RequestMapping("/Bupacas/api")
public class Test {
    @RequestMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("OKiss");
    }

    @RequestMapping("/holaMundo")
    public ResponseEntity<String> holaMundo(){
        return ResponseEntity.ok("Hi Sky :)");
    }

    @RequestMapping("/random")
    public ResponseEntity<Integer> random(){
        Random random = new Random();
        int num_random = random.nextInt(100);
        return ResponseEntity.ok(num_random);
    }
}
