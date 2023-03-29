package com.cfcons.moneymgr.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Main application controller
 */
@Controller
@AllArgsConstructor
@RequestMapping("/app")
public class AppController {

    /**
     * Return main application page
     *
     * @return Main application view
     */
    @GetMapping
    public String getApp() {
        return "app/main";
    }
}
