package org.cloudme.webgallery;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {
    @RequestMapping(value = "/photos/test", method = RequestMethod.POST)
    public String get(String name, Model model) {
        model.addAttribute("name", name);
        return "test";
    }
}
