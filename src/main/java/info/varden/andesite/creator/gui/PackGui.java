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

import info.varden.andesite.core.AndesiteProject;
import info.varden.andesite.crypto.CipheredKeyPair;
import info.varden.andesite.io.AndesiteIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Marius
 */
public class PackGui extends javax.swing.JDialog {
    
    private HashMap<String, CipheredKeyPair> keys;
    private File outFile = null;
    private AndesiteProject proj;

    /**
     * Creates new form PackGui
     */
    public PackGui(java.awt.Frame parent, boolean modal, AndesiteProject project) {
        super(parent, modal);
        initComponents();
        this.proj = project;
        updateKeyDetails((String) jComboBox1.getSelectedItem());
        setLocationRelativeTo(parent);
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
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pack mod");
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);

        jButton1.setText("Browse...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setEditable(false);

        jLabel1.setText("Save as:");

        jButton2.setText("Manage...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new DefaultComboBoxModel(getKeys()));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Select keypair:");

        jButton3.setText("Pack");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Cancel");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel3.setText("Mod author:");

        jTextField2.setEditable(false);

        jLabel4.setText("Digital signature algorithms:");

        jTextField3.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 161, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTextField3))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Manage
        KeyManagerGui kmg = new KeyManagerGui(new JFrame(), this, true);
        kmg.setVisible(true);
        jComboBox1.setModel(new DefaultComboBoxModel(getKeys()));
        updateKeyDetails((String) jComboBox1.getSelectedItem());
        jButton3.setEnabled(checkIfAllIsGood());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        updateKeyDetails((String) jComboBox1.getSelectedItem());
        jButton3.setEnabled(checkIfAllIsGood());
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Browse
        JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isFile()) {
                    String[] okExts = new String[] {".anp"};
                    for (String ext : okExts) {
                        if (pathname.getName().endsWith(ext)) {
                            return true;
                        }
                    }
                }
                return pathname.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Andesite mod package (*.anp)";
            }
        });
        if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            if (!f.getName().toLowerCase().endsWith(".anp")) {
                f = new File(f.getAbsolutePath() + ".anp");
            }
            this.outFile = f;
            jTextField1.setText(f.getAbsolutePath());
        }
        jButton3.setEnabled(checkIfAllIsGood());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // Close
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Pack
        if (!checkIfAllIsGood()) {
            jButton3.setEnabled(false);
            return;
        }
        if (this.outFile.exists()) {
            int n = JOptionPane.showConfirmDialog(this, "The selected file already exists. Do you want to overwrite it?", "File already exists", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (n != JOptionPane.YES_OPTION) {
                return;
            }
        }
        CipheredKeyPair pair = this.keys.get((String) jComboBox1.getSelectedItem());
        PrivateKeyDecryptDialog pkdd = new PrivateKeyDecryptDialog(null, this, true, pair);
        pkdd.setVisible(true);
        if (pkdd.pk != null) {
            try {
                proj.author = pair.getOwner();
                AndesiteIO.publish(this.proj, this.outFile, pkdd.pk, pair.getPublicKey());
                JOptionPane.showMessageDialog(this, "Mod package successfully created!", "Packing successful", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (IOException ex) {
                Logger.getLogger(PackGui.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Failed to publish: Failed to write file to disk. (0x0501)\n\n" + ex.getClass().getName() + ":\n" + ex.getMessage(), "Failed to publish", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(PackGui.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Failed to publish: RSA is not supported by this machine. (0x0502)\n\n" + ex.getClass().getName() + ":\n" + ex.getMessage(), "Failed to publish", JOptionPane.ERROR_MESSAGE);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(PackGui.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Failed to publish: RSA or SHA-512 is not supported by this machine. (0x0503)\n\n" + ex.getClass().getName() + ":\n" + ex.getMessage(), "Failed to publish", JOptionPane.ERROR_MESSAGE);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(PackGui.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Failed to publish: RSA is not supported by this machine. (0x0504)\n\n" + ex.getClass().getName() + ":\n" + ex.getMessage(), "Failed to publish", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(PackGui.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Failed to publish: The private key for this keypair is invalid, please pick another keypair. (0x0505)\n\n" + ex.getClass().getName() + ":\n" + ex.getMessage(), "Failed to publish", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(PackGui.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Failed to publish: The private key for this keypair is invalid, please pick another keypair. (0x0506)\n\n" + ex.getClass().getName() + ":\n" + ex.getMessage(), "Failed to publish", JOptionPane.ERROR_MESSAGE);
            } catch (BadPaddingException ex) {
                Logger.getLogger(PackGui.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Failed to publish: The project is invalid. (0x0507)\n\n" + ex.getClass().getName() + ":\n" + ex.getMessage(), "Failed to publish", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private String[] getKeys() {
        try {
            this.keys = AndesiteIO.readKeybase(new File("AndesiteKeybase.akb"));
            return this.keys.keySet().toArray(new String[this.keys.size()]);
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            Logger.getLogger(KeyManagerGui.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Failed to load keys: (0x0508)\n\n" + ex.getClass().getName() + ":\n" + ex.getMessage(), "I/O error", JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyManagerGui.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Failed to load keys: (0x0509)\n\n" + ex.getClass().getName() + ":\n" + ex.getMessage(), "I/O error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(KeyManagerGui.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Failed to load keys: (0x050A)\n\n" + ex.getClass().getName() + ":\n" + ex.getMessage(), "I/O error", JOptionPane.ERROR_MESSAGE);
        }
        return new String[0];
    }
    
    private void updateKeyDetails(String key) {
        if (key == null) {
            jTextField2.setText("<unknown>");
            jTextField3.setText("<unknown>");
        }
        CipheredKeyPair skey = keys.get(key);
        jTextField2.setText(skey.getOwner().equals("") ? "<unknown>" : skey.getOwner());
        try {
            jTextField3.setText("SHA-512, RSA-" + skey.getRSAKeyLength());
        } catch (Exception ex) {
            Logger.getLogger(PackGui.class.getName()).log(Level.SEVERE, null, ex);
            jTextField3.setText("SHA-512, RSA");
        }
    }
    
    private boolean checkIfAllIsGood() {
        return (this.outFile != null && this.proj != null && jComboBox1.getSelectedIndex() >= 0);
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
            java.util.logging.Logger.getLogger(PackGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PackGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PackGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PackGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PackGui dialog = new PackGui(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}