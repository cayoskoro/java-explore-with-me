package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.NewUserRequest;
import ru.practicum.dto.UserDto;
import ru.practicum.service.UserService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class UserAdminController {
    private final UserService userService;

    @GetMapping
    public Collection<UserDto> getAllUsers(@RequestParam(required = false) Collection<Long> ids,
                                           @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                           @RequestParam(defaultValue = "10") @Positive int size) {
        return null;
    }

    @PostMapping
    public UserDto addNewUser(@RequestBody NewUserRequest newUserRequest) {
        return null;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
    }
}
