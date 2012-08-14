package org.proteosuite.data;
/**
 *
 * @author faviel
 */
public class Template2 {
    private int templateIndex;
    private Coord[] aCoords = new Coord[9];

    public Template2() {
        this.templateIndex=0;
        for (int iI=0; iI<9; iI++)
        {
            this.aCoords[iI] = new Coord();
        }        
    }
    public static class Coord
    {
        float x=0.0f, y=0.0f, relativeIntensity=0.0f;      
        public Coord(){
            this.x = 0;
            this.y = 0;
            this.relativeIntensity = 0;
        }
        public float getX()
        {
            return x;
        }
        public float getY()
        {
            return y;
        }
        public float getRelIntensity()
        {
            return relativeIntensity;
        }
        public void setCoord(float x, float y, float relInt)
        {
            this.x = x;
            this.y = y;
            this.relativeIntensity = relInt;
        }
    }
    public void setIndex(int iIndex)
    {
        templateIndex = iIndex;
    }
    public int getIndex()
    {
        return templateIndex;
    }
    public void setCoords(int iOffset, float relInt)
    {        
        float factor1 = 0.0f, factor2 = 0.0f;
        factor1 = (float)1/36;
        factor2 = (float)4/9;
        int iCount = 0;
        
        for (int x=iOffset-1; x<iOffset+2; x++)
        {
            for (int y=-1;y<2;y++)
            {
                if((x==iOffset)&&(y==0))
                {
                    aCoords[iCount].setCoord(x, 0, factor2*relInt);
                }
                else
                {
                    aCoords[iCount].setCoord(x, y, factor1*relInt);
                }
                iCount++;                
            }
        }                
    }    
    public Coord[] getCoords()
    {
        return aCoords;
    }
    public Coord getCoord(int iIndexCoord)
    {
        return aCoords[iIndexCoord];
    }
}
