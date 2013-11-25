/*
 * --------------------------------------------------------------------------
 * Unimod2MSGPlus.java
 * --------------------------------------------------------------------------
 * Description:       Write unimod.xml files into MSGFPlus
 * Developer:         Andy Jones
 * Created:           03 April 2013
 * Notes:             GUI generated using NetBeans IDE 7.0.1
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.utils;

/**
 * This class allows the upload of modifications specified on Unimod
 * @author jonesar
 */
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import uk.ac.liv.unimod.CompositionT;
import uk.ac.liv.unimod.ModT;
import uk.ac.liv.unimod.ModificationsT;
import uk.ac.liv.unimod.SpecificityT;
import uk.ac.liv.unimod.UnimodT;

public class Unimod2MSGPlus {
    
    private static String UNIMOD_PATH =  "/resources/unimod.xml";
    private List<ModT> modList;
    private List<List<String>> alMods = new ArrayList<List<String>>();

    //... Constructor ...//
    public Unimod2MSGPlus(){
        try{
            InputStream stream = getClass().getResourceAsStream(UNIMOD_PATH);
            UnimodT unimod = unmarshal(UnimodT.class, stream);

            ModificationsT mods = unimod.getModifications();
            modList = mods.getMod();              
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //... Getting all modifications ...//
    public List<List<String>> getAllMods(){        
        try{
            for (ModT mod : modList) {
                String modValue = "";
                CompositionT comp = mod.getDelta();
                double mass = comp.getMonoMass();

                String psiMs = mod.getTitle();
                for(SpecificityT spec : mod.getSpecificity()){
                    String site = spec.getSite();
                    String site2 = spec.getSite();
                    String position = spec.getPosition().value();

                    if(site.equals("N-term") || site.equals("C-term")){
                        site = "*";
                    }
                    if(position.equals("Anywhere")){
                        position = "any";
                    }
                    else if(position.equals("Protein N-term")){
                        position = "Prot-N-term";                            
                    }
                    else if(position.equals("Protein C-term")){
                        position = "Prot-N-term";                            
                    }
                    else if(position.equals("Any N-term")){
                        position = "N-term";                            
                    }
                    else if(position.equals("Any C-term")){
                        position = "C-term";                            
                    }
                    else{
                        System.out.println("Position not recognized:" + position);
                    }
                    List<String> al = new ArrayList<String>();                                        
                    modValue = psiMs+" ("+site2+")";

                    al.add(modValue);
                    al.add(Double.toString(mass));
                    al.add(site);
                    al.add(position);
                    al.add(psiMs);
                    alMods.add(al);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Collections.sort(alMods, new ArrayList2DComparator());        
        return alMods;
    }
    //... Sorting array ...//
    private static class ArrayList2DComparator implements Comparator<List<String>> {  
        public int compare(List<String> P, List<String> Q) {  
            String S = P.get(0);
            String T = Q.get(0);
            
            return S.toLowerCase().compareTo(T.toLowerCase());  
        }  
    }     
    
    //... JAXB unmarshalling ...//
    public <T> T unmarshal( Class<T> docClass, InputStream inputStream )
        throws JAXBException {
        String packageName = docClass.getPackage().getName();
        JAXBContext jc = JAXBContext.newInstance( packageName );
        Unmarshaller u = jc.createUnmarshaller();
        @SuppressWarnings("unchecked")
        JAXBElement<T> doc = (JAXBElement<T>)u.unmarshal( inputStream );
        return doc.getValue();
    }
}
