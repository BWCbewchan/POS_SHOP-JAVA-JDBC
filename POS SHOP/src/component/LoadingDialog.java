package component;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class LoadingDialog extends JDialog {
    public LoadingDialog() {
        setModal(true);
        setSize(200, 100);
        setLocationRelativeTo(null);
        setUndecorated(true); // Ẩn thanh tiêu đề của dialog

        JLabel label = new JLabel("Đang tải...");
        add(label);
    }
}