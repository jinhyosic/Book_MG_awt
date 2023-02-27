package 도서관리프로그램;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class BookRental extends Frame implements ActionListener{
	//디비관련 클래스변수들...
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/book_db?useUnicode=true&characterEncoding=utf8";	
		String id = "root";
		String pass = "qwer";
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		//////////////////////////////////////////////////////////
		
	
	Label lbTitle = new Label("[[ 도서대여하기 ]]");
	
	Label lbBookName =    new Label("대여책제목");
	Label lbMemberName =    new Label("대여자명");
	Label lbMemberHp =  new Label("대여자연락처");
	
	TextField tfBookName = new TextField();
	TextField tfMemberName = new TextField();
	TextField tfMemberHp = new TextField();
	
	Button btnBookCheck = new Button("찾기");	
	Button btnEdit = new Button("대여하기");	
	
	
	Font font25 = new Font("TimesRoman", Font.PLAIN, 25);
	Font font15 = new Font("SansSerif", Font.BOLD, 15);
	BookRental()
	{
		super("도서대여");
		dbCon();
		this.setSize(400,350);
		this.init();//화면레이아웃구성메서드
		start();
		this.setLocation(500, 200);
		this.setVisible(true);
		
	}	
	void start() {		
		btnEdit.addActionListener(this);
		btnBookCheck.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				viewClose();
			}
		});
	}
	void viewClose() {		
		this.setVisible(false);
	}
	void init() //레이아웃 구성하기...
	{
		this.setLayout(null);//레이아웃을 직접좌표처리하는방식으로하기위해서...		
		this.add(lbTitle);
		this.add(lbTitle);
		lbTitle.setFont(font25);
		lbTitle.setBounds(90, 50, 200, 30);
	
		Label lbBookName =    new Label("대여책제목");
		Label lbMemberName =    new Label("대여자명");
		Label lbMemberHp =  new Label("대여자연락처");
		
		this.add(lbBookName);			lbBookName.setFont(font15);		lbBookName.setBounds(30, 100, 120, 30);		
		this.add(tfBookName);			tfBookName.setFont(font15);		tfBookName.setBounds(160, 100, 120, 30);
		this.add(btnBookCheck);		btnBookCheck.setFont(font15);		btnBookCheck.setBounds(290, 100, 70, 30);
		
		this.add(lbMemberName);			lbMemberName.setFont(font15);	lbMemberName.setBounds(30, 150, 120, 30);		
		this.add(tfMemberName);			tfMemberName.setFont(font15);	tfMemberName.setBounds(160, 150, 120, 30);
		
		
		this.add(lbMemberHp);		lbMemberHp.setFont(font15);			lbMemberHp.setBounds(30, 200, 120, 30);		
		this.add(tfMemberHp);		tfMemberHp.setFont(font15);			tfMemberHp.setBounds(160, 200, 120, 30);
		
	
		
		this.add(btnEdit);		btnEdit.setFont(font15);	btnEdit.setBounds(130, 270, 100, 30);		
		
	}
	void dbCon()
	{
		////////////////////////////////////////
		///데이타베이스접속..	
		try {	Class.forName("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException ee) {System.exit(0);}	

		try {
			conn = DriverManager.getConnection(url, id, pass);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		////////////////////////////////////////////
	}
	void dbClose()
	{		
		// Close 작업
		try {
			rs.close();				
			stmt.close();
			pstmt.close();
			if (conn != null) {
				if (!conn.isClosed()) {
					conn.close();
				}
				conn = null;
			}
		} catch (SQLException ee) {
			System.err.println("닫기 실패~!!");
		}
	}
	void idCheck(String bookName)
	{
		//수정아이디 찾기
		String query = "select * from book where title='"+bookName+"' and book_rent='yes'";				
		try {								
			rs = stmt.executeQuery(query);
			boolean idCheck = false;				
			while (rs.next()) {			
				idCheck =true;
				dlgMsg("대여가능한 책입니다.");		
			
				break;				
				
			}
			if(idCheck==false)
			{				
				dlgMsg("대여가능한 책이 아닙니다.");				
			}
			
		} catch (SQLException ee) {
			System.err.println("실행결과 획득실패!!");
		}
		
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {	
		if(e.getSource() == btnBookCheck)  {
			if(tfBookName.getText().equals("")){dlgMsg("책제목을 입력하시오.");return;	}			
			idCheck(tfBookName.getText());
		}
		else if(e.getSource() == btnEdit) {
			if(tfBookName.getText().equals("")){dlgMsg("책제목을 입력하시오.");return;	}
			else if(tfMemberName.getText().equals("")){dlgMsg("대여자를 입력하시오.");return;	}
			else if(tfMemberHp.getText().equals("")){dlgMsg("대여자연락처를 입력하시오.");return;	}	
			updateBook(tfBookName.getText());
			bookRent(tfBookName.getText(),tfMemberName.getText(),tfMemberHp.getText());
			dbClose();
		}
			

		
	}
	
	void bookRent(String title, String name, String hp)
	{	
		String pquery = "insert into book_rental values (null, ?, ?, ?,now(),null)";	
		try {
			conn = DriverManager.getConnection(url, id, pass);
			pstmt = conn.prepareStatement(pquery);
			pstmt.setString(1, title);
			pstmt.setString(2, name);
			pstmt.setString(3, hp);	
						
			pstmt.executeUpdate();
			System.out.println("대여 성공");
		} catch (SQLException ee) {
			System.err.println("Query 실행 클래스 생성 에러~!! : " + ee.toString());
		}	
		
		
		//자판기 상품등록처리후 사후제어처리...
				
		tfBookName.setText("");
		tfMemberName.setText("");
		tfMemberHp.setText("");	
		
		dlgMsg("책 대여 완료!");		
		
			
	}
	// 책 렌탈 상태 변경
	void updateBook(String title) {		
		System.out.println(title);
			String query = "update book set book_rent = ? where title = ?";
			
			try {
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "no");				
				pstmt.setString(2, title);
				pstmt.executeUpdate();
				pstmt.close();
				dlgMsg("책 대여상태로 변경!");
			} catch (SQLException ee) {
				System.err.println("책 대여상태로 실패!!");
			
			}
			
		}
	void dlgMsg(String msg)
	{
		Dialog dlg = new Dialog(this, "대여하기정보", true);
		Label lbContent = new Label(msg);
		Button bt = new Button("확인");		
		dlg.setLayout(null);		
		dlg.add(lbContent);	 lbContent.setFont(font15);
		dlg.add(bt);		 bt.setFont(font15);
		lbContent.setBounds(50, 50, 200, 30);
		bt.setBounds(80, 120, 120, 30);		
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlg.setVisible(false);
			}
		});
		dlg.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dlg.setVisible(false);
			}
		});		
		dlg.setLocation(480,250);
		dlg.setSize(300, 200);
		dlg.setVisible(true);
	}
}








