package am.itspace.car_rental_web.controller;

import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.entity.UserImage;
import am.itspace.car_rental_common.service.UserInfoService;
import am.itspace.car_rental_common.service.UserImageService;
import am.itspace.car_rental_common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserDetailController {

    private final UserService userService;
    private final UserImageService userImageService;
    private final UserInfoService userDetailService;
    @GetMapping("/user/details/{id}")
    public String showUserDetails(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<User> user = userService.findById(id);
        List<UserImage> allImagesByUserId = userImageService.findAllImagesByUserId(id);
        modelMap.addAttribute("images", allImagesByUserId);
        user.ifPresent(value -> modelMap.addAttribute("user", value));
        return "user-details";
    }

    @GetMapping(value = "/user/detail/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) {
        return userDetailService.getUserImage(fileName);
    }
}
