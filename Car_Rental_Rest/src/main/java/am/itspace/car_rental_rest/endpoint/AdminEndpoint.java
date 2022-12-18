package am.itspace.car_rental_rest.endpoint;

import am.itspace.car_rental_common.entity.Role;
import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.service.UserService;
import am.itspace.car_rental_rest.dto.UpdateUserDto;
import am.itspace.car_rental_rest.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminEndpoint {
    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * get list of drivers
     */
    @GetMapping("/list/drivers")
    public ResponseEntity<List<User>> listOfDrivers() {
        return ResponseEntity.ok(userService.findAllByRole(Role.DRIVER));
    }

    /**
     * get list of dealers
     */

    @GetMapping("/list/dealers")
    public ResponseEntity<List<User>> listOfDealers() {
        return ResponseEntity.ok(userService.findAllByRole(Role.DEALER));
    }

    /**
     * get list of clients
     */

    @GetMapping("/list/clients")
    public ResponseEntity<List<User>> listOfClients() {
        return ResponseEntity.ok(userService.findAllByRole(Role.CLIENT));
    }

    /**
     * delete user by id
     */

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable(name = "id") int id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * update user
     */

    @PatchMapping("/user/change/{id}")
    public ResponseEntity<User> changeUserById(@PathVariable(name = "id") int id, @RequestBody UpdateUserDto updateUserDto) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            userMapper.update(updateUserDto, user);
            userService.update(user);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }
}
