package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HelloWorld")
public class HelloWorld extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Jedis jedis = new Jedis("localhost");
	Gson gson = new Gson();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String os = null;
		String session = req.getSession().getId();
		String userAgent = req.getHeader("User-Agent").toLowerCase();

		if (userAgent.toLowerCase().indexOf("chrome") >= 0) {
			os = "Chrome";
		} else if (userAgent.toLowerCase().indexOf("windows") >= 0) {
			os = "Windows";
		} else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
			os = "Mac";
		} else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
			os = "Unix";
		} else if (userAgent.toLowerCase().indexOf("android") >= 0) {
			os = "Android";
		} else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
			os = "IPhone";
		} else {
			os = "UnKnown, More-Info: " + userAgent;
		}
		
		jedis.set(session + os, "ok");
		System.out.println("redis 存储的字符串为: " + jedis.keys("*"));

		Map<String, String> mapO = new HashMap<>();
		mapO.put("name", "pige");
		mapO.put("age", "18");
		mapO.put("session", session);

		PrintWriter out = resp.getWriter();
		out.print(gson.toJson(mapO));
		out.flush();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
