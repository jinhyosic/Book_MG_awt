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

class BookReturn extends Frame implements  ActionListener{
	//디비관련 클래스변수들...
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/book_db?useUnicode=true&characterEncoding=utf8";	
		String id = "root";
		String pass = "qwer";
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		//////////////////////////////////////////////////////////
		
	
	Label lbTitle = new Label("[[ 도서 반납 하기 ]]");
	
	Label lbName =    new Label("대여책제목:");
	
	
	TextField tfName = new TextField();
	
	Button btnNameCheck = new Button("찾기");	
	
	boolean bookCheck =false;//반납가능여부체크
	
	
	Font font25 = new Font("TimesRoman", Font.PLAIN, 25);
	Font font15 = new Font("SansSerif", Font.BOLD, 15);
	BookReturn()
	{
		super("도서반납처리");
		dbCon();
		this.setSize(400,250);
		this.init();//화면레이아웃구성메서드
		start();
		this.setLocation(500, 200);
		this.setVisible(true);
		
	}	
	void start() {

		btnNameCheck.addActionListener(this);
		
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
		lbTitle.setBounds(80, 50, 200, 30);
		
		this.add(lbName);			lbName.setFont(font15);		lbName.setBounds(30, 100, 100, 30);		
		this.add(tfName);			tfName.setFont(font15);		tfName.setBounds(130, 100, 120, 30);
		this.add(btnNameCheck);		btnNameCheck.setFont(font15);		btnNameCheck.setBounds(250, 100, 50, 30);
		

		
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
			//pstmt.close();
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
	void nameCheck(String bookName)
	{
		bookCheck =false;
		//반납책 체크 
		String query = "select * from book_rental where book_title='"+bookName+"' and isnull(return_date)";				
		try {								
			rs = stmt.executeQuery(query);							
			while (rs.next()) {			
				bookCheck =true;
				dlgMsg("반납처리가능!정보확인!");		
			
				dlgReturn(bookName, rs.getString("rent_member_hp"),rs.getString("rent_date"));
				break;				
				
			}
			if(bookCheck==false)
			{				
				dlgMsg("반납가능한 책이 아닙니다.");				
			}
			
		} catch (SQLException ee) {
			System.err.println("실행결과 획득실패!!");
		}
		
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {	
		if(e.getSource() == btnNameCheck)  {
			if(tfName.getText().equals("")){dlgMsg("책제목을 입력하시오.");return;	}			
			nameCheck(tfName.getText());
		}


		
	}
	
	// 책 렌탈 상태 변경
		void updateBook(String title) {		
			System.out.println(title);
				String query = "update book set book_rent = ? where title = ?";
				
				try {
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, "yes");				
					pstmt.setString(2, title);
					pstmt.executeUpdate();
					pstmt.close();
					dlgMsg("책 대여상태로 변경!");
				} catch (SQLException ee) {
					System.err.println("책 대여상태로 실패!!");
				
				}
				
			}
	// 책대여리스트 반납일  업데이트..
	void updateRentalList(String title) {
			String query = "update book_rental set return_date = now() where book_title = ?";
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, title);
				pstmt.executeUpdate();
				pstmt.close();
				dlgMsg("반납완료!");
			} catch (SQLException ee) {
				System.err.println("책 반납 실패!!");
			
			}
			
		}
	
	void dlgReturn(String name,String hp,String date)
	{
		Dialog dlg = new Dialog(this, "반납처리하기", true);
		Label lbTitle = new Label("반납책이름:");
		Label lbTitle2 = new Label(name);
		
		Label lbHp = new Label("연락처:");
		Label lbHp2 = new Label(hp);
		Label lbRentDate = new Label("대여일자:");
		Label lbRentDate2 = new Label(date);
		
		Button bt = new Button("반납처리하기");		
		dlg.setLayout(null);		
		dlg.add(lbTitle);	 lbTitle.setFont(font15);
		dlg.add(lbTitle2);	 lbTitle2.setFont(font15);
		dlg.add(lbHp);	 lbHp.setFont(font15);
		dlg.add(lbHp2);	 lbHp2.setFont(font15);
		dlg.add(lbRentDate);	 lbRentDate.setFont(font15);
		dlg.add(lbRentDate2);	 lbRentDate2.setFont(font15);
		dlg.add(bt);		 bt.setFont(font15);
		lbTitle.setBounds(50, 50, 100, 30);
		lbTitle2.setBounds(150, 50, 100, 30);
		lbHp.setBounds(50, 100, 100, 30);
		lbHp2.setBounds(150, 100, 100, 30);
		lbRentDate.setBounds(50, 150, 100, 30);
		lbRentDate2.setBounds(150, 150, 140, 30);
		
		bt.setBounds(80, 200, 120, 30);		
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateBook(name);//책제목으로 호출해서 대여상태변환
				updateRentalList(name);				
				dlg.setVisible(false);
				
			}
		});
		dlg.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dlg.setVisible(false);
			}
		});		
		dlg.setLocation(480,250);
		dlg.setSize(320, 300);
		dlg.setVisible(true);
	}
	
	void dlgMsg(String msg)
	{
		Dialog dlg = new Dialog(this, "대상찾기", true);
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








