package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadingExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Loading Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Product");
        frame.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoadingDialog(frame);

                // Simulate adding a product (replace this with your actual logic)
                SwingUtilities.invokeLater(() -> {
                    try {
                        Thread.sleep(1000); // Simulating some time-consuming process
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    closeLoadingDialog();
                });
            }
        });

        frame.setVisible(true);
    }

    private static JDialog loadingDialog;

    private static void showLoadingDialog(Frame owner) {
        loadingDialog = new JDialog(owner, "Loading...", true);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        loadingDialog.add(BorderLayout.CENTER, progressBar);
        loadingDialog.setSize(200, 75);
        loadingDialog.setLocationRelativeTo(owner);
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        loadingDialog.setVisible(true);
    }

    private static void closeLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dispose();
        }
    }
}
