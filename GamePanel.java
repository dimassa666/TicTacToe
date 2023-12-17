import java.awt.Dimension;     //Import Dimension class untuk merangkum lebar dan tinggi suatu komponen/objek dalam ruang 2 dimensi
import java.awt.Color;		   //Import Color class untuk menggunakan 'color' class pada paket java.awt
import java.awt.event.*;	   //Paket ini berisi berbagai class yang digunakan dalam pemrograman GUI.
import java.awt.Graphics;	   //Import Graphics class untuk menyediakan rangkaian API untuk membuat GUI dan melakukan operasi grafis
import java.awt.Image;		   //Import Image class untuk menyediakan cara bekerja dengan gambar dalam platform
import java.awt.Font;		   //Import Font class untuk menyediakan cara bekerja dengan teks dalam platform
import java.awt.Toolkit;	   //Import Toolkit class untuk bekerja dengan gambar dan akses properti sistem
import javax.swing.JButton;    //Import JButton class untuk membuat tombol yang bisa diklik dalam GUI
import javax.swing.JFrame;	   //Import Jframe class untuk membuat main window dari java swing application
import javax.swing.JPanel;	   //Import Jpanel class menyediakan sebuah area dimana kita bisa membuat group dan mengatur komponen lain seperti tombol,label, atau grafis kustom
import javax.swing.ImageIcon;  //Import ImageIcon class untuk memuat dan menampilkan gambar dalam aplikasi Swing
import java.io.*;			   //Import io.* class untuk menyediakan semua class yang bisa melakukan operasi input-output
import java.util.Scanner;	   //Import Scanner class untuk membaca input dari bermacam sumber seperti console,file

public class GamePanel extends JPanel implements ActionListener { //Membuat class publik yang bisa diakses oleh class lain dengan nama Gamepanel yang diextend dari class JPanel ddan mengimplementasikan interface ActionListener

	// logic variables
	boolean playerX;						//Membuat variable boolean pemain pertama
	boolean gameDone = false;				//Membuat variable boolean gamedone dan sekaligus mengisinya dengan false.
	int winner = -1;						//Membuat variable int dengan nilai -1 karena belum diketahui pemenangnya (Flag)
	int player1wins = 0, player2wins = 0;   //Membuat variable int player1wins dan player2wins dengan nilai masing-masing 0
	int[][] board = new int[5][5];			//Membuat variable int board dengan dimensi 5x5 (secara sistem terbaca 6x6) untuk meletakkan value kepada masing" kolom & baris pada permainan

	// paint variables
	int lineWidth = 5;						//Membuat variable int lineWidth (lebar garis) yang bernilai 5 digunakan untuk garis papan tic tac toe dibawah nanti
	int lineLength = 270;					//Membuat variable int lineLength (panjang garis) yang bernilai 270 digunakan untuk garis papan tic tac toe dibawah nanti
	int x = 15, y = 100;					//Membuat variable int x yang bernilai 12 dan y yang bernilai 100 untuk mempermudah membangun GUI tic tac toe
	int offset = 95;						//Membuat variable int offset yang bernilai 95 untuk menentukan jarak antara garis 1 dan garis lainnya
	int a = 0;								//Membuat variable int a dengan nilai 0
	int b = 5;								//Membuat variable int b dengan nilai 5
	int selX = 0;							//Membuat variable int selX dengan nilai 0
	int selY = 0;							//Membuat variable int selY dengan nilai 0

	// COLORS
	Color turtle = new Color(0x80bdab);		//Mendeklarasikan variable warna dengan nama turtle menggunakan kode hex berwarna hijau
	Color orange = new Color(0xfdcb9e);		//Mendeklarasikan variable warna dengan nama orange menggunakan kode hex berwarna orange
	Color offwhite = new Color(0xf7f7f7);	//Mendeklarasikan variable warna dengan nama offwhite menggunakan kode hex berwarna putih asap
	Color darkgray = new Color(0x3f3f44);	//Mendeklarasikan variable warna dengan nama darkgray menggunakan kode hex berwarna abu-abu gelap

	// COMPONENTS
	JButton jButton; //Membuat tombol

	// CONSTRUCTOR
	public GamePanel() { //Constructor dari kelas GamePanel.
		Dimension size = new Dimension(420, 300); //Konfigurasi ukuran objek 'Dimension' dengan lebar 420 pixels dan tinggi 300 pixels
		setPreferredSize(size);								   //Mengatur ukuran yang diinginkan merujuk kepada 'size'
		setMaximumSize(size);								   //Mengatur ukuran maksimum yang diinginkan merujuk kepada 'size'
		setMinimumSize(size);								   //Mengatur ukuran minimum yang diinginkan merujuk kepada 'size'
		addMouseListener(new XOListener());					   //Membuat pendeteksi mouse dalam panel dengan nama XOListener
		jButton = new JButton("Play Again?");			   //Membuat tombol bertuliskan "Play Again?"
		jButton.addActionListener(this);					   //Menambahkan listener untuk tombolnya
		jButton.setBounds(315, 210, 100, 30); //Mengatur letak dan dimensi tombol
		add(jButton);										   //Menambahkan tombol ke panel
		resetGame();										   //Memanggil resetGame() method untuk inisiasi atau restart game
	}
	public void resetGame() { //Method untuk reset game / inisiasi game
		playerX = true; 					  //Mengisi variable playerX dengan true
		winner = -1;						  //Mengisi variable winner dengan -1 karena belum diketahui
		gameDone = false;					  //Mengisi variable gameDone dengan false
		for (int i = 0; i < 5; i++) {		  //Melakukan loop dengan variable i dari 0 - 4
			for (int j = 0; j < 5; j++) {     //Melakukan nested loop dengan variable j dari 0 - 4
				board[i][j] = 0; 			  //Mengosongkan setiap kolom dan baris
			}
		}
		getJButton().setVisible(false); //Menyembunyikan tombol yang telah dibuat dikarenakan permainan belum selesai
	}

	public void paintComponent(Graphics page) { //Method dengan parameter Graphics bernama page yang otomatis diarahkan ke 'paintComponent' method. Objek ini menyediakan method yang diperlukan untuk menggambar bentuk, text, dan gambar pada komponen
		super.paintComponent(page);	//untuk memastikan rendering yang tepat dan menghindari potensi masalah dengan mekanisme painting Swing.
		drawBoard(page);			//Memanggil method drawBoard
		drawUI(page);
		drawGame(page);
	}

	public void drawBoard(Graphics page) {
		setBackground(turtle);
		page.setColor(darkgray);
		page.fillRoundRect(x, y, lineLength, lineWidth, 5, 30);
		page.fillRoundRect(x, y + offset, lineLength, lineWidth, 5, 30);
		page.fillRoundRect(y, x, lineWidth, lineLength, 30, 5);
		page.fillRoundRect(y + offset, x, lineWidth, lineLength, 30, 5);
	}

	public void drawUI(Graphics page) {
		// SET COLOR AND FONT
		page.setColor(darkgray);
		page.fillRect(300, 0, 120, 300);
		Font font = new Font("Helvetica", Font.PLAIN, 20);
		page.setFont(font);

		// SET WIN COUNTER
		page.setColor(offwhite);
		page.drawString("Win Count", 310, 30);
		page.drawString(": " + player1wins, 362, 70);
		page.drawString(": " + player2wins, 362, 105);

		// DRAW score X
		ImageIcon xIcon = new ImageIcon("orangex.png");
		Image xImg = xIcon.getImage();
		Image newXImg = xImg.getScaledInstance(27, 27, java.awt.Image.SCALE_SMOOTH);
		ImageIcon newXIcon = new ImageIcon(newXImg);
		page.drawImage(newXIcon.getImage(), 44 + offset * 1 + 190, 47 + offset * 0, null);

		// DRAW score O
		page.setColor(offwhite);
		page.fillOval(43 + 190 + offset, 80, 30, 30);
		page.setColor(darkgray);
		page.fillOval(49 + 190 + offset, 85, 19, 19);

		// DRAW WHOS TURN or WINNER
		page.setColor(offwhite);
		Font font1 = new Font("Serif", Font.ITALIC, 18);
		page.setFont(font1);

		if (gameDone) {
			if (winner == 1) { // x
				page.drawString("Pemenangnya", 310, 150);
				page.drawImage(xImg, 335, 160, null);
			} else if (winner == 2) { // o
				page.drawString("Pemenangnya", 310, 150);
				page.setColor(offwhite);
				page.fillOval(332, 160, 50, 50);
				page.setColor(darkgray);
				page.fillOval(342, 170, 30, 30);
			} else if (winner == 3) { // tie
				page.drawString("Seri", 345, 160);
			}
		} else {
			Font font2 = new Font("Serif", Font.ITALIC, 20);
			page.setFont(font2);
			page.drawString("Sekarang", 327, 150);
			if (playerX) {
				page.drawString("Giliran X", 325, 170);
			} else {
				page.drawString("Giliran O", 325, 170);
			}
		}
		// DRAW LOGO
		Image Wayang = Toolkit.getDefaultToolkit().getImage("wayang.png");
		page.drawImage(Wayang, 335, 215, 60, 60, this);
		Font c = new Font("Courier", Font.BOLD + Font.CENTER_BASELINE, 13);
		page.setFont(c);
		page.drawString("Selamat Bermain", 300, 280);
	}

	public void drawGame(Graphics page) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == 0) {

				} else if (board[i][j] == 1) {
					ImageIcon xIcon = new ImageIcon("orangex.png");
					Image xImg = xIcon.getImage();
					page.drawImage(xImg, 30 + offset * i, 30 + offset * j, null);
				} else if (board[i][j] == 2) {
					page.setColor(offwhite);
					page.fillOval(30 + offset * i, 30 + offset * j, 50, 50);
					page.setColor(turtle);
					page.fillOval(40 + offset * i, 40 + offset * j, 30, 30);
				}
			}
		}
		repaint();
	}

	public void checkWinner() {  //Function untuk mengecek pemenang / seri
		if (gameDone == true) {  //Mengecek jika variable gamedone = true
			System.out.print("gameDone"); //Maka gamenya selesai
			return; //mengembalikan ke semula
		}
		int temp = -1; //Membuat variable temporary untuk menampung pemenangnya
		   
			// Check rows
			for (int i = 0; i < 5; i++) { //Membuat kondisi loop untuk mengecek dari index i=0 ke index i=4
				if (board[i][0] == board[i][1] && board[i][1] == board[i][2]     ////
					&& board[i][2] == board[i][3] && board[i][3] == board[i][4]  //// Mengecek kondisi board [i][0] sampai board[i][4] apakah sama
					&& board[i][0] != 0) {										 //// Isi board [i][0] tidak sama dengan 0
					temp = board[i][0];	// Jika memenuhi maka memasukkan pemenang pada variable temp.
					break; //Keluar dari loop.
				}
			}
		
			// Check columns
			for (int j = 0; j < 5; j++) { //Membuat kondisi loop untuk mengecek dari index i=0 ke index i=4
				if (board[0][j] == board[1][j] && board[1][j] == board[2][j]    ////
					&& board[2][j] == board[3][j] && board[3][j] == board[4][j] //// Mengecek kondisi board [0][j] sampai board[4][j] apakah sama
					&& board[0][j] != 0) {										//// isi Board [0][j] tidak sama denagn 0
					temp = board[0][j]; // Jika memenuhi maka memasukkan pemenang pada variable temp.
					break; //Keluar dari loop.
				}
			}
		
			// Check diagonals
			if (board[0][0] == board[1][1] && board[1][1] == board[2][2]     ////
				&& board[2][2] == board[3][3] && board[3][3] == board[4][4]  //// Mengecek kondisi board [0][0] sampai board [4][4] apakah sama
				&& board[0][0] != 0) {										 //// isi board [0][0] tidak sama dengan 0
				temp = board[0][0]; //Jika memenuhi maka memasukkan pemenang pada variable temp.

			} else if (board[0][4] == board[1][3] && board[1][3] == board[2][2]    ///
					   && board[2][2] == board[3][1] && board[3][1] == board[4][0] /// Mengecek kondisi board diagonal sisi lain apakah sama
					   && board[0][4] != 0) {									   /// isi board[0][4] tidak sama dengan 0
				temp = board[0][4]; //jika memenuhi maka memasukkan pemenang pada variable temp.
			}
		
			// Check for a tie
			boolean notDone = false;
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (board[i][j] == 0) {
						notDone = true;
						break;
					}
				}
			}
		
			if (temp > 0) {
				winner = temp;
				if (winner == 1) {
					player1wins++;
					System.out.println("Winner is X");
				} else if (winner == 2) {
					player2wins++;
					System.out.println("Winner is O");
				} else if (winner == 3) {
					System.out.println("It's a tie");
				}
				gameDone = true;
				getJButton().setVisible(true);
			} else if (!notDone) {
				// All spots are filled, but no winner
				winner = 3; // Tie
				System.out.println("It's a tie");
				gameDone = true;
				getJButton().setVisible(true);
			}
		}
	public JButton getJButton() {	return jButton; }

	public void setPlayerXWins(int a) {
		player1wins = a;
	}

	public void setPlayerOWins(int a) {
		player2wins = a;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Tic Tac Toe");
		frame.getContentPane();

		GamePanel gamePanel = new GamePanel();
		frame.add(gamePanel);

		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				try {
					File file = new File("score.txt");
					Scanner sc = new Scanner(file);
					gamePanel.setPlayerXWins(Integer.parseInt(sc.nextLine()));
					gamePanel.setPlayerOWins(Integer.parseInt(sc.nextLine()));
					sc.close();
				} catch (IOException io) {
				}
			}

			public void windowClosing(WindowEvent e) {
				try {
					PrintWriter pw = new PrintWriter("score.txt");
					pw.write("");
					pw.write(gamePanel.player1wins + "\n");
					pw.write(gamePanel.player2wins + "\n");
					pw.close();
				} catch (FileNotFoundException e1) { }
			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	private class XOListener implements MouseListener {

		public void mouseClicked(MouseEvent event) {
			selX = -1;
			selY = -1;
			if (gameDone == false) {
				a = event.getX();
				b = event.getY();
				int selX = 0, selY = 0;
				if (a > 12 && a < 99) {
					selX = 0;
				} else if (a > 103 && a < 195) {
					selX = 1;
				} else if (a > 200 && a < 287) {
					selX = 2;
				} else {
					selX = -1;
				}

				if (b > 12 && b < 99) {
					selY = 0;
				} else if (b > 103 && b < 195) {
					selY = 1;
				} else if (b > 200 && b < 287) {
					selY = 2;
				} else {
					selY = -1;
				}
				if (selX != -1 && selY != -1) {

					if (board[selX][selY] == 0) {
						if (playerX) {
							board[selX][selY] = 1;
							playerX = false;
						} else {
							board[selX][selY] = 2;
							playerX = true;
						}
						checkWinner();
						System.out.println(" CLICK= x:" + a + ",y: " + b + "; selX,selY: " + selX + "," + selY);
					}
				} else {
					System.out.println("invalid click");
				}
			}
		}

		public void mouseReleased(MouseEvent event) {}
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
		public void mousePressed(MouseEvent event) {}
	}

	@Override
	public void actionPerformed(ActionEvent e) { resetGame(); }

}