package 도서관리프로그램;

import java.awt.Button;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


class MainProgram extends Frame implements ActionListener{
	//화면별 클래스 변수 선언...
	public static Menu1BookManager menu1BookManager = null; //도서관리
	public static Menu2MemberManager menu2MemberManager = null; //회원관리
	public static Menu3QnA menu3QnA = null; //문의하기
	public static Menu4Admin menu4Admin = null; //관리자화면
	
	
	
	Label lbTitle = new Label("도서대여프로그램");	
	Button btnBook = new Button("도서관리");	
	Button btnMember = new Button("회원관리");
	Button btnWrite = new Button("문의하기");
	Button btnAdmin = new Button("관리자화면");
	Button btnClose = new Button("닫기");

	Font font25 = new Font("TimesRoman", Font.PLAIN, 25);
	Font font15 = new Font("SansSerif", Font.BOLD, 15);
	MainProgram(String loginId)
	{
		super("도서대여프로그램");
		this.setSize(300,400);
		this.init(loginId);//화면레이아웃구성메서드
		this.start();
		this.setLocation(200, 200);
		this.setVisible(true);
		
	}
	void start()
	{
		btnBook.addActionListener(this);
		btnMember.addActionListener(this);
		btnWrite.addActionListener(this);
		btnAdmin.addActionListener(this);
		btnClose.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	void init(String loginId) //레이아웃 구성하기...
	{
		this.setLayout(null);//레이아웃을 직접좌표처리하는방식으로하기위해서...
		this.add(lbTitle);//현재 클래스 내부에 생성해서 넣음...
		lbTitle.setBounds(50, 40, 220, 30);
		lbTitle.setFont(font25);
		
		this.add(btnBook);//현재 클래스 내부에 생성해서 넣음...
		btnBook.setBounds(80, 100, 140, 40);
		btnBook.setFont(font15);	

		this.add(btnMember);//현재 클래스 내부에 생성해서 넣음...
		btnMember.setBounds(80, 150, 140, 40);
		btnMember.setFont(font15);
		
		this.add(btnWrite);//현재 클래스 내부에 생성해서 넣음...
		btnWrite.setBounds(80, 200, 140, 40);
		btnWrite.setFont(font15);
		
		if(loginId.equals("admin"))
		{
			this.add(btnAdmin);//현재 클래스 내부에 생성해서 넣음...
			btnAdmin.setBounds(80, 250, 140, 40);
			btnAdmin.setFont(font15);
		}
		
		this.add(btnClose);//현재 클래스 내부에 생성해서 넣음...
		btnClose.setBounds(80, 300, 140, 40);
		btnClose.setFont(font15);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnBook)        {			
			if(menu1BookManager==null){ menu1BookManager = new Menu1BookManager();	}
			else{	menu1BookManager.setVisible(true);}
		}
		else if(e.getSource()==btnMember) {
			if(menu2MemberManager==null){ menu2MemberManager = new Menu2MemberManager();	}
			else{	menu2MemberManager.setVisible(true);}
		}
		else if(e.getSource()==btnWrite)  {			
			if(menu3QnA==null){ menu3QnA = new Menu3QnA();	}
			else{	menu3QnA.setVisible(true);}
		}
		else if(e.getSource()==btnAdmin)  { 
			if(menu4Admin==null){ menu4Admin = new Menu4Admin();	}
			else{	menu4Admin.setVisible(true);}
		}
		else if(e.getSource()==btnClose){ System.exit(0);}		
	}

}

