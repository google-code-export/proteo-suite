/*
 * --------------------------------------------------------------------------
 * psTemplate.java
 * --------------------------------------------------------------------------
 * Description:       Template for Label Free Quantitation
 * Developer:         fgonzalez
 * Created:           10 July 2012
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.data;

public class psTemplate {
    private int templateIndex;
    /**
     *  This corresponds to the 9 positions x 6 isotopes
     */
    private Coord[] aCoords = new Coord[54];

    public psTemplate() {
        this.templateIndex=0;
        for (int iI=0; iI<54; iI++){
            this.aCoords[iI] = new Coord();
        }
    }
    public static class Coord{
        int x=0, y=0;
        float relativeIntensity=0.0f;      
        public Coord(){
            this.x = 0;
            this.y = 0;
            this.relativeIntensity = 0;
        }
        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }
        public float getRelIntensity(){
            return relativeIntensity;
        }
        public void setCoord(int x, int y, float relInt){
            this.x = x;
            this.y = y;
            this.relativeIntensity = relInt;
        }
    }
    public void setIndex(int iIndex){
        templateIndex = iIndex;
    }
    public int getIndex(){
        return templateIndex;
    }
    /**
     * Fills the coordinate
     * @param x
     * @param y
     * @param relInt
     * @param iPos
     */
    public void setCoords(int x, int y, float relInt, int iPos){
        aCoords[iPos].setCoord(x, y, relInt);
    }
    /**
     * Creates a 9 points template
     * @param iOffset
     * @param relInt
     * @param iIndexTemp1
     */
    public void setCoords(int iOffset, float relInt, int iIndexTemp1){        
        float factor1 = 0.0f, factor2 = 0.0f;
        factor1 = (float)1/36;
        factor2 = (float)4/9;
        int iCount = iIndexTemp1*9;
        
        for (int x=iOffset-1; x<iOffset+2; x++){
            for (int y=-1;y<2;y++){
                if((x==iOffset)&&(y==0)){
                    aCoords[iCount].setCoord(x, 0, factor2*relInt);
                }
                else{
                    aCoords[iCount].setCoord(x, y, factor1*relInt);
                }
                iCount++;
            }
        }                
    }    
    public Coord[] getCoords(){
        return aCoords;
    }
    public Coord getCoord(int iIndexCoord){
        return aCoords[iIndexCoord];
    }
}
