package 도서관리프로그램;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StartProgramBook_Login {
	public static void main(String[] args) {	
		Login pg = new Login();
	}
}
class Login extends Frame implements FocusListener, ActionListener{
	Label lbTitle = new Label("로그인화면");
	Label lbId = new Label("아이디:");
	Label lbPw = new Label("패스워드:");
	TextField tfId = new TextField();
	TextField tfPw = new TextField();
	Button btnLogin = new Button("로그인");	
	Button btnJoin = new Button("회원가입");
	
	Font font25 = new Font("TimesRoman", Font.PLAIN, 25);
	Font font15 = new Font("SansSerif", Font.BOLD, 15);
	String id = "admin", pw="1004";
	Login()
	{
		super("로그인화면");
		this.setSize(300,400);
		this.init();//화면레이아웃구성메서드
		this.start();//이벤트메서드처리
		this.setLocation(400, 200);
		this.setVisible(true);
		
	}	
	void start()
	{
		btnLogin.addActionListener(this);
		btnJoin.addActionListener(this);
		tfId.addFocusListener(this);
		tfPw.addFocusListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	void init() //레이아웃 구성하기...
	{
		this.setLayout(null);//레이아웃을 직접좌표처리하는방식으로하기위해서...
		this.add(lbTitle);//현재 클래스 내부에 생성해서 넣음...
		lbTitle.setBounds(60, 40, 220, 30);
		lbTitle.setFont(font25);
		
		this.add(lbId);//현재 클래스 내부에 생성해서 넣음...
		lbId.setBounds(40, 120, 70, 30);
		lbId.setFont(font15);

		this.add(tfId);//현재 클래스 내부에 생성해서 넣음...
		tfId.setBounds(150, 120, 100, 30);
		tfId.setFont(font15);

		
		this.add(lbPw);//현재 클래스 내부에 생성해서 넣음...
		lbPw.setBounds(40, 170, 100, 30);
		lbPw.setFont(font15);

		this.add(tfPw);//현재 클래스 내부에 생성해서 넣음...
		tfPw.setBounds(150, 170, 100, 30);
		tfPw.setFont(font15);
		tfPw.setEchoChar('*');
		
		this.add(btnLogin);//현재 클래스 내부에 생성해서 넣음...
		btnLogin.setBounds(100, 250, 100, 30);
		btnLogin.setFont(font15);
		
		this.add(btnJoin);//현재 클래스 내부에 생성해서 넣음...
		btnJoin.setBounds(100, 300, 100, 30);
		btnJoin.setFont(font15);
		
		
	}
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource() == tfId) {}
		else if(e.getSource() == tfPw) {
			if(tfId.getText().equals(""))
			{
				tfId.requestFocus();
				System.out.println("Id input plz...");
			}
			int check = tfId.getText().trim().length();
			if(check>12)
			{
				tfId.requestFocus();
				tfId.setText("");
				System.out.println("Id length MAX 12.....");
			}
			
		}
		
	}
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnLogin)
		{
			if(tfId.getText().equals("")){
				dlgMsg("아이디를 입력하시오.");
				return;}
			if(tfPw.getText().equals("")){
				dlgMsg("패스워드를 입력하시오.");
				return;}
			
			String inputId = tfId.getText();
			String inputPw = tfPw.getText();
			////////////////////////////////////////
			///데이타베이스접속..
			
			try {
				Class.forName("org.gjt.mm.mysql.Driver");
			} catch (ClassNotFoundException ee) {
				System.exit(0);
			}
			Connection conn = null;
			//접속 주소 : 3306/디비명
			String url = "jdbc:mysql://localhost:3306/book_db?useUnicode=true&characterEncoding=utf8";
			//String url = "jdbc:mysql://127.0.0.1:3306/java";
			String id = "root";
			String pass = "qwer";
			Statement stmt = null;
			ResultSet rs = null;
			String query = "select * from member";
			try {
				conn = DriverManager.getConnection(url, id, pass);
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				boolean idOk = false;
				while (rs.next()) {				
					if(inputId.equals(rs.getString(2)))
					{
						idOk=true;
						if(inputPw.equals(rs.getString(3)))
						{							
							dlgMsg("로그인 성공!!");
							MainProgram mv = new MainProgram(rs.getString("id"));
							this.setVisible(false);
						}
						else
						{
							dlgMsg("비번이 틀립니다.");break;
						}
					}
					
				}				
				if(idOk == false)
				{
					dlgMsg("아이디를 확인하세요.");
				}
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException ee) {
				System.err.println("error = " + ee.toString());
			}
			
			
		}
		else if(e.getSource() == btnJoin)
		{
			MemberJoin mj = new MemberJoin();
		}
		
	}
	void dlgMsg(String msg)
	{
		Dialog dlg = new Dialog(this, "로그인체크", true);
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

