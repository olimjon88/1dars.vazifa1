package uz.pdp.vazifa1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.vazifa1.entity.Worker;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {

    Optional<Worker> findByIdAndStatusTrue(Integer id);

    List<Worker> findAllByDepartmentId(Integer department_id);

    List<Worker> findAllByStatusTrueAndDepartment_CompanyId(Integer company_id);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);

}
