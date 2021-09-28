package uz.pdp.vazifa1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.vazifa1.dto.DepartmentDto;
import uz.pdp.vazifa1.dto.ResponseStatus;
import uz.pdp.vazifa1.entity.Company;
import uz.pdp.vazifa1.entity.Department;
import uz.pdp.vazifa1.repository.CompanyRepository;
import uz.pdp.vazifa1.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
  final
  DepartmentRepository departmentRepository;

  final
  CompanyRepository companyRepository;

    public DepartmentService(DepartmentRepository departmentRepository, CompanyRepository companyRepository) {
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
    }



    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentRepository.findAll());
    }

    public ResponseEntity<Department> getById(Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.map(ResponseEntity::ok).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<Department>> getByCompany(Integer companyId) {
        return ResponseEntity.ok(departmentRepository.findAllByCompanyId(companyId));
    }

    public ResponseEntity<ResponseStatus> addDepartment(DepartmentDto dto) {
        if (departmentRepository.existsByNameAndCompanyId(dto.getName(), dto.getCompanyId()))
            return new ResponseEntity<>(new ResponseStatus("This department is exist", false), HttpStatus.CONFLICT);

        Optional<Company> optionalCompany = companyRepository.findById(dto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ResponseEntity<>(new ResponseStatus("This company was not found", false), HttpStatus.NOT_FOUND);

        Company company = optionalCompany.get();

        departmentRepository.save(Department.builder()
                .name(dto.getName())
                .status(true)
                .company(company).build());

        return ResponseEntity.ok(new ResponseStatus("Department created successfully", true));
    }


    public ResponseEntity<ResponseStatus> edit(Integer id, DepartmentDto dto) {
        if (departmentRepository.existsByNameAndCompanyIdAndIdNot(dto.getName(), dto.getCompanyId(), id))
            return new ResponseEntity<>(new ResponseStatus("This department has already existed", false), HttpStatus.CONFLICT);

        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();

            Optional<Company> optionalCompany = companyRepository.findById(dto.getCompanyId());
            if (optionalCompany.isPresent()) {
                Company company = optionalCompany.get();

                department.setCompany(company);
                department.setName(dto.getName());
                departmentRepository.save(department);

                return ResponseEntity.ok(new ResponseStatus("Department edited", true));
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ResponseStatus> delete(Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new ResponseStatus("Department deleted", true));
    }
}
