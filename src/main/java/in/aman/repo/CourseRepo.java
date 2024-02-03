package in.aman.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.aman.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer> {

}
