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

	// ������Ϸ����

	/*
	 * ��Ļ�ֱ�����:
	 * 
	 * int w = f.getToolkit().getScreenSize().width;//���
	 * 
	 * int h = f.getToolkit().getScreenSize().height;//�߶�
	 * 
	 * Toolkit.getDefaultToolkit().getScreenSize().width//�������ͬ������������ȡ��Ļ�Ŀ�ߣ�
	 * 
	 * this.setLocation((width - 500) / 2 , (height - 500) / 2);
	 * ʹ�����ܹ�������ʾ���������������ۡ�
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	int height = Toolkit.getDefaultToolkit().getScreenSize().height;

	int x, y;// �����������

	int[][] allChess = new int[19][19]; // ���������������ӣ�0��ʾ���ӣ�1��ʾ���ӣ�2��ʾ����
	static final List<Integer> Xposition = new ArrayList<Integer>();
	static final List<Integer> Yposition = new ArrayList<Integer>();// ��¼ս��

	static boolean isblack = true; // ������ʾ���ӻ��ǰ��ӣ� true��ʾ���� false��ʾ����
	static boolean canPlay = true; // ������ʾ��ǰ��Ϸ�Ƿ����
	static int time = 8;
	static int i = time;
	static int prei = i;

	String message = "�ڷ�����";
	String blackMessage = "������";
	String whiteMessage = "������";

	public Third() {

		this.setTitle("������");

		this.setSize(800, 800);

		this.setLocation((width - 1000) / 2, (height - 1000) / 2);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setResizable(false); // ���ô��ڲ��ɸı䣬�̶����ڴ�С

		this.setVisible(true);

		this.repaint(); // java��repaint()���ػ�component�ķ������������paint�����Խ���������л滭

		while (true) {
			try {
				String s = JOptionPane.showInputDialog(null, "ÿ��������ʱ������Ϊ��λ��: ", "��Ϸ����", JOptionPane.QUESTION_MESSAGE);
				if (s == null) {
					JOptionPane.showMessageDialog(null, "������ÿ��������ʱ", "����", JOptionPane.WARNING_MESSAGE);
					continue;
				}
				time = Integer.parseInt(s);
				if (time < 1)
					JOptionPane.showMessageDialog(null, "�趨��ʱ��Ϊ������!", "error", JOptionPane.ERROR_MESSAGE);
				else
					break;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "����������ʱ�䣨��λ��s)��", "error", JOptionPane.ERROR_MESSAGE);
			}
		}
		whiteMessage = time + "��";
		thread = new Thread() {
			public void run() {
				i = time;
				boolean flag = true;
				while (flag) {
					if (i < 0) {
						if (isblack == true) {
							JOptionPane.showMessageDialog(null, "�ڷ���ʱ���׷�ʤ��");
						} else {
							JOptionPane.showMessageDialog(null, "�׷���ʱ���ڷ�ʤ��");
						}
						canPlay = false; // ��ʾ��Ϸ����;
						flag = false;
					}
					blackMessage = i + "��";
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

	// �����̽���
	public void paint(Graphics g) {

		// ˫���弼��

		BufferedImage buf = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);

		// ���bufferedImage ���������ϲ�������÷������Ƕ�ͼ���һ�ִ���

		Graphics g1 = buf.createGraphics(); // ��������

		g1.setColor(new Color(255, 215, 0));

		g1.fill3DRect(0, 0, 800, 800, true);

		// �����е���
		g1.setColor(Color.BLACK);
		for (int i = 0; i <= 18; ++i) {
			g1.drawLine(20, 80 + i * 35, 650, 80 + i * 35);
			g1.drawLine(20 + i * 35, 80, 20 + i * 35, 710);
		}

		// �����е�9����λ
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				g1.fillOval(120 + i * 210, 180 + j * 210, 10, 10);
			}

		g1.setFont(new Font("����", Font.BOLD, 35));
		g1.drawString("��Ϸ��Ϣ:" + message, 20, 70);
		g1.drawRect(20, 715, 180, 40);
		g1.drawRect(240, 715, 180, 40); // ���ڷ�ʱ����׷�ʱ���ַ����ı߿�
		g1.setFont(new Font("����", Font.BOLD, 15));
		g1.drawString("�ڷ�ʱ��: " + blackMessage, 30, 740);
		g1.drawString("�׷�ʱ��: " + whiteMessage, 250, 740);
		g1.drawRect(663, 80, 110, 50);
		g1.setFont(new Font("����", Font.BOLD, 22));
		g1.drawString("���¿�ʼ", 670, 110); // ���¿�ʼ��ť
		g1.drawRect(663, 150, 110, 50);
		g1.drawString("��Ϸ����", 670, 180); // ��Ϸ˵����ť
		g1.drawRect(663, 220, 110, 50);
		g1.drawString("��Ϸ����", 670, 250); // ��Ϸ���ð�ť
		g1.drawRect(663, 400, 110, 50);
		g1.drawString("����", 690, 430); // ����
		g1.drawRect(663, 470, 110, 50);
		g1.drawString("����", 690, 500); // ����
		g1.drawRect(663, 540, 110, 50);
		g1.drawString("����", 690, 570); // ����

		// ������
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
				// ����������̷�Χ��
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

				// ����
				if (allChess[x][y] == 0) {// �жϴ˴��Ƿ���������
					thread.interrupt();
					Xposition.add(x);
					Yposition.add(y);
					if (isblack) {
						allChess[x][y] = 1;
						isblack = false;
						message = "�׷�����";
					} else {
						allChess[x][y] = 2;
						isblack = true;
						message = "�ڷ�����";
					}
					thread = new Thread() {
						public void run() {
							i = time;
							boolean flag = true;
							while (flag) {
								if (i < 0) {
									if (isblack == true) {
										JOptionPane.showMessageDialog(null, "�ڷ���ʱ���׷�ʤ��");
									} else {
										JOptionPane.showMessageDialog(null, "�׷���ʱ���ڷ�ʤ��");
									}
									canPlay = false; // ��ʾ��Ϸ����;
									flag = false;
								}
								if (isblack)
									blackMessage = i + "��";
								else
									whiteMessage = i + "��";
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

						JOptionPane.showMessageDialog(this, "��Ϸ�������ڷ�ʤ��");

					} else {

						JOptionPane.showMessageDialog(this, "��Ϸ�������׷�ʤ��");

					}

					canPlay = false; // ��ʾ��Ϸ����

				}

				if (Xposition.size() == 361) {
					thread.interrupt();
					JOptionPane.showMessageDialog(this, "˫�����壡", "��Ϸ����", JOptionPane.CLOSED_OPTION);
					canPlay = false;
				}
			}

			// ����
			if (x >= 663 && x <= (663 + 110) && y >= 400 && y <= (400 + 50) && Xposition.size() != 0) {
				thread.interrupt();
				int result = JOptionPane.showConfirmDialog(this, (isblack == true ? "�׷�����,�ڷ��Ƿ�ͬ�⣿" : "�ڷ����壬�׷��Ƿ�ͬ�⣿"));
				if (result == 0) {
					allChess[Xposition.get(Xposition.size() - 1)][Yposition.get(Yposition.size() - 1)] = 0;
					Xposition.remove(Xposition.size() - 1);
					Yposition.remove(Yposition.size() - 1);
					if (isblack == true) {
						isblack = false;
						message = "�׷�����";
					} else {
						isblack = true;
						message = "�ڷ�����";
					}
					prei = time;
					this.repaint(); // �ػ�����
				}
				thread = new Thread() {
					public void run() {
						i = prei;
						boolean flag = true;
						while (flag) {
							if (i < 0) {
								if (isblack == true) {
									JOptionPane.showMessageDialog(null, "�ڷ���ʱ���׷�ʤ��");
								} else {
									JOptionPane.showMessageDialog(null, "�׷���ʱ���ڷ�ʤ��");
								}
								canPlay = false; // ��ʾ��Ϸ����;
								flag = false;
							}

							if (isblack)
								blackMessage = i + "��";
							else
								whiteMessage = i + "��";
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

			// ����
			if (x >= 663 && x <= 773 && y >= 470 && y <= 520) {
				thread.interrupt();
				int result = JOptionPane.showConfirmDialog(this,
						(isblack == true ? "�ڷ��������,�׷��Ƿ�ͬ�⣿" : "�׷�������壬�ڷ��Ƿ�ͬ�⣿"), "��ʾ", JOptionPane.OK_CANCEL_OPTION);
				if (result == 0) {
					JOptionPane.showMessageDialog(this, "˫������!", "��Ϸ����", JOptionPane.CLOSED_OPTION);
					canPlay = false;
				} else {
					thread = new Thread() {
						public void run() {
							i = prei;
							boolean flag = true;
							while (flag) {
								if (i < 0) {
									if (isblack == true) {
										JOptionPane.showMessageDialog(null, "�ڷ���ʱ���׷�ʤ��");
									} else {
										JOptionPane.showMessageDialog(null, "�׷���ʱ���ڷ�ʤ��");
									}
									canPlay = false; // ��ʾ��Ϸ����;
									flag = false;
								}

								if (isblack)
									blackMessage = i + "��";
								else
									whiteMessage = i + "��";
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

			// ����
			if (x >= 663 && x <= 773 && y >= 540 && y <= 590) {
				thread.interrupt();
				int result = JOptionPane.showConfirmDialog(this, "�Ƿ�����?", "��ʾ", JOptionPane.OK_CANCEL_OPTION);
				if (result == 0) {
					JOptionPane.showMessageDialog(this, isblack == true ? "�ڷ����䣬�׷���ʤ!" : "�׷����䣬�ڷ���ʤ!", "��Ϸ����",
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
										JOptionPane.showMessageDialog(null, "�ڷ���ʱ���׷�ʤ��");
									} else {
										JOptionPane.showMessageDialog(null, "�׷���ʱ���ڷ�ʤ��");
									}
									canPlay = false; // ��ʾ��Ϸ����;
									flag = false;
								}

								if (isblack)
									blackMessage = i + "��";
								else
									whiteMessage = i + "��";
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

		// ���¿�ʼ��Ϸ
		if (x >= 663 && x <= (663 + 110) && y >= 80 && y <= (80 + 50)) {
			thread.interrupt();
			int result = JOptionPane.showConfirmDialog(this, "�Ƿ����¿�ʼ��Ϸ��");

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
										JOptionPane.showMessageDialog(null, "�ڷ���ʱ���׷�ʤ��");
									} else {
										JOptionPane.showMessageDialog(null, "�׷���ʱ���ڷ�ʤ��");
									}
									canPlay = false; // ��ʾ��Ϸ����;
									flag = false;
								}

								if (isblack)
									blackMessage = i + "��";
								else
									whiteMessage = i + "��";
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

		// ��Ϸ����
		if (x >= 663 && x <= (663 + 110) && y >= 150 && y <= (150 + 50)) {
			thread.interrupt();
			JOptionPane.showMessageDialog(null, "����б�����������߻�ʤ!", "��Ϸ����", JOptionPane.CLOSED_OPTION);
			if (canPlay) {
				thread = new Thread() {
					public void run() {
						i = prei;
						boolean flag = true;
						while (flag) {
							if (i < 0) {
								if (isblack) {
									JOptionPane.showMessageDialog(null, "�ڷ���ʱ���׷�ʤ��");
								} else {
									JOptionPane.showMessageDialog(null, "�׷���ʱ���ڷ�ʤ��");
								}
								canPlay = false; // ��ʾ��Ϸ����;
								flag = false;
							}

							if (isblack)
								blackMessage = i + "��";
							else
								whiteMessage = i + "��";
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

		// ��Ϸ����
		if (x >= 663 && x <= (663 + 110) && y >= 220 && y <= (220 + 50)) {
			thread.interrupt();
			while (true) {
				try {
					String s = JOptionPane.showInputDialog(null, "ÿ��������ʱ������Ϊ��λ��: ", "��Ϸ����",
							JOptionPane.QUESTION_MESSAGE);
					if (s == null)
						break;
					if (Integer.parseInt(s) < 1)
						JOptionPane.showMessageDialog(null, "�趨��ʱ��Ϊ������!", "error", JOptionPane.ERROR_MESSAGE);
					else {
						time = Integer.parseInt(s);
						prei = time;
						blackMessage = time + "��";
						whiteMessage = time + "��";
						break;
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "����������ʱ�䣨��λ��s)��", "error", JOptionPane.ERROR_MESSAGE);
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
									JOptionPane.showMessageDialog(null, "�ڷ���ʱ���׷�ʤ��");
								} else {
									JOptionPane.showMessageDialog(null, "�׷���ʱ���ڷ�ʤ��");
								}
								canPlay = false; // ��ʾ��Ϸ����;
								flag = false;
							}

							if (isblack)
								blackMessage = i + "��";
							else
								whiteMessage = i + "��";
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

	// �ж��Ƿ��ѷֳ�ʤ��
	public boolean isWin() {
		int color = allChess[x][y]; // color = 1 (����) color = 2(����)

		// �жϺ����Ƿ���5�������������ص�:��allChess[x][y]��yֵ��ͬ,count�������湲����ͬ��ɫ������������
		int count = this.checkCount(1, 0, color);
		if (count >= 5) {

			return true;

		} else {

			// �ж�����

			count = this.checkCount(0, 1, color);

			if (count >= 5) {

				return true;

			} else {

				// �ж�����,����

				count = this.checkCount(1, -1, color);

				if (count >= 5) {

					return true;

				} else {

					// �ж�����,����

					count = this.checkCount(1, 1, color);

					if (count >= 5) {

						return true;

					}

				}

			}

		}

		return false;

	}

	// ��������е��������Ƿ���������
	public int checkCount(int xChange, int yChange, int color) {

		int count = 1;

		int tempX = xChange;

		int tempy = yChange; // �����ʼֵ��������һ��ѭ�����������

		// ȫ�ֱ���x,y���Ϊ�����������,�������Ǳذ������壬�Ӹ��忪ʼ��������
		// xchangeΪ1ʱ����Ҳ����ӣ�0ʱΪ�����飬ychangeΪ1ʱ����·����ӣ�-1�����Ϸ�����
		while (x + xChange >= 0 && x + xChange < 19 && y + yChange >= 0 && y + yChange < 19
				&& color == allChess[x + xChange][y + yChange]) {

			count++;// ��ɫ����

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

		yChange = tempy; // �ָ���ʼֵ

		// xchangeΪ1ʱ���������ӣ�0ʱΪ�����飬ychangeΪ1ʱ����Ϸ����ӣ�-1�����·�����
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

	// ���¿�ʼ
	public void restarGame() {

		for (int i = 0; i < 19; i++) {

			for (int j = 0; j < 19; j++) {

				allChess[i][j] = 0; // ������̵�����
				Xposition.clear();
				Yposition.clear();// �����ʷս��

			}

		}

		message = "�ڷ�����";

		blackMessage = time + "��";

		whiteMessage = time + "��";

		isblack = true;

		canPlay = true;

		i = time;

		thread = new Thread() {
			public void run() {
				boolean flag = true;
				while (flag) {
					if (i < 0) {
						if (isblack == true) {
							JOptionPane.showMessageDialog(null, "�ڷ���ʱ���׷�ʤ��");
						} else {
							JOptionPane.showMessageDialog(null, "�׷���ʱ���ڷ�ʤ��");
						}
						canPlay = false; // ��ʾ��Ϸ����;
						flag = false;
					}

					if (isblack)
						blackMessage = i + "��";
					else
						whiteMessage = i + "��";
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
