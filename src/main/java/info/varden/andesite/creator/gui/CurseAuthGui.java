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

import java.awt.Cursor;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;

/**
 *
 * @author Marius
 */
public class CurseAuthGui extends javax.swing.JDialog implements HyperlinkListener {

    /**
     * Creates new form CurseAuthGui
     */
    public CurseAuthGui(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane2 = getPaneWithCookieSupport();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jEditorPane2.setEditable(false);
        jEditorPane2.setContentType("text/html"); // NOI18N
        jEditorPane2.addHyperlinkListener(this);
        jScrollPane2.setViewportView(jEditorPane2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            showPage(new URL("http://localhost/curselogin/Login%20-%20CurseForge.html"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(CurseAuthGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private JEditorPane getPaneWithCookieSupport() {
        return new JEditorPane() {
            private Hashtable pageProperties;
            
            @Override
            protected InputStream getStream(URL page) throws IOException {
                System.out.println(page.toString());
                final URLConnection conn = page.openConnection();
                if (conn instanceof HttpURLConnection) {
                    HttpURLConnection hconn = (HttpURLConnection) conn;
                    hconn.setInstanceFollowRedirects(false);
                    Object postData = getDocument().getProperty("javax.swing.JEditorPane.postdata");
                    if (postData != null) {
                        handlePostData(hconn, postData);
                    }
                    int response = hconn.getResponseCode();
                    boolean redirect = (response >= 300 && response <= 399);

                    if (redirect) {
                        String loc = conn.getHeaderField("Location");
                        if (loc.startsWith("http", 0)) {
                            page = new URL(loc);
                        } else {
                            page = new URL(page, loc);
                        }
                        return getStream(page);
                    }
                }

                if (SwingUtilities.isEventDispatchThread()) {
                    handleConnectionProperties(conn);
                } else {
                    try {
                        SwingUtilities.invokeAndWait(new Runnable() {
                            public void run() {
                                handleConnectionProperties(conn);
                            }
                        });
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
                
                return conn.getInputStream();
            }
            
            private void handlePostData(HttpURLConnection conn, Object postData) throws IOException {
                conn.setDoOutput(true);
                DataOutputStream os = null;
                try {
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes((String) postData);
                } finally {
                    if (os != null) {
                        os.close();
                    }
                }
            }
            
            private void handleConnectionProperties(URLConnection conn) {
                if (pageProperties == null) {
                    pageProperties = new Hashtable();
                }
                String type = conn.getContentType();
                if (type != null) {
                    setContentType(type);
                    pageProperties.put("content-type", type);
                }
                pageProperties.put(Document.StreamDescriptionProperty, conn.getURL());
                String enc = conn.getContentEncoding();
                if (enc != null) {
                    pageProperties.put("content-encoding", enc);
                }
            }
        };
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
            java.util.logging.Logger.getLogger(CurseAuthGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CurseAuthGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CurseAuthGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CurseAuthGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CurseAuthGui dialog = new CurseAuthGui(new javax.swing.JFrame(), true);
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
    private javax.swing.JEditorPane jEditorPane2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

    private void showPage(URL url) {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            jEditorPane2.setPage(url);
        } catch (IOException ex) {
            Logger.getLogger(CurseAuthGui.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }
    
    @Override
    public void hyperlinkUpdate(HyperlinkEvent e) {
        System.out.println(e.getURL().toString());
    }
}
