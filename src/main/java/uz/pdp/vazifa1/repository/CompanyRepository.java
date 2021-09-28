package uz.pdp.vazifa1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.vazifa1.entity.Company;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByCorpName(String corpName);

    boolean existsByCorpNameAndIdNot(String corpName, Integer id);
}
