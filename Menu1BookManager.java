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


class Menu1BookManager extends Frame implements ActionListener{
	//도서관리 클래스 하위 클래스 선언.
	public static BookRental bookRental = null; //도서대여
	public static BookReturn bookReturn = null; //도서반납
	public static BookRentList bookRentList = null;//대여책목록
	public static BookRentMemberList bookRentMemberList = null;//대여자목록
	public static BookManager bookManager = null;//책관리(등록,수정,삭제)
	
	
	
	
	Label lbTitle = new Label("도서관리");	
	Button btnRent = new Button("도서대여");	
	Button btnReturn = new Button("도서반납");
	Button btnBookList = new Button("대여가능책목록");
	Button btnRentMember = new Button("대여자목록");
	Button btnBookManager = new Button("책관리");
	Button btnClose = new Button("닫기");
	
	Font font25 = new Font("TimesRoman", Font.PLAIN, 25);
	Font font15 = new Font("SansSerif", Font.BOLD, 15);
	

	Menu1BookManager()
	{
		super("도서관리");
		this.setSize(300,450);
		this.init();//화면레이아웃구성메서드
		this.start();
		this.setLocation(250, 150);
		this.setVisible(true);
		
	}
	void start()
	{

		btnRent.addActionListener(this);
		btnReturn.addActionListener(this);
		btnBookList.addActionListener(this);
		btnRentMember.addActionListener(this);
		btnBookManager.addActionListener(this);
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
		lbTitle.setBounds(90, 40, 220, 30);
		lbTitle.setFont(font25);
		
		this.add(btnRent);//현재 클래스 내부에 생성해서 넣음...
		btnRent.setBounds(80, 100, 140, 40);
		btnRent.setFont(font15);	

		this.add(btnReturn);//현재 클래스 내부에 생성해서 넣음...
		btnReturn.setBounds(80, 150, 140, 40);
		btnReturn.setFont(font15);
		
		this.add(btnBookList);//현재 클래스 내부에 생성해서 넣음...
		btnBookList.setBounds(80, 200, 140, 40);
		btnBookList.setFont(font15);

		this.add(btnRentMember);//현재 클래스 내부에 생성해서 넣음...
		btnRentMember.setBounds(80, 250, 140, 40);
		btnRentMember.setFont(font15);

		this.add(btnBookManager);//현재 클래스 내부에 생성해서 넣음...
		btnBookManager.setBounds(80, 300, 140, 40);
		btnBookManager.setFont(font15);
		
		this.add(btnClose);//현재 클래스 내부에 생성해서 넣음...
		btnClose.setBounds(80, 350, 140, 40);
		btnClose.setFont(font15);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnRent)             {			
			if(bookRental==null){ bookRental = new BookRental();	}
			else{	bookRental.setVisible(true);}			
		}
		else if(e.getSource()==btnReturn)      {
			if(bookReturn==null){ bookReturn = new BookReturn();	}
			else{	bookReturn.setVisible(true);}	
		}
		else if(e.getSource()==btnBookList)    {			
			if(bookRentList==null){ bookRentList = new BookRentList();	}
			else{	bookRentList.setVisible(true);}	
		}
		else if(e.getSource()==btnRentMember)  {
			if(bookRentMemberList==null){ bookRentMemberList = new BookRentMemberList();	}
			else{	bookRentMemberList.setVisible(true);}		}
		
		else if(e.getSource()==btnBookManager) {
			if(bookManager==null){ bookManager = new BookManager();	}
			else{	bookManager.setVisible(true);}
		}
		else if(e.getSource()==btnClose){ viewClose();}
		
		
	}
}

