package uz.pdp.vazifa1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.vazifa1.entity.Department;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    List<Department> findAllByCompanyId(Integer company_id);

    boolean existsByNameAndCompanyId(String name, Integer company_id);

    boolean existsByNameAndCompanyIdAndIdNot(String name, Integer company_id, Integer id);
}
