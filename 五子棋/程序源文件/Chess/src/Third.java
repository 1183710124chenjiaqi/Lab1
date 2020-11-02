import java.awt.Color;

import java.awt.Font;

import java.awt.Graphics;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Third extends JFrame implements MouseListener {

	// 设置游戏界面

	/*
	 * 屏幕分辨率求法:
	 * 
	 * int w = f.getToolkit().getScreenSize().width;//宽度
	 * 
	 * int h = f.getToolkit().getScreenSize().height;//高度
	 * 
	 * Toolkit.getDefaultToolkit().getScreenSize().width//与上面等同，都是用来获取屏幕的宽高，
	 * 
	 * this.setLocation((width - 500) / 2 , (height - 500) / 2);
	 * 使窗口能够居中显示，这样看起来美观。
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	int height = Toolkit.getDefaultToolkit().getScreenSize().height;

	int x, y;// 定义鼠标坐标

	int[][] allChess = new int[19][19]; // 用数组来保存棋子，0表示无子，1表示黑子，2表示白子
	static final List<Integer> Xposition = new ArrayList<Integer>();
	static final List<Integer> Yposition = new ArrayList<Integer>();// 记录战局

	static boolean isblack = true; // 用来表示黑子还是白子， true表示黑子 false表示白子
	static boolean canPlay = true; // 用来表示当前游戏是否结束
	static int time = 8;
	static int i = time;
	static int prei = i;

	String message = "黑方先行";
	String blackMessage = "无限制";
	String whiteMessage = "无限制";

	public Third() {

		this.setTitle("五子棋");

		this.setSize(800, 800);

		this.setLocation((width - 1000) / 2, (height - 1000) / 2);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setResizable(false); // 设置窗口不可改变，固定窗口大小

		this.setVisible(true);

		this.repaint(); // java里repaint()是重绘component的方法，它会调用paint方法对界面进行重行绘画

		while (true) {
			try {
				String s = JOptionPane.showInputDialog(null, "每步棋的最长用时（以秒为单位）: ", "游戏设置", JOptionPane.QUESTION_MESSAGE);
				if (s == null) {
					JOptionPane.showMessageDialog(null, "请设置每步棋的最长用时", "警告", JOptionPane.WARNING_MESSAGE);
					continue;
				}
				time = Integer.parseInt(s);
				if (time < 1)
					JOptionPane.showMessageDialog(null, "设定用时需为正整数!", "error", JOptionPane.ERROR_MESSAGE);
				else
					break;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "请输入整数时间（单位：s)！", "error", JOptionPane.ERROR_MESSAGE);
			}
		}
		whiteMessage = time + "秒";
		thread = new Thread() {
			public void run() {
				i = time;
				boolean flag = true;
				while (flag) {
					if (i < 0) {
						if (isblack == true) {
							JOptionPane.showMessageDialog(null, "黑方超时，白方胜利");
						} else {
							JOptionPane.showMessageDialog(null, "白方超时，黑方胜利");
						}
						canPlay = false; // 表示游戏结束;
						flag = false;
					}
					blackMessage = i + "秒";
					if (flag) {
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							prei = i;
							flag = false;
						}
					}
					i--;
				}
			}
		};
		thread.start();
		this.addMouseListener(this);
	}

	static Thread thread = null;

	// 画棋盘界面
	public void paint(Graphics g) {

		// 双缓冲技术

		BufferedImage buf = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);

		// 这个bufferedImage 可以在网上查找相关用法，它是对图像的一种处理。

		Graphics g1 = buf.createGraphics(); // 创建画笔

		g1.setColor(new Color(255, 215, 0));

		g1.fill3DRect(0, 0, 800, 800, true);

		// 棋盘中的线
		g1.setColor(Color.BLACK);
		for (int i = 0; i <= 18; ++i) {
			g1.drawLine(20, 80 + i * 35, 650, 80 + i * 35);
			g1.drawLine(20 + i * 35, 80, 20 + i * 35, 710);
		}

		// 棋盘中的9个星位
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				g1.fillOval(120 + i * 210, 180 + j * 210, 10, 10);
			}

		g1.setFont(new Font("黑体", Font.BOLD, 35));
		g1.drawString("游戏信息:" + message, 20, 70);
		g1.drawRect(20, 715, 180, 40);
		g1.drawRect(240, 715, 180, 40); // 画黑方时间与白方时间字符串的边框
		g1.setFont(new Font("宋体", Font.BOLD, 15));
		g1.drawString("黑方时间: " + blackMessage, 30, 740);
		g1.drawString("白方时间: " + whiteMessage, 250, 740);
		g1.drawRect(663, 80, 110, 50);
		g1.setFont(new Font("黑体", Font.BOLD, 22));
		g1.drawString("重新开始", 670, 110); // 重新开始按钮
		g1.drawRect(663, 150, 110, 50);
		g1.drawString("游戏规则", 670, 180); // 游戏说明按钮
		g1.drawRect(663, 220, 110, 50);
		g1.drawString("游戏设置", 670, 250); // 游戏设置按钮
		g1.drawRect(663, 400, 110, 50);
		g1.drawString("悔棋", 690, 430); // 悔棋
		g1.drawRect(663, 470, 110, 50);
		g1.drawString("和棋", 690, 500); // 和棋
		g1.drawRect(663, 540, 110, 50);
		g1.drawString("认输", 690, 570); // 认输

		// 画棋子
		for (int i = 0; i < 19; ++i)
			for (int j = 0; j < 19; ++j) {
				if (allChess[i][j] == 1) {
					g1.setColor(Color.BLACK);
					g1.fillOval(8 + i * 35, 70 + j * 35, 24, 24);
				}
				if (allChess[i][j] == 2) {
					g1.setColor(Color.WHITE);
					g1.fillOval(8 + i * 35, 70 + j * 35, 24, 24);
				}
			}

		g.drawImage(buf, 0, 0, this);
	}

	public void mousePressed(MouseEvent e) {

		x = e.getX();
		y = e.getY();

		if (canPlay) {
			if (x > 10 && x <= 660 && y >= 70 && y <= 720) {
				// 让鼠标在棋盘范围内
				if ((x - 20) % 35 > 17) {
					x = (x - 20) / 35 + 1;
				} else {
					x = (x - 20) / 35;
				}
				if ((y - 80) % 35 > 17) {
					y = (y - 80) / 35 + 1;
				} else {
					y = (y - 80) / 35;
				}

				// 落子
				if (allChess[x][y] == 0) {// 判断此处是否已有棋子
					thread.interrupt();
					Xposition.add(x);
					Yposition.add(y);
					if (isblack) {
						allChess[x][y] = 1;
						isblack = false;
						message = "白方下子";
					} else {
						allChess[x][y] = 2;
						isblack = true;
						message = "黑方下子";
					}
					thread = new Thread() {
						public void run() {
							i = time;
							boolean flag = true;
							while (flag) {
								if (i < 0) {
									if (isblack == true) {
										JOptionPane.showMessageDialog(null, "黑方超时，白方胜利");
									} else {
										JOptionPane.showMessageDialog(null, "白方超时，黑方胜利");
									}
									canPlay = false; // 表示游戏结束;
									flag = false;
								}
								if (isblack)
									blackMessage = i + "秒";
								else
									whiteMessage = i + "秒";
								i--;
								if (flag) {
									try {
										Thread.sleep(1000);
									} catch (Exception e) {
										prei = i;
										flag = false;
									}
								}
							}
						}
					};
					thread.start();
				}
				this.repaint();

				if (this.isWin()) {

					thread.interrupt();

					if (allChess[x][y] == 1) {

						JOptionPane.showMessageDialog(this, "游戏结束，黑方胜利");

					} else {

						JOptionPane.showMessageDialog(this, "游戏结束，白方胜利");

					}

					canPlay = false; // 表示游戏结束

				}

				if (Xposition.size() == 361) {
					thread.interrupt();
					JOptionPane.showMessageDialog(this, "双方和棋！", "游戏结束", JOptionPane.CLOSED_OPTION);
					canPlay = false;
				}
			}

			// 悔棋
			if (x >= 663 && x <= (663 + 110) && y >= 400 && y <= (400 + 50) && Xposition.size() != 0) {
				thread.interrupt();
				int result = JOptionPane.showConfirmDialog(this, (isblack == true ? "白方悔棋,黑方是否同意？" : "黑方悔棋，白方是否同意？"));
				if (result == 0) {
					allChess[Xposition.get(Xposition.size() - 1)][Yposition.get(Yposition.size() - 1)] = 0;
					Xposition.remove(Xposition.size() - 1);
					Yposition.remove(Yposition.size() - 1);
					if (isblack == true) {
						isblack = false;
						message = "白方下子";
					} else {
						isblack = true;
						message = "黑方下子";
					}
					prei = time;
					this.repaint(); // 重绘棋盘
				}
				thread = new Thread() {
					public void run() {
						i = prei;
						boolean flag = true;
						while (flag) {
							if (i < 0) {
								if (isblack == true) {
									JOptionPane.showMessageDialog(null, "黑方超时，白方胜利");
								} else {
									JOptionPane.showMessageDialog(null, "白方超时，黑方胜利");
								}
								canPlay = false; // 表示游戏结束;
								flag = false;
							}

							if (isblack)
								blackMessage = i + "秒";
							else
								whiteMessage = i + "秒";
							i--;
							if (flag) {
								try {
									Thread.sleep(1000);
								} catch (Exception e) {
									prei = i;
									flag = false;
								}
							}
						}
					}
				};
				thread.start();
			}

			// 和棋
			if (x >= 663 && x <= 773 && y >= 470 && y <= 520) {
				thread.interrupt();
				int result = JOptionPane.showConfirmDialog(this,
						(isblack == true ? "黑方请求和棋,白方是否同意？" : "白方请求和棋，黑方是否同意？"), "提示", JOptionPane.OK_CANCEL_OPTION);
				if (result == 0) {
					JOptionPane.showMessageDialog(this, "双方和棋!", "游戏结束", JOptionPane.CLOSED_OPTION);
					canPlay = false;
				} else {
					thread = new Thread() {
						public void run() {
							i = prei;
							boolean flag = true;
							while (flag) {
								if (i < 0) {
									if (isblack == true) {
										JOptionPane.showMessageDialog(null, "黑方超时，白方胜利");
									} else {
										JOptionPane.showMessageDialog(null, "白方超时，黑方胜利");
									}
									canPlay = false; // 表示游戏结束;
									flag = false;
								}

								if (isblack)
									blackMessage = i + "秒";
								else
									whiteMessage = i + "秒";
								i--;
								if (flag) {
									try {
										Thread.sleep(1000);
									} catch (Exception e) {
										prei = i;
										flag = false;
									}
								}
							}
						}
					};
					thread.start();
				}
			}

			// 认输
			if (x >= 663 && x <= 773 && y >= 540 && y <= 590) {
				thread.interrupt();
				int result = JOptionPane.showConfirmDialog(this, "是否认输?", "提示", JOptionPane.OK_CANCEL_OPTION);
				if (result == 0) {
					JOptionPane.showMessageDialog(this, isblack == true ? "黑方认输，白方获胜!" : "白方认输，黑方获胜!", "游戏结束",
							JOptionPane.CLOSED_OPTION);
					canPlay = false;
				} else {
					thread = new Thread() {
						public void run() {
							i = prei;
							boolean flag = true;
							while (flag) {
								if (i < 0) {
									if (isblack == true) {
										JOptionPane.showMessageDialog(null, "黑方超时，白方胜利");
									} else {
										JOptionPane.showMessageDialog(null, "白方超时，黑方胜利");
									}
									canPlay = false; // 表示游戏结束;
									flag = false;
								}

								if (isblack)
									blackMessage = i + "秒";
								else
									whiteMessage = i + "秒";
								i--;
								if (flag) {
									try {
										Thread.sleep(1000);
									} catch (Exception e) {
										prei = i;
										flag = false;
									}
								}
							}
						}
					};
					thread.start();
				}
			}
		}

		// 重新开始游戏
		if (x >= 663 && x <= (663 + 110) && y >= 80 && y <= (80 + 50)) {
			thread.interrupt();
			int result = JOptionPane.showConfirmDialog(this, "是否重新开始游戏？");

			if (result == 0) {

				restarGame();

			} else {
				if (canPlay) {
					thread = new Thread() {
						public void run() {
							i = prei;
							boolean flag = true;
							while (flag) {
								if (i < 0) {
									if (isblack == true) {
										JOptionPane.showMessageDialog(null, "黑方超时，白方胜利");
									} else {
										JOptionPane.showMessageDialog(null, "白方超时，黑方胜利");
									}
									canPlay = false; // 表示游戏结束;
									flag = false;
								}

								if (isblack)
									blackMessage = i + "秒";
								else
									whiteMessage = i + "秒";
								i--;
								if (flag) {
									try {
										Thread.sleep(1000);
									} catch (Exception e) {
										prei = i;
										flag = false;
									}
								}
							}
						}
					};
					thread.start();
				}
			}
		}

		// 游戏规则
		if (x >= 663 && x <= (663 + 110) && y >= 150 && y <= (150 + 50)) {
			thread.interrupt();
			JOptionPane.showMessageDialog(null, "横竖斜先连成五子者获胜!", "游戏规则", JOptionPane.CLOSED_OPTION);
			if (canPlay) {
				thread = new Thread() {
					public void run() {
						i = prei;
						boolean flag = true;
						while (flag) {
							if (i < 0) {
								if (isblack) {
									JOptionPane.showMessageDialog(null, "黑方超时，白方胜利");
								} else {
									JOptionPane.showMessageDialog(null, "白方超时，黑方胜利");
								}
								canPlay = false; // 表示游戏结束;
								flag = false;
							}

							if (isblack)
								blackMessage = i + "秒";
							else
								whiteMessage = i + "秒";
							i--;
							if (flag) {
								try {
									Thread.sleep(1000);
								} catch (Exception e) {
									prei = i;
									flag = false;
								}
							}
						}
					}
				};
				thread.start();
			}
		}

		// 游戏设置
		if (x >= 663 && x <= (663 + 110) && y >= 220 && y <= (220 + 50)) {
			thread.interrupt();
			while (true) {
				try {
					String s = JOptionPane.showInputDialog(null, "每步棋的最长用时（以秒为单位）: ", "游戏设置",
							JOptionPane.QUESTION_MESSAGE);
					if (s == null)
						break;
					if (Integer.parseInt(s) < 1)
						JOptionPane.showMessageDialog(null, "设定用时需为正整数!", "error", JOptionPane.ERROR_MESSAGE);
					else {
						time = Integer.parseInt(s);
						prei = time;
						blackMessage = time + "秒";
						whiteMessage = time + "秒";
						break;
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "请输入整数时间（单位：s)！", "error", JOptionPane.ERROR_MESSAGE);
				}
			}
			if (canPlay) {
				thread = new Thread() {
					public void run() {
						i = prei;
						boolean flag = true;
						while (flag) {
							if (i < 0) {
								if (isblack == true) {
									JOptionPane.showMessageDialog(null, "黑方超时，白方胜利");
								} else {
									JOptionPane.showMessageDialog(null, "白方超时，黑方胜利");
								}
								canPlay = false; // 表示游戏结束;
								flag = false;
							}

							if (isblack)
								blackMessage = i + "秒";
							else
								whiteMessage = i + "秒";
							i--;
							if (flag) {
								try {
									Thread.sleep(1000);
								} catch (Exception e) {
									prei = i;
									flag = false;
								}
							}
						}
					}
				};
				thread.start();
			}
		}
	}

	// 判断是否已分出胜负
	public boolean isWin() {
		int color = allChess[x][y]; // color = 1 (黑子) color = 2(白子)

		// 判断横向是否有5个棋子相连，特点:即allChess[x][y]中y值相同,count用来保存共有相同颜色多少棋子相连
		int count = this.checkCount(1, 0, color);
		if (count >= 5) {

			return true;

		} else {

			// 判断纵向

			count = this.checkCount(0, 1, color);

			if (count >= 5) {

				return true;

			} else {

				// 判断右上,左下

				count = this.checkCount(1, -1, color);

				if (count >= 5) {

					return true;

				} else {

					// 判断右下,左上

					count = this.checkCount(1, 1, color);

					if (count >= 5) {

						return true;

					}

				}

			}

		}

		return false;

	}

	// 检查棋盘中的五子棋是否五子连城
	public int checkCount(int xChange, int yChange, int color) {

		int count = 1;

		int tempX = xChange;

		int tempy = yChange; // 保存初始值，用于下一轮循环反方向检索

		// 全局变量x,y最初为鼠标点击的坐标,五子连城必包括此棋，从该棋开始进行搜索
		// xchange为1时检查右侧棋子，0时为纵向检查，ychange为1时检查下方棋子，-1则检查上方棋子
		while (x + xChange >= 0 && x + xChange < 19 && y + yChange >= 0 && y + yChange < 19
				&& color == allChess[x + xChange][y + yChange]) {

			count++;// 颜色满足

			if (xChange != 0)
				xChange++;

			if (yChange != 0) {
				if (yChange > 0) {

					yChange++;

				} else {

					yChange--;

				}
			}
		}

		xChange = tempX;

		yChange = tempy; // 恢复初始值

		// xchange为1时检查左侧棋子，0时为纵向检查，ychange为1时检查上方棋子，-1则检查下方棋子
		while (x - xChange >= 0 && x - xChange < 19 && y - yChange >= 0 &&

				y - yChange < 19 && color == allChess[x - xChange][y - yChange]) {

			count++;

			if (xChange != 0) {

				xChange++;

			}

			if (yChange != 0) {

				if (yChange > 0) {

					yChange++;

				} else {

					yChange--;

				}

			}

		}

		return count;

	}

	// 重新开始
	public void restarGame() {

		for (int i = 0; i < 19; i++) {

			for (int j = 0; j < 19; j++) {

				allChess[i][j] = 0; // 清空棋盘的棋子
				Xposition.clear();
				Yposition.clear();// 清空历史战局

			}

		}

		message = "黑方先行";

		blackMessage = time + "秒";

		whiteMessage = time + "秒";

		isblack = true;

		canPlay = true;

		i = time;

		thread = new Thread() {
			public void run() {
				boolean flag = true;
				while (flag) {
					if (i < 0) {
						if (isblack == true) {
							JOptionPane.showMessageDialog(null, "黑方超时，白方胜利");
						} else {
							JOptionPane.showMessageDialog(null, "白方超时，黑方胜利");
						}
						canPlay = false; // 表示游戏结束;
						flag = false;
					}

					if (isblack)
						blackMessage = i + "秒";
					else
						whiteMessage = i + "秒";
					i--;
					if (flag) {
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							prei = i;
							flag = false;
						}
					}
				}
			}
		};
		thread.start();

		this.repaint();

	}

	public static void main(String[] args) {
		final Third s = new Third();
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(100);
						s.repaint();
					} catch (Exception e) {
					}
				}
			}
		}.start();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
