package com.shinhan.dept;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO(Data Transfer Object)...data전송시 사용되는 객체의 틀(template)
//JavaBeans기술은 default생성자가 있어야한다.
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeptDTO {
	public DeptDTO(String deptId, String deptName, String managerId, String locId) {
		// TODO Auto-generated constructor stub
	}
	
	
	
	private int department_id;    
	private String department_name;  
	private int manager_id;       
	private int location_id;
	public void setDepartment_id(String deptId) {
		// TODO Auto-generated method stub
		
	}      
}
