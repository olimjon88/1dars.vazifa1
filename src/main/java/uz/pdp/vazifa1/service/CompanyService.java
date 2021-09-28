package uz.pdp.vazifa1.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.vazifa1.dto.CompanyDto;
import uz.pdp.vazifa1.dto.ResponseStatus;
import uz.pdp.vazifa1.entity.Address;
import uz.pdp.vazifa1.entity.Company;
import uz.pdp.vazifa1.repository.AddressRepository;
import uz.pdp.vazifa1.repository.CompanyRepository;
import uz.pdp.vazifa1.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    final
    CompanyRepository companyRepository;

    final
    AddressRepository addressRepository;

    final
    DepartmentRepository departmentRepository;

    public CompanyService(CompanyRepository companyRepository, AddressRepository addressRepository, DepartmentRepository departmentRepository) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
        this.departmentRepository = departmentRepository;
    }

    public ResponseEntity<List<Company>> getAllCompanyInfo() {
        return ResponseEntity.ok(companyRepository.findAll());
    }

    public ResponseEntity<Company> getInfoById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.map(ResponseEntity::ok).orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    public ResponseEntity<ResponseStatus> addCompany(CompanyDto dto) {
        if (companyRepository.existsByCorpName(dto.getCorpName()))
            return new ResponseEntity<>(new ResponseStatus("This company exists", false), HttpStatus.CONFLICT);

        Address address = addressRepository.save(Address.builder().homeNumber(dto.getHomeNumber()).street(dto.getStreet()).status(true).build());

        companyRepository.save(Company.builder()
                .corpName(dto.getCorpName())
                .address(address)
                .status(true)
                .directorName(dto.getDirectorName()).build());

        return ResponseEntity.ok(new ResponseStatus("Created new Company", true));
    }

    public ResponseEntity<ResponseStatus> editCompany(Integer id, CompanyDto dto) {
        if (companyRepository.existsByCorpNameAndIdNot(dto.getCorpName(), id))
            return new ResponseEntity<>(new ResponseStatus("This company exists", false), HttpStatus.CONFLICT);

        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()){
            Company company = optionalCompany.get();

            Address address = company.getAddress();
            address.setStreet(dto.getStreet());
            address.setHomeNumber(dto.getHomeNumber());
            addressRepository.save(address);

            company.setCorpName(dto.getCorpName());
            company.setDirectorName(dto.getDirectorName());
            companyRepository.save(company);

            return ResponseEntity.ok(new ResponseStatus("Company edited successfully", true));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ResponseStatus> delete(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        companyRepository.deleteById(id);
        return ResponseEntity.ok(new ResponseStatus("Company deleted", true));
    }
}
