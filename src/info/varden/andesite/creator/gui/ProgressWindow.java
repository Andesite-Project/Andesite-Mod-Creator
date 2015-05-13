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

import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author Marius
 */
public abstract class ProgressWindow<T> extends JDialog {
    private JPanel basePanel;
    private JProgressBar progress;
    
    private final JLabel[] steps;
    private int currentStep = 0;
    
    public abstract void onComplete(T results);
    public abstract Task getTask();
    
    public ProgressWindow(JFrame parent, JDialog parent1, String title, String[] steps) {
        super(parent, true);
        if (steps.length <= 0) {
            throw new IllegalArgumentException("At least one step must be specified!");
        }
        this.steps = new JLabel[steps.length];
        initComponents(title, steps);
        setLocationRelativeTo(parent);
        if (parent1 != null) {
            setLocationRelativeTo(parent1);
        }
    }
    
    public void nextStep() {
        currentStep++;
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < steps.length; i++) {
                    if (i < currentStep) {
                        steps[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/ok.png")));
                    } else if (i == currentStep) {
                        steps[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/next.png")));
                    } else {
                        steps[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/null16.png")));
                    }
                }
            }
            
        });
        
    }
    
    public boolean performTaskThreaded() {
        final Task task = getTask();
        if (task == null) {
            return false;
        }
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent evt) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        T results = task.run();
                        dispose();
                        onComplete(results);
                    }
                    
                }).start();
            }
        });
        setVisible(true);
        return true;
    }
    
    private void initComponents(String title, String[] steps) {
        for (int i = 0; i < steps.length; i++) {
            this.steps[i] = new JLabel();
            this.steps[i].setText((i + 1) + ". " + steps[i]);
            this.steps[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/" + (i == currentStep ? "next.png" : "null16.png"))));
        }
        basePanel = new javax.swing.JPanel();
        progress = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(title);
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);
        

        progress.setIndeterminate(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(basePanel);
        basePanel.setLayout(layout);
        
        ParallelGroup labelGroup = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        for (JLabel step : this.steps) {
            labelGroup = labelGroup.addComponent(step);
        }
        
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(labelGroup)
                        .addGap(0, 76, Short.MAX_VALUE)))
                .addContainerGap())
        );
        
        SequentialGroup seqGroup = layout.createSequentialGroup().addContainerGap();
        for (int i = 0; i < this.steps.length; i++) {
            if (i > 0) {
                seqGroup = seqGroup.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(this.steps[i]);
            } else {
                seqGroup = seqGroup.addComponent(this.steps[i]);
            }
        }
        seqGroup = seqGroup.addGap(18, 18, Short.MAX_VALUE)
                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap();
        
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seqGroup)
        );

        javax.swing.GroupLayout rootLayout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(rootLayout);
        rootLayout.setHorizontalGroup(rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(basePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        rootLayout.setVerticalGroup(rootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(basePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }
    
    public abstract class Task {
        public abstract T run();
    }
}
