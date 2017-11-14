package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import web.exceptions.ParsingJsonToDaoException;
import web.model.ClientJSON;
import web.services.ClientService;

import java.util.Map;

@Controller
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired(required = true)
    private ClientService clientService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String addClient(@ModelAttribute("client") ClientJSON clientJSON) {
        try {
            clientService.saveOrUpdate(clientService.convertToClientDb(clientJSON));
        } catch (ParsingJsonToDaoException e) {
            e.printStackTrace();
        }

        return "OK";
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String deleteClient(@PathVariable("id") String id) {
        clientService.delete(id);
        return "OK";
    }


}
