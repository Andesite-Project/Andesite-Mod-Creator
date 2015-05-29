/*
 * The MIT License
 *
 * Copyright 2015 Marius.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package info.varden.andesite.creator.gui;

import info.varden.andesite.creator.CipheredKeyPair;
import info.varden.andesite.crypto.BenchmarkResults;
import info.varden.andesite.crypto.CryptoBenchmark;
import info.varden.andesite.helper.RSAKeygenResults;
import info.varden.andesite.helper.ThreadAccessibleObjectStorage;

import java.awt.Color;
import java.awt.Frame;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Dialog for generating new keypairs
 * @author Marius
 */
public class NewKeyGui extends javax.swing.JDialog {
    
    private volatile BenchmarkResults rsaPerformance;
    private volatile boolean bchSuccessful = false;
    private volatile boolean bchComplete = false;
    private final Frame parent;
    
    public final ThreadAccessibleObjectStorage<CipheredKeyPair> result = new ThreadAccessibleObjectStorage<CipheredKeyPair>(null);
    public String name;

    /**
     * Creates new form NewKeyGui
     */
    public NewKeyGui(java.awt.Frame parent, JDialog parent1, boolean modal) {
        super(parent, modal);
        initComponents();
        this.parent = parent;
        setLocationRelativeTo(parent);
        if (parent1 != null) {
            setLocationRelativeTo(parent1);
        }
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    CryptoBenchmark.initializeEncrypter();
                    rsaPerformance = CryptoBenchmark.getRSAKeyGenSpeed(1024, 20);
                    bchSuccessful = true;
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            int selKS = Integer.valueOf((String) jComboBox1.getSelectedItem());
                            try {
                                double timeEst = CryptoBenchmark.getRSACalcConvKeysizeSeconds(rsaPerformance.getAverageBenchmarkMillis() / 1000D, 1024, selKS);
                                int hour = 0;
                                int min = 0;
                                while (timeEst >= 3600D) {
                                    hour++;
                                    timeEst -= 3600D;
                                }
                                while (timeEst >= 60D) {
                                    min++;
                                    timeEst -= 60D;
                                }
                                jLabel3.setText("Keygen ETA:" + (hour > 0 ? " " + hour + "h" : "") + (min > 0 ? " " + min + "m" : "") + " " + ((double) Math.round(timeEst * 10D) / 10D) + "s");
                            } catch (InvalidKeyException ex) {
                                Logger.getLogger(NewKeyGui.class.getName()).log(Level.SEVERE, null, ex);
                                jLabel3.setText("Invalid key size!");
                            }
                        }
                    
                    });
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(NewKeyGui.class.getName()).log(Level.SEVERE, null, ex);
                    bchSuccessful = false;
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            JOptionPane.showMessageDialog(NewKeyGui.this, "Failed to test your system performance! (0x0301)", "Keygen time estimation failed", JOptionPane.WARNING_MESSAGE);
                            jLabel3.setText("Failed to test your system performance!");
                        }
                    
                    });
                }
                bchComplete = true;
            }
            
        });
        t.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Generate new keypair");
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);

        jLabel1.setText("RSA key size:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1024", "2048", "4096", "8192" }));
        jComboBox1.setSelectedItem("2048");
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setText("<html>Giving your key a name will help you identify the key later. You can change this at any time.</html>");
        jLabel2.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Testing your system, please wait...");

        jComboBox2.setModel(supportsAES256() ? new DefaultComboBoxModel(new String[] { "128", "192", "256" }) : new DefaultComboBoxModel(new String[] { "128" }));
        jComboBox2.setSelectedItem(supportsAES256() ? "256" : "128");

        jLabel4.setText("AES key size:");

        jLabel5.setText("<html>Larger AES keys improve security, but due to cryptography export regulations, not all key sizes are available everywhere. 256 is recommended if available.</html>");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(supportsAES256() ? new Color(0, 127, 0) : new Color(255, 0, 0));
        jLabel6.setText(supportsAES256() ? "Your system supports AES-192 and AES-256" : "Your system is limited to AES-128");

        jLabel7.setText("Password:");

        jLabel8.setText("<html>In order to stop people from impersonating you, you are required to set a password for the RSA private key. Choose a strong password.</html>");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Please enter a password.");

        jButton1.setText("Create key");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel10.setText("Key name:");

        jLabel11.setText("<html>Larger RSA keys increase security, but take longer to encrypt and decrypt than shorter keys. A key size of 2048 or 4096 is recommended.</html>");
        jLabel11.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        jPasswordField1.getDocument().addDocumentListener(getDocumentListener());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (bchComplete && bchSuccessful) {
            int selKS = Integer.valueOf((String) jComboBox1.getSelectedItem());
            try {
                double timeEst = CryptoBenchmark.getRSACalcConvKeysizeSeconds(rsaPerformance.getAverageBenchmarkMillis() / 1000D, 1024, selKS);
                int hour = 0;
                int min = 0;
                while (timeEst >= 3600D) {
                    hour++;
                    timeEst -= 3600D;
                }
                while (timeEst >= 60D) {
                    min++;
                    timeEst -= 60D;
                }
                jLabel3.setText("Keygen ETA:" + (hour > 0 ? " " + hour + "h" : "") + (min > 0 ? " " + min + "m" : "") + " " + ((double) Math.round(timeEst * 10D) / 10D) + "s");
            } catch (InvalidKeyException ex) {
                Logger.getLogger(NewKeyGui.class.getName()).log(Level.SEVERE, null, ex);
                jLabel3.setText("Invalid key size!");
            }
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Generate
        if (jPasswordField1.getPassword().length <= 0) {
            JOptionPane.showMessageDialog(NewKeyGui.this.parent, "Please enter a password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        this.name = jTextField1.getText();
        final RSAKeygenForm rskgn = new RSAKeygenForm(new JFrame(), this,
                Integer.valueOf((String) jComboBox1.getSelectedItem()),
                Integer.valueOf((String) jComboBox2.getSelectedItem()),
                new String(jPasswordField1.getPassword())
        ) {
            @Override
            public void onComplete(final RSAKeygenResults results) {
                if (results.isError()) {
                    results.getException().printStackTrace();
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            JOptionPane.showMessageDialog(NewKeyGui.this, results.getMessage(), "Key generation failed", JOptionPane.ERROR_MESSAGE);
                        }

                    });
                } else {
                    result.set(results.getKeyPair());
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            NewKeyGui.this.dispose();
                            try {
                                JOptionPane.showMessageDialog(NewKeyGui.this.parent, "<html><body><p style='width: 400px;'>Successfully generated " + results.getKeyPair().getRSAKeyLength() + "-bit RSA keypair encrypted with AES-" + results.getKeyPair().getAESKeyLength() + "!</p></body></html>", "Key generation successful", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                Logger.getLogger(NewKeyGui.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(NewKeyGui.this.parent, "<html><body><p style='width: 400px;'>Successfully generated RSA keypair encrypted with AES-" + results.getKeyPair().getAESKeyLength() + "!</p></body></html>", "Key generation successful", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }

                    });
                }
            }
        };
        rskgn.performTaskThreaded();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Cancel
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private DocumentListener getDocumentListener() {
        return new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                update(new String(jPasswordField1.getPassword()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(new String(jPasswordField1.getPassword()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update(new String(jPasswordField1.getPassword()));
            }
            
            private void update(String s) {
                int score = 0;
                if (s.length() >= 8) {
                    score += 4;
                }
                if (s.length() >= 12) {
                    score += 4;
                }
                if (s.length() >= 16) {
                    score += 4;
                }
                Pattern psc = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher msc = psc.matcher(s);
                if (msc.find()) {
                    score += 3;
                }
                if (msc.find()) {
                    score += 3;
                }
                Pattern pac = Pattern.compile("[A-Z]");
                Matcher mac = pac.matcher(s);
                if (mac.find()) {
                    score += 2;
                }
                if (mac.find()) {
                    score += 2;
                }
                Pattern plc = Pattern.compile("[a-z]");
                Matcher mlc = plc.matcher(s);
                if (mlc.find()) {
                    score += 2;
                }
                if (mlc.find()) {
                    score += 2;
                }
                Pattern pdc = Pattern.compile("[0-9]");
                Matcher mdc = pdc.matcher(s);
                if (mdc.find()) {
                    score += 2;
                }
                if (mdc.find()) {
                    score += 2;
                }
                if (score <= 0) {
                    jLabel9.setForeground(Color.BLACK);
                    jLabel9.setText("Please enter a password.");
                } else if (score < 5) {
                    jLabel9.setForeground(Color.RED);
                    jLabel9.setText("Very weak (" + score + "/30)");
                } else if (score < 10) {
                    jLabel9.setForeground(new Color(248, 128, 23));
                    jLabel9.setText("Weak (" + score + "/30)");
                } else if (score < 15) {
                    jLabel9.setForeground(new Color(255, 165, 0));
                    jLabel9.setText("Medium (" + score + "/30)");
                } else if (score < 20) {
                    jLabel9.setForeground(new Color(0, 200, 0));
                    jLabel9.setText("Strong (" + score + "/30)");
                } else if (score < 25) {
                    jLabel9.setForeground(new Color(0, 100, 0));
                    jLabel9.setText("Very strong (" + score + "/30)");
                } else if (score <= 30) {
                    jLabel9.setForeground(Color.BLUE);
                    jLabel9.setText("Exceptionally strong (" + score + "/30)");
                }
            }
        };
    }
    
    private boolean supportsAES256() {
        try {
            return Cipher.getMaxAllowedKeyLength("AES") >= 256;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(NewKeyGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewKeyGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewKeyGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewKeyGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewKeyGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewKeyGui dialog = new NewKeyGui(new javax.swing.JFrame(), null, true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
