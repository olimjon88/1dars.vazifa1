package uz.pdp.vazifa1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.vazifa1.dto.ResponseStatus;
import uz.pdp.vazifa1.dto.WorkerDto;
import uz.pdp.vazifa1.entity.Worker;
import uz.pdp.vazifa1.service.WorkerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping
    public ResponseEntity<List<Worker>> get(){
        return workerService.get();
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Worker>> getByDepartment(@PathVariable Integer departmentId){
        return workerService.getByDepartment(departmentId);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Worker>> getByCompany(@PathVariable Integer companyId){
        return workerService.getByCompany(companyId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Worker> get(@PathVariable Integer id){
        return workerService.get(id);
    }

    @PostMapping
    public ResponseEntity<ResponseStatus> add(@Valid @RequestBody WorkerDto dto){
        return workerService.add(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStatus> edit(@Valid @PathVariable Integer id, @RequestBody WorkerDto dto){
        return workerService.edit(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStatus> delete(@PathVariable Integer id){
        return workerService.delete(id);
    }


}
