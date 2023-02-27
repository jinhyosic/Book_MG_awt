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


class Menu2MemberManager extends Frame implements ActionListener{
	Label lbTitle = new Label("메인화면");	
	Button btnList = new Button("회원목록보기");	
	Button btnEdit = new Button("회원정보수정");
	Button btnDel = new Button("회원삭제");
	Button btnJoin = new Button("회원가입");
	Button btnClose = new Button("닫기");
	
	Font font25 = new Font("TimesRoman", Font.PLAIN, 25);
	Font font15 = new Font("SansSerif", Font.BOLD, 15);
	Menu2MemberManager()
	{
		super("메인화면");
		this.setSize(300,400);
		this.init();//화면레이아웃구성메서드
		this.start();
		this.setLocation(500, 300);
		this.setVisible(true);
		
	}
	void start()
	{
		btnList.addActionListener(this);
		btnEdit.addActionListener(this);
		btnDel.addActionListener(this);
		btnJoin.addActionListener(this);
		btnClose.addActionListener(this);
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
		this.add(lbTitle);//현재 클래스 내부에 생성해서 넣음...
		lbTitle.setBounds(100, 40, 220, 30);
		lbTitle.setFont(font25);
		
		this.add(btnList);//현재 클래스 내부에 생성해서 넣음...
		btnList.setBounds(80, 100, 140, 40);
		btnList.setFont(font15);	

		this.add(btnEdit);//현재 클래스 내부에 생성해서 넣음...
		btnEdit.setBounds(80, 150, 140, 40);
		btnEdit.setFont(font15);
		
		this.add(btnDel);//현재 클래스 내부에 생성해서 넣음...
		btnDel.setBounds(80, 200, 140, 40);
		btnDel.setFont(font15);
		
		this.add(btnJoin);//현재 클래스 내부에 생성해서 넣음...
		btnJoin.setBounds(80, 250, 140, 40);
		btnJoin.setFont(font15);
		
		this.add(btnClose);//현재 클래스 내부에 생성해서 넣음...
		btnClose.setBounds(80, 300, 140, 40);
		btnClose.setFont(font15);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnList)      {MemberList ml = new MemberList();}
		else if(e.getSource()==btnEdit) {MemberEdit me = new MemberEdit();}
		else if(e.getSource()==btnDel)  {MemberDel md = new MemberDel();}
		else if(e.getSource()==btnJoin) {MemberJoin mj = new MemberJoin();}
		else if(e.getSource()==btnClose){ viewClose();}
		
	}
}

