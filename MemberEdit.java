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

class MemberEdit extends Frame implements ItemListener, ActionListener{
	//디비관련 클래스변수들...
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/book_db?useUnicode=true&characterEncoding=utf8";	
		String id = "root";
		String pass = "qwer";
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		//////////////////////////////////////////////////////////
		
	
	Label lbTitle = new Label("[[ 회원정보수정 ]]");
	Label lbId =    new Label("아 이 디:");
	Label lbPw =    new Label("패스워드:");
	Label lbName =  new Label("이   름:");
	Label lbHp =    new Label("연 락 처:");
	Label lbSex =    new Label("성   별:");
	TextField tfId = new TextField();
	TextField tfPw = new TextField();
	TextField tfName = new TextField();
	TextField tfHp = new TextField();
	Button btnIdCheck = new Button("찾기");	
	Button btnEdit = new Button("수정완료");
	Button btnCancel = new Button("취소");
	
	Choice chSex = new Choice();
	String sex="남자";
	Font font25 = new Font("TimesRoman", Font.PLAIN, 25);
	Font font15 = new Font("SansSerif", Font.BOLD, 15);
	MemberEdit()
	{
		super("회원정보수정");
		dbCon();
		this.setSize(300,450);
		this.init();//화면레이아웃구성메서드
		start();
		this.setLocation(500, 200);
		this.setVisible(true);
		
	}	
	void start() {
		btnCancel.addActionListener(this);
		btnEdit.addActionListener(this);
		btnIdCheck.addActionListener(this);
		chSex.addItemListener(this);
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
		
		this.add(lbId);			lbId.setFont(font15);		lbId.setBounds(30, 100, 80, 30);		
		this.add(tfId);			tfId.setFont(font15);		tfId.setBounds(110, 100, 80, 30);
		this.add(btnIdCheck);	btnIdCheck.setFont(font15);	btnIdCheck.setBounds(200, 100, 80, 30);
		
		this.add(lbPw);			lbPw.setFont(font15);		lbPw.setBounds(30, 150, 80, 30);		
		this.add(tfPw);			tfPw.setFont(font15);		tfPw.setBounds(110, 150, 80, 30);
		
		this.add(lbName);		lbName.setFont(font15);		lbName.setBounds(30, 200, 80, 30);		
		this.add(tfName);		tfName.setFont(font15);		tfName.setBounds(110, 200, 120, 30);
		
		this.add(lbHp);			lbHp.setFont(font15);		lbHp.setBounds(30, 250, 80, 30);		
		this.add(tfHp);			tfHp.setFont(font15);		tfHp.setBounds(110, 250, 120, 30);
		
		
		this.add(lbSex); lbSex.setFont(font15);				lbSex.setBounds(30, 300, 80, 30);
		this.add(chSex); chSex.setFont(font15);				chSex.setBounds(110, 300, 120, 30);
		chSex.add("남자"); chSex.add("여자");
		
		
		this.add(btnEdit);		btnEdit.setFont(font15);	btnEdit.setBounds(110, 340, 80, 30);
		this.add(btnCancel);	btnCancel.setFont(font15);	btnCancel.setBounds(110, 380, 80, 30);
		
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
	void idCheck(String findId)
	{
		//수정아이디 찾기
		String query = "select * from member where id='"+findId+"'";				
		try {								
			rs = stmt.executeQuery(query);
			boolean idCheck = false;				
			while (rs.next()) {			
				idCheck =true;
				dlgMsg("수정대상을 찾았습니다.");
				tfPw.setText(rs.getString("pw"));
				tfName.setText(rs.getString("name"));
				tfHp.setText(rs.getString("hp"));
				chSex.select(rs.getString("sex"));
				sex = rs.getString("sex");
				break;
			}
			if(idCheck==false)
			{				
				dlgMsg("수정대상이 없습니다.");				
			}
			
		} catch (SQLException ee) {
			System.err.println("실행결과 획득실패!!");
		}
		
		
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		sex = chSex.getSelectedItem();		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {	
		if(e.getSource() == btnIdCheck)  {
			if(tfId.getText().equals("")){dlgMsg("아이디를 입력하시오.");return;	}			
			idCheck(tfId.getText());
		}
		else if(e.getSource() == btnEdit) {
			if(tfId.getText().equals("")){dlgMsg("아이디를 입력하시오.");return;	}
			else if(tfPw.getText().equals("")){dlgMsg("패스워드를 입력하시오.");return;	}
			else if(tfName.getText().equals("")){dlgMsg("이름을 입력하시오.");return;	}
			else if(tfHp.getText().equals("")){dlgMsg("연락처를 입력하시오.");return;	}
			
			updateMember(tfId.getText(),tfPw.getText(),tfName.getText(),tfHp.getText(), sex);
			
		}
		else if(e.getSource() == btnCancel) {viewClose();}	

		
	}
	// 회원 정보수정을 위해서...
	void updateMember(String id, String pass, String name, String hp, String sex) {
			String query = "update member set id = ?, pw = ?, name = ?, hp = ?, sex = ? where id = ?";
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, id);
				pstmt.setString(2, pass);
				pstmt.setString(3, name);
				pstmt.setString(4, hp);
				pstmt.setString(5, sex);
				pstmt.setString(6, id);
				pstmt.executeUpdate();
				pstmt.close();
				dlgMsg("수정완료!");
			} catch (SQLException ee) {
				System.err.println("회원 정보수정 실패!!");
			
			}
			
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








