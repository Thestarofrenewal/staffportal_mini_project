package in.aman.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.aman.binding.LoginForm;
import in.aman.binding.SignUpForm;
import in.aman.binding.UnlockForm;
import in.aman.entity.UserDtlsEntity;
import in.aman.repo.UserDtlsRepo;
import in.aman.util.EmailUtils;
import in.aman.util.PwdUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDtlsRepo userDtlsRepo;

	@Autowired
	EmailUtils emailUtils;
	
	@Autowired
	private HttpSession session;

	@Override
	public String login(LoginForm form) {

		UserDtlsEntity entity = userDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());

		if (entity == null) {
			return "Invalid Credentials";
		}

		if (entity.getAccStatus().equals("LOCKED")) {
			return "Your Account Locked";
		}

		session.setAttribute("userId", entity.getUserId());
		return "success";
	}

	@Override
	public boolean unlockAccount(UnlockForm form) {

		UserDtlsEntity entity = userDtlsRepo.findByEmail(form.getEmail());

		if (entity.getPwd().equals(form.getTempPwd())) {

			entity.setPwd(form.getNewPwd());
			entity.setAccStatus("UnLocked");
			userDtlsRepo.save(entity);

			return true;
		}
		return false;
	}

	@Override
	public boolean signUp(SignUpForm form) {


		UserDtlsEntity user = userDtlsRepo.findByEmail(form.getEmail());

		if (user != null) {
			return false;
		}
		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);

		String tempPwd = PwdUtils.generateRandomPwd();

		entity.setPwd(tempPwd);

		entity.setAccStatus("LOCKED");

		userDtlsRepo.save(entity);

		String to = form.getEmail();

		String subject = "Unloack your Account ! Ashok IT";

		StringBuffer body = new StringBuffer("");
		body.append("<h1>Use below temporary pwd to unlock your account</h1>");
		body.append("Temporary pwd : " + tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\">Click here to unlock your account</a>");

		emailUtils.sendEmail(to, subject, body.toString());

		return true;
	}

	@Override
	public boolean forgotPwd(String email) {
		
		UserDtlsEntity status = userDtlsRepo.findByEmail(email);
		
		if(status == null) {
			
			return false;
		}
		
		String subject = "Recover Password";
		
		String body = "Your pwd :: "+ status.getPwd();
		
		emailUtils.sendEmail(email, subject, body);
		
		return true;
	}
}
