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


class BookManager extends Frame implements ActionListener{
	//책관리 하위 클래스 
	public static BookManagerAdd bookManagerAdd=null; //책등록
	public static BookManagerList bookManagerList=null; //책리스트
	public static BookManagerEdit bookManagerEdit=null; //책정보수정
	public static BookManagerDel bookManagerDel=null; //책삭제
	
	
	Label lbTitle = new Label("[[[ 책관리 ]]]");
	Button btnAdd = new Button("책등록");
	Button btnList = new Button("책리스트");	
	Button btnEdit = new Button("책정보수정");
	Button btnDel = new Button("책삭제");
	
	Button btnClose = new Button("닫기");
	
	Font font25 = new Font("TimesRoman", Font.PLAIN, 25);
	Font font15 = new Font("SansSerif", Font.BOLD, 15);
	BookManager()
	{
		super("책등록관리");
		this.setSize(300,450);
		this.init();//화면레이아웃구성메서드
		this.start();
		this.setLocation(300, 100);
		this.setVisible(true);
		
	}
	void start()
	{
		btnAdd.addActionListener(this);
		btnList.addActionListener(this);
		btnEdit.addActionListener(this);
		btnDel.addActionListener(this);
		
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
	
		this.add(btnAdd);//현재 클래스 내부에 생성해서 넣음...
		btnAdd.setBounds(80, 100, 140, 40);
		btnAdd.setFont(font15);	

		this.add(btnList);//현재 클래스 내부에 생성해서 넣음...
		btnList.setBounds(80, 150, 140, 40);
		btnList.setFont(font15);
		
		this.add(btnEdit);//현재 클래스 내부에 생성해서 넣음...
		btnEdit.setBounds(80, 200, 140, 40);
		btnEdit.setFont(font15);

		this.add(btnDel);//현재 클래스 내부에 생성해서 넣음...
		btnDel.setBounds(80, 250, 140, 40);
		btnDel.setFont(font15);

		this.add(btnClose);//현재 클래스 내부에 생성해서 넣음...
		btnClose.setBounds(80, 350, 140, 40);
		btnClose.setFont(font15);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnAdd)       {
			if(bookManagerAdd==null){ bookManagerAdd = new BookManagerAdd();	}
			else{	bookManagerAdd.setVisible(true);}
		}
		else if(e.getSource()==btnList) {
			if(bookManagerList==null){ bookManagerList = new BookManagerList();	}
			else{	bookManagerList.setVisible(true);}
		}
		else if(e.getSource()==btnEdit) {
			if(bookManagerEdit==null){ bookManagerEdit = new BookManagerEdit();	}
			else{	bookManagerEdit.setVisible(true);}
		}
		else if(e.getSource()==btnDel)  {
			if(bookManagerDel==null){ bookManagerDel = new BookManagerDel();	}
			else{	bookManagerDel.setVisible(true);}
		}		
		else if(e.getSource()==btnClose){ viewClose();}

	}
}

