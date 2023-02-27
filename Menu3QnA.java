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

class Menu3QnA extends Frame implements ActionListener{
	//디비관련 클래스변수들...
	Connection conn = null;
	String url = "jdbc:mysql://localhost:3306/book_db?useUnicode=true&characterEncoding=utf8";	
	String id = "root";
	String pass = "qwer";
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	
	Label lbTitle = new Label("[[ 문의 글 등록 ]]");
	Label lbSubject =    new Label("제목:");
	Label lbContent =    new Label("내용:");
	
	TextField tfSubject = new TextField();
	TextArea ta = new TextArea();
	
	Button btnReg = new Button("글등록");	
	
	
	Font font25 = new Font("TimesRoman", Font.PLAIN, 25);
	Font font15 = new Font("SansSerif", Font.BOLD, 15);
	Menu3QnA()
	{
		super("관리자문의");
		this.setSize(350,450);
		this.init();//화면레이아웃구성메서드
		dbCon();
		start();
		this.setLocation(300, 100);
		this.setVisible(true);
		
	}	
	void viewClose() {
		
		this.setVisible(false);
	}
	void start() {
		btnReg.addActionListener(this);		
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				viewClose(); 
			}
		});
	}
	void init() //레이아웃 구성하기...
	{
		this.setLayout(null);//레이아웃을 직접좌표처리하는방식으로하기위해서...		
		this.add(lbTitle);
		lbTitle.setFont(font25);
		lbTitle.setBounds(80, 50, 200, 30);
		
		this.add(lbSubject);			lbSubject.setFont(font15);		lbSubject.setBounds(30, 100, 50, 30);		
		this.add(tfSubject);			tfSubject.setFont(font15);		tfSubject.setBounds(90, 100, 200, 30);
		this.add(lbContent);			lbContent.setFont(font15);		lbContent.setBounds(30, 150, 80, 30);
		this.add(ta);					ta.setFont(font15);				ta.setBounds(30, 180, 300, 170);
		this.add(btnReg);				btnReg.setFont(font15);			btnReg.setBounds(140, 360, 100, 40);		
				
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(tfSubject.getText().equals(""))
		{
			dlgMsg("제목을 넣어주세요~~!!");
			return;
		}
		else if(ta.getText().equals(""))
		{
			dlgMsg("내용을 넣어주세요~~!!");
			return;
		}
		
		//글등록진행
		regist();
		tfSubject.setText("");
		ta.setText("");
		dlgMsg("정상적으로 글 등록 완료!");
		dbClose();//디비작업끝나서 닫기
		
		Menu4Admin.mList.removeAll();//리시트항목 모두제거
		Menu4Admin.dataLoad();//삭제후 디비 새로읽기
		this.setVisible(false);
		
		
	}
	
	void dlgMsg(String msg)
	{
		Dialog dlg = new Dialog(this, "게시글등록알림", true);
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
	
	void regist()
	{	
		String pquery = "insert into board values (null, ?, ?, now())";	
		try {
			conn = DriverManager.getConnection(url, id, pass);
			pstmt = conn.prepareStatement(pquery);
			pstmt.setString(1, tfSubject.getText());
			pstmt.setString(2, ta.getText());			
			pstmt.executeUpdate();
			System.out.println("실행성공");
		} catch (SQLException ee) {
			System.err.println("Query 실행 클래스 생성 에러~!! : " + ee.toString());
		}		
		
			
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
}








