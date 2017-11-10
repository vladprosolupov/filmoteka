package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.model.ClientJSON;
import web.services.ClientService;

@Controller
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired(required = true)
    private ClientService clientService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody String addClient(@RequestBody ClientJSON clientJSON) {
        clientService.saveOrUpdate(clientService.convertToClientDb(clientJSON));
        return "OK";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String deleteClient(@PathVariable("id") String id) {
        clientService.delete(id);
        return "OK";
    }


}
