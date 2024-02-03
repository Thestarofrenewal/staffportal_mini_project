package in.aman.service;

import in.aman.binding.LoginForm;
import in.aman.binding.SignUpForm;
import in.aman.binding.UnlockForm;

public interface UserService {
	
	public String login(LoginForm form);
	
	public boolean signUp(SignUpForm form);
	
	public boolean unlockAccount(UnlockForm form);
	
	public boolean forgotPwd(String email);

}
