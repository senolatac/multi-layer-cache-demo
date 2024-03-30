package com.example.multilayercachedemo.controller;

import com.example.multilayercachedemo.service.MathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MathController {
    private final MathService mathService;

    @GetMapping("/sum")
    public ResponseEntity<Integer> sum(@RequestParam(defaultValue = "0") Integer a, @RequestParam(defaultValue = "0") Integer b) {
        return ResponseEntity.ok(mathService.sum(a, b));
    }

    @GetMapping("/multiply")
    public ResponseEntity<Integer> multiply(@RequestParam(defaultValue = "0") Integer a, @RequestParam(defaultValue = "0") Integer b) {
        return ResponseEntity.ok(mathService.multiply(a, b));
    }

    @GetMapping("/subtract")
    public ResponseEntity<Integer> subtract(@RequestParam(defaultValue = "0") Integer a, @RequestParam(defaultValue = "0") Integer b) {
        return ResponseEntity.ok(mathService.subtract(a, b));
    }

    @GetMapping("/evict")
    public ResponseEntity<Void> evict(@RequestParam String key) {
        mathService.evictMathCache(key);
        return ResponseEntity.noContent().build();
    }
}
