package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/user/{id}/photo")
    public ResponseEntity<byte[]> getAuthorPhoto(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null || user.getPicture() == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] data = user.getPicture().getData();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // o rileva dinamicamente
                .body(data);
    }
}
