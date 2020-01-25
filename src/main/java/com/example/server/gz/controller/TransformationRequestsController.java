package com.example.server.gz.controller;

import com.example.server.gz.service.GZTraCServerHandler;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/requests/")
public class TransformationRequestsController {

  private final String host = "";

  @GetMapping("/{id}/{message}")
  @ResponseBody
  @ResponseStatus(HttpStatus.ACCEPTED)
  public String newRequest(@PathVariable String message, @PathVariable String id) {
    GZTraCServerHandler.sendNewMessage(message, id);
    return host + "/requests/" + id + "/status";
  }

  @GetMapping("/{id}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<Byte[]> getRequestResult(@PathVariable String id) {
    return GZTraCServerHandler.requestsResultMap.get(id);
  }

  @GetMapping("/{id}/status")
  @ResponseBody
  @ResponseStatus(HttpStatus.SEE_OTHER)
  public ResponseEntity<String> getRequestStatus(@PathVariable String id) {
    List<Byte[]> bytes = GZTraCServerHandler.requestsResultMap.get(id);
    if (bytes != null) {
      if (bytes.size() > 2) {
        return new ResponseEntity<>("/requests/" + id, HttpStatus.SEE_OTHER);
      }
    }
    return new ResponseEntity<String>("", HttpStatus.OK);
  }
}
