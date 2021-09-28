package uz.pdp.vazifa1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.vazifa1.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
