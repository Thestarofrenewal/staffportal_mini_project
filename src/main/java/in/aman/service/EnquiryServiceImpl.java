package in.aman.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.aman.binding.DashboardResponse;
import in.aman.binding.EnquiryForm;
import in.aman.entity.CourseEntity;
import in.aman.entity.EnqStatusEntity;
import in.aman.entity.StudentEnqEntity;
import in.aman.entity.UserDtlsEntity;
import in.aman.repo.CourseRepo;
import in.aman.repo.EnqStatusRepo;
import in.aman.repo.StudentEnqRepo;
import in.aman.repo.UserDtlsRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	CourseRepo courseRepo;
	
	@Autowired
	EnqStatusRepo statusRepo;
	
	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private StudentEnqRepo enqRepo;
	
	@Autowired
	private HttpSession session;

	@Override
	public DashboardResponse getDashboardData(Integer userId) {

		DashboardResponse response = new DashboardResponse();
		
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);

		if (findById.isPresent()) {
			UserDtlsEntity userEntity = findById.get();

			List<StudentEnqEntity> enquiries = userEntity.getEnquiries();

			Integer totalCnt = enquiries.size();

			Integer enrolledCnt = enquiries.stream().filter(e -> e.getEnqStatus().equals("Enrolled")).collect(Collectors.toList()).size();
			
			Integer lostCnt = enquiries.stream().filter(e->e.getEnqStatus().equals("Lost")).collect(Collectors.toList()).size();
		
			response.setTotalEnqyiryCnt(totalCnt);
			
			response.setEnrolledCnt(enrolledCnt);
			
			response.setLostCnt(lostCnt);
			
		}

		return response;
	}

	@Override
	public List<String> getCoures() {
		
		List<CourseEntity> findAll = courseRepo.findAll();
		
		List<String> names = new ArrayList<>();
		
		for (CourseEntity entity : findAll) {
			
			names.add(entity.getCourseName());
		}
		
		return names;
	}
	
	@Override
	public List<String> getEnqStatuses() {

		List<EnqStatusEntity> findAll = statusRepo.findAll();
		 
		List<String> statusList = new ArrayList<>();
		
		for (EnqStatusEntity entity : findAll) {
			
			statusList.add(entity.getStatusName());			
		}
		
		return statusList;
	}
	
	@Override
	public boolean saveEnquiry(EnquiryForm form) {

		StudentEnqEntity enqEntity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, enqEntity);
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		UserDtlsEntity userEntity = userDtlsRepo.findById(userId).get();
		
		enqEntity.setUser(userEntity);
		
		enqRepo.save(enqEntity);
		
		return true;
	}
}
