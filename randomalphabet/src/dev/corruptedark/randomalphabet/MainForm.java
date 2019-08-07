package dev.corruptedark.randomalphabet;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutionException;

public class MainForm {

    private JTextField keyBox;
    private JLabel keyLabel;
    private JButton generateAlphabetButton;
    private JButton encodeButton;
    private JButton decodeButton;
    private JTextArea plainTextBox;
    private JLabel plainTextLabel;
    private JLabel encodedTextLabel;
    private JTextArea encodedTextBox;
    private JPanel mainForm;
    private JSpinner bucketSizeSpinner;
    private JLabel bucketSizeLabel;
    private JLabel invalidLabel;
    private RandomTranslator translator;
    private SpinnerNumberModel numberModel;


    public MainForm() {

        numberModel = new SpinnerNumberModel();
        numberModel.setMinimum(1);
        numberModel.setMaximum(9999);

        bucketSizeSpinner.setModel(numberModel);

        bucketSizeSpinner.setValue(10);

        generateAlphabetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                translator = new RandomTranslator(keyBox.getText(), (int)bucketSizeSpinner.getValue());
            }
        });


        encodeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(translator != null)
                {
                    try
                    {
                        encodedTextBox.setText(translator.encodeText(plainTextBox.getText()));
                        invalidLabel.setVisible(false);
                    }
                    catch (Exception except)
                    {
                        invalidLabel.setVisible(true);
                    }
                }
            }
        });


        decodeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(translator != null)
                {
                    try
                    {
                        plainTextBox.setText(translator.decodeText(encodedTextBox.getText()));
                        invalidLabel.setVisible(false);
                    }
                    catch(Exception except)
                    {
                        invalidLabel.setVisible(true);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        frame.setContentPane(new MainForm().mainForm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
    }
}
