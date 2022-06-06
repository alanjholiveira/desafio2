package tech.dock.desafio.api.application.controller;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.net.URL;

@RestController
@ApiIgnore
public class HomeController {

    @Value("${spring.application.name}")
    private String appName;

    @Value("classpath:templates/home-with-actions.html")
    private Resource homeWithActionsTemplate;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String index() throws IOException {
        URL url = this.homeWithActionsTemplate.getURL();
        return Resources.toString(url, Charsets.UTF_8)
                .replace("$appName", this.appName);
    }
}
