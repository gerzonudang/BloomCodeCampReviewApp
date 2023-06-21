package com.hcc.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/error")
public class ErrorController {
    @RequestMapping("/")
    public ModelAndView handleError() {
        // Create a ModelAndView object and set the appropriate view name for your error page
        ModelAndView mav = new ModelAndView("errorPage");
        // You can also add additional attributes to the ModelAndView object if needed
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        // Create a ModelAndView object and set the appropriate view name for the exception handling page
        ModelAndView mav = new ModelAndView("exceptionPage");
        // You can also add additional attributes to the ModelAndView object if needed
        mav.addObject("exception", ex);
        return mav;
    }
}
