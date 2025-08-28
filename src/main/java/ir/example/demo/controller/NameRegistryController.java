package ir.example.demo.controller;

import ir.example.demo.dto.RegisterNameDto;
import ir.example.demo.dto.filter.NameRegistryFilterDto;
import ir.example.demo.model.NameRegistry;
import ir.example.demo.service.NameRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/name-registries")
@AllArgsConstructor
public class NameRegistryController {
    private final NameRegistryService nameRegistryService;

    @PostMapping
    public ResponseEntity<NameRegistry> addName(@RequestBody RegisterNameDto nameDto) {
        return ResponseEntity.ok(nameRegistryService.addName(nameDto));
    }

    @PostMapping("/list")
    public ResponseEntity<List<NameRegistry>> addNames(@RequestBody List<RegisterNameDto> nameDtoList) {
        return ResponseEntity.ok(nameRegistryService.addNames(nameDtoList));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NameRegistry> updateName(@PathVariable Long id, @RequestBody RegisterNameDto nameDto) {
        return ResponseEntity.ok(nameRegistryService.updateName(id, nameDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeName(@PathVariable Long id) {
        nameRegistryService.removeName(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NameRegistry> getName(@PathVariable Long id) {
        return ResponseEntity.ok(nameRegistryService.getName(id));
    }

    /***
     * NOTE: Use @RequestBody whit @PostMapping instead of @PathVariable by @GetMapping because the filter contains complex objects,
     * but in other cases if we don't have any complex objects we should use @PathVariable by @GetMapping
     */
    @PostMapping("/filter/list")
    public ResponseEntity<List<NameRegistry>> getNames(@RequestBody NameRegistryFilterDto filterDto) {
        return ResponseEntity.ok(nameRegistryService.getNames(filterDto));
    }


    @PostMapping("/filter/page")
    public ResponseEntity<Page<NameRegistry>> getPaginatedNames(@RequestBody NameRegistryFilterDto filterDto) {
        return ResponseEntity.ok(nameRegistryService.getPaginatedNames(filterDto));
    }

}
