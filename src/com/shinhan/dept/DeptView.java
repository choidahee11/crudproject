package com.shinhan.dept;

import java.util.List;

//View(Web에서 JSP로 변경될 예정이다)
public class DeptView {

	 public static void display(List<DeptDTO>deptlist) {
		 if(deptlist.size()==0) {
			 
		 }
		 System.out.println("======부서목록=======");
		 deptlist.stream().forEach(dept->{
			System.out.println("부서번호:"+dept.getDepartment_id()); 
			System.out.println("부서이름:"+dept.getDepartment_name()); 
			System.out.println("매니저번호:"+dept.getManager_id()); 
			System.out.println("지역코드:"+dept.getLocation_id()); 
		 });
	 }
 
public void display(String message) {
	System.out.println("==END==");

}
public static void menuDisplay() {
	System.out.println("1.모든 부서 조회 ");
	System.out.println("2.부서 정보 상세보기 ");
	System.out.println("3.부서입력(i)");
	System.out.println("4.부서수정(u)");
	System.out.println("3.부서삭제(d)");
	System.out.println("3.부서 삭제(exit)");
	
	
}
}
