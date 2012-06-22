/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.data;

import java.util.ArrayList;

/**
 *
 * @author faviel
 */
public class IdentMLparams {

    private ArrayList<String> Database;
    private String Enzyme;
    private int MissedCleavage;
    private ArrayList<String> FixedMod;
    private ArrayList<String> VarMod;
    private double PepTol;
    private String PepTolUnit;
    private int NumC13;
    private double MS2Tol;
    private String MS2TolUnit;
    private int PepChr;
    private String DataFileName;
    private String DataFormat;
    private String MonoAver;
    private double PrecursorMz;
    private String Instrument;
    private boolean Decoy;
    private boolean ErrTol;
    private int NumTop;

    public IdentMLparams(){
        // TODO: initialise
    }
    
    public void setDatabase(ArrayList<String> Database) {
        this.Database = new ArrayList<String>(Database);
    }

    public ArrayList<String> getDatabase() {
        return this.Database;
    }

    public void setEnzyme(String Enzyme) {
        this.Enzyme = Enzyme;
    }

    public String getEnzyme() {
        return this.Enzyme;
    }

    public void setMissedCleavage(int MissedCleavage) {
        this.MissedCleavage = MissedCleavage;
    }

    public int getMissedCleavage() {
        return this.MissedCleavage;
    }
    
    public void setFixedMod(ArrayList<String> FixedMod){
        this.FixedMod = new ArrayList<String>(FixedMod);
    }
    
    public ArrayList<String> getFixedMod(){
        return this.FixedMod;
    }
    
    public void setVarMod(ArrayList<String> VarMod){
        this.VarMod = new ArrayList<String>(VarMod);
    }
    
    public ArrayList<String> getVarMod(){
        return this.VarMod;
    }
    
    public void setPepTol(double PepTol){
        this.PepTol = PepTol;
    }
    
    public double getPepTol(){
        return this.PepTol;
    }
    
    public void setPepTolUnit(String PepTolUnit){
        this.PepTolUnit = PepTolUnit;
    }
    
    public String getPepTolUnit(){
        return this.PepTolUnit;
    }
    
    public void setNumC13(int NumC13){
        this.NumC13 = NumC13;
    }
    
    public int getNumC13(){
        return this.NumC13;
    }
    
    public void setMS2Tol(double MS2Tol){
        this.MS2Tol = MS2Tol;
    }
    
    public double getMS2Tol(){
        return this.MS2Tol;
    }
    
    public void setMS2TolUnit(String MS2TolUnit){
        this.MS2TolUnit = MS2TolUnit;
    }
    
    public String getMS2TolUnit(){
        return this.MS2TolUnit;
    }
    
    public void setPepChr(int PepChr){
        this.PepChr = PepChr;
    }
    
    public int getPepChr(){
        return this.PepChr;
    }
    
    public void setDataFileName(String DataFileName){
        this.DataFileName = DataFileName;
    }
    
    public String getDataFileName(){
        return this.DataFileName;
    }
    
    public void setDataFormat(String DataFormat){
        this.DataFormat = DataFormat;
    }
    
    public String getDataFormat(){
        return this.DataFormat;
    }
    
    public void setMonoAver(String MonoAver){
        this.MonoAver = MonoAver;
    }
    
    public String getMonoAver(){
        return this.MonoAver;
    }
    
    public void setPrecursorMz(double PrecursorMz){
        this.PrecursorMz = PrecursorMz;
    }
    
    public double getPrecursorMz(){
        return this.PrecursorMz;
    }
    
    public void setInstrument(String Instrument){
        this.Instrument = Instrument;
    }
    
    public String getInstrument(){
        return this.Instrument;
    }
    
    public void setDecoy(boolean Decoy){
        this.Decoy = Decoy;
    }
    
    public boolean getDecoy(){
        return this.Decoy;
    }
    
    public void setErrTol(boolean ErrTol){
        this.ErrTol = ErrTol;
    }
    
    public boolean getErrTol(){
        return this.ErrTol;
    }
    
    public void setNumTop(int NumTop){
        this.NumTop = NumTop;
    }
    
    public int getNumTop(){
        return this.NumTop;
    }
}
