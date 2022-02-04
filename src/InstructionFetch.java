public class InstructionFetch {
    String opcode;
    String rs;
    String rt;
    String rd;
    String immediate;

    String[] setOfInstructions;
    public InstructionFetch() {
        setOfInstructions = new String[10];
    }

    public String[] splitInstr(String instruction){
        String[] arr = instruction.split(" ");
        return arr;
    }

    public String instructionBreakdown(String instrString){
        InstructionFetch instruction = new InstructionFetch();
        if(instrString.startsWith("add") || instrString.startsWith("sub"))
        {

            instruction.opcode = "0000";
            instruction.rs = " " + String.format("%4s", Integer.toBinaryString(Integer.parseInt(splitInstr(instrString)[1]))).replace(' ', '0') + " ";
            instruction.rt = " " + String.format("%4s", Integer.toBinaryString(Integer.parseInt(splitInstr(instrString)[2]))).replace(' ', '0') + " ";
            instruction.rd = " " + String.format("%4s", Integer.toBinaryString(Integer.parseInt(splitInstr(instrString)[3]))).replace(' ', '0') + " ";
            instruction.immediate = "";
        }
        if(instrString.startsWith("addi") || instrString.startsWith("subi"))
        {
            instruction.opcode = "0010";
            instruction.rs = " " + String.format("%4s", Integer.toBinaryString(Integer.parseInt(splitInstr(instrString)[1]))).replace(' ', '0') + " ";
            instruction.rt = " " + String.format("%4s", Integer.toBinaryString(Integer.parseInt(splitInstr(instrString)[2]))).replace(' ', '0') + " ";
            instruction.rd = ""; //+ String.format("%4s", Integer.toBinaryString(Integer.parseInt(splitInstr(instrString)[3]))).replace(' ', '0') + " ";
            instruction.immediate = " " + String.format("%4s", Integer.toBinaryString(Integer.parseInt(splitInstr(instrString)[3]))).replace(' ', '0') + " ";
        }
        if(instrString.startsWith("lw") || instrString.startsWith("sw"))
        {
            instruction.opcode = "0100";
            instruction.rs = " " + String.format("%4s", Integer.toBinaryString(Integer.parseInt(splitInstr(instrString)[3]))).replace(' ', '0') + " ";
            instruction.rt = "";//" " + String.format("%4s", Integer.toBinaryString(Integer.parseInt(splitInstr(instrString)[2]))).replace(' ', '0') + " ";
            instruction.rd = "";
            instruction.immediate = " " + String.format("%4s", Integer.toBinaryString(Integer.parseInt(splitInstr(instrString)[1]))).replace(' ', '0') + "  " + String.format("%4s", Integer.toBinaryString(Integer.parseInt(splitInstr(instrString)[2]))).replace(' ', '0') + " ";
        }
        if(instrString.startsWith("j"))
        {
            instruction.opcode = "0110";
            instruction.immediate = " " + String.format("%12s", Integer.toBinaryString(Integer.parseInt(splitInstr(instrString)[1]))).replace(' ', '0') + " ";
            instruction.rt = "";
            instruction.rd = "";
            instruction.rs = "";
        }
        String instructionBreak = "" + instruction.opcode + " " + instruction.rs + instruction.rt + instruction.rd + instruction.immediate;
        return instructionBreak;
    }



}