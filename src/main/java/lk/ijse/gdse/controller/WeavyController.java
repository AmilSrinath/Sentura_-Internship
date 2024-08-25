package lk.ijse.gdse.controller;

import lk.ijse.gdse.dto.UserDTO;
import lk.ijse.gdse.service.WeavyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
