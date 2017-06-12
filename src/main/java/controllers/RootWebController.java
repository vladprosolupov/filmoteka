package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Rostyk on 12.06.2017.
 */
@RestController
public class RootWebController {

    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }

}
