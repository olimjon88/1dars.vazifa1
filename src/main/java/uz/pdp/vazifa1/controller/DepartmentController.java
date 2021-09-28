package uz.pdp.vazifa1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.vazifa1.dto.DepartmentDto;
import uz.pdp.vazifa1.dto.ResponseStatus;
import uz.pdp.vazifa1.entity.Department;
import uz.pdp.vazifa1.service.DepartmentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<Department>> getDepartments(){
        return departmentService.getAllDepartments();
}

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Department>> getInfoByCompany(@PathVariable Integer companyId){
        return departmentService.getByCompany(companyId);
}

    @GetMapping("/{id}")
    public ResponseEntity<Department> get(@PathVariable Integer id){
        return departmentService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseStatus> add (@Valid @RequestBody DepartmentDto dto){
        return departmentService.addDepartment(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<uz.pdp.vazifa1.dto.ResponseStatus> edit(@Valid @PathVariable Integer id, @RequestBody DepartmentDto dto){
        return departmentService.edit(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStatus> delete(@PathVariable Integer id){
        return departmentService.delete(id);
    }





}
