package dev.corruptedark.randomalphabet;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private JLabel feedBackLabel;
    private JButton plainTextCopyButton;
    private JButton encodedTextCopyButton;
    private JButton plainTextPasteButton;
    private JButton encodeTextPasteButton;
    private RandomTranslator translator;
    private SpinnerNumberModel numberModel;
    private Clipboard clipboard;


    public MainForm() {

        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

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
                feedBackLabel.setForeground(Color.green);
                feedBackLabel.setText("Alphabet Generated");
                feedBackLabel.setVisible(true);
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
                        feedBackLabel.setVisible(false);
                    }
                    catch (Exception except)
                    {
                        feedBackLabel.setText("Invalid Translation");
                        feedBackLabel.setForeground(Color.red);
                        feedBackLabel.setVisible(true);
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
                        feedBackLabel.setVisible(false);
                    }
                    catch(Exception except)
                    {
                        feedBackLabel.setText("Invalid Translation");
                        feedBackLabel.setForeground(Color.red);
                        feedBackLabel.setVisible(true);
                    }
                }
            }
        });


        plainTextCopyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                StringSelection selection = new StringSelection(plainTextBox.getText());
                clipboard.setContents(selection, null);

                feedBackLabel.setText("Text Copied");
                feedBackLabel.setForeground(Color.green);
                feedBackLabel.setVisible(true);
            }
        });


        plainTextPasteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try
                {
                    String pasteText = (String)clipboard.getData(DataFlavor.selectBestTextFlavor(clipboard.getAvailableDataFlavors()));
                    plainTextBox.setText(pasteText);
                    feedBackLabel.setText("Paste Successful");
                    feedBackLabel.setForeground(Color.green);
                    feedBackLabel.setVisible(true);
                }
                catch (Exception except)
                {
                    feedBackLabel.setText("Invalid Paste");
                    feedBackLabel.setForeground(Color.red);
                    feedBackLabel.setVisible(true);
                }

            }
        });


        encodedTextCopyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                StringSelection selection = new StringSelection(encodedTextBox.getText());
                clipboard.setContents(selection, null);

                feedBackLabel.setText("Text Copied");
                feedBackLabel.setForeground(Color.green);
                feedBackLabel.setVisible(true);
            }
        });


        encodeTextPasteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try
                {
                    String pasteText = (String)clipboard.getData(DataFlavor.selectBestTextFlavor(clipboard.getAvailableDataFlavors()));
                    encodedTextBox.setText(pasteText);
                    feedBackLabel.setText("Paste Successful");
                    feedBackLabel.setForeground(Color.green);
                    feedBackLabel.setVisible(true);
                }
                catch (Exception except)
                {
                    feedBackLabel.setText("Invalid Paste");
                    feedBackLabel.setForeground(Color.red);
                    feedBackLabel.setVisible(true);
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
