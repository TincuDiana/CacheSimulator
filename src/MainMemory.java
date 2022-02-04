public class MainMemory {
    String mainMemorySize;
    String tagBits;
    String indexBits;
    int nrOfLines;
    private int nbOfAddressBits;

    public MainMemory() {
    }

    public void setNrOfLines(Integer nrOfLines){
        this.nrOfLines=nrOfLines;
    }

    public int parseMemoryLines(String mainMemorySize)
    {
        int nrOfLines = Integer.parseInt(mainMemorySize);
        return nrOfLines;
    }

    public boolean searchForInstruction(String instruction, String setOfInstructions[]){
        for (String i:setOfInstructions
             ) {
            if(instruction.equals(i))
                return true;
        }
        return false;
    }


}
