package com.dannymarsland.magnolia.handlebars.example.templates;

import com.dannymarsland.magnolia.handlebars.annotations.ParentTemplate;
import info.magnolia.module.blossom.annotation.Template;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Template(id = DetailsPageTemplate.ID, title = "Details Page")
@ParentTemplate(HomePageTemplate.class)
public class DetailsPageTemplate {

    public static final String ID = "magnolia-handlebars-example:pages/details-page";

    @RequestMapping("/details-page")
    public String render(Model model) {
        model.addAttribute("name", "World");
        return "details-page";
    }
}
