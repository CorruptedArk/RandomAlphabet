/*
 * RandomAlphabet is a Java GUI tool to encode and decode text strings using pseudorandomly generated ciphers from string keys.
 *     Copyright (C) 2019  Noah Stanford <noahstandingford@gmail.com>
 *
 *     RandomAlphabet is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     RandomAlphabet is distributed in the hope that it will be fun, interesting, and useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package dev.corruptedark.randomalphabet;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

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
    private JSpinner decoySpinner;
    private JLabel decoyCountLabel;
    private JButton plainTextClearButton;
    private JButton encodeTextClearButton;
    private JButton aboutButton;
    private JButton importPlainButton;
    private JButton importEncodedButton;
    private JButton exportPlainButton;
    private JButton exportEncodedButton;
    private RandomTranslator translator;
    private SpinnerNumberModel bucketNumberModel;
    private SpinnerNumberModel decoyNumberModel;
    private Clipboard clipboard;
    private AboutForm aboutForm;

    public MainForm() {
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        bucketNumberModel = new SpinnerNumberModel();
        bucketNumberModel.setMinimum(1);
        bucketNumberModel.setMaximum(9999);

        decoyNumberModel = new SpinnerNumberModel();
        decoyNumberModel.setMinimum(1);
        decoyNumberModel.setMaximum(9999);

        bucketSizeSpinner.setModel(bucketNumberModel);

        bucketSizeSpinner.setValue(10);

        decoySpinner.setModel(decoyNumberModel);

        decoySpinner.setValue(2);

        generateAlphabetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                translator = new RandomTranslator(keyBox.getText(), (int)bucketSizeSpinner.getValue(), (int)decoySpinner.getValue());
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
                clipboard.setContents(selection, selection);

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
                    StringBuilder pasteText = new StringBuilder();
                    try {

                        InputStreamReader reader = (InputStreamReader) clipboard.getData(DataFlavor.selectBestTextFlavor(clipboard.getAvailableDataFlavors()));
                        BufferedReader bufferedReader = new BufferedReader(reader);

                        while (bufferedReader.ready()) {
                            pasteText.append(bufferedReader.readLine());
                        }
                        bufferedReader.close();

                        if (pasteText.length() > 0)
                            pasteText.deleteCharAt(pasteText.length() - 1);
                    }
                    catch (ClassCastException classExcept)
                    {
                        String tempPasteString =  (String) clipboard.getData(DataFlavor.selectBestTextFlavor(clipboard.getAvailableDataFlavors()));
                        pasteText.append(tempPasteString);
                    }

                    plainTextBox.setText(pasteText.toString());
                    feedBackLabel.setText("Paste Successful");
                    feedBackLabel.setForeground(Color.green);
                    feedBackLabel.setVisible(true);
                }
                catch (Exception except)
                {
                    feedBackLabel.setText("Invalid Paste");
                    feedBackLabel.setForeground(Color.red);
                    feedBackLabel.setVisible(true);
                    except.printStackTrace();
                }

            }
        });


        encodedTextCopyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                StringSelection selection = new StringSelection(encodedTextBox.getText());
                clipboard.setContents(selection, selection);

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
                    StringBuilder pasteText = new StringBuilder();
                    try {

                        InputStreamReader reader = (InputStreamReader) clipboard.getData(DataFlavor.selectBestTextFlavor(clipboard.getAvailableDataFlavors()));
                        BufferedReader bufferedReader = new BufferedReader(reader);

                        while (bufferedReader.ready()) {
                            pasteText.append(bufferedReader.readLine());
                        }
                        bufferedReader.close();

                        if (pasteText.length() > 0)
                            pasteText.deleteCharAt(pasteText.length() - 1);
                    }
                    catch (ClassCastException classExcept)
                    {
                        String tempPasteString =  (String) clipboard.getData(DataFlavor.selectBestTextFlavor(clipboard.getAvailableDataFlavors()));
                        pasteText.append(tempPasteString);
                    }

                    encodedTextBox.setText(pasteText.toString());
                    feedBackLabel.setText("Paste Successful");
                    feedBackLabel.setForeground(Color.green);
                    feedBackLabel.setVisible(true);
                }
                catch (Exception except)
                {
                    feedBackLabel.setText("Invalid Paste");
                    feedBackLabel.setForeground(Color.red);
                    feedBackLabel.setVisible(true);
                    except.printStackTrace();
                }
            }
        });


        plainTextClearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                plainTextBox.setText("");
            }
        });


        encodeTextClearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                encodedTextBox.setText("");
            }
        });
        aboutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(aboutForm == null)
                {
                    aboutForm = new AboutForm();
                }
                else if(aboutForm.isDisplayable())
                {
                    aboutForm.setVisible(true);
                }

            }
        });
        importPlainButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                feedBackLabel.setVisible(false);

                File selectedFile = null;
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt","md","text");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(mainForm);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    selectedFile = chooser.getSelectedFile();
                    try {
                        FileReader reader = new FileReader(selectedFile);
                        BufferedReader bufferedReader = new BufferedReader(reader);

                        StringBuilder text = new StringBuilder();
                        while(bufferedReader.ready())
                        {
                            text.append(bufferedReader.readLine());
                        }
                        bufferedReader.close();
                        plainTextBox.setText(text.toString());
                    } catch (Exception ex) {
                        feedBackLabel.setText("Import Failed");
                        feedBackLabel.setForeground(Color.green);
                        feedBackLabel.setVisible(true);
                    }
                }
            }
        });
        importEncodedButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                feedBackLabel.setVisible(false);

                File selectedFile = null;
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt","md","text");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(mainForm);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    selectedFile = chooser.getSelectedFile();
                    try {
                        FileReader reader = new FileReader(selectedFile);
                        BufferedReader bufferedReader = new BufferedReader(reader);

                        StringBuilder text = new StringBuilder();
                        while(bufferedReader.ready())
                        {
                            text.append(bufferedReader.readLine());
                        }
                        bufferedReader.close();
                        encodedTextBox.setText(text.toString());
                    } catch (Exception ex) {
                        feedBackLabel.setText("Import Failed");
                        feedBackLabel.setForeground(Color.green);
                        feedBackLabel.setVisible(true);
                    }
                }
            }
        });
        exportPlainButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                feedBackLabel.setVisible(false);

                File selectedFile = null;
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", ".txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showSaveDialog(mainForm);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    selectedFile = chooser.getSelectedFile();
                    try {
                        FileWriter writer = new FileWriter(selectedFile);
                        BufferedWriter bufferedWriter = new BufferedWriter(writer);

                        String text = plainTextBox.getText();
                        bufferedWriter.write(text);
                        bufferedWriter.close();
                    } catch (Exception ex) {
                        feedBackLabel.setText("Export Failed");
                        feedBackLabel.setForeground(Color.green);
                        feedBackLabel.setVisible(true);
                    }
                }
            }
        });
        exportEncodedButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                feedBackLabel.setVisible(false);

                File selectedFile = null;
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", ".txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showSaveDialog(mainForm);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    selectedFile = chooser.getSelectedFile();
                    try {
                        FileWriter writer = new FileWriter(selectedFile);
                        BufferedWriter bufferedWriter = new BufferedWriter(writer);

                        String text = encodedTextBox.getText();
                        bufferedWriter.write(text);
                        bufferedWriter.close();
                    } catch (Exception ex) {
                        feedBackLabel.setText("Export Failed");
                        feedBackLabel.setForeground(Color.green);
                        feedBackLabel.setVisible(true);
                    }
                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Random Alphabet");
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
