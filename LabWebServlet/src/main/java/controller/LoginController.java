package controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import model.CustomerBean;
import model.CustomerService;

@Controller
@RequestMapping(path={"/secure/login.controller"})
@SessionAttributes(names={"user"})
public class LoginController {
	@Autowired
	private CustomerService customerService;
	@RequestMapping(method={RequestMethod.GET, RequestMethod.POST})
	public String get(String username, String password, Model model) {
	
//驗證資料
		Map<String, String> errors = new HashMap<String, String>();
		model.addAttribute("errors", errors);

		if(username==null || username.length()==0) {
			errors.put("username", "ID是必要欄位(mvc)");
		}
		if(password==null || password.length()==0) {
			errors.put("password", "PWD是必要欄位(mvc)");
		}
		if(errors!=null && !errors.isEmpty()) {
			return "login.error";
		}
		
//呼叫Model
		CustomerBean bean = customerService.login(username, password);

//根據Model執行結果，決定需要顯示的View元件
		if(bean==null) {
			errors.put("username", "登入失敗(MVC)");
			return "login.error";
		} else {
			model.addAttribute("user", bean);
			return "login.success";
		}
	}
}
