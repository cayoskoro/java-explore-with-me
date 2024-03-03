package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.dto.UpdateCompilationRequest;
import ru.practicum.service.CompilationService;

@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class CompilationAdminController {
    private final CompilationService compilationService;

    @PostMapping
    public CompilationDto addNewCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        return null;
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable long compId) {
    }

    @PatchMapping("/{compId}")
    public CompilationDto editCompilation(@PathVariable long compId,
                                          @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        return null;
    }
}
