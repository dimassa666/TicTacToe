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
		drawUI(page);				//memanggil method drawui
		drawGame(page);				//memanggil method drawgame
	}

	public void drawBoard(Graphics page) {
		setBackground(turtle);//Mengatur warna latar belakang panel dengan warna yang telah diinisialisasi sebelumnya dengan Color turtle.
		page.setColor(darkgray);//Mengatur warna untuk menggambar elemen-elemen papan permainan, dalam hal ini, warna garis menggunakan darkgray.
		page.fillRoundRect(x, y, lineLength, lineWidth, 5, 30);//Menggambar kotak dengan sudut yang dibulatkan (rounded rectangle) sebagai dasar papan permainan. Parameter-parameter menyediakan koordinat dan ukuran kotak, serta radius sudut bulatan.
		page.fillRoundRect(x, y + offset, lineLength, lineWidth, 5, 30);//Menggambar garis horizontal tengah papan permainan dengan parameter yang serupa.
		page.fillRoundRect(y, x, lineWidth, lineLength, 30, 5);//Menggambar garis vertikal kiri papan permainan dengan parameter yang serupa.
		page.fillRoundRect(y + offset, x, lineWidth, lineLength, 30, 5);//enggambar garis vertikal kanan papan permainan dengan parameter yang serupa.
	}

	public void drawUI(Graphics page) {
		// SET COLOR AND FONT//metode buat set color dan font
		page.setColor(darkgray);// Mengatur warna latar belakang
		page.fillRect(300, 0, 120, 300); // Menggambar latar belakang untuk UI
		Font font = new Font("Helvetica", Font.PLAIN, 20);// Membuat objek Font dengan jenis "Helvetica", style Font.PLAIN, dan ukuran
		page.setFont(font);// Mengatur font untuk UI

		// SET WIN COUNTER
		page.setColor(offwhite); // Mengatur warna untuk teks hitungan kemenangan
		page.drawString("Win Count", 310, 30);// Menggambar teks "Win Count"
		page.drawString(": " + player1wins, 362, 70);// Menggambar jumlah kemenangan pemain X
		page.drawString(": " + player2wins, 362, 105);// Menggambar jumlah kemenangan pemain O

		// DRAW score X
		ImageIcon xIcon = new ImageIcon("orangex.png");// Membuat objek ImageIcon dengan gambar "orangex.png"
		Image xImg = xIcon.getImage();// Mengambil gambar dari ImageIco
		Image newXImg = xImg.getScaledInstance(27, 27, java.awt.Image.SCALE_SMOOTH);// Menyesuaikan ukuran gambar X
		ImageIcon newXIcon = new ImageIcon(newXImg);// Membuat ImageIcon baru dengan gambar yang telah diubah ukurannya
		page.drawImage(newXIcon.getImage(), 44 + offset * 1 + 190, 47 + offset * 0, null);// Menggambar simbol X dengan koordinat yang ditentukan

		// DRAW score O
		ImageIcon oIcon = new ImageIcon("orangeo.png"); 									//Membuat objek ImageIcon dengan gambar "orangeo.png"
		Image oImg = oIcon.getImage();																//Mengambil gambar dari ImageIcon
		Image newOIMG = oImg.getScaledInstance(27, 27, java.awt.Image.SCALE_SMOOTH);	//Menyesuaikan ukuran gambar o
		ImageIcon newOIcon = new ImageIcon(newOIMG);												//Membuat ImageIcon baru dengan gambar yang telah diubah ukurannya
		page.drawImage(newOIcon.getImage(), 44 + offset * 1 + 190,85 + offset * 0, null);	//Menggambar simbol O dengan koordinat yang ditentukan
		
		// DRAW WHOS TURN or WINNER
		page.setColor(offwhite); // Mengatur warna untuk teks dan simbol pemenang/giliran
		Font font1 = new Font("Serif", Font.ITALIC, 18);// Membuat objek Font untuk teks pemenang/giliran
		page.setFont(font1);// Mengatur font untuk teks pemenang/giliran

		if (gameDone) {
			if (winner == 1) { // x // Jika pemain X menang
				page.drawString("Pemenangnya", 310, 150);// Jika permainan sudah selesai
				page.drawImage(xImg, 335, 160, null); // Menggambar simbol X
			} else if (winner == 2) { // o
				page.drawString("Pemenangnya", 310, 150);// Menampilkan teks "Pemenangnya"
				page.setColor(offwhite);// Menggambar lingkaran putih
				page.fillOval(332, 160, 50, 50);// Menggambar lingkaran ptuih
				page.setColor(darkgray); // Jika permainan seri
				page.fillOval(342, 170, 30, 30);// Menggambar lingkaran di atas lingkaran putih
			} else if (winner == 3) { // tie  // Jika permainan seri
				page.drawString("Seri", 345, 160);// Menampilkan teks "Seri"
			}
		} else {  // Jika permainan masih berlangsung
			Font font2 = new Font("Serif", Font.ITALIC, 20); // Membuat objek Font untuk teks giliran
			page.setFont(font2);  // Mengatur font untuk teks giliran
			page.drawString("Sekarang", 327, 150); // Menampilkan teks "Sekarang"
			if (playerX) {
				page.drawString("Giliran X", 325, 170);  // Menampilkan teks "Giliran X"
			} else {
				page.drawString("Giliran O", 325, 170); // Menampilkan teks "Giliran O"
			}
		}
		// DRAW LOGO
		Image Wayang = Toolkit.getDefaultToolkit().getImage("wayang.png"); // Membuat objek gambar logo
		page.drawImage(Wayang, 335, 215, 60, 60, this); // Menggambar logo
		Font c = new Font("Courier", Font.BOLD + Font.CENTER_BASELINE, 13); // Membuat objek Font untuk teks selamat bermain
		page.setFont(c); // Mengatur font untuk teks selamat bermain 
		page.drawString("Selamat Bermain", 300, 280); // Menampilkan teks "Selamat Bermain"
	}

	public void drawGame(Graphics page) {
		// Loop melalui setiap sel pada papan permainan
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				// Jika nilai sel pada papan permainan adalah 0 (kosong), tidak ada gambar yang digambar
				if (board[i][j] == 0) {
					 // Kosong

				} else if (board[i][j] == 1) { // Jika nilai sel adalah 1 (mewakili pemain X)
                // Menggambar simbol X pada posisi tertentu
					ImageIcon xIcon = new ImageIcon("orangex.png");
					Image xImg = xIcon.getImage();
					page.drawImage(xImg, 30 + offset * i, 30 + offset * j, null);
				} else if (board[i][j] == 2) {   // Jika nilai sel adalah 2 (mewakili pemain O)
					ImageIcon oIcon = new ImageIcon("orangeo.png");				//Mengimport file dengan nama "orangeo.png" dalam file
					Image oImg = oIcon.getImage();											//Menjadikan image sebuah variable
					page.drawImage(oImg, 30 + offset * i, 30 + offset * j, null);	//Menentukan koordinat digambarnya O
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
			boolean notDone = false;			//membuat Varable boolean bernama notDone yang berisikan nilai false untuk mengindikasikan bahwa permainan belum selesai 
			for (int i = 0; i < 5; i++) {		//melakukan looping untuk mengecek dari i=0 sampai i=4
				for (int j = 0; j < 5; j++) {	//melakukan nested looping untuk mengecek dari j=0 sampai j=4
					if (board[i][j] == 0) {		//melakukan pengecekan kondisi jika isi dari array i dan j sama dengan 0
						notDone = true;			//maka notDone sama dengan true
						break;					// break (keluar dari looping) 
					}
				}
			}
		
			if (temp > 0) {									//mengecek apakah variable temp lebih dari 0
				winner = temp;								//memberi isi variable winner sama dengan isi variable temp
				if (winner == 1) {							//mengecek kondisi apakah variable winner sama dengan 1
					player1wins++;							//jika sama dengan 1 maka melakukan increament pada variable player1wins
					System.out.println("Winner is X");	//memberi output "Winner is X"
				} else if (winner == 2) {					//mengecek kondisi lain apakah variable winner sama dengan 2
					player2wins++;							//jika sama dengan 2 maka melakukan increament pada variable player2wins 
					System.out.println("Winner is O");	//memberi output "Winner is O"
				} else if (winner == 3) {					//mengecek kondisi lain apakah variable winner sama dengan 3
					System.out.println("It's a tie");		//jika sama dengan 3 maka memberi output "It's a tie"
				}
				gameDone = true;							//memberi value true kepada variable gameDone
				getJButton().setVisible(true);		//memperlihatkan variable button yang kita buat dengan cara mengubah value dari false menjadi true
			} else if (!notDone) {							//mengecek kondisi lain jika variable notDOne tidak true
				winner = 3;									//memberi variable winner dengan nilai 3
				System.out.println("It's a tie");			//memberi output "It's a tie"
				gameDone = true;							//memberi value variable gameDone menjadi true
				getJButton().setVisible(true);		//memperlihatkan variable button yang kita buat dengan cara mengubah value dari false menjadi true
			}
		}
	public JButton getJButton() {	return jButton; }		//membuat method Jbutton yang berfungsi untuk get variable jButton

	public void setPlayerXWins(int a) {						//membuat method yang berfungsi untuk set playerXWins dengan parameter int a
		player1wins = a;									//mengisi variable player1wins = a;
	}

	public void setPlayerOWins(int a) {						//membuat method yang berfungsi untuk set playerOwins dengan parameter int a
		player2wins = a;									//mengisi variable player2wins = a;
	}

	public static void main(String[] args) {				//fungsi utama dalam program
		JFrame frame = new JFrame("Tic Tac Toe");		//membuat frame baru 
		frame.getContentPane();								

		GamePanel gamePanel = new GamePanel();				//membuat panel game baru
		frame.add(gamePanel);								//memasukkan gamePanel ke dalam fram

		frame.addWindowListener(new WindowAdapter() {		//Memasukkan window listener ke dalam frame
			public void windowOpened(WindowEvent e) {		//Method WindowOpened untuk membuka window baru
				try {
					File file = new File("score.txt");					//Untuk membuat file bernama score.txt
					Scanner sc = new Scanner(file);								//Membuat scanner untuk scan file dengan nama score.txt ^
					gamePanel.setPlayerXWins(Integer.parseInt(sc.nextLine()));	//membaca satu baris dari file eksternal (score.txt) sebagai string menggunakan objek Scanner (sc), dan kemudian mengonversinya menjadi tipe data integer. (Untuk player X)
					gamePanel.setPlayerOWins(Integer.parseInt(sc.nextLine()));  //membaca satu baris dari file eksternal (score.txt) sebagai string menggunakan objek Scanner (sc), dan kemudian mengonversinya menjadi tipe data integer. (Untuk player O)
					sc.close();													//menutup scanner
				} catch (IOException io) {										//Throw exception
				}
			}

			public void windowClosing(WindowEvent e) {						//Method untuk close window
				try {		
					PrintWriter pw = new PrintWriter("score.txt");	//Untuk print input baru ke dalam file score.txt
					pw.write("");											//Write""""
					pw.write(gamePanel.player1wins + "\n");					//Untuk write input dari aplikasi ke file
					pw.write(gamePanel.player2wins + "\n");					//untuk write input dari aplikasi ke file
					pw.close();												//untuk close writer
				} catch (FileNotFoundException e1) { }						//Throw exception
			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				//untuk set default operasi close
		frame.setResizable(false);								//untuk set resizeable menjadi false agar window tidak bisa resize (fixed size)
		frame.pack();														//untuk set window sesuai dengan ukuran yang diinginkan
		frame.setVisible(true);											//untuk set visibilitas window menjadi true (Agar terlihat)
	}

	private class XOListener implements MouseListener {		//class untuk mouselistener (pengecek koordinat)

		public void mouseClicked(MouseEvent event) {		//method untuk tracking mouse click
			selX = -1;										//mengisi variable selX dengan nilai -1
			selY = -1;										//mengisi variable selY dengan nilai -1
			if (gameDone == false) {						//mengecek kondisi jika gameDOne sama dengan false
				a = event.getX();							//mendeklarasikan variable a untuk mendapatkan koordinat X
				b = event.getY();							//mendeklarasikan variable a untuk mendapatkan koordinat Y
				int selX = 0, selY = 0;						//membuat variable baru selX dan selY dengan nilai sama sama 0
				if (a > 12 && a < 99) {						//mengecek kondisi jika a lebih dari 12 dan a kurang dari 99 (koordinat)
					selX = 0;								//mengisi nilai selX dengan 0
				} else if (a > 103 && a < 195) {			//mengecek kondisi lain jika a lebih dari 103 dan a kurang dari 195 (koordinat)
					selX = 1;								//mengisi nilai selX dengan 1
				} else if (a > 200 && a < 287) {			//mengecek kondisi lain jika a lebih dari 200 dan a kurang dari 287 (koordinat)
					selX = 2;								//mengisi nilai selX dengan 2
				} else if (a > 291 && a < 383) {			//mengecek kondisi lain jika a lebih dari 291 dan a kurang dari 383 (koordinat)
					selX = 3;								//mengisi nilai selX dengan 3
				} else if (a > 382 && a < 479) {			//mengecek kondisi lain jika a lebih dari 382 dan a kurang dari 479 (koordinat)
					selX = 4; 								//mengisi nilai selX dengan 4
				}else {										//kondisi selain kondisi diatas
					selX = -1;								//mengisi nilai selX dengan -1
				}

				if (b > 12 && b < 99) {						//mengecek kondisi jika b lebih dari 12 dan b kurang dari 99 (koordinat)
					selY = 0;								//mengisi nilai selY dengan 0
				} else if (b > 103 && b < 195) {			//mengecek kondisi jika b lebih dari 12 dan b kurang dari 99 (koordinat)
					selY = 1;								//mengisi nilai selY dengan 0
				} else if (b > 200 && b < 287) {			//mengecek kondisi jika b lebih dari 12 dan b kurang dari 99 (koordinat)
					selY = 2;								//mengisi nilai selY dengan 0
				} else if (b > 291 && b < 383) {			//mengecek kondisi jika b lebih dari 12 dan b kurang dari 99 (koordinat)
					selY = 3;								//mengisi nilai selY dengan 0
				} else if (b > 382 && b < 479) {			//mengecek kondisi jika b lebih dari 12 dan b kurang dari 99 (koordinat)
					selY = 4; 								//mengisi nilai selY dengan 0
				} else {
					selY = -1;								//mengisi nilai selY  dengan -1
				}
				if (selX != -1 && selY != -1) {				//mengecek kondisi jika selX tidak sama dengan -1 dan selY tidak sama dengan -1

					if (board[selX][selY] == 0) {			//melakukan nested if untuk mengecek board index selx dan selY sama dengan 0
						if (playerX) {						//melakukan nested if untuk mengecek playerX apakah true
							board[selX][selY] = 1;			//mengisi nilai board index selX dan selY dengan 1
							playerX = false;				//mengisi variabel playerX dengan false
						} else {							//mengecek selain kondisi di atas
							board[selX][selY] = 2;			//mengisi nilai board index selX dan selY dengan 2
							playerX = true;					//mengisi variabel playerX dengan true
						}
						checkWinner();						//memanggil fungsi checkwinner
						System.out.println(" CLICK= x:" + a + ",y: " + b + "; selX,selY: " + selX + "," + selY);	//memberikan output koordinat x dan Y pada terminal
					}
				} else {									//mengecek kondisi selain diatas
					System.out.println("invalid click");	//mmeberikan output "invalid click" pada terminal
				}
			}
		}

		public void mouseReleased(MouseEvent event) {}		//Metode ini akan dipanggil ketika tombol mouse dilepas setelah sebelumnya ditekan
		public void mouseEntered(MouseEvent event) {}		//Metode ini akan dipanggil saat kursor mouse memasuki batas area yang dipantau oleh XOListener.
		public void mouseExited(MouseEvent event) {}		//Metode ini akan dipanggil saat kursor mouse meninggalkan batas area yang dipantau oleh XOListener. 
		public void mousePressed(MouseEvent event) {}		//Metode ini akan dipanggil saat tombol mouse ditekan. 
	}

	@Override													//meng-overide
	public void actionPerformed(ActionEvent e) { resetGame(); }	//metode untuk mendeteksi ketika suatu tindakan (action) terjadi, permainan akan direset atau diinisialisasi kembali ke kondisi awal

}
