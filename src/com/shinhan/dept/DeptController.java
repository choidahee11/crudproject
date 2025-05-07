package com.shinhan.dept;

import java.util.Scanner;

import com.shinhan.common.CommonControllerInterface;

// Controller(사용자가 요청하면 요청을 처리하고 응답을 보낸다.(Servlet으로 변경할 예정)
// Web 아니고 Console로 동작하는 프로그램으로 키보드입력을 이용(System.in)
public class DeptController implements CommonControllerInterface{
    static Scanner sc = new Scanner(System.in);

    public void execute() {
        boolean isStop = false;
        while (!isStop) {
            menuDisplay();
            String job = sc.nextLine();
            switch (job) {
                case "all" -> {
                    f_all();
                }
                case "detail" -> {
                    f_detail();
                }
                case "i" -> {
                    f_insert();
                }
                case "u" -> {
                    f_update();
                }
                case "d" -> {
                    f_delete();
                }
                case "exit" -> {
                    isStop = true;
                }
                default -> {
                    System.out.println("잘못된 선택입니다.");
                }
            }
        }
        System.out.println("good bye");
    }

    private static void f_all() {
        // 모든 부서 조회
        // DeptService.selectAll() 호출하고 DeptView.display()로 출력
        DeptView.display(DeptService.selectAll());
    }

    private static void f_detail() {
        // 부서 상세 조회
        String deptId = dataInsert("조회할 부서번호>> ");
        DeptView.display(DeptService.selectById(deptId));
    }

    private static void f_insert() {
        String deptId = dataInsert("부서번호>> ");
        String deptName = dataInsert("부서이름>> ");
        String managerId = dataInsert("매니저번호>> ");
        String locId = dataInsert("지역번호>> ");

        DeptDTO newDept = new DeptDTO(deptId, deptName, managerId, locId);
        DeptService.insert(newDept);
    }

    private static void f_update() {
        String deptId = dataInsert("수정할 부서번호>> ");
        String deptName = dataInsert("새 부서이름>> ");

        DeptDTO dept = new DeptDTO();
        dept.setDepartment_id(deptId);
        dept.setDepartment_name(deptName);

        DeptService.update(dept);
    }

    private static void f_delete() {
        String deptId = dataInsert("삭제할 부서번호>> ");
        DeptService.delete(deptId);
    }

    private static String dataInsert(String message) {
        System.out.print(message);
        return sc.nextLine();
    }

    private static void menuDisplay() {
        System.out.println("==================");
        System.out.println("1.모든직원조회 2.부서상세보기 3.부서 수정 4.부서삭제 5.");
        System.out.println("==================");
        System.out.println("전화번호 입력>");
    }
}
