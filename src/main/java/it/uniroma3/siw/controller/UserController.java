package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    @GetMapping("/student/myProfile")
    public String studentProfile(Model model) {
        User currentUser = (User) userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "student/studentProfile";
    }
    @GetMapping("/student/myProfile/editPicture")
    public String studentProfileEdit(Model model) {
        User currentUser = (User) userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "student/studentProfileEditPicture";
    }

    @PostMapping("/student/myProfile/editPicture")
    public String studentEditPicture(@RequestParam("profilePictureInput") MultipartFile inputImage, Model model) {
        User currentUser = (User) userService.getCurrentUser();
        updatePicture(inputImage, currentUser);
        return "redirect:/student/myProfile";
    }


    @GetMapping("/teacher/myProfile")
    public String teacherProfile(Model model) {
        User currentUser = (User) userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "teacher/teacherProfile";
    }
    @GetMapping("/teacher/myProfile/editPicture")
    public String teacherProfileEdit(Model model) {
        User currentUser = (User) userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "teacher/teacherProfileEditPicture";
    }
    @PostMapping("/teacher/myProfile/editPicture")
    public String teacherEditPicture(@RequestParam("profilePictureInput") MultipartFile inputImage, Model model) {
        User currentUser = (User) userService.getCurrentUser();
        updatePicture(inputImage, currentUser);
        return "redirect:/teacher/myProfile";
    }

    private void updatePicture(MultipartFile inputImage, User currentUser) {
        Image profilePicture = new Image();
        if(!inputImage.isEmpty()) {
            try {
                profilePicture.setData(inputImage.getBytes());
                currentUser.setPicture(profilePicture);
                userService.updateUserPicture(currentUser);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
