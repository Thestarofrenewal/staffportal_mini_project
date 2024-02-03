package in.aman.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.aman.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer> {

}
