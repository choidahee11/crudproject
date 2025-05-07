package com.shinhan.emp;

import java.util.List;

//data 를 display 하려는 목적, 나중에 웹으로 변경되면 JSP로 만들예정
public class EmpView {

		//여러건을 출력
	//over 라이딩 : 이름은 같은데 정보가 여러개 
	public static void display(List<EmpDTO>emplist) {
		
		System.out.println("====직원 여러건 조회===");
		
		emplist.stream().forEach(emp->System.out.println(emp));
	}
	public static void display(EmpDTO emp) {
		if(emp==null) {
			display("해당하는 직원이 없습니다");
			return;
		}
		System.out.println("직원정보:"+emp);
	}
	public static void display(String message) {
		System.out.println("알림:"+message);
	}
	
}
