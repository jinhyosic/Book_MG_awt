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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class BookRentList extends Frame implements ItemListener, ActionListener{
	Label lbTitle = new Label("[[  대여책리스트  ]]");
	static List mList = new List();
	Button btnOk = new Button("확인");	
	
	Checkbox cbRentAll = new Checkbox("전체책목록",true);
	Checkbox cbRentYes = new Checkbox("대여가능책목록");
	Checkbox cbRentNo = new Checkbox("대여불가능책목록");
	
	Font font25 = new Font("TimesRoman", Font.PLAIN, 25);
	Font font15 = new Font("SansSerif", Font.PLAIN, 15);
	
	static int boardIdx[] = new int[20];
	
	
	BookRentList()
	{
		super("대여책리스트");
		this.setSize(450,400);
		this.init();//화면레이아웃구성메서드
		start();
		bookList("all");
		this.setLocation(300, 100);		
		this.setVisible(true);
		
	}	
	void start() {
		btnOk.addActionListener(this);
		cbRentAll.addItemListener(this);
		cbRentYes.addItemListener(this);
		cbRentNo.addItemListener(this);
		mList.addItemListener(this);
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
		this.add(cbRentAll);this.add(cbRentYes);this.add(cbRentNo);
		lbTitle.setFont(font25);
		cbRentAll.setFont(font15);	cbRentYes.setFont(font15);	cbRentNo.setFont(font15);
		
		
		
		lbTitle.setBounds(120, 40, 200, 30);	
		cbRentAll.setBounds(20, 80, 200, 30);
		cbRentYes.setBounds(20, 110, 200, 30);
		cbRentNo.setBounds(20, 140, 200, 30);
		
		
		this.add(mList);mList.setFont(font15);
		mList.setBounds(20, 170, 400, 150);

		
		this.add(btnOk);		btnOk.setFont(font15);	btnOk.setBounds(180, 340, 80, 30);
		
		
	}
	static void bookList(String gubun)
	{
	////////////////////////////////////////
	///데이타베이스접속..

		String query="";
		if(gubun.equals("all"))
		{
			query = "select * from book";
		}
		else if(gubun.equals("yes"))
		{
			query = "select * from book where book_rent='"+gubun+"'";
		}
		else if(gubun.equals("no"))
		{
			query = "select * from book where book_rent='"+gubun+"'";
		}
		
		
		
		
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
		
		try {
			conn = DriverManager.getConnection(url, id, pass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			String result="";
			int count = 0;
		while (rs.next()) {
			if(count==20) {break;}
			result = rs.getString("idx")+"/"+rs.getString("title")+"/"+rs.getString("writer")+
					"/"+rs.getString("publishing")+"/"+rs.getString("date_reg");	
			System.out.println("result:"+result);
			mList.add(result);
			
			//상세보기 처리를 위해서 싱크를 맞춰서 idx값을 저장
			//리스트에서 아이템 선택시 인덱스값하고 맞춰서 제어를 위해 
			boardIdx[count] = Integer.parseInt(rs.getString("idx"));
			count++;		
		}
		rs.close();
		stmt.close();
		conn.close();
		} catch (SQLException ee) {
		System.err.println("error = " + ee.toString());
		}
		
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if(e.getSource() == cbRentAll)
		{
			cbRentYes.setState(false);
			cbRentNo.setState(false);
			if(cbRentAll.getState() == true	&& cbRentYes.getState() == false&& cbRentNo.getState() == false	)
			{
				mList.removeAll();
				System.out.println("전체");
				bookList("all");
			}
		}
		else if(e.getSource() == cbRentYes)
		{
			cbRentAll.setState(false);
			cbRentNo.setState(false);
			if(cbRentAll.getState() == false&& cbRentYes.getState() == true	&& cbRentNo.getState() == false	)
			{
				mList.removeAll();
				System.out.println("대여가능");
				bookList("yes");
			}
		}
		else if(e.getSource() == cbRentNo)
		{
			mList.removeAll();
			cbRentAll.setState(false);
			cbRentYes.setState(false);
			if(cbRentAll.getState() == false && cbRentYes.getState() == false&& cbRentNo.getState() == true	)
			{
				System.out.println("대여불가");
				bookList("no");
			}
		}
		
				
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOk)
		{
		 viewClose();
		}
		
			 
		
	}
	
	void dlgMsg(String msg)
	{
		Dialog dlg = new Dialog(this, "회원정보", true);
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








