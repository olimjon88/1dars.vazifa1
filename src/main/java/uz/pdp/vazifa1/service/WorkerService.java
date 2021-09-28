package uz.pdp.vazifa1.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.vazifa1.dto.ResponseStatus;
import uz.pdp.vazifa1.dto.WorkerDto;
import uz.pdp.vazifa1.entity.Address;
import uz.pdp.vazifa1.entity.Department;
import uz.pdp.vazifa1.entity.Worker;
import uz.pdp.vazifa1.repository.AddressRepository;
import uz.pdp.vazifa1.repository.DepartmentRepository;
import uz.pdp.vazifa1.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    private final DepartmentRepository departmentRepository;

    private final WorkerRepository workerRepository;

    private final AddressRepository addressRepository;

    public WorkerService(DepartmentRepository departmentRepository, WorkerRepository workerRepository, AddressRepository addressRepository) {
        this.departmentRepository = departmentRepository;
        this.workerRepository = workerRepository;
        this.addressRepository = addressRepository;
    }

    public ResponseEntity<List<Worker>> get() {
        return ResponseEntity.ok(workerRepository.findAll());
    }

    public ResponseEntity<Worker> get(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<Worker>> getByDepartment(Integer departmentId) {
        return ResponseEntity.ok(workerRepository.findAllByDepartmentId(departmentId));
    }

    public ResponseEntity<List<Worker>> getByCompany(Integer companyId) {
        return ResponseEntity.ok(workerRepository.findAllByStatusTrueAndDepartment_CompanyId(companyId));
    }

    public ResponseEntity<ResponseStatus> add(WorkerDto dto) {
        if (workerRepository.existsByPhoneNumber(dto.getPhoneNumber()))
            return new ResponseEntity<>(new ResponseStatus("This worker has already existed", false), HttpStatus.CONFLICT);

        Optional<Department> optionalDepartment = departmentRepository.findById(dto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ResponseEntity<>(new ResponseStatus("Department not found", false), HttpStatus.NOT_FOUND);

        Department department = optionalDepartment.get();
        Address address = addressRepository.save(Address.builder().homeNumber(dto.getHomeNumber()).status(true).street(dto.getStreet()).build());

        workerRepository.save(Worker.builder()
                .department(department)
                .address(address)
                .status(true)
                .phoneNumber(dto.getPhoneNumber())
                .name(dto.getName()).build());

        return ResponseEntity.ok(new ResponseStatus("Worker created", true));
    }

    public ResponseEntity<ResponseStatus> edit(Integer id, WorkerDto dto) {
        if (workerRepository.existsByPhoneNumberAndIdNot(dto.getPhoneNumber(), id))
            return new ResponseEntity<>(new ResponseStatus("This worker has already existed", false), HttpStatus.CONFLICT);

        Optional<Worker> optionalWorker = workerRepository.findByIdAndStatusTrue(id);
        if (!optionalWorker.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Worker worker = optionalWorker.get();
        Optional<Department> optionalDepartment = departmentRepository.findById(dto.getDepartmentId());
        if (optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();

            Address address = worker.getAddress();
            address.setHomeNumber(dto.getHomeNumber());
            address.setStreet(dto.getStreet());
            addressRepository.save(address);

            worker.setAddress(address);
            worker.setDepartment(department);
            worker.setName(dto.getName());
            worker.setPhoneNumber(dto.getPhoneNumber());
            workerRepository.save(worker);

            return ResponseEntity.ok(new ResponseStatus("Worker edited", true));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ResponseStatus> delete(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findByIdAndStatusTrue(id);
        if (optionalWorker.isPresent()) {
            Worker worker = optionalWorker.get();

            Address address = worker.getAddress();
            address.setStatus(false);
            addressRepository.save(address);

            worker.setStatus(false);
            workerRepository.save(worker);
            return ResponseEntity.ok(new ResponseStatus("Worker deleted", true));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
