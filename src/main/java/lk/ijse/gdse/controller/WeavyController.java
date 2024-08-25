package lk.ijse.gdse.controller;

import lk.ijse.gdse.dto.UserDTO;
import lk.ijse.gdse.service.WeavyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Amil Srinath
 */
@RestController
@RequestMapping("/api/weavy")
public class WeavyController {
    @Autowired
    private WeavyService weavyService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody UserDTO userDTO) throws IOException {
        String result = weavyService.save(userDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<String> getUser(@PathVariable("id") String id) throws IOException {
        String result = weavyService.getUser(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) throws IOException {
        String result = weavyService.delete(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable String uid, @RequestBody UserDTO userDTO) throws IOException {
        String result = weavyService.update(uid, userDTO);
        return ResponseEntity.ok(result);
    }
}
