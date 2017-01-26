package controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import misc.CustomPrimitiveNumberEditor;
import model.ProductBean;
import model.ProductService;

@Controller
@RequestMapping(path={"/pages/product.controller"})
public class ProductController {
	@Autowired
	private ProductService productService;
	@InitBinder
	public void registerCustomerEditor(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(java.util.Date.class,
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		webDataBinder.registerCustomEditor(int.class,
				new CustomPrimitiveNumberEditor(Integer.class, true));
		webDataBinder.registerCustomEditor(double.class,
				new CustomPrimitiveNumberEditor(Double.class, true));
	}
	@RequestMapping(method={RequestMethod.GET, RequestMethod.POST})
	public String processProductForm(String prodaction, ProductBean bean,
			BindingResult bindingResult, Model model) {
//接收資料
//驗證資料
		Map<String, String> errors = new HashMap<String, String>();
		model.addAttribute("errors", errors);
		
		if("Insert".equals(prodaction) || "Update".equals(prodaction) || "Delete".equals(prodaction)) {
			if(bean.getId()==0) {
				errors.put("id", "請輸入Id以便執行"+prodaction);
			}
		}

//轉換資料
		if(bindingResult!=null) {
			if(bindingResult.getFieldError("id") != null) {
				errors.put("id", "Id必須是整數");
			}
			if(bindingResult.getFieldError("price") != null) {
				errors.put("price", "Price必須是數字");
			}
			if(bindingResult.getFieldError("make") != null) {
				errors.put("make", "Make必須是符合yyyy-MM-dd格式的日期");
			}
			if(bindingResult.getFieldError("expire") != null) {
				errors.put("expire", "Expire必須是整數");
			}
		}
		
		if(errors!=null && !errors.isEmpty()) {
			return "product.error";
		}
		
//呼叫Model，根據Model執行結果，決定需要顯示的View元件
		if("Select".equals(prodaction)) {
			List<ProductBean> result = productService.select(bean);
			model.addAttribute("select", result);
			return "product.select";
		} else if("Insert".equals(prodaction)) {
			ProductBean result = productService.insert(bean);
			if(result==null) {
				errors.put("action", "Insert fail");
			} else {
				model.addAttribute("insert", result);
			}
			return "product.error";
		} else if("Update".equals(prodaction)) {
			ProductBean result = productService.update(bean);
			if(result==null) {
				errors.put("action", "Update fail");
			} else {
				model.addAttribute("update", result);
			}
			return "product.error";
		} else if("Delete".equals(prodaction)) {
			boolean result = productService.delete(bean);
			if(!result) {
				model.addAttribute("delete", 0);
			} else {
				model.addAttribute("delete", 1);
			}
			return "product.error";
		} else  {
			errors.put("action", "Unknown Action:"+prodaction);
			return "product.error";
		}
	}
}
