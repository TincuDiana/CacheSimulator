public class InstructionsSet {
        static String[] firstSetOfInstructions;
        static String[] secondSetOfInstructions;
        static String[] thirdSetOfInstructions;
        String[] currentSet;

        public void setFirstSetOfInstructions(){
            String[] instr = new String[9];
            instr[0] = "" + "add 3 4 5";
            instr[1] = "" + "sub 5 4 2";
            instr[2] = "" + "add 1 3 2";
            instr[3] = "" + "sub 5 4 2";
            instr[4] = "" + "lw 3 2 1";
            instr[5] = "" + "lw 3 2 1";
            instr[6] = "" + "lw 3 2 1";
            instr[7] = "" + "add 3 4 5";
            instr[8] = "" + "lw 5 4 3";
            setFirstSetOfInstructions(instr);
        }

        public void setSecondSetOfInstructions(){
            String[] instr = new String[5];
            instr[4] = "" + "add 3 4 5";
            instr[3] = "" + "sub 5 4 2";
            instr[2] = "" + "sw 4 6 2";
            instr[1] = "" + "j 3";
            instr[0] = "" + "j 2";
            setSecondSetOfInstructions(instr);
        }

        public void setThirdSetOfInstructions(){
            String[] instr = new String[6];
            instr[2] = "" + "addi 1 2 3";
            instr[4] = "" + "addi 1 2 3";
            instr[5] = "" + "subi 1 2 3";
            instr[1] = "" + "j 2";
            instr[0] = "" + "sw 3 2 1";
            instr[3] = "" + "j 1";
            setThirdSetOfInstructions(instr);
        }

        public void setFirstSetOfInstructions(String[] firstSetOfInstructions) {
            this.firstSetOfInstructions = firstSetOfInstructions;
        }

        public void setSecondSetOfInstructions(String[] secondSetOfInstructions) {
            this.secondSetOfInstructions = secondSetOfInstructions;
        }

        public void setThirdSetOfInstructions(String[] thirdSetOfInstructions) {
            this.thirdSetOfInstructions = thirdSetOfInstructions;
        }


        void setInstructionSet(int nr){
            if(nr==0) {
                setFirstSetOfInstructions();
                this.currentSet = firstSetOfInstructions;
            }
            if(nr==1) {
                setSecondSetOfInstructions();
                this.currentSet = secondSetOfInstructions;
            }
            if(nr==2){
                setThirdSetOfInstructions();
                this.currentSet = thirdSetOfInstructions;
            }
        }

}
