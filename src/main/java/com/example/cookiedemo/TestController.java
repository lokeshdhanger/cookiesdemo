package com.example.cookiedemo;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.util.datetime.FastDateFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(TestController.class);

	private static final FastDateFormat expiresDateFormat = FastDateFormat.getInstance("EEE, dd MMM yyyy HH:mm:ss zzz",
			TimeZone.getTimeZone("GMT"));

	@GetMapping("/make_test1")
	public void test1(Map<String, String> requestMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("hit");
		logger.info("request scheme: " + request.getScheme());
		logger.info("is request secure : " + request.isSecure());
		
		String requestUrl = request.getRequestURL().toString();
		logger.info("Request Url = {}", requestUrl);
		
		String requestUri = request.getRequestURL() + "?"+ request.getQueryString();
		logger.info("RequestURI = {}", requestUri);
		
		Cookie cookie = new Cookie("TestCookie", "TestValue");
		cookie.setMaxAge(9999999);
		cookie.setPath("/");
		cookie.setDomain("");
		cookie.setSecure(true);
		cookie.setHttpOnly(true);

		addCookie(response, cookie, "none");
		
		response.sendRedirect("https://pawan.gq/cookie_testing/landing.html");

	}

	@GetMapping("/read_1")
	public void readCookie1(Map<String, String> requestMap, HttpServletRequest request, HttpServletResponse response) {
		logger.info("readCookie1() hit");
		logger.info("request scheme: " + request.getScheme());
		logger.info("is request secure : " + request.isSecure());


		String requestUrl = request.getRequestURL().toString();
		logger.info("Request Url = {}", requestUrl);
		
		String requestUri = request.getRequestURL() + "?"+ request.getQueryString();
		logger.info("RequestURI = {}", requestUri);
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			logger.info("cookies present in request");
			for (Cookie cookie : cookies) {
				logger.info("cookie name: " + cookie.getName());
				logger.info("domain: " + cookie.getDomain());
				logger.info("is secure: " + cookie.getSecure());
				logger.info("value: " + cookie.getValue());
				logger.info("max-age: " + cookie.getMaxAge());
				logger.info("path: " + cookie.getPath());
			}
		}

		String header = request.getHeader(HttpHeaders.SET_COOKIE);
		if (header != null && header != "") {
			logger.info(HttpHeaders.SET_COOKIE + " present in request headers");
			logger.info(header);
		}

		String header2 = response.getHeader(HttpHeaders.SET_COOKIE);
		if (header2 != null && header2 != "") {
			logger.info(HttpHeaders.SET_COOKIE + " present in request headers");
			logger.info(header2);
		}
	}

	@GetMapping("/make_test2")
	public void test2(Map<String, String> requestMap, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("hit 2");
		logger.info("request scheme: " + request.getScheme());
		logger.info("is request secure : " + request.isSecure());


		String requestUrl = request.getRequestURL().toString();
		logger.info("Request Url = {}", requestUrl);
		
		String requestUri = request.getRequestURL() + "?"+ request.getQueryString();
		logger.info("RequestURI = {}", requestUri);
		
		ResponseCookieBuilder cookies = ResponseCookie.from("responseCookieKey", "responseCookieValue");
		cookies.httpOnly(true);
		cookies.secure(true);
		cookies.domain("");
		cookies.path("/");
		cookies.sameSite("none");
		cookies.maxAge(9999999);
		ResponseCookie cookie = cookies.build();
		logger.info(cookie.toString());
//		Runnable r = () -> {
//			try {
//				response.sendRedirect("https://pawan.gq/cookie_testing/landingpage.html");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		};
//		CompletableFuture.runAsync(r);
		
		ResponseEntity<Object> build = ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
		Set<Entry<String, List<String>>> entrySet = build.getHeaders().entrySet();		
		for (Entry<String, List<String>> entry : entrySet) {
			for (String entry2 : entry.getValue()) {
				response.addHeader(entry.getKey(), entry2);		
			}
		}

		//response.addCookie(cookie);
		//addCookie(response, cookie, "none");
		
		response.sendRedirect("https://pawan.gq/cookie_testing/landing1.html");
	}

	@GetMapping("/read_2")
	public void readCookie2(Map<String, String> requestMap, HttpServletRequest request, HttpServletResponse response) {
		logger.info("readCookie2() hit");
		logger.info("request scheme: " + request.getScheme());
		logger.info("is request secure : " + request.isSecure());


		String requestUrl = request.getRequestURL().toString();
		logger.info("Request Url = {}", requestUrl);
		
		String requestUri = request.getRequestURL() + "?"+ request.getQueryString();
		logger.info("RequestURI = {}", requestUri);
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			logger.info("cookies present in request");
			for (Cookie cookie : cookies) {
				logger.info("cookie name: " + cookie.getName());
				logger.info("domain: " + cookie.getDomain());
				logger.info("is secure: " + cookie.getSecure());
				logger.info("value: " + cookie.getValue());
				logger.info("max-age: " + cookie.getMaxAge());
				logger.info("path: " + cookie.getPath());
			}
		}

		String header = request.getHeader(HttpHeaders.SET_COOKIE);
		if (header != null && header != "") {
			logger.info(HttpHeaders.SET_COOKIE + " present in request headers");
			logger.info(header);
		}

		String header2 = response.getHeader(HttpHeaders.SET_COOKIE);
		if (header2 != null && header2 != "") {
			logger.info(HttpHeaders.SET_COOKIE + " present in request headers");
			logger.info(header2);
		}
	}

	public static void addCookie(HttpServletResponse response, Cookie cookie, String sameSite) {
		StringBuilder c = new StringBuilder(64 + cookie.getValue().length());
		c.append(cookie.getName());
		c.append('=');
		c.append(cookie.getValue());
		append2cookie(c, "domain", cookie.getDomain());
		append2cookie(c, "path", cookie.getPath());
		append2cookie(c, "SameSite", sameSite);

		if (cookie.getSecure()) {
			c.append("; secure");
		}
		if (cookie.isHttpOnly()) {
			c.append("; HttpOnly");
		}
		if (cookie.getMaxAge() >= 0) {
			append2cookie(c, "Expires", getExpires(cookie.getMaxAge()));
		}

		// add to header
		response.addHeader("Set-Cookie", c.toString());
		// add to cookie
		response.addCookie(cookie);
	}

	private static String getExpires(int maxAge) {
		if (maxAge < 0) {
			return "";
		}
		Calendar expireDate = Calendar.getInstance();
		expireDate.setTime(new Date());
		expireDate.add(Calendar.SECOND, maxAge);

		return expiresDateFormat.format(expireDate);
	}

	private static void append2cookie(StringBuilder cookie, String key, String value) {
		if (key == null || value == null || key.trim().equals("") || value.trim().equals("")) {
			return;
		}
		cookie.append(";");
		cookie.append(key);
		cookie.append('=');
		cookie.append(value);
	}

	public CookieSerializer cookieSerializer(String cookieName, String domainName, String cookiePath,
			boolean isHttpOnly, boolean isSecure) {
		DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
		cookieSerializer.setSameSite("None");
		cookieSerializer.setCookieName(cookieName);
		cookieSerializer.setDomainName(domainName);
		cookieSerializer.setCookiePath(cookiePath);
		cookieSerializer.setUseHttpOnlyCookie(isHttpOnly);
		cookieSerializer.setUseSecureCookie(isSecure);
		cookieSerializer.setCookieMaxAge(9999999);
		// TODO check if rememberMeServices need additional configuration here

		return cookieSerializer;
	}

}
