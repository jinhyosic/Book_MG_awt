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

class MemberList extends Frame implements ItemListener, ActionListener{
	Label lbTitle = new Label("[[  회원 리스트  ]]");
	List mList = new List();
	Button btnOk = new Button("확인");	
	
	Font font25 = new Font("TimesRoman", Font.PLAIN, 25);
	Font font15 = new Font("SansSerif", Font.BOLD, 15);
	MemberList()
	{
		super("회원목록");
		this.setSize(300,400);
		this.init();//화면레이아웃구성메서드
		start();
		this.setLocation(500, 200);
		dataLoad();
		this.setVisible(true);
		
	}	
	void start() {
		btnOk.addActionListener(this);
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
		lbTitle.setFont(font25);
		lbTitle.setBounds(50, 50, 200, 30);
		
		this.add(mList);mList.setFont(font15);
		mList.setBounds(10, 100, 280, 220);
//		mList.add("1 / hong / 홍길동 / 1111111");
//		mList.add("2 / cms / 아무개 / 2222222");
//		mList.add("3 / abc / 둘리 / 3333333");
		
		this.add(btnOk);		btnOk.setFont(font15);	btnOk.setBounds(110, 340, 80, 30);
		
	}
	void dataLoad()
	{
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
			String result="";
		while (rs.next()) {
			result = rs.getString("idx")+"/"+rs.getString("id")+"/"+rs.getString("pw")
			+"/"+rs.getString("name")+"/"+rs.getString("hp")+"/"+rs.getString("sex");	
			System.out.println("result:"+result);
			mList.add(result);
		
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
		dlgMsg(mList.getSelectedItem());		
	}
	@Override
	public void actionPerformed(ActionEvent e) {		
		 viewClose(); 
		
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








