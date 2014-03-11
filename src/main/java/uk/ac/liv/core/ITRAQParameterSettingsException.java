/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.liv.core;

/**
 *
 * @author SPerkins
 */
public class ITRAQParameterSettingsException extends RuntimeException {
    public ITRAQParameterSettingsException() {
        super("Please specify the parameter settings for each raw file in Project->Set Quantitation Parameters->iTRAQ.");
    }
}
