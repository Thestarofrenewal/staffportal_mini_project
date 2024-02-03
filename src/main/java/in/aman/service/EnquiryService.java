package in.aman.service;

import java.util.List;

import in.aman.binding.DashboardResponse;
import in.aman.binding.EnquiryForm;

public interface EnquiryService {
	
	public List<String> getCoures();
	
	public List<String> getEnqStatuses();
	
	public DashboardResponse getDashboardData(Integer userId);
	
	public boolean saveEnquiry(EnquiryForm form);
//	public String upsertEnquiry(EnquiryForm form);
//	
//	public List<EnquiryForm> getEnquiries(Integer userId, EnquirySearchCriteria criteria); 
//	
//	public EnquiryForm getEnquiry(Integer enqId);

}
