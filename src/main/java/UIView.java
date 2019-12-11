import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.*;

public class UIView
{
	JFrame owner; // owner(main screen)
	JPanel window; // draw this pane
	// data storage
	Container container;
	// about page
	int selected_page;
	int start_page;
	int selected_index;
	JButton pages[];
	// ARA
	araLoader ara_load = null;
	int article_number;
	// OTL
	otlLoader otl_load = null;
	String search;
	Container otl_c = null;
	String targettext;
	// etc
	int panelwidth, panelheight;
	Font basicfont = new Font("Arial", 0, 20);

	UIView(JFrame owner, int width, int height)
	{
		this.owner = owner;
		window = new JPanel();
		panelwidth = width;
		panelheight = height;
		selected_page = 1;
		pages = new JButton[11];
		search = "";
		start_page = 1;
		clear();
	}

	void draw_main()
	{
		// clear Panel and draw main screen
		clear();
		window.setSize((int) (panelwidth * 0.6), (int) (panelheight * 0.6));
		window.setLocation(0 + (int) (panelwidth * 0.2), 50 + (int) (panelheight * 0.2));
		window.setLayout(new GridLayout(2, 2, 20, 20));
		JButton btn_ara = new JButton("ARA");
		btn_ara.setFont(new Font("Arial", 0, 20));
		btn_ara.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				owner.setVisible(false);
				UILoader u = new UILoader(1);
				u.parent = owner;
			}
		});
		window.add(btn_ara);
		JButton btn_otl = new JButton("OTL");
		btn_otl.setFont(new Font("Arial", 0, 20));
		btn_otl.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				owner.setVisible(false);
				UILoader u = new UILoader(2);
				u.parent = owner;
			}
		});
		window.add(btn_otl);
		JButton btn_delivery = new JButton("<html>Delivery<br />Assistant</html>");
		btn_delivery.setFont(new Font("Arial", 0, 20));
		btn_delivery.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				owner.setVisible(false);
				UILoader u = new UILoader(3);
				u.parent = owner;
			}
		});
		window.add(btn_delivery);
		JButton btn_rec = new JButton("<html>Application<br />Recommendation</html>");
		btn_rec.setFont(new Font("Arial", 0, 20));
		btn_rec.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				owner.setVisible(false);
				UILoader u = new UILoader(4);
				u.parent = owner;
			}
		});
		window.add(btn_rec);
	}

	void draw_ARA(int scr_number)
	{
		if(ara_load == null) ara_load = new araLoader();
		clear();
		// 0:login, 1:board, 2:article
		switch (scr_number)
		{
			case 0:
				// login
				window.setSize((int) (panelwidth / 3), (int) (panelheight / 3));
				window.setLocation((int) (panelwidth / 3), (int) (panelheight / 3));
				window.setLayout(new GridLayout(4, 1));
				JLabel lbl1 = new JLabel("ARA Login", SwingConstants.CENTER);
				lbl1.setFont(basicfont);
				window.add(lbl1);
				JPanel p = new JPanel();
				p.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
				p.setLayout(new GridLayout(2, 2));
				p.setBackground(Color.WHITE);
				JLabel lbl2 = new JLabel("id");
				lbl2.setFont(basicfont);
				p.add(lbl2);
				final JTextField idfield = new JTextField("");
				p.add(idfield);
				JLabel lbl3 = new JLabel("password");
				lbl3.setFont(basicfont);
				p.add(lbl3);
				final JPasswordField passfield = new JPasswordField("");
				p.add(passfield);
				window.add(p);
				JButton btn_login = new JButton("login");
				btn_login.setFont(basicfont);
				window.add(btn_login);
				final JLabel lbl4 = new JLabel("Login failed", SwingConstants.CENTER);
				lbl4.setFont(basicfont);
				lbl4.setForeground(Color.RED);
				lbl4.setVisible(false);
				window.add(lbl4);
				btn_login.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						// check login
						System.out.println("try login");
						if(ara_load.login(idfield.getText(),passfield.getText()))
						{
							System.out.println("login success");
							draw_ARA(1);
						}
						else
						{
							lbl4.setVisible(true);
						}
					}
				});
				break;
			case 1:
				// ara board
		/*
	    String categories[] = { "Hello1", "Hello2", "Hello3" };
	    JComboBox<> cb;
	    ComboBox<String> combobox = new ComboBox<String>(categories);
	    combobox.setFont(basicfont);
	    combobox.setSize(300, 50);
	    combobox.setLocation(panelwidth / 2 - 150, 0);
	    combobox.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
	    window.add(combobox);
	    */
				//draw selected_page
				//(selected_page-1) * 3 , +1 ~ +3
				int articlewidth = panelwidth - 50;
				int articleheight = panelheight / 4 - 20;
				for (int i = 0; i < 3; i++)
				{
					int targetpage = ((selected_page-1)*3+1+i)/20;
					int targetarticle = ((selected_page-1)*3+1+i)%20;
					Container c = ara_load.fetchPage(targetpage);
					final Article data = c.getArticles().get(targetarticle);
					final Container datumc = ara_load.fetchArticle(data.getId());
					JPanel article = articlebox(articlewidth, articleheight, 25, 10 + panelheight / 4 * i + 10 * i);
					// title, author and date
					JLabel titlelabel = new JLabel(data.getTitle());
					titlelabel.setSize(articlewidth / 2 - 20, articleheight / 5);
					titlelabel.setFont(basicfont);
					titlelabel.setLocation(20, 0);
					article.add(titlelabel);
					JLabel authorlabel = new JLabel(data.getAuthor());
					authorlabel.setSize(articlewidth / 4, articleheight / 5);
					authorlabel.setLocation(articlewidth * 2 / 4, 0);
					authorlabel.setFont(basicfont);
					article.add(authorlabel);
					JLabel datelabel = new JLabel(new SimpleDateFormat( "MM.dd HH:mm").format(datumc.getArticles().get(0).getDate()));
					datelabel.setSize(articlewidth / 4, articleheight / 5);
					datelabel.setLocation(articlewidth * 3 / 4, 0);
					datelabel.setFont(basicfont);
					article.add(datelabel);

					Datum datum = datumc.getArticles().get(0).getContents(0);
					// image
					Image img = null;
					int imgsize = articleheight * 4 / 5 - 10;
					try
					{
						if(datum != null && datum.image != null && false) img = ImageIO.read(datum.image);
						else img = ImageIO.read(new File("src/images/sample.png"));
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
					img = img.getScaledInstance(imgsize, imgsize, Image.SCALE_SMOOTH);
					JLabel imagelabel = new JLabel(new ImageIcon(img));
					imagelabel.setSize(imgsize, imgsize);
					imagelabel.setLocation(20, articleheight / 5 + 5);
					article.add(imagelabel);
					// text
					JTextArea textlabel = new JTextArea();
					if(datum != null && datum.content != null) textlabel.setText(datum.content);
					else textlabel.setText("no data");
					textlabel.setLineWrap(true);
					textlabel.setFont(basicfont);
					textlabel.setSize(articlewidth - imgsize - 35, imgsize);
					textlabel.setLocation(imgsize + 30, articleheight / 5 + 5);
					textlabel.setAlignmentY(Component.TOP_ALIGNMENT);
					textlabel.setEnabled(false);
					textlabel.setDisabledTextColor(Color.BLACK);
					article.add(textlabel);
					article.addMouseListener(new MouseListener()
					{
						public void mouseReleased(MouseEvent e)
						{
						}

						public void mousePressed(MouseEvent e)
						{
							article_number = data.getId();
							draw_ARA(2);
						}

						public void mouseClicked(MouseEvent e)
						{
						}

						public void mouseEntered(MouseEvent e)
						{
						}

						public void mouseExited(MouseEvent e)
						{
						}
					});
					window.add(article);
				}
				// page buttons
				addpagebuttons(0);
				break;
			case 2:
				// select article
				Article selected_article = ara_load.fetchArticle(article_number).getArticles().get(0);

				JButton returnbutton = new JButton("<");
				returnbutton.setSize(50, 50);
				returnbutton.setLocation(5, 5);
				returnbutton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						draw_ARA(1);
					}
				});
				window.add(returnbutton);
				int detailwidth = panelwidth - 20;
				int detailheight = panelheight - 120;
				JPanel detailarticle = articlebox(panelwidth - 20, panelheight - 120, 10, 60);
				JLabel titlelabel = new JLabel(selected_article.getTitle());
				titlelabel.setSize(detailwidth / 2 - 20, 50);
				titlelabel.setFont(basicfont);
				titlelabel.setLocation(20, 0);
				detailarticle.add(titlelabel);
				JLabel authorlabel = new JLabel(selected_article.getAuthor());
				authorlabel.setSize(detailwidth / 4, 50);
				authorlabel.setLocation(detailwidth * 2 / 4, 0);
				authorlabel.setFont(new Font("Arial", 0, 25));
				detailarticle.add(authorlabel);
				JLabel datelabel = new JLabel(new SimpleDateFormat( "MM.dd HH:mm").format(selected_article.getDate()));
				datelabel.setSize(detailwidth / 4, 50);
				datelabel.setLocation(detailwidth * 3 / 4, 0);
				datelabel.setFont(basicfont);
				detailarticle.add(datelabel);
				JPanel textnimg = new JPanel();
				textnimg.setBackground(Color.WHITE);
				int current_height = 0;
				for(int i = 0;i < selected_article.contentsSize();i++)
				{
					if(selected_article.getContents(i).content != null)
					{
						JTextArea tmp = new JTextArea(selected_article.getContents(i).content);
						tmp.setLocation(0,current_height);
						tmp.setSize(detailwidth-100,200);
						tmp.setLineWrap(true);
						tmp.setSize(detailwidth-100,tmp.getLineCount()*20);
						textnimg.add(tmp);
						current_height += 200;
					}
					if(selected_article.getContents(i).image != null)
					{
						Image img = null;
						try {
							img = ImageIO.read(selected_article.getContents(i).image);
							img = img.getScaledInstance(64,64,Image.SCALE_SMOOTH);
						} catch (IOException e) {
							e.printStackTrace();
						}
						JLabel imgtmp = new JLabel(new ImageIcon(img));
						imgtmp.setSize(64,64);
						imgtmp.setLocation(0,current_height);
						textnimg.add(imgtmp);
						current_height += 64;
					}
				}
				JTextArea tmp2 = new JTextArea("---------comment---------");
				tmp2.setLocation(0,current_height);
				tmp2.setSize(detailwidth-100,200);
				tmp2.setLineWrap(true);
				tmp2.setSize(detailwidth-100,tmp2.getLineCount()*20);
				textnimg.add(tmp2);
				current_height += 200;
				for(int i = 0;i < selected_article.commentsSize();i++)
				{
					JTextArea tmp = new JTextArea("Comment from " + selected_article.getComments(i).getAuthor());
					tmp.setLocation(0,current_height);
					tmp.setSize(detailwidth-100,200);
					tmp.setLineWrap(true);
					tmp.setSize(detailwidth-100,tmp.getLineCount()*20);
					textnimg.add(tmp);
					String tmpstr = "";
					for(int j = 0;j < selected_article.getComments(i).contentsSize();j++)
					{
						tmpstr += selected_article.getComments(i).getContents(j);
					}
					JTextArea tmp3 = new JTextArea(tmpstr);
					tmp3.setLocation(0,current_height);
					tmp3.setSize(detailwidth-100,200);
					tmp3.setLineWrap(true);
					tmp3.setSize(detailwidth-100,tmp3.getLineCount()*20);
					textnimg.add(tmp3);
				}
				textnimg.setPreferredSize(new Dimension(detailwidth-100,current_height));
				JScrollPane sp = new JScrollPane(textnimg);
				sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				sp.setBounds(50,70,detailwidth-100,detailheight-100);
				detailarticle.add(sp);
				window.add(detailarticle);
				break;
		}
		window.updateUI();
	}

	void draw_OTL(int scr_number)
	{
		clear();
		if(otl_load == null) otl_load = new otlLoader();
		switch (scr_number)
		{
			case 0:
				// search
				JPanel spanel = new JPanel();
				spanel.setSize(panelwidth - 200, 70);
				spanel.setLayout(null);
				spanel.setLocation(100, panelheight / 2 - 150);
				spanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
				spanel.setBackground(new Color(0x1D4E89));
				final JTextField sfield = new JTextField(40);
				sfield.setSize(panelwidth - 280, 60);
				sfield.setLocation(5, 5);
				sfield.setFont(new Font("Arial", 0, 45));
				spanel.add(sfield);
				JButton search_btn = new JButton(new ImageIcon("src/images/search.png"));
				search_btn.setSize(33, 33);
				search_btn.setLocation(panelwidth - 280 + 28, 20);
				search_btn.setBackground(new Color(0x1D4E89));
				search_btn.setBorder(null);
				search_btn.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						search = sfield.getText();
						draw_OTL(1);
					}
				});
				spanel.add(search_btn);
				window.add(spanel);
				break;
			case 1:
				// searched
		/*
	    JPanel spanel2 = new JPanel();
	    spanel2.setSize(panelwidth - 200, 50);
	    spanel2.setLayout(null);
	    spanel2.setLocation(100, 10);
	    spanel2.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
	    spanel2.setBackground(new Color(0x1D4E89));
	    final JTextField sfield2 = new JTextField(40);
	    sfield2.setText(search);
	    sfield2.setSize(panelwidth - 260, 40);
	    sfield2.setLocation(5, 5);
	    sfield2.setFont(new Font("Arial", 0, 45));
	    spanel2.add(sfield2);
	    JButton search_btn2 = new JButton(new ImageIcon("src/images/search.png"));
	    search_btn2.setSize(33, 33);
	    search_btn2.setLocation(panelwidth - 280 + 38, 8);
	    search_btn2.setBackground(new Color(0x1D4E89));
	    search_btn2.setBorder(null);
	    search_btn2.addActionListener(new ActionListener()
	    {
		public void actionPerformed(ActionEvent e)
		{
		    search = sfield2.getText();
		    draw_OTL(1);
		}
	    });
	    spanel2.add(search_btn2);
	    window.add(spanel2);
	    */
				System.out.println("OTL screen 1 drawing..");
				if(otl_c == null)
				{
					otl_c = otl_load.fetch(search);
					if(otl_c == null)
					{
						draw_OTL(0);
						System.out.println("no result. return");
						return;
					}
				}
				ArrayList<Article> articles = otl_c.getArticles();
				int articlewidth = panelwidth - 100;
				int articleheight = panelheight / 4 - 20;
				for (int i = 0; i < 3; i++)
				{
					System.out.println(i + "th article loading..");
					if((selected_page-1)*3+1+i > articles.size()) continue;
					Article tmp = articles.get((selected_page-1)*3+1+i);
					JPanel article = articlebox(panelwidth - 100, articleheight, 50, 20 + panelheight / 4 * i );
					//"<html>Delivery<br />Assistant</html>"
					String tmp_str = "";
					for(int j = 0;j < tmp.contentsSize();j++)
					{
						tmp_str += tmp.getContents(j).content;
						if(j < tmp.contentsSize()-1) tmp_str+= "\n";
					}
					final String articletext = tmp_str;
					JTextArea textlabel = new JTextArea(articletext);
					textlabel.setLineWrap(true);
					textlabel.setFont(basicfont);
					textlabel.setSize(articlewidth - 40, articleheight-10);
					textlabel.setLocation(20, 3);
					textlabel.setAlignmentY(Component.TOP_ALIGNMENT);
					textlabel.setEnabled(false);
					textlabel.setDisabledTextColor(Color.BLACK);
					article.add(textlabel);
					textlabel.addMouseListener(new MouseListener()
					{
						public void mouseReleased(MouseEvent e)
						{
						}
						public void mousePressed(MouseEvent e)
						{
							targettext = articletext;
							draw_OTL(2);
						}
						public void mouseExited(MouseEvent e)
						{
						}
						public void mouseEntered(MouseEvent e)
						{
						}
						public void mouseClicked(MouseEvent e)
						{
						}
					});
					window.add(article);
				}
				addpagebuttons(1);
				break;
			case 2:
				JButton returnbutton = new JButton("<");
				returnbutton.setSize(50, 50);
				returnbutton.setLocation(5, 5);
				returnbutton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						draw_OTL(1);
					}
				});
				window.add(returnbutton);
				int detailwidth = panelwidth - 20;
				int detailheight = panelheight - 120;
				JTextArea textlabel = new JTextArea(targettext);
				textlabel.setLineWrap(true);
				textlabel.setFont(basicfont);
				textlabel.setDisabledTextColor(Color.BLACK);
				JScrollPane sp = new JScrollPane(textlabel);
				sp.setBounds(20,60,detailwidth-10,detailheight-20);
				sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				window.add(sp);
				break;
		}
		window.updateUI();
	}

	void draw_delivery(int scr_number)
	{
		clear();
		switch (scr_number)
		{
			case 0:
				int articlewidth = panelwidth - 100;
				int articleheight = panelheight / 4 - 20;
				for (int i = 0; i < 3; i++)
				{
					JPanel article = articlebox(articlewidth, articleheight, 50, 10 + panelheight / 4 * i);
					Image img = null;
					int imgsize = articleheight - 30;
					try
					{
						img = ImageIO.read(new File("src/images/sample.png"));
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
					img = img.getScaledInstance(imgsize, imgsize, Image.SCALE_SMOOTH);
					JLabel imagelabel = new JLabel(new ImageIcon(img));
					imagelabel.setSize(imgsize, imgsize);
					imagelabel.setLocation(15, 15);
					article.add(imagelabel);

					JLabel namelabel = new JLabel("Restaurant");
					namelabel.setFont(basicfont);
					namelabel.setSize(articlewidth - imgsize - 30, 40);
					namelabel.setLocation(imgsize + 30, 0);
					article.add(namelabel);

					JTextArea textlabel = new JTextArea(
							"JTJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextArea");
					textlabel.setLineWrap(true);
					textlabel.setFont(basicfont);
					textlabel.setSize(articlewidth - imgsize - 40, articleheight - 48);
					textlabel.setLocation(imgsize + 30, 45);
					textlabel.setAlignmentY(Component.TOP_ALIGNMENT);
					textlabel.setEnabled(false);
					textlabel.setDisabledTextColor(Color.BLACK);
					article.add(textlabel);
					article.addMouseListener(new MouseListener()
					{
						public void mouseReleased(MouseEvent e)
						{
						}

						public void mousePressed(MouseEvent e)
						{
							draw_delivery(1);
						}

						public void mouseExited(MouseEvent e)
						{
						}

						public void mouseEntered(MouseEvent e)
						{
						}

						public void mouseClicked(MouseEvent e)
						{
						}
					});
					window.add(article);
				}
				addpagebuttons(2);
				break;
			case 1:
				JButton returnbutton = new JButton("<");
				returnbutton.setSize(45, 45);
				returnbutton.setLocation(0, 0);
				returnbutton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						draw_delivery(0);
					}
				});
				window.add(returnbutton);
				int articlewidth2 = panelwidth - 100;
				int articleheight2 = panelheight / 6;
				JPanel article = articlebox(articlewidth2, articleheight2, 50, 10);
				Image img = null;
				int imgsize = articleheight2 - 30;
				try
				{
					img = ImageIO.read(new File("src/images/sample.png"));
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
				img = img.getScaledInstance(imgsize, imgsize, Image.SCALE_SMOOTH);
				JLabel imagelabel = new JLabel(new ImageIcon(img));
				imagelabel.setSize(imgsize, imgsize);
				imagelabel.setLocation(15, 15);
				article.add(imagelabel);

				JLabel namelabel = new JLabel("Restaurant");
				namelabel.setFont(basicfont);
				namelabel.setSize(articlewidth2 - imgsize - 30, 40);
				namelabel.setLocation(imgsize + 30, 0);
				article.add(namelabel);

				JTextArea textlabel = new JTextArea(
						"JTJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextAreaJTextArea");
				textlabel.setLineWrap(true);
				textlabel.setFont(basicfont);
				textlabel.setSize(articlewidth2 - imgsize - 40, articleheight2 - 48);
				textlabel.setLocation(imgsize + 30, 45);
				textlabel.setAlignmentY(Component.TOP_ALIGNMENT);
				textlabel.setEnabled(false);
				textlabel.setDisabledTextColor(Color.BLACK);
				article.add(textlabel);
				window.add(article);

				JPanel menucheck = new JPanel();
				menucheck.setLayout(null);
				for (int i = 0; i < 10; i++)
				{
					JPanel menu = articlebox(articlewidth2-30, panelheight/5, 10, 10 + i * panelheight/5 + 10 * i);
					menu.setBackground(Color.GREEN);
					menucheck.add(menu);
				}
				menucheck.setBackground(Color.RED);
				menucheck.setPreferredSize(new Dimension(articlewidth2,9999)); //TODO resize the height according to data size
				JScrollPane sp = new JScrollPane(menucheck);
				//sp.setSize(articlewidth2, panelheight * 3 / 5 - 20);
				//sp.setLocation(50, panelheight / 5 + 40);
				sp.setBounds(50,panelheight / 5,articlewidth2,panelheight * 3 / 5 - 20);
				sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				window.add(sp);

				JButton sound_translate = new JButton("Sound translation");
				sound_translate.setSize(300, 80);
				sound_translate.setLocation(50, panelheight * 4 / 5 - 15);
				sound_translate.setFont(basicfont);
				window.add(sound_translate);
				Image tmp = null;
				try
				{
					tmp = ImageIO.read(new File("src/images/play.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
				JButton sound_play = new JButton(new ImageIcon(tmp));
				sound_play.setSize(80, 80);
				sound_play.setLocation(articlewidth2 - 210, panelheight * 4 / 5 - 15);
				sound_play.setBackground(Color.WHITE);
				window.add(sound_play);
				Image tmp2 = null;
				try
				{
					tmp2 = ImageIO.read(new File("src/images/stop.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
				JButton sound_stop = new JButton(new ImageIcon(tmp2));
				sound_stop.setSize(80, 80);
				sound_stop.setLocation(articlewidth2 - 120, panelheight * 4 / 5 - 15);
				sound_stop.setBackground(Color.WHITE);
				window.add(sound_stop);
				Image tmp3 = null;
				try
				{
					tmp3 = ImageIO.read(new File("src/images/reset.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
				JButton sound_reset = new JButton(new ImageIcon(tmp3));
				sound_reset.setSize(80, 80);
				sound_reset.setLocation(articlewidth2 - 30, panelheight * 4 / 5 - 15);
				sound_reset.setBackground(Color.WHITE);
				window.add(sound_reset);
				break;
		}
		window.updateUI();
	}

	void draw_recommend()
	{
		clear();
		// articles
		int articlewidth = panelwidth - 100;
		int articleheight = panelheight / 4;

		for(int i=0;i<3;i++)
		{
			if (i==0){
				JPanel article = articlebox(articlewidth, articleheight, 50, 50 + panelheight / 4 * i + 40 * i);
				Image img=null;
				int imgsize = articleheight - 30;
				try
				{
					img = ImageIO.read(new File("images/library.png"));
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
				img = img.getScaledInstance(imgsize, imgsize, Image.SCALE_SMOOTH);
				JLabel imagelabel = new JLabel(new ImageIcon(img));
				imagelabel.setSize(imgsize, imgsize);
				imagelabel.setLocation(15, 15);
				article.add(imagelabel);

				JLabel namelabel = new JLabel("Application name:KAIST LIBRARY");
				namelabel.setFont(basicfont);
				namelabel.setSize(articlewidth - imgsize - 30, 50);
				namelabel.setLocation(imgsize + 30, 15);
				article.add(namelabel);

				JTextArea textlabel = new JTextArea(
						"INTRODUCTION:Inquiries, reservations for library seats, and how to borrow books   https://play.google.com/store/apps/details?id=kr.ac.libit.kaist&hl=en ");
				textlabel.setLineWrap(true);
				textlabel.setFont(basicfont);
				textlabel.setSize(articlewidth - imgsize - 40, articleheight - 90);
				textlabel.setLocation(imgsize + 30, 80);
				textlabel.setAlignmentY(Component.TOP_ALIGNMENT);
				textlabel.setEnabled(false);
				textlabel.setDisabledTextColor(Color.BLACK);
				article.add(textlabel);

				window.add(article);
			}
			if (i==1){
				JPanel article = articlebox(articlewidth, articleheight, 50, 50 + panelheight / 4 * i + 40 * i);
				Image img=null;
				int imgsize = articleheight - 30;
				try
				{
					img = ImageIO.read(new File("images/portal.png"));
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
				img = img.getScaledInstance(imgsize, imgsize, Image.SCALE_SMOOTH);
				JLabel imagelabel = new JLabel(new ImageIcon(img));
				imagelabel.setSize(imgsize, imgsize);
				imagelabel.setLocation(15, 15);
				article.add(imagelabel);

				JLabel namelabel = new JLabel("Application name：KAIST PORTAL");
				namelabel.setFont(basicfont);
				namelabel.setSize(articlewidth - imgsize - 30, 50);
				namelabel.setLocation(imgsize + 30, 15);
				article.add(namelabel);

				JTextArea textlabel = new JTextArea(
						"INTRODUCTION：Integrated application receive notifications select courses also access to mail and KLMS   LINK：https://play.google.com/store/apps/details?id=kr.ac.kaist.portal&hl=en");
				textlabel.setLineWrap(true);
				textlabel.setFont(basicfont);
				textlabel.setSize(articlewidth - imgsize - 40, articleheight - 90);
				textlabel.setLocation(imgsize + 30, 80);
				textlabel.setAlignmentY(Component.TOP_ALIGNMENT);
				textlabel.setEnabled(false);
				textlabel.setDisabledTextColor(Color.BLACK);
				article.add(textlabel);

				window.add(article);
			}
			if (i==2){
				JPanel article = articlebox(articlewidth, articleheight, 50, 50 + panelheight / 4 * i + 40 * i);
				Image img=null;
				int imgsize = articleheight - 30;
				try
				{
					img = ImageIO.read(new File("images/kairen.png"));
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
				img = img.getScaledInstance(imgsize, imgsize, Image.SCALE_SMOOTH);
				JLabel imagelabel = new JLabel(new ImageIcon(img));
				imagelabel.setSize(imgsize, imgsize);
				imagelabel.setLocation(15, 15);
				article.add(imagelabel);

				JLabel namelabel = new JLabel("Application name：KAIREN");
				namelabel.setFont(basicfont);
				namelabel.setSize(articlewidth - imgsize - 30, 50);
				namelabel.setLocation(imgsize + 30, 15);
				article.add(namelabel);

				JTextArea textlabel = new JTextArea(
						"INTRODUCTION：This app works for the communication and safety of KAIST people and KAIST security team by reporting security issue. LINK：https://play.google.com/store/apps/details?id=kr.ac.kaist.kairen.kairen&hl=en");
				textlabel.setLineWrap(true);
				textlabel.setFont(basicfont);
				textlabel.setSize(articlewidth - imgsize - 40, articleheight - 90);
				textlabel.setLocation(imgsize + 30, 80);
				textlabel.setAlignmentY(Component.TOP_ALIGNMENT);
				textlabel.setEnabled(false);
				textlabel.setDisabledTextColor(Color.BLACK);
				article.add(textlabel);

				window.add(article);
			}


		}

		// page buttons
		addpagebuttons(3);
	}

	// helper functions
	void clear() // reset the panel
	{
		window.removeAll();
		window.setSize(panelwidth, panelheight);
		window.setLocation(0, 50);
		window.setBackground(Color.WHITE);
		window.setLayout(null);
	}

	JPanel articlebox(int width, int height, int X, int Y) // white background,
	// black bound
	// rectangle
	{
		JPanel p = new JPanel();
		p.setSize(width, height);
		p.setLayout(null);
		p.setLocation(X, Y);
		p.setBackground(Color.WHITE);
		p.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		return p;
	}

	void addpagebuttons(int pagetype)
	{
		// page buttons
		for (int i = 0; i < 11; i++)
		{
			if (i == 0) pages[i] = new JButton("<");
			else if (i == 10) pages[i] = new JButton(">");
			else pages[i] = new JButton((start_page + i - 1) + "");
			pages[i].setSize(50, 50);
			pages[i].setLocation(panelwidth / 2 + 50 * i - 250 - 25, panelheight - 110);
			pages[i].setBackground(Color.WHITE);
			pages[i].setFont(basicfont);
			pages[i].setBorder(null);
			pages[i].addActionListener(new pagelistener(i, pagetype));
			if (selected_page == start_page + i - 1) pages[i].setForeground(Color.BLUE);
			window.add(pages[i]);
		}
	}

	class pagelistener implements ActionListener
	{
		int index, pagetype;

		pagelistener(int index, int pagetype) // 0 : ARA, 1 : OTL, 2 : Delivery,
		// 3 : Rec
		{
			this.index = index;// 0:<,10:>
			this.pagetype = pagetype;
		}

		public void actionPerformed(ActionEvent e)
		{
			if (index == 0)
			{
				if (start_page <= 1 && selected_page <= 1) return; // do nothing
				selected_page--;
				if (selected_page < start_page) start_page--;
			}
			else if (index == 10)
			{
				selected_page++;
				if (selected_page > start_page + 8) start_page++;
			}
			else
			{
				selected_page = start_page + index - 1;
			}
			switch (pagetype)
			{
				case 0:
					draw_ARA(1);
					break;
				case 1:
					draw_OTL(1);
					break;
				case 2:
					draw_delivery(0);
					break;
				case 3:
					draw_recommend();
					break;
			}
			window.updateUI();
		}
	}
}
