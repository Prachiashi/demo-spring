package com.example.demospring;

import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class TODOController {

  @Autowired
  private TODOService todoService;

  public TODOController(){
    System.out.println("2222");
  }


  @GetMapping("/hello") @ResponseBody
  public String hello() {
    return "Hello, world!";
  }

  @GetMapping("/getTODOs")
  @ResponseBody
  public ResponseEntity<List<TODO>> getTODOs() {
    try {
      List<TODO> todos = todoService.getAllTodos();
      return ResponseEntity.ok(todos);
    } catch (SQLException e) {
      // Log the exception and return a proper error response
      System.err.println("Error fetching TODOs: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
    }
  }

  @PostMapping(value="/addTODO", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TODO> addTODO(@RequestBody TODO newTodo) throws ServerException {
    TODO todo = todoService.add(newTodo);


    
    if (todo == null) {
      throw new ServerException("Invalid Entry");
    } else {
      ResponseEntity<TODO> re = ResponseEntity.status(HttpStatus.CREATED).body(todo);
      return re;
    }
  }

  @PostMapping(value="/updateTODO", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> updateTODO(@RequestBody TODO newTodo) throws ServerException {
    Boolean isUpdated = todoService.updateTODO(newTodo);
    ResponseEntity<Boolean> re = (isUpdated ?
            ResponseEntity.status(HttpStatus.CREATED).body(isUpdated) :
            ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(isUpdated));
    return re;
  }
  @DeleteMapping(value = "/deleteTODO", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> deleteTODO(@RequestBody TODO todo) {
    boolean isDeleted = todoService.delete(todo.getDescription());
    return isDeleted
            ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(true)
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
  }
}
