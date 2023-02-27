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


class BookManagerAdd extends Frame implements ActionListener{
	

	
	//디비관련 클래스변수들...
	Connection conn = null;
	String url = "jdbc:mysql://localhost:3306/book_db?useUnicode=true&characterEncoding=utf8";	
	String id = "root";
	String pass = "qwer";
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	/////////////////////////////////////////////////////////////////////////
	
	Label lbTitle = new Label("[[    책 등록    ]]");
	Label lbName =    new Label("책제목:");
	Label lbWriter =    new Label("책저자:");
	Label lbPublishing =  new Label("출판사:");
	
	TextField tfName = new TextField();
	TextField tfWriter = new TextField();
	TextField tfPublishing = new TextField();
	
		
	Button btnAdd = new Button("책등록");
	
	Font font25 = new Font("TimesRoman", Font.PLAIN, 25);
	Font font15 = new Font("SansSerif", Font.BOLD, 15);
	BookManagerAdd()
	{		
		super("책등록화면");
		dbCon();//디비접속
		this.setSize(300,330);
		this.init();//화면레이아웃구성메서드
		start();
		this.setLocation(500, 200);
		this.setVisible(true);
		
	}	
	void start() {
		btnAdd.addActionListener(this);	

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
		lbTitle.setFont(font25);
		lbTitle.setBounds(50, 50, 200, 30);
		
		this.add(lbName);			lbName.setFont(font15);		lbName.setBounds(30, 100, 80, 30);		
		this.add(tfName);			tfName.setFont(font15);		tfName.setBounds(110, 100, 120, 30);
		
		
		this.add(lbWriter);			lbWriter.setFont(font15);	lbWriter.setBounds(30, 150, 80, 30);		
		this.add(tfWriter);			tfWriter.setFont(font15);	tfWriter.setBounds(110, 150, 120, 30);
		
		this.add(lbPublishing);		lbPublishing.setFont(font15);			lbPublishing.setBounds(30, 200, 80, 30);		
		this.add(tfPublishing);		tfPublishing.setFont(font15);			tfPublishing.setBounds(110, 200, 80, 30);
		
	
		
		this.add(btnAdd);		btnAdd.setFont(font15);	btnAdd.setBounds(110, 250, 80, 30);
		
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
	

	@Override
	public void actionPerformed(ActionEvent e) {	
		//자판기 상품등록버튼
		if( e.getSource() == btnAdd) 
		{
			if(tfName.getText().equals("")){
				dlgMsg("책제목을 입력하시오.");
				return;
			}
			else if(tfWriter.getText().equals("")){
				dlgMsg("저자를 입력하시오.");
				return;
			}
			else if(tfPublishing.getText().equals("")){
				dlgMsg("출판사를 입력하시오.");
				return;
			}
			
							
			//책등록 디비메서드
			add();
			dbClose();
		}
	
	}
	
	void add()
	{	
		String pquery = "insert into book values (null, ?, ?, ?,now(),?)";	
		try {
			conn = DriverManager.getConnection(url, id, pass);
			pstmt = conn.prepareStatement(pquery);
			pstmt.setString(1, tfName.getText());
			pstmt.setString(2, tfWriter.getText());
			pstmt.setString(3, tfPublishing.getText());				
			pstmt.setString(4,"yes");
			pstmt.executeUpdate();
			System.out.println("책등록 성공");
		} catch (SQLException ee) {
			System.err.println("Query 실행 클래스 생성 에러~!! : " + ee.toString());
		}	
		
		
		//자판기 상품등록처리후 사후제어처리...
				
		tfWriter.setText("");
		tfPublishing.setText("");
		tfName.setText("");	
		
		dlgMsg("책 등록 완료!");
		
		dbClose();
			
	}
	void dlgMsg(String msg)
	{
		Dialog dlg = new Dialog(this, "책등록화면", true);
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







