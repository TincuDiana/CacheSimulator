import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CacheSim {
        private JFrame frame;
        private JTable mainMemTable;
        private JTable cacheMemTable;
        private JTextField hitRateTextField;
        private JTextField missRateTextField;
        private JTextField instrTextField;
        private JTextField instrBrTextField;

        /**
         * Launch the application.
         */
        public static void main(String[] args) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        CacheSim window = new CacheSim();
                        window.frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        /**
         * Create the application.
         */
        public CacheSim() {
            initialize();
        }

        /**
         * Initialize the contents of the frame.
         */
        private void initialize() {
            InstructionsSet instructionsGivenByUser = new InstructionsSet();
            instructionsGivenByUser.setFirstSetOfInstructions();
            MainMemory mainMemory=new MainMemory();
            Cache cache = new Cache();
            InstructionFetch instruction= new InstructionFetch();
            final int[] counter = {0};
            final int[][] instrAddresses = {{0}};
            final int[] counterForSetInstr = {0};
            final int[] nrOfMainMemLines = new int[1];
            final int[] nrOfCacheMemLines = {0};
            final int[][] addressArray = {new int[100]};
            final String[][] addressArrayStr = {new String[100]};

            //instructionsGivenByUser.setInstructionSet(counterForSetInstr[0]);

            frame = new JFrame();
            frame.setBounds(100, 100, 724, 479);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setLayout(null);

            JScrollPane scrollPaneMain = new JScrollPane();
            scrollPaneMain.setBounds(21, 50, 121, 296);
            frame.getContentPane().add(scrollPaneMain);

            /*String[] mainMemoryColumns = {"INDEX","DATA"};
            String[][] data = {{"000111", "0001010"},{"000111", "0001010"}};
            mainMemTable = new JTable(data,mainMemoryColumns);
            scrollPaneMain.setViewportView(mainMemTable);*/


            JScrollPane scrollPaneCache = new JScrollPane();
            scrollPaneCache.setBounds(158, 50, 258, 296);
            frame.getContentPane().add(scrollPaneCache);

            /*String[] cacheMemoryColumns = {"INDEX", "ADDRESS", "DATA"};
            String[][] cacheData = {{"", "", ""},{"","", ""}};
            cacheMemTable = new JTable(cacheData,cacheMemoryColumns);
            scrollPaneCache.setViewportView(cacheMemTable);*/

            JLabel mainMemLabel = new JLabel("Main Memory");
            mainMemLabel.setBounds(50, 25, 92, 14);
            frame.getContentPane().add(mainMemLabel);

            JLabel cacheMemLabel = new JLabel("Cache Memory");
            cacheMemLabel.setBounds(230, 25, 86, 14);
            frame.getContentPane().add(cacheMemLabel);

            JLabel mainSizeLabel = new JLabel("Main Memory Size in Bytes:");
            mainSizeLabel.setBounds(442, 155, 155, 14);
            frame.getContentPane().add(mainSizeLabel);

            JLabel cacheSizeLabel = new JLabel("Cache Memory Size:");
            cacheSizeLabel.setBounds(442, 218, 175, 14);
            frame.getContentPane().add(cacheSizeLabel);

            JLabel hitRateLabel = new JLabel("Hit Rate");
            hitRateLabel.setBounds(442, 273, 121, 14);
            frame.getContentPane().add(hitRateLabel);

            JLabel missRateLabel = new JLabel("Miss Rate");
            missRateLabel.setBounds(442, 331, 94, 14);
            frame.getContentPane().add(missRateLabel);

            hitRateTextField = new JTextField();
            hitRateTextField.setBounds(574, 271, 86, 17);
            frame.getContentPane().add(hitRateTextField);
            hitRateTextField.setColumns(10);


            missRateTextField = new JTextField();
            missRateTextField.setBounds(574, 329, 86, 17);
            frame.getContentPane().add(missRateTextField);
            missRateTextField.setColumns(10);

            instrTextField = new JTextField();
            instrTextField.setBounds(442, 74, 175, 20);
            frame.getContentPane().add(instrTextField);
            instrTextField.setColumns(10);
            instrTextField.setText(instructionsGivenByUser.firstSetOfInstructions[counter[0]]);
            instructionsGivenByUser.currentSet=instructionsGivenByUser.firstSetOfInstructions;

            JLabel instrNameLabel = new JLabel("Instruction Name:");
            instrNameLabel.setBounds(442, 50, 121, 14);
            frame.getContentPane().add(instrNameLabel);

            /*JLabel label = new JLabel("New label");
            label.setBounds(579, 50, 21, -9);
            frame.getContentPane().add(label);*/

            JLabel instructionBreakDownLabel = new JLabel("Instruction code:");
            instructionBreakDownLabel.setBounds(21, 372, 175, 26);
            frame.getContentPane().add(instructionBreakDownLabel);

            instrBrTextField = new JTextField();
            instrBrTextField.setBounds(178, 375, 238, 20);
            frame.getContentPane().add(instrBrTextField);
            instrBrTextField.setColumns(10);

            JLabel inBytesLabel = new JLabel("in Bytes");
            inBytesLabel.setBounds(442, 231, 94, 14);
            frame.getContentPane().add(inBytesLabel);

            String[] sizesOfMain = {"8", "16", "32"};
            JComboBox mainSizeComboBox = new JComboBox(sizesOfMain);
            mainSizeComboBox.setBounds(600, 152, 86, 20);
            frame.getContentPane().add(mainSizeComboBox);

            mainSizeComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedSize = mainSizeComboBox.getSelectedItem().toString();
                    nrOfMainMemLines[0] = mainMemory.parseMemoryLines(selectedSize);
                    mainMemory.setNrOfLines(nrOfMainMemLines[0]);

                    String[] mainMemoryColumns = {"INDEX","DATA"};
                    String[][] data = new String[mainMemory.nrOfLines][3];
                    instrAddresses[0] = new int[mainMemory.nrOfLines];
                    for(int i=0;i<instructionsGivenByUser.currentSet.length ;i++) {
                        data[i][0] = Integer.toBinaryString(i);
                        data[i][1] = instructionsGivenByUser.currentSet[i];
                    }

                    addressArray[0] = new int[instructionsGivenByUser.currentSet.length];
                    for(int i = 0; i < addressArray[0].length; i++)
                    {
                        addressArray[0][i] = i;
                    }
                    for(int i = 0; i < addressArray[0].length; i++)
                    {
                        /*if(addressArray[0][i] == 0 || addressArray[0][i] == 1 ) {
                            addressArrayStr[0][i] = String.format("%4s", Integer.toBinaryString(addressArray[0][i])).replace(' ', '0');
                            data[i][0] = String.format("%4s", Integer.toBinaryString(addressArray[0][i])).replace(' ', '0');
                        }else if(addressArray[0][i] == 2 || addressArray[0][i] == 3 )
                        {
                            addressArrayStr[0][i] = String.format("%4s", Integer.toBinaryString(addressArray[0][i])).replace(' ', '0');
                            data[i][0] = String.format("%4s", Integer.toBinaryString(addressArray[0][i])).replace(' ', '0');
                        }
                        else*/
                        {
                            addressArrayStr[0][i] = Integer.toBinaryString(addressArray[0][i]);
                            data[i][0] = String.format("%4s", Integer.toBinaryString(addressArray[0][i])).replace(' ','0');
                        }
                    }

                    mainMemTable = new JTable(data,mainMemoryColumns);
                    scrollPaneMain.setViewportView(mainMemTable);
                }
            });

            String[] sizesOfCache = {"4", "8"};
            JComboBox cacheSizeComboBox = new JComboBox(sizesOfCache);
            cacheSizeComboBox.setBounds(600, 215, 86, 20);
            frame.getContentPane().add(cacheSizeComboBox);
            cacheSizeComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedCacheSize = cacheSizeComboBox.getSelectedItem().toString();
                    nrOfCacheMemLines[0] = cache.parseCacheLines(selectedCacheSize);
                    cache.setNrOfLines(nrOfCacheMemLines[0]);
                    cache.initCache(nrOfCacheMemLines[0]);
                    String[] cacheMemoryColumns = {"INDEX", "ADDRESS", "DATA"};
                    String[][] cacheData=new String[cache.nrLinesCache][3];

                    cacheMemTable = new JTable(cacheData,cacheMemoryColumns);
                    scrollPaneCache.setViewportView(cacheMemTable);

                }
            });


            JButton nextInstrButton = new JButton("Next");
            nextInstrButton.setBounds(620, 73, 66, 23);
            frame.getContentPane().add(nextInstrButton);
            nextInstrButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(counter[0] < (instructionsGivenByUser.currentSet.length ))
                    {
                        instrTextField.setText(instructionsGivenByUser.currentSet[counter[0]]);
                        System.out.println("setul curent de instructiuni este: "+ instructionsGivenByUser.currentSet);
                    }
                    else
                    {
                        counter[0]=0;
                        instrTextField.setText(instructionsGivenByUser.currentSet[counter[0]]);
                    }

                    instrBrTextField.setText(instruction.instructionBreakdown(instrTextField.getText()));

                    ActionEvent actionEvent=null;
                    mainSizeComboBox.actionPerformed(actionEvent);
                    //final int[] addressInMain = new int[mainMemory.nrOfLines];

                    System.out.println("Instructiunea curenta " + instructionsGivenByUser.currentSet[counter[0]]);
                    System.out.println("counter[0]= " + counter[0]);
                    cache.addInstruction(cache,instructionsGivenByUser.currentSet[counter[0]],addressArrayStr[0][counter[0]]);

                    String[] mainMemoryColumns = {"INDEX","DATA"};
                    String[][] data = new String[mainMemory.nrOfLines][3];
                    instrAddresses[0] = new int[mainMemory.nrOfLines];
                    for(int i=0;i<instructionsGivenByUser.currentSet.length ;i++) {
                        data[i][0] = Integer.toBinaryString(i);
                        data[i][1] = instructionsGivenByUser.currentSet[i];
                    }

                    addressArray[0] = new int[instructionsGivenByUser.currentSet.length];
                    for(int i = 0; i < addressArray[0].length; i++)
                    {
                        addressArray[0][i] = i;
                    }
                    for(int i = 0; i < addressArray[0].length; i++)
                    {
                        addressArrayStr[0][i] = Integer.toBinaryString(addressArray[0][i]);
                        data[i][0] = String.format("%4s", Integer.toBinaryString(addressArray[0][i])).replace(' ','0');
                    }

                    mainMemTable = new JTable(data,mainMemoryColumns){
                        public Component prepareRenderer(TableCellRenderer renderer,
                                                         int row, int column)
                        {
                            if(cache.inCache(cache, data[counter[0]][1])==false){
                                cache.missIndex=counter[0];
                            }
                            Component c = super.prepareRenderer(renderer, row, column);
                            Color color1 = Color.RED;
                            Color color2 = Color.WHITE;
                            if(!c.getBackground().equals(getSelectionBackground())) {
                                Color coleur = (row == cache.missIndex ? color1 : color2);
                                c.setBackground(coleur);
                                coleur = null;
                            }
                            return c;
                        }
                    };
                    scrollPaneMain.setViewportView(mainMemTable);

                    for(int i= 0;i<cache.cacheInstructions.length;i++) {
                        System.out.println("Instr din cache: " + cache.cacheInstructions[i]);
                        System.out.println("[contorInstr]= " + cache.contorInstr[i]);
                    }
                    String[] cacheMemoryColumns = {"INDEX", "ADDRESS", "DATA"};
                    String[][] cacheData=new String[cache.nrLinesCache][3];

                    for(int i=0 ;i < cache.cacheInstructions.length;i++) {
                        cacheData[i][0] = String.format("%4s", Integer.toBinaryString(i)).replace(' ', '0');
                        cacheData[i][1] = String.format("%4s", cache.instrAddresses[i]).replace(' ', '0');
                        cacheData[i][2] = cache.cacheInstructions[i];
                    }

                    cacheMemTable = new JTable(cacheData,cacheMemoryColumns){
                        public Component prepareRenderer(TableCellRenderer renderer,
                                                         int row, int column)
                        {
                            Component c = super.prepareRenderer(renderer, row, column);
                            Color color1 = Color.GREEN;
                            Color color2 = Color.WHITE;
                            if(!c.getBackground().equals(getSelectionBackground())) {
                                Color coleur = (row == cache.hitIndex ? color1 : color2);
                                c.setBackground(coleur);
                                coleur = null;
                            }
                            return c;
                        }
                    };
                    scrollPaneCache.setViewportView(cacheMemTable);

                    //System.out.println("[contorInstr]= " + cache.contorInstr[counter[0]]);
                    counter[0]++;

                    DecimalFormat df = new DecimalFormat("#.######");
                    df.setRoundingMode(RoundingMode.CEILING);

                    hitRateTextField.setText("" + df.format(cache.hitRate(cache)));
                    System.out.println("cache.hit: " + cache.hit + " cache hitRate: " + df.format(cache.hitRate(cache)) + " totalCacheAccesses: " + cache.totalCacheAccesses);

                    missRateTextField.setText("" + df.format(cache.missRate(cache)));
                    System.out.println("cache.miss: " + cache.miss + " cache missRate: " + df.format(cache.missRate(cache)) + " totalCacheAccesses: " + cache.totalCacheAccesses);
                }
            });


            JButton nextSetOfInstrButton = new JButton("Next Set of Instructions");
            nextSetOfInstrButton.setBounds(442, 104, 244, 23);
            frame.getContentPane().add(nextSetOfInstrButton);
            nextSetOfInstrButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cache.clearCache();

                    final int[] addressInMain = new int[mainMemory.nrOfLines];

                    if(counterForSetInstr[0]<3)
                    {
                        System.out.println("current set = " + instructionsGivenByUser.currentSet);
                        instructionsGivenByUser.setInstructionSet(counterForSetInstr[0]);
                        counter[0] = 0;
                    }
                    else
                    {
                        counterForSetInstr[0]=0;
                        counter[0]=0;
                        instructionsGivenByUser.setInstructionSet(counterForSetInstr[0]);
                    }

                    ActionEvent actionEvent=null;
                    mainSizeComboBox.actionPerformed(actionEvent);

                    nextInstrButton.doClick();
                    counterForSetInstr[0]++;
                    counter[0]=0;

                    String[] mainMemoryColumns = {"INDEX","DATA"};
                    String[][] data = new String[mainMemory.nrOfLines][3];
                    instrAddresses[0] = new int[mainMemory.nrOfLines];
                    for(int i=0;i<instructionsGivenByUser.currentSet.length ;i++) {
                        data[i][0] = Integer.toBinaryString(i);
                        data[i][1] = instructionsGivenByUser.currentSet[i];
                    }

                    addressArray[0] = new int[instructionsGivenByUser.currentSet.length];
                    for(int i = 0; i < addressArray[0].length; i++)
                    {
                        addressArray[0][i] = i;
                    }
                    for(int i = 0; i < addressArray[0].length; i++)
                    {
                        addressArrayStr[0][i] = Integer.toBinaryString(addressArray[0][i]);
                        data[i][0] = String.format("%4s", Integer.toBinaryString(addressArray[0][i])).replace(' ','0');
                    }

                    mainMemTable = new JTable(data,mainMemoryColumns){
                        public Component prepareRenderer(TableCellRenderer renderer,
                                                         int row, int column)
                        {
                            if(cache.inCache(cache, data[counter[0]][1])==false){
                                cache.missIndex=counter[0];
                            }
                            Component c = super.prepareRenderer(renderer, row, column);
                            Color color1 = Color.RED;
                            Color color2 = Color.WHITE;
                            if(!c.getBackground().equals(getSelectionBackground())) {
                                Color coleur =color2;
                                c.setBackground(coleur);
                                coleur = null;
                            }
                            return c;
                        }
                    };
                    scrollPaneMain.setViewportView(mainMemTable);

                    String[] cacheMemoryColumns = {"INDEX", "ADDRESS", "DATA"};
                    String[][] cacheData=new String[cache.nrLinesCache][3];

                    for(int i=0 ;i < cache.cacheInstructions.length;i++) {
                        cacheData[i][0] = String.format("%4s", Integer.toBinaryString(i)).replace(' ', '0');
                        cacheData[i][1] = String.format("%4s", cache.instrAddresses[i]).replace(' ', '0');
                        cacheData[i][2] = cache.cacheInstructions[i];
                    }

                    cacheMemTable = new JTable(cacheData,cacheMemoryColumns){
                        public Component prepareRenderer(TableCellRenderer renderer,
                                                         int row, int column)
                        {
                            Component c = super.prepareRenderer(renderer, row, column);
                            Color color2 = Color.WHITE;
                            if(!c.getBackground().equals(getSelectionBackground())) {
                                Color coleur = color2;
                                c.setBackground(coleur);
                                coleur = null;
                            }
                            return c;
                        }
                    };
                    scrollPaneCache.setViewportView(cacheMemTable);


                    DecimalFormat df = new DecimalFormat("#.########");
                    df.setRoundingMode(RoundingMode.CEILING);

                    hitRateTextField.setText("" + df.format(cache.hitRate(cache)));
                    System.out.println("cache.hit: " + cache.hit + " cache hitRate: " + df.format(cache.hitRate(cache)) + " totalCacheAccesses: " + cache.totalCacheAccesses);

                    missRateTextField.setText("" + df.format(cache.missRate(cache)));
                    System.out.println("cache.miss: " + cache.miss + " cache missRate: " + df.format(cache.missRate(cache)) + " totalCacheAccesses: " + cache.totalCacheAccesses);
                }
            });
        }

}
