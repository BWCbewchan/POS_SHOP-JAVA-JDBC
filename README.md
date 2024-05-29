### CÔNG NGHỆ
BEWCHAN06/POS-SHOP là một dự án viết bằng Java để tạo một ứng dụng quản lý bán hàng sử dụng mã QR. Dự án này sử dụng công nghệ camera để quét mã QR từ hình ảnh và hiển thị nội dung của mã QR trên giao diện người dùng. Ngoài ra, dự án cũng cung cấp tính năng tạo mã QR mới. Bài viết cũng đề cập đến việc kết nối cơ sở dữ liệu và tạo liên kết với database, cũng như các chức năng quản lý khác như nhân viên, sản phẩm, hóa đơn, khách hàng và thống kê.
# camera read code
```java
package gui;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import org.opencv.imgcodecs.Imgcodecs;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class CameraPanel extends JPanel {
	private String temp;
    private JLabel cameraViewLabel;
    private VideoCapture capture;
    private List<QRCodeListener> listeners = new ArrayList<>();

    public CameraPanel() {
    	
        setLayout(new BorderLayout());
        
        cameraViewLabel = new JLabel();
        cameraViewLabel.setPreferredSize(new Dimension(300, 300));
        add(cameraViewLabel, BorderLayout.CENTER);
    }

    public void startCamera() {
        capture = new VideoCapture(0);

        SwingWorker<Void, Mat> worker = new SwingWorker<>() {
            

			@Override
            protected Void doInBackground() {
                if (!capture.isOpened()) {
                    System.out.println("Không thể mở camera.");
                } else {
                    System.out.println("Camera đang mở...");
                    
                    Mat frame = new Mat();
                    temp = "";
                    while (!isCancelled()) {
                        capture.read(frame);
                        if (!frame.empty()) {
                            BufferedImage image = convertMatToBufferedImage(frame);
                            cameraViewLabel.setIcon(new ImageIcon(image));
                            
                            // Quét mã QR từ hình ảnh
                            String qrText = readQRCode(image);
                            if (qrText != null) {
                            	if(!temp.equals(qrText)) {
                            		temp = qrText;
//                            		System.out.println("Mã QR: " + temp);
                            		playBeepSound();
                            		notifyListeners(temp);
                            	}
                                
                            }
                        }
                    }
//                    while (!isCancelled()) {
//                        capture.read(frame);
//                        if (!frame.empty()) {
//                            publish(frame.clone());
//                        }
//                    }
                    capture.release();
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Mat> chunks) {
                if (!chunks.isEmpty()) {
                    Mat latestFrame = chunks.get(chunks.size() - 1);
                    BufferedImage image = convertMatToBufferedImage(latestFrame);
                    cameraViewLabel.setIcon(new ImageIcon(image));
                }
            }
        };

        worker.execute();
    }
    public void addQRCodeListener(QRCodeListener listener) {
        listeners.add(listener);
    }

    public void removeQRCodeListener(QRCodeListener listener) {
        listeners.remove(listener);
    }
    private void notifyListeners(String qrCode) {
        for (QRCodeListener listener : listeners) {
            listener.onQRCodeRead(qrCode);
        }
    }
    public String getQRCodeValue() {
        return temp;
    }
    
    private static void playBeepSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("data/amThanh/beep.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private String readQRCode(BufferedImage image) {
        if (image != null) {
            try {
                // Chuyển đổi BufferedImage thành hình ảnh grayscale để cải thiện việc đọc mã QR
                BufferedImage grayscaleImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                grayscaleImage.getGraphics().drawImage(image, 0, 0, null);

                BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(grayscaleImage);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                MultiFormatReader reader = new MultiFormatReader();
                Result result = reader.decode(bitmap);
                return result.getText();
            } catch (NotFoundException e) {
                // Mã QR không được tìm thấy
                // Có thể in ra hoặc xử lý lỗi tại đây
                return null;
            }
        }
        return null;
    }

    private BufferedImage convertMatToBufferedImage(Mat frame) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (frame.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        int bufferSize = frame.channels() * frame.cols() * frame.rows();
        byte[] buffer = new byte[bufferSize];
        frame.get(0, 0, buffer);

        BufferedImage image = new BufferedImage(frame.cols(), frame.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);

        int targetWidth = 174;
        int targetHeight = 118;
        Image scaledImage = image.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage scaledBufferedImage = new BufferedImage(targetWidth, targetHeight, type);
        scaledBufferedImage.getGraphics().drawImage(scaledImage, 0, 0, null);

        return scaledBufferedImage;
    }
    private void displayImage(Mat frame) {
        BufferedImage image = convertMatToBufferedImage(frame);

        // Thay đổi kích thước của ảnh
        int targetWidth = 300;
        int targetHeight = 300;
        Image scaledImage = image.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);

        // Hiển thị ảnh đã thay đổi kích thước trên cameraViewLabel
        cameraViewLabel.setIcon(imageIcon);
    }
    public interface QRCodeListener {
        void onQRCodeRead(String qrCode);
    }
}
```
# create a new QRCode
```java
package component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BarcodeGenerator {

    public static void main(String[] args) throws WriterException {
//        generateBarcode("SP02","QUAN","SP02", "hinhanh/SP02.png");
    }

    public static void generateBarcode(String data, String barcodeName, String additionalInfo, String filePath) throws WriterException {
        Code128Writer writer = new Code128Writer();
        try {
            BitMatrix matrix = writer.encode(data, BarcodeFormat.CODE_128, 400, 200);

            int barcodeHeight = matrix.getHeight();
            int totalHeight = barcodeHeight + 80; // Tăng chiều cao thêm 80 pixel, 40 pixel trên và dưới mã vạch
            BufferedImage image = new BufferedImage(matrix.getWidth(), totalHeight, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, matrix.getWidth(), totalHeight);

            // Vẽ mã vạch với khoảng cách 40 pixel ở trên
            graphics.setColor(Color.BLACK);
            for (int i = 0; i < matrix.getWidth(); i++) {
                for (int j = 40; j < matrix.getHeight() + 40; j++) {
                    if (matrix.get(i, j - 40)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            graphics.setFont(new Font("Arial", Font.PLAIN, 12));
            FontMetrics fontMetrics = graphics.getFontMetrics();
            int additionalInfoWidth = fontMetrics.stringWidth(additionalInfo);
            int x = (matrix.getWidth() - additionalInfoWidth) / 2;
            int y = totalHeight - 20;
            graphics.drawString(additionalInfo, x, y);

            graphics.drawString(barcodeName, 50, 20); // Vị trí dưới mã vạch

            ImageIO.write(image, "png", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

```
- demo image qr code
<img src="POS SHOP/printer/hinhanh/SP28.png" alt="">
### CONNECT DATABASE

```java
package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KetNoiSQL {
    private static Connection conn = null;
    private static KetNoiSQL instance = new KetNoiSQL();
    
    public static KetNoiSQL getInstance(){  
        return instance;
    }
    
     public void connect(){
        String url = "jdbc:sqlserver://localhost:1433;database=Postshop";
        String username = "sa";
        String password = "sapassword";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(KetNoiSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(KetNoiSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Connection getConnection(){
        return conn;
    }

}
```
- create database link <a href="POS SHOP/data/testdata part1.sql">here</a>
- import data for database <a href="POS SHOP/data/testdata part1 data.sql">here</a>
### GD MANG HINH
- GD LOGIN 
<img src="POS SHOP/GD/HAGD/login.png" alt="">

- GD CHÍNH
<img src="POS SHOP/GD/HAGD/giaodienchinh.png" alt="">
</br>

- GD BÁN HÀNG
<img src="POS SHOP/GD/HAGD/Bán Hàng .png" alt="">

- GD ĐỔI TRẢ
<img src="POS SHOP/GD/HAGD/TraHang.png" alt="">

- GD SẢN PHẨM
<img src="POS SHOP/GD/HAGD/SanPham.png" alt="">

- GD THUỘC TÍNH 
<img src="POS SHOP/GD/HAGD/Thêm Thuộc Tính.png" alt="">

- GD HÓA ĐƠN  
<img src="POS SHOP/GD/HAGD/HoaDon.png" alt="">

- GD KHUYẾN MÃI
<img src="POS SHOP/GD/HAGD/khuyenMai.png" alt="">

- GD Nhân Viên
<img src="POS SHOP/GD/HAGD/NhanVien.png" alt="">

- GD Tài Khoản
<img src="POS SHOP/GD/HAGD/Tài Khoản.png" alt="">

- GD Khách Hàng
<img src="POS SHOP/GD/HAGD/KhachHang.png" alt="">

- GD Thống Kê
<img src="POS SHOP/GD/HAGD/ThongKe.png" alt="">

- GD Thống Kê
<img src="POS SHOP/GD/HAGD/TKDOITRA.png" alt="">
