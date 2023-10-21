package com.test.edu;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.test.edu.model.Item;
import com.test.edu.model.Restaurant;
import com.test.edu.model.RestaurantTable;
import com.test.edu.model.RestaurantUser;
import com.test.edu.model.User;

@Controller
public class LoginController {

	private RestTemplate restTemplate = new RestTemplate();

	@RequestMapping("/")
	public String startscreen() {
		return "startscrn";
	}

	@RequestMapping("/login")
	public String login(HttpServletRequest request) {
		boolean cookie_check = false;
		Cookie cookies[] = request.getCookies();
		String role = null;
		if (cookies == null) {
			return "login";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("role")) {
				if (cookie.getValue() != null) {
					role = cookie.getValue();
					cookie_check = true;
				}
			}
		}
		if (cookie_check == true) {
			if (role.equals("waiter"))
				return "waiter_dashboard";
			else if (role.equals("chef"))
				return "chef_dashboard";
			else if (role.equals("owner"))
				return "owner_dashboard";
			else
				return "location";
		} else
			return "login";
		// return "login";
	}

	@RequestMapping("/login2")
	public String login2() {
		return "login2";
	}

	@RequestMapping("/menu")
	public String menu(HttpServletRequest request) {
		boolean cookie_check = false;
		Cookie cookies[] = request.getCookies();
		String role = null;
		if (cookies == null) {
			return "login";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("role")) {
				if (cookie.getValue() != null) {
					role = cookie.getValue();
					cookie_check = true;
				}
			}
		}
		if (cookie_check == true) {
			if (role.equals("waiter"))
				return "waiter_dashboard";
			else if (role.equals("chef"))
				return "chef_dashboard";
			else if (role.equals("owner"))
				return "owner_dashboard";
			else
				return "menu";
		} else
			return "menu";
		// return "menu";
	}
	
	
	@RequestMapping(value="/bill")
	public String bill(HttpServletRequest request) {
		boolean cookie_check = false;
		Cookie cookies[] = request.getCookies();
		String role = null;
		if (cookies == null) {
			return "login";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("role")) {
				if (cookie.getValue() != null) {
					role = cookie.getValue();
					cookie_check = true;
				}
			}
		}
		if (cookie_check == true) {
			if (role.equals("waiter"))
				return "waiter_dashboard";
			else if (role.equals("chef"))
				return "chef_dashboard";
			else if (role.equals("owner"))
				return "owner_dashboard";
			else {
				
				return "bill";
			}
				
		} else
			return "login";
		// return "menu";
	}
	

	@RequestMapping(value = "/login-validate", method = RequestMethod.POST)
	public String login_validation(@RequestParam("mobile_no") long mobile_no, HttpServletRequest request,
			HttpServletResponse response) {
		User user = restTemplate.getForObject("http://localhost:8081/api/user/getuser/" + mobile_no, User.class);
		RestaurantUser r_user = restTemplate.getForObject(
				"http://localhost:8081/api/restaurentuser/getrestaurantuser/" + mobile_no, RestaurantUser.class);
		if (user != null) {
			Random random = new Random();
			String otp = String.format("%04d", random.nextInt(10000));
			System.out.println(otp);
			HttpSession session = request.getSession();
			session.setAttribute("otp", otp);
			session.setAttribute("role", user.getRole());
			return "otp";
		} else if (r_user != null) {
			Cookie cookie = new Cookie("role", r_user.getRole());
			response.addCookie(cookie);
			Random random = new Random();
			String otp = String.format("%04d", random.nextInt(10000));
			System.out.println(otp);
			HttpSession session = request.getSession();
			session.setAttribute("otp", otp);
			session.setAttribute("role", r_user.getRole());
			session.setAttribute("mobile", r_user.getMobile_no());
			return "otp";
		} else
			return "login2";
	}

	@RequestMapping(value = "/validate-otp", method = RequestMethod.POST)
	public String otp(@RequestParam("otp1") int otp1, @RequestParam("otp2") int otp2, @RequestParam("otp3") int otp3,
			@RequestParam("otp4") int otp4, HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String otp_ = (String) session.getAttribute("otp");
		String entered_otp = String.valueOf(otp1) + String.valueOf(otp2) + String.valueOf(otp3) + String.valueOf(otp4);
		if (entered_otp.equals(otp_)) {
			String role=(String)session.getAttribute("role");
			Cookie cookie1 = new Cookie("role", role);
			response.addCookie(cookie1);
			session.removeAttribute("otp");
			//System.out.println(mobile_no);
			// String role=(String) session.getAttribute("role");
			if (role.equals("user")) {
				response.sendRedirect("location");
				return "location";
			}
				
			else if (role.equals("waiter")) {
				long mobile_no=(long)session.getAttribute("mobile");
				RestaurantUser r_user=restTemplate.getForObject(
						"http://localhost:8081/api/restaurentuser/logged_in/" + mobile_no, RestaurantUser.class);
				System.out.println(r_user.getIsLoggedin());
				response.sendRedirect("waiter_dashboard");
				return "waiter_dashboard";
			}
			else if (role.equals("chef")) {
				response.sendRedirect("chef_dashboard");
				return "chef_dashboard";
			}
				
			else if (role.equals("owner")) {
				response.sendRedirect("owner_dashboard");
				return "owner_dashboard";
			}
				
		}
		response.sendRedirect("login");
		return "login";
	}
	
	@RequestMapping("/location")
	public String location() {
		return "location";
	}
	
	@RequestMapping("/owner_dashboard")
	public String owner_dashboard() {
		return "owner_dashboard";
	}
	
	@RequestMapping("/chef_dashboard")
	public String chef_dashboard() {
		return "chef_dashboard";
	}
	
	@RequestMapping("/waiter_dashboard")
	public String waiter_dashboard() {
		return "waiter_dashboard";
	}
	

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@RequestParam("mobile_no") long mobile_no, @RequestParam("fname") String fname,
			@RequestParam("lname") String lname, HttpServletRequest request) {
		Random random = new Random();
		String otp = String.format("%04d", random.nextInt(10000));
		System.out.println(otp);
		HttpSession session = request.getSession();
		session.setAttribute("otp", otp);
		session.setAttribute("fname", fname);
		session.setAttribute("lname", lname);
		session.setAttribute("mobile", mobile_no);
		return "otp2";
	}

	@RequestMapping(value = "/register-validate-otp", method = RequestMethod.POST)
	public String register_otp(@RequestParam("otp1") int otp1, @RequestParam("otp2") int otp2,
			@RequestParam("otp3") int otp3, @RequestParam("otp4") int otp4, HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		String fname = (String) session.getAttribute("fname");
		String lname = (String) session.getAttribute("lname");
		long mobile_no = (long) session.getAttribute("mobile");
		String otp_ = (String) session.getAttribute("otp");
		String entered_otp = String.valueOf(otp1) + String.valueOf(otp2) + String.valueOf(otp3) + String.valueOf(otp4);
		if (entered_otp.equals(otp_)) {
			try {
				restTemplate.getForObject(
						"http://localhost:8081/api/user/createuser/" + fname + "/" + lname + "/" + mobile_no,
						User.class);
				session.removeAttribute("otp");
				response.sendRedirect("location");
				return "location";
			} catch (Exception e) {
				return "otp2";
			}
		}
		return "otp2";
	}

	@RequestMapping(value = "/restaurant", method = RequestMethod.POST)
	public String getRestaurent(@RequestParam("restaurent_id") int restaurent_id, HttpServletRequest request) {
		ResponseEntity<Restaurant> res = restTemplate.exchange(
				"http://localhost:8081/api/restaurant/getrestaurant/" + restaurent_id, HttpMethod.GET, null,
				new ParameterizedTypeReference<Restaurant>() {
				});
		Restaurant restaurant = res.getBody();
		if (restaurant != null) {
			HttpSession session = request.getSession();
			session.setAttribute("res_id", restaurent_id);
			return "table-number";
		} else
			return "location";
	}

	@RequestMapping(value = "/menu-1", method = RequestMethod.POST)
	public String reservetable(@RequestParam("table_no") int table_no, HttpServletRequest request) {
		System.out.println("Here");
		HttpSession session = request.getSession();
		int res_id = (int) session.getAttribute("res_id");
		System.out.println(res_id);
		//System.out.println(table_no);
		ResponseEntity<RestaurantTable> res = restTemplate.exchange(
				"http://localhost:8081/api/tables/reservetable/" + res_id + "/" + table_no, HttpMethod.GET, null,
				new ParameterizedTypeReference<RestaurantTable>() {
				});
		RestaurantTable table = res.getBody();
		// System.out.println(table.getTable_no());
		session.setAttribute("table", table);
		return "menu";
	}

}
