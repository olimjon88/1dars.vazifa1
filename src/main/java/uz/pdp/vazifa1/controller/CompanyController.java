package uz.pdp.vazifa1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.vazifa1.dto.CompanyDto;
import uz.pdp.vazifa1.dto.ResponseStatus;
import uz.pdp.vazifa1.entity.Company;
import uz.pdp.vazifa1.service.CompanyService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getCompanyInfo(){
        return companyService.getAllCompanyInfo();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getById(@PathVariable Integer id) {
        return companyService.getInfoById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseStatus> add (@Valid @RequestBody CompanyDto dto){
        return companyService.addCompany(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStatus> edit(@Valid @PathVariable Integer id, @RequestBody CompanyDto dto){
        return companyService.editCompany(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStatus> delete(@PathVariable Integer id){
        return companyService.delete(id);
    }






}
