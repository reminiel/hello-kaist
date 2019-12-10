import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UILoader
{
    JFrame parent = null;
    JFrame window;
    int loadertype; //0:main, 1:ARA, 2:OTL, 3:Delivery, 4:Recommendation
    int framewidth,frameheight;
    UILoader(int loadertype)
    {
	//default setting
	this.loadertype = loadertype;
	window = new JFrame();
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	framewidth = (int)screenSize.getWidth();
	frameheight = (int)screenSize.getHeight();
	window.setSize(framewidth,frameheight);
	window.setUndecorated(true);
	window.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
	window.getContentPane().setBackground(Color.WHITE);
	window.setLayout(null);
	window.setResizable(false);
	
	//top of frame. title, exit buttons ...
	JLabel titlelabel = new JLabel("Hello@KAIST");
	titlelabel.setFont(new Font("Arial", Font.BOLD, 27));
	titlelabel.setLocation(10,0);
	titlelabel.setSize(600,50);
	window.add(titlelabel);
	
	JButton btn_min = new JButton(new ImageIcon("images/min.png"));
	btn_min.setLocation(framewidth - 100,0);
	btn_min.setSize(50,50);
	btn_min.setBackground(Color.WHITE);
	btn_min.setBorder(null);
	btn_min.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		window.setState(Frame.ICONIFIED);
	    }
	});
	window.add(btn_min);

	JButton btn_home = new JButton(new ImageIcon("images/home.png"));
	btn_home.setLocation(framewidth - 150,0);
	btn_home.setSize(50,50);
	btn_home.setBackground(Color.WHITE);
	btn_home.setBorder(null);
	btn_home.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		if(parent == null) return;
		window.setVisible(false);
		parent.setVisible(true);
	    }
	});
	window.add(btn_home);
	
	JButton btn_exit = new JButton(new ImageIcon("images/exit.png"));
	btn_exit.setLocation(framewidth - 50,0);
	btn_exit.setSize(50,50);
	btn_exit.setBackground(Color.WHITE);
	btn_exit.setBorder(null);
	btn_exit.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		System.exit(1);
	    }
	});
	window.add(btn_exit);
	//main panel
	UIView u = new UIView(window,framewidth,frameheight);
	window.add(u.window);
	//according to loader type ...
	switch(loadertype)
	{
	case 0:
	    //configuration button
	    JButton btn_config = new JButton(new ImageIcon("images/config.png"));
	    btn_config.setLocation(framewidth - 200,0);
	    btn_config.setSize(50,50);
	    btn_config.setBackground(Color.WHITE);
	    btn_config.setBorder(null);
	    window.add(btn_config);
	    //main panel
	    u.draw_main();
	    break;
	case 1:
	    titlelabel.setText("Hello@Kaist ARA");
	    u.draw_ARA(0);
	    break;
	case 2:
	    titlelabel.setText("Hello@Kaist OTL");
	    u.draw_OTL(0);
	    break;
	case 3:
	    titlelabel.setText("Hello@Kaist Delivery Assistant");
	    u.draw_delivery(0);
	    break;
	case 4:
	    titlelabel.setText("Hello@Kaist Application Recommendation");
	    u.draw_recommend();
	    break;
	}
	window.setVisible(true);
    }

}
