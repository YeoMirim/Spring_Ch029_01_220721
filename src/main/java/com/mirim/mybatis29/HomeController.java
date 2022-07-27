package com.mirim.mybatis29;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mirim.mybatis29.dao.IDao;
import com.mirim.mybatis29.dto.ContentDto;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

//	ContentDao dao;
	
	@Autowired
	private SqlSession sqlSession;	// bean을 바로 불러와서 쓸 수 있게끔 주입이 됨
	
//
//	@Autowired
//	public void setDao(ContentDao dao) {
//		this.dao = dao;
//	}
	
	@RequestMapping (value = "/writeForm")
	public String writeForm() {
		
		return "writeForm";
	}
	
	
	@RequestMapping (value = "write")
	public String write(HttpServletRequest request) {
		
		String mwriter = request.getParameter("mwriter");   // 입력했던 값을 추출해서
		String mcontent = request.getParameter("mcontent");
	
		IDao idao = sqlSession.getMapper(IDao.class);
		idao.writeDao(mwriter, mcontent);
		
//		dao.writeDao(mwriter, mcontent);
		
		return "redirect:list";
	}
	
	@RequestMapping (value = "/list")
	public String list(Model model) {
		
//		ArrayList<ContentDto> dtos = dao.listDao();
		
		IDao idao = sqlSession.getMapper(IDao.class);		// sqlSession에서 mapper를 가져옴(객체)
		ArrayList<ContentDto> dtos = idao.listDao();		// 호출하면서 select문을 실행함
		
		model.addAttribute("list", dtos);
		
		return "list";
	}
	
	@RequestMapping (value = "/delete")
	public String delete(HttpServletRequest request) {
		
		String mid = request.getParameter("mid");
		
		IDao idao = sqlSession.getMapper(IDao.class);
		idao.deleteDao(mid);
		
//		dao.deleteDao(request.getParameter("mid"));
		
		return "redirect:list";
	}
}
