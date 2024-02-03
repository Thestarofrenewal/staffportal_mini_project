package in.aman.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.aman.entity.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer> {

}
