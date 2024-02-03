package in.aman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.aman.binding.LoginForm;
import in.aman.binding.SignUpForm;
import in.aman.binding.UnlockForm;
import in.aman.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/signup")
	public String signUp(Model model) {

		model.addAttribute("user", new SignUpForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form, Model model) {

		boolean status = userService.signUp(form);

		if (status) {
			model.addAttribute("succMsg", "Account created, Check Your Email");

		} else {
			model.addAttribute("errMsg", "Choose Unique Email");
		}

		return "signUp";
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {

		UnlockForm unlockFormObj = new UnlockForm();

		unlockFormObj.setEmail(email);

		model.addAttribute("unlock", unlockFormObj);

		return "unlock";
	}

	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock, Model model) {

		System.out.println(unlock);
		if (unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			boolean status = userService.unlockAccount(unlock);

			if (status) {
				model.addAttribute("succMsg", "Account Unlocked Successfully");

			} else {
				model.addAttribute("errMsg", "Incorrect Temporary Pwd");
			}

		} else {
			model.addAttribute("errMsg", "New pwd & Confirm pwd no matching");
		}
		return "unlock";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {

		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {

		String status = userService.login(loginForm);

		if (status.contains("success")) {

			return "redirect:/dashboard";
		}
		model.addAttribute("errMsg", status);

		return "login";
	}

	@GetMapping("/forgot")
	public String forgotPwdPage() {
		return "forgotPwd";
	} 
	
	@PostMapping("/forgot")
	public String forgotPwd(@RequestParam("email") String email, Model model) {
		
		boolean status = userService.forgotPwd(email);
		
		if (status) {
			model.addAttribute("succMsg", "Pwd sent to your email");
		} else {
			model.addAttribute("errMsg", "Invalid Email");
		}	
		return "forgotPwd";
	}
}
