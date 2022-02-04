import java.util.ArrayList;

public class Cache {
    String cacheSize;
    int nrLinesCache;
    int nrInstructions;
    int totalCacheAccesses=0;
    String[] cacheInstructions;
    int[] contorInstr;
    String[] instrAddresses;
    int hit=0;
    int oldHit=0;
    int hitIndex;
    int miss=0;
    int missIndex;
    InstructionsSet instructionsGivenByUser;


    public int getNrLinesCache() {
        return nrLinesCache;
    }

    public void setNrOfLines(Integer nrOfLines){
        this.nrLinesCache=nrOfLines;
        System.out.println("Set number of cache lines to: " + this.nrLinesCache);
    }
    public Cache(){  //nrLinesCache = cacheSize/blockSize;

    }
//    public static int getPowerOf2(String sizeGivenByUser){    // se pot da maxim 4KB ca size
//        int power = 0;
//        if(sizeGivenByUser.contains("KB")){
//            power = 10;
//        }else if(sizeGivenByUser.contains("MB")){
//            power = 20;
//        }else if(sizeGivenByUser.contains("GB")){
//            power = 30;
//        }
//        if(sizeGivenByUser.startsWith("2")){
//            power++;
//        }else if(sizeGivenByUser.startsWith("4")) {
//            power += 2;
//        }
//        return power;
//    }
    public int parseCacheLines(String cacheSize)
    {
        int nrOfLines = Integer.parseInt(cacheSize);
        System.out.println("Number of Cache Lines by Size:" + nrOfLines);
        return nrOfLines;
    }

   /* boolean searchInCache(Cache cache, String instruction){
        for(int i=0; i<nrLinesCache;i++){
            if(cacheInstructions==null){
                return false;
            }
            if(cacheInstructions[i].equals(instruction)){
                return true;
            }
        }
        return false;
    }*/

    void initCache(int sizeforCache) {
        this.cacheInstructions=new String[sizeforCache];
        this.instrAddresses=new String[sizeforCache];
        this.contorInstr=new int[sizeforCache];
    }

    boolean isCacheEmpty(Cache cache){
        if(cacheInstructions==null){
            return true;
        }
        else
            return false;
    }

    void clearCache(){
        for (int i=0;i<cacheInstructions.length;i++){
            cacheInstructions[i]=null;
            instrAddresses[i]=null;
        }
    }

    int getIndexOfInstructionToSubstitute(){
        //int min =contorInstr[0];
        int indexMin= 0;
        for(int i=0; i < cacheInstructions.length; i++) {
            if (cacheInstructions[i] == null) {
                return i;
            }
        }

        for(int i =0; i<contorInstr.length;i++){
            if(contorInstr[i]==0)
            {
                indexMin=i;
                //min=contorInstr[i];
            }
        }
        return indexMin;
    }

    void addInstruction(Cache cache, String instructionToAdd, String instrAddr){
        int index=0;
        int indexAdr=0;
        int ok=0;
        for(int i=0; i < cacheInstructions.length; i++) {
            if (cacheInstructions[i] == null) {
                System.out.println("MISS");
                miss++;
                totalCacheAccesses++;
                for(int j=0;j<contorInstr.length;j++)
                    if(j!=i && contorInstr[j]>0)
                        contorInstr[j]--;
                index = getIndexOfInstructionToSubstitute();
                indexAdr = getIndexOfInstructionToSubstitute();
                System.out.println("Index: " + index + " IndexAdr: " + indexAdr);
                break;
            }
            if (cacheInstructions[i].equals(instructionToAdd)) {
                System.out.println("HIT");
                ok=1;
                oldHit=hit;
                hit++;
                hitIndex=i;
                totalCacheAccesses++;
                for(int j=0;j<contorInstr.length;j++)
                    if(j!=i && contorInstr[j]>0)
                        contorInstr[j]--;
                break;
            }
        }

        if(ok==0) {
            index = getIndexOfInstructionToSubstitute();
            indexAdr = getIndexOfInstructionToSubstitute();
            cacheInstructions[index] = instructionToAdd;
            instrAddresses[indexAdr] = instrAddr;
            contorInstr[index]=nrLinesCache;
        }
    }

    boolean inCache(Cache cache, String instructionToFind){
        int index=0;
        int indexAdr=0;
        int ok=0;
        //System.out.println("Nr linii cache "+ nrLinesCache);
        for(int i=0; i < cacheInstructions.length; i++) {
            if (cacheInstructions[i] == null) {
                //System.out.println("MISS in cache");
                index=i;
                break;
            }
            if (cacheInstructions[i].equals(instructionToFind)) {
                //System.out.println("HIT in cache");
                ok=1;
                return true;
            }
        }
        return false;
    }

    double hitRate(Cache cache){
        return (1 - missRate(cache));
    }

    double missRate(Cache cache){
        return (double) (cache.miss)/(totalCacheAccesses);
    }
}
